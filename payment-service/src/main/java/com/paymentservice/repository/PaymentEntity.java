package com.paymentservice.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentEntity(
        UUID id,
        UUID rideId,
        UUID passengerId,
        BigDecimal amountPaid,
        BigDecimal totalAmount,
        BigDecimal change,
        String status,
        String method,
        LocalDateTime createdAt
) {}
