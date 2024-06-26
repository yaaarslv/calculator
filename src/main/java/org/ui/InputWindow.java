package org.ui;

import org.coefficients.EexiCoefficient;
import org.coefficients.EexiRequiredCoefficient;

import javax.swing.*;
import java.awt.*;


public class InputWindow {
    private final Language language;
    private final PanelInputFiller panelInputFiller;

    public InputWindow(String lang) {
        if (lang.equals("Русский")) {
            this.language = Language.Russian;
        } else {
            this.language = Language.English;
        }

        EexiCoefficient coefficient = new EexiCoefficient();
        EexiRequiredCoefficient requiredCoefficient = new EexiRequiredCoefficient(coefficient);
        this.panelInputFiller = new PanelInputFiller(coefficient, language, requiredCoefficient);
    }

    public void createAndShowGUI() {
        ImageIcon icon = new ImageIcon("src/main/resources/rmrs.png");
        JFrame frame = new JFrame(language == Language.Russian ? "Расчет коэффициента энергоэффективности существующего судна " +
                "(КЭСС)" : "Calculation of the energy efficiency existing ship index (EEXI)");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(400, 400));

        JPanel panelInput = new JPanel();
        panelInputFiller.addContentToPanelInput(panelInput);

        frame.getContentPane().add(panelInput);
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }
}