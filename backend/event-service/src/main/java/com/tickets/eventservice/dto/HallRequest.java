package com.tickets.eventservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record HallRequest(
        @NotBlank String name,
        @Positive  int totalRows,
        @Positive int totalSeatsPerRow
) {
}
