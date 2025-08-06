package com.tickets.eventservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object for a hall")
public record HallResponse(
        Long id,
        String name,
        int totalRows,
        int totalSeatsPerRow
) {
}
