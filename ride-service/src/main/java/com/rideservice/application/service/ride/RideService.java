package com.rideservice.application.service.ride;

import com.rideservice.application.service.events.DriverAcceptedRideEvent;
import com.rideservice.application.service.events.RideCancelledEvent;
import com.rideservice.domain.enums.RideStatus;
import com.rideservice.domain.model.entity.driver.Driver;
import com.rideservice.domain.model.entity.ride.Ride;
import com.rideservice.infrastructure.repository.ride.RideRepository;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@KafkaListener(topics = "driver-accepted-ride-topic", groupId = "ride-service")
public class RideService {
    private final RideRepository rideRepository;
    private final KafkaTemplate<String, RideCancelledEvent> kafkaTemplate;

    public RideService(RideRepository rideRepository, KafkaTemplate<String, RideCancelledEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.rideRepository = rideRepository;
    }

    public Ride createRide(Ride ride) {
        return rideRepository.save(ride);
    }

    public Optional<Ride> findRide(UUID id) {
        return rideRepository.findById(id);
    }

    @KafkaHandler
    public void handleDriverAccpetedRide(DriverAcceptedRideEvent event){
        Ride ride = rideRepository.findById(event.rideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        Driver driver = new Driver();
        // TODO: use driver id

        ride.setDriver(driver);
        ride.setRideStatus(RideStatus.DRIVER_ASSIGNED);

        rideRepository.save(ride);
    }

    public void cancelRide(UUID rideId, UUID passengerId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));


        if (ride.getRideStatus() == RideStatus.COMPLETED) {
            throw new RuntimeException("Ride already completed, cannot cancel");
        }

        ride.setRideStatus(RideStatus.CANCELLED);
        rideRepository.save(ride);

        RideCancelledEvent event = new RideCancelledEvent(
                rideId,
                passengerId
        );

        kafkaTemplate.send("ride-cancelled-topic", event);

        // TODO: add listeners to payment service
    }
}
