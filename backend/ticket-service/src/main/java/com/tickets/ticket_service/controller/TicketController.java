package com.tickets.ticket_service.controller;

import com.tickets.ticket_service.domain.ApiResponse;
import com.tickets.ticket_service.dto.TicketRequest;
import com.tickets.ticket_service.dto.TicketResponse;
import com.tickets.ticket_service.service.TicketService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @Schema(description = "Request to create a new ticket")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER')")
    public ResponseEntity<ApiResponse<TicketResponse>> createTicket(
            @Valid @RequestBody TicketRequest request
    ) {

        TicketResponse ticket = ticketService.createTicket(request);
        return ResponseEntity.ok(
                ApiResponse.success("/api/tickets", "Ticket created successfully", ticket)
        );
    }

    @Schema(description = "Request to get a ticket by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER')")
    public ResponseEntity<ApiResponse<TicketResponse>> getTicket(@PathVariable Long id, JwtAuthenticationToken jwtToken) {

        TicketResponse ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(
                ApiResponse.success("/api/tickets/" + id, "Ticket found", ticket)
        );
    }

    @Schema(description = "Request to get all tickets by user ID with pagination")
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER') or #userId == authentication.principal.id")
    public ResponseEntity<ApiResponse<Page<TicketResponse>>> getTicketsByUserId(
            @PathVariable String userId,
            Pageable pageable
    ) {
        Page<TicketResponse> tickets = ticketService.getTicketsByUserId(userId, pageable);
        return ResponseEntity.ok(
                ApiResponse.success("/api/tickets/user/" + userId, "Tickets found for user", tickets)
        );
    }

    @Schema(description = "Request to get all tickets by event ID with pagination")
    @GetMapping("/event/{eventId}")
    public ResponseEntity<ApiResponse<Page<TicketResponse>>> getTicketsByEventId(
            @PathVariable Long eventId,
            Pageable pageable
    ) {
        Page<TicketResponse> tickets = ticketService.getTicketsByEventId(eventId, pageable);
        return ResponseEntity.ok(
                ApiResponse.success("/api/tickets/event/" + eventId, "Tickets found for event", tickets)
        );
    }
}
