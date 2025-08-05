package com.tickets.api_gateway.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SecurityContextRelayGatewayFilterFactory extends AbstractGatewayFilterFactory<SecurityContextRelayGatewayFilterFactory.Config> {

    public SecurityContextRelayGatewayFilterFactory() {
        super(Config.class);
    }

   @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> exchange.getPrincipal()
                .filter(principal -> principal instanceof JwtAuthenticationToken)
                .cast(JwtAuthenticationToken.class)
                .switchIfEmpty(Mono.error(new IllegalStateException("No valid JWT authentication found")))
                .flatMap(jwtAuth -> {
                    Jwt jwt = jwtAuth.getToken();
                    log.info("JWT Token claims: {}", jwt.getClaims());

                    // Extract roles manually from nested JWT structure
                    java.util.List<String> roles = new java.util.ArrayList<>();

                    // Extract from realm_access.roles
                    try {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
                        if (realmAccess != null) {
                            @SuppressWarnings("unchecked")
                            List<String> realmRoles = (List<String>) realmAccess.get("roles");
                            if (realmRoles != null) {
                                roles.addAll(realmRoles);
                            }
                        }
                    } catch (Exception e) {
                        log.warn("Error extracting realm roles", e);
                    }

                    try {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
                        if (resourceAccess != null) {
                            @SuppressWarnings("unchecked")
                            Map<String, Object> clientResource = (Map<String, Object>) resourceAccess.get("api-gateway");
                            if (clientResource != null) {
                                @SuppressWarnings("unchecked")
                                List<String> clientRoles = (List<String>) clientResource.get("roles");
                                if (clientRoles != null) {
                                    roles.addAll(clientRoles);
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.warn("Error extracting client roles", e);
                    }

                    log.info("Extracted roles: {}", roles);

                    String rolesHeader = roles.stream()
                            .map(role -> role.startsWith("ROLE_") ? role : role)
                            .peek(role -> log.info("Sending role: {}", role))
                            .collect(Collectors.joining(","));

                    log.info("Final roles header: {}", rolesHeader);

                    ServerHttpRequest request = exchange.getRequest().mutate()
                            .header("X-User-Id", jwt.getSubject())
                            .header("X-Username", jwt.getClaimAsString("preferred_username"))
                            .header("X-Email", jwt.getClaimAsString("email"))
                            .header("X-Roles", rolesHeader)
                            .header("X-Gateway-Secret", config.getGatewaySecret())
                            .build();
                    return chain.filter(exchange.mutate().request(request).build());
                })
                .onErrorResume(IllegalStateException.class, e -> {
                    log.error("Error in SecurityContextRelay: {}", e.getMessage());
                    return chain.filter(exchange);
                });
    }

    @Setter
    @Getter
    public static class Config {
        private String gatewaySecret;

    }
}

