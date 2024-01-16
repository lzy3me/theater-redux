package com.lzy3me.theater.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String roleName;
    private int accessLevel;
    private UUID createdBy;
    private Date createdAt;
    private UUID updatedBy;
    private Date updatedAt;

    @OneToOne(mappedBy = "role")
    private UserRole userRole;
}
