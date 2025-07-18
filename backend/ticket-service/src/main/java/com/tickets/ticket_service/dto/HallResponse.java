package com.tickets.ticket_service.dto;

public record HallResponse(
        Long id,
        String name,
        int totalRows,
        int totalSeatsPerRow
) {
}
