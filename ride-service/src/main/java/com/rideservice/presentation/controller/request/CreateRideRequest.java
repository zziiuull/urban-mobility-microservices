package com.rideservice.presentation.controller;

import com.rideservice.domain.vo.location.Location;

import java.util.UUID;

public record RideRequestDTO(UUID passengerId,
         Location origin,
         Location destination) {
}
