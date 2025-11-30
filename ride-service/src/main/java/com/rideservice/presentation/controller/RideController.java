package com.rideservice.presentation.controller;

import com.rideservice.application.service.RideService;
import com.rideservice.application.service.params.CreateRideParams;
import com.rideservice.domain.model.entity.ride.Ride;
import com.rideservice.presentation.controller.request.CreateRideRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ride")
public class RideController {
    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/request")
    public Ride requestRide(@RequestBody CreateRideRequest dto) {
        var params = new CreateRideParams(
                dto.passengerId(),
                dto.origin(),
                dto.destination()
        );

        return rideService.createRide(params);
    }

    @PostMapping("/{rideId}/cancel")
    public ResponseEntity<Void> cancelRide(@PathVariable UUID rideId,
                                           @RequestParam UUID passengerId) {
        rideService.cancelRide(rideId, passengerId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{id}")
    public Ride getRide(@PathVariable UUID id) {
        return rideService.findRide(id);
    }
}
