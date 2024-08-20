package com.example.dam.service.implement;

import com.example.dam.enums.TargetLevel;
import com.example.dam.input.UserCredentials;
import com.example.dam.model.Credential;
import com.example.dam.model.Role;
import com.example.dam.model.User;
import com.example.dam.repository.CredentialRepository;
import com.example.dam.repository.UserFolderRepository;
import com.example.dam.repository.UserRepository;
import com.example.dam.repository.UserSpaceRepository;
import com.example.dam.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    CredentialRepository credentialRepository;
    UserRepository userRepository;

    UserSpaceRepository userSpaceRepository;

    UserFolderRepository userFolderRepository;




    @Override
    public User registerUser(User user) {
        return null;
    }

    @Override
    public String authenticateUser(UserCredentials credentials) {
        return null;
    }

    @Override
    public boolean assignRoleToUser(UUID userId, Role role, TargetLevel level, UUID targetId) {
        return false;
    }

    @Override
    public boolean removeRoleFromUser(UUID userId, Role role, TargetLevel level, UUID targetId) {
        return false;
    }

    @Override
    public List<Credential> getUserCredentials(UUID userId) {
        return null;
    }

    @Override
    public Credential createCredential(UUID tenantId, UUID userId) {
        return null;
    }


    @Override
    public void deleteCredential(UUID credentialId) {

    }

    @Override
    public void updateCredential(UUID credentialId, Credential credential) {

    }

    public Role getRoleUserIn(UUID userId, TargetLevel level, UUID targetId) {
        Map<TargetLevel, Supplier<Role>> roleMap = Map.of(
                TargetLevel.TENANT, () -> userRepository.getRoleInTenant(userId, targetId),
                TargetLevel.SPACE, () -> userSpaceRepository.getRoleInSpace(userId, targetId),
                TargetLevel.FOLDER, () -> userFolderRepository.getRoleInFolder(userId, targetId)
        );
        return roleMap.get(level).get();
    }
}
