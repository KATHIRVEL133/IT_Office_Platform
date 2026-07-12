package com.office.workspace.service.impl;

import com.office.workspace.constant.WorkspaceStatus;
import com.office.workspace.dto.request.CreateWorkspaceRequest;
import com.office.workspace.dto.request.UpdateWorkspaceRequest;
import com.office.workspace.dto.response.WorkspaceResponse;
import com.office.workspace.entity.Workspace;
import com.office.workspace.exception.DuplicateWorkspaceException;
import com.office.workspace.exception.WorkspaceNotFoundException;
import com.office.workspace.mapper.WorkspaceMapper;
import com.office.workspace.repository.WorkspaceRepository;
import com.office.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl
        implements WorkspaceService {

    private final WorkspaceRepository repository;

    @Override
    public WorkspaceResponse createWorkspace(
            String tenantId,
            CreateWorkspaceRequest request) {

        if (repository.existsByTenantIdAndName(
                tenantId,
                request.getName())) {

            throw new DuplicateWorkspaceException(
                    "Workspace already exists with name : "
                            + request.getName());
        }

        Workspace workspace =
                Workspace.builder()
                        .tenantId(tenantId)
                        .name(request.getName())
                        .description(request.getDescription())
                        .status(WorkspaceStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

        Workspace saved = repository.save(workspace);

        return WorkspaceMapper.toResponse(saved);
    }

    @Override
    public Page<WorkspaceResponse> getAllWorkspaces(
            String tenantId,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("DESC")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return repository.findByTenantIdAndStatus(
                        tenantId,
                        WorkspaceStatus.ACTIVE,
                        pageable)
                .map(WorkspaceMapper::toResponse);
    }

    @Override
    public WorkspaceResponse getWorkspaceById(
            Long workspaceId,
            String tenantId) {

        Workspace workspace =
                repository.findById(workspaceId)
                        .filter(w ->
                                w.getTenantId().equals(tenantId))
                        .orElseThrow(() ->
                                new WorkspaceNotFoundException(
                                        "Workspace not found"));

        return WorkspaceMapper.toResponse(workspace);
    }

    @Override
    public WorkspaceResponse updateWorkspace(
            Long workspaceId,
            String tenantId,
            UpdateWorkspaceRequest request) {

        Workspace workspace =
                repository.findById(workspaceId)
                        .filter(w ->
                                w.getTenantId().equals(tenantId))
                        .orElseThrow(() ->
                                new WorkspaceNotFoundException(
                                        "Workspace not found"));

        if (repository.existsByTenantIdAndNameAndIdNot(
                tenantId,
                request.getName(),
                workspaceId)) {

            throw new DuplicateWorkspaceException(
                    "Workspace already exists.");
        }

        workspace.setName(request.getName());
        workspace.setDescription(request.getDescription());
        workspace.setUpdatedAt(LocalDateTime.now());

        Workspace updated =
                repository.save(workspace);

        return WorkspaceMapper.toResponse(updated);
    }

    @Override
    public void archiveWorkspace(
            Long workspaceId,
            String tenantId) {

        Workspace workspace =
                repository.findById(workspaceId)
                        .filter(w ->
                                w.getTenantId().equals(tenantId))
                        .orElseThrow(() ->
                                new WorkspaceNotFoundException(
                                        "Workspace not found"));

        workspace.setStatus(WorkspaceStatus.ARCHIVED);
        workspace.setUpdatedAt(LocalDateTime.now());

        repository.save(workspace);
    }

    @Override
    public Page<WorkspaceResponse> searchWorkspaces(
            String tenantId,
            String keyword,
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return repository
                .findByTenantIdAndNameContainingIgnoreCaseAndStatus(
                        tenantId,
                        keyword,
                        WorkspaceStatus.ACTIVE,
                        pageable)
                .map(WorkspaceMapper::toResponse);
    }
}