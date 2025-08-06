package com.tickets.eventservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EventDateRangeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEventDateRange {
    String message() default "startDateTime must be before endDateTime and in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}