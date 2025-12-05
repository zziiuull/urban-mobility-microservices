package com.rideservice.domain.vo.price;

import com.rideservice.domain.vo.location.Location;

public record Price(double amount, Location origin, Location destination) {
    public Price {
        if (amount < 0)
            throw new IllegalArgumentException("Amount must be >= 0");

        if (origin == null || destination == null)
            throw new IllegalArgumentException("Origin and destination must not be null");

    }

    public Price add(Price other) {
        if (!origin.equals(other.origin) || !destination.equals(other.destination))
            throw new IllegalArgumentException("Prices must refer to same route");

        return new Price(this.amount + other.amount, this.origin, this.destination);
    }
}
