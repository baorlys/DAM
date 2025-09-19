package com.example.dam.service;

import com.example.dam.config.StorageProperties;
import com.example.dam.dto.AssetDTO;
import com.example.dam.input.AssetInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.MimeTypeUtils;

import javax.security.auth.login.CredentialException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
class UploadServiceTest {
    @Mock
    private UploadService uploadService;
    @Mock
    private StorageProperties storageProperties;
    private String apiKey;
    private String apiSecret;
    private UUID spaceId;
    private final String IMAGE_URL = "shoe.jpg";
    private final String VIDEO_URL = "dance-2.mp4";
    private final String GIF_URL = "test.pdf";
    private AssetInput assetInput;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        apiKey = "api_key_1";
        apiSecret = "secret_key_1";
        spaceId = UUID.fromString("FC19AD23-C41C-4DB8-BA96-0A60DA84137B");
        assetInput = new AssetInput();
        Map<String, String> options = new HashMap<>();
        options.put("folder_name", "New folder");
        assetInput.setMetadata(options);
    }

    @Test
    void testUploadVideo() throws IOException, CredentialException, InterruptedException {
        MockMultipartFile videoFile = new MockMultipartFile("video-test", "video.mp4",
                MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE,
                new FileInputStream(storageProperties.getPath() + VIDEO_URL));
        assetInput.setFile(videoFile);
        AssetDTO response = uploadService.upload(assetInput, spaceId, apiKey, apiSecret);
        assertNotNull(response);
    }

    @Test
    void testUploadImage() throws IOException, CredentialException, InterruptedException {
        MockMultipartFile imageFile = new MockMultipartFile("image-test", "image.jpg",
                MimeTypeUtils.IMAGE_PNG_VALUE,
                new FileInputStream(storageProperties.getPath() + IMAGE_URL));
        assetInput.setFile(imageFile);
        AssetDTO response = uploadService.upload(assetInput, spaceId, apiKey, apiSecret);
        assertNotNull(response);
    }

    @Test
    void testUploadGif() throws IOException, CredentialException, InterruptedException {
        MockMultipartFile gifFile = new MockMultipartFile("gif-test", "animation.gif",
                MimeTypeUtils.IMAGE_GIF_VALUE,
                new FileInputStream(storageProperties.getPath() + GIF_URL));
        assetInput.setFile(gifFile);
        AssetDTO response = uploadService.upload(assetInput, spaceId, apiKey, apiSecret);
        assertNotNull(response);
    }

}
