package com.tickets.ticket_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class WebClientConfig {

    @Value(value = "${gateway.url}")
    private String gatewayUrl;

    @Bean
    public WebClient eventServiceClient(WebClient.Builder builder) {
        return builder
                .baseUrl(gatewayUrl)
                .filter((request, next) -> {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication != null) {
                        Object details = authentication.getDetails();
                        if (details instanceof Map) {
                            Map<String, String> detailsMap = (Map<String, String>) details;
                            String userId = detailsMap.get("userId");

                            ClientRequest newRequest = ClientRequest.from(request)
                                    .header("X-User-Id", userId)
                                    .header("X-Username", authentication.getName())
                                    .header("X-Roles", authentication.getAuthorities().stream()
                                            .map(GrantedAuthority::getAuthority)
                                            .collect(Collectors.joining(",")))
                                    .build();
                            return next.exchange(newRequest);
                        }
                    }
                    return next.exchange(request);
                })
                .build();
    }
}
