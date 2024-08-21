package com.example.dam.service;

import com.example.dam.dto.FolderDTO;
import com.example.dam.model.Folder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class FolderServiceTest {
    @Mock
    private FolderService folderService;
    UUID userId;
    UUID tenantId;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        tenantId = UUID.randomUUID();
    }

    @Test
    void testCreateFolder() throws IOException {
        String fName = "Folder 1";
        FolderDTO dto = folderService.createFolder(userId, fName, tenantId, null, null);
        assertNotNull(dto);
    }

    @Test
    void testShareFolder() {
        UUID roleId = UUID.randomUUID();
        UUID folderId = UUID.randomUUID();
        FolderDTO dto = folderService.shareFolder("ankhoa@gmail.com", folderId, roleId, tenantId);
        assertNotNull(dto);
    }

    @Test
    void testGetFolders() {
        List<FolderDTO> dto = folderService.getAccessibleFolders(UUID.randomUUID(), 10, 1, null);
        assertTrue(dto.stream().count() > 0);
    }

    @Test
    void testGetFolderById() {
        Folder folder = folderService.getFolderById(UUID.randomUUID());
        assertNotNull(folder);
    }
}
