package com.example.dam.service;

import com.example.dam.dto.AssetDTO;
import com.example.dam.input.ConfigurationInput;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.util.Map;

public interface GetAssetService {
    AssetDTO getAsset(ConfigurationInput key, String path, Map<String, String> options) throws CredentialException, IOException, InterruptedException;


}
