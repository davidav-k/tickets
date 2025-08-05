package com.tickets.eventservice.service;

public interface UserSyncService {

    void syncUserFromHeaders(String userId, String username, String email);

}
