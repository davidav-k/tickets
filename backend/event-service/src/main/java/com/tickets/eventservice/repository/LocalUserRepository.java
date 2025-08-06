package com.tickets.eventservice.repository;

import com.tickets.eventservice.entity.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalUserRepository extends JpaRepository<LocalUser, Long> {
    /**
     * Finds a user by their Keycloak ID.
     *
     * @param keycloakId the Keycloak ID of the user
     * @return an Optional containing the User if found, or empty if not found
     */

    Optional<LocalUser> findByKeycloakId(String keycloakId);

    boolean existsByKeycloakId(String keycloakId);

}
