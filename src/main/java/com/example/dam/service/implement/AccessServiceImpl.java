package com.example.dam.service.implement;

import com.example.dam.model.Asset;
import com.example.dam.model.Credential;
import com.example.dam.repository.AssetRepository;
import com.example.dam.repository.CredentialRepository;
import com.example.dam.service.AccessService;
import com.example.dam.service.CommonService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccessServiceImpl implements AccessService {
    AssetRepository assetRepository;
    CredentialRepository credentialRepository;

    @Override
    public boolean isValidKey(String apiKey, String secretKey) {
        return credentialRepository.existsByApiKeyAndSecretKey(apiKey, secretKey);
    }

    @Override
    public boolean isAccessible(String apiKey, String secretKey, String filePath) {
        Credential credential = credentialRepository.findByApiKeyAndSecretKey(apiKey, secretKey);
        CommonService.throwIsNull(credential, "Credential not found");

        Asset asset = assetRepository.findByFilePath(filePath);
        CommonService.throwIsNull(asset, "Asset not found");

        return true;


    }
}
