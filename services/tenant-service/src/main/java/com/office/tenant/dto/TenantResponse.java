package com.office.tenant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;



@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TenantResponse {
    private String tenantId;
    private String companyName;
    private String contactEmail;
    private String domain;
    private String status;
    private LocalDateTime createdAt;
}