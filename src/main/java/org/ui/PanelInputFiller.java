package org.ui;

import org.coefficients.EexiCoefficient;
import org.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
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
    private List<JCheckBox> correctionFactorsCheckBoxes;

    public PanelInputFiller(EexiCoefficient coefficient, Language language) {
        this.coefficient = coefficient;
        this.language = language;
        this.correctionFactorsCheckBoxes = new ArrayList<>();
    }

    public void addContentToPanelInput(JPanel panelInput) {
        panelInput.setLayout(null);
        JLabel shipNameLabel = new JLabel(language == Language.Russian ? "Название судна" : "Ship name");
        JTextField shipNameField = new JTextField();
        if (language == Language.Russian) {
            shipNameLabel.setBounds(300, 10, 100, 20);
            shipNameField.setBounds(405, 10, 200, 20);
        } else {
            shipNameLabel.setBounds(300, 10, 70, 20);
            shipNameField.setBounds(375, 10, 90, 20);
        }

        shipNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!shipNameField.getText().isEmpty()) {
                    coefficient.setShipName(shipNameField.getText());
                    shipNameField.setBackground(Color.WHITE);
                } else {
                    shipNameField.setBackground(Color.RED);
                }
            }
        });

        shipNameField.setToolTipText(language == Language.Russian ? "Название судна (для сохранения значения после ввода нажмите 'Enter')" : "Ship name (to save the value, when finished, press 'Enter')");

        panelInput.add(shipNameLabel);
        panelInput.add(shipNameField);

        JLabel imoNumberLabel = new JLabel(language == Language.Russian ? "Номер IMO" : "IMO number");
        JTextField imoNumberField = new JTextField();
        if (language == Language.Russian) {
            imoNumberLabel.setBounds(615, 10, 70, 20);
            imoNumberField.setBounds(690, 10, 90, 20);
        } else {
            imoNumberLabel.setBounds(475, 10, 70, 20);
            imoNumberField.setBounds(550, 10, 90, 20);
        }
        imoNumberField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!imoNumberField.getText().isEmpty() && isNumeric(imoNumberField.getText())) {
                    coefficient.setImoNumber(Integer.parseInt(imoNumberField.getText()));
                    imoNumberField.setBackground(Color.WHITE);
                } else {
                    imoNumberField.setBackground(Color.RED);
                }
            }
        });

        imoNumberField.setToolTipText(language == Language.Russian ? "Номер IMO (для сохранения значения после ввода нажмите 'Enter')" : "IMO number (to save the value, when finished, press 'Enter')");

        panelInput.add(imoNumberLabel);
        panelInput.add(imoNumberField);

        JLabel registerNumberLabel = new JLabel(language == Language.Russian ? "Регистровый номер" : "Register number");
        JTextField registerNumberField = new JTextField();
        if (language == Language.Russian) {
            registerNumberLabel.setBounds(790, 10, 120, 20);
            registerNumberField.setBounds(915, 10, 90, 20);
        } else {
            registerNumberLabel.setBounds(650, 10, 195, 20);
            registerNumberField.setBounds(750, 10, 90, 20);
        }
        registerNumberField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!registerNumberField.getText().isEmpty() && isNumeric(registerNumberField.getText())) {
                    coefficient.setRegisterNumber(Integer.parseInt(registerNumberField.getText()));
                    registerNumberField.setBackground(Color.WHITE);
                } else {
                    registerNumberField.setBackground(Color.RED);
                }
            }
        });

        registerNumberField.setToolTipText(language == Language.Russian ? "Регистровый номер (для сохранения значения после ввода нажмите 'Enter')" : "Register number (to save the value, when finished, press 'Enter')");

        panelInput.add(registerNumberLabel);
        panelInput.add(registerNumberField);

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

        lengthBetweenPerpendicularsLabel = new JLabel(language == Language.Russian ? "<html>Длина между перпендикулярами (L<sub>pp</sub>), м</html>" : "<html>Length between perpendiculars (L<sub>pp</sub>), m</html>");
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

        breadthLabel = new JLabel(language == Language.Russian ? "<html>Ширина судна (B<sub>s</sub>), м</html>" : "<html>Breadth (B<sub>s</sub>), m</html>");
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

        draughtLabel = new JLabel(language == Language.Russian ? "<html>Осадка судна (d<sub>s</sub>), м</html>" : "<html>Draught (d<sub>s</sub>), m</html>");
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

        MeetsToTheGeneralIACSRules.setBounds(10, 100, 300, 20);
        Reliquefaction.setBounds(10, 120, 300, 20);
        OilTankerOrNLSTanker.setBounds(10, 140, 300, 20);
        ChemicalTanker.setBounds(10, 160, 300, 20);
        ShuttleTanker.setBounds(10, 180, 300, 20);
        CargoCranes.setBounds(10, 200, 300, 20);
        SideLoaders.setBounds(10, 220, 300, 20);
        RoRoRamp.setBounds(10, 240, 300, 20);
        NonConventionalPropulsion.setBounds(10, 260, 300, 20);

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
            }
        };

        correctionFactorsCheckBoxes.add(MeetsToTheGeneralIACSRules);
        correctionFactorsCheckBoxes.add(Reliquefaction);
        correctionFactorsCheckBoxes.add(OilTankerOrNLSTanker);
        correctionFactorsCheckBoxes.add(ChemicalTanker);
        correctionFactorsCheckBoxes.add(ShuttleTanker);
        correctionFactorsCheckBoxes.add(CargoCranes);
        correctionFactorsCheckBoxes.add(SideLoaders);
        correctionFactorsCheckBoxes.add(RoRoRamp);
        correctionFactorsCheckBoxes.add(NonConventionalPropulsion);

        correctionFactorsCheckBoxes.forEach(checkbox -> {
            checkbox.addItemListener(correctionFactorsListener);
            panelInput.add(checkbox);
            checkbox.setEnabled(false);
        });
        MeetsToTheGeneralIACSRules.setEnabled(true);

        JLabel vrefLabel = new JLabel(language == Language.Russian ? "<html>Скорость судна (V<sub>ref</sub>), узлы</html>" : "<html>Ship speed (V<sub>ref</sub>), knots</html>");
        JTextField vrefField = new JTextField();
        if (language == Language.Russian) {
            vrefLabel.setBounds(10, 285, 200, 20);
            vrefField.setBounds(185, 285, 90, 20);
        } else {
            vrefLabel.setBounds(10, 285, 200, 20);
            vrefField.setBounds(165, 285, 90, 20);
        }

        vrefField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!vrefField.getText().isEmpty() && isNumeric(vrefField.getText())) {
                    coefficient.setV_ref(Double.parseDouble(vrefField.getText()));
                    vrefField.setBackground(Color.WHITE);
                } else {
                    vrefField.setBackground(Color.RED);
                }
            }
        });

        vrefField.setToolTipText(language == Language.Russian ? "Скорость судна (для сохранения значения после ввода нажмите 'Enter')" : "Speed of the vessel (to save the value, when finished, press 'Enter')");

        panelInput.add(vrefLabel);
        panelInput.add(vrefField);

        JLabel fwLabel = new JLabel(language == Language.Russian ? "<html>Коэффициент погодных условий (f<sub>w</sub>)</html>" : "<html>Weather factor (f<sub>w</sub>)</html>");
        JTextField fwField = new JTextField();
        if (language == Language.Russian) {
            fwLabel.setBounds(10, 315, 240, 20);
            fwField.setBounds(245, 315, 90, 20);
        } else {
            fwLabel.setBounds(10, 315, 115, 20);
            fwField.setBounds(135, 315, 90, 20);
        }

        fwField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fwField.getText().isEmpty() && isNumeric(fwField.getText())) {
                    coefficient.setF_w(Double.parseDouble(fwField.getText()));
                    fwField.setBackground(Color.WHITE);
                } else {
                    fwField.setBackground(Color.RED);
                }
            }
        });

        fwField.setToolTipText(language == Language.Russian ? "Коэффициент погодных условий (для сохранения значения после ввода нажмите 'Enter')" : "Weather factor (to save the value, when finished, press 'Enter')");

        panelInput.add(fwLabel);
        panelInput.add(fwField);

        JLabel fivseLabel = new JLabel(language == Language.Russian ? "<html>Коэффициент особенностей конструкции корпуса (f<sub>iVSE</sub>)</html>" : "<html>Capacity correction factor for ship specific voluntary structural (f<sub>iVSE</sub>)</html>");
        JTextField fivseField = new JTextField();
        if (language == Language.Russian) {
            fivseLabel.setBounds(10, 350, 350, 20);
            fivseField.setBounds(360, 350, 90, 20);
        } else {
            fivseLabel.setBounds(10, 350, 400, 20);
            fivseField.setBounds(410, 350, 90, 20);
        }

        fivseField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fivseField.getText().isEmpty() && isNumeric(fivseField.getText())) {
                    coefficient.setF_ivse(Double.parseDouble(fivseField.getText()));
                    fivseField.setBackground(Color.WHITE);
                } else {
                    fivseField.setBackground(Color.RED);
                }
            }
        });

        fivseField.setToolTipText(language == Language.Russian ? "Коэффициент погодных условий (для сохранения значения после ввода нажмите 'Enter')" : "Weather factor (to save the value, when finished, press 'Enter')");

        panelInput.add(fivseLabel);
        panelInput.add(fivseField);
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
            List<String> availableFactors = coefficient.getShipTypeAvailableCorrectionFactors(language);
            correctionFactorsCheckBoxes.forEach(checkbox -> {
                checkbox.setEnabled(availableFactors.contains(checkbox.getText()));
                checkbox.setSelected(false);
            });
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

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
