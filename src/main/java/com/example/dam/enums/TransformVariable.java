package com.example.dam.enums;


public enum TransformVariable {
    WIDTH("w"),
    HEIGHT("h"),
    QUALITY("q"),
    RESOLUTION("r");

    private final String shortName;
    TransformVariable(String value) {
        this.shortName = value;
    }

    public String getShortName() {
        return shortName;
    }

}
