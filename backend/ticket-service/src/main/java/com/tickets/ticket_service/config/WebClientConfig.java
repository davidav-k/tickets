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
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8092")
                .filter((request, next) -> {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    if (auth != null) {
                        Map<String, Object> details = (Map<String, Object>) auth.getDetails();
                        String userId = (String) details.get("userId");
                        String username = auth.getPrincipal().toString();
                        String roles = String.join(",", auth.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()));
                        return next.exchange(ClientRequest.from(request)
                                .header("X-User-Id", userId)
                                .header("X-Username", username)
                                .header("X-Roles", roles)
                                .build());
                    }
                    return next.exchange(request);
                })
                .build();
    }
}
