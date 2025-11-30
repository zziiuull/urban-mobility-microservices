package com.driverservice.application.service;

import com.driverservice.application.service.params.UpdateDriverLocationParams;
import com.driverservice.domain.vo.Location;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DriverLocationService {
    // TODO: key
    @CachePut(cacheNames = "driverLocation", key = "#params")
    public Optional<Location> updateLocation(UpdateDriverLocationParams params) {
        // TODO: put location in database
        return Optional.empty();
    }

    @Cacheable(cacheNames = "driverLocation", key = "#driverId")
    public Optional<Location> getLocation(UUID driverId) {
        // TODO: get location from database
        return Optional.empty();
    }
}
