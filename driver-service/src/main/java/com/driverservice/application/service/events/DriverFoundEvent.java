package com.driverservice.application.service.events;

import java.util.UUID;

public record DriverFoundEvent(UUID rideId, UUID driverId) {
}
