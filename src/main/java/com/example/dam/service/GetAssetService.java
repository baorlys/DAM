package com.example.dam.service;

import com.example.dam.dto.AssetDTO;
import com.example.dam.input.ConfigurationInput;
import com.example.dam.model.Credential;

import javax.security.auth.login.CredentialException;
import java.util.List;

public interface GetAssetService {
    AssetDTO getAsset(ConfigurationInput key, String publicId) throws CredentialException;
}
