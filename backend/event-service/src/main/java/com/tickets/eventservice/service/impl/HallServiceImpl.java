package com.tickets.eventservice.service.impl;


import com.tickets.eventservice.dto.HallRequest;
import com.tickets.eventservice.dto.HallResponse;
import com.tickets.eventservice.dto.UserResponse;
import com.tickets.eventservice.dto.mapper.HallMapper;
import com.tickets.eventservice.dto.mapper.LocalUserMapper;
import com.tickets.eventservice.entity.Hall;
import com.tickets.eventservice.entity.LocalUser;
import com.tickets.eventservice.repository.HallRepository;
import com.tickets.eventservice.repository.LocalUserRepository;
import com.tickets.eventservice.service.HallService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tickets.eventservice.domain.Constant.*;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallMapper hallMapper;
    private final LocalUserMapper userMapper;
    private final HallRepository hallRepository;
    private final LocalUserRepository userRepository;




    @Override
    @Transactional
    public HallResponse saveHall(HallRequest request) {

        Hall savedHall = hallRepository.save(hallMapper.toEntity(request));

        return hallMapper.toDto(savedHall);
    }

    @Override
    public void deleteHall(Long id) {

        Hall hall = getHallById(id);

        hallRepository.delete(hall);
    }

    @Override
    public Page<HallResponse> getAllHallsResponse() {
        Pageable pageable = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        Page<Hall> halls = hallRepository.findAll(pageable);
        return halls.map(hallMapper::toDto);
    }

    @Override
    public Hall getHallById(Long id) {
        return hallRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hall not found with id: " + id));
    }

    @Override
    public HallResponse getHallResponseById(Long id) {

        Hall hall = getHallById(id);

        return hallMapper.toDto(hall);
    }

    @Override
    public HallResponse updateHall(Long id, HallRequest hallRequest) {
        Hall hall = getHallById(id);
        hallMapper.updateEntityFromDto(hallRequest, hall);
        Hall updatedHall = hallRepository.save(hall);
        return hallMapper.toDto(updatedHall);
    }



    @Override
    public UserResponse getHallCreator(Long hallId) {
        Hall hall = getHallById(hallId);

        if (hall.getCreatedBy() == null) {
            return null;
        }

        LocalUser user = userRepository.findByKeycloakId(hall.getCreatedBy())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + hall.getCreatedBy()));

        return userMapper.toDto(user);
    }


}
