package org.coefficients;

import org.models.CiiFuelTable;
import org.models.IceClassEnglish;
import org.models.ShipTypeEnglish;

import java.util.ArrayList;
import java.util.List;

public class CiiCoefficient implements Coefficient {
    private int year;
    private ShipTypeEnglish shipTypeEnglish;
    private IceClassEnglish iceClassEnglish;
    private List<String> correctionFactors;
    private Double DWT;
    private Double DWT_old;
    private Double GT;
    private Double Dt;
    private Double Dx;
    private Double Ws;
    private Double Dj;
    private Double EEPI;
    private Double CapacityCiiRef;
    private Double CiiRef;
    private Double decreasingCoefficient;
    private CiiFuelTable ciiFuelTable;
    private double FiIceClass;
    private double L;
    private double B;
    private double D;
    private double delta;
    private double Vtank;
    private double Vhold;


    public CiiCoefficient() {
        this.correctionFactors = new ArrayList<>();
        this.DWT = 100000.0;
        this.Dt = 100000.0;
        this.Dj = 50000.0;
        this.Dx = 0.0;
        this.ciiFuelTable = new CiiFuelTable();
        this.FiIceClass = 0.0;
    }

    public CiiFuelTable getCiiFuelTable() {
        return ciiFuelTable;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDecreasingCoefficient(Double decreasingCoefficient) {
        this.decreasingCoefficient = decreasingCoefficient;
    }

    public void setCiiRef(Double ciiRef) {
        this.CiiRef = ciiRef;
    }

    public void setCapacityCiiRef(Double capacityCiiRef) {
        this.CapacityCiiRef = capacityCiiRef;
    }

    public void setDt(Double dt) {
        this.Dt = dt;
    }

    public void setDWT(Double DWT) {
        this.DWT = DWT;
    }

    public void setCorrectionFactors(List<String> correctionFactors) {
        this.correctionFactors = correctionFactors;
    }

    public void setIceClass(IceClassEnglish iceClassEnglish) {
        this.iceClassEnglish = iceClassEnglish;
    }

    public void setShipType(ShipTypeEnglish shipTypeEnglish) {
        this.shipTypeEnglish = shipTypeEnglish;
    }

    public void setEEPI(Double EEPI) {
        this.EEPI = EEPI;
    }

    public void setDj(Double dj) {
        this.Dj = dj;
    }

    public void setWs(Double ws) {
        this.Ws = ws;
    }

    public double calculateCoefficient() {
        double up = 0.0;

        for (var fuel : ciiFuelTable.getFuelFC().keySet()) {
//            System.out.println("Коэф преобразования: " + ciiFuelTable.getFuelCF().get(fuel));
//            System.out.println("Fc: " + ciiFuelTable.getFuelFC().getOrDefault(fuel, 0.0));
//            System.out.println("Fcvoyage: " + ciiFuelTable.getFuelFCVoyage().getOrDefault(fuel, 0.0));
//            System.out.println("TF: " + calculateTF(fuel));
//            System.out.println("year: " + (0.75 - 0.03 * (year - 2023)));
//            System.out.println("FcElectrical: " + ciiFuelTable.getFuelFCElectrical().getOrDefault(fuel, 0.0));
//            System.out.println("FcBoiler: " + ciiFuelTable.getFuelFCBoiler().getOrDefault(fuel, 0.0));
//            System.out.println("FcOther: " + ciiFuelTable.getFuelFCOther().getOrDefault(fuel, 0.0));
            up += (ciiFuelTable.getFuelCF().get(fuel) * (ciiFuelTable.getFuelFC().getOrDefault(fuel, 0.0) - (ciiFuelTable.getFuelFCVoyage().getOrDefault(fuel, 0.0) + calculateTF(fuel) + (0.75 - 0.03 * (year - 2023)) * (ciiFuelTable.getFuelFCElectrical().getOrDefault(fuel, 0.0) + ciiFuelTable.getFuelFCBoiler().getOrDefault(fuel, 0.0) + ciiFuelTable.getFuelFCOther().getOrDefault(fuel, 0.0)))));
        }
        double down = calculateFi() * getFm() * calculateFc() * calculateFiVSE() * getCapacityAC()[0] * (Dt - Dx);


        // разница в подсчете для разных типов судов заключается в разных electrical, boiler и other.
        return up / down;
    }

    private double[] getCapacityAC() {
        switch (shipTypeEnglish) {
            case BulkCarrier -> {
                if (DWT >= 279000) {
                    return new double[]{279000, 4745, 0.622};
                } else {
                    return new double[]{DWT, 4745, 0.622};
                }
            }
            case GasCarrier -> {
                if (DWT >= 65000) {
                    return new double[]{DWT, 14405E7, 2.071};
                } else {
                    return new double[]{DWT, 8104, 0.639};
                }
            }
            case Tanker -> {
                return new double[]{DWT, 5247, 0.610};
            }
            case ContainerCarrier -> {
                return new double[]{DWT, 1984, 0.489};
            }
            case GenCargo -> {
                if (DWT >= 20000) {
                    return new double[]{DWT, 31948, 0.792};
                } else {
                    return new double[]{DWT, 588, 0.3885};
                }
            }
            case Refrigerator -> {
                return new double[]{DWT, 4600, 0.557};
            }
            case CombiShip -> {
                return new double[]{DWT, 5119, 0.622};
            }
            case GasCarrierLNG -> {
                if (DWT >= 100000) {
                    return new double[]{DWT, 9.827, 0.0};
                } else if (DWT < 100000 && DWT >= 65000) {
                    return new double[]{DWT, 14479E10, 2.673};
                } else if (DWT < 65000) {
                    return new double[]{65000, 14779E10, 2.673};
                }
            }
            case RoRoCarCarrier -> {
                if (GT >= 57700) {
                    return new double[]{57000, 3627, 0.590};
                } else if (GT < 57700 && GT >= 30000) {
                    return new double[]{GT, 3627, 0.590};
                } else if (GT < 30000) {
                    return new double[]{GT, 330, 0.329};
                }
            }
            case RoRoCargoCarrier -> {
                return new double[]{GT, 1967, 0.485};
            }
            case RoRoPassengerCarrier -> {
                return new double[]{GT, 2023, 0.460};
            }
            case VSSRoRoPassengerCarrier -> {
                return new double[]{GT, 4196, 0.460};
            }
            case CruisePassengerShip -> {
                return new double[]{GT, 930, 0.383};
            }
        }

        return new double[0];
    }

    private double calculateFiVSE() {
        if (DWT_old != null) {
            return DWT_old / DWT;
        } else {
            return 1.0;
        }
    }

    private double calculateFc() {
        double Fc = 1.0;
        if (shipTypeEnglish == ShipTypeEnglish.Tanker && correctionFactors.contains("Химовоз")) {
            double R = DWT / Vtank;
            if (R < 0.98) {
                Fc = Math.pow(R, -0.7) - 0.014;
            } else if (R >= 0.98) {
                Fc = 1.0;
            }
        } else if (shipTypeEnglish == ShipTypeEnglish.GasCarrierLNG) {
            double R = DWT / Vtank;
            Fc = Math.pow(R, -0.56);
        } else if (shipTypeEnglish == ShipTypeEnglish.RoRoPassengerCarrier) {
            double div = DWT / GT;
            if (div < 0.25) {
                Fc = Math.pow((div / 0.25), -0.8);
            }
        } else if (shipTypeEnglish == ShipTypeEnglish.BulkCarrier) {
            double R = DWT / Vhold;
            if (R < 0.55) {
                Fc = Math.pow(R, -0.15);
            }
        }

        return Fc;
    }

    private double getFm() {
        return switch (iceClassEnglish) {
            case IA_Arc4_PC7, IA_Super_Arc5_PC6 -> 1.05;
            default -> 1.0;
        };
    }

    private double calculateTF(String fuelType) {
        double AfTanker = 0;
        if (shipTypeEnglish == ShipTypeEnglish.Tanker) {
            if (correctionFactors.contains("Шатл-танкер ST с динамическим позиционированием")) {
                AfTanker = calculateAfTankerShuttle();
                ciiFuelTable.getFuelFCElectrical().put(fuelType, 0.0);
                ciiFuelTable.getFuelFCBoiler().put(fuelType, 0.0);
                ciiFuelTable.getFuelFCOther().put(fuelType, 0.0);
            } else if (correctionFactors.contains("Танкер STS для перевалки на рейде")) {
                AfTanker = calculateAfTankerSts();
                ciiFuelTable.getFuelFCElectrical().put(fuelType, 0.0);
                ciiFuelTable.getFuelFCBoiler().put(fuelType, 0.0);
                ciiFuelTable.getFuelFCOther().put(fuelType, 0.0);
            }

            double TF = (1 - AfTanker) * ciiFuelTable.getFuelFC().get(fuelType);
            if (TF > 0) {
                ciiFuelTable.getFuelFCElectrical().put(fuelType, 0.0);
                ciiFuelTable.getFuelFCBoiler().put(fuelType, 0.0);
                ciiFuelTable.getFuelFCOther().put(fuelType, 0.0);
            }
            return TF;
        }
        return 0;
    }

    private double calculateAfTankerSts() {
        return 6.1742 * Math.pow(DWT, -0.246);
    }

    private double calculateAfTankerShuttle() {
        return 5.6805 * Math.pow(DWT, -0.208);
    }

    private double calculateWs() {
        return getCapacityAC()[0] * (Dt - Dx);
    }

    private double calculateFi() {
        return calculateFiIceClass() * calculateFiCb();
    }

    private double calculateFiCb() {
        return switch (shipTypeEnglish) {
            case BulkCarrier, Tanker, GenCargo -> calculateCbReferenceDesign() / calculateCb();
            default -> 1.0;
        };
    }

    private double calculateCbReferenceDesign() {
        if (shipTypeEnglish == ShipTypeEnglish.GenCargo) {
            return 0.80;
        } else if (shipTypeEnglish == ShipTypeEnglish.Tanker) {
            if (DWT < 25000) {
                return 0.78;
            } else if (DWT >= 25000 && DWT < 55000) {
                return 0.80;
            } else if (DWT >= 55000) {
                return 0.83;
            }
        } else if (shipTypeEnglish == ShipTypeEnglish.BulkCarrier) {
            if (DWT < 10000) {
                return 0.78;
            } else if (DWT >= 10000 && DWT < 25000) {
                return 0.80;
            } else if (DWT >= 25000 && DWT < 55000) {
                return 0.82;
            } else if (DWT >= 55000) {
                return 0.86;
            }
        }
        return 0;
    }

    private double calculateCb() {
        if (delta != 0 && L != 0 && B != 0 && D != 0) {
            return delta / (L * B * D);
        } else {
            return 1.0;
        }

    }

    private double calculateFiIceClass() {
        return switch (iceClassEnglish.getTitle()) {
            case "IC" -> getFiICClass(DWT);
            case "IB" -> getFiIBClass(DWT);
            case "IA" -> getFiIAClass(DWT);
            case "IA_Super" -> getFiIASuperClass(DWT);
            default -> 1.0;
        };
    }

    public double calculateReachedCii() {
        return ciiFuelTable.getEjectedMassPerYear() / calculateWs();
    }

    public double calculateEEPI() {
        return ciiFuelTable.getEjectedMassPerYear() / (getCapacityAC()[0] * Dj);
    }

    public double calculateCIIRef() {
        var cac = getCapacityAC();
        return cac[1] * Math.pow(cac[0], -cac[2]);
    }

    private double getFiICClass(double deadWeight) {
        return 1.0041 + 58.5 / deadWeight;
    }

    private double getFiIBClass(double deadWeight) {
        return 1.0067 + 62.7 / deadWeight;
    }

    private double getFiIAClass(double deadWeight) {
        return 1.0099 + 95.1 / deadWeight;
    }

    private double getFiIASuperClass(double deadWeight) {
        return 1.0151 + 228.7 / deadWeight;
    }
}
