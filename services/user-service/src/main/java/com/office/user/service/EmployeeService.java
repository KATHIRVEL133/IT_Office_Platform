package com.office.user.service;

import com.office.user.dto.EmployeeRequestDTO;
import com.office.user.dto.EmployeeResponseDTO;


import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employee, String tenantId);

    List<EmployeeResponseDTO> getAllEmployees(String tenantId);

    EmployeeResponseDTO getEmployeeById(Long id, String tenantId);

    void deleteEmployee(Long id, String tenantId);
}