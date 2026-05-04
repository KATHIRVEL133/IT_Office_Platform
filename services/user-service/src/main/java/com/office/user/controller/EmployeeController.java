package com.office.user.controller;

import com.office.user.dto.EmployeeRequestDTO;
import com.office.user.dto.EmployeeResponseDTO;
import com.office.user.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> create(@RequestBody EmployeeRequestDTO employee,@RequestHeader("X-Tenant-Id") String tenantId) {
        return service.createEmployee(employee,tenantId);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAll() {
        return service.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable Long id,@RequestHeader("X-Tenant-Id") String tenantId) {
        return service.getEmployeeById(id, tenantId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEmployee(id);
    }
}