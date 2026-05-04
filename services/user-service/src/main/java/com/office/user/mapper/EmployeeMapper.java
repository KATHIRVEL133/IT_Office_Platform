package com.office.user.mapper;

import org.springframework.stereotype.Component;
import com.office.user.dto.*;
import com.office.user.entity.Employee;

@Component
public class EmployeeMapper {

    public  Employee toEntity(EmployeeRequestDTO dto, String tenantId) {
        return Employee.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .role(dto.getRole())
                .status(dto.getStatus())
                .tenantId(tenantId)
                .build();
    }

    public  EmployeeResponseDTO toDTO(Employee emp) {
        return EmployeeResponseDTO.builder()
                .id(emp.getId())
                .firstName(emp.getFirstName())
                .lastName(emp.getLastName())
                .email(emp.getEmail())
                .department(emp.getDepartment())
                .role(emp.getRole())
                .status(emp.getStatus())
                .build();
    }
}