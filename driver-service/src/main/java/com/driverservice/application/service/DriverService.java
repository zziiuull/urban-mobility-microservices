package com.driverservice.application.service;

import com.driverservice.application.service.params.CreateDriverParams;
import com.driverservice.domain.entity.Driver;
import com.driverservice.domain.vo.Location;
import com.driverservice.infrastructure.repository.DriverEntity;
import com.driverservice.infrastructure.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public UUID registerDriver(CreateDriverParams params) {
        Driver driver = new Driver(params.name(), new Location(params.latitude(), params.longitude()));

        var newDriver = new DriverEntity(driver.getId(), driver.getName(), driver.getRating(), driver.isAvailable(), driver.getCurrentLocation().latitude(), driver.getCurrentLocation().longitude());
        driverRepository.save(newDriver);

        return driver.getId();
    }

    public Optional<Driver> findDriver(UUID id) {
        var driver = driverRepository.findById(id);

        return driver.map(driverEntity -> new Driver(driverEntity.id(), driverEntity.name(), driverEntity.rating(), driverEntity.available(), new Location(driverEntity.latitude(), driverEntity.longitude())));
    }
}
