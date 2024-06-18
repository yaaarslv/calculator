package org.ui;

import org.coefficients.EexiCoefficient;
import org.coefficients.EexiRequiredCoefficient;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import static javax.swing.GroupLayout.Alignment.LEADING;

public class InputWindow {
    private final Language language;
    private PanelInputFiller panelInputFiller;
    private PanelResultFiller panelResultFiller;
    private final EexiCoefficient coefficient;
    private final EexiRequiredCoefficient requiredCoefficient;
    private final JFrame frame;

    public InputWindow(String lang, JFrame frame) {
        if (lang.equals("Русский")) {
            this.language = Language.Russian;
        } else {
            this.language = Language.English;
        }

        this.coefficient = new EexiCoefficient();
        this.requiredCoefficient = new EexiRequiredCoefficient(coefficient);
        this.panelInputFiller = new PanelInputFiller(coefficient, language, requiredCoefficient);
        this.panelResultFiller = new PanelResultFiller(coefficient, language);
        this.frame = frame;
    }

    public void createAndShowGUI() {
        ImageIcon icon = new ImageIcon("src/main/resources/rmrs.png");
        JFrame frame = new JFrame(language == Language.Russian ? "Расчет коэффициента энергоэффективности существующего судна " +
                "(КЭСС)" : "Calculation of the energy efficiency existing ship index (EEXI)");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(400, 400));

        final JTabbedPane tabbedPane = new JTabbedPane();
        JPanel panelInput = new JPanel();
        JPanel panelResult = new JPanel();

        panelInputFiller.addContentToPanelInput(panelInput);
        panelResultFiller.addContentToPanelResult(panelResult);

        tabbedPane.addTab(language == Language.Russian ? "Ввод данных" : "Data input", panelInput);
        tabbedPane.addTab(language == Language.Russian ? "Результаты" : "Results", panelResult);

        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }
}