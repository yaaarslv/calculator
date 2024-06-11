package org.ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputWindow {
    private String language;
    private int i = 0;

    public InputWindow(String language) {
        this.language = language;
    }

    public void createAndShowGUI() {
        ImageIcon icon = new ImageIcon("src/main/resources/rmrs.png");
        JFrame frame;
        final JTabbedPane tabbedPane = new JTabbedPane();
        if ("Русский".equals(language)) {
            frame = new JFrame("Расчет коэффициента энергоэффективности существующего судна (КЭСС)");
            tabbedPane.addTab("Ввод данных", new JPanel());
            tabbedPane.addTab("Результаты", new JPanel());
        } else {
            frame = new JFrame("Calculation of the energy efficiency existing ship index (EEXI)");
            tabbedPane.addTab("Data input", new JPanel());
            tabbedPane.addTab("Results", new JPanel());
        }
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setLayout(new BorderLayout());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}