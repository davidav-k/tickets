package com.tickets.ticket_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickets.ticket_service.domain.TicketStatus;
import com.tickets.ticket_service.dto.TicketRequest;
import com.tickets.ticket_service.dto.TicketResponse;
import com.tickets.ticket_service.service.TicketService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        // Здесь можно добавить общие настройки для тестов, если нужно
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateTicket() throws Exception {
        TicketRequest request = new TicketRequest(1L, 1, 1, "John", "Doe");
        TicketResponse response = new TicketResponse(1L, 1L, "Concert Hall", 1L, "Main Hall",
                LocalDateTime.now(), LocalDateTime.now(), 1, 1, "John Doe", TicketStatus.ACTIVE.toString());

        when(ticketService.createTicket(request)).thenReturn(response);

        mockMvc.perform(post("/tickets")
                        .with(user("admin").roles("ADMIN"))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.eventId").value(1L))
                .andExpect(jsonPath("$.data.eventTitle").value("Concert Hall"))
                .andExpect(jsonPath("$.data.hallId").value(1L))
                .andExpect(jsonPath("$.data.hallName").value("Main Hall"))
                .andExpect(jsonPath("$.data.ticketStatus").value("ACTIVE"));
    }

    @Test
    @WithMockUser(roles = "CASHIER")
    void testCreateTicketAsCashier() throws Exception {
        TicketRequest request = new TicketRequest(1L, 1, 1, "Jane", "Doe");
        TicketResponse response = new TicketResponse(2L, 1L, "Concert Hall", 1L, "Main Hall",
                LocalDateTime.now(), LocalDateTime.now(), 2, 2, "Jane Doe", TicketStatus.ACTIVE.toString());

        when(ticketService.createTicket(request)).thenReturn(response);

        mockMvc.perform(post("/tickets")
                        .with(user("cashier").roles("CASHIER"))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(2L))
                .andExpect(jsonPath("$.data.eventId").value(1L))
                .andExpect(jsonPath("$.data.eventTitle").value("Concert Hall"))
                .andExpect(jsonPath("$.data.hallId").value(1L))
                .andExpect(jsonPath("$.data.hallName").value("Main Hall"))
                .andExpect(jsonPath("$.data.ticketStatus").value("ACTIVE"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testCreateTicketAsUser() throws Exception {
        TicketRequest request = new TicketRequest(1L, 1, 1, "Alice", "Smith");

        mockMvc.perform(post("/tickets")
                        .with(user("user").roles("USER"))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateTicketWithInvalidData() throws Exception {
        TicketRequest request = new TicketRequest(null, 1, 1, "John", "Doe"); // Missing eventId

        mockMvc.perform(post("/tickets")
                        .with(user("admin").roles("ADMIN"))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("eventId: must not be null"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetTicketById() throws Exception {
        TicketResponse response = new TicketResponse(1L, 1L, "Concert Hall", 1L, "Main Hall",
                LocalDateTime.now(), LocalDateTime.now(), 1, 1, "John Doe", TicketStatus.ACTIVE.toString());

        when(ticketService.getTicketById(1L)).thenReturn(response);

        mockMvc.perform(get("/tickets/1")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.eventId").value(1L))
                .andExpect(jsonPath("$.data.eventTitle").value("Concert Hall"))
                .andExpect(jsonPath("$.data.hallId").value(1L))
                .andExpect(jsonPath("$.data.hallName").value("Main Hall"))
                .andExpect(jsonPath("$.data.ticketStatus").value("ACTIVE"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetTicketByIdNotFound() throws Exception {
        when(ticketService.getTicketById(999L)).thenThrow(new EntityNotFoundException("Ticket not found with id: 999"));

        mockMvc.perform(get("/tickets/999")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Ticket not found with id: 999"));
    }

    @Test
    void testGetTicketByIdUnauthorized() throws Exception {
        mockMvc.perform(get("/tickets/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetTicketsByUserId() throws Exception {
        TicketResponse response = new TicketResponse(1L, 1L, "Concert Hall", 1L, "Main Hall",
                LocalDateTime.now(), LocalDateTime.now(), 1, 1, "John Doe", TicketStatus.ACTIVE.toString());
        Page<TicketResponse> page = new PageImpl<>(List.of(response));

        when(ticketService.getTicketsByUserId(any(String.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/tickets/user/1")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(1L))
                .andExpect(jsonPath("$.data.content[0].eventId").value(1L))
                .andExpect(jsonPath("$.data.content[0].eventTitle").value("Concert Hall"))
                .andExpect(jsonPath("$.data.content[0].hallId").value(1L))
                .andExpect(jsonPath("$.data.content[0].hallName").value("Main Hall"))
                .andExpect(jsonPath("$.data.content[0].ticketStatus").value("ACTIVE"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetTicketsByUserIdNotFound() throws Exception {
        when(ticketService.getTicketsByUserId(eq("999"), any())).thenThrow(
                new EntityNotFoundException("No tickets found for user with id: 999")
        );
        mockMvc.perform(get("/tickets/user/999")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No tickets found for user with id: 999"));
    }

    @Test
    void testGetTicketsByUserIdUnauthorized() throws Exception {
        mockMvc.perform(get("/tickets/user/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetTicketsByEventId() throws Exception {
        TicketResponse response = new TicketResponse(1L, 1L, "Concert Hall", 1L, "Main Hall",
                LocalDateTime.now(), LocalDateTime.now(), 1, 1, "John Doe", TicketStatus.ACTIVE.toString());
        Page<TicketResponse> page = new PageImpl<>(List.of(response));

        when(ticketService.getTicketsByEventId(any(Long.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/tickets/event/1")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(1L))
                .andExpect(jsonPath("$.data.content[0].eventId").value(1L))
                .andExpect(jsonPath("$.data.content[0].eventTitle").value("Concert Hall"))
                .andExpect(jsonPath("$.data.content[0].hallId").value(1L))
                .andExpect(jsonPath("$.data.content[0].hallName").value("Main Hall"))
                .andExpect(jsonPath("$.data.content[0].ticketStatus").value("ACTIVE"));
    }


}