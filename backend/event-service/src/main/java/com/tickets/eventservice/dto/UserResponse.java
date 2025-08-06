package com.tickets.eventservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object for a user")
public record UserResponse(
    String username,
    String email,
    String firstName,
    String lastName
) {}
