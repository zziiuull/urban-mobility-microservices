package com.driverservice.presentation.controller.exceptions;

public class IllegalLocation extends RuntimeException {
    public IllegalLocation(String message) {
        super(message);
    }
}
