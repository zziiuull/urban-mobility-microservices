package com.paymentservice.infrastructure.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "payments")
public class PaymentEntity{
    @Id
    private UUID id;
    private UUID rideId;
    private UUID passengerId;
    private double amountPaid;
    private double totalAmount;
    private double change;
    private String status;
    private String method;

    public PaymentEntity() {
    }

    public PaymentEntity(UUID id, UUID rideId, UUID passengerId, double amountPaid, double totalAmount, double change, String status, String method) {
        this.id = id;
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.amountPaid = amountPaid;
        this.totalAmount = totalAmount;
        this.change = change;
        this.status = status;
        this.method = method;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRideId() {
        return rideId;
    }

    public void setRideId(UUID rideId) {
        this.rideId = rideId;
    }

    public UUID getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(UUID passengerId) {
        this.passengerId = passengerId;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
