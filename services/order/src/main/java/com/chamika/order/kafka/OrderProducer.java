package com.chamika.order.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j

// The OrderProducer class is responsible for sending order confirmations to the Kafka topic `order-topic`.
public class OrderProducer {

    // to produce messages to Kafka topics.
    private final KafkaTemplate<String, OrderConfirmation> kafkaTemplate;

    // Create a Kafka message with the order confirmation as the payload and send it to the Kafka topic `order-topic`.
    public void sendOrderConfirmation(OrderConfirmation orderConfirmation) {

        log.info("Sending order confirmation to Kafka `order-topic` : {}", orderConfirmation);

        Message<OrderConfirmation> message = MessageBuilder.withPayload(orderConfirmation)
                .setHeader(KafkaHeaders.TOPIC, "order-topic")
                .build();

        kafkaTemplate.send(message);


    }

}
