package org.coefficients;

import lombok.Getter;
import lombok.Setter;
import org.models.*;
import org.ui.Language;

import java.util.*;

@Setter
@Getter
public class EexiCoefficient {
    private ShipTypeEnglish shipTypeEnglish;
    private IceClassEnglish iceClassEnglish;
    private List<CorrectionFactorEnglish> correctionFactorsEnglish;
    private Date deliveryDate;
    private double DWT;
    private double GT;
    private double V_ref;
    private List<Engine> mainEngines;
    private List<Engine> auxiliaryEngines;
    private double efficiencyElectricGenerator1;
    private double efficiencyElectricGeneratorAE;
    private double shaftGenCount;
    private double elPropEngCount;
    private double MCR_PTO;
    private double MCR_PTI;
    private double f_w;
    private double efficiencyElectricTransmission;
    private double P_ae;
    private double f_ivse;
    private double P_eff;
    private double f_eff;
    private double P_AE_eff;
    private double f_AE_eff;
    private double L_pp;
    private double B_s;
    private double d_s;
    private double delta;
    private boolean energyConsumptionTableExistence;
    private boolean calculationOfShipPowerPlantLoads;
    private boolean propellerShaftPowerLimitation;
    private boolean dieselElectricPropulsionPowerPlant;
    private boolean steamTurbinePowerPlant;
    private double CTC_LNG;
    private double R_reliquefy;
    private double BOR;
    private double COP_cooling;
    private double R;
    private boolean cargoCranes;
    private boolean sideRamp;
    private boolean cargoRamp;
    private int cargoCrane1Count = 0;
    private int cargoCrane2Count = 0;
    private int cargoCrane3Count = 0;
    private double SWL1 = 0;
    private double SWL2 = 0;
    private double SWL3 = 0;
    private double Reach1 = 0;
    private double Reach2 = 0;
    private double Reach3 = 0;
    private double f_sideloader = 1;
    private double f_roro = 1;
    private double f_DF_gas;
    private String shipName;
    private int imoNumber;
    private int registerNumber;

    public EexiCoefficient() {
        this.correctionFactorsEnglish = new ArrayList<>();
        this.mainEngines = new ArrayList<>();
        this.auxiliaryEngines = new ArrayList<>();
        this.energyConsumptionTableExistence = true;
        this.calculationOfShipPowerPlantLoads = false;
        this.propellerShaftPowerLimitation = false;
        this.dieselElectricPropulsionPowerPlant = false;
        this.steamTurbinePowerPlant = false;
        this.cargoCranes = false;
        this.sideRamp = false;
        this.cargoRamp = false;
        this.f_eff = 0;
        this.f_w = 1;
        this.P_AE_eff = 0;
        this.P_eff = 0;
        this.f_AE_eff = 0;
        this.f_DF_gas = 1;
        this.f_ivse = 1;
    }

    public List<String> getShipTypeAvailableCorrectionFactors(Language language) {
        switch (shipTypeEnglish) {
            case BulkCarrier -> {
                return List.of(new String[]{language == Language.Russian ? CorrectionFactorRussian.MeetsToTheGeneralIACSRules.getTitle() : CorrectionFactorEnglish.MeetsToTheGeneralIACSRules.getTitle()});
            }
            case GasCarrier, GasCarrierLNG -> {
                return List.of(new String[]{language == Language.Russian ? CorrectionFactorRussian.Reliquefaction.getTitle() : CorrectionFactorEnglish.Reliquefaction.getTitle()});
            }
            case Tanker -> {
                return List.of(new String[]{language == Language.Russian ? CorrectionFactorRussian.OilTankerOrNLSTanker.getTitle() : CorrectionFactorEnglish.OilTankerOrNLSTanker.getTitle(),
                        language == Language.Russian ? CorrectionFactorRussian.ChemicalTanker.getTitle() : CorrectionFactorEnglish.ChemicalTanker.getTitle(),
                        language == Language.Russian ? CorrectionFactorRussian.ShuttleTanker.getTitle() : CorrectionFactorEnglish.ShuttleTanker.getTitle(),
                        language == Language.Russian ? CorrectionFactorRussian.MeetsToTheGeneralIACSRules.getTitle() : CorrectionFactorEnglish.MeetsToTheGeneralIACSRules.getTitle()});
            }
            case GenCargo -> {
                return List.of(new String[]{language == Language.Russian ? CorrectionFactorRussian.CargoCranes.getTitle() : CorrectionFactorEnglish.CargoCranes.getTitle(),
                        language == Language.Russian ? CorrectionFactorRussian.SideLoaders.getTitle() : CorrectionFactorEnglish.SideLoaders.getTitle(),
                        language == Language.Russian ? CorrectionFactorRussian.RoRoRamp.getTitle() : CorrectionFactorEnglish.RoRoRamp.getTitle()});
            }
            case CruisePassengerShip -> {
                return List.of(new String[]{language == Language.Russian ? CorrectionFactorRussian.NonConventionalPropulsion.getTitle() : CorrectionFactorEnglish.NonConventionalPropulsion.getTitle()});
            }
            default -> {
                return List.of(new String[]{});
            }
        }
    }

