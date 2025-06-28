package com.tickets.ticket_service.service;


import com.tickets.ticket_service.domain.CreateTicketRequest;
import com.tickets.ticket_service.domain.TicketResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.UUID;

public interface TicketService {


    TicketResponse createTicket(@Valid CreateTicketRequest request, Principal principal);

    TicketResponse getTicketById(UUID id);

    Page<TicketResponse> getTicketsByUserId(UUID userId, Pageable pageable);

    Page<TicketResponse> getTicketsByEventId(UUID eventId, Pageable pageable);

}
