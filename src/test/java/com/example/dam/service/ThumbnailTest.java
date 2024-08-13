package com.example.dam.service;

import com.example.dam.global.service.CommonService;
import com.example.dam.service.thumbnail.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class ThumbnailTest {
    IGenerateThumbnail iGenerateThumbnail;
    @InjectMocks
    CommonService commonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void videoThumbnail() throws IOException, InterruptedException {
         iGenerateThumbnail = new VideoThumbnail();
         String path = "src/test/resources/test.mp4";
         String outputPath = commonService.addRandomId("src/test/resources/thumbnail/video.jpg");

         iGenerateThumbnail.generateThumbnail(path, outputPath, 300);
    }

    @Test
    void imageThumbnail() throws IOException, InterruptedException {
        iGenerateThumbnail = new ImageThumbnail();
        String path = "src/test/resources/cup-on-a-table.jpg";
        String outputPath = commonService.addRandomId("src/test/resources/thumbnail/image.jpg");

        iGenerateThumbnail.generateThumbnail(path, outputPath, 300);
    }

    @Test
    void pdfThumbnail() throws IOException, InterruptedException {
        iGenerateThumbnail = new DocumentThumbnail();
        String path = "src/test/resources/test.pdf";
        String outputPath = commonService.addRandomId("src/test/resources/thumbnail/pdf.jpg");

        iGenerateThumbnail.generateThumbnail(path, outputPath, 300);
    }

    @Test
    void docxThumbnail() throws IOException, InterruptedException {
        iGenerateThumbnail = new DocumentThumbnail();
        String path = "src/test/resources/test.docx";
        String outputPath = commonService.addRandomId("src/test/resources/thumbnail/docx.jpg");

        iGenerateThumbnail.generateThumbnail(path, outputPath, 300);
    }
}