    public void addCorrectionFactor(CorrectionFactorEnglish correctionFactorEnglish) {
        this.correctionFactorsEnglish.add(correctionFactorEnglish);
    }

    public void removeCorrectionFactor(CorrectionFactorEnglish correctionFactorEnglish) {
        this.correctionFactorsEnglish.remove(correctionFactorEnglish);
    }

    public void addMainEngine(Engine engine) {
        this.mainEngines.add(engine);
    }

    public void removeMainEngine(Engine engine) {
        this.mainEngines.remove(engine);
    }

    public void addAuxiliaryEngine(Engine engine) {
        this.auxiliaryEngines.add(engine);
    }

    public void removeAuxiliaryEngine(Engine engine) {
        this.auxiliaryEngines.remove(engine);
    }

    private double calculateSumP_PTI_i() {
        double sum = 0;
        double coeff = steamTurbinePowerPlant ? 0.83 : 0.75;

        for (int i = 0; i < elPropEngCount; i++) {
            sum += MCR_PTI * coeff;
        }

        if (sum > 0) {
            return sum / auxiliaryEngines.getFirst().getEfficiencyOfElectricGenerator();
        } else {
            return 0;
        }
    }

//    private double calculateP_ME_i() {
//        double P_ME = 0;
//        if (shipTypeEnglish == ShipTypeEnglish.GasCarrierLNG && dieselElectricPropulsionPowerPlant) {
//            P_ME = 0.83 * MCR_ME1 / efficiencyElectricGenerator1;
//        } else {
//            P_ME = 0.75 * MCR_ME1;
//        }
//
//        return P_ME;
//    }

//    private double calculateP_AE() {
//        double P_AE = 0;
//        double sumMCRME = 0;
//        for (Engine engine : mainEngines) sumMCRME += engine.getMCR();
//        double propulsionPower = sumMCRME + (calculateSumP_PTI_i() / 0.75);
//        System.out.println("propulsionPower: " + propulsionPower);
//        System.out.println("calculateSumP_PTI_i: " + calculateSumP_PTI_i());
//        if (propulsionPower >= 10000) {
//            P_AE = (0.025 * (propulsionPower)) + 250;
//        } else {
//            P_AE = (0.05 * propulsionPower);
//        }
//
//
//        if (correctionFactorsEnglish.contains(CorrectionFactorEnglish.Reliquefaction)) {
//            double COP_reliquefy = (425 * 511) / (24 * 3600 * COP_cooling);
//            P_AE += CTC_LNG * BOR * R_reliquefy * COP_reliquefy;
//        }
//        if (shipTypeEnglish == ShipTypeEnglish.GasCarrierLNG && dieselElectricPropulsionPowerPlant) {
//            double sum = 0;
//            for (Engine engine : mainEngines) {
//                FuelTypeEnglish sfc_type = engine.getSFC_map().keySet().stream().filter(fuelTypeEnglish -> fuelTypeEnglish.equals(FuelTypeEnglish.LNG) || fuelTypeEnglish.equals(FuelTypeEnglish.LPG)).findFirst().get();
//                double sfc_gasmode = engine.getSFC_map().get(sfc_type);
//                sum += sfc_gasmode * engine.getP() / 1000;
//            }
//            P_AE += 0.33 * sum;
//
////            P_AE += 0.02 * (Math.pow(calculateP_ME_i(), 2) + Math.pow(calculateP_ME_i(2), 2) + Math.pow(calculateP_ME_i(3), 2));
//        }
//
//
//        return P_AE;
//    }

    private double getCByFuel(FuelTypeEnglish fuelType) {
        return switch (fuelType) {
            case diesel -> 3.206;
            case LFO -> 3.151;
            case HFO -> 3.114;
            case LPG -> 3.0;
            case LNG -> 2.750;
            case methanol -> 1.375;
            case ethanol -> 1.913;
        };
    }

