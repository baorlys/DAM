package com.example.dam.service;

import com.example.dam.dto.AssetDTO;
import com.example.dam.input.ConfigurationInput;

import javax.security.auth.login.CredentialException;

public interface GetAssetService {
    AssetDTO getAsset(ConfigurationInput key, String publicId) throws CredentialException;
}
