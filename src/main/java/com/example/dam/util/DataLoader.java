package com.example.dam.util;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.dam.model.*;
import com.example.dam.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final SpaceRepository spaceRepository;
    private final FolderRepository folderRepository;
    private final RoleRepository roleRepository;
    private final AssetRepository assetRepository;
    private final CredentialRepository credentialRepository;
    private final UserFolderRepository userFolderRepository;
    private final UserSpaceRepository userSpaceRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() < 1) {

            // Create sample Users
            User user1 = new User(UUID.randomUUID(), "user1", "user1@example.com", "password1", LocalDateTime.now(), LocalDateTime.now());
            User user2 = new User(UUID.randomUUID(), "user2", "user2@example.com", "password2", LocalDateTime.now(), LocalDateTime.now());
            userRepository.save(user1);
            userRepository.save(user2);

            // Create sample Tenants
            Tenant tenantA = new Tenant(UUID.randomUUID(), "tenant-1", user1, LocalDateTime.now(), LocalDateTime.now());
            Tenant tenantB = new Tenant(UUID.randomUUID(), "tenant-2", user2, LocalDateTime.now(), LocalDateTime.now());
            tenantRepository.save(tenantA);
            tenantRepository.save(tenantB);

            // Create sample Spaces
            Space space1 = new Space(UUID.fromString("47B11EDC-4A0C-4EA6-8D9C-7FD2B84C3C74"), tenantA, "Space 1", LocalDateTime.now(), LocalDateTime.now());
            Space space2 = new Space(UUID.randomUUID(), tenantB, "Space 2", LocalDateTime.now(), LocalDateTime.now());
            spaceRepository.save(space1);
            spaceRepository.save(space2);

            // Create sample Folders
            Folder folder1 = new Folder(UUID.fromString("AEA784B4-B727-49D0-BBA2-5A49BC5B5F72"), space1, null, "Folder 1", LocalDateTime.now(), LocalDateTime.now());
            Folder folder2 = new Folder(UUID.randomUUID(), space2, null, "Folder 2", LocalDateTime.now(), LocalDateTime.now());
            folderRepository.save(folder1);
            folderRepository.save(folder2);

            // Create sample Roles
            Role roleAdmin = new Role(UUID.randomUUID(), "Admin", 1);
            Role roleUser = new Role(UUID.randomUUID(), "User", 2);
            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);

            // Create sample Assets
            Asset asset1 = new Asset(UUID.randomUUID(), space1, folder1, "Asset 1", "/path/to/asset1", "metadata1");
            Asset asset2 = new Asset(UUID.randomUUID(), space2, folder2, "Asset 2", "/path/to/asset2", "metadata2");
            assetRepository.save(asset1);
            assetRepository.save(asset2);

            // Create sample Credentials
            Credential credential1 = new Credential(UUID.randomUUID(), user1, "apiKey1", "secretKey1", LocalDateTime.now(), LocalDateTime.now(), Credential.Status.ACTIVE);
            Credential credential2 = new Credential(UUID.randomUUID(), user2, "apiKey2", "secretKey2", LocalDateTime.now(), LocalDateTime.now(), Credential.Status.INACTIVE);
            credentialRepository.save(credential1);
            credentialRepository.save(credential2);

            // Create sample UserFolders
            UserFolder userFolder1 = new UserFolder(UUID.randomUUID(), user1, folder1, UserFolder.Access.READ);
            UserFolder userFolder2 = new UserFolder(UUID.randomUUID(), user2, folder2, UserFolder.Access.WRITE);
            userFolderRepository.save(userFolder1);
            userFolderRepository.save(userFolder2);

            // Create sample UserSpaces
            UserSpace userSpace1 = new UserSpace(UUID.randomUUID(), user1, space1, roleAdmin, LocalDateTime.now());
            UserSpace userSpace2 = new UserSpace(UUID.randomUUID(), user2, space2, roleUser, LocalDateTime.now());
            userSpaceRepository.save(userSpace1);
            userSpaceRepository.save(userSpace2);
        }
    }
}
