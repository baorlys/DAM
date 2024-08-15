package com.example.dam.service;

import com.example.dam.dto.FolderDTO;
import com.example.dam.input.TenantUserInput;
import com.example.dam.model.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface FolderService {
    Folder createFolder(UUID userId, String fName, UUID tenantId, UUID spaceId, UUID parentId) throws IOException;
    Folder createFolder(User user, String fName, Tenant tenant, Space space);
    Folder shareFolder(String email, UUID folderId, UUID roleId, UUID tenantId);
    void deleteFolder(UUID folderId);
    Folder getFolderById(UUID folderId);

    List<FolderDTO> getAccessibleFolders(TenantUserInput input, String pageSize, String pageNum, String sortBy);
}
