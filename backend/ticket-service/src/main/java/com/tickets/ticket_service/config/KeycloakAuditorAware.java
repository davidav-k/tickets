package com.tickets.ticket_service.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/** KeycloakAuditorAware implementation for Spring Data JPA auditing.
 * This class retrieves the current authenticated user's ID from Keycloak
 * and provides it as the auditor for auditing purposes.
 */


@Component
public class KeycloakAuditorAware implements AuditorAware<String> {

    @Override
    public @NotNull Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
            authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        if (authentication instanceof JwtAuthenticationToken) {
            return Optional.of(authentication.getName());
        }

        return Optional.empty();
    }
}
