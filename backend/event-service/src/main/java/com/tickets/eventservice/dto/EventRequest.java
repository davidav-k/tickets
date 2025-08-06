package com.tickets.eventservice.dto;

import com.tickets.eventservice.validation.ValidEventDateRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Request to create a new event")
@ValidEventDateRange
public record EventRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull LocalDateTime startDateTime,
        @NotNull LocalDateTime endDateTime,
        @NotNull Long hallId
) {
}
