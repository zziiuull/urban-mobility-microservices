namespace PriceService.Models;

public record PriceResult(decimal Amount, decimal SurgeFactor, bool CacheHit);