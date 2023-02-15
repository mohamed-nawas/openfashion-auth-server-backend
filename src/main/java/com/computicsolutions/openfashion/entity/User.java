package com.computicsolutions.openfashion.entity;

import com.computicsolutions.openfashion.dto.request.UserRegistrationRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * User entity
 */
@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {

    @Transient
    private static final String USER_ID_PREFIX = "uid-";

    @Id
    private String id;
    @Column(nullable = false)
    private String username;

    // email property is unique to each user
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    @Column
    private Date created;
    @Column
    private Date updated;

    @PrePersist
    private void onCreate() {
        created = new Date();
        updated = new Date();
    }

    @PreUpdate
    private void onUpdate() {
        updated = new Date();
    }

    public User(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.created = user.getCreated();
        this.updated = user.getUpdated();
    }

    public User(UserRegistrationRequestDto dto) {
        this.id = USER_ID_PREFIX + UUID.randomUUID();
        this.username = dto.getUsername();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.role = dto.getRole();
    }
}
