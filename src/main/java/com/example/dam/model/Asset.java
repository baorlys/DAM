package com.example.dam.model;

import com.example.dam.enums.Format;
import com.example.dam.enums.ResourceType;
import com.example.dam.enums.Type;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Asset {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false, unique = true)
    private String publicId;

    @Column(nullable = false)
    private String signature;

    private int width;
    private int height;
    private Format format;

    @Column(nullable = false)
    private ResourceType resourceType;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private long bytes;

    @Column(nullable = false)
    private Type type;

    private boolean placeholder;
    private String url;
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;
    private String displayName;
    private String originalFilename;
}
