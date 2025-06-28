package com.tickets.ticket_service.repository;

import com.tickets.ticket_service.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository extends JpaRepository<Hall, Long> {

}
