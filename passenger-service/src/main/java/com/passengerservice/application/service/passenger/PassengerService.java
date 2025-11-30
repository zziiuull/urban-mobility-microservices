package com.passengerservice.application.service.passenger;

import com.passengerservice.domain.model.entity.passenger.Passenger;
import com.passengerservice.infrastructure.repository.passenger.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Passenger createPassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    public Optional<Passenger> findPassenger(UUID id) {
        return passengerRepository.findById(id);
    }

}
