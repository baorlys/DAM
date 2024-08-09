package com.example.dam.model;

import com.example.dam.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    Tenant tenant;

    @ManyToOne
    User client;

    @Enumerated(EnumType.STRING)
    Role role;
}
