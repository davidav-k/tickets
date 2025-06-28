package com.tickets.ticket_service.repository;

import com.tickets.ticket_service.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {


}