    private double calculateFj() {
        double sum = 0;
        for (Engine engine : mainEngines) {
            sum += engine.getMCR();
        }
        double f_j = 0;
        double f_j_0 = 0;
        double f_j_min = 0;
        if (iceClassEnglish == IceClassEnglish.IA_Super_Arc5_PC6 || iceClassEnglish == IceClassEnglish.IA_Arc4_PC7 || iceClassEnglish == IceClassEnglish.IB_Ice3 || iceClassEnglish == IceClassEnglish.IC_Ice2) {
            if (shipTypeEnglish == ShipTypeEnglish.Tanker) {
                f_j_0 = (17.444 * Math.pow(DWT, 0.5766)) / sum;
                if (iceClassEnglish == IceClassEnglish.IA_Super_Arc5_PC6) {
                    f_j_min = 0.2488 * Math.pow(DWT, 0.0903);
                } else if (iceClassEnglish == IceClassEnglish.IA_Arc4_PC7) {
                    f_j_min = 0.4541 * Math.pow(DWT, 0.0524);
                } else if (iceClassEnglish == IceClassEnglish.IB_Ice3) {
                    f_j_min = 0.7783 * Math.pow(DWT, 0.0145);
                } else if (iceClassEnglish == IceClassEnglish.IC_Ice2) {
                    f_j_min = 0.8741 * Math.pow(DWT, 0.0079);
                }

                return Math.min((Math.max(f_j_0, f_j_min)), 1);
            } else if (shipTypeEnglish == ShipTypeEnglish.BulkCarrier) {
                f_j_0 = (17.207 * Math.pow(DWT, 0.5705)) / sum;
                if (iceClassEnglish == IceClassEnglish.IA_Super_Arc5_PC6) {
                    f_j_min = 0.2515 * Math.pow(DWT, 0.0851);
                } else if (iceClassEnglish == IceClassEnglish.IA_Arc4_PC7) {
                    f_j_min = 0.3918 * Math.pow(DWT, 0.0556);
                } else if (iceClassEnglish == IceClassEnglish.IB_Ice3) {
                    f_j_min = 0.8075 * Math.pow(DWT, 0.0071);
                } else if (iceClassEnglish == IceClassEnglish.IC_Ice2) {
                    f_j_min = 0.8573 * Math.pow(DWT, 0.0087);
                }

                return Math.min((Math.max(f_j_0, f_j_min)), 1);
            } else if (shipTypeEnglish == ShipTypeEnglish.GenCargo) {
                f_j_0 = (1.974 * Math.pow(DWT, 0.7987)) / sum;
                if (iceClassEnglish == IceClassEnglish.IA_Super_Arc5_PC6) {
                    f_j_min = 0.1381 * Math.pow(DWT, 0.1435);
                } else if (iceClassEnglish == IceClassEnglish.IA_Arc4_PC7) {
                    f_j_min = 0.1574 * Math.pow(DWT, 0.144);
                } else if (iceClassEnglish == IceClassEnglish.IB_Ice3) {
                    f_j_min = 0.3256 * Math.pow(DWT, 0.0922);
                } else if (iceClassEnglish == IceClassEnglish.IC_Ice2) {
                    f_j_min = 0.4966 * Math.pow(DWT, 0.0583);
                }

                return Math.min((Math.max(f_j_0, f_j_min)), 1);
            } else if (shipTypeEnglish == ShipTypeEnglish.Refrigerator) {
                f_j_0 = (5.598 * Math.pow(DWT, 0.696)) / sum;
                if (iceClassEnglish == IceClassEnglish.IA_Super_Arc5_PC6) {
                    f_j_min = 0.5254 * Math.pow(DWT, 0.0357);
                } else if (iceClassEnglish == IceClassEnglish.IA_Arc4_PC7) {
                    f_j_min = 0.6325 * Math.pow(DWT, 0.0278);
                } else if (iceClassEnglish == IceClassEnglish.IB_Ice3) {
                    f_j_min = 0.7670 * Math.pow(DWT, 0.0159);
                } else if (iceClassEnglish == IceClassEnglish.IC_Ice2) {
                    f_j_min = 0.8918 * Math.pow(DWT, 0.0079);
                }

                return Math.min((Math.max(f_j_0, f_j_min)), 1);
            }
        }

        if (correctionFactorsEnglish.contains(CorrectionFactorEnglish.ShuttleTanker) && DWT >= 80000 && DWT <= 160000) {
            return 0.77;
        } else if (shipTypeEnglish == ShipTypeEnglish.RoRoPassengerCarrier || shipTypeEnglish == ShipTypeEnglish.RoRoCargoCarrier) {
            double F_nl = (0.5144 * V_ref) / (Math.sqrt(L_pp * 9.81));
            var exponents = getExponents();
            double f_j_roro = 1 / (Math.pow(F_nl, exponents[0]) * Math.pow((L_pp / B_s), exponents[1]) * Math.pow((B_s / d_s), exponents[2]) * Math.pow((L_pp / Math.pow(delta, 1 / 3)), exponents[3]));
            return Math.min(f_j_roro, 1);
        } else if (shipTypeEnglish == ShipTypeEnglish.GenCargo) {
            double F_n_delta = (0.5144 * V_ref) / (Math.sqrt(9.81 * Math.pow(delta, 1 / 3)));
            F_n_delta = Math.min(F_n_delta, 0.6);
            double C_b = delta / (L_pp * B_s * d_s);
            double f_i = 0.174 / (Math.pow(F_n_delta, 2.3) * Math.pow(C_b, 0.3));
            return Math.min(f_i, 1);
        } else {
            return 1.0;
        }
    }

