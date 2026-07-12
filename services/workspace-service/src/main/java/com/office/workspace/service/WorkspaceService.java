package com.office.workspace.service;

import com.office.workspace.dto.request.CreateWorkspaceRequest;
import com.office.workspace.dto.request.UpdateWorkspaceRequest;
import com.office.workspace.dto.response.WorkspaceResponse;
import org.springframework.data.domain.Page;

public interface WorkspaceService {

    WorkspaceResponse createWorkspace(
            String tenantId,
            CreateWorkspaceRequest request);

    Page<WorkspaceResponse> getAllWorkspaces(
            String tenantId,
            int page,
            int size,
            String sortBy,
            String direction);

    WorkspaceResponse getWorkspaceById(
            Long workspaceId,
            String tenantId);

    WorkspaceResponse updateWorkspace(
            Long workspaceId,
            String tenantId,
            UpdateWorkspaceRequest request);

    void archiveWorkspace(
            Long workspaceId,
            String tenantId);

    Page<WorkspaceResponse> searchWorkspaces(
            String tenantId,
            String keyword,
            int page,
            int size);
}