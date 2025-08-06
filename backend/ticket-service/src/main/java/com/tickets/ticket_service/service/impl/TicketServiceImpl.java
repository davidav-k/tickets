package com.tickets.ticket_service.service.impl;

import com.tickets.ticket_service.domain.TicketStatus;
import com.tickets.ticket_service.dto.EventResponse;
import com.tickets.ticket_service.dto.TicketRequest;
import com.tickets.ticket_service.dto.TicketResponse;
import com.tickets.ticket_service.dto.mapper.TicketMapper;
import com.tickets.ticket_service.entity.Ticket;
import com.tickets.ticket_service.exception.TicketServiceException;
import com.tickets.ticket_service.repository.TicketRepository;
import com.tickets.ticket_service.service.EventClient;
import com.tickets.ticket_service.service.TicketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.tickets.ticket_service.domain.Constant.DEFAULT_PAGE_NUMBER;
import static com.tickets.ticket_service.domain.Constant.DEFAULT_PAGE_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final EventClient eventClient;
    private final TicketMapper ticketMapper;

    @Override
    @Transactional
    public TicketResponse createTicket(TicketRequest request) {
        EventResponse eventResponse = eventClient.getEventById(request.eventId());

        if (eventResponse == null) {
            throw new TicketServiceException("Event with ID " + request.eventId() + " not found.");
        }

        int totalRows = eventResponse.hallResponse().totalRows();
        int totalSeatsPerRow = eventResponse.hallResponse().totalSeatsPerRow();
        if (request.row() < 1 || request.row() > totalRows) {
            throw new TicketServiceException("Row number " + request.row() + " is invalid. Must be between 1 and " + totalRows);
        }
        if (request.seat() < 1 || request.seat() > totalSeatsPerRow) {
            throw new TicketServiceException("Seat number " + request.seat() + " is invalid. Must be between 1 and " + totalSeatsPerRow);
        }


        boolean exists = ticketRepository.existsByEventIdAndRowAndSeatAndStatus(
                request.eventId(),
                request.row(),
                request.seat(),
                TicketStatus.ACTIVE
        );

if (exists) {
    throw new TicketServiceException(
            MessageFormatter.arrayFormat(
                    "Ticket already exists for eventId={}, row={}, seat={}",
                    new Object[]{request.eventId(), request.row(), request.seat()}
            ).getMessage()
    );
}

        Ticket ticket = Ticket.builder()
                .eventId(eventResponse.id())
                .row(request.row())
                .seat(request.seat())
                .purchaseDate(LocalDateTime.now())
                .holderFullName(request.firstName() + " " + request.lastName())
                .status(TicketStatus.ACTIVE)
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);

        return ticketMapper.toDto(savedTicket, eventResponse);
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));
    }


    @Override
    public TicketResponse getTicketResponseById(Long id) {
        Ticket ticket = getTicketById(id);
        EventResponse eventResponse = eventClient.getEventById(ticket.getEventId());

        return ticketMapper.toDto(ticket, eventResponse);
    }

    @Override
    public Page<TicketResponse> getTicketsByUserId(String userId) {
        Pageable pageable = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        return ticketRepository.findByHolderFullNameContaining(userId, pageable)
                .map(ticket -> {
                    EventResponse eventResponse = eventClient.getEventById(ticket.getEventId());
                    return ticketMapper.toDto(ticket, eventResponse);
                });

    }

    @Override
    public Page<TicketResponse> getTicketsByEventId(Long eventId) {
        Pageable pageable = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        return ticketRepository.findByEventId(eventId, pageable)
                .map(ticket -> {
                    EventResponse eventResponse = eventClient.getEventById(ticket.getEventId());
                    return ticketMapper.toDto(ticket, eventResponse);
                });

    }

    @Override
    @Transactional
    public void deleteTicket(Long id) {
        Ticket ticket = getTicketById(id);

        ticketRepository.delete(ticket);
    }
}