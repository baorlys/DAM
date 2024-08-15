package com.example.dam.service.implement;

import com.example.dam.model.Asset;
import com.example.dam.model.Credential;
import com.example.dam.model.User;
import com.example.dam.repository.*;
import com.example.dam.service.AccessService;
import com.example.dam.global.service.CommonService;
import graphql.com.google.common.base.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccessServiceImpl implements AccessService {
    AssetRepository assetRepository;
    CredentialRepository credentialRepository;

    UserFolderRepository userFolderRepository;

    UserSpaceRepository userSpaceRepository;

    @Override
    public boolean isValidKey(String apiKey, String secretKey) {
        return credentialRepository.existsByApiKeyAndSecretKey(apiKey, secretKey);
    }

    @Override
    public boolean isAccessible(String apiKey, String secretKey, String filePath) throws CredentialException {
        Credential credential = credentialRepository.findByApiKeyAndSecretKey(apiKey, secretKey);
        CommonService.throwNotFound(credential, "Credential not found");

        User user = credential.getUser();

        Asset asset = assetRepository.findByFilePath(filePath);
        CommonService.throwNotFound(asset, "Asset not found");

        boolean hasAccessTenant = asset.getTenant().getId().equals(user.getTenant().getId());
        CommonService.throwIsNotExists(!hasAccessTenant, "User does not have access to the tenant");

        boolean hasAccessSpace = Optional.of(asset.getSpace())
                .transform(space -> userSpaceRepository.hasAccess(user.getId(), space.getId()))
                .or(true);
        CommonService.throwIsNotExists(!hasAccessSpace, "User does not have access to the space");

        boolean hasAccessFolder = Optional.of(asset.getFolder())
                .transform(folder -> userFolderRepository.hasAccess(user.getId(), folder.getId()))
                .or(true);
        CommonService.throwIsNotExists(!hasAccessFolder, "User does not have access to the folder");

        return true;


    }
}
