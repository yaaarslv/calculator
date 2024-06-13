package org.models;

import java.util.Arrays;

public enum ShipTypeRussian {
    BulkCarrier("Балкер"),
    GasCarrier("Газовоз"),
    Tanker("Танкер"),
    ContainerCarrier("Контейнеровоз"),
    GenCargo("Ген. груз"),
    Refrigerator("Рефрижератор"),
    CombiShip("Комбинированное судно"),
    RoRoCarCarrier("Ро-ро автомобилевоз"),
    RoRoCargoCarrier("Ро-ро грузовой"),
    RoRoPassengerCarrier("Ро-ро пассажирский"),
    GasCarrierLNG("Газовоз СПГ"),
    CruisePassengerShip("Круизное пассажирское судно"),
    VSSRoRoPassengerCarrier("Пассажирское судно");

    private final String title;

    ShipTypeRussian(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static ShipTypeRussian getByTitle(String title) {
        return Arrays.stream(ShipTypeRussian.values()).filter(f -> f.title.equals(title)).findFirst().orElse(null);
    }
}
