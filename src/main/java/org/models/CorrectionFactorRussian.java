package org.models;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CorrectionFactorRussian {
    MeetsToTheGeneralIACSRules("Соответствует общим Правилам МАКО"),
    Reliquefaction("Повторное сжижение"),
    OilTankerOrNLSTanker("Нефтеналивное судно или танкер ВЖВ"),
    ChemicalTanker("Химовоз"),
    ShuttleTanker("Шатл-танкер"),
    CargoCranes("Грузовые краны"),
    SideLoaders("Боковая аппарель (лацпорт)"),
    RoRoRamp("Грузовая аппарель"),
    NonConventionalPropulsion("Не конвенционная пропульсивная установка");

    private final String title;

    CorrectionFactorRussian(String title) {
        this.title = title;
    }

    public static CorrectionFactorRussian getByTitle(String title) {
        return Arrays.stream(CorrectionFactorRussian.values()).filter(f -> f.title.equals(title)).findFirst().orElse(null);
    }
}
