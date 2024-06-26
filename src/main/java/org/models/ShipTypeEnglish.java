package org.models;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ShipTypeEnglish {
    BulkCarrier("Bulk carrier"),
    GasCarrier("Gas carrier (not LNG)"),
    Tanker("Tanker"),
    ContainerCarrier("Container ship"),
    GenCargo("General cargo ship"),
    Refrigerator("Refrigerated cargo ship"),
    CombiShip("Combination carrier"),
    RoRoCarCarrier("Ro-ro vehicle carrier"),
    RoRoCargoCarrier("Ro-ro cargo ship"),
    RoRoPassengerCarrier("Ro-ro passenger ship"),
    GasCarrierLNG("LNG gas carrier"),
    CruisePassengerShip("Cruise passenger ship"),
    VSSRoRoPassengerCarrier("Passenger ship");

    private final String title;

    ShipTypeEnglish(String title) {
        this.title = title;
    }

    public static ShipTypeEnglish getByTitle(String title) {
        return Arrays.stream(ShipTypeEnglish.values()).filter(f -> f.title.equals(title)).findFirst().orElse(null);
    }
}
