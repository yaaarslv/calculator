package org.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Engine {
    private double fuelTypeCount;
    private Map<FuelTypeEnglish, Double> SFC_map;
    private List<FuelTypeEnglish> fuelTypes;
    private double MCR;
    private double P;
    private boolean lngIsMainFuel;

    public Engine(double fuelTypeCount, double MCR, double p){
        this.fuelTypeCount = fuelTypeCount;
        this.MCR = MCR;
        this.P = p;
        this.fuelTypes = new ArrayList<>();
        this.SFC_map = new HashMap<>();
        this.lngIsMainFuel = true;
    }

    public void addFuelType(FuelTypeEnglish fuelType, double sfc_fuelType) {
        this.fuelTypes.add(fuelType);
        this.SFC_map.put(fuelType, sfc_fuelType);
    }
}
