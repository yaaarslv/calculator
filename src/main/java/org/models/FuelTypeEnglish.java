package org.models;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FuelTypeEnglish {
    diesel("Diesel/Gas oil"),
    LFO("Light fuel oil"),
    HFO("Heavy fuel oil"),
    LPG("Liquefied petroleum gas"),
    LNG("Liquefied natural gas"),
    methanol("Methanol"),
    ethanol("Ethanol");

    private final String title;

    FuelTypeEnglish(String title) {
        this.title = title;
    }

    public static FuelTypeEnglish getByTitle(String title) {
        return Arrays.stream(FuelTypeEnglish.values()).filter(f -> f.title.equals(title)).findFirst().orElse(null);
    }
}
