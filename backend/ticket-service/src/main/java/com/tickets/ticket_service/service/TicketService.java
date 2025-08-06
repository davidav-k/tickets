package com.tickets.ticket_service.service;


import com.tickets.ticket_service.dto.TicketRequest;
import com.tickets.ticket_service.dto.TicketResponse;
import com.tickets.ticket_service.entity.Ticket;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * TicketService interface defines the contract for ticket-related operations.
 * It includes methods for creating, retrieving, and deleting tickets.
 */

public interface TicketService {


    TicketResponse createTicket(@Valid TicketRequest request);

    Ticket getTicketById(Long id);

    TicketResponse getTicketResponseById(Long id);

    Page<TicketResponse> getTicketsByUserId(String userId);

    Page<TicketResponse> getTicketsByEventId(Long eventId);

    void deleteTicket(Long id);


}
