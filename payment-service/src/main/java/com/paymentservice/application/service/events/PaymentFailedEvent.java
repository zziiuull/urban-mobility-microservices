package com.paymentservice.application.service.events;

import java.util.UUID;

public record PaymentFailedEvent(
        UUID  rideId,
        UUID  passengerId
) {}
