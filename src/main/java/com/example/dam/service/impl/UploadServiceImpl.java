package com.example.dam.service.impl;

import com.example.dam.dto.UploadAssetDTO;
import com.example.dam.enums.Format;
import com.example.dam.input.AssetInput;
import com.example.dam.repository.CredentialRepository;
import com.example.dam.repository.TenantRepository;
import com.example.dam.service.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class UploadServiceImpl implements UploadService {
    private final String storageDirectory = "src/main/resources/storage/tenant-1";
    private final CredentialRepository credentialRepository;
    private final TenantRepository tenantRepository;

    @Override
    public UploadAssetDTO upload(AssetInput assetInput) throws IOException {
//        checkConfiguration(assetInput.getTenantId(), assetInput.getApiKey());
//        String publicId = UUID.randomUUID().toString();
//        Path filePath = Paths.get(storageDirectory, publicId + "-" + assetInput.getFile().getOriginalFilename());
//        Files.createDirectories(filePath.getParent());
//        Files.copy(assetInput.getFile().getInputStream(), filePath);
//        long bytes = assetInput.getFile().getSize();
//        Map<? , ?> attributes = buildUploadParams(assetInput.getOptions());
//        Asset asset = new Asset();
//        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
//        asset.setTenant(tenant);
//        asset.setPublicId(publicId);
//        asset.setFormat(getFormat((String) attributes.get("format")));
//        asset.setSize(bytes);
//        asset.setHeight((Integer) attributes.getOrDefault("height", 1000));
//        asset.setWidth((Integer) attributes.getOrDefault("width", 1000));
//        asset.setFolder((Folder) attributes.getOrDefault("asset_folder", null));
//        asset.setDisplayName((String) attributes.getOrDefault("display_name", ""));
//        asset.setType((Type) attributes.get("type"));
//        asset.setPlaceholder((Boolean) attributes.getOrDefault("place_holder", false));
//        asset.setUrl(filePath.toString());
        return new UploadAssetDTO();
    }

    public Format getFormat(String val) {
        return Arrays.stream(Format.values())
                .filter(f -> f.name().equalsIgnoreCase(val))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid format: " + val));
    }


    private Map<String, String> buildUploadParams(Map<String, String> options) {
        if (options == null) {
            options = Collections.emptyMap();
        }
        Map<String, String> params = new HashMap<>();
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
