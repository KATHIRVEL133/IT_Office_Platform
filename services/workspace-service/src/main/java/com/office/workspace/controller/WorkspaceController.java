package com.office.workspace.controller;

import com.office.common.util.constants.TenantConstants;
import com.office.workspace.dto.request.CreateWorkspaceRequest;
import com.office.workspace.dto.request.UpdateWorkspaceRequest;
import com.office.workspace.dto.response.WorkspaceResponse;
import com.office.workspace.service.WorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public WorkspaceResponse createWorkspace(
            @RequestHeader(TenantConstants.TENANT_HEADER)
            String tenantId,

            @Valid
            @RequestBody
            CreateWorkspaceRequest request) {

        return workspaceService.createWorkspace(
                tenantId,
                request);
    }

    @GetMapping
    public Page<WorkspaceResponse> getAllWorkspaces(

            @RequestHeader(TenantConstants.TENANT_HEADER)
            String tenantId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "createdAt")
            String sortBy,

            @RequestParam(defaultValue = "DESC")
            String direction) {

        return workspaceService.getAllWorkspaces(
                tenantId,
                page,
                size,
                sortBy,
                direction);
    }

    @GetMapping("/{workspaceId}")
    public WorkspaceResponse getWorkspaceById(

            @RequestHeader(TenantConstants.TENANT_HEADER)
            String tenantId,

            @PathVariable
            Long workspaceId) {

        return workspaceService.getWorkspaceById(
                workspaceId,
                tenantId);
    }

    @PutMapping("/{workspaceId}")
    public WorkspaceResponse updateWorkspace(

            @RequestHeader(TenantConstants.TENANT_HEADER)
            String tenantId,

            @PathVariable
            Long workspaceId,

            @Valid
            @RequestBody
            UpdateWorkspaceRequest request) {

        return workspaceService.updateWorkspace(
                workspaceId,
                tenantId,
                request);
    }

    @DeleteMapping("/{workspaceId}")
    public void archiveWorkspace(

            @RequestHeader(TenantConstants.TENANT_HEADER)
            String tenantId,

            @PathVariable
            Long workspaceId) {

        workspaceService.archiveWorkspace(
                workspaceId,
                tenantId);
    }

    @GetMapping("/search")
    public Page<WorkspaceResponse> searchWorkspaces(

            @RequestHeader(TenantConstants.TENANT_HEADER)
            String tenantId,

            @RequestParam
            String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return workspaceService.searchWorkspaces(
                tenantId,
                keyword,
                page,
                size);
    }

}