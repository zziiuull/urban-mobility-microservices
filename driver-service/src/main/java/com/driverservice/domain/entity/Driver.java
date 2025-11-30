package com.driverservice.domain.entity;

import com.driverservice.domain.vo.Location;

import java.util.Objects;
import java.util.UUID;

public class Driver {
    private final UUID id;
    private String name;
    private double rating;
    private boolean available;
    Location currentLocation;

    public Driver(String name, Location currentLocation) {
        this.id = java.util.UUID.randomUUID();
        this.name = name;
        this.rating = 0;
        this.available = true;
        this.currentLocation = currentLocation;
    }

    public Driver(UUID id, String name, double rating, boolean available, Location currentLocation) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.available = available;
        this.currentLocation = currentLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Driver driver)) return false;
        return Objects.equals(id, driver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
