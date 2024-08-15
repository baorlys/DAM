package com.example.dam.service.implement;

import com.example.dam.model.*;
import com.example.dam.repository.*;
import com.example.dam.global.service.CommonService;
import com.example.dam.service.FolderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class FolderServiceImpl implements FolderService {
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final UserFolderRepository userFolderRepository;
    @Value("${storage.url}")
    private static final String storageDirectory = "";

    private final TenantRepository tenantRepository;
    private final RoleRepository roleRepository;

    @Override
    public Folder createFolder(UUID userId, String fName, UUID tenantId, UUID spaceId, UUID parentId) throws IOException {
        Path folderPath = Paths.get(storageDirectory, fName);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }
        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        Space space = spaceRepository.findById(spaceId).orElse(null);
        Folder parent = Optional.ofNullable(parentId)
                .map(this::getFolderById)
                .orElse(null);
        CommonService.checkNonNull(user, space);
        Folder folder = new Folder();
        folder.setName(fName);
        folder.setTenant(tenant);
        folder.setSpace(space);
        folder.setParent(parent);
        folder = folderRepository.save(folder);
        return folder;
    }

    @Override
    public Folder createFolder(User user, String fName, Tenant tenant, Space space) {
        Folder folder = new Folder();
        folder.setName(fName);
        folder.setTenant(tenant);
        folder.setSpace(space);
        return folderRepository.save(folder);
    }
    @Override
    public Folder shareFolder(String email, UUID folderId, UUID roleId, UUID tenantId) {
        User user = userRepository.findUserByEmail(email);
        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
        Folder folder = getFolderById(folderId);
        Role role = roleRepository.findById(roleId).orElse(null);
        CommonService.checkNonNull(user, folder);
        createFolderAccess(tenant, user, folder, role);
        return folder;
    }

    public void createFolderAccess(Tenant t, User u, Folder f, Role r) {
        UserFolder userFolder = new UserFolder(t, UUID.randomUUID(), u, f, r);
        userFolderRepository.save(userFolder);
    }

    @Override
    public void deleteFolder(UUID folderId) {
        Folder folder = getFolderById(folderId);
        CommonService.checkNonNull(folder);
        folderRepository.delete(folder);
    }

    @Override
    public Folder getFolderById(UUID folderId) {
        return folderRepository.findById(folderId).orElse(null);
    }
}
