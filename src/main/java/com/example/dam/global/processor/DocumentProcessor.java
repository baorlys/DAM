package com.example.dam.global.processor;

import java.io.IOException;

public interface DocumentProcessor {
    void processDocument(String filePath, String outputPath) throws IOException;
}
