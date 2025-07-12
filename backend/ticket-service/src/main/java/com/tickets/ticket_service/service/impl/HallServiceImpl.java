package com.tickets.ticket_service.service.impl;

import com.tickets.ticket_service.domain.CreateHallRequest;
import com.tickets.ticket_service.domain.HallResponse;
import com.tickets.ticket_service.dto.UserDTO;
import com.tickets.ticket_service.entity.Hall;
import com.tickets.ticket_service.entity.User;
import com.tickets.ticket_service.repository.HallRepository;
import com.tickets.ticket_service.repository.UserRepository;
import com.tickets.ticket_service.service.HallService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public HallResponse saveHall(CreateHallRequest request) {

        Hall hall = Hall.builder()
                .name(request.name())
                .totalRows(request.totalRows())
                .totalSeatsPerRow(request.totalSeatsPerRow())
                .build();

        Hall savedHall = hallRepository.save(hall);

        return new HallResponse(
                savedHall.getId(),
                savedHall.getName(),
                savedHall.getTotalRows(),
                savedHall.getTotalSeatsPerRow()
        );
    }

    @Override
    public void deleteHall(UUID id) {

    }

    @Override
    public Page<HallResponse> getAllHalls() {
        return null;
    }

    @Override
    public HallResponse getHallById(UUID id) {
        return null;
    }

    @Override
    public UserDTO getHallCreator(UUID hallId) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new EntityNotFoundException("Hall not found"));

        if (hall.getCreatedBy() == null) {
            return null;
        }

        User user = userRepository.findById(hall.getCreatedBy())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }


}
