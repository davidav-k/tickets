package com.tickets.eventservice.dto;

import java.time.LocalDateTime;


public record EventResponse(Long id, String title, String description, LocalDateTime eventTime, String hallName) {
}