package com.rideservice.application.service.dto.ride.request;

import com.rideservice.domain.vo.location.Location;

import java.util.UUID;

public record RideRequestDTO(UUID passengerId,
         Location origin,
         Location destination) {
}
