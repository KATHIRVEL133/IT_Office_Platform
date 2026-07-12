package com.office.workspace.mapper;

import com.office.workspace.dto.response.WorkspaceResponse;
import com.office.workspace.entity.Workspace;

public class WorkspaceMapper {

    private WorkspaceMapper() {
    }

    public static WorkspaceResponse toResponse(
            Workspace workspace) {

        return WorkspaceResponse.builder()
                .id(workspace.getId())
                .tenantId(workspace.getTenantId())
                .name(workspace.getName())
                .description(workspace.getDescription())
                .status(workspace.getStatus().name())
                .createdAt(workspace.getCreatedAt())
                .updatedAt(workspace.getUpdatedAt())
                .build();
    }

}