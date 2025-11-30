package com.driverservice.presentation.controller.requests;

public record CreateDriverRequest(
        String name,
        double latitude,
        double longitude
) {
}
