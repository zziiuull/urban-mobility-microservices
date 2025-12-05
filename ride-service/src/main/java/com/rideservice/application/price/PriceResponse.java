package com.rideservice.application.price;

import java.time.Instant;
import java.util.UUID;

public record PriceResponse(
        UUID rideId,
        double amount,
        String currency,
        double surgeFactor,
        boolean cacheHit,
        Instant calculatedAtUtc
) {
}
