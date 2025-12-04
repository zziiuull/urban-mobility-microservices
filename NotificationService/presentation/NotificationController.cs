using Microsoft.AspNetCore.Mvc;
using NotificationService.WebSockets;

namespace NotificationService.Presentation;

[ApiController]
[Route("/v1/[controller]")]
public class NotificationController(NotificationHub hub) : ControllerBase
{
    [HttpGet("health")]
    public IActionResult Health() => Ok(new { status = "ok", service = "notification" });

    // POST /api/v1/notification/dev/push/{rideId}
    [HttpPost("dev/push/{rideId}")]
    public async Task<IActionResult> DevPush([FromRoute] Guid rideId, [FromBody] object payload)
    {
        await hub.BroadcastAsync(rideId, System.Text.Json.JsonSerializer.Serialize(payload));
        return Accepted();
    }
}