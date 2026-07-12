package com.office.user.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.office.user.event.EmployeeCreatedEvent;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmployeeEventConsumer {

    @KafkaListener(
            topics = "employee.created",
            groupId = "user-service-group"
    )
    public void consume(EmployeeCreatedEvent event) {

        log.info(
                "Received employee created event: {}",
                event
        );
    }
}
