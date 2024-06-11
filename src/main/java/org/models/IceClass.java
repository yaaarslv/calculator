package org.models;

public enum IceClass {
    withoutIceClass ("withoutIceClass"),
    PC1_PC5_Arc9_Arc6 ("-"),
    IA_Super_Arc5_PC6 ("IA_Super"),
    IA_Arc4_PC7 ("IA"),
    IB_Ice3 ("IB"),
    IC_Ice2 ("IC"),
    Ice1 ("-");

    private final String title;

    IceClass(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
