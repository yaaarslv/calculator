package org.ui;

import org.coefficients.EexiCoefficient;

import javax.swing.*;

public class PanelResultFiller {
    private final EexiCoefficient coefficient;
    private final Language language;

    public PanelResultFiller(EexiCoefficient coefficient, Language language) {
        this.coefficient = coefficient;
        this.language = language;
    }

    public void addContentToPanelResult(JPanel panelResult) {
        panelResult.add(new JButton("rfrrfrf"));
    }
}
