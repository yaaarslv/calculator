package org.models;

import java.util.Arrays;

public enum EngineTypeEnglish {
    Main("Main"),
    Auxiliary("Auxiliary");

    private final String title;

    EngineTypeEnglish(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static EngineTypeEnglish getByTitle(String title) {
        return Arrays.stream(EngineTypeEnglish.values()).filter(f -> f.title.equals(title)).findFirst().orElse(null);
    }
}
