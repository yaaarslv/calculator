import org.coefficients.EexiCoefficient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.models.Engine;
import org.models.FuelTypeEnglish;
import org.models.IceClassEnglish;
import org.models.ShipTypeEnglish;

import java.text.DecimalFormat;

public class EexiTest {
    @Test
    public void test1() {
        EexiCoefficient coeff = new EexiCoefficient();
        coeff.setShipTypeEnglish(ShipTypeEnglish.BulkCarrier);
        coeff.setIceClassEnglish(IceClassEnglish.withoutIceClassOrIce1);
        coeff.setDWT(150000);
        coeff.setV_ref(13.20);
        Engine engineM = new Engine(1, 15000, 8250, 0, 0 ,0);
        engineM.addFuelType(FuelTypeEnglish.diesel, 166.5);
        Engine engineA = new Engine(1, 0, 625, 0, 0 ,0);
        engineA.addFuelType(FuelTypeEnglish.diesel, 220);
        coeff.addMainEngine(engineM);
        coeff.addAuxiliaryEngine(engineA);
        double result = coeff.calculateEEXI();
        DecimalFormat df = new DecimalFormat("#.##");
        String result_str = df.format(result);

        Assertions.assertEquals("2,45", result_str);
    }

    @Test
    public void test2() {
        EexiCoefficient coeff = new EexiCoefficient();
        coeff.setShipTypeEnglish(ShipTypeEnglish.BulkCarrier);
        coeff.setIceClassEnglish(IceClassEnglish.withoutIceClassOrIce1);
        coeff.setDWT(81200);
        coeff.setV_ref(14);
        Engine engineM = new Engine(1, 9930, 7447.5, 0, 0 ,0);
        engineM.addFuelType(FuelTypeEnglish.diesel, 165);
        Engine engineA = new Engine(1, 0, 496.5, 0, 0 ,0);
        engineA.addFuelType(FuelTypeEnglish.diesel, 210);
        coeff.addMainEngine(engineM);
        coeff.addAuxiliaryEngine(engineA);
        double result = coeff.calculateEEXI();
        DecimalFormat df = new DecimalFormat("#.##");
        String result_str = df.format(result);

        Assertions.assertEquals("3,76", result_str);
    }

    @Test
    public void test3() {
        EexiCoefficient coeff = new EexiCoefficient();
        coeff.setShipTypeEnglish(ShipTypeEnglish.BulkCarrier);
        coeff.setIceClassEnglish(IceClassEnglish.withoutIceClassOrIce1);
        coeff.setDWT(81200);
        coeff.setV_ref(14);
        Engine engineM = new Engine(2, 9930, 7447.5, 0, 0 ,0);
        engineM.addFuelType(FuelTypeEnglish.diesel, 6);
        engineM.addFuelType(FuelTypeEnglish.LNG, 136);
        Engine engineA = new Engine(2, 0, 496.5, 0, 0 ,0);
        engineA.addFuelType(FuelTypeEnglish.diesel, 7);
        engineA.addFuelType(FuelTypeEnglish.LNG, 160);
        coeff.addMainEngine(engineM);
        coeff.addAuxiliaryEngine(engineA);
        double result = coeff.calculateEEXI();
        DecimalFormat df = new DecimalFormat("#.##");
        String result_str = df.format(result);

        Assertions.assertEquals("2,78", result_str);
    }
}
