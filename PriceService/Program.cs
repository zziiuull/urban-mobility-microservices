using Confluent.Kafka;
using StackExchange.Redis;
//using PriceService.Consumers;
using PriceService.Infrastructure;
//using PriceService.Producers;
using PriceService.Services;

var builder = WebApplication.CreateBuilder(args);

// Config
var cfg = builder.Configuration;

var redisConn = cfg["Redis:Connection"] ?? "localhost:6379,abortConnect=false";

// Redis
builder.Services.AddSingleton<IConnectionMultiplexer>(_ =>
{
    var opt = ConfigurationOptions.Parse(redisConn);
    opt.AbortOnConnectFail = false;
    opt.ConnectRetry = 5;
    opt.ReconnectRetryPolicy = new ExponentialRetry(5000);
    return ConnectionMultiplexer.Connect(opt);
});

// Kafka
builder.Services.AddSingleton(new ProducerConfig {
    BootstrapServers = cfg["Kafka:BootstrapServers"] ?? "localhost:9092",
});
builder.Services.AddSingleton(new ConsumerConfig {
    BootstrapServers = cfg["Kafka:BootstrapServers"] ?? "localhost:9092",
    GroupId = cfg["Kafka:GroupId"] ?? "price-service",
    AutoOffsetReset = AutoOffsetReset.Earliest
});

builder.Services.AddSingleton(new AdminClientConfig {
    BootstrapServers = builder.Configuration["Kafka:BootstrapServers"] ?? "localhost:9092"
});

// var neededTopicsPrice = new[] { "price.calculated" };
// builder.Services.AddSingleton<IHostedService>(sp =>
//     new TopicBootstrapper(sp.GetRequiredService<AdminClientConfig>(), neededTopicsPrice));

builder.Services.AddSingleton<CacheService>();
builder.Services.AddSingleton<PriceCalculator>();
//builder.Services.AddSingleton<PriceProducer>();

// Background Consumer
// builder.Services.AddHostedService<PriceConsumer>();

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddControllers();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger(); app.UseSwaggerUI();
}

app.MapControllers();
app.Run();
