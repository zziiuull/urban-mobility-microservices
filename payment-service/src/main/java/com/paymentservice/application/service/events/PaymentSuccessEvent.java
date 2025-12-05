package com.paymentservice.application.service.events;

import java.util.UUID;

public record PaymentSuccessEvent(UUID  rideId, double change) {
}
