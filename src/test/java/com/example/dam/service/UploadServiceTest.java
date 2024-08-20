//package com.example.dam.service;
//
//import com.example.dam.input.AssetInput;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.util.MimeTypeUtils;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ActiveProfiles("test")
//class UploadServiceTest {
//    @Mock
//    private UploadService uploadService;
//    private String apiKey;
//    private String apiSecret;
//    private UUID spaceId;
//
//    private String imgUrl = "src/main/resources/storage/shoe.jpg";
//
//    private AssetInput assetInput;
//    @BeforeEach
//    void init() {
//        MockitoAnnotations.openMocks(this);
//        apiKey = "api_key_1";
//        apiSecret = "secret_key_1";
//        spaceId = UUID.fromString("FC19AD23-C41C-4DB8-BA96-0A60DA84137B");
//        assetInput = new AssetInput();
//        Map<String, String> options = new HashMap<>();
//        options.put("folder_name", "New folder");
//        assetInput.setMetadata(options);
//    }
//
//    @Test
//    void testUploadVideo() throws IOException {
//        MockMultipartFile videoFile = new MockMultipartFile("video-test", "video.mp4",
//                MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE, new FileInputStream("src/main/resources/storage/tenant-1/dance-2.mp4"));
//        assetInput.setFile(videoFile);
//        String response = uploadService.upload(assetInput, spaceId, apiKey, apiSecret);
//        assertNotNull(response);
//    }
//
//    @Test
//    void testUploadImage() throws IOException {
//        MockMultipartFile imageFile = new MockMultipartFile("image-test", "image.jpg",
//                MimeTypeUtils.IMAGE_PNG_VALUE, new FileInputStream(imgUrl));
//        assetInput.setFile(imageFile);
//        String response = uploadService.upload(assetInput, spaceId, apiKey, apiSecret);
//        System.out.println(response);
//        assertNotNull(response);
//    }
//
//    @Test
//    void testUploadGif() throws IOException {
//        MockMultipartFile gifFile = new MockMultipartFile("gif-test", "animation.gif",
//                MimeTypeUtils.IMAGE_GIF_VALUE, new FileInputStream("path/to/animation.gif"));
//        assetInput.setFile(gifFile);
//        String response = uploadService.upload(assetInput, spaceId, apiKey, apiSecret);
//        assertNotNull(response);
//    }
//
//}
