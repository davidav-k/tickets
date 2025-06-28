package com.tickets.ticket_service.service;


import com.tickets.ticket_service.dto.HallDto;

import java.util.List;
import java.util.Optional;

public interface HallService {

    List<HallDto> getAllHalls();

    Optional<HallDto> getHallById(Long id);

    HallDto saveHall(HallDto dto);

    void deleteHall(Long id);
}
