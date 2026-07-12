package com.office.user.controller;

import com.office.user.dto.EmployeeRequestDTO;
import com.office.user.dto.EmployeeResponseDTO;
import com.office.user.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService service;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> create(
            @RequestBody @Valid EmployeeRequestDTO employee,
            @RequestHeader("X-Tenant-Id") String tenantId) {
        log.info("Received request to create employee with email: {} for tenant: {}", employee.getEmail(), tenantId);
        EmployeeResponseDTO response = service.createEmployee(employee, tenantId);

        log.info("Employee created successfully with id: {} for tenant: {}", response.getId(), tenantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAll(
            @RequestHeader("X-Tenant-Id") String tenantId) {
        log.info("Received request to fetch all employees for tenant: {}", tenantId);
        return ResponseEntity.ok(service.getAllEmployees(tenantId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getById(
            @PathVariable Long id,
            @RequestHeader("X-Tenant-Id") String tenantId) {
        log.info("Received request to fetch employee with id: {} for tenant: {}", id, tenantId);
        return ResponseEntity.ok(service.getEmployeeById(id, tenantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestHeader("X-Tenant-Id") String tenantId) {
        log.info("Received request to delete employee with id: {} for tenant: {}", id, tenantId);
        service.deleteEmployee(id, tenantId);
        return ResponseEntity.noContent().build();
    }
}