package com.example.dam.service;

import com.example.dam.dto.AssetDTO;

import java.util.List;

public interface GetAssetService {
    List<AssetDTO> getAsset(String publicId);
}
