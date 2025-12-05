package com.paymentservice.application.service.events;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentSuccessEvent(UUID id, BigDecimal change) {
}
