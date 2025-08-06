package com.tickets.eventservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = HallDimensionsValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidHallDimensions {
    String message() default "totalRows and totalSeatsPerRow must be positive and within reasonable limits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
