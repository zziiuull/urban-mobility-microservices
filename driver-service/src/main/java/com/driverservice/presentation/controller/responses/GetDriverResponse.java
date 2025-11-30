package com.driverservice.presentation.controller.responses;

import com.driverservice.domain.vo.Location;

import java.util.UUID;

public record GetDriverResponse(UUID id, String name, double rating, boolean available, Location currentLocation) {
}
