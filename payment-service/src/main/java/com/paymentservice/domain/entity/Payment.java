package com.paymentservice.domain.entity;

import com.paymentservice.domain.vo.PaymentMethod;
import com.paymentservice.domain.vo.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Payment {
    private final UUID id;
    private final UUID rideId;
    private final UUID passengerId;
    private final BigDecimal amountPaid;
    private final BigDecimal totalAmount;
    private final BigDecimal change;
    private final PaymentStatus status;
    private final PaymentMethod method;
    private final LocalDateTime createdAt;

    public Payment(
            UUID rideId,
            UUID passengerId,
            BigDecimal amountPaid,
            BigDecimal totalAmount,
            PaymentMethod method
    ) {
        this.id = UUID.randomUUID();
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.amountPaid = amountPaid;
        this.totalAmount = totalAmount;
        this.method = method;
        this.createdAt = LocalDateTime.now();

        if (amountPaid.compareTo(totalAmount) < 0) {
            this.status = PaymentStatus.FAILED;
            this.change = BigDecimal.ZERO;
        } else {
            this.status = PaymentStatus.SUCCESS;
            this.change = amountPaid.subtract(totalAmount);
        }
    }

    public Payment(
            UUID id,
            UUID rideId,
            UUID passengerId,
            BigDecimal amountPaid,
            BigDecimal totalAmount,
            BigDecimal change,
            PaymentStatus status,
            PaymentMethod method,
            LocalDateTime createdAt
    ) {
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Payment payment)) return false;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public UUID getId() {
        return id;
    }

    public UUID getRideId() {
        return rideId;
    }

    public UUID getPassengerId() {
        return passengerId;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getChange() {
        return change;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
