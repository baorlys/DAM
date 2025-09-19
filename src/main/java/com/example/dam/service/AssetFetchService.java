package com.example.dam.service;

import com.example.dam.exception.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.util.Map;

public interface AssetFetchService {
    MultipartFile getAssetFile(String tenantId, String type, String path, Map<String, String> options) throws CredentialException, IOException, InterruptedException, NotFoundException;

    String getFilePath(String tenantId, String path) throws CredentialException, IOException, InterruptedException, NotFoundException;
}
