package com.passengerservice.domain.model.entity.passenger;

import java.util.UUID;

public class Passenger {
    private UUID id;
    private String name;

    public Passenger(){}

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
