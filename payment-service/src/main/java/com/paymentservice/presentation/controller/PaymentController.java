package com.paymentservice.presentation.controller;

import com.paymentservice.application.service.PaymentService;
import com.paymentservice.application.service.params.PayParams;
import com.paymentservice.presentation.controller.requests.PayRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay/{rideId}")
    public ResponseEntity<Void> pay(@PathVariable UUID id, @RequestBody PayRequest request) {
        var params = new PayParams(id, request.passengerId(), request.amountPaid(), request.totalToPay(), request.method());
        paymentService.pay(params);
        return ResponseEntity.ok().build();
    }
}
