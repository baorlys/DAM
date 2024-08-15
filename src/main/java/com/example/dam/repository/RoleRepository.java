package com.example.dam.repository;

import com.example.dam.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findRoleByNameAndTenant_Id(String name, UUID tenantId);
}
