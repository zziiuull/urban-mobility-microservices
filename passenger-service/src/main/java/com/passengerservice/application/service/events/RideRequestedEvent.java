package com.passengerservice.application.service.events;

import com.rideservice.domain.vo.location.Location;

import java.math.BigDecimal;
import java.util.UUID;

public record RideRequestedEvent(
     UUID rideId,
     UUID passengerId,
     Location origin,
     Location destination,
     BigDecimal amount){
}