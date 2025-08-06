package com.tickets.eventservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response object for an event")
public record EventResponse(
    Long id,
    String title,
    String description,
    LocalDateTime startDateTime,
    LocalDateTime endDateTime,
    HallResponse hallResponse
) {
}
