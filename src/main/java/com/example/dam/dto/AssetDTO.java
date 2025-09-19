package com.example.dam.dto;

import com.example.dam.enums.ExtensionFile;
import com.example.dam.enums.ResourceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AssetDTO {
    UUID id;
    String publicId;
    String displayName;
    ResourceType type;
    ExtensionFile extensionFile;
    long size;
    String originPath;
    String filePath;
    String thumbnailUrl;
    String originalFilename;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
