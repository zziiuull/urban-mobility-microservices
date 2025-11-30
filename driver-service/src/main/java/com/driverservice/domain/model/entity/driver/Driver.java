package com.driverservice.domain.model.entity.driver;

import com.driverservice.domain.vo.location.Location;

import java.util.UUID;

public class Driver {
    private UUID id;
    private String name;
    private double rating;
    private boolean available;
    Location currentLocation;

    public Driver() {

    }

    public Driver(String name, double rating, boolean available, Location currentLocation) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.rating = rating;
        this.available = available;
        this.currentLocation = currentLocation;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
}
