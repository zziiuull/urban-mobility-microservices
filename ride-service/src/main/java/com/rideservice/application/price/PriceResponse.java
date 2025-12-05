package com.rideservice.application.price;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PriceResponse(
        UUID rideId,
        BigDecimal amount,
        String currency,
        BigDecimal surgeFactor,
        boolean cacheHit,
        Instant calculatedAtUtc
) {
}
