package com.example.dam.global.service;

import com.example.dam.model.Folder;
import com.example.dam.model.Space;
import com.example.dam.model.Tenant;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileService {
    private FileService() {
        // Empty constructor
    }

    public static String extractExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return fileName.substring(dotIndex + 1);
    }

    public static String buildRelativePath(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        String name = fileName.substring(0, dotIndex);
        String extension = fileName.substring(dotIndex);
        String randomString = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        return name + randomString + extension;

    }

    public static String buildAbsolutePath(String fileName, Tenant tenant, Space space, Folder folder) {
        return tenant.getId().toString() + '/' +
                (CommonService.checkNull(space) ? space.getId().toString() + '/' : "") +
                (CommonService.checkNull(folder) ? folder.getName() + '/' : "") +
                fileName;
    }

    public static void saveFile(MultipartFile file, String fileName, String storageDirectory) throws IOException {
        Path filePath = Paths.get(storageDirectory, fileName);
        Path parentDir = filePath.getParent();
        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
        Files.copy(file.getInputStream(), filePath);
    }
}
