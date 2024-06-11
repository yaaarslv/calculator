package org.models;

import java.util.Hashtable;
import java.util.Map;

public class CiiFuelTable {
    private Map<String, Double> fuelFC;
    private Map<String, Double> fuelCF;
    private Map<String, Double> fuelFCVoyage;
    private Map<String, Double> fuelFCsShuttle;
    private Map<String, Double> fuelFCsSTS;
    private Map<String, Double> fuelElectricityGenerated;
    private Map<String, Double> fuelReeferDaysSea;
    private Map<String, Double> fuelReeferDaysPort;
    private Map<String, Double> fuelFCElectrical;
    private Map<String, Double> fuelFCBoiler;
    private Map<String, Double> fuelFCOther;
    private Map<String, Double> reducedMass;
    private Map<String, Double> ejectionMass;
    private double ejectedMassPerYear;

    public CiiFuelTable() {
        this.fuelCF = new Hashtable<>();
        this.fuelCF.put("diesel", 3.206);
        this.fuelCF.put("LFO", 3.151);
        this.fuelCF.put("HFO", 3.114);
        this.fuelCF.put("LPG", 3.0);
        this.fuelCF.put("LNG", 2.75);
        this.fuelCF.put("methanol", 1.375);
        this.fuelCF.put("ethanol", 1.913);
        this.fuelFC = new Hashtable<>();
        this.fuelFCVoyage = new Hashtable<>();
        this.fuelFCsShuttle = new Hashtable<>();
        this.fuelFCsSTS = new Hashtable<>();
        this.fuelElectricityGenerated = new Hashtable<>();
        this.fuelReeferDaysSea = new Hashtable<>();
        this.fuelFCElectrical = new Hashtable<>();
        this.fuelReeferDaysPort = new Hashtable<>();
        this.fuelFCBoiler = new Hashtable<>();
        this.fuelFCOther = new Hashtable<>();
        this.reducedMass = new Hashtable<>();
        this.ejectionMass = new Hashtable<>();
        this.ejectedMassPerYear = 0;
    }

    public double getEjectedMassPerYear() {
        double sum = ejectionMass.values().stream().mapToDouble(a -> a).sum();
        return sum;
    }

    public Map<String, Double> getEjectionMass() {
        return ejectionMass;
    }

    public void addEjectionMass(String fuel, Double number) {
        this.ejectionMass.put(fuel, number);
    }

    public Map<String, Double> getReducedMass() {
        return reducedMass;
    }

    public void addReducedMass(String fuel, Double number) {
        this.reducedMass.put(fuel, number);
    }

    public Map<String, Double> getFuelFCOther() {
        return fuelFCOther;
    }

    public void addFuelFCOther(String fuel, Double number) {
        this.fuelFCOther.put(fuel, number);
    }

    public Map<String, Double> getFuelFCBoiler() {
        return fuelFCBoiler;
    }

    public void addFuelFCBoiler(String fuel, Double number) {
        this.fuelFCBoiler.put(fuel, number);
    }

    public Map<String, Double> getFuelFCElectrical() {
        return fuelFCElectrical;
    }

    public void addFuelFCElectrical(String fuel, Double number) {
        this.fuelFCElectrical.put(fuel, number);
    }

    public Map<String, Double> getFuelReeferDaysPort() {
        return fuelReeferDaysPort;
    }

    public void addFuelReeferDaysPort(String fuel, Double number) {
        this.fuelReeferDaysPort.put(fuel, number);
    }

    public Map<String, Double> getFuelReeferDaysSea() {
        return fuelReeferDaysSea;
    }

    public void addFuelReeferDaysSea(String fuel, Double number) {
        this.fuelReeferDaysSea.put(fuel, number);
    }

    public Map<String, Double> getFuelElectricityGenerated() {
        return fuelElectricityGenerated;
    }

    public void addFuelElectricityGenerated(String fuel, Double number) {
        this.fuelElectricityGenerated.put(fuel, number);
    }

    public Map<String, Double> getFuelFCsSTS() {
        return fuelFCsSTS;
    }

    public void addFuelFCsSTS(String fuel, Double number) {
        this.fuelFCsSTS.put(fuel, number);
    }

    public Map<String, Double> getFuelFCsShuttle() {
        return fuelFCsShuttle;
    }

    public void addFuelFCsShuttle(String fuel, Double number) {
        this.fuelFCsShuttle.put(fuel, number);
    }

    public Map<String, Double> getFuelFCVoyage() {
        return fuelFCVoyage;
    }

    public void addFuelFCVoyage(String fuel, Double number) {
        this.fuelFCVoyage.put(fuel, number);
    }

    public Map<String, Double> getFuelCF() {
        return fuelCF;
    }

    public void addFuelCF(String fuel, Double number) {
        this.fuelCF.put(fuel, number);
    }

    public Map<String, Double> getFuelFC() {
        return fuelFC;
    }

    public void addFuelFC(String fuel, Double number) {
        this.fuelFC.put(fuel, number);
        this.ejectionMass.put(fuel, number * this.fuelCF.get(fuel));
    }
}
