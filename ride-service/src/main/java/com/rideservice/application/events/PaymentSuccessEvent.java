package com.rideservice.application.events;

import java.util.UUID;

public record PaymentSuccessEvent(UUID rideId, double change) {
}
