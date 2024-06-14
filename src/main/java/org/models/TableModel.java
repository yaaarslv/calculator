package org.models;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class TableModel {
    private EngineTypeEnglish engineTypeEnglish;
    private int engineCount;
    private double mcr;
    private int fuelTypesCount;
    private double p;
    private FuelTypeEnglish fuelTypeMainEngine;
    private FuelTypeEnglish fuelTypeAdditionalEngine;
    private double sfc;

    public TableModel(EngineTypeEnglish engineTypeEnglish, int engineCount, double mcr, int fuelTypesCount, double p, FuelTypeEnglish fuelTypeMainEngine, FuelTypeEnglish fuelTypeAdditionalEngine, double sfc) {
        this.engineTypeEnglish = engineTypeEnglish;
        this.engineCount = engineCount;
        this.mcr = mcr;
        this.fuelTypesCount = fuelTypesCount;
        this.p = p;
        this.fuelTypeMainEngine = fuelTypeMainEngine;
        this.fuelTypeAdditionalEngine = fuelTypeAdditionalEngine;
        this.sfc = sfc;
    }
}
