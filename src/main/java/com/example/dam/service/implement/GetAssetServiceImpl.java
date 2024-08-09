package com.example.dam.service.implement;

import com.example.dam.dto.AssetDTO;
import com.example.dam.model.Credential;
import com.example.dam.repository.AssetRepository;
import com.example.dam.repository.CredentialRepository;
import com.example.dam.service.GetAssetService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAssetServiceImpl implements GetAssetService {
    AssetRepository assetRepository;
    CredentialRepository credentialRepository;


    private boolean validKey(Credential credential) {
        return credentialRepository.exists(Example.of(credential));
    }

    @Override
    public List<AssetDTO> getAsset() {

        return null;
    }




}
