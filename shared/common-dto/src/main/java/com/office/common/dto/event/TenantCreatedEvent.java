package com.office.common.dto.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantCreatedEvent {

    private String tenantId;
    private String tenantName;
}