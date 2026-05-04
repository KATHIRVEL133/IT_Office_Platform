package com.office.user.service;

import com.office.user.dto.EmployeeRequestDTO;
import com.office.user.dto.EmployeeResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {

    ResponseEntity<EmployeeResponseDTO> createEmployee(EmployeeRequestDTO employee,String tenantId);

    ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees();

    ResponseEntity<EmployeeResponseDTO> getEmployeeById(Long id,String tenantId);

    void deleteEmployee(Long id);
}