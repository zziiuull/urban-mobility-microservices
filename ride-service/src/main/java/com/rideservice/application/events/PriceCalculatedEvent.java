package com.rideservice.application.events;

import java.util.UUID;

public record PriceCalculatedEvent(
        UUID rideId,
        double amount,
        String currency,
        double surgeFactor,
        boolean cacheHit
) {}
