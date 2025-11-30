package com.rideservice.presentation.controller.exception;

public class RideNotFoundException extends RuntimeException {
    public RideNotFoundException(String message) {
        super(message);
    }
}
