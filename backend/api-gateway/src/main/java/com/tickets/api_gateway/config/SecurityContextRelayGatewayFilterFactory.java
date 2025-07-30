package com.tickets.api_gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SecurityContextRelayGatewayFilterFactory extends AbstractGatewayFilterFactory<SecurityContextRelayGatewayFilterFactory.Config> {

    public SecurityContextRelayGatewayFilterFactory() {
        super(Config.class);
    }

@Override
public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .filter(Objects::nonNull)
            .map(authentication -> {
                if (authentication instanceof JwtAuthenticationToken jwtAuth) {
                    Jwt jwt = jwtAuth.getToken();
                    String userId = jwt.getSubject();
                    String username = jwt.getClaim("preferred_username");

                    // Extract actual roles from realm_access.roles
                    String roles = "";
                    try {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
                        if (realmAccess != null) {
                            @SuppressWarnings("unchecked")
                            List<String> rolesList = (List<String>) realmAccess.get("roles");
                            if (rolesList != null) {
                                roles = rolesList.stream()
                                    .collect(Collectors.joining(","));
                            }
                        }
                    } catch (Exception e) {
                        roles = jwtAuth.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(","));
                    }

                    String finalRoles = roles;

                    log.info("Setting security headers: X-User-Id={}, X-Username={}, X-Roles={}",
                              userId, username, finalRoles);

                    return exchange.mutate()
                            .request(r -> r.headers(headers -> {
                                headers.add("X-User-Id", userId);
                                headers.add("X-Username", username);
                                headers.add("X-Roles", finalRoles);
                            }))
                            .build();
                }
                return exchange;
            })
            .switchIfEmpty(Mono.just(exchange))
            .flatMap(chain::filter);
    };
}

    public static class Config {
        // Configuration properties if needed
    }
}
