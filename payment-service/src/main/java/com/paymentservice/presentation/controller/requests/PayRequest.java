package com.paymentservice.presentation.controller.requests;

import com.paymentservice.domain.vo.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

public record PayRequest(UUID passengerId, BigDecimal amountPaid, BigDecimal totalToPay, PaymentMethod method) {
}
