package com.tickets.ticket_service.service.impl;

import com.tickets.ticket_service.dto.SeatResponse;
import com.tickets.ticket_service.entity.Seat;
import com.tickets.ticket_service.repository.HallRepository;
import com.tickets.ticket_service.repository.SeatRepository;
import com.tickets.ticket_service.service.HallService;
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
    private final HallService hallService;

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
    public Seat createSeat(Long hallId, int rowNumber, int seatNumber) {
        if(seatRepository.existsByHallIdAndRowNumberAndSeatNumber(hallId, rowNumber, seatNumber)) {
            throw new IllegalArgumentException("Seat already exists in hall with id: " + hallId + ", row: " + rowNumber + ", seat: " + seatNumber);
        }

        Seat seat = Seat.builder()
                .rowNumber(rowNumber)
                .seatNumber(seatNumber)
                .hall(hallService.getHallById(hallId))
                .build();

        return seatRepository.save(seat);
    }

    @Override
    public void deleteSeat(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + id));

        seatRepository.delete(seat);

    }

    @Override
    public boolean existsByHallIdAndRowAndSeat(Long hallId, Integer row, Integer seat) {

        return seatRepository.existsByHallIdAndRowNumberAndSeatNumber(hallId, row, seat);
    }

}
