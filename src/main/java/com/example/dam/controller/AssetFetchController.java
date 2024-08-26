package com.example.dam.controller;

import com.example.dam.enums.AssetType;
import com.example.dam.exception.NotFoundException;
import com.example.dam.service.AssetFetchService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.CredentialException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

@RestController
@RequestMapping("api/get-assets")
@AllArgsConstructor
@Validated
public class AssetFetchController {
    AssetFetchService assetFetchService;

    static final String HEADER_VALUE = "inline; filename=";

    @GetMapping("/{tenantId}/thumbnail/{path}")
    public ResponseEntity<InputStreamResource> getThumbnailFile(@PathVariable String tenantId,
                                                                @PathVariable String path,
                                                                @RequestParam @Nullable Map<String, String> options)
            throws CredentialException, IOException, InterruptedException, NotFoundException {

        return getFile(tenantId, AssetType.THUMBNAIL.getValue(), path, options);
    }

    @GetMapping("/{tenantId}/image/{path}")
    public ResponseEntity<InputStreamResource> getImageFile(@PathVariable String tenantId,
                                                            @PathVariable String path,
                                                            @RequestParam @Nullable Map<String, String> options)
            throws CredentialException, IOException, InterruptedException, NotFoundException {

        return getFile(tenantId, AssetType.IMAGE.getValue(), path, options);
    }

    private ResponseEntity<InputStreamResource> getFile(String tenantId,
                                                        String type,
                                                        String path,
                                                        @Nullable Map<String, String> options)
            throws CredentialException, IOException, InterruptedException, NotFoundException {

        MultipartFile multipartFile = assetFetchService.getAssetFile(tenantId, type, path, options);

        return createResponseEntity(
                multipartFile.getInputStream(),
                multipartFile.getContentType(),
                multipartFile.getOriginalFilename(),
                multipartFile.getSize()
        );
    }

    @GetMapping("/{tenantId}/video/{path}")
    public ResponseEntity<Resource> streamVideo(@PathVariable String tenantId,
                                                @PathVariable String path,
                                                @RequestParam @Nullable Map<String, String> options,
                                                HttpServletRequest request)
            throws IOException, InterruptedException, CredentialException, NotFoundException {

        String filePath = assetFetchService.getFilePath(tenantId, path);
        File file = new File(filePath);

        byte[] fileBytes = Files.readAllBytes(file.toPath());
        String contentType = assetFetchService
                .getAssetFile(tenantId, AssetType.VIDEO.getValue(), path, options)
                .getContentType();

        String rangeHeader = request.getHeader(HttpHeaders.RANGE);
        return createRangeResponseEntity(rangeHeader, fileBytes, contentType, file.getName());
    }

    private ResponseEntity<InputStreamResource> createResponseEntity(InputStream inputStream,
                                                                     String contentType,
                                                                     String filename,
                                                                     long size) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, HEADER_VALUE + filename);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(size));

        return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
    }

    private ResponseEntity<Resource> createRangeResponseEntity(String rangeHeader,
                                                               byte[] fileBytes,
                                                               String contentType,
                                                               String filename) {
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            return createPartialContentResponse(rangeHeader, fileBytes, contentType, filename);
        }
        return createFullContentResponse(fileBytes, contentType, filename);
    }

    // Handle streaming of video files
    private ResponseEntity<Resource> createPartialContentResponse(String rangeHeader,
                                                                  byte[] fileBytes,
                                                                  String contentType,
                                                                  String filename) {
        String[] ranges = rangeHeader.substring("bytes=".length()).split("-");
        long start = Long.parseLong(ranges[0]);
        long end = ranges.length > 1 ? Long.parseLong(ranges[1]) : fileBytes.length - 1;

        byte[] partialContent = new byte[(int) (end - start + 1)];
        System.arraycopy(fileBytes, (int) start, partialContent, 0, partialContent.length);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        headers.add(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileBytes.length);
        headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(partialContent.length));
        headers.add(HttpHeaders.CONTENT_DISPOSITION, HEADER_VALUE + filename);

        return new ResponseEntity<>(new ByteArrayResource(partialContent), headers, HttpStatus.PARTIAL_CONTENT);
    }

    private ResponseEntity<Resource> createFullContentResponse(byte[] fileBytes,
                                                               String contentType,
                                                               String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileBytes.length));
        headers.add(HttpHeaders.CONTENT_DISPOSITION, HEADER_VALUE + filename);

        return new ResponseEntity<>(new ByteArrayResource(fileBytes), headers, HttpStatus.OK);
    }


}
