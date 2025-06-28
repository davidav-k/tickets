package com.tickets.ticket_service.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response object for a ticket")
public record TicketResponse(
        UUID id,
        UUID eventId,
        String eventTitle,
        UUID hallId,
        String hallName,
        LocalDateTime eventTime,
        LocalDateTime purchaseDate,
        int row,
        int seat,
        String holderFullName,
        String ticketStatus
) {}
