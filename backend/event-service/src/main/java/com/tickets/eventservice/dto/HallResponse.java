package com.tickets.eventservice.dto;

public record HallResponse(
        Long id,
        String name,
        int totalRows,
        int totalSeatsPerRow
) {
}
