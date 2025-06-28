package com.tickets.ticket_service.repository;

import com.tickets.ticket_service.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
