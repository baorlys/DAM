//package com.example.dam.service.implement;
//
//import com.example.dam.config.StorageProperties;
//import com.example.dam.service.FileService;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//
//@Service
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class FileServiceImpl implements FileService {
//
//    StorageProperties storageProperties;
//
//    @Override
//    public File getFile(String path) {
//        return new File(storageProperties.getPath() + path);
//    }
//}
