package com.example.dam.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserFolder {
    @ManyToOne
    @JoinColumn(nullable = false)
    Tenant tenant;

    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    User user;

    @ManyToOne
    Folder folder;

    @ManyToOne
    @JoinColumn(nullable = false)
    Role role;


}