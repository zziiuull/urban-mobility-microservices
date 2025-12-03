using System.Globalization;
using StackExchange.Redis;

namespace PriceService.Services;

public class CacheService
{
    private readonly IConnectionMultiplexer _mux;
    private readonly TimeSpan _ttl;

    public CacheService(IConnectionMultiplexer mux, IConfiguration cfg)
    {
        _mux = mux;
        var ttlSec = int.TryParse(cfg["Redis:DefaultTtlSeconds"], out var s) ? s : 90;
        _ttl = TimeSpan.FromSeconds(ttlSec);
    }

    public async Task<decimal?> GetDecimalAsync(string key)
    {
        var db = _mux.GetDatabase();
        var val = await db.StringGetAsync(key);
        if (val.IsNullOrEmpty) return null;
        return decimal.Parse(val!);
    }

    public async Task SetDecimalAsync(string key, decimal value)
    {
        var db = _mux.GetDatabase();
        await db.StringSetAsync(key, value.ToString(CultureInfo.InvariantCulture), _ttl);
    }
}