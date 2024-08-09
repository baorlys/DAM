package com.example.dam.repository;

import com.example.dam.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CredentialRepository extends JpaRepository<Credential, UUID> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Credential c WHERE c.tenant.id = ?1 AND c.apiKey = ?2 AND c.apiSecret = ?3")
    boolean exists(String tenantId, String apiKey, String secretKey);
}
