package com.example.dam.repository;

import com.example.dam.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID> {
    @Query("SELECT a FROM Asset a WHERE a.filePath = ?1")
    Asset findByFilePath(String filePath);


    List<Asset> findAllByTenantId(UUID tenantId);
}