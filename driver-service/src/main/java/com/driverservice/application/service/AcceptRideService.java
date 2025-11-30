package com.driverservice.application.service;

import com.driverservice.application.service.events.DriverAcceptedRideEvent;
import com.driverservice.application.service.params.RideAcceptanceParams;
import com.driverservice.domain.entity.Driver;
import com.driverservice.infrastructure.repository.DriverMapper;
import com.driverservice.infrastructure.repository.DriverRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AcceptRideService {
    private final DriverRepository driverRepository;
    private final KafkaTemplate<String, DriverAcceptedRideEvent> kafkaTemplate;

    public AcceptRideService(DriverRepository driverRepository, KafkaTemplate<String, DriverAcceptedRideEvent> kafkaTemplate) {
        this.driverRepository = driverRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Optional<Driver> accept(RideAcceptanceParams params) {
        var driverEntity = driverRepository.findById(params.driverId());
        if (driverEntity.isEmpty()) return Optional.empty();

        DriverAcceptedRideEvent event = new DriverAcceptedRideEvent(params.rideId(), params.driverId());
        kafkaTemplate.send("driver-accepted-ride-topic", event);

        var driver = driverEntity.get();
        return Optional.of(DriverMapper.toDomain(driver));
    }
}
