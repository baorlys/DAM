package com.example.dam.controller;

import com.example.dam.dto.UploadAssetDTO;
import com.example.dam.input.AssetInput;
import com.example.dam.service.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/upload-assets")
@AllArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @PostMapping("/large")
    public ResponseEntity<UploadAssetDTO> uploadLarge(@ModelAttribute AssetInput assetInput) {
        UploadAssetDTO dto = uploadService.uploadLarge(assetInput);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity upload(@ModelAttribute AssetInput assetInput) throws IOException {
        uploadService.upload(assetInput);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
