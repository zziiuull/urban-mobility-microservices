package com.driverservice.presentation.controller.responses;

import java.util.UUID;

public record RideAcceptanceResponse(UUID rideId, UUID driverId) {
}
