package com.example.dam.service;

import com.example.dam.dto.UploadAssetDTO;
import com.example.dam.input.AssetInput;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public interface UploadService {
    String upload(AssetInput assetInput, UUID spaceId, String apikey, String apiSecret) throws IOException;
}
