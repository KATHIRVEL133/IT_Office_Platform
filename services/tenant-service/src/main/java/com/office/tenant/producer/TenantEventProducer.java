package com.office.tenant.producer;

import com.office.tenant.event.TenantCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishTenantCreatedEvent(TenantCreatedEvent event) {

        log.info("========== KAFKA PRODUCER START ==========");
        log.info("Publishing tenant created event: {}", event);

        try {

            CompletableFuture<SendResult<String, Object>> future =
                    kafkaTemplate.send(
                            "tenant.created",
                            event.getTenantId(),
                            event
                    );

            future.thenAccept(result -> {

                log.info("Kafka event published successfully");

                log.info(
                        "Topic: {}, Partition: {}, Offset: {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()
                );

            }).exceptionally(ex -> {

                log.error("Kafka publish failed");
                log.error("Error Message: {}", ex.getMessage(), ex);

                return null;
            });

        } catch (Exception ex) {

            log.error("Kafka send failed before execution");
            log.error("Exception Message: {}", ex.getMessage(), ex);

            // Option 1: just log and continue
            // Option 2: throw custom exception

            // throw new KafkaPublishException(
            //         "Failed to publish tenant event", ex);

        }

        log.info("========== KAFKA PRODUCER END ==========");
    }
}