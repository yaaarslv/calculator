package org.ui;

import org.coefficients.EexiCoefficient;
import org.coefficients.EexiRequiredCoefficient;
import org.models.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

public class PanelInputFiller implements ItemListener {
    private final EexiCoefficient coefficient;
    private final EexiRequiredCoefficient requiredCoefficient;
    private final Language language;
    private JLabel lengthBetweenPerpendicularsLabel;
    private JTextField lengthBetweenPerpendicularsField;
    private JLabel breadthLabel;
    private JTextField breadthField;
    private JLabel draughtLabel;
    private JTextField draughtField;
    private final List<JCheckBox> correctionFactorsCheckBoxes;
    private final List<Cell> unEditableCells;
    private final List<Component> temporaryComponents;
    private int additionalEngineFirstIndex;
    private JLabel volumetricDisplacementLabel;
    private JTextField volumetricDisplacementField;
    private JLabel specificCapacityLabel;
    private JTextField specificCapacityField;
    private JLabel powerOfAdditionalEnginesLabel;
    private JTextField powerOfAdditionalEnginesField;
    private JLabel grossTonnageLabel;
    private JTextField grossTonnageField;
    private JLabel deadWeightLabel;
    private JTextField deadWeightField;
    private JLabel lngTankCapacityLabel;
    private JTextField lngTankCapacityField;
    private JLabel factorOfReliquefyLabel;
    private JTextField factorOfReliquefyField;
    private JLabel factorOfBoilOffGasLabel;
    private JTextField factorOfBoilOffGasField;
    private JLabel factorOfDesignPerformanceOfReliquefyLabel;
    private JTextField factorOfDesignPerformanceOfReliquefyField;

    private JPanel panelCranes;
    private JLabel sideloaderFactorLabel;
    private JTextField sideloaderFactorField;
    private JLabel roroRampFactorLabel;
    private JTextField roroRampFactorField;

    public PanelInputFiller(EexiCoefficient coefficient, Language language, EexiRequiredCoefficient requiredCoefficient) {
        this.coefficient = coefficient;
        this.language = language;
        this.requiredCoefficient = requiredCoefficient;
        this.correctionFactorsCheckBoxes = new ArrayList<>();
        this.unEditableCells = new ArrayList<>();
        coefficient.setShipTypeEnglish(ShipTypeEnglish.BulkCarrier);
        coefficient.setIceClassEnglish(IceClassEnglish.withoutIceClassOrIce1);
        this.temporaryComponents = new ArrayList<>();
        this.additionalEngineFirstIndex = 1;
    }

    private void addUnEditableCell(int row, int column) {
        Cell cell = new Cell(row, column);
        unEditableCells.add(cell);
    }

    private void removeUnEditableCell(int row, int column) {
        unEditableCells.remove(unEditableCells.stream().filter(cell -> cell.getRow() == row && cell.getColumn() == column).findFirst().orElse(null));
    }

    private void recalculationIncreaseUnEditableCells(int row) {
        unEditableCells.stream().filter(cell -> cell.getRow() >= row).forEach(cell -> cell.setRow(cell.getRow() + 1));
    }

