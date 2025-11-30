package com.passengerservice.domain.entity;

import java.util.Objects;
import java.util.UUID;

public class Passenger {
    private final UUID id;
    private String name;

    public Passenger(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Passenger(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Passenger passenger)) return false;
        return Objects.equals(id, passenger.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
