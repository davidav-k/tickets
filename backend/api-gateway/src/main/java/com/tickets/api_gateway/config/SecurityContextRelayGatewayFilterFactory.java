package com.tickets.api_gateway.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class SecurityContextRelayGatewayFilterFactory extends AbstractGatewayFilterFactory<SecurityContextRelayGatewayFilterFactory.Config> {

    public SecurityContextRelayGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        if (config.getGatewaySecret() == null || config.getGatewaySecret().isEmpty()) {
            log.error("Gateway secret is not configured");
            throw new IllegalStateException("Gateway secret must be configured");
        }
        return (exchange, chain) -> {
            log.info("Applying SecurityContextRelayFilter with gatewaySecret: {}", config.getGatewaySecret());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthenticationToken jwtAuth) {
                String userId = jwtAuth.getToken().getSubject();
                String username = jwtAuth.getToken().getClaimAsString("preferred_username");
                String email = jwtAuth.getToken().getClaimAsString("email");
                String roles = jwtAuth.getAuthorities().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(","));
                log.debug("Relaying headers: userId={}, username={}, roles={}", userId, username, roles);
                exchange.getRequest().mutate()
                        .header("X-User-Id", userId)
                        .header("X-Username", username)
                        .header("X-Email", email)
                        .header("X-Roles", roles)
                        .header("X-Gateway-Secret", config.getGatewaySecret())
                        .build();
            } else {
                log.warn("No valid JWT authentication found");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    @Setter
    @Getter
    public static class Config {
        private String gatewaySecret;

    }
}

