package com.example.dam.service;

import com.example.dam.enums.Format;
import com.example.dam.model.Folder;
import com.example.dam.model.Space;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class CommonService {
    private CommonService() {
        // Empty constructor
    }

    public static void throwIsNull(Object obj, String msg) throws NullPointerException {
        if (obj == null) {
            throw new NullPointerException(msg);
        }
    }

    public static void throwIsNotExists(boolean isNotExists, String msg) throws CredentialException {
        if (isNotExists) {
            throw new CredentialException(msg);
        }
    }

    public static Format getFormat(String val) {
        return Arrays.stream(Format.values())
                .filter(f -> f.name().equalsIgnoreCase(val))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid format: " + val));
    }

    public static void checkNonNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                throw new NotFoundException("Can not find object");
            }
        }
    }

    public static void checkNull(Object... objects) {
        for (Object o : objects) {
            if (o != null) {
                throw new RuntimeException("Object already exist");
            }
        }
    }


    public static String filePathHandler(Space space, Folder folder, String fileName) {
        String filePath = folder != null ? space.getName() + "/" + folder.getName() : space.getName();
        return filePath + "/" + fileName;
    }

}
