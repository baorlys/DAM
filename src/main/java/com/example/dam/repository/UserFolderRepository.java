package com.example.dam.repository;

import com.example.dam.model.Role;
import com.example.dam.model.UserFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserFolderRepository extends JpaRepository<UserFolder, UUID> {
    @Query("SELECT CASE WHEN COUNT(uf) > 0 THEN TRUE ELSE FALSE END FROM UserFolder uf WHERE uf.user.id = :userId AND uf.folder.id = :folderId")
    boolean hasAccess(UUID userId, UUID folderId);

    @Query("SELECT uf.role FROM UserFolder uf WHERE uf.user.id = :userId AND uf.folder.id = :folderId")
    Role getRoleInFolder(UUID userId, UUID folderId);
}
