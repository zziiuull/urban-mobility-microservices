package com.passengerservice.infrastructure.repository;

import com.passengerservice.domain.entity.Passenger;

public class PassengerMapper {
    public static Passenger toDomain(PassengerEntity entity) {
        return new Passenger(entity.id(), entity.name());
    }

    public static PassengerEntity toEntity(Passenger passenger) {
        return new PassengerEntity(passenger.getId(), passenger.getName());
    }
}
