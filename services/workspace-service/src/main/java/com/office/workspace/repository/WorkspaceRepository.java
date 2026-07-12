package com.office.workspace.repository;

import com.office.workspace.constant.WorkspaceStatus;
import com.office.workspace.entity.Workspace;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository
        extends JpaRepository<Workspace, Long> {

    Page<Workspace> findByTenantIdAndStatus(
            String tenantId,
            WorkspaceStatus status,
            Pageable pageable);

    boolean existsByTenantIdAndName(
            String tenantId,
            String name);

    boolean existsByTenantIdAndNameAndIdNot(
            String tenantId,
            String name,
            Long id);

    Page<Workspace> findByTenantIdAndNameContainingIgnoreCaseAndStatus(
            String tenantId,
            String name,
            WorkspaceStatus status,
            Pageable pageable);

}