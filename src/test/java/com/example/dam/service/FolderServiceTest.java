package com.example.dam.service;

import com.example.dam.model.Folder;
import com.example.dam.model.UserFolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FolderServiceTest {
    @Autowired
    private FolderService folderService;

    UUID folderId;
    @BeforeEach
    void init() {
         folderId = UUID.fromString("AEA784B4-B727-49D0-BBA2-5A49BC5B5F72");
    }

    @Test
    void testGetFolderById(){
        Folder folder = folderService.getFolderById(folderId);
        assertNotNull(folder);
    }

    @Test
    void testCreateFolder() throws IOException {
        UUID spaceId = UUID.fromString("47B11EDC-4A0C-4EA6-8D9C-7FD2B84C3C74");
        UUID parentId = UUID.fromString("1E09BFA0-4E6C-479A-AA8E-6205FBF51FA9");
        String folderName = "New folder";
        UUID userId = UUID.fromString("2A054434-CC21-46B6-8414-4A360CEE8E27");
        Folder folder = folderService.createFolder(userId, folderName, spaceId, parentId);
        assertNotNull(folder);
    }

    @Test
    void testShareFolder(){
        String email = "user2@example.com";
        UserFolder.Access access = UserFolder.Access.READ;
        Folder folder = folderService.shareFolder(email, folderId, access);
        assertNotNull(folder);
    }

}
