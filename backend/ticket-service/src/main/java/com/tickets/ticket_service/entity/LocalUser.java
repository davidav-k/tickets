package com.tickets.ticket_service.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocalUser extends BaseEntity{

    @Column(name = "keycloak_id", nullable = false, unique = true)
    private String keycloakId;

    private String username;
    private String email;
    private String firstName;
    private String lastName;

}