    private void recalculationDecreaseUnEditableCells(int row) {
        unEditableCells.stream().filter(cell -> cell.getRow() > row).forEach(cell -> cell.setRow(cell.getRow() - 1));
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

        shipNameField.addActionListener(e -> {
            if (!shipNameField.getText().isEmpty()) {
                coefficient.setShipName(shipNameField.getText());
                shipNameField.setBackground(Color.WHITE);
            } else {
                shipNameField.setBackground(Color.RED);
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
        imoNumberField.addActionListener(e -> {
            if (!imoNumberField.getText().isEmpty() && isNumeric(imoNumberField.getText())) {
                coefficient.setImoNumber(Integer.parseInt(imoNumberField.getText()));
                imoNumberField.setBackground(Color.WHITE);
            } else {
                imoNumberField.setBackground(Color.RED);
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
        registerNumberField.addActionListener(e -> {
            if (!registerNumberField.getText().isEmpty() && isNumeric(registerNumberField.getText())) {
                coefficient.setRegisterNumber(Integer.parseInt(registerNumberField.getText()));
                registerNumberField.setBackground(Color.WHITE);
            } else {
                registerNumberField.setBackground(Color.RED);
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

        deadWeightLabel = new JLabel(language == Language.Russian ? "Дедвейт (DWT), т" : "Deadweight (DWT), t");
        deadWeightField = new JTextField();
        deadWeightLabel.setBounds(10, 35, 120, 20);
        deadWeightField.setBounds(130, 35, 90, 20);
        deadWeightField.addActionListener(e -> {
            if (!deadWeightField.getText().isEmpty() && isNumeric(deadWeightField.getText())) {
                coefficient.setDWT(Double.parseDouble(deadWeightField.getText()));
                System.out.println(coefficient.getDWT());
                deadWeightField.setBackground(Color.WHITE);
            } else {
                deadWeightField.setBackground(Color.RED);
            }
        });

        deadWeightField.setToolTipText(language == Language.Russian ? "Дедвейт судна (для сохранения значения после ввода нажмите 'Enter')" : "Deadweight of the vessel (to save the value, when finished, press 'Enter')");

        panelInput.add(deadWeightLabel);
        panelInput.add(deadWeightField);

        grossTonnageLabel = new JLabel(language == Language.Russian ? "Гросстоннаж (GT), т" : "Grosstonnage (GT), t");
        grossTonnageField = new JTextField();
        grossTonnageLabel.setBounds(230, 35, 120, 20);
        grossTonnageField.setBounds(355, 35, 90, 20);
        grossTonnageField.addActionListener(e -> {
            if (!grossTonnageField.getText().isEmpty() && isNumeric(grossTonnageField.getText())) {
                coefficient.setGT(Double.parseDouble(grossTonnageField.getText()));
                grossTonnageField.setBackground(Color.WHITE);
            } else {
                grossTonnageField.setBackground(Color.RED);
            }
        });

        grossTonnageField.setToolTipText(language == Language.Russian ? "Дедвейт судна (для сохранения значения после ввода нажмите 'Enter')" : "Deadweight of the vessel (to save the value, when finished, press 'Enter')");

        grossTonnageLabel.setVisible(false);
        grossTonnageField.setVisible(false);

        panelInput.add(grossTonnageLabel);
        panelInput.add(grossTonnageField);

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

        lengthBetweenPerpendicularsField.addActionListener(e -> {
            if (!lengthBetweenPerpendicularsField.getText().isEmpty() && isNumeric(lengthBetweenPerpendicularsField.getText())) {
                coefficient.setL_pp(Double.parseDouble(lengthBetweenPerpendicularsField.getText()));
                lengthBetweenPerpendicularsField.setBackground(Color.WHITE);
            } else {
                lengthBetweenPerpendicularsField.setBackground(Color.RED);
            }
        });

        lengthBetweenPerpendicularsField.setToolTipText(language == Language.Russian ? "Длина между перпендикулярами (для сохранения значения после ввода нажмите 'Enter')" : "Length between perpendiculars (to save the value, when finished, press 'Enter')");

//        lengthBetweenPerpendicularsLabel.setVisible(false);
//        lengthBetweenPerpendicularsField.setVisible(false);

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

        breadthField.addActionListener(e -> {
            if (!breadthField.getText().isEmpty() && isNumeric(breadthField.getText())) {
                coefficient.setB_s(Double.parseDouble(breadthField.getText()));
                breadthField.setBackground(Color.WHITE);
            } else {
                breadthField.setBackground(Color.RED);
            }
        });

        breadthField.setToolTipText(language == Language.Russian ? "Ширина судна (для сохранения значения после ввода нажмите 'Enter')" : "Breadth of the vessel (to save the value, when finished, press 'Enter')");

//        breadthLabel.setVisible(false);
//        breadthField.setVisible(false);

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

        draughtField.addActionListener(e -> {
            if (!draughtField.getText().isEmpty() && isNumeric(draughtField.getText())) {
                coefficient.setB_s(Double.parseDouble(draughtField.getText()));
                draughtField.setBackground(Color.WHITE);
            } else {
                draughtField.setBackground(Color.RED);
            }
        });

        draughtField.setToolTipText(language == Language.Russian ? "Осадка судна (для сохранения значения после ввода нажмите 'Enter')" : "Draught of the vessel (to save the value, when finished, press 'Enter')");

//        draughtLabel.setVisible(false);
//        draughtField.setVisible(false);

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

        ItemListener correctionFactorsListener = event -> {
            JCheckBox selectedItem = (JCheckBox) event.getItem();
            CorrectionFactorEnglish correctionFactorEnglish;
            if (language == Language.Russian) {
                CorrectionFactorRussian typeRussian = CorrectionFactorRussian.getByTitle(selectedItem.getText());
                correctionFactorEnglish = CorrectionFactorEnglish.valueOf(typeRussian.name());
            } else {
                correctionFactorEnglish = CorrectionFactorEnglish.getByTitle(selectedItem.getText());
            }

            if (event.getStateChange() == ItemEvent.SELECTED) {
                coefficient.addCorrectionFactor(correctionFactorEnglish);
                changeFieldsVisibleByShipTypeAndCorrectionFactor(correctionFactorEnglish, true);
            } else if (event.getStateChange() == ItemEvent.DESELECTED) {
                coefficient.removeCorrectionFactor(correctionFactorEnglish);
                changeFieldsVisibleByShipTypeAndCorrectionFactor(correctionFactorEnglish, false);
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

        vrefField.addActionListener(e -> {
            if (!vrefField.getText().isEmpty() && isNumeric(vrefField.getText())) {
                coefficient.setV_ref(Double.parseDouble(vrefField.getText()));
                System.out.println(coefficient.getV_ref());
                vrefField.setBackground(Color.WHITE);
            } else {
                vrefField.setBackground(Color.RED);
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

        fwField.addActionListener(e -> {
            if (!fwField.getText().isEmpty() && isNumeric(fwField.getText())) {
                coefficient.setF_w(Double.parseDouble(fwField.getText()));
                fwField.setBackground(Color.WHITE);
            } else {
                fwField.setBackground(Color.RED);
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

        fivseField.addActionListener(e -> {
            if (!fivseField.getText().isEmpty() && isNumeric(fivseField.getText())) {
                coefficient.setF_ivse(Double.parseDouble(fivseField.getText()));
                fivseField.setBackground(Color.WHITE);
            } else {
                fivseField.setBackground(Color.RED);
            }
        });

        fivseField.setToolTipText(language == Language.Russian ? "Коэффициент особенностей конструкции корпуса (для сохранения значения после ввода нажмите 'Enter')" : "Capacity correction factor for ship specific voluntary structural (to save the value, when finished, press 'Enter')");

        panelInput.add(fivseLabel);
        panelInput.add(fivseField);

        volumetricDisplacementLabel = new JLabel(language == Language.Russian ? "<html>Объёмное водоизмещение (∇), м<sup>3</sup></html>" : "<html>Volumetric displacement (∇), m<sup>3</sup></html>");
        volumetricDisplacementField = new JTextField();

        volumetricDisplacementLabel.setBounds(10, 375, 210, 20);

        if (language == Language.Russian) {
            volumetricDisplacementField.setBounds(230, 375, 90, 20);
        } else {
            volumetricDisplacementField.setBounds(205, 375, 90, 20);
        }

        volumetricDisplacementField.addActionListener(e -> {
            if (!volumetricDisplacementField.getText().isEmpty() && isNumeric(volumetricDisplacementField.getText())) {
                coefficient.setDelta(Double.parseDouble(volumetricDisplacementField.getText()));
                volumetricDisplacementField.setBackground(Color.WHITE);
            } else {
                volumetricDisplacementField.setBackground(Color.RED);
            }
        });

        volumetricDisplacementField.setToolTipText(language == Language.Russian ? "Объёмное водоизмещение (для сохранения значения после ввода нажмите 'Enter')" : "Volumetric displacement (to save the value, when finished, press 'Enter')");

//        volumetricDisplacementLabel.setVisible(false);
//        volumetricDisplacementField.setVisible(false);

        panelInput.add(volumetricDisplacementLabel);
        panelInput.add(volumetricDisplacementField);

        specificCapacityLabel = new JLabel(language == Language.Russian ? "<html>Удельная вместимость (R), м<sup>3</sup> / т</html>" : "<html>Specific capacity (R), m<sup>3</sup> / t</html>");
        specificCapacityField = new JTextField();

        specificCapacityLabel.setBounds(10, 400, 210, 20);

        if (language == Language.Russian) {
            specificCapacityField.setBounds(220, 400, 90, 20);
        } else {
            specificCapacityField.setBounds(180, 400, 90, 20);
        }

        specificCapacityField.addActionListener(e -> {
            if (!specificCapacityField.getText().isEmpty() && isNumeric(specificCapacityField.getText())) {
                coefficient.setR(Double.parseDouble(specificCapacityField.getText()));
                specificCapacityField.setBackground(Color.WHITE);
            } else {
                specificCapacityField.setBackground(Color.RED);
            }
        });

        specificCapacityField.setToolTipText(language == Language.Russian ? "Удельная вместимость (для сохранения значения после ввода нажмите 'Enter')" : "Specific capacity (to save the value, when finished, press 'Enter')");

        specificCapacityLabel.setVisible(false);
        specificCapacityField.setVisible(false);

        panelInput.add(specificCapacityLabel);
        panelInput.add(specificCapacityField);

        powerOfAdditionalEnginesLabel = new JLabel(language == Language.Russian ? "<html>Мощность вспомогательной установки на ходу судна (P<sub>ae</sub>), кВт</html>" : "<html>Power of additional engines (P<sub>ae</sub>), kW</html>");
        powerOfAdditionalEnginesField = new JTextField();

        powerOfAdditionalEnginesLabel.setBounds(10, 400, 400, 20);

        if (language == Language.Russian) {
            powerOfAdditionalEnginesField.setBounds(395, 400, 90, 20);
        } else {
            powerOfAdditionalEnginesField.setBounds(240, 400, 90, 20);
        }

        powerOfAdditionalEnginesField.addActionListener(e -> {
            if (!powerOfAdditionalEnginesField.getText().isEmpty() && isNumeric(powerOfAdditionalEnginesField.getText())) {
                coefficient.setP_ae(Double.parseDouble(powerOfAdditionalEnginesField.getText()));
                powerOfAdditionalEnginesField.setBackground(Color.WHITE);
            } else {
                powerOfAdditionalEnginesField.setBackground(Color.RED);
            }
        });

        powerOfAdditionalEnginesField.setToolTipText(language == Language.Russian ? "Мощность вспомогательной установки на ходу судна (для сохранения значения после ввода нажмите 'Enter')" : "Power of additional engines (to save the value, when finished, press 'Enter')");

        powerOfAdditionalEnginesLabel.setVisible(false);
        powerOfAdditionalEnginesField.setVisible(false);

        panelInput.add(powerOfAdditionalEnginesLabel);
        panelInput.add(powerOfAdditionalEnginesField);

        lngTankCapacityLabel = new JLabel(language == Language.Russian ? "<html>Вместимость танков СПГ (CTC<sub>LNG</sub>), м<sup>3</sup></html>" : "<html>LNG tanks capacity (CTC<sub>LNG</sub>), m<sup>3</sup></html>");
        lngTankCapacityField = new JTextField();

        lngTankCapacityLabel.setBounds(10, 425, 300, 25);

        if (language == Language.Russian) {
            lngTankCapacityField.setBounds(255, 425, 90, 20);
        } else {
            lngTankCapacityField.setBounds(220, 425, 90, 20);
        }

        lngTankCapacityField.addActionListener(e -> {
            if (!lngTankCapacityField.getText().isEmpty() && isNumeric(lngTankCapacityField.getText())) {
                coefficient.setCTC_LNG(Double.parseDouble(lngTankCapacityField.getText()));
                lngTankCapacityField.setBackground(Color.WHITE);
            } else {
                lngTankCapacityField.setBackground(Color.RED);
            }
        });

        lngTankCapacityField.setToolTipText(language == Language.Russian ? "Вместимость танков СПГ (для сохранения значения после ввода нажмите 'Enter')" : "LNG tanks capacity (to save the value, when finished, press 'Enter')");

        lngTankCapacityLabel.setVisible(false);
        lngTankCapacityField.setVisible(false);

        panelInput.add(lngTankCapacityLabel);
        panelInput.add(lngTankCapacityField);

        factorOfReliquefyLabel = new JLabel(language == Language.Russian ? "<html>Коэффициент повторного сжижения (R<sub>reliquefy</sub>)</html>" : "<html>Factor of reliquefy (R<sub>reliquefy</sub>)</html>");
        factorOfReliquefyField = new JTextField();

        factorOfReliquefyLabel.setBounds(10, 450, 300, 25);

        if (language == Language.Russian) {
            factorOfReliquefyField.setBounds(300, 450, 90, 20);
        } else {
            factorOfReliquefyField.setBounds(220, 450, 90, 20);
        }

        factorOfReliquefyField.addActionListener(e -> {
            if (!factorOfReliquefyField.getText().isEmpty() && isNumeric(factorOfReliquefyField.getText())) {
                coefficient.setR_reliquefy(Double.parseDouble(factorOfReliquefyField.getText()));
                factorOfReliquefyField.setBackground(Color.WHITE);
            } else {
                factorOfReliquefyField.setBackground(Color.RED);
            }
        });

        factorOfReliquefyField.setToolTipText(language == Language.Russian ? "Коэффициент повторного сжижения (для сохранения значения после ввода нажмите 'Enter')" : "Factor of reliquefy (to save the value, when finished, press 'Enter')");

        factorOfReliquefyLabel.setVisible(false);
        factorOfReliquefyField.setVisible(false);

        panelInput.add(factorOfReliquefyLabel);
        panelInput.add(factorOfReliquefyField);

        factorOfBoilOffGasLabel = new JLabel(language == Language.Russian ? "Коэффициент испарения в день (BOR), % / день" : "Factor of boil-offgas (BOR), % / day");
        factorOfBoilOffGasField = new JTextField();

        factorOfBoilOffGasLabel.setBounds(10, 475, 300, 25);

        if (language == Language.Russian) {
            factorOfBoilOffGasField.setBounds(305, 475, 90, 20);
        } else {
            factorOfBoilOffGasField.setBounds(220, 475, 90, 20);
        }

        factorOfBoilOffGasField.addActionListener(e -> {
            if (!factorOfBoilOffGasField.getText().isEmpty() && isNumeric(factorOfBoilOffGasField.getText())) {
                coefficient.setBOR(Double.parseDouble(factorOfBoilOffGasField.getText()));
                factorOfBoilOffGasField.setBackground(Color.WHITE);
            } else {
                factorOfBoilOffGasField.setBackground(Color.RED);
            }
        });

        factorOfBoilOffGasField.setToolTipText(language == Language.Russian ? "Коэффициент испарения в день (для сохранения значения после ввода нажмите 'Enter')" : "Factor of boil-offgas (to save the value, when finished, press 'Enter')");

        factorOfBoilOffGasLabel.setVisible(false);
        factorOfBoilOffGasField.setVisible(false);

        panelInput.add(factorOfBoilOffGasLabel);
        panelInput.add(factorOfBoilOffGasField);

        factorOfDesignPerformanceOfReliquefyLabel = new JLabel(language == Language.Russian ? "<html>Коэфф. проектной произв-ти повторного сжижения (COP<sub>cooling</sub>)</html>" : "<html>Factor of design performance of reliquefy (COP<sub>cooling</sub>)</html>");
        factorOfDesignPerformanceOfReliquefyField = new JTextField();

        factorOfDesignPerformanceOfReliquefyLabel.setBounds(10, 500, 500, 25);

        if (language == Language.Russian) {
            factorOfDesignPerformanceOfReliquefyField.setBounds(395, 500, 90, 20);
        } else {
            factorOfDesignPerformanceOfReliquefyField.setBounds(340, 500, 90, 20);
        }

        factorOfDesignPerformanceOfReliquefyField.addActionListener(e -> {
            if (!factorOfDesignPerformanceOfReliquefyField.getText().isEmpty() && isNumeric(factorOfDesignPerformanceOfReliquefyField.getText())) {
                coefficient.setCOP_cooling(Double.parseDouble(factorOfDesignPerformanceOfReliquefyField.getText()));
                factorOfDesignPerformanceOfReliquefyField.setBackground(Color.WHITE);
            } else {
                factorOfDesignPerformanceOfReliquefyField.setBackground(Color.RED);
            }
        });

        factorOfDesignPerformanceOfReliquefyField.setToolTipText(language == Language.Russian ? "Коэффициент проектной производительности повторного сжижения (для сохранения значения после ввода нажмите 'Enter')" : "Factor of design performance of reliquefy (to save the value, when finished, press 'Enter')");

        factorOfDesignPerformanceOfReliquefyLabel.setVisible(false);
        factorOfDesignPerformanceOfReliquefyField.setVisible(false);

        panelInput.add(factorOfDesignPerformanceOfReliquefyLabel);
        panelInput.add(factorOfDesignPerformanceOfReliquefyField);

        sideloaderFactorLabel = new JLabel(language == Language.Russian ? "<html>Коэффициент бортовой аппарели (лацпорт) (f<sub>sideloader</sub>)</html>" : "<html>Sideloader factor (f<sub>sideloader</sub>)</html>");
        sideloaderFactorField = new JTextField();

        sideloaderFactorLabel.setBounds(10, 425, 400, 25);

        if (language == Language.Russian) {
            sideloaderFactorField.setBounds(360, 425, 90, 20);
        } else {
            sideloaderFactorField.setBounds(200, 425, 90, 20);
        }

        sideloaderFactorField.addActionListener(e -> {
            if (!sideloaderFactorField.getText().isEmpty() && isNumeric(sideloaderFactorField.getText())) {
                coefficient.setF_sideloader(Double.parseDouble(sideloaderFactorField.getText()));
                sideloaderFactorField.setBackground(Color.WHITE);
            } else {
                sideloaderFactorField.setBackground(Color.RED);
            }
        });

        sideloaderFactorField.setToolTipText(language == Language.Russian ? "Коэффициент бортовой аппарели (лацпорт) (для сохранения значения после ввода нажмите 'Enter')" : "Sideloader factor (to save the value, when finished, press 'Enter')");

        sideloaderFactorLabel.setVisible(false);
        sideloaderFactorField.setVisible(false);

        panelInput.add(sideloaderFactorLabel);
        panelInput.add(sideloaderFactorField);

        roroRampFactorLabel = new JLabel(language == Language.Russian ? "<html>Коэффициент грузовой аппарели (f<sub>RoRo</sub>)</html>" : "<html>Ro-ro ramp factor (f<sub>RoRo</sub>)</html>");
        roroRampFactorField = new JTextField();

        roroRampFactorLabel.setBounds(10, 450, 255, 25);

        if (language == Language.Russian) {
            roroRampFactorField.setBounds(270, 450, 90, 20);
        } else {
            roroRampFactorField.setBounds(175, 450, 90, 20);
        }

        roroRampFactorField.addActionListener(e -> {
            if (!roroRampFactorField.getText().isEmpty() && isNumeric(roroRampFactorField.getText())) {
                coefficient.setF_roro(Double.parseDouble(roroRampFactorField.getText()));
                roroRampFactorField.setBackground(Color.WHITE);
            } else {
                roroRampFactorField.setBackground(Color.RED);
            }
        });

        roroRampFactorField.setToolTipText(language == Language.Russian ? "Коэффициент грузовой аппарели (для сохранения значения после ввода нажмите 'Enter')" : "Ro-ro ramp factor (to save the value, when finished, press 'Enter')");

        roroRampFactorLabel.setVisible(false);
        roroRampFactorField.setVisible(false);

        panelInput.add(roroRampFactorLabel);
        panelInput.add(roroRampFactorField);


        JPanel panelEngine = new JPanel();
        DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                } else
                    return unEditableCells.stream().noneMatch(cell -> cell.getColumn() == column && cell.getRow() == row);
            }
        };

        model.addColumn(language == Language.Russian ? "<html><b>Тип <br>двигателя</b></html>" : "<html><b>Engine type</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Кол-во</b></html>" : "<html><b>Count</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Мощность (MCR<sub>i</sub>) данного <br>двигателя, <br>кВт</b></html>" : "<html><b>Power (MCR<sub>i</sub>), kW</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Количество <br>типов топлива для данного двигателя </b></html>" : "<html><b>Available fuel type count</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Мощность (P<sub>i</sub>) данного <br>двигателя, <br>кВт</b></html>" : "<html><b>Power (P<sub>i</sub>), kW</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Тип топлива <br>основного двигателя</b></html>" : "<html><b>Main engine fuel type</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Тип запального <br>топлива</b></html>" : "<html><b>Pilotfuel type</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Удельный расход <br>основного топлива (SFC), <br>г / кВт * ч</b></html>" : "<html><b>Specific consumption fuel oil (SFC), <br>g / kW * h</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Удельный расход <br>запального топлива (SFC<sub>Pilotfuel</sub>), <br>г / кВт * ч</b></html>" : "<html><b>Specific consumption fuel oil (SFC<sub>Pilotfuel</sub>), <br>g / kW * h</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>КПД эл. генератора, %</b></html>" : "<html><b>Efficiency of el. generator, %</b></html>");

        model.addRow(new Object[]{language == Language.Russian ? EngineTypeRussian.Main.getTitle() : EngineTypeEnglish.Main.getTitle()});
        model.addRow(new Object[]{language == Language.Russian ? EngineTypeRussian.Additional.getTitle() : EngineTypeEnglish.Additional.getTitle(), null, "0"});
        addUnEditableCell(1, 2);
        JTable table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);

        List<String> russianFuelShipType = Arrays.stream(FuelTypeRussian.values()).map(FuelTypeRussian::getTitle).toList();
        List<String> englishFuelShipType = Arrays.stream(FuelTypeEnglish.values()).map(FuelTypeEnglish::getTitle).toList();
        JComboBox fuelTypeBox = new JComboBox(language == Language.Russian ? russianFuelShipType.toArray() : englishFuelShipType.toArray());
//        JComboBox engineCountBox = new JComboBox(new Object[]{"1", "2"});
        JComboBox fuelCountBox = new JComboBox(new Object[]{"1", "2"});

        table.getColumnModel().getColumn(0).setPreferredWidth(95);
        table.getColumnModel().getColumn(1).setPreferredWidth(35);
//        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(engineCountBox));
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(fuelCountBox));
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(fuelTypeBox));
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(fuelTypeBox));
        table.getColumnModel().getColumn(7).setPreferredWidth(100);
        table.getColumnModel().getColumn(8).setPreferredWidth(100);

        table.getTableHeader().setPreferredSize(new Dimension(1000, 130));
        table.setRowHeight(25);
        table.getTableHeader().setResizingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setHorizontalTextPosition(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        panelEngine.setLayout(new BorderLayout());
        panelEngine.setBounds(520, 160, 1000, getTableHeight(table));
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLACK));
        model.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (model.getValueAt(row, column) != null) {
                    if (column != 5 && column != 6 && !isNumeric((String) model.getValueAt(row, column))) {
                        JOptionPane.showMessageDialog(null, language == Language.Russian ? "Недопустимый тип: вводите только числа" : "Unavailable type: please insert only numbers");
                        model.setValueAt(null, row, column);
                    } else if (column != 5 && column != 6 && Double.parseDouble((String) model.getValueAt(row, column)) < 0) {
                        JOptionPane.showMessageDialog(null, language == Language.Russian ? "Недопустимое значение: вводите только только положительные значения" : "Unavailable value: please input only positive numbers");
                        model.setValueAt(null, row, column);
                    } else {
                        if (column == 1) {
                            if (model.getValueAt(row, column).equals("0")) {
                                JOptionPane.showMessageDialog(null, language == Language.Russian ? "Недопустимое значение: количество двигателей не может быть нулевым" : "Unavailable value: engine count can't be zero");
                                model.setValueAt(null, row, column);
                            } else {
                                if (row == 0) {
                                    int diff = additionalEngineFirstIndex;
                                    int newCellsCount = Integer.parseInt((String) model.getValueAt(row, column));
                                    if (diff - newCellsCount > 0) {
                                        for (int i = diff - 1; i >= newCellsCount; i--) {
                                            model.removeRow(additionalEngineFirstIndex - 1);
                                            removeUnEditableCell(additionalEngineFirstIndex - 1, column);
                                            recalculationDecreaseUnEditableCells(additionalEngineFirstIndex - 1);
                                            additionalEngineFirstIndex -= 1;
                                        }
                                    } else if (diff - newCellsCount < 0) {
                                        for (int i = diff; i < newCellsCount; i++) {
                                            model.insertRow(i, new Object[]{model.getValueAt(row, 0), "---------", model.getValueAt(row, 2), model.getValueAt(row, 3), model.getValueAt(row, 4), model.getValueAt(row, 5), model.getValueAt(row, 6), model.getValueAt(row, 7), model.getValueAt(row, 8), model.getValueAt(row, 9)});
                                            recalculationIncreaseUnEditableCells(i);
                                            addUnEditableCell(i, column);
                                            additionalEngineFirstIndex += 1;
                                        }
                                    }
                                } else if (row == additionalEngineFirstIndex) {
                                    int diff = model.getRowCount() - additionalEngineFirstIndex;
                                    int newCellsCount = Integer.parseInt((String) model.getValueAt(row, column));

                                    if (diff - newCellsCount > 0) {
                                        int size = model.getRowCount();
                                        for (int i = 0; i < (size - newCellsCount) - additionalEngineFirstIndex; i++) {
                                            removeUnEditableCell(model.getRowCount() - 1, column);
                                            removeUnEditableCell(model.getRowCount() - 1, 2);
                                            removeUnEditableCell(model.getRowCount() - 1, 9);
                                            model.removeRow(model.getRowCount() - 1);
                                        }
                                    } else if (diff - newCellsCount < 0) {
                                        for (int i = 0; i < newCellsCount - diff; i++) {
                                            model.addRow(new Object[]{model.getValueAt(row, 0), "---------", model.getValueAt(row, 2), model.getValueAt(row, 3), model.getValueAt(row, 4), model.getValueAt(row, 5), model.getValueAt(row, 6), model.getValueAt(row, 7), model.getValueAt(row, 8), model.getValueAt(row, 9)});
                                            addUnEditableCell(model.getRowCount() - 1, column);
                                            addUnEditableCell(model.getRowCount() - 1, 2);
                                            addUnEditableCell(model.getRowCount() - 1, 9);
                                        }
                                    }
                                }
                            }

                            updatePanelEngine(panelEngine, table);
                        } else if (column == 3) {
                            if (model.getValueAt(row, column).equals("2")) {
                                removeUnEditableCell(row, 6);
                                removeUnEditableCell(row, 8);
                            } else if (model.getValueAt(row, column).equals("1")) {
                                model.setValueAt("----------------", row, 6);
                                model.setValueAt("0", row, 8);
                                addUnEditableCell(row, 6);
                                addUnEditableCell(row, 8);
                            }
                        }

                    }
                }
            }

        });

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setWheelScrollingEnabled(true);

        panelEngine.add(scrollPane);
        panelInput.add(panelEngine);

        JLabel shaftGenCountlabel = new JLabel(language == Language.Russian ? "Количество валогенераторов" : "Shaft generators quantity");
        JTextField shaftGenCountField = new JTextField();

        if (language == Language.Russian) {
            shaftGenCountlabel.setBounds(820, 100, 175, 20);
        } else {
            shaftGenCountlabel.setBounds(850, 100, 175, 20);
        }


        shaftGenCountField.setBounds(1000, 100, 90, 20);

        shaftGenCountField.addActionListener(e -> {
            if (!shaftGenCountField.getText().isEmpty() && isNumeric(shaftGenCountField.getText())) {
                coefficient.setShaftGenCount(Double.parseDouble(shaftGenCountField.getText()));
                shaftGenCountField.setBackground(Color.WHITE);
            } else {
                shaftGenCountField.setBackground(Color.RED);
            }
        });

        shaftGenCountField.setToolTipText(language == Language.Russian ? "Количество валогенераторов (для сохранения значения после ввода нажмите 'Enter')" : "Shaft generators quantity (to save the value, when finished, press 'Enter')");

        panelInput.add(shaftGenCountlabel);
        panelInput.add(shaftGenCountField);

        JLabel shaftGenMcrlabel = new JLabel(language == Language.Russian ? "<html>Мощность валогенераторов (MCR<sub>PTO</sub>), кВт</html>" : "<html>Power of shaft generators (MCR<sub>PTO</sub>), kW</html>");
        JTextField shaftGenMcrField = new JTextField();

        if (language == Language.Russian) {
            shaftGenMcrlabel.setBounds(1160, 100, 290, 20);
        } else {
            shaftGenMcrlabel.setBounds(1180, 100, 290, 20);
        }


        shaftGenMcrField.setBounds(1425, 100, 90, 20);

        shaftGenMcrField.addActionListener(e -> {
            if (!shaftGenMcrField.getText().isEmpty() && isNumeric(shaftGenMcrField.getText())) {
                coefficient.setMCR_PTO(Double.parseDouble(shaftGenMcrField.getText()));
                shaftGenMcrField.setBackground(Color.WHITE);
            } else {
                shaftGenMcrField.setBackground(Color.RED);
            }
        });

        shaftGenMcrField.setToolTipText(language == Language.Russian ? "Мощность валогенераторов (для сохранения значения после ввода нажмите 'Enter')" : "Power of shaft generator (to save the value, when finished, press 'Enter')");

        panelInput.add(shaftGenMcrlabel);
        panelInput.add(shaftGenMcrField);

        JLabel elPropEngCountlabel = new JLabel(language == Language.Russian ? "Количество гребных эл. двигателей" : "Electric propeller engine quantity");
        JTextField elPropEngCountField = new JTextField();

        if (language == Language.Russian) {
            elPropEngCountlabel.setBounds(780, 125, 225, 20);
        } else {
            elPropEngCountlabel.setBounds(805, 125, 190, 20);
        }


        elPropEngCountField.setBounds(1000, 125, 90, 20);

        elPropEngCountField.addActionListener(e -> {
            if (!elPropEngCountField.getText().isEmpty() && isNumeric(elPropEngCountField.getText())) {
                coefficient.setElPropEngCount(Double.parseDouble(elPropEngCountField.getText()));
                elPropEngCountField.setBackground(Color.WHITE);
            } else {
                elPropEngCountField.setBackground(Color.RED);
            }
        });

        elPropEngCountField.setToolTipText(language == Language.Russian ? "Количество гребных эл. двигателей (для сохранения значения после ввода нажмите 'Enter')" : "Electric propeller engine quantity (to save the value, when finished, press 'Enter')");

        panelInput.add(elPropEngCountlabel);
        panelInput.add(elPropEngCountField);

        JLabel elPropEngMcrlabel = new JLabel(language == Language.Russian ? "<html>Мощность гребных эл. двигателей (MCR<sub>PTI</sub>), кВт</html>" : "<html>Power of electric propeller engines (MCR<sub>PTI</sub>), kW</html>");
        JTextField elPropEngMcrField = new JTextField();

        if (language == Language.Russian) {
            elPropEngMcrlabel.setBounds(1125, 125, 290, 20);
        } else {
            elPropEngMcrlabel.setBounds(1132, 125, 290, 20);
        }


        elPropEngMcrField.setBounds(1425, 125, 90, 20);

        elPropEngMcrField.addActionListener(e -> {
            if (!elPropEngMcrField.getText().isEmpty() && isNumeric(elPropEngMcrField.getText())) {
                coefficient.setMCR_PTI(Double.parseDouble(elPropEngMcrField.getText()));
                elPropEngMcrField.setBackground(Color.WHITE);
            } else {
                elPropEngMcrField.setBackground(Color.RED);
            }
        });

        elPropEngMcrField.setToolTipText(language == Language.Russian ? "Мощность гребных эл. двигателей (для сохранения значения после ввода нажмите 'Enter')" : "Power of electric propeller engines (to save the value, when finished, press 'Enter')");

        panelInput.add(elPropEngMcrlabel);
        panelInput.add(elPropEngMcrField);


        panelCranes = new JPanel();
        DefaultTableModel modelCranes = new DefaultTableModel();

        modelCranes.addColumn("");
        modelCranes.addColumn(language == Language.Russian ? "<html><b>Количество кранов <br>одинаковой грузоподъёмности</b></html>" : "<html><b>Cranes count with equal load capacity</b></html>");
        modelCranes.addColumn(language == Language.Russian ? "<html><b>Грузоподъёмность, т</b></html>" : "<html><b>Load capacity, t</b></html>");
        modelCranes.addColumn(language == Language.Russian ? "<html><b>Высота <br>подъёма, м</b></html>" : "<html><b>Reach, m</b></html>");

        modelCranes.addRow(new Object[]{language == Language.Russian ? "Грузовые краны 1" : "Cargo cranes 1"});
        modelCranes.addRow(new Object[]{language == Language.Russian ? "Грузовые краны 2" : "Cargo cranes 2"});
        modelCranes.addRow(new Object[]{language == Language.Russian ? "Грузовые краны 3" : "Cargo cranes 3"});

        JTable tableCranes = new JTable(modelCranes);
        tableCranes.getTableHeader().setReorderingAllowed(false);

        tableCranes.getColumnModel().getColumn(0).setPreferredWidth(120);
        tableCranes.getColumnModel().getColumn(1).setPreferredWidth(130);
        tableCranes.getColumnModel().getColumn(2).setPreferredWidth(140);
        tableCranes.getColumnModel().getColumn(3).setPreferredWidth(75);

        tableCranes.getTableHeader().setPreferredSize(new Dimension(455, 90));
        tableCranes.setRowHeight(25);
        tableCranes.getTableHeader().setResizingAllowed(false);

        tableCranes.setDefaultRenderer(Object.class, centerRenderer);

        panelCranes.setLayout(new BorderLayout());
        panelCranes.setBounds(10, 480, 455, getTableHeight(tableCranes));
        tableCranes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tableCranes.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLACK));
        modelCranes.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (modelCranes.getValueAt(row, column) != null) {
                    if (!isNumeric((String) modelCranes.getValueAt(row, column))) {
                        JOptionPane.showMessageDialog(null, language == Language.Russian ? "Недопустимый тип: вводите только числа" : "Unavailable type: please insert only numbers");
                        modelCranes.setValueAt(null, row, column);
                    } else if (Double.parseDouble((String) modelCranes.getValueAt(row, column)) < 0) {
                        JOptionPane.showMessageDialog(null, language == Language.Russian ? "Недопустимое значение: вводите только только положительные значения" : "Unavailable value: please input only positive numbers");
                        modelCranes.setValueAt(null, row, column);
                    }
                }
            }
        });

        panelCranes.add(new JScrollPane(tableCranes));
        panelCranes.setVisible(false);
        panelInput.add(panelCranes);


//        temporaryComponents.add(lengthBetweenPerpendicularsLabel);
//        temporaryComponents.add(lengthBetweenPerpendicularsField);
//        temporaryComponents.add(breadthLabel);
//        temporaryComponents.add(breadthField);
//        temporaryComponents.add(draughtLabel);
//        temporaryComponents.add(draughtField);
        temporaryComponents.add(deadWeightLabel);
        temporaryComponents.add(deadWeightField);
//        temporaryComponents.add(volumetricDisplacementLabel);
//        temporaryComponents.add(volumetricDisplacementField);
        temporaryComponents.add(specificCapacityLabel);
        temporaryComponents.add(specificCapacityField);
        temporaryComponents.add(grossTonnageLabel);
        temporaryComponents.add(grossTonnageField);
        temporaryComponents.add(powerOfAdditionalEnginesLabel);
        temporaryComponents.add(powerOfAdditionalEnginesField);
        temporaryComponents.add(lngTankCapacityLabel);
        temporaryComponents.add(lngTankCapacityField);
        temporaryComponents.add(factorOfReliquefyLabel);
        temporaryComponents.add(factorOfReliquefyField);
        temporaryComponents.add(factorOfBoilOffGasLabel);
        temporaryComponents.add(factorOfBoilOffGasField);
        temporaryComponents.add(factorOfDesignPerformanceOfReliquefyLabel);
        temporaryComponents.add(factorOfDesignPerformanceOfReliquefyField);
        temporaryComponents.add(panelCranes);
        temporaryComponents.add(sideloaderFactorLabel);
        temporaryComponents.add(sideloaderFactorField);
        temporaryComponents.add(roroRampFactorLabel);
        temporaryComponents.add(roroRampFactorField);


        JButton solveButton = new JButton(language == Language.Russian ? "Вычислить КЭСС" : "Calculate EEXI");
        solveButton.addActionListener(e -> {
            coefficient.getMainEngines().clear();
            coefficient.getAdditionalEngines().clear();
            setEngineTableData(table);
            if (coefficient.isCargoCranes()) {
                setCranesTableData(tableCranes);
            }
            double eexi = coefficient.calculateEEXI();
            double eexi_required = requiredCoefficient.getRequiredEEXI();
            DecimalFormat df = new DecimalFormat("#.###");
            String eexi_str = df.format(eexi);
            String eexi_required_str = df.format(eexi_required);
            String stock = df.format((eexi_required - eexi) / eexi * 100);
            JOptionPane.showMessageDialog(null, (language == Language.Russian ? "Достигнутый КЭСС: " + eexi_str + " грамм CO2 / тонн * мили\n" : "Attained EEXI: " + eexi_str + " gramm CO2 / tonn * mile\n") +
                    (language == Language.Russian ? "Требуемый КЭСС: " + eexi_required_str + " грамм CO2 / тонн * мили\n" : "Required EEXI: " + eexi_required_str + " gramm CO2 / tonn * mile\n") +
                    (language == Language.Russian ? "Запас: " + stock + "%" : "Stock: " + stock + "%"));
        });

        solveButton.setBounds(10, 750, 150, 20);

        panelInput.add(solveButton);

        JCheckBox calculationOfShipPowerPlantLoads = new JCheckBox(language == Language.Russian ? "Расчёт нагрузок судовой электростанции" : "Auxiliary load power calculation");
        calculationOfShipPowerPlantLoads.addItemListener(e -> {
            coefficient.setCalculationOfShipPowerPlantLoads(e.getStateChange() == ItemEvent.SELECTED);
        });
        calculationOfShipPowerPlantLoads.setBounds(10, 680, 300, 15);

        JCheckBox propellerShaftPowerLimitation = new JCheckBox(language == Language.Russian ? "Ограничение мощности на гребном валу" : "Propulsion power limitation");
        propellerShaftPowerLimitation.addItemListener(e -> {
            coefficient.setPropellerShaftPowerLimitation(e.getStateChange() == ItemEvent.SELECTED);
        });
        propellerShaftPowerLimitation.setBounds(10, 700, 300, 15);

        JCheckBox dieselElectricPropulsionPowerPlant = new JCheckBox(language == Language.Russian ? "Дизель-электрическая пропульсивная ЭУ" : "Diesel-electric propulsion");
        dieselElectricPropulsionPowerPlant.addItemListener(e -> {
            coefficient.setDieselElectricPropulsionPowerPlant(e.getStateChange() == ItemEvent.SELECTED);
        });
        dieselElectricPropulsionPowerPlant.setBounds(10, 720, 300, 15);

        panelInput.add(calculationOfShipPowerPlantLoads);
        panelInput.add(propellerShaftPowerLimitation);
        panelInput.add(dieselElectricPropulsionPowerPlant);
    }

    private void changeFieldsVisibleByShipTypeAndCorrectionFactor(CorrectionFactorEnglish
                                                                          correctionFactorEnglish, boolean show) {
        ShipTypeEnglish shipTypeEnglish = coefficient.getShipTypeEnglish();

        switch (correctionFactorEnglish) {
            case MeetsToTheGeneralIACSRules -> {
                if (shipTypeEnglish == ShipTypeEnglish.BulkCarrier) {
                    volumetricDisplacementLabel.setVisible(show);
                    volumetricDisplacementField.setVisible(show);
                }
            }

            case ChemicalTanker -> {
                specificCapacityLabel.setVisible(show);
                specificCapacityField.setVisible(show);
            }

            case Reliquefaction -> {
                if (shipTypeEnglish == ShipTypeEnglish.GasCarrierLNG) {
                    lngTankCapacityLabel.setVisible(show);
                    lngTankCapacityField.setVisible(show);
                    factorOfReliquefyLabel.setVisible(show);
                    factorOfReliquefyField.setVisible(show);
                    factorOfBoilOffGasLabel.setVisible(show);
                    factorOfBoilOffGasField.setVisible(show);
                    factorOfDesignPerformanceOfReliquefyLabel.setVisible(show);
                    factorOfDesignPerformanceOfReliquefyField.setVisible(show);
                }
            }

            case CargoCranes -> {
                panelCranes.setVisible(show);
                coefficient.setCargoCranes(show);
            }

            case SideLoaders -> {
                sideloaderFactorLabel.setVisible(show);
                sideloaderFactorField.setVisible(show);
                coefficient.setSideRamp(show);
            }

            case RoRoRamp -> {
                roroRampFactorLabel.setVisible(show);
                roroRampFactorField.setVisible(show);
                coefficient.setCargoRamp(show);
            }
        }
    }

    private void setCranesTableData(JTable table) {
        int count1 = Integer.parseInt(table.getValueAt(0, 1) != null ? (String) table.getValueAt(0, 1) : "0");
        int count2 = Integer.parseInt(table.getValueAt(1, 1) != null ? (String) table.getValueAt(1, 1) : "0");
        int count3 = Integer.parseInt(table.getValueAt(2, 1) != null ? (String) table.getValueAt(2, 1) : "0");
        double SWL1 = Double.parseDouble(table.getValueAt(0, 2) != null ? (String) table.getValueAt(0, 2) : "0");
        double SWL2 = Double.parseDouble(table.getValueAt(1, 2) != null ? (String) table.getValueAt(1, 2) : "0");
        double SWL3 = Double.parseDouble(table.getValueAt(2, 2) != null ? (String) table.getValueAt(2, 2) : "0");
        double reach1 = Double.parseDouble(table.getValueAt(0, 3) != null ? (String) table.getValueAt(0, 3) : "0");
        double reach2 = Double.parseDouble(table.getValueAt(1, 3) != null ? (String) table.getValueAt(1, 3) : "0");
        double reach3 = Double.parseDouble(table.getValueAt(2, 3) != null ? (String) table.getValueAt(2, 3) : "0");

        coefficient.setCargoCrane1Count(count1);
        coefficient.setCargoCrane2Count(count2);
        coefficient.setCargoCrane3Count(count3);

        coefficient.setSWL1(SWL1);
        coefficient.setSWL2(SWL2);
        coefficient.setSWL3(SWL3);

        coefficient.setReach1(reach1);
        coefficient.setReach2(reach2);
        coefficient.setReach3(reach3);
    }

    private void setEngineTableData(JTable table) {
        int maxIndex = table.getRowCount();
        int c = 0;
        while (c < maxIndex) {
            int engCount = Integer.parseInt((String) table.getValueAt(c, 1));
            for (int i = 0; i < engCount; i++) {
                String engType = (String) table.getValueAt(c, 0);
                setEngineData(table, c, engType);
                c++;
            }
        }
    }

    private void setEngineData(JTable table, int c, String engType) {
        double mcr_i = Double.parseDouble((String) table.getValueAt(c, 2));
        int fuelTypeCount = Integer.parseInt((String) table.getValueAt(c, 3));
        double p_i = Double.parseDouble((String) table.getValueAt(c, 4));
        String mainFuelTypeStr = (String) table.getValueAt(c, 5);
        String pilotFuelTypeStr = (String) table.getValueAt(c, 6);
        double sfcMain = Double.parseDouble((String) table.getValueAt(c, 7));
        double sfcPilot = Double.parseDouble((String) table.getValueAt(c, 8));
        double efficiencyOfElectricGenerator = (table.getValueAt(c, 9) != null && !table.getValueAt(c, 9).equals("---------")) ? Double.parseDouble((String) table.getValueAt(c, 9)) : 0;

        FuelTypeEnglish mainFuelType;
        FuelTypeEnglish pilotFuelType;

        Engine engine = new Engine(fuelTypeCount, mcr_i, p_i, efficiencyOfElectricGenerator);

        if (language == Language.Russian) {
            FuelTypeRussian mainTypeRussian = FuelTypeRussian.getByTitle(mainFuelTypeStr);
            FuelTypeRussian pilotTypeRussian = FuelTypeRussian.getByTitle(pilotFuelTypeStr);
            mainFuelType = FuelTypeEnglish.valueOf(mainTypeRussian.name());
            if (pilotTypeRussian != null) {
                pilotFuelType = FuelTypeEnglish.valueOf(pilotTypeRussian.name());
                engine.addFuelType(pilotFuelType, sfcPilot);
            }

        } else {
            mainFuelType = FuelTypeEnglish.getByTitle(mainFuelTypeStr);
            pilotFuelType = FuelTypeEnglish.getByTitle(pilotFuelTypeStr);
            if (pilotFuelType != null) {
                engine.addFuelType(pilotFuelType, sfcPilot);
            }
        }

        engine.addFuelType(mainFuelType, sfcMain);

        if (engType.equals("Главный") || engType.equals("Main")) {
            coefficient.addMainEngine(engine);
        } else {
            coefficient.addAdditionalEngine(engine);
        }
    }

    private void updatePanelEngine(JPanel panel, JTable table) {
        panel.setVisible(false);
        panel.setBounds(520, 150, 1000, Math.min(getTableHeight(table), 400));
        panel.setVisible(true);
    }

    private int getTableHeight(JTable table) {
        return (table.getModel().getRowCount()) * table.getRowHeight() + table.getTableHeader().getPreferredSize().height + 3;
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
    }

    private void itemShipTypeChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED) {
            var selectedItem = event.getItem();
            ShipTypeEnglish shipTypeEnglish;
            if (language == Language.Russian) {
                ShipTypeRussian typeRussian = ShipTypeRussian.getByTitle(selectedItem.toString());
                shipTypeEnglish = ShipTypeEnglish.valueOf(typeRussian.name());
            } else {
                shipTypeEnglish = ShipTypeEnglish.getByTitle(selectedItem.toString());
            }
            coefficient.setShipTypeEnglish(shipTypeEnglish);

            List<String> availableFactors = coefficient.getShipTypeAvailableCorrectionFactors(language);
            correctionFactorsCheckBoxes.forEach(checkbox -> {
                checkbox.setEnabled(availableFactors.contains(checkbox.getText()));
                checkbox.setSelected(false);
            });

            changeFieldsVisibleByShipType(shipTypeEnglish);
        }
    }

    private void changeFieldsVisibleByShipType(ShipTypeEnglish shipTypeEnglish) {
        temporaryComponents.forEach(component -> component.setVisible(false));
        deadWeightLabel.setVisible(true);
        deadWeightField.setVisible(true);
        switch (shipTypeEnglish) {
            case ContainerCarrier, CombiShip -> {
                lengthBetweenPerpendicularsLabel.setVisible(true);
                lengthBetweenPerpendicularsField.setVisible(true);
                breadthLabel.setVisible(true);
                breadthField.setVisible(true);
                draughtLabel.setVisible(true);
                draughtField.setVisible(true);
            }

            case RoRoCargoCarrier -> {
                lengthBetweenPerpendicularsLabel.setVisible(true);
                lengthBetweenPerpendicularsField.setVisible(true);
                breadthLabel.setVisible(true);
                breadthField.setVisible(true);
                draughtLabel.setVisible(true);
                draughtField.setVisible(true);
                specificCapacityLabel.setVisible(true);
                specificCapacityField.setVisible(true);
            }

            case RoRoPassengerCarrier -> {
                lengthBetweenPerpendicularsLabel.setVisible(true);
                lengthBetweenPerpendicularsField.setVisible(true);
                breadthLabel.setVisible(true);
                breadthField.setVisible(true);
                draughtLabel.setVisible(true);
                draughtField.setVisible(true);
                volumetricDisplacementLabel.setVisible(true);
                volumetricDisplacementField.setVisible(true);
                grossTonnageLabel.setVisible(true);
                grossTonnageField.setVisible(true);
                powerOfAdditionalEnginesLabel.setVisible(true);
                powerOfAdditionalEnginesField.setVisible(true);
            }

            case GasCarrierLNG -> {
                specificCapacityLabel.setVisible(true);
                specificCapacityField.setVisible(true);
            }

            case CruisePassengerShip -> {
                deadWeightLabel.setVisible(false);
                deadWeightField.setVisible(false);
                grossTonnageLabel.setVisible(true);
                grossTonnageField.setVisible(true);
                powerOfAdditionalEnginesLabel.setVisible(true);
                powerOfAdditionalEnginesField.setVisible(true);
            }

            case VSSRoRoPassengerCarrier -> {
                deadWeightLabel.setVisible(false);
                deadWeightField.setVisible(false);
                grossTonnageLabel.setVisible(true);
                grossTonnageField.setVisible(true);
            }

            case RoRoCarCarrier -> {
                grossTonnageLabel.setVisible(true);
                grossTonnageField.setVisible(true);
            }
        }
    }

    private void itemIceClassChanged(ItemEvent event) {
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
