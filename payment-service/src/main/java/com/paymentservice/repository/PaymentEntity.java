package com.paymentservice.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class PaymentEntity{
    @Id
    private UUID id;
    private UUID rideId;
    private UUID passengerId;
    private BigDecimal amountPaid;
    private BigDecimal totalAmount;
    private BigDecimal change;
    private String status;
    private String method;
    private LocalDateTime createdAt;

    public PaymentEntity() {
    }

    public PaymentEntity(UUID id, UUID rideId, UUID passengerId, BigDecimal amountPaid, BigDecimal totalAmount, BigDecimal change, String status, String method, LocalDateTime createdAt) {
        this.id = id;
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.amountPaid = amountPaid;
        this.totalAmount = totalAmount;
        this.change = change;
        this.status = status;
        this.method = method;
        this.createdAt = createdAt;
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

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
