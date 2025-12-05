package com.paymentservice.application.service.params;

import com.paymentservice.domain.vo.PaymentMethod;

import java.util.UUID;

public record PayParams(UUID rideId, UUID passengerId, double amountPaid, double totalToPay, PaymentMethod method) {
}
