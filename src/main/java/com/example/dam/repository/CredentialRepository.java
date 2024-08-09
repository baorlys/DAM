package com.example.dam.repository;

import com.example.dam.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CredentialRepository extends JpaRepository<Credential, UUID> {
    Credential findCredentialByApiKeyAndAndApiSecret(String apiKey, String apiSecret);
}
