package com.example.dam.service.transform;

import com.example.dam.enums.TransformVariable;

import java.io.IOException;
import java.util.Map;

public interface ITransformable {
    String transform(String filePath, Map<TransformVariable, String> options) throws IOException, InterruptedException;
}
