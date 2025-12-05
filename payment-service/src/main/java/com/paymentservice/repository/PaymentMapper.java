package com.paymentservice.repository;

import com.paymentservice.domain.entity.Payment;
import com.paymentservice.domain.vo.PaymentMethod;
import com.paymentservice.domain.vo.PaymentStatus;

public class PaymentMapper {
    public static PaymentEntity toEntity(Payment payment) {
        return new PaymentEntity(
                payment.getId(),
                payment.getRideId(),
                payment.getPassengerId(),
                payment.getAmountPaid(),
                payment.getTotalAmount(),
                payment.getChange(),
                payment.getStatus().name(),
                payment.getMethod().name()
        );
    }

    public static Payment toDomain(PaymentEntity entity) {
        return new Payment(
                entity.getId(),
                entity.getRideId(),
                entity.getPassengerId(),
                entity.getAmountPaid(),
                entity.getTotalAmount(),
                entity.getChange(),
                PaymentStatus.valueOf(entity.getStatus()),
                PaymentMethod.valueOf(entity.getMethod())
        );
    }
}
