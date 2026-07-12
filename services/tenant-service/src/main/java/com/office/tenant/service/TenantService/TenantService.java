package com.office.tenant.service.TenantService;



import java.util.List;

import com.office.tenant.dto.TenantRequest;
import com.office.tenant.dto.TenantResponse;

public interface TenantService {

    TenantResponse createTenant(
            TenantRequest requestDto
    );

    TenantResponse getTenantByTenantId(
            String tenantId
    );

    List<TenantResponse> getAllTenants();

    TenantResponse updateTenant(
            String tenantId,
            TenantRequest requestDto
    );

    void deleteTenant(
            String tenantId
    );
}