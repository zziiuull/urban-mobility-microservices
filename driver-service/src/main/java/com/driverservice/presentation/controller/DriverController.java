package com.driverservice.presentation.controller;

import com.driverservice.application.service.DriverLocationService;
import com.driverservice.application.service.DriverService;
import com.driverservice.application.service.params.CreateDriverParams;
import com.driverservice.application.service.params.UpdateDriverLocationParams;
import com.driverservice.domain.entity.Driver;
import com.driverservice.domain.vo.Location;
import com.driverservice.presentation.controller.events.DriverAcceptedRideEvent;
import com.driverservice.presentation.controller.requests.CreateDriverRequest;
import com.driverservice.presentation.controller.requests.UpdateDriverLocationRequest;
import com.driverservice.presentation.controller.responses.GetLocationResponse;
import com.driverservice.presentation.controller.responses.RideAcceptanceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<UUID> createDriver(@RequestBody CreateDriverRequest request) {
        var params = new CreateDriverParams(request.name(), request.latitude(), request.longitude());
        UUID id = driverService.registerDriver(params);

        URI location = URI.create("/api/driver/" + id);
        return ResponseEntity.created(location).body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriver(@PathVariable UUID id) {
        return driverService.findDriver(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/location")
    public ResponseEntity<Void> updateLocation(@PathVariable UUID id, @RequestBody UpdateDriverLocationRequest request) {
        var params = new UpdateDriverLocationParams(id, request.latitude(), request.longitude());
        var driver = driverLocationService.updateLocation(params);

        if (driver.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<GetLocationResponse> getLocation(@PathVariable UUID id) {
        var driverLocation = driverLocationService.getLocation(id);

        if (driverLocation.isEmpty()) return ResponseEntity.notFound().build();

        Location location = driverLocation.get();
        var response = new GetLocationResponse(location.latitude(), location.longitude());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{driverId}/accept")
    public ResponseEntity<RideAcceptanceResponse> acceptRide(@PathVariable UUID driverId, @RequestParam UUID rideId) {
        var optionalDriver = driverService.findDriver(driverId);
        if (optionalDriver.isEmpty()) return ResponseEntity.notFound().build();

        Driver driver = optionalDriver.get();

        DriverAcceptedRideEvent event = new DriverAcceptedRideEvent(rideId, driver.getId());
        kafkaTemplate.send("driver-accepted-ride-topic", event);

        RideAcceptanceResponse response = new RideAcceptanceResponse(rideId, driver.getId());
        return ResponseEntity.ok(response);
    }
}
