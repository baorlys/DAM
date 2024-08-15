package com.example.dam.global.random;


import java.util.UUID;

public class RandomId {
    private RandomId() {
        // Empty constructor
    }
    static final int ID_LENGTH = 6;

    public static String generateRandomId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-","").substring(0, ID_LENGTH);
    }
}
