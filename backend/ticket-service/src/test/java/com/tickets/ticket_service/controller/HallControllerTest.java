package com.tickets.ticket_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickets.ticket_service.dto.HallRequest;
import com.tickets.ticket_service.dto.HallResponse;
import com.tickets.ticket_service.dto.UserResponse;
import com.tickets.ticket_service.service.HallService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HallControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HallService hallService;

    @Test
    void testCreateHall() throws Exception {

        HallRequest request = new HallRequest("Concert Hall", 500, 25);
        HallResponse response = new HallResponse(1L, "Concert Hall", 500, 25);
        when(hallService.saveHall(request)).thenReturn(response);

        MvcResult result = mockMvc.perform(post("/tickets/halls")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print()) // Вывод логов для отладки
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Concert Hall"))
                .andExpect(jsonPath("$.data.totalRows").value(500))
                .andExpect(jsonPath("$.data.totalSeatsPerRow").value(25))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);

        verify(hallService).saveHall(request);
    }

    @Test
    void testCreateHallWithoutAdminRole() throws Exception {
        HallRequest request = new HallRequest("Conference Room", 200, 20);

        mockMvc.perform(post("/tickets/halls")
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateHallWithInvalidData() throws Exception {
        HallRequest request = new HallRequest("", -1, 0);

        mockMvc.perform(post("/tickets/halls")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value(containsString("name: must not be blank")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateHallWithNullValues() throws Exception {
        HallRequest request = new HallRequest(null, 0, 0);

        mockMvc.perform(post("/tickets/halls")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value(containsString("name: must not be blank")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateHallWithNegativeRows() throws Exception {
        HallRequest request = new HallRequest("Test Hall", -5, 20);

        mockMvc.perform(post("/tickets/halls")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value(containsString("totalRows: must be greater than")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateHallWithNegativeSeatsPerRow() throws Exception {
        HallRequest request = new HallRequest("Test Hall", 10, -5);

        mockMvc.perform(post("/tickets/halls")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value(containsString("totalSeatsPerRow: must be greater than")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetHallByIdWithoutAdminOrCashierRole() throws Exception {
        Long hallId = 1L;

        mockMvc.perform(get("/tickets/halls/{id}", hallId)
                        .with(user("user").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetHallById() throws Exception {
        Long hallId = 1L;
        HallResponse response = new HallResponse(hallId, "Test Hall", 10, 20);
        when(hallService.getHallResponseById(hallId)).thenReturn(response);

        mockMvc.perform(get("/tickets/halls/{id}", hallId)
                        .with(user("admin").roles("ADMIN")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(hallId))
                .andExpect(jsonPath("$.data.name").value("Test Hall"))
                .andExpect(jsonPath("$.data.totalRows").value(10))
                .andExpect(jsonPath("$.data.totalSeatsPerRow").value(20));

        verify(hallService).getHallResponseById(hallId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllHalls() throws Exception {

        Page<HallResponse> hallsPage = new PageImpl<>(List.of(
                new HallResponse(1L, "Hall 1", 100, 10),
                new HallResponse(2L, "Hall 2", 200, 20)
        ));

        when(hallService.getAllHallsResponse()).thenReturn(hallsPage);

        mockMvc.perform(get("/tickets/halls")
                        .with(user("admin").roles("ADMIN")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.content[0].id").value(1L))
                .andExpect(jsonPath("$.data.content[0].name").value("Hall 1"))
                .andExpect(jsonPath("$.data.content[0].totalRows").value(100))
                .andExpect(jsonPath("$.data.content[0].totalSeatsPerRow").value(10))
                .andExpect(jsonPath("$.data.content[1].id").value(2L))
                .andExpect(jsonPath("$.data.content[1].name").value("Hall 2"))
                .andExpect(jsonPath("$.data.content[1].totalRows").value(200))
                .andExpect(jsonPath("$.data.content[1].totalSeatsPerRow").value(20));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteHall() throws Exception {
        Long hallId = 1L;

        mockMvc.perform(delete("/tickets/halls/{id}", hallId)
                        .with(user("admin").roles("ADMIN")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Hall deleted successfully"));

        verify(hallService).deleteHall(hallId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteHallWithoutAdminRole() throws Exception {
        Long hallId = 1L;

        mockMvc.perform(delete("/tickets/halls/{id}", hallId)
                        .with(user("user").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}

