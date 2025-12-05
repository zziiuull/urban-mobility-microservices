package com.rideservice.infrastructure.repository.ride;

import com.rideservice.domain.model.entity.driver.Driver;
import com.rideservice.domain.model.entity.passenger.Passenger;
import com.rideservice.domain.model.entity.ride.Ride;
import com.rideservice.domain.vo.location.Location;
import com.rideservice.domain.vo.price.Price;
import com.rideservice.infrastructure.repository.ride.vo.LocationEntity;
import com.rideservice.infrastructure.repository.ride.vo.PriceEntity;

public class RideMapper {
    public static Ride toDomain(RideEntity entity) {
        return new Ride(
                entity.getId(),
                new Price(entity.getPrice().getAmount(), new Location(entity.getPrice().getOrigin().getLatitude(), entity.getPrice().getOrigin().getLongitude()), new Location(entity.getPrice().getDestination().getLatitude(), entity.getPrice().getDestination().getLongitude())),
                entity.getDriverId() != null ? new Driver(entity.getDriverId()) : null,
                new Passenger(entity.getPassengerId()),
                entity.getTravelTime(),
                new Location(entity.getOrigin().getLatitude(), entity.getOrigin().getLongitude()),
                new Location(entity.getDestination().getLatitude(), entity.getDestination().getLongitude()),
                entity.getRideStatus()
        );
    }

    public static RideEntity toEntity(Ride ride) {

        return new RideEntity(
                ride.getId(),
                ride.getPassenger().getId(),
                ride.getDriver() != null ? ride.getDriver().getId() : null,
                new LocationEntity(ride.getOrigin().latitude(), ride.getOrigin().longitude()),
                new LocationEntity(ride.getDestination().latitude(), ride.getDestination().longitude()),
                ride.getTravelTime(),
                new PriceEntity(ride.getPrice().amount(), new LocationEntity(ride.getOrigin().latitude(), ride.getOrigin().longitude()), new LocationEntity(ride.getDestination().latitude(), ride.getDestination().longitude())),
                ride.getRideStatus()
        );
    }
}
