package com.driverservice.application.service.params;

import java.util.UUID;

public record UpdateDriverLocationParams(UUID id, double latitude, double longitude) {
}
