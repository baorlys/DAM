package com.example.dam.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Folder {
    @Id
    UUID id;

    @ManyToOne
    @JoinColumn( nullable = false)
    Space space;

    @ManyToOne
    Folder parent;

    @Column(nullable = false)
    String name;

    @Column(updatable = false, nullable = false)
    @CreatedDate
    LocalDateTime createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    LocalDateTime updatedAt;
}