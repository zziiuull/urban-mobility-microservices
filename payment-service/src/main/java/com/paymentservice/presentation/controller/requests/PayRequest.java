package com.paymentservice.presentation.controller.requests;

import com.paymentservice.domain.vo.PaymentMethod;

import java.util.UUID;

public record PayRequest(UUID passengerId, double amountPaid, double totalToPay, PaymentMethod method) {
}
