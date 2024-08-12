package com.example.dam.service.implement;

import com.example.dam.dto.AssetDTO;
import com.example.dam.enums.ResourceType;
import com.example.dam.enums.TransformVariable;
import com.example.dam.global.mapper.DamMapper;
import com.example.dam.input.ConfigurationInput;
import com.example.dam.model.Asset;
import com.example.dam.repository.AssetRepository;
import com.example.dam.service.CommonService;
import com.example.dam.service.AccessService;
import com.example.dam.service.GetAssetService;
import com.example.dam.service.implement.transform.ITransformable;
import com.example.dam.service.implement.transform.TransformFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAssetServiceImpl implements GetAssetService {
    AssetRepository assetRepository;
    AccessService accessService;

    DamMapper mapper;

    ObjectMapper objectMapper;


    @Override
    public AssetDTO getAsset(ConfigurationInput key, String path, Map<TransformVariable, String> options)
            throws CredentialException, IOException, InterruptedException {
        boolean accessible = accessService.isAccessible(key.getApiKey(), key.getSecretKey(), path);
        CommonService.throwIsNotExists(!accessible, "Asset not accessible");

        Asset asset = assetRepository.findByFilePath(path);

        ResourceType resourceType = ResourceType.valueOf(getAssetMetadata(path, "resourceType").toUpperCase());
        ITransformable transformable = TransformFactory.getTransform(resourceType);

        String transformedPath = transformable.transform(path, options);

        asset.setFilePath(transformedPath);
        return mapper.map(asset, AssetDTO.class);
    }


    public Map<String, String> getAssetMetadata(String path) throws JsonProcessingException {
        Asset asset = assetRepository.findByFilePath(path);
        return objectMapper.readValue(asset.getMetadata(), new TypeReference<>() {});
    }

    public String getAssetMetadata(String path, String key) throws JsonProcessingException {
        Asset asset = assetRepository.findByFilePath(path);
        Map<String, String> metadata = objectMapper.readValue(asset.getMetadata(), new TypeReference<>() {});
        return metadata.get(key);
    }

}
