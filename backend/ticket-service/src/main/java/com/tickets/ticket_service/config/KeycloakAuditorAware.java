package com.tickets.ticket_service.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;
import java.util.UUID;

/**
 * AuditorAware implementation that retrieves the current auditor from the Keycloak authentication context.
 * This class is used to automatically populate createdBy and updatedBy fields in JPA entities.
 */

public class KeycloakAuditorAware implements AuditorAware<UUID> {

    @Override
    public @NotNull Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        if (authentication instanceof JwtAuthenticationToken token) {
            String subject = token.getToken().getSubject();
            return Optional.of(UUID.fromString(subject));
        }

        return Optional.empty();
    }
}
