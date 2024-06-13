package org.ui;

import org.coefficients.EexiCoefficient;
import org.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.List;

public class PanelInputFiller implements ItemListener {
    private final EexiCoefficient coefficient;
    private final Language language;
    private JLabel lengthBetweenPerpendicularsLabel;
    private JTextField lengthBetweenPerpendicularsField;
    private JLabel breadthLabel;
    private JTextField breadthField;
    private JLabel draughtLabel;
    private JTextField draughtField;

    public PanelInputFiller(EexiCoefficient coefficient, Language language) {
        this.coefficient = coefficient;
        this.language = language;
    }

    public void addContentToPanelInput(JPanel panelInput) {
        panelInput.setLayout(null);
        JLabel shipTypeLabel = new JLabel(language == Language.Russian ? "Тип судна" : "Ship type");
        shipTypeLabel.setBounds(10, 10, 70, 20);

        List<String> russianValuesShipType = Arrays.stream(ShipTypeRussian.values()).map(ShipTypeRussian::getTitle).toList();
        List<String> englishValuesShipType = Arrays.stream(ShipTypeEnglish.values()).map(ShipTypeEnglish::getTitle).toList();
        JComboBox shipTypeBox = new JComboBox(language == Language.Russian ? russianValuesShipType.toArray() : englishValuesShipType.toArray());
        shipTypeBox.setBounds(85, 10, 200, 20);
        shipTypeBox.addItemListener(this::itemShipTypeChanged);

        panelInput.add(shipTypeLabel);
        panelInput.add(shipTypeBox);

        JLabel deadWeightLabel = new JLabel(language == Language.Russian ? "Дедвейт (DWT), т" : "Deadweight (DWT), t");
        JTextField deadWeightField = new JTextField();
        deadWeightLabel.setBounds(10, 35, 120, 20);
        deadWeightField.setBounds(130, 35, 90, 20);
        deadWeightField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!deadWeightField.getText().isEmpty() && isNumeric(deadWeightField.getText())) {
                    coefficient.setDWT(Double.parseDouble(deadWeightField.getText()));
                    deadWeightField.setBackground(Color.WHITE);
                } else {
                    deadWeightField.setBackground(Color.RED);
                }
            }
        });

        deadWeightField.setToolTipText(language == Language.Russian ? "Дедвейт судна (для сохранения значения после ввода нажмите 'Enter')" : "Deadweight of the vessel (to save the value, when finished, press 'Enter')");

        panelInput.add(deadWeightLabel);
        panelInput.add(deadWeightField);

        JLabel iceClassLabel = new JLabel(language == Language.Russian ? "Ледовый класс" : "Ice class");
        if (language == Language.Russian) {
            iceClassLabel.setBounds(10, 60, 105, 20);
        } else {
            iceClassLabel.setBounds(10, 60, 70, 20);
        }

        List<String> russianValuesIceClass = Arrays.stream(IceClassRussian.values()).map(IceClassRussian::getTitle).toList();
        List<String> englishValuesIceClass = Arrays.stream(IceClassEnglish.values()).map(IceClassEnglish::getTitle).toList();
        JComboBox iceClassBox = new JComboBox(language == Language.Russian ? russianValuesIceClass.toArray() : englishValuesIceClass.toArray());
        if (language == Language.Russian) {
            iceClassBox.setBounds(110, 60, 200, 20);
        } else {
            iceClassBox.setBounds(85, 60, 200, 20);
        }

        iceClassBox.addItemListener(this::itemIceClassChanged);

        panelInput.add(iceClassLabel);
        panelInput.add(iceClassBox);

        lengthBetweenPerpendicularsLabel = new JLabel(language == Language.Russian ? "Длина между перпендикулярами (Lpp), м" : "Length between perpendiculars (Lpp), m");
        lengthBetweenPerpendicularsField = new JTextField();
        if (language == Language.Russian) {
            lengthBetweenPerpendicularsLabel.setBounds(330, 60, 245, 20);
            lengthBetweenPerpendicularsField.setBounds(580, 60, 90, 20);
        } else {
            lengthBetweenPerpendicularsLabel.setBounds(330, 60, 245, 20);
            lengthBetweenPerpendicularsField.setBounds(565, 60, 90, 20);
        }

        lengthBetweenPerpendicularsField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!lengthBetweenPerpendicularsField.getText().isEmpty() && isNumeric(lengthBetweenPerpendicularsField.getText())) {
                    coefficient.setL_pp(Double.parseDouble(lengthBetweenPerpendicularsField.getText()));
                    lengthBetweenPerpendicularsField.setBackground(Color.WHITE);
                } else {
                    lengthBetweenPerpendicularsField.setBackground(Color.RED);
                }
            }
        });

        lengthBetweenPerpendicularsField.setToolTipText(language == Language.Russian ? "Длина между перпендикулярами (для сохранения значения после ввода нажмите 'Enter')" : "Length between perpendiculars (to save the value, when finished, press 'Enter')");

        lengthBetweenPerpendicularsLabel.setVisible(false);
        lengthBetweenPerpendicularsField.setVisible(false);

        panelInput.add(lengthBetweenPerpendicularsLabel);
        panelInput.add(lengthBetweenPerpendicularsField);

        breadthLabel = new JLabel(language == Language.Russian ? "Ширина судна (Bs), м" : "Breadth (Bs), m");
        breadthField = new JTextField();
        if (language == Language.Russian) {
            breadthLabel.setBounds(680, 60, 125, 20);
            breadthField.setBounds(810, 60, 90, 20);
        } else {
            breadthLabel.setBounds(665, 60, 125, 20);
            breadthField.setBounds(760, 60, 90, 20);
        }

        breadthField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!breadthField.getText().isEmpty() && isNumeric(breadthField.getText())) {
                    coefficient.setB_s(Double.parseDouble(breadthField.getText()));
                    breadthField.setBackground(Color.WHITE);
                } else {
                    breadthField.setBackground(Color.RED);
                }
            }
        });

        breadthField.setToolTipText(language == Language.Russian ? "Ширина судна (для сохранения значения после ввода нажмите 'Enter')" : "Breadth of the vessel (to save the value, when finished, press 'Enter')");

        breadthLabel.setVisible(false);
        breadthField.setVisible(false);

        panelInput.add(breadthLabel);
        panelInput.add(breadthField);

        draughtLabel = new JLabel(language == Language.Russian ? "Осадка судна (ds), м" : "Draught (ds), m");
        draughtField = new JTextField();
        if (language == Language.Russian) {
            draughtLabel.setBounds(910, 60, 125, 20);
            draughtField.setBounds(1040, 60, 90, 20);
        } else {
            draughtLabel.setBounds(860, 60, 125, 20);
            draughtField.setBounds(955, 60, 90, 20);
        }

        draughtField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!draughtField.getText().isEmpty() && isNumeric(draughtField.getText())) {
                    coefficient.setB_s(Double.parseDouble(draughtField.getText()));
                    draughtField.setBackground(Color.WHITE);
                } else {
                    draughtField.setBackground(Color.RED);
                }
            }
        });

        draughtField.setToolTipText(language == Language.Russian ? "Осадка судна (для сохранения значения после ввода нажмите 'Enter')" : "Draught of the vessel (to save the value, when finished, press 'Enter')");

        draughtLabel.setVisible(false);
        draughtField.setVisible(false);

        panelInput.add(draughtLabel);
        panelInput.add(draughtField);

        JCheckBox MeetsToTheGeneralIACSRules = new JCheckBox(language == Language.Russian ? CorrectionFactorRussian.MeetsToTheGeneralIACSRules.getTitle() : CorrectionFactorEnglish.MeetsToTheGeneralIACSRules.getTitle());
        JCheckBox Reliquefaction = new JCheckBox(language == Language.Russian ? CorrectionFactorRussian.Reliquefaction.getTitle() : CorrectionFactorEnglish.Reliquefaction.getTitle());
        JCheckBox OilTankerOrNLSTanker = new JCheckBox(language == Language.Russian ? CorrectionFactorRussian.OilTankerOrNLSTanker.getTitle() : CorrectionFactorEnglish.OilTankerOrNLSTanker.getTitle());
        JCheckBox ChemicalTanker = new JCheckBox(language == Language.Russian ? CorrectionFactorRussian.ChemicalTanker.getTitle() : CorrectionFactorEnglish.ChemicalTanker.getTitle());
        JCheckBox ShuttleTanker = new JCheckBox(language == Language.Russian ? CorrectionFactorRussian.ShuttleTanker.getTitle() : CorrectionFactorEnglish.ShuttleTanker.getTitle());
        JCheckBox CargoCranes = new JCheckBox(language == Language.Russian ? CorrectionFactorRussian.CargoCranes.getTitle() : CorrectionFactorEnglish.CargoCranes.getTitle());
        JCheckBox SideLoaders = new JCheckBox(language == Language.Russian ? CorrectionFactorRussian.SideLoaders.getTitle() : CorrectionFactorEnglish.SideLoaders.getTitle());
        JCheckBox RoRoRamp = new JCheckBox(language == Language.Russian ? CorrectionFactorRussian.RoRoRamp.getTitle() : CorrectionFactorEnglish.RoRoRamp.getTitle());
        JCheckBox NonConventionalPropulsion = new JCheckBox(language == Language.Russian ? CorrectionFactorRussian.NonConventionalPropulsion.getTitle() : CorrectionFactorEnglish.NonConventionalPropulsion.getTitle());

        MeetsToTheGeneralIACSRules.setBounds(0, 100, 300, 20);
        Reliquefaction.setBounds(0, 120, 300, 20);
        OilTankerOrNLSTanker.setBounds(0, 140, 300, 20);
        ChemicalTanker.setBounds(0, 160, 300, 20);
        ShuttleTanker.setBounds(0, 180, 300, 20);
        CargoCranes.setBounds(0, 200, 300, 20);
        SideLoaders.setBounds(0, 220, 300, 20);
        RoRoRamp.setBounds(0, 240, 300, 20);
        NonConventionalPropulsion.setBounds(0, 260, 300, 20);

        ItemListener correctionFactorsListener = new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    JCheckBox selectedItem = (JCheckBox) event.getItem();
                    if (language == Language.Russian) {
                        CorrectionFactorRussian typeRussian = CorrectionFactorRussian.getByTitle(selectedItem.getText());
                        coefficient.addCorrectionFactor(CorrectionFactorEnglish.valueOf(typeRussian.name()));
                    } else {
                        coefficient.addCorrectionFactor(CorrectionFactorEnglish.getByTitle(selectedItem.getText()));
                    }
                } else if (event.getStateChange() == ItemEvent.DESELECTED) {
                    JCheckBox selectedItem = (JCheckBox) event.getItem();
                    if (language == Language.Russian) {
                        CorrectionFactorRussian typeRussian = CorrectionFactorRussian.getByTitle(selectedItem.getText());
                        coefficient.removeCorrectionFactor(CorrectionFactorEnglish.valueOf(typeRussian.name()));
                    } else {
                        coefficient.removeCorrectionFactor(CorrectionFactorEnglish.getByTitle(selectedItem.getText()));
                    }
                }
                System.out.println(coefficient.getCorrectionFactorsEnglish());
            }
        };

        MeetsToTheGeneralIACSRules.addItemListener(correctionFactorsListener);
        Reliquefaction.addItemListener(correctionFactorsListener);
        OilTankerOrNLSTanker.addItemListener(correctionFactorsListener);
        ChemicalTanker.addItemListener(correctionFactorsListener);
        ShuttleTanker.addItemListener(correctionFactorsListener);
        CargoCranes.addItemListener(correctionFactorsListener);
        SideLoaders.addItemListener(correctionFactorsListener);
        RoRoRamp.addItemListener(correctionFactorsListener);
        NonConventionalPropulsion.addItemListener(correctionFactorsListener);

        panelInput.add(MeetsToTheGeneralIACSRules);
        panelInput.add(Reliquefaction);
        panelInput.add(OilTankerOrNLSTanker);
        panelInput.add(ChemicalTanker);
        panelInput.add(ShuttleTanker);
        panelInput.add(CargoCranes);
        panelInput.add(SideLoaders);
        panelInput.add(RoRoRamp);
        panelInput.add(NonConventionalPropulsion);
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
    }

    public void itemShipTypeChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED) {
            var selectedItem = event.getItem();
            if (language == Language.Russian) {
                ShipTypeRussian typeRussian = ShipTypeRussian.getByTitle(selectedItem.toString());
                coefficient.setShipTypeEnglish(ShipTypeEnglish.valueOf(typeRussian.name()));
            } else {
                coefficient.setShipTypeEnglish(ShipTypeEnglish.getByTitle(selectedItem.toString()));
            }
        }
    }

    public void itemIceClassChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED) {
            var selectedItem = event.getItem();
            if (language == Language.Russian) {
                IceClassRussian typeRussian = IceClassRussian.getByTitle(selectedItem.toString());
                coefficient.setIceClassEnglish(IceClassEnglish.valueOf(typeRussian.name()));
            } else {
                coefficient.setIceClassEnglish(IceClassEnglish.getByTitle(selectedItem.toString()));
            }

            if (coefficient.getIceClassEnglish() != IceClassEnglish.withoutIceClassOrIce1) {
                lengthBetweenPerpendicularsLabel.setVisible(true);
                breadthLabel.setVisible(true);
                draughtLabel.setVisible(true);
                lengthBetweenPerpendicularsField.setVisible(true);
                breadthField.setVisible(true);
                draughtField.setVisible(true);
            } else {
                lengthBetweenPerpendicularsLabel.setVisible(false);
                breadthLabel.setVisible(false);
                draughtLabel.setVisible(false);
                lengthBetweenPerpendicularsField.setVisible(false);
                breadthField.setVisible(false);
                draughtField.setVisible(false);
            }
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
