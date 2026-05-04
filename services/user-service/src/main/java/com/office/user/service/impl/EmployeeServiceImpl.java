package com.office.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.office.user.dto.EmployeeRequestDTO;
import com.office.user.dto.EmployeeResponseDTO;
import com.office.user.entity.Employee;
import com.office.user.exception.DuplicateResourceException;
import com.office.user.mapper.EmployeeMapper;
import com.office.user.repository.EmployeeRepository;
import com.office.user.service.EmployeeService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    @Override
    public ResponseEntity<EmployeeResponseDTO> createEmployee(EmployeeRequestDTO employee,String tenantId) {
        
        Employee empEntity = mapper.toEntity(employee,tenantId);
        if(repository.existsByEmail(employee.getEmail())) {
            throw new DuplicateResourceException("Employee with email already exists");
        }
        employee.setStatus("ACTIVE");
        return new ResponseEntity<>(mapper.toDTO(repository.save(empEntity)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        try{
         return new ResponseEntity<>(repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList()), HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
    }

    @Override
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(Long id,String tenantId) {
        try
        {
            return new ResponseEntity<>(mapper.toDTO(repository.findByIdAndTenantId(id,tenantId)
                .orElseThrow(() -> new RuntimeException("Employee not found"))), HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }
        repository.deleteById(id);
    }
}