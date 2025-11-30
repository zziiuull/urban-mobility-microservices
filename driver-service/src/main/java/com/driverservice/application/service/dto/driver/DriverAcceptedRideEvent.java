package com.driverservice.application.service.dto.driver;

import java.util.UUID;

public record DriverAcceptedRideEvent(UUID rideId, UUID driverId) {
}
