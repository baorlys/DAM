package com.example.dam.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    UUID id;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    LocalDateTime createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    LocalDateTime updatedAt;


}
