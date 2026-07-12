package com.office.user.repository;

import com.office.user.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmailAndTenantId(String email, String tenantId);

    boolean existsByEmailAndTenantId(String email, String tenantId);

    Optional<Employee> findByIdAndTenantId(Long id, String tenantId);

    List<Employee> findAllByTenantId(String tenantId);
}