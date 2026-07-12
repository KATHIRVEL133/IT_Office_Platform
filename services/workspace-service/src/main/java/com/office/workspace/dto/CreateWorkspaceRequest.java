package com.office.workspace.dto;

import lombok.Data;


@Data
public class CreateWorkspaceRequest {

    private String name;
    private String description;
}