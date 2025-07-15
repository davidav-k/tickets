package com.tickets.ticket_service.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HallRequest(
        @NotBlank String name,
        @NotNull int totalRows,
        @NotNull int totalSeatsPerRow
) {
}
