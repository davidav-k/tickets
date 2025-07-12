package com.tickets.ticket_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

/**
 * Configuration class for enabling JPA auditing.
 * This class sets up the auditor provider to use Keycloak's authentication context
 * to automatically populate createdBy and updatedBy fields in entities.
 */

@Configuration
@EnableJpaAuditing(
        auditorAwareRef = "auditorProvider")
public class AuditConfig {

    @Bean
    public AuditorAware<UUID> auditorProvider() {
        return new KeycloakAuditorAware();
    }

}
