package com.example.dam.controller;

import com.example.dam.input.AssetInput;
import com.example.dam.service.UploadService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("api/upload-assets")
@AllArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity upload(@ModelAttribute AssetInput assetInput,
                                 @RequestHeader("X-API-Key") @NonNull String apikey,
                                 @RequestHeader("X-API-Secret") @NonNull String apiSecret,
                                 @RequestHeader("X-Space-ID") @NonNull String spaceId
    ) throws IOException {
        UUID space = UUID.fromString(spaceId);
        String url = uploadService.upload(assetInput, space, apikey, apiSecret);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }
}
