package com.example.dam.repository;

import com.example.dam.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID> {

    Asset findByFilePath(String filePath);
}
