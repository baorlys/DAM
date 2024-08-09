package com.example.dam.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuditLog {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    User user;

    @Column(nullable = false)
    String action;

    @ManyToOne
    private Asset asset;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    LocalDateTime timestamp;
}
