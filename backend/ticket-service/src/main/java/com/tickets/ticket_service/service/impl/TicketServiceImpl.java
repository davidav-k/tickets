package com.tickets.ticket_service.service.impl;

import com.tickets.ticket_service.domain.CreateTicketRequest;
import com.tickets.ticket_service.domain.TicketResponse;
import com.tickets.ticket_service.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;



// Temporary stub for TicketServiceImpl,
// until the logic for working
// with Keycloak and the database is implemented
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    @Override
    public TicketResponse createTicket(CreateTicketRequest request, Principal principal) {
        // Тут нужно получить userId из токена Keycloak
        UUID userId = UUID.randomUUID(); // временно
        return new TicketResponse(
                UUID.randomUUID(),
                request.eventId(),
                "Metallica Live",
                request.hallId(),
                "Main Hall",
                java.time.LocalDateTime.of(2025, 8, 1, 20, 0),
                java.time.LocalDateTime.now(),
                request.row(),
                request.seat(),
                request.firstName() + " " + request.lastName(),
                "ACTIVE"
        );
    }

    @Override
    public TicketResponse getTicketById(UUID id) {
        return new TicketResponse(
                id,
                UUID.randomUUID(),
                "Metallica Live",
                UUID.randomUUID(),
                "Main Hall",
                java.time.LocalDateTime.of(2025, 8, 1, 20, 0),
                java.time.LocalDateTime.now(),
                3,
                15,
                "Ivan Ivanov",
                "ACTIVE"
        );
    }

    @Override
    public Page<TicketResponse> getTicketsByUserId(UUID userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<TicketResponse> getTicketsByEventId(UUID eventId, Pageable pageable) {
        return null;
    }

}


//    private final TicketRepository ticketRepository;
//    private final TicketMapper ticketMapper;
//    private final EventRepository eventRepository;
//    private final SeatRepository seatRepository;
//
//    @Override
//    public Page<TicketDto> getAllTickets(Pageable pageable) {
//        return ticketRepository.findAll(pageable)
//                .map(ticketMapper::toDto);
//    }
//
//    @Override
//    public TicketResponse getTicketById(Long id) {
//        Ticket ticket = ticketRepository.findById(id).orElseThrow(
//                () -> new TicketServiceException("Ticket not found"));
//        return null;
//    }
//
//    @Override
//    public Page<TicketDto> getTicketsByUserId(String id, Pageable pageable) {
//        return ticketRepository.findByPurchaserKeycloakId(id, pageable)
//                .map(ticketMapper::toDto);
//    }
//
//    @Override
//    public Page<TicketDto> getTicketsByEventId(Long eventId, Pageable pageable) {
//        return ticketRepository.findByEventId(eventId, pageable)
//                .map(ticketMapper::toDto);
//    }
//
//    @Override
//    public TicketResponse saveTicket(CreateTicketRequest request) {
//        Ticket ticket = ticketMapper.toEntity(request);
//
//        ticket.setEvent(eventRepository.findById(request.getEventId())
//                .orElseThrow(() -> new TicketServiceException("Event not found")));
//
//        if (request.getSeatId() != null) {
//            ticket.setSeat(seatRepository.findById(request.getSeatId())
//                    .orElseThrow(() -> new TicketServiceException("Seat not found")));
//        }
//
//        Ticket saved = ticketRepository.save(ticket);
//        return ticketMapper.toDto(saved);
//    }
//
//    @Override
//    public void deleteTicket(Long id) {
//        ticketRepository.deleteById(id);
//    }
//
//    @Override
//    public TicketResponse createTicket(CreateTicketRequest request, Principal principal) {
//        return null;
//    }

