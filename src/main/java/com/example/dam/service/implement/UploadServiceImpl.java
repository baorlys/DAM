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
import com.example.dam.service.AccessService;
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
    private AccessService accessService;
    private HandleAssetService handleAssetService;
    private DamMapper damMapper;

//    @Override
//    public AssetDTO upload(AssetInput assetInput, UUID tenantId, String apiKey, String secretKey) throws IOException, CredentialException, InterruptedException {
//        // build params
//        Map<String, Object> attributes = buildUploadParams(assetInput.getMetadata());
//        MultipartFile file = assetInput.getFile();
//
//        // check tenant and credential
//        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
//        CommonService.throwNotFound(tenant, "Can not find tenant");
//        Credential credential = credentialRepository.findByApiKeyAndSecretKey(apiKey, secretKey);
//
//        // handle folder & file name
//        String fName = (String) attributes.get("folder");
//        Space space = getSpaceById(attributes.get("space_id"));
//        String originName = file.getOriginalFilename();
//        Folder folder = findOrCreateFolder(tenant, credential.getUser(), space, fName);
//        String path = FileService.buildRelativePath(Objects.requireNonNull(originName));
////        accessService.isAccessible(apiKey, secretKey, path);
//
//        // save asset and thumbnail
//        String absolutePath = FileService.buildAbsolutePath(path, tenant, space, folder);
//        FileService.saveFile(file, absolutePath, storageProperties.getPath());
//        String thumbnail = storageProperties.getThumbnailPath() + path;
//        handleAssetService.generateThumbnail(
//                (ResourceType) attributes.get("resource_type"),
//                storageProperties.getPath() + absolutePath, thumbnail, 300);
//
//        // handle file information
//        attributes.put("size", file.getSize());
//        attributes.put("origin_name", originName);
//        attributes.put("extension", FileService.extractExtension(originName));
//
//        // save to database
//        Asset asset = new Asset();
//        asset.setId(UUID.randomUUID());
//        asset.setTenant(tenant);
//        asset.setSpace(space);
//        asset.setFolder(folder);
//        asset.setMetadata(objectMapper.writeValueAsString(attributes));
//        asset.setPublicId(UUID.randomUUID().toString());
//        asset.setFilePath(path);
//        asset.setDisplayName(file.getOriginalFilename());
//        asset.setThumbnailPath(thumbnail);
//        return damMapper.mapAsset(assetRepository.save(asset));
//    }


    @Override
    public String upload(AssetInput assetInput, UUID tenantId, String apiKey, String secretKey) throws IOException, CredentialException, InterruptedException {
        // build params
        Map<String, Object> attributes = buildUploadParams(assetInput.getMetadata());
        MultipartFile file = assetInput.getFile();

        // check tenant and credential
        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
        CommonService.throwNotFound(tenant, "Can not find tenant");
        Credential credential = credentialRepository.findByApiKeyAndSecretKey(apiKey, secretKey);

        // handle folder & file name
        String fName = (String) attributes.get("folder");
        Space space = getSpaceById(attributes.get("space_id"));
        String originName = file.getOriginalFilename();
        Folder folder = findOrCreateFolder(tenant, credential.getUser(), space, fName);
        String path = FileService.buildRelativePath(Objects.requireNonNull(originName));
//        accessService.isAccessible(apiKey, secretKey, path);

        // save asset and thumbnail
        String absolutePath = FileService.buildAbsolutePath(path, tenant, space, folder);
        FileService.saveFile(file, absolutePath, storageProperties.getPath());

        ResourceType srcType = (ResourceType) attributes.get("resource_type");
        String outputPath = storageProperties.getTransformPath() + absolutePath;
        Map<String, String> transform = (Map<String, String>) attributes.get("transform");
        handleAssetService.transform(srcType, storageProperties.getPath() + absolutePath, outputPath,
                handleAssetService.convertToTransformVariable(transform));

        String thumbnail = storageProperties.getThumbnailPath() + path;
        handleAssetService.generateThumbnail(srcType, storageProperties.getPath() + absolutePath, thumbnail, 300);

        // save to database
        Asset asset = new Asset();
        asset.setId(UUID.randomUUID());
        asset.setTenant(tenant);
        asset.setSpace(space);
        asset.setFolder(folder);
        asset.setMetadata(handleMetadata(attributes, file));
        asset.setPublicId(UUID.randomUUID().toString());
        asset.setFilePath(path);
        asset.setDisplayName(file.getOriginalFilename());
        asset.setThumbnailPath(thumbnail);
        assetRepository.save(asset);
        return asset.getFilePath();
    }

    private String handleMetadata(Map<String, Object> data, MultipartFile file) throws JsonProcessingException {
        data.put("size", file.getSize());
        data.put("origin_name", file.getOriginalFilename());
        data.put("extension", FileService.extractExtension(file.getOriginalFilename()));
        data.remove("space_id");
        data.remove("folder");
        data.remove("transform");
        return objectMapper.writeValueAsString(data);
    }


    private Folder findOrCreateFolder(Tenant tenant, User user, Space space, String folderName) {
        if (folderName == null) {
            return null;
        }
        return Optional.ofNullable(folderRepository.findFolderByNameAndTenant_Id(folderName, tenant.getId()))
                .orElseGet(() -> folderService.createFolder(user, folderName, tenant, space));
    }


    private Map<String, Object> buildUploadParams(Map<String, String> options) throws JsonProcessingException {
        if (options == null) {
            options = Collections.emptyMap();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("public_id", options.get("public_id"));
        params.put("space_id", options.get("space"));
        params.put("folder", options.get("folder_name"));
        params.put("type", options.get("type"));
        params.put("resource_type", CommonService.findResourceType(options.get("resource_type")));
        params.put("display_name", options.get("display_name"));
        params.put("notification_url", options.get("notification_url"));
        params.put("transform", objectMapper.readValue(options.get("transformation"), Map.class));
        return params;
    }

    private Space getSpaceById(Object spaceId) {
        return Optional.ofNullable(spaceId)
                .map(id -> UUID.fromString(id.toString()))
                .flatMap(spaceRepository::findById)
                .orElse(null);
    }


}
