package org.application;

import org.coefficients.EexiCoefficient;
import org.coefficients.EexiRequiredCoefficient;
import org.models.Engine;
import org.models.FuelTypeEnglish;
import org.models.IceClassEnglish;
import org.models.ShipTypeEnglish;
import org.ui.EnergyEfficiencyProgram;

import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {
        EnergyEfficiencyProgram.main(new String[]{});

//        EexiCoefficient coefficient = new EexiCoefficient();
//
////        Engine me1 = new Engine(2, 4000, 3000);
//        Engine me2 = new Engine(1, 15000, 8250, 76);
//        Engine ae1 = new Engine(1, 0, 625, 76);
////        Engine ae2 = new Engine(1, 0, 625);
////        Engine ae3 = new Engine(1, 0, 625);
//
////        me1.addFuelType(FuelType.LNG, 158);
////        me1.addFuelType(FuelType.diesel, 6);
//        me2.addFuelType(FuelTypeEnglish.diesel, 166.5);
////        me2.addFuelType(FuelType.diesel, 6);
//
////        me1.setLngIsMainFuel(true);
////        ae1.setLngIsMainFuel(true);
////        me2.setLngIsMainFuel(false);
//
////        ae1.addFuelType(FuelType.LNG, 160);
////        ae1.addFuelType(FuelType.LNG, 160);
//        ae1.addFuelType(FuelTypeEnglish.diesel, 220);
////        ae2.addFuelType(FuelTypeEnglish.diesel, 220);
////        ae3.addFuelType(FuelTypeEnglish.diesel, 220);
//
//        coefficient.addMainEngine(me2);
////        coefficient.addMainEngine(me1);
//        coefficient.addAdditionalEngine(ae1);
////        coefficient.addAdditionalEngine(ae2);
////        coefficient.addAdditionalEngine(ae3);
////        coefficient.setC_F_MDO(3.206);
////        coefficient.setSFC_ME_MDO(165);
////        coefficient.setSFC_AE_MDO(187);
////        coefficient.setF_DF_gas(0.1261);
//        coefficient.setIceClassEnglish(IceClassEnglish.withoutIceClassOrIce1);
//        coefficient.setShipTypeEnglish(ShipTypeEnglish.BulkCarrier);
////        coefficient.setL(250);
////        coefficient.setL_pp(240);
////        coefficient.setB_s(40);
////        coefficient.setD_s(14);
//        coefficient.setDWT(150000);
//        coefficient.setV_ref(13.20);
////        coefficient.setF_w(1);
//
//        EexiRequiredCoefficient requiredCoefficient = new EexiRequiredCoefficient(coefficient);
//
//        double eexi = coefficient.calculateEEXI();
//        double eexi_required = requiredCoefficient.getRequiredEEXI();
//        DecimalFormat df = new DecimalFormat("#.###");
//        String eexi_str = df.format(eexi);
//        String eexi_required_str = df.format(eexi_required);
//        String stock = df.format((eexi_required - eexi) / eexi * 100);
//        System.out.println();
//        System.out.println("Достигнутый EEXI: " + eexi_str + " г CO2/т•морские мили");
//        System.out.println("Требуемый EEXI: " + eexi_required_str + " г CO2/т•морские мили");
//        System.out.println("Запас: " + stock + "%");
    }
}