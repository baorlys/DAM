package com.example.dam.service;

public interface CredentialService {
    boolean isValidKey(String tenantId, String apiKey, String secretKey);

}
