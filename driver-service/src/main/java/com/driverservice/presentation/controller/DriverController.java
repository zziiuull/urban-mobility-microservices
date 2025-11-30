package com.driverservice.presentation.controller;

import com.driverservice.application.service.driver.DriverLocationService;
import com.driverservice.application.service.driver.DriverService;
import com.driverservice.application.service.dto.driver.DriverAcceptedRideEvent;
import com.driverservice.application.service.dto.driver.RideAcceptanceResponse;
import com.driverservice.domain.model.entity.driver.Driver;
import com.driverservice.domain.vo.location.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/driver")
public class DriverController {
    private final DriverService driverService;
    private final DriverLocationService driverLocationService;
    private final KafkaTemplate<String, DriverAcceptedRideEvent> kafkaTemplate;

    public DriverController(DriverService driverService, DriverLocationService driverLocationService, KafkaTemplate<String, DriverAcceptedRideEvent> kafkaTemplate) {
        this.driverService = driverService;
        this.kafkaTemplate = kafkaTemplate;
        this.driverLocationService = driverLocationService;
    }

    @PostMapping
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        Driver created = driverService.registerDriver(driver);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/{id}")
    public Driver getDriver(@PathVariable UUID id) {
        return driverService.findDriver(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }


    @PutMapping("/{id}/location")
    public Driver updateLocation(@PathVariable UUID id, @RequestBody Location location) {
        Driver driver = driverService.findDriver(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setCurrentLocation(location);
        driverService.registerDriver(driver);

        driverLocationService.updateLocation(id, location);

        return driver;

    }

    @GetMapping("/{id}/location")
    public Location getLocation(@PathVariable UUID id) {
        Location location = driverLocationService.getLocation(id);
        if (location != null) return location;

        Driver driver = driverService.findDriver(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        return driver.getCurrentLocation();
    }

    @PostMapping("/{driverId}/accept")
    public ResponseEntity<RideAcceptanceResponse> acceptRide(@PathVariable UUID driverId, @RequestParam UUID rideId) {
        Driver driver = driverService.findDriver(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        DriverAcceptedRideEvent event =
                new DriverAcceptedRideEvent(rideId, driver.getId());
        kafkaTemplate.send("driver-accepted-ride-topic", event);

        RideAcceptanceResponse response =
                new RideAcceptanceResponse(rideId, driver.getId());

        return ResponseEntity.ok(response);
    }
}