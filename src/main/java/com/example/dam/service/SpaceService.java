package com.example.dam.service;

import com.example.dam.model.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpaceService extends JpaRepository<Space, UUID> {
}
