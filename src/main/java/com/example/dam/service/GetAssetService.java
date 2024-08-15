package com.example.dam.service;

import com.example.dam.dto.AssetDTO;
import com.example.dam.exception.NotFoundException;
import com.example.dam.input.ConfigurationInput;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.util.Map;

public interface GetAssetService {
    AssetDTO getAsset(ConfigurationInput key, String path, Map<String, String> options) throws CredentialException, IOException, InterruptedException, NotFoundException;

    MultipartFile getAssetFile(String tenantId, String type, String path, Map<String, String> options) throws CredentialException, IOException, InterruptedException, NotFoundException;

    String getFilePath(String tenantId, String path) throws CredentialException, IOException, InterruptedException, NotFoundException;
}
