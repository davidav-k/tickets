package com.tickets.ticket_service.repository;

import com.tickets.ticket_service.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Page<Ticket> findByUserId(String userId, Pageable pageable);

    Page<Ticket> findByEventId(Long eventId, Pageable pageable);

}