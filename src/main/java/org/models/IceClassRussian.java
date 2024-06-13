package org.models;

import java.util.Arrays;

public enum IceClassRussian {
    withoutIceClassOrIce1("Без ледового класса или Ice1"),
    PC1_PC5_Arc9_Arc6 ("PC1_PC5_Arc9_Arc6"),
    IA_Super_Arc5_PC6 ("IA_Super_Arc5_PC6"),
    IA_Arc4_PC7 ("IA_Arc4_PC7"),
    IB_Ice3 ("IB_Ice3"),
    IC_Ice2 ("IC_Ice2");

    private final String title;

    IceClassRussian(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static IceClassRussian getByTitle(String title) {
        return Arrays.stream(IceClassRussian.values()).filter(f -> f.title.equals(title)).findFirst().orElse(null);
    }
}
