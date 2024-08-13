package com.example.dam.service.transform;

import com.example.dam.enums.TransformVariable;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Map;

public interface ITransformable {
    @Value("${transformed.path}")
    String TRANSFORMED_PATH = "";
    void transform(String filePath, String outputPath, Map<TransformVariable, String> options) throws IOException, InterruptedException;
}
