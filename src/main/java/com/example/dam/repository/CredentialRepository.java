package com.example.dam.repository;

import com.example.dam.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CredentialRepository extends JpaRepository<Credential, UUID> {
     Optional<Credential> findCredentialByApiKeyAndSecretKey(String apiKey, String secretKet);
}
