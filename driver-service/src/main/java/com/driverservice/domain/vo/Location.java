package com.driverservice.domain.vo;

import com.driverservice.presentation.controller.exceptions.IllegalLocation;

public record Location(double latitude, double longitude) {
    public Location {
        if (latitude < -90 || latitude > 90) throw new IllegalLocation("Latitude must be between -90 and 90");

        if (longitude < -180 || longitude > 180)
            throw new IllegalLocation("Longitude must be between -180 and 180");
    }
}
