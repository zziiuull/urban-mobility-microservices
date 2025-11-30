package com.driverservice.application.service;

import com.driverservice.application.service.events.DriverAcceptedRideEvent;
import com.driverservice.application.service.params.RideAcceptanceParams;
import com.driverservice.domain.entity.Driver;
import com.driverservice.infrastructure.repository.DriverMapper;
import com.driverservice.infrastructure.repository.DriverRepository;
import com.driverservice.presentation.controller.exceptions.DriverNotFound;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AcceptRideService {
    private final DriverRepository driverRepository;
    private final KafkaTemplate<String, DriverAcceptedRideEvent> kafkaTemplate;

    public AcceptRideService(DriverRepository driverRepository, KafkaTemplate<String, DriverAcceptedRideEvent> kafkaTemplate) {
        this.driverRepository = driverRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Driver accept(RideAcceptanceParams params) {
        var driverEntity = driverRepository.findById(params.driverId());
        var driver = driverEntity.map(DriverMapper::toDomain).orElseThrow(() -> new DriverNotFound("Driver not found"));

        DriverAcceptedRideEvent event = new DriverAcceptedRideEvent(params.rideId(), params.driverId());
        kafkaTemplate.send("driver-accepted-ride-topic", event);

        return driver;
    }
}
