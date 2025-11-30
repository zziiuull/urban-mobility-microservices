package com.rideservice.domain.model.entity.ride;

import com.rideservice.domain.enums.RideStatus;
import com.rideservice.domain.model.entity.driver.Driver;
import com.rideservice.domain.model.entity.passenger.Passenger;
import com.rideservice.domain.vo.location.Location;
import com.rideservice.domain.vo.price.Price;
import com.rideservice.presentation.controller.exception.DriverAlreadyAssigned;
import com.rideservice.presentation.controller.exception.RideAlreadyCanceledException;

import java.util.UUID;

public class Ride {
    UUID id;
    Price price;
    Driver driver;
    int travelTime;
    Location origin;
    Location destination;
    Passenger passenger;
    RideStatus rideStatus;

    public Ride() {
        this.id = UUID.randomUUID();
    }

    public Ride(Price price, Driver driver, int travelTime, Location origin, Passenger passenger, Location destination, RideStatus rideStatus) {
        this.id = UUID.randomUUID();
        this.price = price;
        this.driver = driver;
        this.travelTime = travelTime;
        this.origin = origin;
        this.passenger = passenger;
        this.destination = destination;
        this.rideStatus = rideStatus;
    }

    public Ride(Price price, Passenger passenger, Location origin, Location destination) {
        this.id = UUID.randomUUID();
        this.price = price;
        this.origin = origin;
        this.passenger = passenger;
        this.destination = destination;
        this.rideStatus = RideStatus.REQUESTED;
    }

    public Ride(UUID id, Price price, Driver driver, Passenger passenger, int travelTime, Location origin, Location destination, RideStatus rideStatus) {
        this.id = id;
        this.price = price;
        this.driver = driver;
        this.travelTime = travelTime;
        this.origin = origin;
        this.passenger = passenger;
        this.destination = destination;
        this.rideStatus = rideStatus;
    }

    public void assignDriver(Driver newDriver) {
        if (driver != null) throw new DriverAlreadyAssigned("Driver already assigned for this ride");

        setDriver(newDriver);
        setRideStatus(RideStatus.DRIVER_ASSIGNED);
    }

    public void cancel() {
        if (rideStatus == RideStatus.COMPLETED) {
            throw new RideAlreadyCanceledException("Ride already completed, cannot cancel");
        }

        setRideStatus(RideStatus.CANCELLED);
    }

    public UUID getId() {
        return id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
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
