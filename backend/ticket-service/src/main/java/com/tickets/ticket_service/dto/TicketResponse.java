package com.tickets.ticket_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(description = "Response object for a ticket")
@Builder
public record TicketResponse(
        Long id,
        Long eventId,
        String eventTitle,
        Long hallId,
        String hallName,
        LocalDateTime eventTime,
        LocalDateTime purchaseDate,
        int row,
        int seat,
        String holderFullName,
        String ticketStatus
) {}
