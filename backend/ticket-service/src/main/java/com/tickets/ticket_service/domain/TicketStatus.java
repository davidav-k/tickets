package com.tickets.ticket_service.domain;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Enumeration representing the status of a ticket")
public enum TicketStatus {
    ACTIVE, CANCELLED, USED, RESERVED;
}
