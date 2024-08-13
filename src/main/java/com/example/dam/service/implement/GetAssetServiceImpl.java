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
import com.example.dam.service.transform.ITransformable;
import com.example.dam.service.transform.TransformFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAssetServiceImpl implements GetAssetService {
    @Value("${storage.path}")
    final String storagePath;
    @Value("${transformed.path}")
    final String transformedPath;
    AssetRepository assetRepository;
    AccessService accessService;

    DamMapper mapper;

    ObjectMapper objectMapper;


    @Override
    public AssetDTO getAsset(ConfigurationInput key, String path, Map<String, String> options)
            throws CredentialException, IOException, InterruptedException {
        path = storagePath + path;
        boolean accessible = accessService.isAccessible(key.getApiKey(), key.getSecretKey(), path);
        CommonService.throwIsNotExists(!accessible, "Asset not accessible");

        Asset asset = assetRepository.findByFilePath(path);
        Map<String, String> metadata = objectMapper.readValue(asset.getMetadata(), new TypeReference<>() {});
        // Transform the asset if necessary
        ResourceType resourceType = ResourceType.valueOf(metadata.get("resourceType").toUpperCase());
        ITransformable transformable = TransformFactory.getTransform(resourceType);

        String outputPath = transformedPath + asset.getFilePath();
        transformable.transform(path,outputPath, convertToTransformVariable(options));

        asset.setFilePath(transformedPath);
        return mapper.map(asset, AssetDTO.class);
    }


    private Map<TransformVariable, String> convertToTransformVariable(Map<String, String> options) {
        Map<TransformVariable, String> transformVariables = new EnumMap<>(TransformVariable.class);
        for (Map.Entry<String, String> entry : options.entrySet()) {
            transformVariables.put(TransformVariable.fromShortCut(entry.getKey()), entry.getValue());
        }
        return transformVariables;
    }



}
