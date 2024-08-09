package com.example.dam.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Credential {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    @CreatedBy
    Tenant tenant;

    @Column(nullable = false, unique = true)
    String apiKey;

    @Column(nullable = false, unique = true)
    String apiSecret;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    LocalDateTime createdAt;

    LocalDateTime lastUsedAt;
}
