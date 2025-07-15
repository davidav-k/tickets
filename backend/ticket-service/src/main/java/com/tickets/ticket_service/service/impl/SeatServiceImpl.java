package com.tickets.ticket_service.service.impl;

import com.tickets.ticket_service.dto.SeatRequest;
import com.tickets.ticket_service.dto.SeatResponse;
import com.tickets.ticket_service.entity.Seat;
import com.tickets.ticket_service.repository.HallRepository;
import com.tickets.ticket_service.repository.SeatRepository;
import com.tickets.ticket_service.service.SeatService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private  final SeatRepository seatRepository;
    private final HallRepository hallRepository;

    @Override
    public Page<SeatResponse> getAllSeatsByHall(Long hallId, Pageable pageable) {
        return seatRepository.findByHallId(hallId, pageable)
                .map(seat -> new SeatResponse(
                        seat.getId(),
                        seat.getRowNumber(),
                        seat.getSeatNumber(),
                        seat.getHall().getId()
                ));


    }

    @Override
    public SeatResponse getSeatById(Long id) {
        return seatRepository.findById(id)
                .map(seat -> new SeatResponse(
                        seat.getId(),
                        seat.getRowNumber(),
                        seat.getSeatNumber(),
                        seat.getHall().getId()
                ))
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + id));

    }

    @Override
    public SeatResponse createSeat(SeatRequest seatRequest) {

        Seat seat = Seat.builder()
                .rowNumber(seatRequest.rowNumber())
                .seatNumber(seatRequest.seatNumber())
                .hall(hallRepository.findById(seatRequest.hallId())
                        .orElseThrow(() -> new EntityNotFoundException("Hall not found with id: " + seatRequest.hallId())))
                .build();

        Seat savedSeat = seatRepository.save(seat);

        return new SeatResponse(
                savedSeat.getId(),
                savedSeat.getRowNumber(),
                savedSeat.getSeatNumber(),
                savedSeat.getHall().getId()
        );
    }

    @Override
    public void deleteSeat(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + id));

        seatRepository.delete(seat);

    }

}
