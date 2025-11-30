package com.passengerservice.application.service;

import com.passengerservice.application.service.params.CreatePassengerParam;
import com.passengerservice.domain.model.entity.Passenger;
import com.passengerservice.infrastructure.repository.PassengerEntity;
import com.passengerservice.infrastructure.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public UUID createPassenger(CreatePassengerParam params) {
        Passenger passenger = new Passenger(params.name());
        var newPassenger = new PassengerEntity(passenger.getId(), passenger.getName());

        passengerRepository.save(newPassenger);

        return passenger.getId();
    }

    public Optional<Passenger> findPassenger(UUID id) {
        var passenger = passengerRepository.findById(id);

        return passenger.map(passengerEntity -> new Passenger(passengerEntity.id(), passengerEntity.name()));
    }
}
