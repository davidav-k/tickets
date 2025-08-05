package com.tickets.ticket_service.service.impl;

import com.tickets.ticket_service.entity.LocalUser;
import com.tickets.ticket_service.repository.LocalUserRepository;
import com.tickets.ticket_service.service.UserSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSyncServiceImpl implements UserSyncService {

    private final LocalUserRepository localUserRepository;

    @Override
    public void syncUserFromHeaders(String userId, String username, String email) {
        localUserRepository.findByKeycloakId(userId)
                .orElseGet(() -> LocalUser.builder()
                        .keycloakId(userId)
                        .username(username)
                        .email(email)
                        .build());
    }
}
