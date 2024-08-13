package com.example.dam.service.implement;

import com.example.dam.input.AssetInput;
import com.example.dam.model.Asset;
import com.example.dam.model.Credential;
import com.example.dam.model.Folder;
import com.example.dam.model.Space;
import com.example.dam.repository.*;
import com.example.dam.service.CommonService;
import com.example.dam.service.FolderService;
import com.example.dam.service.UploadService;
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
    private final String storageDirectory = "src/main/resources/storage/tenant-1";
    private final CredentialRepository credentialRepository;
    private FolderService folderService;
    private final SpaceRepository spaceRepository;
    private final AssetRepository assetRepository;

    @Override
    public String upload(AssetInput assetInput, UUID spaceId, String apikey, String apiSecret) throws IOException {
        Map<String, Object> attributes = buildUploadParams(assetInput.getOptions());
        Space space = getSpace(spaceId);
        Credential credential = credentialRepository.findByApiKeyAndSecretKey(apikey, apiSecret);
        Folder folder = getOrCreateFolder((String) attributes.get("folder_name"), credential, spaceId);
        String path = CommonService.filePathHandler(space, folder, assetInput.getFile().getOriginalFilename());
        String url = saveHandler(assetInput.getFile(), path, storageDirectory);
        Asset asset = new Asset(space, folder, assetInput.getFile().getName(), url, attributes.toString());
        assetRepository.save(asset);
        return asset.getFilePath();
    }

    private Space getSpace(UUID spaceId) {
        return spaceRepository.findById(spaceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Space ID: " + spaceId));
    }

    private Folder getOrCreateFolder(String folderName, Credential credential, UUID spaceId) throws IOException {
        if (folderName == null || credential == null) {
            return null;
        }
        return folderService.createFolder(credential.getUser().getId(), folderName, spaceId, null);
    }

    public String saveHandler(MultipartFile file, String fileName, String storageDirectory) throws IOException {
        Path filePath = Paths.get(storageDirectory, fileName);
        Path parentDir = filePath.getParent();
        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
        Files.copy(file.getInputStream(), filePath);
        return filePath.toString();
    }

    private Map<String, Object> buildUploadParams(Map<String, String> options) {
        if (options == null) {
            options = Collections.emptyMap();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("public_id", options.get("public_id"));
        params.put("folder_name", options.get("folder_name"));
        params.put("parent_folder", options.get("parent_folder"));
        params.put("type", options.get("type"));
        params.put("display_name", options.get("display_name"));
        params.put("notification_url", options.get("notification_url"));
        return params;
    }

}
