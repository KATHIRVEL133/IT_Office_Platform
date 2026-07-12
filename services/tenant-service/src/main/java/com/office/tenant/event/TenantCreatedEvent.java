package com.office.tenant.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TenantCreatedEvent {

    private String tenantId;
    private String companyName;
    private String contactEmail;
    private String domain;
}