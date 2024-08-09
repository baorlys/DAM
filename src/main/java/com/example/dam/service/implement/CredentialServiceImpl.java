package com.example.dam.service.implement;

import com.example.dam.repository.CredentialRepository;
import com.example.dam.service.CredentialService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CredentialServiceImpl implements CredentialService {
    CredentialRepository credentialRepository;

    @Override
    public boolean isValidKey(String tenantId, String apiKey, String secretKey) {
        return credentialRepository.exists(tenantId, apiKey, secretKey);
    }
}
