package com.example.dam.repository;

import com.example.dam.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, UUID> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Credential c WHERE c.apiKey = ?1 AND c.secretKey = ?2")
    boolean existsByApiKeyAndSecretKey(String apiKey, String secretKey);

    @Query("SELECT c FROM Credential c WHERE c.apiKey = ?1 AND c.secretKey = ?2")
    Credential findByApiKeyAndSecretKey(String apiKey, String secretKey);
}
