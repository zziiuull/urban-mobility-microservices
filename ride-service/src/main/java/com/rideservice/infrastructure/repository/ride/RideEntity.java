package com.rideservice.infrastructure.repository.ride;

import com.rideservice.domain.enums.RideStatus;
import com.rideservice.domain.vo.location.Location;
import com.rideservice.domain.vo.price.Price;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "rides")
public class RideEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID passengerId;

    @Column(nullable = false)
    private UUID driverId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "origin_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "origin_longitude"))
    })
    private Location origin;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "destination_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "destination_longitude"))
    })
    private Location destination;

    private int travelTime;

    @Embedded
    private Price price;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    public RideEntity() {}


    public RideEntity(UUID id, UUID passengerId, UUID driverId, Location origin, Location destination, int travelTime, Price price, RideStatus rideStatus) {
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

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatus rideStatus) {
        this.rideStatus = rideStatus;
    }

}

