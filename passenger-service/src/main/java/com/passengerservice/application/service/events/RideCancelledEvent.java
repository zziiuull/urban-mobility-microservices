package com.passengerservice.application.service.events;

import java.util.UUID;

public record RideCancelledEvent(
        UUID rideId,
        UUID passengerId
) {}