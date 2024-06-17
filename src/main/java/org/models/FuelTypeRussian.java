package org.models;

import java.util.Arrays;

public enum FuelTypeRussian {
    diesel("Дизельное / Газойль"),
    LFO("Лёгкое (LFO)"),
    HFO("Тяжёлое (HFO)"),
    LPG("Нефтяной газ (LPG)"),
    LNG("Природный газ (LNG)"),
    methanol("Метанол"),
    ethanol("Этанол");

    private final String title;

    FuelTypeRussian(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static FuelTypeRussian getByTitle(String title) {
        return Arrays.stream(FuelTypeRussian.values()).filter(f -> f.title.equals(title)).findFirst().orElse(null);
    }
}
