package com.driverservice.application.service.driver;

import com.driverservice.domain.vo.location.Location;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DriverLocationService {
    @CachePut(cacheNames = "driverLocation", key = "#driverId")
    public Location updateLocation(UUID driverId, Location location){
        // TODO: put location in database
        return location;
    }

    @Cacheable
    public Location getLocation(UUID driverId){
        return null;
    }
}
