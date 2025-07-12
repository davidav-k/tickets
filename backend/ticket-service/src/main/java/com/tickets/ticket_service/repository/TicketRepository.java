package com.tickets.ticket_service.repository;

import com.tickets.ticket_service.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    Page<Ticket> findByUserId(UUID userId, Pageable pageable);

    Page<Ticket> findByEventId(UUID eventId, Pageable pageable);

}