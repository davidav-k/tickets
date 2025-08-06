package com.tickets.eventservice.validation;

import com.tickets.eventservice.dto.EventRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class EventDateRangeValidator implements ConstraintValidator<ValidEventDateRange, EventRequest> {

    @Override
    public boolean isValid(EventRequest request, ConstraintValidatorContext context) {
        if (request.startDateTime() == null || request.endDateTime() == null) {
            return false;
        }

        boolean isValid = true;
        if (!request.startDateTime().isBefore(request.endDateTime())) {
            context.buildConstraintViolationWithTemplate("startDateTime must be before endDateTime")
                    .addPropertyNode("startDateTime")
                    .addConstraintViolation();
            isValid = false;
        }

        if (!request.startDateTime().isAfter(LocalDateTime.now())) {
            context.buildConstraintViolationWithTemplate("startDateTime must be in the future")
                    .addPropertyNode("startDateTime")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
