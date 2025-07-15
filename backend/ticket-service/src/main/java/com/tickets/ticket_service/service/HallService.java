package com.tickets.ticket_service.service;


import com.tickets.ticket_service.dto.CreateHallRequest;
import com.tickets.ticket_service.dto.HallResponse;
import com.tickets.ticket_service.dto.UserResponse;
import org.springframework.data.domain.Page;

/**
 * Service interface for managing halls in the ticket service.
 * Provides methods to create, delete, and retrieve hall information.
 */

public interface HallService {

    HallResponse saveHall(CreateHallRequest request);

    void deleteHall(Long id);

    Page<HallResponse> getAllHalls();

    HallResponse getHallById(Long id);

    UserResponse getHallCreator(Long hallId);





}
