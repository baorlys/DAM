package com.example.dam.service;

import com.example.dam.dto.UploadAssetDTO;
import com.example.dam.input.AssetInput;
import com.example.dam.input.ConfigurationInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MimeTypeUtils;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UploadServiceTest {
    @Autowired
    private UploadService uploadService;

    private ConfigurationInput configurationInput;

    @BeforeEach
    void init() {
        configurationInput = new ConfigurationInput();
        configurationInput.setTenantId("tenant");
        configurationInput.setApiKey("apikey");
        configurationInput.setApiSecret("secret");
    }
    @Test
    void testUploadVideo() throws IOException {
        MockMultipartFile videoFile = new MockMultipartFile("video", "video.mp4",
                MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE, new FileInputStream("src/main/resources/storage/tenant-1/dance-2.mp4"));
        AssetInput assetInput = new AssetInput();
        assetInput.setFile(videoFile);
        assetInput.setConfiguration(configurationInput);
        UploadAssetDTO response = uploadService.upload(assetInput);
        assertNotNull(response);
    }

    @Test
    void testUploadFilePDF() throws IOException {
//        MockMultipartFile pdfFile = new MockMultipartFile("pdf", "document.pdf",
//                MimeTypeUtils.APPLICATION_PDF_VALUE, new FileInputStream("path/to/document.pdf"));
//        AssetInput assetInput = new AssetInput();
//        assetInput.setFile(videoFile);
//        assetInput.setConfiguration(configurationInput);
//        AssetDTO response = uploadService.upload(assetInput);
//        assertNotNull(response);
    }

    @Test
    void testUploadImage() throws IOException {
        MockMultipartFile imageFile = new MockMultipartFile("image", "image.jpg",
                MimeTypeUtils.IMAGE_PNG_VALUE, new FileInputStream("src/main/resources/storage/tenant-2/nature-mountains.jpg"));
        AssetInput assetInput = new AssetInput();
        assetInput.setFile(imageFile);
        assetInput.setConfiguration(configurationInput);
        UploadAssetDTO response = uploadService.upload(assetInput);
        assertNotNull(response);
    }

    @Test
    void testUploadEmoji() throws IOException {
        MockMultipartFile emojiFile = new MockMultipartFile("emoji", "emoji.png",
                MimeTypeUtils.IMAGE_PNG_VALUE, new FileInputStream("path/to/emoji.png"));
        AssetInput assetInput = new AssetInput();
        assetInput.setFile(emojiFile);
        assetInput.setConfiguration(configurationInput);
        UploadAssetDTO response = uploadService.upload(assetInput);
        assertNotNull(response);
    }

    @Test
    void testUploadGif() throws IOException {
        MockMultipartFile gifFile = new MockMultipartFile("gif", "animation.gif",
                MimeTypeUtils.IMAGE_GIF_VALUE, new FileInputStream("path/to/animation.gif"));
        AssetInput assetInput = new AssetInput();
        assetInput.setFile(gifFile);
        assetInput.setConfiguration(configurationInput);
        UploadAssetDTO response = uploadService.upload(assetInput);
        assertNotNull(response);
    }

}
