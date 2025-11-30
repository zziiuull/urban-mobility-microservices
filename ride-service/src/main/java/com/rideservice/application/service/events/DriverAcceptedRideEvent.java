package com.rideservice.application.service.events;

import java.util.UUID;

public record DriverAcceptedRideEvent(UUID rideId, UUID driverId) {
}
