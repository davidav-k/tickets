package com.tickets.ticket_service.service.impl;

import com.tickets.ticket_service.domain.ApiResponse;
import com.tickets.ticket_service.dto.EventResponse;
import com.tickets.ticket_service.service.EventClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventClientImpl implements EventClient {

    private final WebClient eventServiceClient;

    @Value("${gateway.secret}")
    private String gatewaySecret;

    @Override
    public EventResponse getEventById(Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> details = (Map<String, Object>) authentication.getDetails();

        return eventServiceClient.get()
                .uri("/events/{id}", eventId)
                .header("X-Gateway-Secret", gatewaySecret)
                .header("X-User-Id", (String) details.get("userId"))
                .header("X-Username", authentication.getName())
                .header("X-Email", (String) details.get("email"))
                .header("X-Roles", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<EventResponse>>() {})
                .map(ApiResponse::data)
                .block();
    }
}
