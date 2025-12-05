package com.rideservice.application.service;

import com.rideservice.application.events.DriverAssignedEvent;
import com.rideservice.application.events.FindDriverEvent;
import com.rideservice.application.events.PaymentSuccessEvent;
import com.rideservice.application.price.PriceResponse;
import com.rideservice.application.service.events.DriverAcceptedRideEvent;
import com.rideservice.application.service.events.RideCancelledEvent;
import com.rideservice.application.service.events.RideRequestedEvent;
import com.rideservice.application.service.params.CreateRideParams;
import com.rideservice.domain.model.entity.driver.Driver;
import com.rideservice.domain.model.entity.passenger.Passenger;
import com.rideservice.domain.model.entity.ride.Ride;
import com.rideservice.domain.vo.price.Price;
import com.rideservice.infrastructure.repository.ride.RideEntity;
import com.rideservice.infrastructure.repository.ride.RideMapper;
import com.rideservice.infrastructure.repository.ride.RideRepository;
import com.rideservice.presentation.controller.exception.RideNotFoundException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
public class RideService {
    private final RideRepository rideRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public RideService(RideRepository rideRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.rideRepository = rideRepository;
    }

    public Ride createRide(CreateRideParams params) {
        WebClient client = WebClient.create("http://price-service:8080");
        PriceResponse response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/Price")
                        .queryParam("oLat", params.origin().latitude())
                        .queryParam("oLng", params.origin().longitude())
                        .queryParam("dLat", params.destination().latitude())
                        .queryParam("dLng", params.destination().longitude())
                        .build()
                )
                .retrieve()
                .bodyToMono(PriceResponse.class)
                .block();

        if (response == null) return null;

        Ride ride = new Ride(
                params.passengerId(),
                new Price(
                        response.amount(),
                        params.origin(),
                        params.destination()),
                new Passenger(params.passengerId()),
                params.origin(),
                params.destination());

        RideEntity entity = RideMapper.toEntity(ride);
        RideEntity saved = rideRepository.save(entity);

        kafkaTemplate.send(
                "ride-requested",
                new RideRequestedEvent(
                        ride.getId(),
                        params.passengerId(),
                        params.origin(),
                        params.destination(),
                        ride.getPrice().amount()
                )
        );

        return RideMapper.toDomain(saved);
    }

    public Ride findRide(UUID id) {
        return rideRepository.findById(id)
                .map(RideMapper::toDomain)
                .orElseThrow(() -> new RideNotFoundException("Ride not found"));
    }

    @KafkaListener(topics = "payment-success", containerFactory = "kafkaListenerContainerFactory")
    public void handlePaymentSuccess(PaymentSuccessEvent event) {
        kafkaTemplate.send("find-driver", new FindDriverEvent(event.rideId()));
    }

    @KafkaListener(
            topics = "driver-found",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleDriverAcceptedRide(DriverAcceptedRideEvent event) {
        RideEntity entity = rideRepository.findById(event.rideId())
                .orElseThrow(() -> new RideNotFoundException("Ride not found"));

        Ride ride = RideMapper.toDomain(entity);

        ride.assignDriver(new Driver(event.driverId()));

        rideRepository.save(RideMapper.toEntity(ride));

        kafkaTemplate.send("driver-assigned", new DriverAssignedEvent(event.rideId(), event.driverId()));
    }

    public void cancelRide(UUID rideId, UUID passengerId) {
        RideEntity entity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundException("Ride not found"));

        Ride ride = RideMapper.toDomain(entity);

        ride.cancel();

        rideRepository.save(RideMapper.toEntity(ride));

        kafkaTemplate.send("ride-cancelled-topic",
                new RideCancelledEvent(rideId, passengerId)
        );
    }
}
