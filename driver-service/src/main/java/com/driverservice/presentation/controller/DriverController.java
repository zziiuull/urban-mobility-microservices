package com.driverservice.presentation.controller;

import com.driverservice.application.service.AcceptRideService;
import com.driverservice.application.service.DriverLocationService;
import com.driverservice.application.service.DriverService;
import com.driverservice.application.service.params.CreateDriverParams;
import com.driverservice.application.service.params.RideAcceptanceParams;
import com.driverservice.application.service.params.UpdateDriverLocationParams;
import com.driverservice.domain.entity.Driver;
import com.driverservice.domain.vo.Location;
import com.driverservice.presentation.controller.requests.CreateDriverRequest;
import com.driverservice.presentation.controller.requests.UpdateDriverLocationRequest;
import com.driverservice.presentation.controller.responses.GetDriverResponse;
import com.driverservice.presentation.controller.responses.GetLocationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping
public class DriverController {
    private final DriverService driverService;
    private final DriverLocationService driverLocationService;
    private final AcceptRideService acceptRideService;

    public DriverController(DriverService driverService, DriverLocationService driverLocationService, AcceptRideService acceptRideService) {
        this.driverService = driverService;
        this.driverLocationService = driverLocationService;
        this.acceptRideService = acceptRideService;
    }

    @PostMapping
    public ResponseEntity<UUID> createDriver(@RequestBody CreateDriverRequest request) {
        var params = new CreateDriverParams(request.name(), request.latitude(), request.longitude());
        UUID id = driverService.registerDriver(params);

        URI location = URI.create("/api/driver/" + id);
        return ResponseEntity.created(location).body(id);
    }

    // TODO: response
    @GetMapping("/{id}")
    public ResponseEntity<GetDriverResponse> getDriver(@PathVariable UUID id) {
        Driver driver = driverService.findDriver(id);
        var response = new GetDriverResponse(driver.getId(), driver.getName(), driver.getRating(), driver.isAvailable(), driver.getCurrentLocation());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/location")
    public ResponseEntity<Void> updateLocation(@PathVariable UUID id, @RequestBody UpdateDriverLocationRequest request) {
        var params = new UpdateDriverLocationParams(id, request.latitude(), request.longitude());
        driverLocationService.updateLocation(params);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<GetLocationResponse> getLocation(@PathVariable UUID id) {
        var location = driverLocationService.getLocation(id);

        var response = new GetLocationResponse(location.latitude(), location.longitude());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{driverId}/accept")
    public ResponseEntity<RideAcceptanceParams> acceptRide(@PathVariable UUID driverId, @RequestParam UUID rideId) {
        RideAcceptanceParams params = new RideAcceptanceParams(rideId, driverId);
        acceptRideService.accept(params);

        return ResponseEntity.ok().build();
    }
}
