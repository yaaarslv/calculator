package org.ui;

import org.coefficients.EexiCoefficient;

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

    public InputWindow(String lang) {
        if (lang.equals("Русский")) {
            this.language = Language.Russian;
        } else {
            this.language = Language.English;
        }

        this.coefficient = new EexiCoefficient();
        this.panelInputFiller = new PanelInputFiller(coefficient, language);
        this.panelResultFiller = new PanelResultFiller(coefficient, language);
    }

    public void createAndShowGUI() {
        ImageIcon icon = new ImageIcon("src/main/resources/rmrs.png");
        JFrame frame = new JFrame(language == Language.Russian ? "Расчет коэффициента энергоэффективности существующего судна " +
                "(КЭСС)" : "Calculation of the energy efficiency existing ship index (EEXI)");

        final JTabbedPane tabbedPane = new JTabbedPane();
        JPanel panelInput = new JPanel();
        JPanel panelResult = new JPanel();

        panelInputFiller.addContentToPanelInput(panelInput);
        panelResultFiller.addContentToPanelResult(panelResult);

        tabbedPane.addTab(language == Language.Russian ? "Ввод данных" : "Data input", panelInput);
        tabbedPane.addTab(language == Language.Russian ? "Результаты" : "Results", panelResult);

        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(400, 400));

        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }
}