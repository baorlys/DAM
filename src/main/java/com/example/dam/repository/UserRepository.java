package com.example.dam.repository;

import com.example.dam.model.Role;
import com.example.dam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findUserByEmail(String email);

    @Query("SELECT r FROM User u JOIN u.role r WHERE u.id = :userId AND r.tenant.id = :tenantId")
    Role getRoleInTenant(UUID userId, UUID tenantId);
}
