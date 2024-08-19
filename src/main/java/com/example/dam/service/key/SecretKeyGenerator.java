package com.example.dam.service.key;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator implements IKeyGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();
    private static final int SECRET_KEY_BYTE_LENGTH = 36;

    @Override
    public String generate() {
        byte[] randomBytes = new byte[SECRET_KEY_BYTE_LENGTH];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
