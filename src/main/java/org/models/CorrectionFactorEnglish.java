package org.models;

import java.util.Arrays;

public enum CorrectionFactorEnglish {
    MeetsToTheGeneralIACSRules("Meets to the general IACS rules"),
    Reliquefaction("Reliquefaction"),
    OilTankerOrNLSTanker("Oil tanker or NLS tanker"),
    ChemicalTanker("Chemical tanker"),
    ShuttleTanker("Shuttle tanker"),
    CargoCranes("Cargo cranes"),
    SideLoaders("Side loaders"),
    RoRoRamp("Ro-Ro ramp"),
    NonConventionalPropulsion("Non-conventional propulsion");

    private final String title;

    CorrectionFactorEnglish(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static CorrectionFactorEnglish getByTitle(String title) {
        return Arrays.stream(CorrectionFactorEnglish.values()).filter(f -> f.title.equals(title)).findFirst().orElse(null);
    }
}
