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

    @ManyToOne(optional = false)
    private Event event;

    @ManyToOne
    private Seat seat;

    @Column(nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private LocalDateTime purchaseDate;
}


