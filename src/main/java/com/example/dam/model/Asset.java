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
    @GeneratedValue
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

    String thumbnailPath;
    String metadata;


    @CreatedDate
    @Column(updatable = false, nullable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    LocalDateTime updatedAt = LocalDateTime.now();

    public Asset(Space space, Folder folder, String name, String filePath, String metadata) {
        this.id = UUID.randomUUID();
        this.space = space;
        this.folder = folder;
        this.name = name;
        this.filePath = filePath;
        this.metadata = metadata;
    }
}
