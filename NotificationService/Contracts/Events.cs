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
    decimal Amount);

public record PriceCalculated(
    Guid RideId,
    decimal Amount,
    string Currency,
    decimal SurgeFactor,
    bool CacheHit);

public record RideStatusChanged(
    Guid RideId,
    string Status);

public record PaymentSucceeded(
    Guid RideId,
    decimal Change
);

public record PaymentFailed(
    Guid RideId, 
    string PassengerId
);

public record DriverAssigned(
    Guid RideId,
    Guid DriverId
);

public record FindDriver(
    Guid RideId
);

public record DriverFound(
    Guid RideId,
    Guid DriverId
);


public static class Topics
{
    public const string RideRequested    = "ride-requested";
    public const string PriceCalculated  = "price.calculated";
    public const string RideStatusChanged= "ride.status-changed";
    public const string PaymentSucceeded = "payment-success";
    public const string PaymentFailed    = "payment-failed";
    public const string DriverAssigned = "driver.assigned";
    public const string FindDriver = "find-driver";
    public const string DriverFound = "driver-found";
}
