package com.office.tenant.service.impl;


import com.office.tenant.dto.TenantRequest;
import com.office.tenant.dto.TenantResponse;
import com.office.tenant.entity.Tenant;
import com.office.tenant.event.TenantCreatedEvent;
import com.office.tenant.exception.DuplicateTenantException;
import com.office.tenant.exception.TenantNotFoundException;
import com.office.tenant.mapper.TenantMapper;
import com.office.tenant.producer.TenantEventProducer;
import com.office.tenant.repository.TenantRepository;
import com.office.tenant.service.TenantService.TenantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    private final TenantMapper tenantMapper;

    private final TenantEventProducer tenantEventProducer;

    @Override
    public TenantResponse createTenant(TenantRequest requestDto) {

        log.info(
                "Creating tenant for company: {}",
                requestDto.getCompanyName()
        );

        if (tenantRepository.existsByContactEmail(requestDto.getContactEmail())) 
        {

            throw new DuplicateTenantException( "Email already exists");
        }

        Tenant tenant = tenantMapper.toEntity(requestDto);

        tenant.setTenantId(UUID.randomUUID().toString());

        tenant.setCreatedAt(LocalDateTime.now());

        Tenant savedTenant = tenantRepository.save(tenant);

        log.info( "Tenant created successfully: {}",savedTenant.getTenantId());

        TenantCreatedEvent event = TenantCreatedEvent.builder()
                .tenantId(savedTenant.getTenantId())
                .companyName(savedTenant.getCompanyName())
                .contactEmail(savedTenant.getContactEmail())
                .domain(savedTenant.getDomain())
                .build();

        tenantEventProducer.publishTenantCreatedEvent(event);

        return tenantMapper.toResponseDto(savedTenant);
    }

    @Override
    public TenantResponse getTenantByTenantId(
            String tenantId
    ) {

        Tenant tenant =
                tenantRepository
                        .findByTenantId(
                                tenantId
                        )
                        .orElseThrow(() ->
                                new TenantNotFoundException(
                                        "Tenant not found with id: "
                                                + tenantId
                                )
                        );

        return tenantMapper.toResponseDto(
                tenant
        );
    }

    @Override
    public List<TenantResponse> getAllTenants() {

        return tenantRepository
                .findAll()
                .stream()
                .map(
                        tenantMapper::toResponseDto
                )
                .toList();
    }

    @Override
    public TenantResponse updateTenant(
            String tenantId,
            TenantRequest requestDto
    ) {

        Tenant tenant =
                tenantRepository
                        .findByTenantId(
                                tenantId
                        )
                        .orElseThrow(() ->
                                new TenantNotFoundException(
                                        "Tenant not found"
                                )
                        );

        tenant.setCompanyName(
                requestDto.getCompanyName()
        );

        tenant.setDomain(
                requestDto.getDomain()
        );

        tenant.setContactEmail(
                requestDto.getContactEmail()
        );

        tenant.setStatus(
                requestDto.getStatus()
        );

        Tenant updatedTenant =
                tenantRepository.save(
                        tenant
                );

        return tenantMapper.toResponseDto(
                updatedTenant
        );
    }

    @Override
    public void deleteTenant(
            String tenantId
    ) {

        Tenant tenant =
                tenantRepository
                        .findByTenantId(
                                tenantId
                        )
                        .orElseThrow(() ->
                                new TenantNotFoundException(
                                        "Tenant not found"
                                )
                        );

        tenantRepository.delete(
                tenant
        );

        log.info(
                "Tenant deleted successfully: {}",
                tenantId
        );
    }
}