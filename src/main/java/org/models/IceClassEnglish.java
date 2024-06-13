package org.models;

import java.util.Arrays;

public enum IceClassEnglish {
    withoutIceClassOrIce1("Without ice class or Ice1"),
    PC1_PC5_Arc9_Arc6 ("PC1_PC5_Arc9_Arc6"),
    IA_Super_Arc5_PC6 ("IA_Super_Arc5_PC6"),
    IA_Arc4_PC7 ("IA_Arc4_PC7"),
    IB_Ice3 ("IB_Ice3"),
    IC_Ice2 ("IC_Ice2");

    private final String title;

    IceClassEnglish(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static IceClassEnglish getByTitle(String title) {
        return Arrays.stream(IceClassEnglish.values()).filter(f -> f.title.equals(title)).findFirst().orElse(null);
    }
}
