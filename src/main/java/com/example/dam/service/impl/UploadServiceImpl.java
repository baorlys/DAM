package com.example.dam.service.impl;

import com.example.dam.dto.UploadAssetDTO;
import com.example.dam.enums.Format;
import com.example.dam.enums.Type;
import com.example.dam.input.AssetInput;
import com.example.dam.input.ConfigurationInput;
import com.example.dam.model.*;
import com.example.dam.repository.AssetRepository;
import com.example.dam.repository.CredentialRepository;
import com.example.dam.repository.TenantRepository;
import com.example.dam.service.CredentialService;
import com.example.dam.service.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@AllArgsConstructor
public class UploadServiceImpl implements UploadService {
    private final String storageDirectory = "src/main/resources/storage/";
    private final CredentialService credentialService;
    private final TenantRepository tenantRepository;

    private final AssetRepository assetRepository;

    @Override
    public String upload(AssetInput assetInput, String tenantId, String apiKey, String secretKey) throws IOException {
        credentialService.isValidKey(tenantId, apiKey, secretKey);
        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
        Path filePath = Paths.get(storageDirectory + tenant.getId(), assetInput.getFile().getOriginalFilename());
        Files.createDirectories(filePath.getParent());
        Files.copy(assetInput.getFile().getInputStream(), filePath);
        long bytes = assetInput.getFile().getSize();
        Map attributes = buildUploadParams(assetInput.getOptions());
        Asset asset = new Asset();

        asset.setPublicId((String) attributes.getOrDefault("public_id", UUID.randomUUID().toString()));
        asset.setTenant(tenant);
        asset.setFormat(getFormat((String) attributes.getOrDefault("format", Format.MP4)));
        asset.setSize(bytes);
        asset.setHeight((Integer) attributes.getOrDefault("height", 1000));
        asset.setWidth((Integer) attributes.getOrDefault("width", 1000));
        asset.setFolder((Folder) attributes.getOrDefault("asset_folder", null));
        asset.setDisplayName((String) attributes.getOrDefault("display_name", ""));
        asset.setType((Type) attributes.getOrDefault("type", Type.UPLOAD));
        asset.setPlaceholder((Boolean) attributes.getOrDefault("place_holder", false));
        asset.setUrl(filePath.toString());

        assetRepository.save(asset);
        return asset.getUrl();
    }

    public Format getFormat(String val) {
        return Arrays.stream(Format.values())
                .filter(f -> f.name().equalsIgnoreCase(val))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid format: " + val));
    }


    private Map<String, Object> buildUploadParams(Map options) {
        if (options == null) {
            options = Collections.emptyMap();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("public_id", options.get("public_id"));
        params.put("format", options.get("format"));
        params.put("type", options.get("type"));
        params.put("asset_folder", options.get("asset_folder"));
        params.put("display_name", options.get("display_name"));
        params.put("notification_url", options.get("notification_url"));
        return params;
    }


    @Override
    public UploadAssetDTO uploadLarge(AssetInput assetInput) {
        return null;
    }

}
