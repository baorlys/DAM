package com.example.dam.service;

import com.example.dam.enums.ResourceType;
import com.example.dam.enums.TransformVariable;

import java.io.IOException;
import java.util.Map;

public interface HandleAssetService {
    void transform(ResourceType type, String inputPath, String outputPath, Map<TransformVariable, String> options)
            throws IOException, InterruptedException;

    void generateThumbnail(ResourceType type, String filePath, String outputPath, int scaleTarget)
            throws IOException, InterruptedException;
}
