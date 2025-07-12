package com.tickets.ticket_service.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateHallRequest(
        @NotBlank String name,
        @NotNull int totalRows,
        @NotNull int totalSeatsPerRow
) {
}
