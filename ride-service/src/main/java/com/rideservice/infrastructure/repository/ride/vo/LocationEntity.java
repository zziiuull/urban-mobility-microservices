package com.rideservice.infrastructure.repository.ride.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public class LocationEntity {

    private double latitude;
    private double longitude;

    protected LocationEntity() {}

    public LocationEntity(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
