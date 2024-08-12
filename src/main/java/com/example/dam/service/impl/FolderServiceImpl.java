package com.example.dam.service.impl;

import com.example.dam.model.*;
import com.example.dam.repository.FolderRepository;
import com.example.dam.repository.SpaceRepository;
import com.example.dam.repository.UserFolderRepository;
import com.example.dam.repository.UserRepository;
import com.example.dam.service.CommonService;
import com.example.dam.service.FolderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class FolderServiceImpl implements FolderService {
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final UserFolderRepository userFolderRepository;
    private final String storageDirectory = "src/main/resources/storage/tenant-1";

    @Override
    public Folder createFolder(UUID userId, String fName, UUID spaceId, UUID parentId) throws IOException {
        Folder folder = folderRepository.findFolderByNameAndSpace_Id(fName, spaceId);
        CommonService.checkNull(folder);
        Path folderPath = Paths.get(storageDirectory, folder.getName());
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }
        User user = userRepository.findById(userId).orElse(null);
        Space space = spaceRepository.findById(spaceId).orElse(null);
        Folder parent = (parentId != null) ? getFolderById(parentId) : null;
        CommonService.checkNonNull(user, space);
        folder = new Folder(space, parent, fName);
        createFolderAccess(user, folder, UserFolder.Access.ADMIN);
        return folderRepository.save(folder);
    }

    @Override
    public Folder shareFolder(String email, UUID folderId, UserFolder.Access access) {
        User user = userRepository.findUserByEmail(email);
        Folder folder = getFolderById(folderId);
        CommonService.checkNonNull(user, folder);
        createFolderAccess(user, folder, access);
        return folder;
    }

    public void createFolderAccess(User u, Folder f, UserFolder.Access access) {
        UserFolder userFolder = new UserFolder(u, f, access);
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
