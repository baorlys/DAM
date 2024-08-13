package com.example.dam.service;


import com.example.dam.dto.AssetDTO;
import com.example.dam.global.mapper.DamMapper;
import com.example.dam.input.ConfigurationInput;
import com.example.dam.model.Asset;
import com.example.dam.repository.AssetRepository;
import com.example.dam.service.AccessService;
import com.example.dam.service.implement.GetAssetServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class GetAssetServiceImplTest {

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private AccessService accessService;

    @Mock
    private DamMapper mapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private GetAssetServiceImpl getAssetService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAsset_success() throws CredentialException, IOException, InterruptedException {
        // Setup
        String path = "cup-on-a-table.jpg";
        ConfigurationInput key = new ConfigurationInput("c1d4e15e-852c-4452-847d-0970cf36d1d4","apiKeyA", "secretKeyA");
        Map<String, String> options = new HashMap<>();
        Asset asset = assetRepository.findByFilePath(path);



        // Execute
        AssetDTO result = getAssetService.getAsset(key, path, options);

        // Verify
        assertNotNull(result);
        verify(accessService).isAccessible(key.getApiKey(), key.getSecretKey(), path);
        verify(assetRepository).findByFilePath(path);
        verify(mapper).map(asset, AssetDTO.class);
    }

    // Add more tests as needed
}
