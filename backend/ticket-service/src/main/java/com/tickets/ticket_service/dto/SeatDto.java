package com.tickets.ticket_service.dto;

import lombok.Data;

@Data
public class SeatDto {

    private Long id;
    private int rowNumber;
    private int seatNumber;
    private Long hallId;

}
