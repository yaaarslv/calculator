package org.coefficients;

import org.models.EexiDecreasingIndex;
import org.models.ShipTypeEnglish;

import java.util.ArrayList;
import java.util.List;

public class EexiRequiredCoefficient {
    private final List<EexiDecreasingIndex> indexList;
    private final EexiCoefficient coefficient;

    public EexiRequiredCoefficient(EexiCoefficient coefficient) {
        this.coefficient = coefficient;
        this.indexList = new ArrayList<>();
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.BulkCarrier, 200000, Double.MAX_VALUE, 15, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.BulkCarrier, 20000, 200000, 20, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.BulkCarrier, 10000, 20000, 20, true));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.GasCarrier, 15000, Double.MAX_VALUE, 30, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.GasCarrier, 10000, 15000, 30, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.GasCarrier, 2000, 10000, 20, true));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.Tanker, 200000, Double.MAX_VALUE, 15, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.Tanker, 20000, 200000, 20, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.Tanker, 4000, 20000, 20, true));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.ContainerCarrier, 200000, Double.MAX_VALUE, 50, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.ContainerCarrier, 120000, 200000, 45, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.ContainerCarrier, 80000, 120000, 35, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.ContainerCarrier, 40000, 80000, 30, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.ContainerCarrier, 15000, 40000, 20, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.ContainerCarrier, 10000, 15000, 20, true));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.GenCargo, 15000, Double.MAX_VALUE, 30, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.GenCargo, 3000, 15000, 30, true));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.Refrigerator, 5000, Double.MAX_VALUE, 15, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.Refrigerator, 3000, 5000, 15, true));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.CombiShip, 20000, Double.MAX_VALUE, 20, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.CombiShip, 4000, 20000, 20, true));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.GasCarrierLNG, 10000, Double.MAX_VALUE, 30, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.RoRoCarCarrier, 1000, Double.MAX_VALUE, 15, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.RoRoCargoCarrier, 2000, Double.MAX_VALUE, 5, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.RoRoCargoCarrier, 1000, 2000, 5, true));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.RoRoPassengerCarrier, 1000, Double.MAX_VALUE, 5, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.RoRoPassengerCarrier, 250, 1000, 5, true));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.CruisePassengerShip, 85000, Double.MAX_VALUE, 30, false));
        indexList.add(new EexiDecreasingIndex(ShipTypeEnglish.CruisePassengerShip, 25000, 85000, 30, true));
    }


    private double calculateIndexByMinDWTAndMaxDWT(double maxDWT, double DWT, int maxIndex) {
        return DWT * maxIndex / maxDWT;
    }

    private double getYByShipTypeAndDWT() {
        for (EexiDecreasingIndex index : indexList) {
            if (coefficient.getShipTypeEnglish() == index.getShipTypeEnglish()) {
                if (coefficient.getShipTypeEnglish() == ShipTypeEnglish.CruisePassengerShip) {
                    if (index.isWithinRange(coefficient.getGT())) {
                        if (index.isRange()) {
                            return calculateIndexByMinDWTAndMaxDWT(index.getMaxDWT(), coefficient.getGT(), index.getPercentage());
                        } else {
                            return index.getPercentage();
                        }
                    }
                } else {
                    if (index.isWithinRange(coefficient.getDWT())) {
                        if (index.isRange()) {
                            return calculateIndexByMinDWTAndMaxDWT(index.getMaxDWT(), coefficient.getDWT(), index.getPercentage());
                        } else {
                            return index.getPercentage();
                        }
                    }
                }
            }
        }
        return 25;
    }

    private double getIndexesABC() {
        double dwt = coefficient.getDWT();
        double gt = coefficient.getGT();
        switch (coefficient.getShipTypeEnglish()) {
            case BulkCarrier -> {
                return 961.79 * Math.pow(Math.min(dwt, 279000), -0.477);
            }
            case CombiShip -> {
                return 1219.0 * Math.pow(dwt, -0.488);
            }
            case ContainerCarrier -> {
                return 174.22 * Math.pow(dwt, -0.201);
            }
            case CruisePassengerShip -> {
                return 170.84 * Math.pow(gt, -0.214);
            }
            case GasCarrier -> {
                return 1120.0 * Math.pow(dwt, -0.456);
            }
            case GenCargo -> {
                return 107.48 * Math.pow(dwt, -0.216);
            }
            case GasCarrierLNG -> {
                return 2253.7 * Math.pow(dwt, -0.474);
            }
            case Refrigerator -> {
                return 227.01 * Math.pow(dwt, -0.244);
            }
            case RoRoCargoCarrier -> {
                return 1686.17 * Math.pow(Math.min(dwt, 17000), -0.498);
            }
            case RoRoCarCarrier -> {
                double a = Math.min(Math.pow(dwt / gt, -0.7) * 780.36, 1812.63);
                return a * Math.pow(dwt, -0.471);
            }
            case RoRoPassengerCarrier -> {
                return 902.59 * Math.pow(Math.min(dwt, 10000), -0.381);
            }
            case Tanker -> {
                return 1218.80 * Math.pow(dwt, -0.488);
            }
        }
        return 0;
    }

    public Double getRequiredEEXI() {
        double part1 = 1 - (getYByShipTypeAndDWT() / 100);
        double part2 = getIndexesABC();
        return part1 * part2;
    }
}
