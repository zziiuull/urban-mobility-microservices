package com.rideservice.application.events;

import java.util.UUID;

public record DriverAssignedEvent(UUID rideId,
                                  UUID driverId) {
}
