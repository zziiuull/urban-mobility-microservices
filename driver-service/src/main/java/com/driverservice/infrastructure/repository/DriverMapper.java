package com.driverservice.infrastructure.repository;

import com.driverservice.domain.entity.Driver;
import com.driverservice.domain.vo.Location;

public class DriverMapper {
    public static Driver toDomain(DriverEntity entity) {
        return new Driver(
                entity.id(),
                entity.name(),
                entity.rating(),
                entity.available(),
                new Location(entity.latitude(), entity.longitude())
        );
    }

    public static DriverEntity toEntity(Driver driver) {
        return new DriverEntity(
                driver.getId(),
                driver.getName(),
                driver.getRating(),
                driver.isAvailable(),
                driver.getCurrentLocation().latitude(),
                driver.getCurrentLocation().longitude()
        );
    }

}
