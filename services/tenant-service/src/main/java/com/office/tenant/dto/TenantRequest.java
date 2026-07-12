package com.office.tenant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TenantRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Contact email is required")
    private String contactEmail;

    @NotBlank(message = "Domain is required")
    private String domain;

    @NotBlank(message = "Status is required")
    private String status;
}