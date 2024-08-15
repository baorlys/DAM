package com.example.dam.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @ManyToOne
    @JoinColumn(nullable = false)
    Tenant tenant;

    @Id
    @GeneratedValue
    UUID id;

    @Column(nullable = false)
    String name;
}
