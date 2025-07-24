package com.tickets.eventservice.config;


import com.tickets.eventservice.entity.LocalUser;
import com.tickets.eventservice.repository.LocalUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** Filter to synchronize Keycloak user information with the local database.
 * This filter checks if the user exists in the database based on their Keycloak ID,
 * and if not, creates a new user record with the information from the JWT token.
 */

@Component
@RequiredArgsConstructor
public class KeycloakUserSyncFilter extends OncePerRequestFilter {
    private final LocalUserRepository userRepository;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken token) {
            String subject = token.getToken().getSubject();

            if (subject != null) {
                userRepository.findByKeycloakId(subject)
                    .orElseGet(() -> {
                        LocalUser user = LocalUser.builder()
                            .keycloakId(subject)
                            .username(token.getToken().getClaimAsString("preferred_username"))
                            .email(token.getToken().getClaimAsString("email"))
                            .firstName(token.getToken().getClaimAsString("given_name"))
                            .lastName(token.getToken().getClaimAsString("family_name"))
                            .build();
                        return userRepository.save(user);
                    });
            }
        }

        filterChain.doFilter(request, response);
    }
}