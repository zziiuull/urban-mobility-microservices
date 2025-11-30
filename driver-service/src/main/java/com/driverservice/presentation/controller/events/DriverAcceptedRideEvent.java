package com.driverservice.presentation.controller.events;

import java.util.UUID;

public record DriverAcceptedRideEvent(UUID rideId, UUID driverId) {
}
