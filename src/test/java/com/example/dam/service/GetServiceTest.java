package com.example.dam.service;

import com.example.dam.dto.AssetDTO;
import com.example.dam.model.Credential;
import com.example.dam.model.Tenant;
import com.example.dam.repository.AssetRepository;
import com.example.dam.repository.CredentialRepository;
import com.example.dam.service.implement.GetAssetServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetServiceTest {
    @InjectMocks
    private GetAssetServiceImpl getService;

    @Mock
    private CredentialRepository credentialRepository;

    @Mock
    private AssetRepository assetRepository;

    @Test
    void testValidKey() {
        String publicId = "publicId";
        UUID tenantId = UUID.randomUUID();
        Tenant tenant = new Tenant();
        tenant.setId(tenantId);
        String apiKey = "key";
        String apiSecret = "secret";
        Credential credential = new Credential();
        credential.setTenant(tenant);
        credential.setApiKey(apiKey);
        credential.setApiSecret(apiSecret);


        when(credentialRepository.exists(Example.of(credential))).thenReturn(true);
        when(credentialRepository.findOne(Example.of(credential))).thenReturn(Optional.of(credential));
        when(assetRepository.findByPublicId(publicId)).thenReturn(null);


        verify(credentialRepository, times(1)).exists(Example.of(credential));
        verify(credentialRepository, times(1)).findOne(Example.of(credential));
        verify(assetRepository, times(1)).findByPublicId(publicId);



    }


}
