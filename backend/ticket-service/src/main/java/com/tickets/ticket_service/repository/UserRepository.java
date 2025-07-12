package com.tickets.ticket_service.repository;

import com.tickets.ticket_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    /**
     * Finds a user by their Keycloak ID.
     *
     * @param keycloakId the Keycloak ID of the user
     * @return an Optional containing the User if found, or empty if not found
     */

    Optional<User> findByKeycloakId(String keycloakId);

    boolean existsByKeycloakId(String keycloakId);

}
