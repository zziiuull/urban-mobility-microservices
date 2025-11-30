package com.driverservice.infrastructure.repository.driver;

import com.driverservice.domain.model.entity.driver.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
}
