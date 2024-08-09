package com.example.dam.model;

import com.example.dam.enums.Format;
import com.example.dam.enums.ResourceType;
import com.example.dam.enums.Type;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Asset {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    User client;

    @Column(nullable = false, unique = true)
    String publicId;

    int width;
    int height;

    @Enumerated(EnumType.STRING)
    Format format;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ResourceType resourceType;

    @Column(nullable = false)
    @CreatedDate
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;

    @Column(nullable = false)
    long size;

    @Column(nullable = false)
    Type type;

    boolean placeholder;
    String url;
    String thumbnailUrl;
    @ManyToOne
    Folder folder;

    String displayName;

    String originalFilename;
}
