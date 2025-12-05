package com.rideservice.application.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentSuccessEvent(UUID rideId, LocalDateTime createdAt, BigDecimal change) {
}
