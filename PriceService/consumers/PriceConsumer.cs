using System.Text.Json;
using Confluent.Kafka;
using Contracts;
using PriceService.Producers;
using PriceService.Services;

namespace PriceService.Consumers;

public class PriceConsumer(ConsumerConfig cfg, PriceCalculator calc, CacheService cache, PriceProducer producer)
    : BackgroundService
{
    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        await Task.Delay(2000, stoppingToken);
        
        var jsonOpts = new JsonSerializerOptions { PropertyNameCaseInsensitive = true };

        while (!stoppingToken.IsCancellationRequested)
        {
            try
            {
                using var consumer = new ConsumerBuilder<string, string>(cfg).Build();
                consumer.Subscribe(Topics.RideRequested);

                Console.WriteLine("[PriceConsumer] Kafka consumer inicializado.");

                while (!stoppingToken.IsCancellationRequested)
                {
                    var cr = consumer.Consume(stoppingToken);
                    if (cr?.Message == null) continue;

                    Console.WriteLine($"[PriceConsumer] Evento recebido: {cr.Topic} - {cr.Message.Value}");

                    var evt = JsonSerializer.Deserialize<RideRequested>(cr.Message.Value, jsonOpts)!;

                    var (amount, surge, cacheHit) = await calc.CalculateAsync(
                        evt.Origin.Lat, evt.Origin.Lng,
                        evt.Destination.Lat, evt.Destination.Lng,
                        cache);

                    var outEvt = new PriceCalculated(
                        evt.RideId, amount, "BRL", surge, cacheHit, DateTime.UtcNow);
                    
                    await producer.PublishAsync(outEvt, outEvt.RideId, stoppingToken);
                }
            }
            catch (ConsumeException ex) when (ex.Error.Code == ErrorCode.UnknownTopicOrPart)
            {
                Console.WriteLine($"[PriceConsumer] tópico inexistente: {ex.Error}. Retry em 3s…");
                await Task.Delay(3000, stoppingToken);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"[PriceConsumer] erro: {ex.Message}. Retry em 3s…");
                await Task.Delay(3000, stoppingToken);
            }
        }
    }
}
