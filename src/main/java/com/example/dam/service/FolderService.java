package com.example.dam.service;

import com.example.dam.model.Folder;
import com.example.dam.model.UserFolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public interface FolderService {
    Folder createFolder(UUID userId, String folderName, UUID spaceId, UUID parentId) throws IOException;

    Folder shareFolder(String email, UUID folderId, UserFolder.Access access);

    void deleteFolder(UUID folderId);

    Folder getFolderById(UUID folderId);
}
