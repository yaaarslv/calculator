package org.models;

import java.util.Arrays;

public enum FuelTypeRussian {
    diesel("Diesel/Gas oil"),
    LFO("Light fuel oil"),
    HFO("Heavy fuel oil"),
    LPG("Liquefied petroleum gas"),
    LNG("Liquefied natural gas"),
    methanol("Methanol"),
    ethanol("Ethanol");

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
