using System.Text.Json;
using Confluent.Kafka;
using Contracts;
using NotificationService.WebSockets;

namespace NotificationService.Consumers;

public class NotificationConsumer(ConsumerConfig cfg, NotificationHub hub, ILogger<NotificationConsumer> log)
    : BackgroundService
{
    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        await Task.Delay(2000, stoppingToken);

        while (!stoppingToken.IsCancellationRequested)
        {
            try
            {
                using var consumer = new ConsumerBuilder<string, string>(cfg).Build();
                consumer.Subscribe([
                    Topics.PriceCalculated,
                    Topics.RideStatusChanged,
                    Topics.PaymentSucceeded,
                    Topics.PaymentFailed
                ]);

                log.LogInformation("[NotificationConsumer] Kafka consumer inicializado.");

                while (!stoppingToken.IsCancellationRequested)
                {
                    var cr = consumer.Consume(stoppingToken);
                    if (cr?.Message == null) continue;

                    var keyString = cr.Message.Key ?? TryExtractRideId(cr.Message.Value);

                    log.LogInformation("Evento recebido: {Topic} key={Key} len={Len}",
                        cr.Topic, keyString, cr.Message.Value?.Length ?? 0);
                    
                    if (Guid.TryParse(keyString, out var rideIdGuid))
                    {
                        if (cr.Message.Value != null) await hub.BroadcastAsync(rideIdGuid, cr.Message.Value);
                    }

                    log.LogInformation("Broadcast enviado para rideId={RideId}", keyString);
                }
            }
            catch (ConsumeException ex) when (ex.Error.Code == ErrorCode.UnknownTopicOrPart)
            {
                log.LogWarning(ex, "Falha no consume: {Error}", ex.Error);
                await Task.Delay(3000, stoppingToken);
            }
            catch (Exception ex)
            {
                log.LogError(ex, "Erro no NotificationConsumer. Retentando em 3s...");
                await Task.Delay(3000, stoppingToken);
            }
        }
    }

    private static string TryExtractRideId(string json)
    {
        try
        {
            using var doc = JsonDocument.Parse(json);
            return doc.RootElement.TryGetProperty("rideId", out var p)
                ? p.GetString() ?? "unknown"
                : "unknown";
        }
        catch { return "unknown"; }
    }
}