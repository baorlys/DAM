package com.example.dam.service;

import com.example.dam.dto.UploadAssetDTO;
import com.example.dam.input.AssetInput;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface UploadService {


    UploadAssetDTO uploadLarge(AssetInput assetInput);

    String upload(AssetInput assetInput, String tenantId, String apiKey, String secretKey) throws IOException;
}
