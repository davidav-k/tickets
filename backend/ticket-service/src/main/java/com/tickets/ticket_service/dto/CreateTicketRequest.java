package com.tickets.ticket_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to create a new ticket")
public record CreateTicketRequest(
        @NotNull Long eventId,
        @NotNull Long hallId,
        @NotNull Integer row,
        @NotNull Integer seat,
        @NotBlank String firstName,
        @NotBlank String lastName
) {}
