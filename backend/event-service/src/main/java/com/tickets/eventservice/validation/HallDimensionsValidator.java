package com.tickets.eventservice.validation;

import com.tickets.eventservice.dto.HallRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HallDimensionsValidator implements ConstraintValidator<ValidHallDimensions, HallRequest> {

    private static final int MAX_ROWS = 100; // Максимальное количество рядов
    private static final int MAX_SEATS_PER_ROW = 100; // Максимальное количество мест в ряду

    @Override
    public boolean isValid(HallRequest request, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (request.totalRows() > MAX_ROWS) {
            context.buildConstraintViolationWithTemplate(
                            "totalRows must be between 1 and " + MAX_ROWS)
                    .addPropertyNode("totalRows")
                    .addConstraintViolation();
            isValid = false;
        }

        if (request.totalSeatsPerRow() > MAX_SEATS_PER_ROW) {
            context.buildConstraintViolationWithTemplate(
                            "totalSeatsPerRow must be between 1 and " + MAX_SEATS_PER_ROW)
                    .addPropertyNode("totalSeatsPerRow")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
