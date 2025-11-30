package com.rideservice.application.service.params;

import com.rideservice.domain.vo.location.Location;

import java.util.UUID;

public record CreateRideParams(
    UUID passengerId,
    Location origin,
    Location destination
) {
}
