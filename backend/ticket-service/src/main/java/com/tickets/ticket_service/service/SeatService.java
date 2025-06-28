package com.tickets.ticket_service.service;

import com.tickets.ticket_service.dto.SeatDto;
import com.tickets.ticket_service.entity.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SeatService {

    Page<SeatDto> getAllSeats(Pageable pageable);

    SeatDto getSeatById(Long id);

    SeatDto saveSeat(SeatDto dto);

    void deleteSeat(Long id);
}
