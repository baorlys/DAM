package com.example.dam.service.implement;

import com.example.dam.config.StorageProperties;
import com.example.dam.dto.FolderDTO;
import com.example.dam.model.*;
import com.example.dam.repository.*;
import com.example.dam.global.service.CommonService;
import com.example.dam.service.FolderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
    private final StorageProperties storageProperties;
    private final TenantRepository tenantRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public FolderDTO createFolder(UUID userId, String fName, UUID tenantId, UUID spaceId, UUID parentId) throws IOException {
        Path folderPath = Paths.get(storageProperties.getPath(), fName);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }
        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        Space space = spaceRepository.findById(spaceId).orElse(null);
        Folder parent = Optional.ofNullable(parentId)
                .map(this::getFolderById)
                .orElse(null);
        CommonService.checkNonNull(user, tenant);
        Folder folder = createAndSaveFolder(fName, tenant, space, parent, user);
        return modelMapper.map(folder, FolderDTO.class);
    }

    @Override
    public Folder createFolder(User user, String fName, Tenant tenant, Space space) {
        return createAndSaveFolder(fName, tenant, space, null, user);
    }

    private Folder createAndSaveFolder(String fName, Tenant tenant, Space space, Folder parent, User user) {
        Folder folder = new Folder();
        folder.setName(fName);
        folder.setTenant(tenant);
        folder.setSpace(space);
        folder.setParent(parent);
        folder = folderRepository.save(folder);
        Role role = roleRepository.findRoleByNameAndTenant_Id("Admin", tenant.getId());
        createFolderAccess(tenant, user, folder, role);
        return folder;
    }


    @Override
    public FolderDTO shareFolder(String email, UUID folderId, UUID roleId, UUID tenantId) {
        User user = userRepository.findUserByEmail(email);
        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
        Folder folder = getFolderById(folderId);
        Role role = roleRepository.findById(roleId).orElse(null);
        CommonService.checkNonNull(user, folder);
        createFolderAccess(tenant, user, folder, role);
        return modelMapper.map(folder, FolderDTO.class);
    }

    public void createFolderAccess(Tenant tenant, User user, Folder folder, Role role) {
        UserFolder userFolder = new UserFolder(tenant, UUID.randomUUID(), user, folder, role);
        userFolderRepository.save(userFolder);
    }

    @Override
    public void deleteFolder(UUID folderId) {
        Folder folder = getFolderById(folderId);
        CommonService.checkNonNull(folder);
        folder.setDeleted(true);
        folderRepository.save(folder);
    }

    @Override
    public Folder getFolderById(UUID folderId) {
        return folderRepository.findById(folderId).orElse(null);
    }


    @Override
    public List<FolderDTO> getAccessibleFolders(UUID input, String pageSize, String pageNum, String sortBy) {
        int size = Integer.parseInt(pageSize);
        int page = Integer.parseInt(pageNum);
        Sort sort = (sortBy != null && !sortBy.isEmpty()) ? Sort.by(sortBy) : Sort.unsorted();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Folder> folders = folderRepository.findFoldersByTenant_Id(input, pageable);
        return folders.getContent().stream()
                .map(folder -> modelMapper.map(folder, FolderDTO.class))
                .toList();
    }
}
