package com.tickets.ticket_service.service;

import com.tickets.ticket_service.dto.EventRequest;
import com.tickets.ticket_service.dto.EventResponse;
import org.springframework.data.domain.Page;

/**
 * Service interface for managing events.
 * Provides methods to retrieve, create, and delete events.
 */

public interface EventService {

    Page<EventResponse> getAllEvents();

    EventResponse getEventById(Long id);

    EventResponse saveEvent(EventRequest eventRequest);

    void deleteEvent(Long id);
}
