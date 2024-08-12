package com.example.dam.service;

import javax.security.auth.login.CredentialException;

public interface AccessService {
    boolean isValidKey(String apiKey, String secretKey);

    boolean isAccessible(String apiKey, String secretKey, String filePath) throws CredentialException;
}
