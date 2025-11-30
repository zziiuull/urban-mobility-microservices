package com.rideservice.presentation.controller.request;

import com.rideservice.domain.vo.location.Location;

import java.util.UUID;

public record CreateRideRequest(UUID passengerId,
                                Location origin,
                                Location destination) {
}
