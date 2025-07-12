package com.tickets.ticket_service.domain;

import java.util.UUID;

public record HallResponse(
        UUID id,
        String name,
        int totalRows,
        int totalSeatsPerRow
) {
}
