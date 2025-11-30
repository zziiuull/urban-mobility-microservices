package com.passengerservice.presentation.controller;

import com.passengerservice.application.service.passenger.PassengerService;
import com.passengerservice.domain.model.entity.passenger.Passenger;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public Passenger createPassenger(@RequestBody Passenger passenger) {
        return passengerService.createPassenger(passenger);
    }

    @GetMapping("/{id}")
    public Passenger getPassenger(@PathVariable UUID id) {
        return passengerService.findPassenger(id).orElseThrow(() -> new RuntimeException("Passenger not found"));
    }
}
