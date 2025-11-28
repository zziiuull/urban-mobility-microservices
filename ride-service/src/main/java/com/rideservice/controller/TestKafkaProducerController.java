package com.rideservice.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-kafka")
public class TestKafkaProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public TestKafkaProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        kafkaTemplate.send("test-topic", message);
        return "Mensagem enviada para Kafka: " + message;
    }
}
