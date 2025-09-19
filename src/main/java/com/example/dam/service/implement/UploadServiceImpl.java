package com.example.dam.service.implement;

import com.example.dam.config.StorageProperties;
import com.example.dam.dto.AssetDTO;
import com.example.dam.config.OptionParam;
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
import java.time.LocalDateTime;
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
        String fName = (String) attributes.get(OptionParam.FOLDER);
        Space space = getSpaceById(attributes.get(OptionParam.SPACE_ID));
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
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
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
        ResourceType resourceType = (ResourceType) attributes.get(OptionParam.RESOURCE_TYPE);
        String transformPath = storageProperties.getTransformPath() + absolutePath;
        Map<String, String> transform = (Map<String, String>) attributes.get(OptionParam.TRANSFORM);
        if (file.getSize() > OptionParam.THRESH_HOLD && resourceType == ResourceType.IMAGE) {
            handleAssetService.transform(resourceType, storageProperties.getPath() + absolutePath, transformPath,
                    handleAssetService.convertToTransformVariable(transform));
        } else {
            FileService.saveFile(file, "", transformPath);
        }
        handleAssetService.generateThumbnail(resourceType, storageProperties.getPath() + absolutePath,
                thumbnail, 300);
    }


    private String handleMetadata(Map<String, Object> data, MultipartFile file) throws JsonProcessingException {
        data.put(OptionParam.SIZE, file.getSize());
        data.put(OptionParam.ORIGIN_NAME, file.getOriginalFilename());
        data.put(OptionParam.EXTENSION, FileService.extractExtension(Objects.requireNonNull(file.getOriginalFilename())));
        data.remove(OptionParam.SPACE_ID);
        data.remove(OptionParam.FOLDER);
        data.remove(OptionParam.TRANSFORM);
        return objectMapper.writeValueAsString(data);
    }

    private Map<String, Object> buildUploadParams(Map<String, String> options) throws JsonProcessingException {
        Map<String, String> finalOptions = Optional.ofNullable(options).orElse(Collections.emptyMap());
        Map<String, Object> params = new HashMap<>();
        params.put(OptionParam.PUBLIC_ID, finalOptions.get(OptionParam.PUBLIC_ID));
        params.put(OptionParam.SPACE_ID, finalOptions.get(OptionParam.SPACE_ID));
        params.put(OptionParam.FOLDER, finalOptions.get(OptionParam.FOLDER));
        params.put(OptionParam.TYPE, finalOptions.get(OptionParam.TYPE));
        params.put(OptionParam.RESOURCE_TYPE, CommonService.findResourceType(finalOptions.get(OptionParam.RESOURCE_TYPE)));
        params.put(OptionParam.DISPLAY_NAME, finalOptions.get(OptionParam.DISPLAY_NAME));
        params.put(OptionParam.NOTIFICATION_URL, finalOptions.get(OptionParam.NOTIFICATION_URL));
        params.put(OptionParam.TRANSFORM, convertTransform(finalOptions.get(OptionParam.TRANSFORM)));
        return params;
    }

    private Map convertTransform(String transform) throws JsonProcessingException {
        if (transform == null) {
            return Collections.emptyMap();
        }
        return objectMapper.readValue(transform, Map.class);
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
