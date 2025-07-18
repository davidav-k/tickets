package com.tickets.ticket_service.controller;

import com.tickets.ticket_service.domain.ApiResponse;
import com.tickets.ticket_service.dto.EventRequest;
import com.tickets.ticket_service.dto.EventResponse;
import com.tickets.ticket_service.service.EventService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/tickets/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @Schema(description = "Request to create a new event")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        log.info("Creating a new event: {}", eventRequest.title());
        EventResponse eventResponse = eventService.saveEvent(eventRequest);
        return ResponseEntity.ok(
                ApiResponse.success("/api/events", "Event created successfully", eventResponse)
        );
    }

    @Schema(description = "Request to get all events")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER') or hasRole('USER')")
    public ResponseEntity<ApiResponse<Page<EventResponse>>> getAllEvents() {
        log.info("Fetching all events");
        Page<EventResponse> eventsResponse = eventService.getAllEvents();
        return ResponseEntity.ok(
                ApiResponse.success("/api/events", "All events retrieved successfully", eventsResponse)
        );
    }

    @Schema(description = "Request to get events by hall ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER') or hasRole('USER')")
    public ResponseEntity<ApiResponse<EventResponse>> getEventById(@PathVariable Long id) {
        log.info("Fetching event with ID: {}", id);
        EventResponse eventResponse = eventService.getEventResponseById(id);
        return ResponseEntity.ok(
                ApiResponse.success("/api/events/" + id, "Event retrieved successfully", eventResponse)
        );
    }

    @Schema(description = "Request to update an event")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteEvent(@PathVariable Long id) {
        log.info("Deleting event with ID: {}", id);
        eventService.deleteEvent(id);
        return ResponseEntity.ok(
                ApiResponse.success("/api/events/" + id, "Event deleted successfully", "Event with ID " + id + " has been deleted")
        );
    }

}
