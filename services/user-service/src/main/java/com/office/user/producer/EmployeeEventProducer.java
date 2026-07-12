package com.office.user.producer;

import com.office.user.event.EmployeeCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishEmployeeCreatedEvent(
            EmployeeCreatedEvent event) {

        log.info("========== KAFKA PRODUCER START ==========");

        log.info(
                "Publishing employee created event for employeeId: {}",
                event.getEmployeeId()
        );

        log.info("Tenant Id: {}", event.getTenantId());
        log.info("Event Payload: {}", event);
        log.info("Kafka Topic: employee.created");

        try {

            log.info("Sending message to Kafka...");

            CompletableFuture<SendResult<String, Object>> future =
                    kafkaTemplate.send(
             "employee.created",
                     event.getTenantId(),
                     event
);

            log.info("Kafka send() method executed");

            future.thenAccept(result -> {

                log.info("========== KAFKA SUCCESS ==========");

                log.info(
                        "Kafka event published successfully for employeeId: {}",
                        event.getEmployeeId()
                );

                log.info(
                        "Topic: {}",
                        result.getRecordMetadata().topic()
                );

                log.info(
                        "Partition: {}",
                        result.getRecordMetadata().partition()
                );

                log.info(
                        "Offset: {}",
                        result.getRecordMetadata().offset()
                );

            }).exceptionally(ex -> {

                log.error("========== KAFKA FAILURE ==========");

                log.error(
                        "Failed to publish kafka event"
                );

                log.error(
                        "Exception Message: {}",
                        ex.getMessage()
                );

                log.error(
                        "Root Cause: {}",
                        ex.getCause()
                );

                ex.printStackTrace();

                return null;
            });

        } catch (Exception ex) {

            log.error("========== KAFKA EXCEPTION ==========");
            log.error("Kafka send failed before execution");

            log.error(
                    "Exception Message: {}",
                    ex.getMessage()
            );

            ex.printStackTrace();
        }

        log.info("========== KAFKA PRODUCER END ==========");
    }
}