package com.example.dam.service;

import com.example.dam.input.AssetInput;

import java.io.IOException;
import java.util.UUID;

public interface UploadService {
    String upload(AssetInput assetInput, UUID spaceId, String apikey, String apiSecret) throws IOException;
}
