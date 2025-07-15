package com.tickets.ticket_service.service.impl;

import com.tickets.ticket_service.dto.CreateTicketRequest;
import com.tickets.ticket_service.dto.TicketResponse;
import com.tickets.ticket_service.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    @Override
    public TicketResponse createTicket(CreateTicketRequest request) {
        return null;
    }

    @Override
    public TicketResponse getTicketById(Long id) {
        return null;
    }

    @Override
    public Page<TicketResponse> getTicketsByUserId(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<TicketResponse> getTicketsByEventId(Long eventId, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteTicket(Long id) {

    }

}