package com.rideservice.domain.model.entity.ride;


import com.rideservice.domain.model.entity.driver.Driver;
import com.rideservice.domain.model.entity.passenger.Passenger;
import com.rideservice.domain.vo.location.Location;
import com.rideservice.domain.vo.price.Price;
import com.rideservice.domain.enums.RideStatus;


import java.util.UUID;

public class Ride {
    UUID id;
    Passenger passenger;
    Driver driver;
    Location origin;
    Location destination;
    int travelTime;
    Price price;
    RideStatus rideStatus;

    public Ride() {
    }

    public Ride(UUID id, Passenger passenger, Driver driver, Location origin, Location destination, int travelTime, Price price, RideStatus rideStatus) {
        this.id = id;
        this.passenger = passenger;
        this.driver = driver;
        this.origin = origin;
        this.destination = destination;
        this.travelTime = travelTime;
        this.price = price;
        this.rideStatus = rideStatus;
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
