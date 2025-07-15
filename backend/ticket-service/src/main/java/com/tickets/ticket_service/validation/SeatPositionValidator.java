package com.tickets.ticket_service.validation;

import com.tickets.ticket_service.entity.Seat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SeatPositionValidator implements ConstraintValidator<ValidSeatPosition, Seat> {
    @Override
    public boolean isValid(Seat seat, ConstraintValidatorContext context) {
        if (seat == null || seat.getHall() == null) {
            return true;
        }

        return seat.getRowNumber() <= seat.getHall().getTotalRows() &&
                seat.getSeatNumber() <= seat.getHall().getTotalSeatsPerRow();
    }
}
