package com.tickets.ticket_service.dto;

import jakarta.validation.constraints.NotNull;

public record SeatRequest(
    @NotNull int rowNumber,
    @NotNull int seatNumber,
    @NotNull Long hallId
) {
}
