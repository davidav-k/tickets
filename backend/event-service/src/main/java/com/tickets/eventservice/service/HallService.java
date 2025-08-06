package com.tickets.eventservice.service;



import com.tickets.eventservice.dto.HallRequest;
import com.tickets.eventservice.dto.HallResponse;
import com.tickets.eventservice.dto.UserResponse;
import com.tickets.eventservice.entity.Hall;
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

    HallResponse updateHall(Long id, HallRequest hallRequest);

    UserResponse getHallCreator(Long hallId);





}
