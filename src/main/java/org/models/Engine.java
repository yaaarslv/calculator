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
    private double SFC_liquid;
    private double CF_liquid;
    private List<FuelTypeEnglish> fuelTypes;
    private double MCR;
    private double P;
    private boolean lngIsMainFuel;
    private double efficiencyOfElectricGenerator;

    public Engine(double fuelTypeCount, double MCR, double p, double efficiencyOfElectricGenerator, double CF_liquid, double SFC_liquid){
        this.fuelTypeCount = fuelTypeCount;
        this.MCR = MCR;
        this.P = p;
        this.fuelTypes = new ArrayList<>();
        this.SFC_map = new HashMap<>();
        this.lngIsMainFuel = true;
        this.efficiencyOfElectricGenerator = efficiencyOfElectricGenerator;
        this.CF_liquid = CF_liquid;
        this.SFC_liquid = SFC_liquid;
    }

    public void addFuelType(FuelTypeEnglish fuelType, double sfc_fuelType) {
        this.fuelTypes.add(fuelType);
        this.SFC_map.put(fuelType, sfc_fuelType);
    }
}
