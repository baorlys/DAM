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
    @ManyToOne
    @JoinColumn(nullable = false)
    Tenant tenant;

    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    Space space;

    @ManyToOne
    Folder parent;

    @Column(nullable = false)
    String name;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    LocalDateTime updatedAt = LocalDateTime.now();

    boolean isDeleted = false;

    public Folder(Space space, Folder parent, String name) {
        this.id = UUID.randomUUID();
        this.space = space;
        this.parent = parent;
        this.name = name;
    }
}