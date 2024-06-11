package org.models;

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
}
