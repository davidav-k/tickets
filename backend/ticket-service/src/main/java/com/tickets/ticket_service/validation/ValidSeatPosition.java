package com.tickets.ticket_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SeatPositionValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSeatPosition {
    String message() default "Seat position must be within hall limits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
