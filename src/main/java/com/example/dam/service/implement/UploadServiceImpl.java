package com.example.dam.service.implement;

import com.example.dam.config.StorageProperties;
import com.example.dam.global.service.FileService;
import com.example.dam.input.AssetInput;
import com.example.dam.model.*;
import com.example.dam.repository.*;
import com.example.dam.global.service.CommonService;
import com.example.dam.service.FolderService;
import com.example.dam.service.UploadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadServiceImpl implements UploadService {
    private StorageProperties storageProperties;
    private CredentialRepository credentialRepository;
    private FolderService folderService;
    private SpaceRepository spaceRepository;
    private AssetRepository assetRepository;
    private FolderRepository folderRepository;
    private TenantRepository tenantRepository;
    private ObjectMapper objectMapper;

    @Override
    public String upload(AssetInput assetInput, UUID tenantId, String apiKey, String secretKey) throws IOException {
        // build params
        Map<String, Object> attributes = buildUploadParams(assetInput.getMetadata());

        //check tenant and credential
        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
        CommonService.throwNotFound(tenant, "Can not find tenant");
        Credential credential = credentialRepository.findByApiKeyAndSecretKey(apiKey, secretKey);
        CommonService.throwNotFound(credential, "Can not find credential");

        // save to storage
        String fName = (String) attributes.get("folder");
        Space space = getSpaceById(attributes.get("space_id"));
        Folder folder = findOrCreateFolder(tenant, credential.getUser(), space, fName);
        String path = FileService.buildRelativePath(assetInput.getFile().getOriginalFilename(), space, folder);
        FileService.saveFile(assetInput.getFile(),
                FileService.buildAbsolutePath(assetInput.getFile().getOriginalFilename(), tenant, space, folder),
                storageProperties.getPath());

        // save to database
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



    private Folder findOrCreateFolder(Tenant tenant, User user, Space space, String folderName) {
        if (folderName == null) {
            return null;
        }
        return Optional.ofNullable(folderRepository.findFolderByNameAndTenant_Id(folderName, tenant.getId()))
                .orElseGet(() -> folderService.createFolder(user, folderName, tenant, space));
    }



    private Map<String, Object> buildUploadParams(Map<String, String> options) {
        if (options == null) {
            options = Collections.emptyMap();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("public_id", options.get("public_id"));
        params.put("space_id", options.get("space"));
        params.put("folder", options.get("folder_name"));
        params.put("type", options.get("type"));
        params.put("resource_type", options.get("resource_type"));
        params.put("display_name", options.get("display_name"));
        params.put("notification_url", options.get("notification_url"));
        return params;
    }

    private Space getSpaceById(Object spaceId) {
        return Optional.ofNullable(spaceId)
                .map(id -> UUID.fromString(id.toString()))
                .flatMap(spaceRepository::findById)
                .orElse(null);
    }


}
