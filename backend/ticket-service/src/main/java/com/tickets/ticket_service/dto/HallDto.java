package com.tickets.ticket_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class HallDto {

    private UUID id;
    private String name;
    private int totalRows;
    private int totalSeatsPerRow;

}
