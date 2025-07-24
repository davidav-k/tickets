package com.tickets.eventservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/** Security configuration for the Ticket Service application.
 * This configuration sets up security filters, JWT authentication, and role-based access control.
 * It allows public access to actuator endpoints, API documentation, and Swagger UI,
 * while securing all other endpoints.
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    private Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return converter;
    }

    @Bean
    public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();

        return jwt -> {
            Collection<GrantedAuthority> defaultAuthorities = defaultConverter.convert(jwt);

            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            if (resourceAccess == null) return defaultAuthorities;

            Map<String, Object> apiGateway = (Map<String, Object>) resourceAccess.get("api-gateway");
            if (apiGateway == null) return defaultAuthorities;

            Collection<String> roles = (Collection<String>) apiGateway.get("roles");
            if (roles == null) return defaultAuthorities;

            Collection<GrantedAuthority> keycloakAuthorities = roles.stream()
                    .map(role -> role.startsWith("ROLE_")
                            ? new SimpleGrantedAuthority(role)
                            : new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());

            defaultAuthorities.addAll(keycloakAuthorities);
            return defaultAuthorities;
        };
    }
}
