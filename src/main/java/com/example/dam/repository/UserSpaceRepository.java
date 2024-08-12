package com.example.dam.repository;

import com.example.dam.model.UserSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserSpaceRepository extends JpaRepository<UserSpace, UUID> {
}
