package com.example.dam.service;

import com.example.dam.model.Folder;
import com.example.dam.model.Space;
import org.webjars.NotFoundException;

import javax.security.auth.login.CredentialException;

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

    public static void checkNonNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                throw new NotFoundException("Object not exist");
            }
        }
    }

    public static String filePathHandler(Space space, Folder folder, String fileName) {
        String filePath = folder != null ? space.getName() + "/" + folder.getName() : space.getName();
        return filePath + "/" + fileName;
    }
}
