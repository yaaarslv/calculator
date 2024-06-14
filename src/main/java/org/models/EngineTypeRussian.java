package org.models;

import java.util.Arrays;

public enum EngineTypeRussian {
    Main("Главный"),
    Additional("Вспомогательный");

    private final String title;

    EngineTypeRussian(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static EngineTypeRussian getByTitle(String title) {
        return Arrays.stream(EngineTypeRussian.values()).filter(f -> f.title.equals(title)).findFirst().orElse(null);
    }
}
