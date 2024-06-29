package org.ui;

import lombok.Getter;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;

public class StartWindow {
    @Getter
    private String selectedLanguage;
    private InputWindow inputWindow;

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Energy Efficiency Existing Ship Index");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("rmrs.png"));
        JPanel titlePanel = new JPanel();

        frame.setIconImage(icon.getImage());
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Расчет коэффициента энергоэффективности существующего судна (КЭСС)");
        JLabel subtitle = new JLabel("Calculation of the energy efficiency existing ship index (EEXI)");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);
        titlePanel.add(subtitle);


        frame.add(titlePanel, BorderLayout.NORTH);
        Image originalImage = icon.getImage();
        Image resizedImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizesIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizesIcon);
        frame.add(imageLabel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton russianButton = new JButton("Русский");
        JButton englishButton = new JButton("English");
        buttonPanel.add(russianButton);
        buttonPanel.add(englishButton);

        JLabel bottomTextRussian = new JLabel("Российский морской регистр судоходства", SwingConstants.CENTER);
        JLabel bottomTextEnglish = new JLabel("Russian maritime register of shipping", SwingConstants.CENTER);
        JLabel year = new JLabel("2024", SwingConstants.CENTER);
        bottomTextRussian.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomTextEnglish.setAlignmentX(Component.CENTER_ALIGNMENT);
        year.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(buttonPanel);
        bottomPanel.add(bottomTextRussian);
        bottomPanel.add(bottomTextEnglish);
        bottomPanel.add(year);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        russianButton.addActionListener(e -> {
            selectedLanguage = "Русский";
            frame.dispose();
            inputWindow = new InputWindow(selectedLanguage);
            inputWindow.createAndShowGUI();
        });

        englishButton.addActionListener(e -> {
            selectedLanguage = "English";
            frame.dispose();
            inputWindow = new InputWindow(selectedLanguage);
            inputWindow.createAndShowGUI();
        });

        frame.setVisible(true);
    }
}
