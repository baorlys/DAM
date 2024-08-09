package com.example.dam.enums;

import java.util.EnumSet;
import java.util.Set;

public enum ResourceType {
    IMAGE(EnumSet.of(Format.PNG, Format.JPG, Format.GIF)),
    VIDEO(EnumSet.of(Format.MP4, Format.MOV)),
    FILE(EnumSet.of(Format.DOCS, Format.PDF, Format.PPT, Format.TXT));

    private final Set<Format> validFormats;

    ResourceType(Set<Format> validFormats) {
        this.validFormats = validFormats;
    }

    public Set<Format> getValidFormats() {
        return validFormats;
    }

    public boolean isValidFormat(Format format) {
        return validFormats.contains(format);
    }
}
