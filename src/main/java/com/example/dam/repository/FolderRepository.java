package com.example.dam.repository;

import com.example.dam.model.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FolderRepository extends JpaRepository<Folder, UUID> {
    Folder findFolderByNameAndTenant_Id(String folderName, UUID tenantId);

    Page<Folder> findFoldersByTenant_Id(UUID tenantId, Pageable pageable);
}
