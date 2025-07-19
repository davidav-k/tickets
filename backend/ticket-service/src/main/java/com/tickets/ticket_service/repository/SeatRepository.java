package com.tickets.ticket_service.repository;

import com.tickets.ticket_service.entity.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    Page<Seat> findByHallId(Long hallId, Pageable pageable);

    boolean existsByHallIdAndRowNumberAndSeatNumber(Long hallId, int rowNumber, int seatNumber);

}
