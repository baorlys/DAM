package com.example.dam.repository;

import com.example.dam.model.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SpaceRepository extends JpaRepository<Space, UUID> {
}
