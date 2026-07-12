package com.office.common.dto.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketCreatedEvent {

    private Long ticketId;
    private String title;
    private String tenantId;
}