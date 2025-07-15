package com.tickets.ticket_service.service;

import com.tickets.ticket_service.dto.SeatRequest;
import com.tickets.ticket_service.dto.SeatResponse;
import com.tickets.ticket_service.entity.Seat;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service interface for managing seats in a hall.
 * Provides methods to retrieve, create, and delete seats.
 */

public interface SeatService {

    Page<SeatResponse> getAllSeatsByHall(Long hallId, Pageable pageable);

    SeatResponse getSeatById(Long id);


    Seat createSeat(Long hallId, int rowNumber, int seatNumber);

    void deleteSeat(Long id);

    boolean existsByHallIdAndRowAndSeat(Long hallId, Integer rowNumber, Integer seatNumber);


}
