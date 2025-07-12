package com.tickets.ticket_service.service;


import com.tickets.ticket_service.domain.CreateHallRequest;
import com.tickets.ticket_service.domain.HallResponse;
import com.tickets.ticket_service.dto.UserDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Service interface for managing halls in the ticket service.
 * Provides methods to create, delete, and retrieve hall information.
 */

public interface HallService {

    /**
     * Saves a new hall based on the provided request.
     *
     * @param request the request containing hall details
     * @return the saved hall response
     */
    HallResponse saveHall(CreateHallRequest request);

    void deleteHall(UUID id);

    Page<HallResponse> getAllHalls();

    HallResponse getHallById(UUID id);

    UserDTO getHallCreator(UUID hallId);





}
