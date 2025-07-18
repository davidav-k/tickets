package com.tickets.ticket_service.service;


import com.tickets.ticket_service.dto.HallRequest;
import com.tickets.ticket_service.dto.HallResponse;
import com.tickets.ticket_service.dto.UserResponse;
import com.tickets.ticket_service.entity.Hall;
import org.springframework.data.domain.Page;

/**
 * Service interface for managing halls in the ticket service.
 * Provides methods to create, delete, and retrieve hall information.
 */

public interface HallService {

    HallResponse saveHall(HallRequest request);

    void deleteHall(Long id);

    Page<HallResponse> getAllHallsResponse();

    HallResponse getHallResponseById(Long id);

    Hall getHallById(Long id);

    UserResponse getHallCreator(Long hallId);





}
