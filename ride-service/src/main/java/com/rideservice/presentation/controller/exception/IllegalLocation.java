package com.rideservice.presentation.controller.exception;

public class IllegalLocation extends RuntimeException {
    public IllegalLocation(String message) {
        super(message);
    }
}
