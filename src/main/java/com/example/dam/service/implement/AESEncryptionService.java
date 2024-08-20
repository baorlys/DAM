package com.example.dam.service.implement;

import com.example.dam.config.PrivateConfig;
import com.example.dam.service.ApiEncryptionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AESEncryptionService implements ApiEncryptionService {
    static final String AES = "AES";
    static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
    static final int GCM_TAG_LENGTH = 16;
    static final int GCM_IV_LENGTH = 12;

    static PrivateConfig privateConfig;
    static final String ENCRYPTION_KEY = privateConfig.get("ENCRYPTION_KEY");



    @Override
    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, iv);

        SecretKeySpec secretKeySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, parameterSpec);

        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        byte[] cipherTextWithIv = new byte[GCM_IV_LENGTH + cipherText.length];
        System.arraycopy(iv, 0, cipherTextWithIv, 0, GCM_IV_LENGTH);
        System.arraycopy(cipherText, 0, cipherTextWithIv, GCM_IV_LENGTH, cipherText.length);

        return Base64.getEncoder().encodeToString(cipherTextWithIv);
    }

    @Override
    public String decrypt(String cipherText) throws Exception {
        byte[] cipherTextWithIv = Base64.getDecoder().decode(cipherText);

        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, cipherTextWithIv, 0, GCM_IV_LENGTH);

        SecretKeySpec secretKeySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, parameterSpec);

        byte[] plainText = cipher.doFinal(cipherTextWithIv, GCM_IV_LENGTH, cipherTextWithIv.length - GCM_IV_LENGTH);
        return new String(plainText);
    }
}