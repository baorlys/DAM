//package com.example.dam.service;
//
//import com.example.dam.model.Folder;
//import com.example.dam.model.UserFolder;
//
//import java.io.IOException;
//import java.util.UUID;
//
//public interface FolderService {
//    Folder createFolder(UUID userId, String fName, UUID spaceId, UUID parentId) throws IOException;
//    Folder shareFolder(String email, UUID folderId, UserFolder.Access access);
//    void deleteFolder(UUID folderId);
//    Folder getFolderById(UUID folderId);
//}
