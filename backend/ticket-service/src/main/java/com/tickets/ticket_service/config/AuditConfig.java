package com.tickets.ticket_service.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class to enable JPA auditing with Keycloak as the auditor provider.
 */

@Configuration
@EnableJpaAuditing(auditorAwareRef = "keycloakAuditorAware")
public class AuditConfig {
}
