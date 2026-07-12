package com.office.tenant.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.office.tenant.dto.TenantRequest;
import com.office.tenant.dto.TenantResponse;
import com.office.tenant.service.TenantService.TenantService;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<TenantResponse>
    createTenant(
            @Valid
            @RequestBody
            TenantRequest requestDto) {

        TenantResponse response =
                tenantService.createTenant(
                        requestDto
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantResponse>
    getTenantById(
            @PathVariable
            String id) {

        return ResponseEntity.ok(
                tenantService.getTenantByTenantId(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<TenantResponse>>
    getAllTenants() {

        return ResponseEntity.ok(
                tenantService.getAllTenants()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantResponse>
    updateTenant(
            @PathVariable
            String id,

            @RequestBody
            TenantRequest requestDto) {

        return ResponseEntity.ok(
                tenantService.updateTenant(
                        id,
                        requestDto
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteTenant(
            @PathVariable
            String id) {

        tenantService.deleteTenant(id);

        return ResponseEntity.ok(
                "Tenant deleted successfully"
        );
    }
}