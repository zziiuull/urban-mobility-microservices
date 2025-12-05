package com.rideservice.infrastructure.repository.ride.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class PriceEntity {

    private double amount;

    @Embedded
    private LocationEntity origin;

    @Embedded
    private LocationEntity destination;

    protected PriceEntity() {
    }

    public PriceEntity(double amount, LocationEntity origin, LocationEntity destination) {
        this.amount = amount;
        this.origin = origin;
        this.destination = destination;
    }

    public double getAmount() {
        return amount;
    }

    public LocationEntity getOrigin() {
        return origin;
    }

    public LocationEntity getDestination() {
        return destination;
    }
}
