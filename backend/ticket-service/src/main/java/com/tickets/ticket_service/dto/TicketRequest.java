package com.tickets.ticket_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to create a new ticket")
public record TicketRequest(
        @NotNull Long eventId,
        @NotNull int row,
        @NotNull int seat,
        @NotBlank String firstName,
        @NotBlank String lastName
) {}
