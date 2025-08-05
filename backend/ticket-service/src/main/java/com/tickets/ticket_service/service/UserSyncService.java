package com.tickets.ticket_service.service;

public interface UserSyncService {

    void syncUserFromHeaders(String userId, String username, String email);

}
