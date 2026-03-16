package com.internship.stock_monitor_service.config.kafka;

import topics.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic alertTriggeredTopic() {
        return TopicBuilder.name(KafkaTopics.STOCK_ALERT_TRIGGERED)
                .partitions(3)
                .replicas(1)
                .build();
    }
}