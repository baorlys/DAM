package com.example.dam.service;


import com.example.dam.dto.AssetDTO;
import com.example.dam.exception.NotFoundException;
import com.example.dam.global.mapper.DamMapper;
import com.example.dam.input.ConfigurationInput;
import com.example.dam.model.Asset;
import com.example.dam.repository.AssetRepository;
import com.example.dam.repository.SpaceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    SpaceRepository spaceRepository;


    @Mock
    private DamMapper mapper;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private GetAssetService getAssetService;


    @Mock
    private AccessService accessService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAsset_success() throws CredentialException, IOException, InterruptedException, NotFoundException {
        // Setup
        String path = "cup-on-a-table.jpg";
        ConfigurationInput key = new ConfigurationInput("C1D4E15E-852C-4452-847D-0970CF36D1D4","apiKeyA", "secretKeyA");
        Map<String, String> options = new HashMap<>();
        Asset asset = assetRepository.findByFilePath("src/main/resources/storage/test-image.jpg");


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
