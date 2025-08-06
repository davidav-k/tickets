package com.tickets.ticket_service.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;



@Component
public class KeycloakAuditorAware implements AuditorAware<String> {
    @Override
    public @NotNull Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getDetails() instanceof Map<?, ?> details) {
                return Optional.ofNullable((String) details.get("userId"));
            }
        }
        return Optional.empty();
    }
}
