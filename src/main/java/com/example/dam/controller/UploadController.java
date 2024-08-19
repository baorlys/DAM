package com.example.dam.controller;

import com.example.dam.dto.AssetDTO;
import com.example.dam.input.AssetInput;
import com.example.dam.service.UploadService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("api/upload-assets")
@AllArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssetDTO> upload(@ModelAttribute AssetInput assetInput,
                                           @RequestHeader("X-API-Key") @NonNull String apiKey,
                                           @RequestHeader("X-Secret-Key") @NonNull String secretKey,
                                           @RequestHeader("X-Tenant-ID") @NonNull String tenantId
    ) throws IOException, CredentialException, InterruptedException {
        UUID tenant = UUID.fromString(tenantId);
        AssetDTO dto = uploadService.upload(assetInput, tenant, apiKey, secretKey);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
