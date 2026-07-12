package com.office.common.dto.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkspaceCreatedEvent {

    private Long workspaceId;
    private String workspaceName;
    private String tenantId;
}