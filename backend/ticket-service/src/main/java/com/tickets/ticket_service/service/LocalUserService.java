package com.tickets.ticket_service.service;

import com.tickets.ticket_service.entity.LocalUser;

public interface LocalUserService {

    LocalUser getUserByKeycloakId(String userId);
}
