package com.office.tenant.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public NewTopic tenantCreatedTopic() {

        return new NewTopic(
                "tenant.created",
                3,
                (short) 1
        );
    }
}
