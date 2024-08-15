package com.example.dam.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Credential {
    @ManyToOne
    @JoinColumn(nullable = false)
    Tenant tenant;


    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    User user;

    @Column(nullable = false, unique = true)
    String apiKey;

    @Column(nullable = false)
    String secretKey;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    LocalDateTime updatedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Status status;

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}