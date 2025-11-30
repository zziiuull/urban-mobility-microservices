package com.rideservice.domain.model.entity.passenger;

import java.util.UUID;

public class Passenger {
    UUID id;
    String name;

    public Passenger(){}



    public Passenger(UUID id) {
        this.id = id;
    }

    public Passenger(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
