package com.example.dam.service;

import com.example.dam.dto.AssetDTO;
import com.example.dam.input.AssetInput;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.util.UUID;
@Service
public interface UploadService {
    AssetDTO upload(AssetInput assetInput, UUID tenantId, String apikey, String apiSecret)
            throws IOException, CredentialException, InterruptedException;
}
