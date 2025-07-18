package com.tickets.ticket_service.dto;

public record SeatResponse(
        Long id,
        int rowNumber,
        int seatNumber,
        Long hallId
) {}
