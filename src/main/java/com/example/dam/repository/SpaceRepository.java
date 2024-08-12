package com.example.dam.repository;

import com.example.dam.model.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpaceRepository extends JpaRepository<Space, UUID> {
    Space findSpaceById(UUID id);
}
