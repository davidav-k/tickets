package com.tickets.ticket_service.service;

import com.tickets.ticket_service.dto.SeatRequest;
import com.tickets.ticket_service.dto.SeatResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing seats in a hall.
 * Provides methods to retrieve, create, and delete seats.
 */

public interface SeatService {

    Page<SeatResponse> getAllSeatsByHall(Long hallId, Pageable pageable);

    SeatResponse getSeatById(Long id);

    SeatResponse createSeat(SeatRequest seatRequest);

    void deleteSeat(Long id);
}
