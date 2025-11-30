package com.rideservice.infrastructure.repository.ride.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.math.BigDecimal;

@Embeddable
public class PriceEntity {

    private BigDecimal amount;

    @Embedded
    private LocationEntity origin;

    @Embedded
    private LocationEntity destination;

    protected PriceEntity() {
    }

    public PriceEntity(BigDecimal amount, LocationEntity origin, LocationEntity destination) {
        this.amount = amount;
        this.origin = origin;
        this.destination = destination;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocationEntity getOrigin() {
        return origin;
    }

    public LocationEntity getDestination() {
        return destination;
    }
}
