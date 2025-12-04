using System.Collections.Concurrent;
using System.Net.WebSockets;

namespace NotificationService.WebSockets;

public class NotificationHub
{
    private readonly ConcurrentDictionary<string, ConcurrentBag<WebSocket>> _map = new();

    public Task RegisterAsync(string rideId, WebSocket ws)
    {
        var bag = _map.GetOrAdd(rideId, _ => new());
        bag.Add(ws);
        return Task.CompletedTask;
    }

    public Task UnregisterAsync(string rideId, WebSocket ws)
    {
        if (_map.TryGetValue(rideId, out var bag))
            bag.TryTake(out _);
        return Task.CompletedTask;
    }

    public async Task BroadcastAsync(Guid rideId, string message)
    {
        if (!_map.TryGetValue(rideId.ToString(), out var bag)) return;
        var seg = new ArraySegment<byte>(System.Text.Encoding.UTF8.GetBytes(message));
        foreach (var ws in bag.ToArray())
        {
            if (ws.State == WebSocketState.Open)
                await ws.SendAsync(seg, WebSocketMessageType.Text, true, CancellationToken.None);
        }
    }
}