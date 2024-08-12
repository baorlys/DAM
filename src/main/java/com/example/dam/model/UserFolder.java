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
@IdClass(UserFolderId.class)
public class UserFolder {
    @Id
    UUID userId;

    @Id
    UUID folderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    AccessLevel accessLevel;

    public enum AccessLevel {
        READ,
        WRITE,
        ADMIN
    }
}