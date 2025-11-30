package com.rideservice.infrastructure.repository.ride;

import com.rideservice.domain.model.entity.ride.Ride;


public class RideMapper {
    public static Ride toDomain(RideEntity entity) {
        return new Ride(
                entity.getId(),
                entity.getPrice(),
                null,
                entity.getTravelTime(),
                entity.getOrigin(),
                null,
                entity.getDestination(),
                entity.getRideStatus()
        );
    }



    public static RideEntity toEntity(Ride ride) {

        return new RideEntity(
                ride.getId(),
                ride.getPassenger().getId(),
                ride.getDriver().getId(),
                ride.getOrigin(),
                ride.getDestination(),
                ride.getTravelTime(),
                ride.getPrice(),
                ride.getRideStatus()
        );
    }
}
