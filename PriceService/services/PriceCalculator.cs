using Contracts;

namespace PriceService.Services;

public class PriceCalculator
{
    private const decimal PerKm = 2.75m;
    private const decimal BaseFee = 4.90m;

    public async Task<(decimal amount, decimal surge, bool cacheHit)> CalculateAsync(
        double oLat, double oLng, double dLat, double dLng, CacheService cache)
    {
        var key = $"price:{Region(oLat,oLng)}:{Region(dLat,dLng)}";

        var cached = await cache.GetDecimalAsync(key);
        if (cached is not null) return (cached.Value, 1.0m, true);

        var km = Haversine(oLat, oLng, dLat, dLng);
        var surge = EstimateSurge(new Location(oLat, oLng));
        var amount = Math.Round((decimal)km * PerKm * surge + BaseFee, 2);
        await cache.SetDecimalAsync(key, amount);
        return (amount, surge, false);
        string Region(double lat, double lng) => $"{Math.Round(lat,1)}:{Math.Round(lng,1)}";
    }

    private static decimal EstimateSurge(Location _origin) => 1.0m;
    
    private static double Haversine(double lat1, double lon1, double lat2, double lon2)
    {
        const double R = 6371;
        var dLat = ToRad(lat2 - lat1);
        var dLon = ToRad(lon2 - lon1);
        var a = Math.Sin(dLat/2)*Math.Sin(dLat/2) +
                Math.Cos(ToRad(lat1))*Math.Cos(ToRad(lat2))*
                Math.Sin(dLon/2)*Math.Sin(dLon/2);
        var c = 2 * Math.Atan2(Math.Sqrt(a), Math.Sqrt(1-a));
        return R * c;
    }
    private static double ToRad(double deg) => deg * Math.PI / 180.0;
}
