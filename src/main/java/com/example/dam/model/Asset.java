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
@Builder
public class Asset {
    @ManyToOne
    @JoinColumn(nullable = false)
    Tenant tenant;


    @Id
    @GeneratedValue
    UUID id;

    String publicId;

    @ManyToOne
    Space space;

    @ManyToOne
    Folder folder;

    @Column(nullable = false)
    String displayName;

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

    boolean isDeleted = false;

    public Asset(Space space, Folder folder, String displayName, String filePath, String metadata) {
        this.id = UUID.randomUUID();
        this.space = space;
        this.folder = folder;
        this.displayName = displayName;
        this.filePath = filePath;
        this.metadata = metadata;
    }


}
