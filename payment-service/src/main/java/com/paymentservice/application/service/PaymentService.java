package com.paymentservice.application.service;

import com.paymentservice.application.service.events.PaymentFailedEvent;
import com.paymentservice.application.service.events.PaymentSuccessEvent;
import com.paymentservice.application.service.params.PayParams;
import com.paymentservice.domain.entity.Payment;
import com.paymentservice.domain.vo.PaymentStatus;
import com.paymentservice.repository.PaymentMapper;
import com.paymentservice.repository.PaymentRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentService(PaymentRepository paymentRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.paymentRepository = paymentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void pay(PayParams params) {
        Payment payment = new Payment(
                params.rideId(),
                params.passengerId(),
                params.amountPaid(),
                params.totalToPay(),
                params.method()
        );

        paymentRepository.save(PaymentMapper.toEntity(payment));

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            kafkaTemplate.send("payment-success", new PaymentSuccessEvent(
                    payment.getId(),
                    payment.getChange()
            ));
        } else {
            kafkaTemplate.send("payment-failed", new PaymentFailedEvent(
                    params.rideId(),
                    params.passengerId()
            ));
        }
    }

}
