package com.example.dam.controller;

import com.example.dam.dto.AssetDTO;
import com.example.dam.enums.TransformVariable;
import com.example.dam.input.ConfigurationInput;
import com.example.dam.service.GetAssetService;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api/get-assets")
@AllArgsConstructor
public class GetAssetController {
    GetAssetService getAssetService;


    @GetMapping("")
    public ResponseEntity<AssetDTO> getAsset(@RequestParam @NotEmpty String path,
                                             @RequestParam Map<TransformVariable,String> options,
                                             @RequestHeader("X-Space-ID") @NotEmpty String spaceId,
                                             @RequestHeader("X-API-Key") @NotEmpty String apiKey,
                                             @RequestHeader("X-Secret-Key") @NotEmpty String secretKey)
            throws CredentialException, IOException, InterruptedException {
        ConfigurationInput key = new ConfigurationInput();
        key.setSpaceId(spaceId);
        key.setApiKey(apiKey);
        key.setSecretKey(secretKey);
        return ResponseEntity.ok(getAssetService.getAsset(key,path,options));
    }




}
