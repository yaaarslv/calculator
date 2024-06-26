package org.ui;

import javax.swing.*;

public class EnergyEfficiencyProgram {
    private static StartWindow startWindow;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            startWindow = new StartWindow();
            startWindow.createAndShowGUI();
        });
    }
}
