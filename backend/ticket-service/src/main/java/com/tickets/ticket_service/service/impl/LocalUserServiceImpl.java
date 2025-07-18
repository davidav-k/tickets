package com.tickets.ticket_service.service.impl;

import com.tickets.ticket_service.entity.LocalUser;
import com.tickets.ticket_service.repository.LocalUserRepository;
import com.tickets.ticket_service.service.LocalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalUserServiceImpl implements LocalUserService {

    private final LocalUserRepository localUserRepository;

    @Override
    public LocalUser getUserByKeycloakId(String userId) {
        return localUserRepository.findByKeycloakId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with Keycloak ID: " + userId));

    }
}
