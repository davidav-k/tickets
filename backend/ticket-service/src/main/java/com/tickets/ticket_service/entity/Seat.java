package com.tickets.ticket_service.entity;

import com.tickets.ticket_service.validation.ValidSeatPosition;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "seats",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_hall_row_seat",
                        columnNames = {"hall_id", "rowNumber", "seatNumber"}
                )
        })
@ValidSeatPosition
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat extends BaseEntity {

    @Positive(message = "Row number must be positive")
    private int rowNumber;

    @Positive(message = "Seat number must be positive")
    private int seatNumber;

    @ManyToOne
    private Hall hall;
}
