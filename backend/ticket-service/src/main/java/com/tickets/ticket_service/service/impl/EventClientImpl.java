package com.tickets.ticket_service.service.impl;

import com.tickets.ticket_service.domain.ApiResponse;
import com.tickets.ticket_service.dto.EventResponse;
import com.tickets.ticket_service.service.EventClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventClientImpl implements EventClient {

    private final WebClient eventWebClient;

    @Override
    public EventResponse getEventById(Long eventId) {
        log.debug("Requesting event data for ID: {}", eventId);
        return eventWebClient.get()
                .uri("/{id}", eventId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<EventResponse>>() {
                })
                .map(ApiResponse::data)
                .block(); // todo: exchange for reactive approach (async)
    }

}
