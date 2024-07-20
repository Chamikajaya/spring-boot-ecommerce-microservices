package com.chamika.order.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class OrderTopicConfig {

    @Bean
    public NewTopic orderTopic() {
        return  TopicBuilder.name("order-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }



}