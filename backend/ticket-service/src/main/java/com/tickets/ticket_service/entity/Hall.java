package com.tickets.ticket_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "halls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hall extends BaseEntity {

    private String name;

    private int totalRows;
    private int totalSeatsPerRow;

    @OneToMany(mappedBy = "hall")
    private List<Seat> seats;

    @OneToMany(mappedBy = "hall")
    private List<Event> events;
}
