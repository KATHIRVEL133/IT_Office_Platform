package com.office.common.dto.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCreatedEvent {

    private Long employeeId;
    private String employeeName;
    private String email;
    private String tenantId;
}