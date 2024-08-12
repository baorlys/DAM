package com.example.dam.repository;

import com.example.dam.model.UserSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserSpaceRepository extends JpaRepository<UserSpace, UUID> {
    @Query("SELECT CASE WHEN COUNT(us) > 0 THEN TRUE ELSE FALSE END FROM UserSpace us WHERE us.user.id = :userId AND us.space.id = :spaceId")
    boolean hasAccess(UUID userId, UUID spaceId);
}
