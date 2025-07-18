package com.tickets.ticket_service.service.impl;

import com.tickets.ticket_service.dto.EventRequest;
import com.tickets.ticket_service.dto.EventResponse;
import com.tickets.ticket_service.entity.Event;
import com.tickets.ticket_service.repository.EventRepository;
import com.tickets.ticket_service.repository.HallRepository;
import com.tickets.ticket_service.service.EventService;
import com.tickets.ticket_service.service.HallService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private  final HallService hallService;

    @Override
    public Page<EventResponse> getAllEvents() {
        Pageable pageable = PageRequest.of(0, 10);
        return eventRepository.findAll(pageable)
                .map(event -> new EventResponse(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getStartDateTime(),
                        event.getEndDateTime(),
                        event.getHall().getId()
                ));
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
    }
    @Override
    public EventResponse getEventResponseById(Long id) {
        Event event = getEventById(id);
        return  new EventResponse(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getStartDateTime(),
                        event.getEndDateTime(),
                        event.getHall().getId());
    }


    @Override
    public EventResponse saveEvent(EventRequest eventRequest) {

        Event event = Event.builder()
                .title(eventRequest.title())
                .description(eventRequest.description())
                .startDateTime(eventRequest.startDateTime())
                .endDateTime(eventRequest.endDateTime())
                .hall(hallService.getHallById(eventRequest.hallId()))
                .build();

        Event savedEvent = eventRepository.save(event);

        return new EventResponse(
                savedEvent.getId(),
                savedEvent.getTitle(),
                savedEvent.getDescription(),
                savedEvent.getStartDateTime(),
                savedEvent.getEndDateTime(),
                savedEvent.getHall().getId()
        );
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));

        eventRepository.delete(event);

    }
}
