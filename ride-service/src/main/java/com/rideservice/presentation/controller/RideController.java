package com.rideservice.presentation.controller;

import com.rideservice.application.service.dto.ride.request.RideRequestDTO;
import com.rideservice.application.service.events.RideRequestedEvent;
import com.rideservice.application.service.ride.RideService;
import com.rideservice.domain.enums.RideStatus;
import com.rideservice.domain.model.entity.passenger.Passenger;
import com.rideservice.domain.model.entity.ride.Ride;
import com.rideservice.domain.vo.price.Price;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/ride")
public class RideController {
    private final RideService rideService;
    private final KafkaTemplate<String, RideRequestedEvent> kafkaTemplate;

    public RideController(KafkaTemplate<String, RideRequestedEvent> kafkaTemplate, RideService rideService) {
        this.rideService = rideService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/request")
    public Ride requestRide(@RequestBody RideRequestDTO dto) {
        Ride ride = new Ride();

        Passenger passenger = new Passenger();
        ride.setPassenger(passenger);

        ride.setOrigin(dto.origin());
        ride.setDestination(dto.destination());

        Price price = new Price(BigDecimal.valueOf(20.0), dto.origin(), dto.destination());
        ride.setPrice(price);

        ride.setRideStatus(RideStatus.REQUESTED);

        rideService.createRide(ride);

        RideRequestedEvent event = new RideRequestedEvent(
                ride.getId(),
                dto.passengerId(),
                dto.origin(),
                dto.destination(),
                price.amount());

        kafkaTemplate.send("ride-requested-topic", event);

        return ride;
    }

    @PostMapping("/{rideId}/cancel")
    public ResponseEntity<Void> cancelRide(@PathVariable UUID rideId,
                                           @RequestParam UUID passengerId) {
        rideService.cancelRide(rideId, passengerId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{id}")
    public Ride getRide(@PathVariable UUID id) {
        return rideService.findRide(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }
}
