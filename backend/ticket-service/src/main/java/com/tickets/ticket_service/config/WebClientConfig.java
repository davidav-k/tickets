package com.tickets.ticket_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value(value = "${gateway.url}")
    private String gatewayUrl;

    @Bean
    public WebClient eventWebClient() {
        return WebClient.builder()
                .baseUrl(gatewayUrl + "/events")
                .filter(new ServletBearerExchangeFilterFunction())
                .build();
    }
}
