package com.tickets.eventservice.service.impl;

import com.tickets.eventservice.dto.EventResponse;
import com.tickets.eventservice.mapper.EventMapper;
import com.tickets.eventservice.repository.EventRepository;
import com.tickets.eventservice.service.EventService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream().map(eventMapper::toDto).toList();
    }

    @Override
    public EventResponse getById(Long id) {
        return eventRepository.findById(id).map(eventMapper::toDto).orElseThrow();
    }
}