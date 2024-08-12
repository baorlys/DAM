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
public class Asset {
    @Id
    UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    Space space;

    @ManyToOne
    Folder folder;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String filePath;

    String metadata;

    @Column(updatable = false)
    @CreatedDate
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;

    public Asset(UUID id, Space space, Folder folder, String name, String filePath, String metadata) {
        this.id = id;
        this.space = space;
        this.folder = folder;
        this.name = name;
        this.filePath = filePath;
        this.metadata = metadata;
    }

    public Asset(Space space, Folder folder, String name, String filePath, String metadata) {
        this.id = UUID.randomUUID();
        this.space = space;
        this.folder = folder;
        this.name = name;
        this.filePath = filePath;
        this.metadata = metadata;
    }
}
