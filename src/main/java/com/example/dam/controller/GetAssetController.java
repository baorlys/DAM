package com.example.dam.controller;

import com.example.dam.dto.AssetDTO;
import com.example.dam.input.ConfigurationInput;
import com.example.dam.service.GetAssetService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotEmpty;
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
public class GetAssetController {
    GetAssetService getAssetService;


    @GetMapping("/{path}")
    public ResponseEntity<AssetDTO> getAsset(@PathVariable String path,
                                             @RequestParam @Nullable Map<String, String> options,
                                             @RequestHeader("X-Space-ID") @NotEmpty String spaceId,
                                             @RequestHeader("X-API-Key") @NotEmpty String apiKey,
                                             @RequestHeader("X-Secret-Key") @NotEmpty String secretKey)
            throws CredentialException, IOException, InterruptedException {
        ConfigurationInput key = new ConfigurationInput();
        key.setSpaceId(spaceId);
        key.setApiKey(apiKey);
        key.setSecretKey(secretKey);
        return ResponseEntity.ok(getAssetService.getAsset(key, path, options));
    }

    @GetMapping("/{spaceId}/image/{path}")
    public ResponseEntity<InputStreamResource> getImageFile(@PathVariable String spaceId,
                                                  @PathVariable String path)
        throws CredentialException, IOException, InterruptedException {

        MultipartFile multipartFile = getAssetService.getAssetFile(spaceId, "image",  path, Map.of());

        // Create an InputStreamResource from the MultipartFile input stream
        InputStream inputStream = multipartFile.getInputStream();

        // Determine the media type (content type) based on the file extension
        String contentType = multipartFile.getContentType();
        // Build the HTTP headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + multipartFile.getOriginalFilename());

        // Return the file as a ResponseEntity with an InputStreamResource
        return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
    }

    @GetMapping("/{spaceId}/video/{path}")
    public ResponseEntity<Resource> streamVideo(@PathVariable String spaceId, @PathVariable String path, HttpServletRequest request)
            throws IOException, InterruptedException, CredentialException {
        // Retrieve the file path
        String filePath = getAssetService.getFilePath(spaceId, path); // Adjust this method to return the file path
        File file = new File(filePath);

        // Read file content into a byte array
        byte[] fileBytes = Files.readAllBytes(file.toPath());

        String contentType = getAssetService.getAssetFile(spaceId, "video",  path, Map.of()).getContentType();

        // Handle range requests for partial content
        String rangeHeader = request.getHeader(HttpHeaders.RANGE);
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String[] ranges = rangeHeader.substring("bytes=".length()).split("-");
            long start = Long.parseLong(ranges[0]);
            long end = ranges.length > 1 ? Long.parseLong(ranges[1]) : fileBytes.length - 1;

            if (start > end || start >= fileBytes.length) {
                return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).build();
            }

            // Create a subarray of the file bytes
            byte[] partialContent = new byte[(int) (end - start + 1)];
            System.arraycopy(fileBytes, (int) start, partialContent, 0, partialContent.length);

            // Return the partial content
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
            headers.add(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileBytes.length);
            headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");
            headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(partialContent.length));

            return new ResponseEntity<>(new ByteArrayResource(partialContent), headers, HttpStatus.PARTIAL_CONTENT);
        }

        // Return the entire file if no range is requested
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileBytes.length));

        return new ResponseEntity<>(new ByteArrayResource(fileBytes), headers, HttpStatus.OK);
    }

}
