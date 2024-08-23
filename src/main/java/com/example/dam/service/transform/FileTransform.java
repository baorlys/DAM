package com.example.dam.service.transform;

import com.example.dam.enums.TransformVariable;

import java.io.IOException;
import java.util.Map;

public class FileTransform implements ITransformable {
    @Override
    public void transform(String filePath, String outputPath, Map<TransformVariable, String> options) throws IOException, InterruptedException {
        throw new UnsupportedOperationException("File transformation is not supported");
    }
}
