package com.tickets.ticket_service.service;


import com.tickets.ticket_service.dto.CreateTicketRequest;
import com.tickets.ticket_service.dto.TicketResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * TicketService interface defines the contract for ticket-related operations.
 * It includes methods for creating, retrieving, and deleting tickets.
 */

public interface TicketService {


    TicketResponse createTicket(@Valid CreateTicketRequest request);

    TicketResponse getTicketById(Long id);

    Page<TicketResponse> getTicketsByUserId(Long userId, Pageable pageable);

    Page<TicketResponse> getTicketsByEventId(Long eventId, Pageable pageable);

    void deleteTicket(Long id);


}
