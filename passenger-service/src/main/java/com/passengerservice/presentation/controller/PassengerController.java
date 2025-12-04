package com.passengerservice.presentation.controller;

import com.passengerservice.application.service.PassengerService;
import com.passengerservice.application.service.params.CreatePassengerParam;
import com.passengerservice.domain.entity.Passenger;
import com.passengerservice.presentation.controller.requests.CreatePassengerRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public ResponseEntity<UUID> createPassenger(@RequestBody CreatePassengerRequest request) {
        var params = new CreatePassengerParam(request.name());
        UUID id = passengerService.createPassenger(params);

        URI location = URI.create("/api/passengers/" + id);
        return ResponseEntity.created(location).body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassenger(@PathVariable UUID id) {
        return ResponseEntity.ok(passengerService.findPassenger(id));
    }
}
