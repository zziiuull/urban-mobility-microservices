package com.passengerservice.infrastructure.repository.passenger;

import com.passengerservice.domain.model.entity.passenger.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
}
