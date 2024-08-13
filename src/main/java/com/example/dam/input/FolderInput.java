package com.example.dam.input;

import lombok.Data;

import java.util.UUID;
@Data
public class FolderInput {
    UUID userId;
    String folderName;
    UUID spaceId;
    UUID parentId = null;
}
