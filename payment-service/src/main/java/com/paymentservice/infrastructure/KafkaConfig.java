package com.paymentservice.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

@Configuration
@EnableKafka
public class KafkaConfig {

    private final ConsumerFactory<String, Object> consumerFactory;
    private final ProducerFactory<String, Object> producerFactory;

    public KafkaConfig(
            ConsumerFactory<String, Object> consumerFactory,
            ProducerFactory<String, Object> producerFactory
    ) {
        this.consumerFactory = consumerFactory;
        this.producerFactory = producerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory);
    }
}
