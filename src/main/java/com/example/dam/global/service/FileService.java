package com.example.dam.global.service;

import java.io.File;

public class FileService {
    private FileService() {
        // Empty constructor
    }
    public static File getFile(String filePath) {
        return new File(filePath);
    }
}
