package com.driverservice.presentation.controller.exceptions;

public class DriverNotFound extends RuntimeException {
    public DriverNotFound(String message) {
        super(message);
    }
}
