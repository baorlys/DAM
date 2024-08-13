package com.example.dam.service;

import com.example.dam.service.thumbnail.IGenerateThumbnail;
import com.example.dam.service.thumbnail.VideoThumbnail;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ThumbnailTest {
    IGenerateThumbnail iGenerateThumbnail;

    @Test
    void videoThumbnail() throws IOException, InterruptedException {
         iGenerateThumbnail = new VideoThumbnail();
         String path = "src/test/resources/test.mp4";
         String outputPath = "src/test/resources/thumbnail/test.jpg";
         iGenerateThumbnail.generateThumbnail(path, outputPath, 300);
    }
}
