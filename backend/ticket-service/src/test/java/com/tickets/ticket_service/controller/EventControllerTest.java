package com.tickets.ticket_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickets.ticket_service.dto.EventResponse;
import com.tickets.ticket_service.service.EventService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EventService eventService;


    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllEvents() throws Exception {
        EventResponse event1 = new EventResponse(1L, "Concert","desc con", LocalDateTime.now(), LocalDateTime.now(),1L);
        EventResponse event2 = new EventResponse(2L, "Theater", "desc theater",LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1),1L);
        Page<EventResponse> eventsPage = new PageImpl<>(List.of(event1, event2));

        when(eventService.getAllEvents()).thenReturn(eventsPage);

        mockMvc.perform(get("/tickets/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content[0].id").value(1L))
                .andExpect(jsonPath("$.data.content[0].title").value("Concert"))
                .andExpect(jsonPath("$.data.content[1].id").value(2L))
                .andExpect(jsonPath("$.data.content[1].title").value("Theater"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetEventById() throws Exception {
        EventResponse event = new EventResponse(1L, "Concert", "desc con", LocalDateTime.now(), LocalDateTime.now(), 1L);

        when(eventService.getEventResponseById(1L)).thenReturn(event);

        mockMvc.perform(get("/tickets/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.title").value("Concert"))
                .andExpect(jsonPath("$.data.description").value("desc con"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetEventByIdNotFound() throws Exception {
        when(eventService.getEventResponseById(999L)).thenThrow(new EntityNotFoundException("Event not found with id: 999"));

        mockMvc.perform(get("/tickets/events/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Event not found with id: 999"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllEventsEmpty() throws Exception {
        Page<EventResponse> emptyPage = new PageImpl<>(List.of());

        when(eventService.getAllEvents()).thenReturn(emptyPage);

        mockMvc.perform(get("/tickets/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isEmpty());
    }

    @Test
    void testGetAllEventsUnauthorized() throws Exception {
        mockMvc.perform(get("/tickets/events"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllEventsWithMockUser() throws Exception {
        EventResponse event1 = new EventResponse(1L, "Concert", "desc con", LocalDateTime.now(), LocalDateTime.now(), 1L);
        EventResponse event2 = new EventResponse(2L, "Theater", "desc theater", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1), 1L);
        Page<EventResponse> eventsPage = new PageImpl<>(List.of(event1, event2));

        when(eventService.getAllEvents()).thenReturn(eventsPage);

        mockMvc.perform(get("/tickets/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content[0].id").value(1L))
                .andExpect(jsonPath("$.data.content[0].title").value("Concert"))
                .andExpect(jsonPath("$.data.content[1].id").value(2L))
                .andExpect(jsonPath("$.data.content[1].title").value("Theater"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetEventByIdWithMockUser() throws Exception {
        EventResponse event = new EventResponse(1L, "Concert", "desc con", LocalDateTime.now(), LocalDateTime.now(), 1L);

        when(eventService.getEventResponseById(1L)).thenReturn(event);

        mockMvc.perform(get("/tickets/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.title").value("Concert"))
                .andExpect(jsonPath("$.data.description").value("desc con"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetEventByIdNotFoundWithMockUser() throws Exception {
        when(eventService.getEventResponseById(999L)).thenThrow(new EntityNotFoundException("Event not found with id: 999"));

        mockMvc.perform(get("/tickets/events/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Event not found with id: 999"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllEventsEmptyWithMockUser() throws Exception {
        Page<EventResponse> emptyPage = new PageImpl<>(List.of());

        when(eventService.getAllEvents()).thenReturn(emptyPage);

        mockMvc.perform(get("/tickets/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isEmpty());
    }

    @Test
    void testGetAllEventsUnauthorizedWithMockUser() throws Exception {
        mockMvc.perform(get("/tickets/events"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllEventsWithMockUserAndEmptyResponse() throws Exception {
        Page<EventResponse> emptyPage = new PageImpl<>(List.of());

        when(eventService.getAllEvents()).thenReturn(emptyPage);

        mockMvc.perform(get("/tickets/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetEventByIdWithMockUserAndEmptyResponse() throws Exception {
        EventResponse event = new EventResponse(1L, "Concert", "desc con", LocalDateTime.now(), LocalDateTime.now(), 1L);

        when(eventService.getEventResponseById(1L)).thenReturn(event);

        mockMvc.perform(get("/tickets/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.title").value("Concert"))
                .andExpect(jsonPath("$.data.description").value("desc con"));
    }


}