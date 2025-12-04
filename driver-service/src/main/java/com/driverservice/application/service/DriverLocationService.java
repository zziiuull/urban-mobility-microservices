package com.driverservice.application.service;

import com.driverservice.application.service.params.UpdateDriverLocationParams;
import com.driverservice.domain.vo.Location;
import com.driverservice.infrastructure.repository.DriverRepository;
import com.driverservice.presentation.controller.exceptions.DriverNotFound;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DriverLocationService {
    private final DriverRepository repository;

    public DriverLocationService(DriverRepository repository) {
        this.repository = repository;
    }

    public void updateLocation(UpdateDriverLocationParams params) {
        var driver = repository.findById(params.id())
                .orElseThrow(() -> new DriverNotFound("Driver not found"));

        driver.setLatitude(params.latitude());
        driver.setLongitude(params.longitude());

        repository.save(driver);
    }

    public Location getLocation(UUID driverId) {
        var driver = repository.findById(driverId)
                .orElseThrow(() -> new DriverNotFound("Driver not found"));

        return new Location(driver.getLatitude(), driver.getLongitude());
    }
}
