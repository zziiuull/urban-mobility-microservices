package com.paymentservice.domain.entity;

import com.paymentservice.domain.vo.PaymentMethod;
import com.paymentservice.domain.vo.PaymentStatus;

import java.util.Objects;
import java.util.UUID;

public class Payment {
    private final UUID id;
    private final UUID rideId;
    private final UUID passengerId;
    private final double amountPaid;
    private final double totalAmount;
    private final double change;
    private final PaymentStatus status;
    private final PaymentMethod method;

    public Payment(
            UUID rideId,
            UUID passengerId,
            double amountPaid,
            double totalAmount,
            PaymentMethod method
    ) {
        this.id = UUID.randomUUID();
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.amountPaid = amountPaid;
        this.totalAmount = totalAmount;
        this.method = method;

        if (amountPaid < totalAmount) {
            this.status = PaymentStatus.FAILED;
            this.change = 0;
        } else {
            this.status = PaymentStatus.SUCCESS;
            this.change = amountPaid -totalAmount;
        }
    }

    public Payment(
            UUID id,
            UUID rideId,
            UUID passengerId,
            double amountPaid,
            double totalAmount,
            double change,
            PaymentStatus status,
            PaymentMethod method
    ) {
        this.id = id;
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.amountPaid = amountPaid;
        this.totalAmount = totalAmount;
        this.change = change;
        this.status = status;
        this.method = method;
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

    public double getAmountPaid() {
        return amountPaid;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getChange() {
        return change;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public PaymentMethod getMethod() {
        return method;
    }
}
