package com.office.user.event;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreatedEvent {

    private Long employeeId;

    private String firstName;

    private String email;

    private String tenantId;

    private String correlationId;
}