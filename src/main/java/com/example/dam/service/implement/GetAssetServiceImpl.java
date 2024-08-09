package com.example.dam.service.implement;

import com.example.dam.dto.AssetDTO;
import com.example.dam.input.ConfigurationInput;
import com.example.dam.model.Credential;
import com.example.dam.repository.AssetRepository;
import com.example.dam.repository.CredentialRepository;
import com.example.dam.service.CommonService;
import com.example.dam.service.CredentialService;
import com.example.dam.service.GetAssetService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAssetServiceImpl implements GetAssetService {
    AssetRepository assetRepository;
    CredentialService credentialService;



    @Override
    public AssetDTO getAsset(ConfigurationInput key, String publicId) throws CredentialException {
        boolean exists = credentialService.isValidKey(key.getTenantId(), key.getApiKey(), key.getSecretKey());
        CommonService.throwIsNotExists(!exists, "Credential not found");
        return null;
    }
}
