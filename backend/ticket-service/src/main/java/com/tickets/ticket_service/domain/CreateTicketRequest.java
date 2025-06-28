package com.tickets.ticket_service.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Request to create a new ticket")
public record CreateTicketRequest(
        @NotNull UUID eventId,
        @NotNull UUID hallId,
        @NotNull Integer row,
        @NotNull Integer seat,
        @NotBlank String firstName,
        @NotBlank String lastName
) {}
