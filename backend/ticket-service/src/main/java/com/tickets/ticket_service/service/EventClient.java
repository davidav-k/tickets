package com.tickets.ticket_service.service;

import com.tickets.ticket_service.dto.EventResponse;

public interface EventClient {

    EventResponse getEventById(Long eventId);
}
