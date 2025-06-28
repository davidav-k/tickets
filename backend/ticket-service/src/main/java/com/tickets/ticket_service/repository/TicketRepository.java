package com.tickets.ticket_service.repository;

import com.tickets.ticket_service.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Page<Ticket> findByUserId(UUID uuid, Pageable pageable);

    Page<Ticket> findByEventId(UUID eventId, Pageable pageable);

}
