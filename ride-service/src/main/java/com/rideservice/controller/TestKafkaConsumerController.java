package com.rideservice.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test-kafka")
public class TestKafkaConsumerController {

    private final List<String> receivedMessages = new ArrayList<>();

    @KafkaListener(topics = "test-topic", groupId = "ride-service-test")
    public void consume(String message) {
        System.out.println("Mensagem recebida do Kafka: " + message);
        synchronized (receivedMessages) {
            receivedMessages.add(message);
        }
    }

    @GetMapping("/received")
    public List<String> getReceivedMessages() {
        synchronized (receivedMessages) {
            return new ArrayList<>(receivedMessages);
        }
    }
}
