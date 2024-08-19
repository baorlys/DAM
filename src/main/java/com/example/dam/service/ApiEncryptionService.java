package com.example.dam.service;

public interface ApiEncryptionService {
    String encrypt(String plainText) throws Exception;
    String decrypt(String cipherText) throws Exception;
}
