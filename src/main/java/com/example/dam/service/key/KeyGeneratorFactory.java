package com.example.dam.service.key;


import java.util.EnumMap;
import java.util.Map;

public class KeyGeneratorFactory {
    private KeyGeneratorFactory() {
        // Hide the constructor
    }
    static final Map<KeyType, IKeyGenerator> keyMap = new EnumMap<>(KeyType.class);

    static {
        keyMap.put(KeyType.API_KEY, new ApiKeyGenerator());
        keyMap.put(KeyType.SECRET_KEY, new SecretKeyGenerator());
    }

    public enum KeyType {
        API_KEY,
        SECRET_KEY
    }
}
