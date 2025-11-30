package com.rideservice.infrastructure.repository.ride;

import com.rideservice.domain.model.entity.ride.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {
}
