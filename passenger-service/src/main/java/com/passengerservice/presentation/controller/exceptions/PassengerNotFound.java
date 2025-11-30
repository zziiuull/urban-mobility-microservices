package com.passengerservice.presentation.controller.exceptions;

public class PassengerNotFound extends RuntimeException {
    public PassengerNotFound(String message) {
        super(message);
    }
}
