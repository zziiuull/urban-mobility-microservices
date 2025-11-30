package com.driverservice.application.service.driver;

import com.driverservice.domain.model.entity.driver.Driver;
import com.driverservice.infrastructure.repository.driver.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver registerDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Optional<Driver> findDriver(UUID driverId){
        return driverRepository.findById(driverId);
    }
}
