using Confluent.Kafka;
using Confluent.Kafka.Admin;

namespace PriceService.Infrastructure;

public class TopicBootstrapper(AdminClientConfig cfg, IEnumerable<string> topics) : IHostedService
{
    private readonly string[] _topics = topics.ToArray();

    public async Task StartAsync(CancellationToken ct)
    {
        using var admin = new AdminClientBuilder(cfg).Build();
        var specs = _topics.Select(t => new TopicSpecification { Name = t, NumPartitions = 3, ReplicationFactor = 1 });
        try { await admin.CreateTopicsAsync(specs); }
        catch (CreateTopicsException ex)
        {
            foreach (var r in ex.Results)
                if (r.Error.Code != ErrorCode.TopicAlreadyExists)
                    Console.WriteLine($"[TopicBootstrapper] {r.Topic}: {r.Error.Reason}");
        }
    }
    public Task StopAsync(CancellationToken ct) => Task.CompletedTask;
}