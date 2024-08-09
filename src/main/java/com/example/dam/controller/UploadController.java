package com.example.dam.controller;

import com.example.dam.dto.UploadAssetDTO;
import com.example.dam.input.AssetInput;
import com.example.dam.service.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/upload-assets")
@AllArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @PostMapping("/large")
    public ResponseEntity<UploadAssetDTO> uploadLarge(@RequestBody AssetInput assetInput) {
        UploadAssetDTO dto = uploadService.uploadLarge(assetInput);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<UploadAssetDTO> upload(@RequestBody AssetInput assetInput) throws IOException {
        UploadAssetDTO dto = uploadService.upload(assetInput);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
