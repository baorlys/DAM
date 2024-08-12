package com.example.dam.service;

import com.example.dam.input.AssetInput;
import com.example.dam.model.Asset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MimeTypeUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UploadServiceTest {
    @Autowired
    private UploadService uploadService;

    private String apiKey;
    private String apiSecret;
    private UUID spaceId;

    private AssetInput assetInput;
    @BeforeEach
    void init() {
        apiKey = "apiKey1";
        apiSecret = "secretKey1";
        spaceId = UUID.fromString("47B11EDC-4A0C-4EA6-8D9C-7FD2B84C3C74");
        assetInput = new AssetInput();
//        Map<String, String> options = new HashMap<>();
//        options.put("folder_name", "New folder");
//        assetInput.setOptions(options);
    }

    @Test
    void testUploadVideo() throws IOException {
        MockMultipartFile videoFile = new MockMultipartFile("video-test", "video.mp4",
                MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE, new FileInputStream("src/main/resources/storage/tenant-1/dance-2.mp4"));
        assetInput.setFile(videoFile);
        String response = uploadService.upload(assetInput, spaceId, apiKey, apiSecret);
        assertNotNull(response);
    }

    @Test
    void testUploadImage() throws IOException {
        MockMultipartFile imageFile = new MockMultipartFile("image-test", "image.jpg",
                MimeTypeUtils.IMAGE_PNG_VALUE, new FileInputStream("src/main/resources/storage/tenant-2/nature-mountains.jpg"));
        assetInput.setFile(imageFile);
        String response = uploadService.upload(assetInput, spaceId, apiKey, apiSecret);
        assertNotNull(response);
    }

    @Test
    void testUploadGif() throws IOException {
        MockMultipartFile gifFile = new MockMultipartFile("gif-test", "animation.gif",
                MimeTypeUtils.IMAGE_GIF_VALUE, new FileInputStream("path/to/animation.gif"));
        assetInput.setFile(gifFile);
        String response = uploadService.upload(assetInput, spaceId, apiKey, apiSecret);
        assertNotNull(response);
    }

}
