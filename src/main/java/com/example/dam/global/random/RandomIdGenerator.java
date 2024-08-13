package com.example.dam.global.random;


import java.util.UUID;

public class RandomIdGenerator {
    static final int ID_LENGTH = 8;

    public static String generateRandomId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, ID_LENGTH);
    }
}
