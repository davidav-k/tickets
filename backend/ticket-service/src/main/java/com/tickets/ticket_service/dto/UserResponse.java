package com.tickets.ticket_service.dto;


public record UserResponse(
    String username,
    String email,
    String firstName,
    String lastName
) {}
