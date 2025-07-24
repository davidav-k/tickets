package com.tickets.ticket_service.entity;

import com.tickets.ticket_service.domain.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets", indexes = {
        @Index(name = "idx_ticket_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket extends BaseEntity{

    private Long eventId;

    private int row;
    private int seat;

    private LocalDateTime purchaseDate;
    private String holderFullName;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

}


