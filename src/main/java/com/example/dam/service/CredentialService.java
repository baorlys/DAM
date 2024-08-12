package com.example.dam.service;

import com.example.dam.model.Credential;
import com.example.dam.model.User;

public interface CredentialService {
    boolean isValidKey(String tenantId, String apiKey, String secretKey);
    Credential getCredential(String apikey, String apiSecret);
}
