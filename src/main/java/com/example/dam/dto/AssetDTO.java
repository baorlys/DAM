package com.example.dam.dto;

import com.example.dam.enums.Format;
import com.example.dam.enums.ResourceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AssetDTO {
    String id;
    String name;
    ResourceType type;
    Format format;
    long size;
    String filePath;
    String thumbnailUrl;
    String displayName;
    String originalFilename;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
