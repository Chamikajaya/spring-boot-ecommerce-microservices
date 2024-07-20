package com.chamika.payment.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class PaymentTopicConfig {

    @Bean
    public NewTopic orderTopic() {
        return  TopicBuilder.name("payment-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }


}
