package com.tickets.ticket_service.repository;

import com.tickets.ticket_service.domain.TicketStatus;
import com.tickets.ticket_service.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {


    Page<Ticket> findByEventId(Long eventId, Pageable pageable);

    boolean existsByEventIdAndRowAndSeatAndStatus(Long eventId,
                                                  int row,
                                                  int seat,
                                                  TicketStatus status);


    Page<Ticket> findByHolderFullNameContaining(String userId, Pageable pageable);
}