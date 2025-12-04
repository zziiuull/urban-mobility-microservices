using Microsoft.AspNetCore.Mvc;
using PriceService.Services;

namespace PriceService.Presentation;

[ApiController]
[Route("v1/[controller]")]
public class PriceController(PriceCalculator calc, CacheService cache) : ControllerBase
{

    [HttpGet("health")]
    public IActionResult Health() => Ok(new { status = "ok", service = "price" });

    [HttpGet]
    public async Task<IActionResult> Get([FromQuery] double oLat, [FromQuery] double oLng,
        [FromQuery] double dLat, [FromQuery] double dLng)
    {
        var (amount, surge, cacheHit) = await calc.CalculateAsync(oLat, oLng, dLat, dLng, cache);
        return Ok(new { amount, currency = "BRL", surgeFactor = surge, cacheHit });
    }
}
