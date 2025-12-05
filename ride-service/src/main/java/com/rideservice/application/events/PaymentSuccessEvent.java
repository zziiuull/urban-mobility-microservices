package com.rideservice.application.events;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentSuccessEvent(UUID rideId, BigDecimal change) {
}
