package com.tickets.ticket_service.service.impl;

import com.tickets.ticket_service.dto.CreateHallRequest;
import com.tickets.ticket_service.dto.HallResponse;
import com.tickets.ticket_service.dto.UserResponse;
import com.tickets.ticket_service.entity.Hall;
import com.tickets.ticket_service.entity.User;
import com.tickets.ticket_service.repository.HallRepository;
import com.tickets.ticket_service.repository.UserRepository;
import com.tickets.ticket_service.service.HallService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void deleteHall(Long id) {

        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hall not found"));

        hallRepository.delete(hall);

    }

    @Override
    public Page<HallResponse> getAllHalls() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Hall> halls = hallRepository.findAll(pageable);
        return halls.map(hall -> new HallResponse(
                hall.getId(),
                hall.getName(),
                hall.getTotalRows(),
                hall.getTotalSeatsPerRow()
        ));

    }

    @Override
    public HallResponse getHallById(Long id) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hall not found"));

        return new HallResponse(
                hall.getId(),
                hall.getName(),
                hall.getTotalRows(),
                hall.getTotalSeatsPerRow()
        );
    }

    @Override
    public UserResponse getHallCreator(Long hallId) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new EntityNotFoundException("Hall not found"));

        if (hall.getCreatedBy() == null) {
            return null;
        }

        User user = userRepository.findByKeycloakId(hall.getCreatedBy())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return new UserResponse(
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }


}
