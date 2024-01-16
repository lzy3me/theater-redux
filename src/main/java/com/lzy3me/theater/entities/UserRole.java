package com.lzy3me.theater.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class UserRole {
    @Id
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    private Role role;

    private Date createdAt;
    private UUID updatedBy;
    private Date updatedAt;
}
