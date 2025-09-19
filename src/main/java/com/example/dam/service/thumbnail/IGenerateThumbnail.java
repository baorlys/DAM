package com.example.dam.service.thumbnail;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public interface IGenerateThumbnail {
    @Value("${thumbnail.path}")
    String THUMBNAIL_PATH = "";

    void generateThumbnail(String filePath, String outputPath, int scaleTarget) throws IOException, InterruptedException;
}
