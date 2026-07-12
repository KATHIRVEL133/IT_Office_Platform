package com.office.user.service.impl;

import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.office.user.dto.EmployeeRequestDTO;
import com.office.user.dto.EmployeeResponseDTO;
import com.office.user.entity.Employee;
import com.office.user.event.EmployeeCreatedEvent;
import com.office.user.exception.DuplicateResourceException;
import com.office.user.mapper.EmployeeMapper;
import com.office.user.producer.EmployeeEventProducer;
import com.office.user.repository.EmployeeRepository;
import com.office.user.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeEventProducer producer;
    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;


    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto, String tenantId) {

        log.info("Creating employee with email: {} for tenant: {}", dto.getEmail(), tenantId);

        if (repository.existsByEmailAndTenantId(dto.getEmail(), tenantId)) {
            log.warn("Duplicate employee attempt for email: {} tenant: {}", dto.getEmail(), tenantId);
            throw new DuplicateResourceException("Employee already exists");
        }

        Employee emp = mapper.toEntity(dto, tenantId);
        emp.setStatus("ACTIVE");

        Employee savedEmployee = repository.save(emp);

        log.info("Employee created successfully with id: {} for tenant: {}", savedEmployee.getId(), tenantId);

        String correlationId = org.slf4j.MDC.get("correlationId");

        EmployeeCreatedEvent event = EmployeeCreatedEvent.builder()
        .employeeId(savedEmployee.getId())
        .firstName(savedEmployee.getFirstName())
        .email(savedEmployee.getEmail())
        .tenantId(savedEmployee.getTenantId())
        .correlationId(correlationId)
        .build();

        producer.publishEmployeeCreatedEvent(event);

        return mapper.toDTO(savedEmployee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees(String tenantId) {
        log.info("Fetching all employees for tenant: {}", tenantId);
        return repository.findAllByTenantId(tenantId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Cacheable(value = "employees", key = "#tenantId + '_' + #id")
    @Override
    public EmployeeResponseDTO getEmployeeById(Long id, String tenantId) {
        log.info("Fetching employee id: {} for tenant: {}", id, tenantId);
        Employee emp = repository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {} for tenant: {}", id, tenantId);
                    return new RuntimeException("Employee not found");
                });

        return mapper.toDTO(emp);
    }

    @CacheEvict(value = "employees", key = "#tenantId + '_' + #id")
    @Override
    public void deleteEmployee(Long id, String tenantId) {

        log.info("Deleting employee id: {} for tenant: {}", id, tenantId);
        Employee emp = repository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {} for tenant: {}", id, tenantId);
                    return new RuntimeException("Employee not found");
                });

        repository.delete(emp);
    }
}