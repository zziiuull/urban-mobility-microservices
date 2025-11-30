package com.paymentservice.application.service.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentSuccessEvent(UUID id, LocalDateTime createdAt, BigDecimal change) {
}
