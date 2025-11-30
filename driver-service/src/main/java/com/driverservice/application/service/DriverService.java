package com.driverservice.application.service;

import com.driverservice.application.service.params.CreateDriverParams;
import com.driverservice.domain.entity.Driver;
import com.driverservice.domain.vo.Location;
import com.driverservice.infrastructure.repository.DriverMapper;
import com.driverservice.infrastructure.repository.DriverRepository;
import com.driverservice.presentation.controller.exceptions.DriverNotFound;
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

        var newDriver = DriverMapper.toEntity(driver);
        driverRepository.save(newDriver);

        return driver.getId();
    }

    public Driver findDriver(UUID id) {
        var driver = driverRepository.findById(id);

        return driver.map(DriverMapper::toDomain).orElseThrow(() -> new DriverNotFound("Driver not found"));
    }
}
