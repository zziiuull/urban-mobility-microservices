package com.driverservice.application.service.dto.driver;

import java.util.UUID;

public record RideAcceptanceResponse(
        UUID rideId,
        UUID driverId
) {}
