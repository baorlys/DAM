package com.example.dam.service.implement;

import com.example.dam.config.StorageProperties;
import com.example.dam.dto.AssetDTO;
import com.example.dam.enums.ResourceType;
import com.example.dam.exception.NotFoundException;
import com.example.dam.global.mapper.DamMapper;
import com.example.dam.global.service.CommonService;
import com.example.dam.input.ConfigurationInput;
import com.example.dam.model.Asset;
import com.example.dam.model.Tenant;
import com.example.dam.repository.AssetRepository;
import com.example.dam.repository.TenantRepository;
import com.example.dam.service.AccessService;
import com.example.dam.service.GetAssetService;
import com.example.dam.service.HandleAssetService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.CredentialException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAssetServiceImpl implements GetAssetService {
    StorageProperties storageProperties;
    AssetRepository assetRepository;
    TenantRepository tenantRepository;
    AccessService accessService;
    HandleAssetService handleAssetService;
    DamMapper mapper;
    ObjectMapper objectMapper;


    @Override
    public AssetDTO getAsset(ConfigurationInput key, String path, Map<String, String> options)
            throws CredentialException, IOException, InterruptedException, NotFoundException {
        Tenant tenant = getTenant(key.getTenantId());
        String buildPath = buildFilePath(tenant, path);

        checkAccess(key.getApiKey(), key.getSecretKey(), path);

        Asset asset = assetRepository.findByFilePath(path);
        Map<String, String> metadata = objectMapper.readValue(asset.getMetadata(), new TypeReference<>() {
        });

        if (options.isEmpty()) {
            return mapper.map(asset, AssetDTO.class);
        }

        ResourceType resourceType = ResourceType.valueOf(metadata.get("resource_type").toUpperCase());
        String outputPath = storageProperties.getTransformPath() + buildPath;
        handleAssetService.transform(resourceType, buildPath, outputPath,
                handleAssetService.convertToTransformVariable(options));

        asset.setFilePath(outputPath);
        return mapper.map(asset, AssetDTO.class);
    }

    @Override
    public MultipartFile getAssetFile(String tenantId, String type, String path, Map<String, String> options)
            throws IOException, NotFoundException, InterruptedException {
        Tenant tenant = getTenant(tenantId);
        String buildPath = buildFilePath(tenant, path);

        Asset asset = assetRepository.findByFilePath(path);
        Map<String, String> metadata = objectMapper.readValue(asset.getMetadata(), new TypeReference<>() {});

        String outputPath = deriveOutputPath(path, buildPath, options, metadata);

        File file = new File(outputPath);
        String contentType = type + "/" + getFileExtension(asset.getFilePath());
        FileInputStream fileInputStream = new FileInputStream(file);

        return new MockMultipartFile(
                file.getName(),
                file.getName(),
                contentType,
                fileInputStream
        );
    }

    private String deriveOutputPath(String path, String buildPath, Map<String, String> options, Map<String, String> metadata)
            throws IOException, InterruptedException {
        String basePath = storageProperties.getTransformPath();
        String outputPath = basePath + buildPath;

        if (options.isEmpty()) {
            return outputPath;
        }

        ResourceType resourceType = ResourceType.valueOf(metadata.get("resource_type").toUpperCase());
        outputPath = basePath + path;
        handleAssetService.transform(
                resourceType,
                storageProperties.getPath() + buildPath,
                outputPath,
                handleAssetService.convertToTransformVariable(options)
        );

        return outputPath;
    }

    private Tenant getTenant(String tenantId) throws NotFoundException {
        return tenantRepository.findById(UUID.fromString(tenantId))
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
    }

    @Override
    public String getFilePath(String tenantId, String path) throws NotFoundException {
        Tenant tenant = getTenant(tenantId);
        Asset asset = assetRepository.findByFilePath(path);
        String buildPath = buildFilePath(tenant, asset.getFilePath());
        return storageProperties.getTransformPath() + buildPath;
    }

    private String buildFilePath(Tenant tenant, String path) {
        return storageProperties.getPathFormat()
                .replace("{tenant}", tenant.getId().toString())
                .replace("{path}", path);
    }

    private void checkAccess(String apiKey, String secretKey, String buildPath) throws CredentialException {
        boolean accessible = accessService.isAccessible(apiKey, secretKey, buildPath);
        CommonService.throwIsNotExists(!accessible, "Access denied");
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? "octet-stream" : fileName.substring(lastDotIndex + 1).toLowerCase();
    }


}
