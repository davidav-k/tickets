package com.tickets.ticket_service.service.impl;

import com.tickets.ticket_service.domain.TicketStatus;
import com.tickets.ticket_service.dto.TicketRequest;
import com.tickets.ticket_service.dto.TicketResponse;
import com.tickets.ticket_service.entity.Event;
import com.tickets.ticket_service.entity.Seat;
import com.tickets.ticket_service.entity.Ticket;
import com.tickets.ticket_service.entity.LocalUser;
import com.tickets.ticket_service.exception.TicketServiceException;
import com.tickets.ticket_service.repository.TicketRepository;
import com.tickets.ticket_service.service.EventService;
import com.tickets.ticket_service.service.LocalUserService;
import com.tickets.ticket_service.service.SeatService;
import com.tickets.ticket_service.service.TicketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final EventService eventService;
    private final SeatService seatService;
    private final LocalUserService localUserService;

    @Override
    @Transactional
    public TicketResponse createTicket(TicketRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "anonymous";

        if (authentication instanceof JwtAuthenticationToken jwtToken) {
            userId = jwtToken.getName();
        } else {
            log.warn("Authentication is not a JwtAuthenticationToken");
        }

        if (userId.equals(NULL_USER_ID) || userId.equals(DEFAULT_USER_ID) || userId.isEmpty()) {
            throw new TicketServiceException("The user is not authenticated");
        }
        Event event = eventService.getEventById(request.eventId());
        Seat seat = seatService.createSeat(
                event.getHall().getId(),
                request.row(),
                request.seat()
        );
        LocalUser user = localUserService.getUserByKeycloakId(userId);


        Ticket ticket = Ticket.builder()
                .event(event)
                .seat(seat)
                .user(user)
                .status(TicketStatus.ACTIVE)
                .purchaseDate(LocalDateTime.now())

                .build();

        Ticket savedTicket = ticketRepository.save(ticket);

        return mapToTicketResponse(savedTicket);
    }

    @Override
    public TicketResponse getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));

        return mapToTicketResponse(ticket);
    }

    @Override
    public Page<TicketResponse> getTicketsByUserId(String userId, Pageable pageable) {
        return ticketRepository.findByUserId(userId, pageable)
                .map(this::mapToTicketResponse);
    }

    @Override
    public Page<TicketResponse> getTicketsByEventId(Long eventId, Pageable pageable) {
        return ticketRepository.findByEventId(eventId, pageable)
                .map(this::mapToTicketResponse);
    }

    @Override
    @Transactional
    public void deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));

        ticketRepository.delete(ticket);
    }

    private TicketResponse mapToTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .eventId(ticket.getEvent().getId())
                .eventTitle(ticket.getEvent().getTitle())
                .hallId(ticket.getEvent().getHall().getId())
                .hallName(ticket.getEvent().getHall().getName())
                .eventTime(ticket.getEvent().getStartDateTime())
                .purchaseDate(ticket.getPurchaseDate())
                .row(ticket.getSeat().getRowNumber())
                .seat(ticket.getSeat().getSeatNumber())
                .holderFullName(ticket.getUser().getFirstName() + " " + ticket.getUser().getLastName())
                .ticketStatus(ticket.getStatus().name())
                .build();

    }
}