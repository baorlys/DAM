package com.example.dam.service;

import com.example.dam.enums.TargetLevel;
import com.example.dam.input.UserCredentials;
import com.example.dam.model.Credential;
import com.example.dam.model.Role;
import com.example.dam.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User registerUser(User user);
    String authenticateUser(UserCredentials credentials);
    boolean assignRoleToUser(UUID userId, Role role, TargetLevel level, UUID targetId);
    boolean removeRoleFromUser(UUID userId, Role role, TargetLevel level, UUID targetId);

    List<Credential> getUserCredentials(UUID userId);
    Credential createCredential(UUID tenantId, UUID userId);
    void deleteCredential(UUID credentialId);
    void updateCredential(UUID credentialId, Credential credential);

}
