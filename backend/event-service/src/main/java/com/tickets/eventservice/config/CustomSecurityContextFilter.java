package com.tickets.eventservice.config;

import com.tickets.eventservice.service.UserSyncService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Slf4j
@RequiredArgsConstructor
public class CustomSecurityContextFilter extends OncePerRequestFilter {

    private final UserSyncService userSyncService;

    @Value("${gateway.secret}")
    String GATEWAY_SECRET;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        log.info("gateway secret: {} vs {}", GATEWAY_SECRET, request.getHeader("X-Gateway-Secret"));
        String gatewaySecret = request.getHeader("X-Gateway-Secret");
        if (!GATEWAY_SECRET.equals(gatewaySecret)) {
            log.error("Invalid gateway secret: {}", gatewaySecret);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String userId = request.getHeader("X-User-Id");
        String username = request.getHeader("X-Username");
        String email = request.getHeader("X-Email");
        String roles = request.getHeader("X-Roles");

        log.debug("Received X-Roles header: '{}'", roles);

        if (userId != null && username != null && roles != null) {
            List<SimpleGrantedAuthority> authorities = Arrays.stream(roles.split(","))
                    .filter(role -> role != null && !role.trim().isEmpty())
                    .map(role -> {
                        String cleanRole = role.trim();
                        log.debug("Processing role: '{}'", cleanRole);
                        return new SimpleGrantedAuthority(cleanRole);
                    })
                    .collect(Collectors.toList());

            log.debug("Created authorities: {}", authorities);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username, null, authorities);
            auth.setDetails(Map.of("userId", userId, "email", email != null ? email : ""));
            SecurityContextHolder.getContext().setAuthentication(auth);
            userSyncService.syncUserFromHeaders(userId, username, email);
        } else {
            log.warn("Missing required headers: userId={}, username={}, roles={}", userId, username, roles);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