    private double[] getExponents() {
        if (shipTypeEnglish == ShipTypeEnglish.RoRoPassengerCarrier) {
            return new double[]{2.50, 0.75, 0.75, 1.00};
        } else if (shipTypeEnglish == ShipTypeEnglish.RoRoCargoCarrier) {
            return new double[]{2.00, 0.50, 0.75, 1.00};
        }
        return new double[0];
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

    private double calculateF_l() {
        double f_cranes = 1;
        if (cargoCranes) {
            f_cranes = 1 + ((0.0519 * SWL1 * Reach1 + 32.11) * cargoCrane1Count + (0.0519 * SWL2 * Reach2 + 32.11) *
                    cargoCrane2Count + (0.0519 * SWL3 * Reach3 + 32.11) * cargoCrane3Count) / getCapacityAC()[0];
        }

        return f_cranes * f_sideloader * f_roro;
    }

    private double calculateFc() {
        double Fc = 1.0;
        if (shipTypeEnglish == ShipTypeEnglish.Tanker && correctionFactorsEnglish.contains(CorrectionFactorEnglish.ChemicalTanker) && R > 0 && R < 0.98) {
            Fc = Math.pow(R, -0.7) - 0.014;
        } else if (shipTypeEnglish == ShipTypeEnglish.GasCarrierLNG) {
            Fc = Math.pow(R, -0.56);
        } else if (shipTypeEnglish == ShipTypeEnglish.RoRoPassengerCarrier && (DWT / GT) < 0.25) {
            Fc = Math.pow(((DWT / GT) / 0.25), -0.8);
        } else if (shipTypeEnglish == ShipTypeEnglish.BulkCarrier && R > 0 && R < 0.55) {
            Fc = Math.pow(R, -0.15);
        } else if (shipTypeEnglish == ShipTypeEnglish.RoRoCarCarrier && (DWT / GT) < 0.35) {
            Fc = Math.pow(((DWT / GT) / 0.35), -0.8);
        }

        return Fc;
    }

    private double getFm() {
        return switch (iceClassEnglish) {
            case IA_Arc4_PC7, IA_Super_Arc5_PC6 -> 1.05;
            default -> 1.0;
        };
    }

    private double calculateFiCSR() {
        if (shipTypeEnglish == ShipTypeEnglish.BulkCarrier || shipTypeEnglish == ShipTypeEnglish.Tanker) {
            return 1 + (0.08 * delta / DWT);
        }

        return 1;
    }

    private double calculateFi() {
        if (iceClassEnglish == IceClassEnglish.IC_Ice2 || iceClassEnglish == IceClassEnglish.IB_Ice3
                || iceClassEnglish == IceClassEnglish.IA_Arc4_PC7
                || iceClassEnglish == IceClassEnglish.IA_Super_Arc5_PC6) {
            return calculateFiIceClass() * calculateFiCb();
        } else {
            return 1;
        }

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
        if (delta != 0 && L_pp != 0 && B_s != 0 && d_s != 0) {
            return delta / (L_pp * B_s * d_s);
        } else {
            return 1.0;
        }

    }

    private double calculateFiIceClass() {
        return switch (iceClassEnglish) {
            case IC_Ice2 -> getFiICClass();
            case IB_Ice3 -> getFiIBClass();
            case IA_Arc4_PC7 -> getFiIAClass();
            case IA_Super_Arc5_PC6 -> getFiIASuperClass();
            default -> 1.0;
        };
    }

    private double getFiICClass() {
        return 1.0041 + 58.5 / DWT;
    }

    private double getFiIBClass() {
        return 1.0067 + 62.7 / DWT;
    }

    private double getFiIAClass() {
        return 1.0099 + 95.1 / DWT;
    }

    private double getFiIASuperClass() {
        return 1.0151 + 228.7 / DWT;
    }

    public double calculateEEXI() {
        if (mainEngines.stream().noneMatch(engine -> engine.getFuelTypeCount() == 2)) {
            f_DF_gas = 1;
        } else {
            if (f_DF_gas < 0.5) {
                mainEngines.stream().filter(engine -> engine.getFuelTypeCount() == 2).forEach(engine -> engine.setLngIsMainFuel(false));
                auxiliaryEngines.stream().filter(engine -> engine.getFuelTypeCount() == 2).forEach(engine -> engine.setLngIsMainFuel(false));
            }
        }

        double sum1 = 0;
        for (Engine engine : mainEngines) {
            double sum_CF_SFC_ME = 0;
            for (FuelTypeEnglish type : engine.getFuelTypes()) {
                double sfc_type = engine.getSFC_map().get(type);
                sum_CF_SFC_ME += getCByFuel(type) * sfc_type;
            }

            if (engine.getFuelTypeCount() == 1 || (engine.getFuelTypeCount() == 2 && engine.isLngIsMainFuel())) {
                sum1 += engine.getP() * sum_CF_SFC_ME;
            } else {
                sum1 += engine.getP() * (f_DF_gas * sum_CF_SFC_ME + (1 - f_DF_gas) * engine.getCF_liquid() * engine.getSFC_liquid());
            }
        }

        double A = calculateFj() * sum1;
        double sum_CF_SFC_AE = 0;
        for (FuelTypeEnglish type : auxiliaryEngines.getFirst().getFuelTypes()) {
            double sfc_type = auxiliaryEngines.getFirst().getSFC_map().get(type);
            sum_CF_SFC_AE += getCByFuel(type) * sfc_type;
        }

        double B = auxiliaryEngines.getFirst().getP() * (f_DF_gas * sum_CF_SFC_AE +
                (1 - f_DF_gas) * auxiliaryEngines.getFirst().getCF_liquid() * auxiliaryEngines.getFirst().getSFC_liquid());

        double sum_CF_SFC_ME = 0;
        for (Engine engine : mainEngines) {
            double sum_CF_SFC_ME_i = 0;
            for (FuelTypeEnglish type : engine.getFuelTypes()) {
                double sfc_type = engine.getSFC_map().get(type);
                sum_CF_SFC_ME_i += getCByFuel(type) * sfc_type;
            }

            sum_CF_SFC_ME += sum_CF_SFC_ME_i;
        }
        System.out.println("pti: " + calculateSumP_PTI_i());
        double C = ((calculateFj() * calculateSumP_PTI_i() - f_eff * P_AE_eff) *
                (f_DF_gas * sum_CF_SFC_AE + (1 - f_DF_gas) * auxiliaryEngines.getFirst().getCF_liquid() * auxiliaryEngines.getFirst().getSFC_liquid()));

        double D;
        if (calculateSumP_PTI_i() > 0) {
            D = f_eff * ((f_DF_gas * sum_CF_SFC_ME + (1 - f_DF_gas) * mainEngines.getFirst().getCF_liquid() * mainEngines.getFirst().getSFC_liquid()) +
                    (f_DF_gas * sum_CF_SFC_AE + (1 - f_DF_gas) * auxiliaryEngines.getFirst().getCF_liquid() * auxiliaryEngines.getFirst().getSFC_liquid())) / 2 *
                    (f_DF_gas * sum_CF_SFC_ME + (1 - f_DF_gas) * mainEngines.getFirst().getCF_liquid() * mainEngines.getFirst().getSFC_liquid());
        } else {
            D = f_eff * P_eff * (f_DF_gas * sum_CF_SFC_ME + (1 - f_DF_gas) * mainEngines.getFirst().getCF_liquid() * mainEngines.getFirst().getSFC_liquid());
        }

        double up = A + B + C - D;

        double down = calculateFi() * calculateFc() * calculateF_l() * getCapacityAC()[0] *
                f_w * V_ref * getFm() * calculateFiCSR() * f_ivse;

        return up / down;
    }
}
