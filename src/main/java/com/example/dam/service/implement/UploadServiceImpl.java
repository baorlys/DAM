package com.example.dam.service.implement;

import com.example.dam.config.StorageProperties;
import com.example.dam.input.AssetInput;
import com.example.dam.model.*;
import com.example.dam.repository.*;
import com.example.dam.global.service.CommonService;
//import com.example.dam.service.FolderService;
import com.example.dam.service.FolderService;
import com.example.dam.service.UploadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class UploadServiceImpl implements UploadService {
    private final StorageProperties storageProperties;
    private final CredentialRepository credentialRepository;
    private final FolderService folderService;
    private final SpaceRepository spaceRepository;
    private final AssetRepository assetRepository;

    private final FolderRepository folderRepository;

    private final TenantRepository tenantRepository;

    private final ObjectMapper objectMapper;

    @Override
    public String upload(AssetInput assetInput, UUID tenantId, String apikey, String apiSecret) throws IOException {
        Map<String, Object> attributes = buildUploadParams(assetInput.getMetadata());
        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
        Credential credential = credentialRepository.findByApiKeyAndSecretKey(apikey, apiSecret);

        String fName = (String) attributes.get("folder");
        Space space = (Space) attributes.get("space");

        Folder folder = getFolder(tenant, credential.getUser(), space, fName);
        String path = buildFilePath(tenant, space, folder, fName);
        saveHandler(assetInput.getFile(), path, storageProperties.getPath());

        Asset asset = new Asset();
        asset.setId(UUID.randomUUID());
        asset.setTenant(tenant);
        asset.setSpace(space);
        asset.setFolder(folder);
        asset.setMetadata(objectMapper.writeValueAsString(attributes));
        asset.setPublicId(UUID.randomUUID().toString());
        asset.setFilePath(path);
        asset.setDisplayName(assetInput.getFile().getOriginalFilename());
        assetRepository.save(asset);
        return asset.getFilePath();
    }

    private String buildFilePath(Tenant tenant, Space space, Folder folder, String fileName) {
        String comma = "/";
        StringBuilder pathBuilder = new StringBuilder().append(tenant.getId());
        if (space != null) {
            pathBuilder.append(comma).append(space.getId());
        }
        if (folder != null) {
            pathBuilder.append(comma).append(folder.getName());
        }
        return pathBuilder.append(comma).append(fileName).toString();
    }

    private Folder getFolder(Tenant tenant, User user, Space space, String fName) throws IOException {
        if (fName == null) {
            return null;
        }
        Folder folder = folderRepository.findFolderByNameAndTenant_Id(fName, tenant.getId());
        if (folder == null) {
            return folderService.createFolder(user, fName, tenant, space);
        }
        return folder;
    }

    public void saveHandler(MultipartFile file, String fileName, String storageDirectory) throws IOException {
        Path filePath = Paths.get(storageDirectory, fileName);
        Path parentDir = filePath.getParent();
        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
        Files.copy(file.getInputStream(), filePath);
    }

    private Map<String, Object> buildUploadParams(Map<String, String> options) {
        if (options == null) {
            options = Collections.emptyMap();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("public_id", options.get("public_id"));
        params.put("space", getSpaceParam(options.get("space_id")));
        params.put("folder", options.get("folder_name"));
        params.put("parent_folder", options.get("parent_folder"));
        params.put("resource_type", options.get("resource_type"));
        params.put("display_name", options.get("display_name"));
        params.put("notification_url", options.get("notification_url"));
        return params;
    }

    private Space getSpaceParam(Object id) {
        if (id == null) {
            return null;
        }
        return spaceRepository.findById((UUID) id).orElse(null);
    }

}
