package com.example.dam.global.service;

import com.example.dam.exception.ExistsRecord;
import com.example.dam.global.random.RandomId;
import com.example.dam.model.Folder;
import com.example.dam.model.Space;
import org.webjars.NotFoundException;

import javax.security.auth.login.CredentialException;

public class CommonService {
    private CommonService() {
        // Empty constructor
    }

    public static void throwNotFound(Object obj, String msg) throws NotFoundException {
        if (obj == null) {
            throw new NotFoundException(msg);
        }
    }

    public static void throwIsNotExists(boolean isNotExists, String msg) throws CredentialException {
        if (isNotExists) {
            throw new CredentialException(msg);
        }
    }

    public static void throwIsExist(boolean isNotExists, String msg) throws ExistsRecord {
        if (isNotExists) {
            throw new ExistsRecord(msg);
        }
    }

    public static void checkNonNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                throw new NotFoundException("Object not exist");
            }
        }
    }

    public static String filePathHandler(Space space, Folder folder, String fileName) {
        String basePath = space.getTenant().getId() + "/" + space.getName();
        String folderPath = (folder != null) ? "/" + folder.getName() : "";
        return basePath + folderPath + "/" + fileName;
    }


    public String addRandomId(String path) {
        String extension = path.substring(path.lastIndexOf("."));
        return path.substring(0, path.lastIndexOf(".")) + RandomId.generateRandomId() + extension;
    }
}
