package com.example.dam.repository;

import com.example.dam.model.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FolderRepository extends JpaRepository<Folder, UUID> {
    Folder findFolderByNameAndTenant_Id(String folderName, UUID tenantId);

    @Query("SELECT f from Folder f JOIN UserFolder uf on f.id = uf.folder.id WHERE f.tenant.id = ?1 AND uf.user.id = ?2")
    Page<Folder> findFoldersByTenantAndUser(UUID tenantId, UUID userId, Pageable pageable);


    Page<Folder> findFoldersByTenant_Id(UUID tenantId, Pageable pageable);
}
