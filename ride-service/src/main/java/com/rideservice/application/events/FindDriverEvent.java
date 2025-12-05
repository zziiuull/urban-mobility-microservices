package com.rideservice.application.events;

import java.util.UUID;

public record FindDriverEvent(UUID rideId) {
}
