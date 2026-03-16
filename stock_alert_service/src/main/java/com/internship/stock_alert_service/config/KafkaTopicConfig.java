package com.internship.stock_alert_service.config;

import topics.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic alertCreationsTopic() {
        return TopicBuilder.name(KafkaTopics.STOCK_ALERT_CREATIONS)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic alertDeletionsTopic() {
        return TopicBuilder.name(KafkaTopics.STOCK_ALERT_DELETIONS)
                .partitions(3)
                .replicas(1)
                .build();
    }
}