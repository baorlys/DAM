package com.example.dam.enums;

public enum AssetType {
    THUMBNAIL("thumbnail"),
    IMAGE("image"),
    VIDEO("video");

    private final String value;

    public String getValue() {
        return value;
    }

    AssetType(String value) {
        this.value = value;
    }
}
