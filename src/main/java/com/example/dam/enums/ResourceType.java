package com.example.dam.enums;

import java.util.EnumSet;
import java.util.Set;

public enum ResourceType {
    IMAGE(EnumSet.of(ExtensionFile.PNG, ExtensionFile.JPG, ExtensionFile.GIF)),
    VIDEO(EnumSet.of(ExtensionFile.MP4, ExtensionFile.MOV)),
    FILE(EnumSet.of(ExtensionFile.DOCS, ExtensionFile.PDF, ExtensionFile.PPT, ExtensionFile.TXT));

    private final Set<ExtensionFile> validExtensionFiles;

    ResourceType(Set<ExtensionFile> validExtensionFiles) {
        this.validExtensionFiles = validExtensionFiles;
    }

    public Set<ExtensionFile> getValidFormats() {
        return validExtensionFiles;
    }

    public boolean isValidFormat(ExtensionFile extensionFile) {
        return validExtensionFiles.contains(extensionFile);
    }
}
