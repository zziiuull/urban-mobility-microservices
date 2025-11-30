package com.paymentservice.application.service.params;

import com.paymentservice.domain.vo.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

public record PayParams(UUID rideId, UUID passengerId, BigDecimal amountPaid, BigDecimal totalToPay, PaymentMethod method) {
}
