package com.tickets.eventservice.dto;

import java.time.LocalDateTime;

public record EventResponse(
    Long id,
    String title,
    String description,
    LocalDateTime startDateTime,
    LocalDateTime endDateTime,
    HallResponse hallResponse
) {
}
