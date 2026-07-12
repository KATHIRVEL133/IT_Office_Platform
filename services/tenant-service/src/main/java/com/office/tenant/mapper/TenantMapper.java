package com.office.tenant.mapper;

import com.office.tenant.dto.TenantRequest;
import com.office.tenant.dto.TenantResponse;
import com.office.tenant.entity.Tenant;
import org.springframework.stereotype.Component;

@Component
public class TenantMapper {

    public Tenant toEntity(
            TenantRequest dto
    ) {

        Tenant tenant = new Tenant();

        tenant.setCompanyName(
                dto.getCompanyName()
        );

        tenant.setDomain(
                dto.getDomain()
        );

        tenant.setContactEmail(
                dto.getContactEmail()
        );

        tenant.setStatus(
                dto.getStatus()
        );

        return tenant;
    }

    public TenantResponse toResponseDto(
            Tenant tenant
    ) {

        TenantResponse dto =
                new TenantResponse();

       

        dto.setTenantId(
                tenant.getTenantId()
        );

        dto.setCompanyName(
                tenant.getCompanyName()
        );

        dto.setDomain(
                tenant.getDomain()
        );

        dto.setContactEmail(
                tenant.getContactEmail()
        );

        dto.setStatus(
                tenant.getStatus()
        );

        dto.setCreatedAt(
                tenant.getCreatedAt()
        );

        return dto;
    }
}