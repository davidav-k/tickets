package com.tickets.eventservice.controller;

import com.tickets.eventservice.dto.EventResponse;
import com.tickets.eventservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;


    @GetMapping
    public List<EventResponse> getAll() {
        return service.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }
}