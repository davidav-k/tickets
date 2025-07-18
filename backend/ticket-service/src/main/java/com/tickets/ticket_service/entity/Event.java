package com.tickets.ticket_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events", indexes = {
        @Index(name = "idx_event_start_date_time", columnList = "startDateTime")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event extends BaseEntity {


    private String title;
    private String description;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @ManyToOne(optional = false)
    private Hall hall;

    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets;
}
