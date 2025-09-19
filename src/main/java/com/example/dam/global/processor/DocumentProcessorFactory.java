package com.example.dam.global.processor;

import java.util.HashMap;
import java.util.Map;

public class DocumentProcessorFactory {
    private DocumentProcessorFactory() {
        // Hide the constructor
    }
    private static final Map<String, DocumentProcessor> processors = new HashMap<>();

    static {
        processors.put(".docx", new DocxProcessor());
        processors.put(".pdf", new PdfProcessor());
    }

    public static DocumentProcessor getProcessor(String filePath) {
        return processors.entrySet().stream()
                .filter(entry -> filePath.endsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported file type: " + filePath));
    }
}
