package com.tickets.ticket_service.dto;

import java.util.UUID;

public record UserDTO(
    String username,
    String email,
    String firstName,
    String lastName
) {}
