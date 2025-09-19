package com.example.dam.enums;


public enum TransformVariable {
    WIDTH("w"),
    HEIGHT("h"),
    QUALITY("q"),
    RESOLUTION("r");

    private final String shortCut;

    public String getShortCut() {
        return shortCut;
    }
    TransformVariable(String shortCut) {
        this.shortCut = shortCut;
    }


    public static TransformVariable fromShortCut(String text) {
        for (TransformVariable b : TransformVariable.values()) {
            if (b.shortCut.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }


}
