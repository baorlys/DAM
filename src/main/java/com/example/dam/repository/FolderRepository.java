package com.example.dam.repository;

import com.example.dam.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, UUID> {
}
