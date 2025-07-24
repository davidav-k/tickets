package com.tickets.eventservice.dto;


public record UserResponse(
    String username,
    String email,
    String firstName,
    String lastName
) {}
