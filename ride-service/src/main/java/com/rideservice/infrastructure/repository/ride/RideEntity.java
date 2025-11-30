package com.rideservice.infrastructure.repository.ride;

import com.rideservice.domain.enums.RideStatus;
import com.rideservice.infrastructure.repository.ride.vo.LocationEntity;
import com.rideservice.infrastructure.repository.ride.vo.PriceEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "rides")
public class RideEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID passengerId;

    private UUID driverId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount")),
            @AttributeOverride(name = "origin.latitude", column = @Column(name = "price_origin_latitude")),
            @AttributeOverride(name = "origin.longitude", column = @Column(name = "price_origin_longitude")),
            @AttributeOverride(name = "destination.latitude", column = @Column(name = "price_destination_latitude")),
            @AttributeOverride(name = "destination.longitude", column = @Column(name = "price_destination_longitude"))
    })
    private PriceEntity price;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "latitude", column = @Column(name = "origin_latitude")), @AttributeOverride(name = "longitude", column = @Column(name = "origin_longitude"))})
    private LocationEntity origin;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "latitude", column = @Column(name = "destination_latitude")), @AttributeOverride(name = "longitude", column = @Column(name = "destination_longitude"))})
    private LocationEntity destination;

    private int travelTime;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    public RideEntity() {
    }

    public RideEntity(UUID id, UUID passengerId, UUID driverId, LocationEntity origin, LocationEntity destination, int travelTime, PriceEntity price, RideStatus rideStatus) {
        this.id = id;
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.origin = origin;
        this.destination = destination;
        this.travelTime = travelTime;
        this.price = price;
        this.rideStatus = rideStatus;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(UUID passengerId) {
        this.passengerId = passengerId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public void setDriverId(UUID driverId) {
        this.driverId = driverId;
    }

    public LocationEntity getOrigin() {
        return origin;
    }

    public void setOrigin(LocationEntity origin) {
        this.origin = origin;
    }

    public LocationEntity getDestination() {
        return destination;
    }

    public void setDestination(LocationEntity destination) {
        this.destination = destination;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    public PriceEntity getPrice() {
        return price;
    }

    public void setPrice(PriceEntity price) {
        this.price = price;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatus rideStatus) {
        this.rideStatus = rideStatus;
    }
}
