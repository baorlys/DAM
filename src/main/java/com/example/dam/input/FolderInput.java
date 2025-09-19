package com.example.dam.input;

import lombok.Data;

import java.util.UUID;

@Data
public class FolderInput {
    String folderName;
    UUID parentId = null;
}
