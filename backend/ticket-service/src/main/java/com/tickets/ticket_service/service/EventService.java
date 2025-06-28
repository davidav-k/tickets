package com.tickets.ticket_service.service;

import com.tickets.ticket_service.dto.EventDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {

    Page<EventDto> getAllEvents(Pageable pageable);

    EventDto getEventById(Long id);

    EventDto saveEvent(EventDto dto);

    void deleteEvent(Long id);
}
