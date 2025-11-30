package com.passengerservice.presentation.controller.exceptions;

public class PassengerNotFoundException extends RuntimeException {
    public PassengerNotFoundException(String message) {
        super(message);
    }
}
