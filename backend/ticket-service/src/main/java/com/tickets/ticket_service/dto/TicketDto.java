package com.tickets.ticket_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketDto {

    private Long id;
    private Long eventId;
    private Long seatId;
    private String purchaserName;
    private String purchaserEmail;
    private String purchaserKeycloakId;
    private String status;
    private LocalDateTime purchaseDate;

}
