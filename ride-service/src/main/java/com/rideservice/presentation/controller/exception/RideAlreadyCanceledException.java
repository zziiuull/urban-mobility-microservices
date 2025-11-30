package com.rideservice.presentation.controller.exception;

public class RideAlreadyCanceledException extends RuntimeException {
    public RideAlreadyCanceledException(String message) {
        super(message);
    }
}