package com.driverservice.application.service.params;

import java.util.UUID;

public record RideAcceptanceParams(UUID rideId, UUID driverId) {
}
