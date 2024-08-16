package com.example.dam.global.service;

import com.example.dam.model.Folder;
import com.example.dam.model.Space;
import com.example.dam.model.Tenant;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {
    public static String buildRelativePath(String fileName, Space space, Folder folder) {
        return '/' +
                (CommonService.checkNull(space) ? space.getId().toString() + '/' : "") +
                (CommonService.checkNull(folder) ? folder.getName() + '/' : "") +
                fileName;
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
