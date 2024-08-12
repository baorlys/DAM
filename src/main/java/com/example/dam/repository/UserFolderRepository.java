package com.example.dam.repository;

import com.example.dam.model.UserFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserFolderRepository extends JpaRepository<UserFolder, UUID> {
}
