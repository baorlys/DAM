package com.example.dam.service;

import com.example.dam.dto.UploadAssetDTO;
import com.example.dam.input.AssetInput;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface UploadService {
    UploadAssetDTO upload(AssetInput assetInput) throws IOException;

    UploadAssetDTO uploadLarge(AssetInput assetInput);
}
