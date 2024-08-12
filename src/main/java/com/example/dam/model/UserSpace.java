package com.example.dam.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserSpaceId.class)
public class UserSpace {
    @Id
    UUID userId;

    @Id
    UUID spaceId;

    @ManyToOne
    @JoinColumn(nullable = false)
    Role role;

    @Column(updatable = false, nullable = false)
    @CreatedDate
    LocalDateTime joinedAt;
}