package org.models;

import lombok.Getter;

@Getter
public class EexiDecreasingIndex {
    private final ShipTypeEnglish shipTypeEnglish;
    private final double minDWT;
    private final double maxDWT;
    private final int percentage;
    private final boolean isRange;

    public EexiDecreasingIndex(ShipTypeEnglish shipTypeEnglish, double minDWT, double maxDWT, int percentage, boolean isRange) {
        this.shipTypeEnglish = shipTypeEnglish;
        this.minDWT = minDWT;
        this.maxDWT = maxDWT;
        this.percentage = percentage;
        this.isRange = isRange;
    }

    public boolean isWithinRange(double DWT) {
        return DWT >= minDWT && DWT < maxDWT;
    }
}
