namespace Contracts;

public record Location(double Lat, double Lng);

public enum RideStatus {
    requested, driver_assigned, driver_arrived, started, completed, canceled
}

public record RideRequested(
    Guid RideId,
    string PassengerId,
    Location Origin,
    Location Destination,
    DateTime RequestedAtUtc);

public record PriceCalculated(
    Guid RideId,
    decimal Amount,
    string Currency,
    decimal SurgeFactor,
    bool CacheHit,
    DateTime CalculatedAtUtc);

public record RideStatusChanged(
    Guid RideId,
    string Status,
    DateTime ChangedAtUtc);

public record PaymentSucceeded(
    Guid RideId, 
    Guid PaymentId,
    decimal Amount,
    string Currency,
    DateTime AtUtc
);

public record PaymentFailed(
    Guid RideId, 
    string PassengerId,
    string Reason, 
    DateTime AtUtc
);

public record DriverAssigned(
    Guid RideId,
    Guid DriverId,
    int EtaMinutes,
    DateTime AssignedAtUtc
);

public static class Topics
{
    public const string RideRequested    = "ride.requested";
    public const string PriceCalculated  = "price.calculated";
    public const string RideStatusChanged= "ride.status-changed";
    public const string PaymentSucceeded = "payment.succeeded";
    public const string PaymentFailed    = "payment.failed";
    public const string DriverAssigned = "driver.assigned";
}