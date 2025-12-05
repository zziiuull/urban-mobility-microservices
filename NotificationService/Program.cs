using Confluent.Kafka;
using Contracts;
using Microsoft.AspNetCore.WebSockets;
using NotificationService.WebSockets;
using NotificationService.Consumers;
using NotificationService.Infrastructure;

var builder = WebApplication.CreateBuilder(args);
var cfg = builder.Configuration;

// Kafka
builder.Services.AddSingleton(new ConsumerConfig {
    BootstrapServers = cfg["Kafka:BootstrapServers"] ?? "localhost:9092",
    GroupId = cfg["Kafka:GroupId"] ?? "notification-service",
    AutoOffsetReset = AutoOffsetReset.Earliest
});

builder.Services.AddSingleton(new AdminClientConfig {
    BootstrapServers = builder.Configuration["Kafka:BootstrapServers"] ?? "localhost:9092"
});
var neededTopics = new[] { Topics.PriceCalculated, Topics.RideStatusChanged, Topics.PaymentSucceeded, Topics.PaymentFailed, Topics.DriverAssigned, Topics.FindDriver, Topics.DriverFound };
builder.Services.AddSingleton<IHostedService>(sp =>
    new TopicBootstrapper(sp.GetRequiredService<AdminClientConfig>(), neededTopics));

builder.Services.Configure<HostOptions>(o =>
    o.BackgroundServiceExceptionBehavior = BackgroundServiceExceptionBehavior.Ignore);

builder.Services.AddSingleton<NotificationHub>();
builder.Services.AddHostedService<NotificationConsumer>();

builder.Services.AddWebSockets(_ => {});
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddControllers();

var app = builder.Build();

app.UseWebSockets();

// ws://host/ws/{rideId}
app.Map("/ws/{rideId}", async (HttpContext ctx, string rideId, NotificationHub hub) =>
{
    if (!ctx.WebSockets.IsWebSocketRequest) { ctx.Response.StatusCode = 400; return; }
    using var ws = await ctx.WebSockets.AcceptWebSocketAsync();
    await hub.RegisterAsync(rideId, ws);

    var buffer = new byte[2];
    while (ws.State == System.Net.WebSockets.WebSocketState.Open)
    {
        var result = await ws.ReceiveAsync(buffer, CancellationToken.None);
        if (result.MessageType == System.Net.WebSockets.WebSocketMessageType.Close) break;
    }
    await hub.UnregisterAsync(rideId, ws);
});

if (app.Environment.IsDevelopment())
{
    app.UseSwagger(); app.UseSwaggerUI();
}

app.MapControllers();
app.Run();
