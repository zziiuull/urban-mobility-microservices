package com.driverservice.application.service;

import com.driverservice.application.service.events.DriverFoundEvent;
import com.driverservice.application.service.events.FindDriverEvent;
import com.driverservice.application.service.params.CreateDriverParams;
import com.driverservice.domain.entity.Driver;
import com.driverservice.domain.vo.Location;
import com.driverservice.infrastructure.repository.DriverMapper;
import com.driverservice.infrastructure.repository.DriverRepository;
import com.driverservice.presentation.controller.exceptions.DriverNotFound;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DriverService(DriverRepository driverRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.driverRepository = driverRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public UUID registerDriver(CreateDriverParams params) {
        Driver driver = new Driver(params.name(), new Location(params.latitude(), params.longitude()));

        var newDriver = DriverMapper.toEntity(driver);
        driverRepository.save(newDriver);

        return driver.getId();
    }

    public Driver findDriver(UUID id) {
        var driver = driverRepository.findById(id);

        return driver.map(DriverMapper::toDomain).orElseThrow(() -> new DriverNotFound("Driver not found"));
    }

    public Driver findNearestDriver(UUID rideId) {
        var driver = driverRepository.findFirstByAvailable(true);

        return driver.map(DriverMapper::toDomain).orElseThrow(() -> new DriverNotFound("Driver not found"));
    }

    // @KafkaListener(topics = "find-driver", containerFactory = "kafkaListenerContainerFactory")
    // public void onFindDriver(FindDriverEvent event) {
    //     UUID driverId = findNearestDriver(event.rideId()).getId();

    //     kafkaTemplate.send("driver-found", new DriverFoundEvent(
    //             event.rideId(), driverId
    //     ));
    // }
}
