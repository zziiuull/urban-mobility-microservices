package com.rideservice.application.service.events;

import com.rideservice.domain.vo.location.Location;

import java.util.UUID;

public record RideRequestedEvent(
        UUID  rideId,
        UUID  passengerId,
     Location origin,
     Location destination,
     double amount){
}
