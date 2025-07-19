package com.tickets.eventservice.service;

import com.tickets.eventservice.dto.EventResponse;
import java.util.List;

public interface EventService {
    List<EventResponse> getAllEvents();
    EventResponse getById(Long id);
}