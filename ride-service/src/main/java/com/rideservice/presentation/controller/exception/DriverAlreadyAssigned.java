package com.rideservice.presentation.controller.exception;

public class DriverAlreadyAssigned extends RuntimeException {
    public DriverAlreadyAssigned(String message) {
        super(message);
    }
}
