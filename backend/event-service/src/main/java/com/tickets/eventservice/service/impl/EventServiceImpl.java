package com.tickets.eventservice.service.impl;


import com.tickets.eventservice.dto.EventRequest;
import com.tickets.eventservice.dto.EventResponse;
import com.tickets.eventservice.dto.mapper.EventMapper;
import com.tickets.eventservice.entity.Event;
import com.tickets.eventservice.entity.Hall;
import com.tickets.eventservice.repository.EventRepository;
import com.tickets.eventservice.service.EventService;
import com.tickets.eventservice.service.HallService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.tickets.eventservice.domain.Constant.DEFAULT_PAGE_NUMBER;
import static com.tickets.eventservice.domain.Constant.DEFAULT_PAGE_SIZE;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {


    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final HallService hallService;

    @Override
    public Page<EventResponse> getAllEvents() {
        Pageable pageable = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        return eventRepository.findAll(pageable)
                .map(eventMapper::toDto);

    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
    }

    @Override
    public EventResponse getEventResponseById(Long id) {
        Event event = getEventById(id);
        EventResponse dto = eventMapper.toDto(event);
        return dto;
    }


    @Override
    public EventResponse saveEvent(EventRequest eventRequest) {

        Event existingEvent = eventMapper.toEntity(eventRequest);
        Hall hall = hallService.getHallById(eventRequest.hallId());
        existingEvent.setHall(hall);

        Event savedEvent = eventRepository.save(
                eventMapper.toEntity(eventRequest));

        return eventMapper.toDto(savedEvent);
    }

    @Override
    public EventResponse updateEvent(Long id, EventRequest eventRequest) {
        Event existingEvent = getEventById(id);

        eventMapper.updateEntityFromDto(eventRequest, existingEvent);

        Hall hall = hallService.getHallById(eventRequest.hallId());
        existingEvent.setHall(hall);
        Event updatedEvent = eventRepository.save(existingEvent);

        return eventMapper.toDto(updatedEvent);
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));

        eventRepository.delete(event);

    }
}
