package com.example.dam.repository;

import com.example.dam.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID> {
    @Query("SELECT a FROM Asset a WHERE a.publicId = :publicId")
    Asset findByPublicId(String publicId);
}
