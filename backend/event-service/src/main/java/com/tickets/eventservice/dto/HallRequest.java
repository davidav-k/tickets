package com.tickets.eventservice.dto;


import com.tickets.eventservice.validation.ValidHallDimensions;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "Request to create a new hall")
@ValidHallDimensions
public record HallRequest(
        @NotBlank String name,
        @Positive  int totalRows,
        @Positive int totalSeatsPerRow
) {
}
