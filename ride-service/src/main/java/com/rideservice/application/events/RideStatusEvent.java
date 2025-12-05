package com.rideservice.application.events;

import com.rideservice.domain.enums.RideStatus;

import java.util.UUID;

public record RideStatusEvent(UUID rideId, RideStatus status) {
}
