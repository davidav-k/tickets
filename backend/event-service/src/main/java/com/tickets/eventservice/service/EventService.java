package com.tickets.eventservice.service;


import com.tickets.eventservice.dto.EventRequest;
import com.tickets.eventservice.dto.EventResponse;
import com.tickets.eventservice.entity.Event;
import org.springframework.data.domain.Page;

/**
 * Service interface for managing events.
 * Provides methods to retrieve, create, and delete events.
 */

public interface EventService {

    Page<EventResponse> getAllEvents();

    EventResponse getEventResponseById(Long id);

    Event getEventById(Long id);

    EventResponse saveEvent(EventRequest eventRequest);

    EventResponse updateEvent(Long id, EventRequest eventRequest);

    void deleteEvent(Long id);
}
