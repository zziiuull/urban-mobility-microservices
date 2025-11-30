package com.driverservice.infrastructure.repository;

import java.util.UUID;

public record DriverEntity(UUID id, String name, double rating, boolean available, double latitude, double longitude) {
}
