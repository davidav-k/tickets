package com.tickets.ticket_service.dto;

import lombok.Data;

@Data
public class HallDto {

    private Long id;
    private String name;
    private int totalRows;
    private int totalSeatsPerRow;

}
