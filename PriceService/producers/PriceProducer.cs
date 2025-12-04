using System.Text.Json;
using Confluent.Kafka;
using Contracts;

namespace PriceService.Producers;

public class PriceProducer(ProducerConfig cfg)
{
    public async Task PublishAsync(PriceCalculated ev, Guid key, CancellationToken ct = default)
    {
        using var producer = new ProducerBuilder<string, string>(cfg).Build();
        var payload = JsonSerializer.Serialize(ev);
        await producer.ProduceAsync(Topics.PriceCalculated,
            new Message<string, string> { Key = key.ToString(), Value = payload }, ct);
    }
}