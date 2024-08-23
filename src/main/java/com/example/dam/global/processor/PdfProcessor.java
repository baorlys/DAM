package com.example.dam.global.processor;

public class PdfProcessor implements DocumentProcessor {
    @Override
    public void processDocument(String filePath, String outputPath)  {
        // No conversion needed for PDFs
    }
}