package com.office.workspace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateWorkspaceRequest {

    @NotBlank(message = "Workspace name is required")
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;
}