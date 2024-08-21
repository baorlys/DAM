package com.example.dam.service.implement;

import com.example.dam.config.StorageProperties;
import com.example.dam.dto.AssetDTO;
import com.example.dam.enums.ResourceType;
import com.example.dam.global.mapper.DamMapper;
import com.example.dam.global.service.FileService;
import com.example.dam.input.AssetInput;
import com.example.dam.model.*;
import com.example.dam.repository.*;
import com.example.dam.global.service.CommonService;
import com.example.dam.service.FolderService;
import com.example.dam.service.HandleAssetService;
import com.example.dam.service.UploadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
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
    private HandleAssetService handleAssetService;
    private DamMapper damMapper;
    private static final String FOLDER = "folder";
    private static final String SPACE_ID = "space_id";
    private static final String TYPE = "type";
    private static final String RESOURCE_TYPE = "resource_type";
    private static final String TRANSFORM = "transformation";
    private static final String PUBLIC_ID = "public_id";
    private static final String DISPLAY_NAME = "display_name";
    private static final String NOTIFICATION_URL = "notification_url";
    private static final int THRESH_HOLD = 5000;
    @Override
    public AssetDTO upload(AssetInput assetInput, UUID tenantId, String apiKey, String secretKey)
            throws IOException, CredentialException, InterruptedException {
        // Build params
        Map<String, Object> attributes = buildUploadParams(assetInput.getMetadata());
        MultipartFile file = Objects.requireNonNull(assetInput.getFile());

        // Validate tenant and credential
        Tenant tenant = validateTenant(tenantId);
        Credential credential = validateCredential(apiKey, secretKey);

        // Handle folder & file name
        String fName = (String) attributes.get(FOLDER);
        Space space = getSpaceById(attributes.get(SPACE_ID));
        String originName = file.getOriginalFilename();
        Folder folder = findOrCreateFolder(tenant, credential.getUser(), space, fName);
        String path = FileService.buildRelativePath(Objects.requireNonNull(originName));

        // Save origin, auto-scale, and thumbnail
        String absolutePath = FileService.buildAbsolutePath(path, tenant, space, folder);
        String thumbnail = storageProperties.getThumbnailPath() + absolutePath;
        saveAndProcessFile(file, absolutePath, attributes, thumbnail);

        // Save to database
        Asset asset = Asset.builder()
                .id(UUID.randomUUID())
                .displayName(file.getOriginalFilename())
                .tenant(tenant)
                .space(space)
                .folder(folder)
                .metadata(handleMetadata(attributes, file))
                .publicId(UUID.randomUUID().toString())
                .filePath(path)
                .thumbnailPath(thumbnail)
                .build();
        return damMapper.mapAsset(assetRepository.save(asset));
    }

    private Tenant validateTenant(UUID tenantId) throws CredentialException {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new CredentialException("Tenant not found"));
    }

    private Credential validateCredential(String apiKey, String secretKey) {
        return credentialRepository.findByApiKeyAndSecretKey(apiKey, secretKey);
    }

    private void saveAndProcessFile(MultipartFile file, String absolutePath, Map<String, Object> attributes, String thumbnail)
            throws IOException, InterruptedException {
        FileService.saveFile(file, absolutePath, storageProperties.getPath());
        ResourceType resourceType = (ResourceType) attributes.get(RESOURCE_TYPE);
        String transformPath = storageProperties.getTransformPath() + absolutePath;
        Map<String, String> transform = (Map<String, String>) attributes.get(TRANSFORM);
        if (file.getSize() > THRESH_HOLD) {
            handleAssetService.transform(resourceType, storageProperties.getPath() + absolutePath, transformPath,
                    handleAssetService.convertToTransformVariable(transform));
        } else {
            FileService.saveFile(file, "", transformPath);
        }
        handleAssetService.generateThumbnail(resourceType, storageProperties.getPath() + absolutePath, thumbnail, 300);
    }


    private String handleMetadata(Map<String, Object> data, MultipartFile file) throws JsonProcessingException {
        data.put("size", file.getSize());
        data.put("origin_name", file.getOriginalFilename());
        data.put("extension", FileService.extractExtension(Objects.requireNonNull(file.getOriginalFilename())));
        data.remove(SPACE_ID);
        data.remove(FOLDER);
        data.remove(TRANSFORM);
        return objectMapper.writeValueAsString(data);
    }

    private Map<String, Object> buildUploadParams(Map<String, String> options) throws JsonProcessingException {
        if (options == null) {
            options = Collections.emptyMap();
        }
        Map<String, Object> params = new HashMap<>();
        params.put(PUBLIC_ID, options.get(PUBLIC_ID));
        params.put(SPACE_ID, options.get(SPACE_ID));
        params.put(FOLDER, options.get(FOLDER));
        params.put(TYPE, options.get(TYPE));
        params.put(RESOURCE_TYPE, CommonService.findResourceType(options.get(RESOURCE_TYPE)));
        params.put(DISPLAY_NAME, options.get(DISPLAY_NAME));
        params.put(NOTIFICATION_URL, options.get(NOTIFICATION_URL));
        params.put(TRANSFORM, objectMapper.readValue(options.get(TRANSFORM), Map.class));
        return params;
    }


    private Folder findOrCreateFolder(Tenant tenant, User user, Space space, String folderName) {
        if (folderName == null) {
            return null;
        }
        return Optional.ofNullable(folderRepository.findFolderByNameAndTenant_Id(folderName, tenant.getId()))
                .orElseGet(() -> folderService.createFolder(user, folderName, tenant, space));
    }


    private Space getSpaceById(Object spaceId) {
        return Optional.ofNullable(spaceId)
                .map(id -> UUID.fromString(id.toString()))
                .flatMap(spaceRepository::findById)
                .orElse(null);
    }


}
