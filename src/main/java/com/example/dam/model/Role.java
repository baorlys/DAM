package com.example.dam.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @ManyToOne
    @JoinColumn(nullable = false)
    Tenant tenant;

    @Id
    @GeneratedValue
    UUID id;

    @Column(nullable = false)
    String name;
}
