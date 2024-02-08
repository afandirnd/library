package com.rnd4impact.identity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -5604881130195585365L;

    public enum Role { ROLE_ADMIN, ROLE_RENTAL_AGENT }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String uuid;

    @Column (nullable = false)
    @Enumerated (EnumType.STRING)
    private Role role;

    @Column (nullable = false, unique = true)
    @Email
    private String email;

    @Column (nullable = false)
    private String password;

    @PrePersist
    private void onPrePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }
}
