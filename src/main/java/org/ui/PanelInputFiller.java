package org.ui;

import org.coefficients.EexiCoefficient;
import org.coefficients.EexiRequiredCoefficient;
import org.models.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private List<JCheckBox> correctionFactorsCheckBoxes;
    private List<Cell> unEditableCells;
    private List<Component> temporaryComponents;
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

    public PanelInputFiller(EexiCoefficient coefficient, Language language, EexiRequiredCoefficient requiredCoefficient) {
        this.coefficient = coefficient;
        this.language = language;
        this.requiredCoefficient = requiredCoefficient;
        this.correctionFactorsCheckBoxes = new ArrayList<>();
        this.unEditableCells = new ArrayList<>();
        coefficient.setShipTypeEnglish(ShipTypeEnglish.BulkCarrier);
        coefficient.setIceClassEnglish(IceClassEnglish.withoutIceClassOrIce1);
        this.temporaryComponents = new ArrayList<>();
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
        System.out.println(coefficient.getShipTypeEnglish());
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

        deadWeightLabel = new JLabel(language == Language.Russian ? "Дедвейт (DWT), т" : "Deadweight (DWT), t");
        deadWeightField = new JTextField();
        deadWeightLabel.setBounds(10, 35, 120, 20);
        deadWeightField.setBounds(130, 35, 90, 20);
        deadWeightField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!deadWeightField.getText().isEmpty() && isNumeric(deadWeightField.getText())) {
                    coefficient.setDWT(Double.parseDouble(deadWeightField.getText()));
                    System.out.println(coefficient.getDWT());
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

        vrefField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!vrefField.getText().isEmpty() && isNumeric(vrefField.getText())) {
                    coefficient.setV_ref(Double.parseDouble(vrefField.getText()));
                    System.out.println(coefficient.getV_ref());
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

        fivseField.setToolTipText(language == Language.Russian ? "Коэффициент особенностей конструкции корпуса (для сохранения значения после ввода нажмите 'Enter')" : "Capacity correction factor for ship specific voluntary structural (to save the value, when finished, press 'Enter')");

        panelInput.add(fivseLabel);
        panelInput.add(fivseField);

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
        model.addColumn(language == Language.Russian ? "<html><b>Количество <br>двигателей</b></html>" : "<html><b>Engine count</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Мощность (MCR<sub>i</sub>) данного двигателя, <br>кВт</b></html>" : "<html><b>Power (MCR<sub>i</sub>), kW</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Количество <br>типов топлива для данного двигателя </b></html>" : "<html><b>Available fuel type count</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Мощность (P<sub>i</sub>) данного <br>двигателя, кВт</b></html>" : "<html><b>Power (P<sub>i</sub>), kW</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Тип топлива <br>основного двигателя</b></html>" : "<html><b>Main engine fuel type</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Тип запального <br>топлива</b></html>" : "<html><b>Pilotfuel type</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Удельный расход <br>основного топлива (SFC), <br>г / кВт * ч</b></html>" : "<html><b>Specific consumption fuel oil (SFC), <br>g / kW * h</b></html>");
        model.addColumn(language == Language.Russian ? "<html><b>Удельный расход <br>запального топлива (SFC<sub>Pilotfuel</sub>), <br>г / кВт * ч</b></html>" : "<html><b>Specific consumption fuel oil (SFC<sub>Pilotfuel</sub>), <br>g / kW * h</b></html>");

        model.addRow(new Object[]{language == Language.Russian ? EngineTypeRussian.Main.getTitle() : EngineTypeEnglish.Main.getTitle()});
        model.addRow(new Object[]{language == Language.Russian ? EngineTypeRussian.Additional.getTitle() : EngineTypeEnglish.Additional.getTitle(), null, "0"});
        addUnEditableCell(1, 2);
        JTable table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);

        List<String> russianFuelShipType = Arrays.stream(FuelTypeRussian.values()).map(FuelTypeRussian::getTitle).toList();
        List<String> englishFuelShipType = Arrays.stream(FuelTypeEnglish.values()).map(FuelTypeEnglish::getTitle).toList();
        JComboBox fuelTypeBox = new JComboBox(language == Language.Russian ? russianFuelShipType.toArray() : englishFuelShipType.toArray());
        JComboBox engineCountBox = new JComboBox(new Object[]{"1", "2"});
        JComboBox fuelCountBox = new JComboBox(new Object[]{"1", "2"});

        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(engineCountBox));
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(fuelCountBox));
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(fuelTypeBox));
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(fuelTypeBox));
        table.getColumnModel().getColumn(8).setPreferredWidth(90);

        table.getTableHeader().setPreferredSize(new Dimension(1000, 130));
        table.setRowHeight(25);
        table.getTableHeader().setResizingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setHorizontalTextPosition(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        panelEngine.setLayout(new BorderLayout());
        panelEngine.setBounds(520, 150, 1000, getTableHeight(table));
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLACK));
        model.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (model.getValueAt(row, column) != null && column != 5 && column != 6 && !isNumeric((String) model.getValueAt(row, column))) {
                    JOptionPane.showMessageDialog(null, language == Language.Russian ? "Недопустимый тип: вводите только числа" : "Unavailable type: please insert only numbers");
                    model.setValueAt(null, row, column);
                } else {
                    if (model.getValueAt(row, column) != null) {
                        if (column == 1) {
                            if (model.getValueAt(row, column).equals("2")) {
                                if (row == model.getRowCount() - 1 || (row < model.getRowCount() - 1 && model.getValueAt(row + 1, 0) != null)) {
                                    String engType = (String) model.getValueAt(row, 0);
                                    if (engType != null && (engType.equals("Вспомогательный") || engType.equals("Additional"))) {
                                        model.insertRow(row + 1, new Object[]{null, "----------------", "0"});
                                        recalculationIncreaseUnEditableCells(row + 1);
                                        addUnEditableCell(row + 1, column);
                                        addUnEditableCell(row + 1, 2);
                                    } else {
                                        model.insertRow(row + 1, new Object[]{null, "----------------"});
                                        recalculationIncreaseUnEditableCells(row + 1);
                                        addUnEditableCell(row + 1, column);
                                    }

                                }
                            } else if (model.getValueAt(row, column).equals("1")) {
                                String engType = (String) model.getValueAt(row, 0);
                                if (engType != null && row < model.getRowCount() - 1) {
                                    String engTypeSus = (String) model.getValueAt(row + 1, 0);
                                    if (engTypeSus == null) {
                                        model.removeRow(row + 1);
                                        removeUnEditableCell(row + 1, column);
                                        recalculationDecreaseUnEditableCells(row + 1);
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

        panelEngine.add(new JScrollPane(table));
        JButton solveButton = new JButton(language == Language.Russian ? "Вычислить КЭСС" : "Calculate EEXI");
        solveButton.addActionListener(e -> {
            coefficient.getMainEngines().clear();
            coefficient.getAdditionalEngines().clear();
            setEngineTableData(table);
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

        solveButton.setBounds(10, 700, 150, 20);
        panelInput.add(panelEngine);
        panelInput.add(solveButton);

        JLabel shaftGenCountlabel = new JLabel(language == Language.Russian ? "Количество валогенераторов" : "Shaft generators quantity");
        JTextField shaftGenCountField = new JTextField();

        if (language == Language.Russian) {
            shaftGenCountlabel.setBounds(730, 400, 175, 20);
        } else {
            shaftGenCountlabel.setBounds(760, 400, 175, 20);
        }


        shaftGenCountField.setBounds(910, 400, 90, 20);

        shaftGenCountField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!shaftGenCountField.getText().isEmpty() && isNumeric(shaftGenCountField.getText())) {
                    coefficient.setShaftGenCount(Double.parseDouble(shaftGenCountField.getText()));
                    shaftGenCountField.setBackground(Color.WHITE);
                } else {
                    shaftGenCountField.setBackground(Color.RED);
                }
            }
        });

        shaftGenCountField.setToolTipText(language == Language.Russian ? "Количество валогенераторов (для сохранения значения после ввода нажмите 'Enter')" : "Shaft generators quantity (to save the value, when finished, press 'Enter')");

        panelInput.add(shaftGenCountlabel);
        panelInput.add(shaftGenCountField);

        JLabel shaftGenMcrlabel = new JLabel(language == Language.Russian ? "<html>Мощность валогенераторов (MCR<sub>PTO</sub>), кВт</html>" : "<html>Power of shaft generators (MCR<sub>PTO</sub>), kW</html>");
        JTextField shaftGenMcrField = new JTextField();

        if (language == Language.Russian) {
            shaftGenMcrlabel.setBounds(1070, 400, 290, 20);
        } else {
            shaftGenMcrlabel.setBounds(1090, 400, 290, 20);
        }


        shaftGenMcrField.setBounds(1335, 400, 90, 20);

        shaftGenMcrField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!shaftGenMcrField.getText().isEmpty() && isNumeric(shaftGenMcrField.getText())) {
                    coefficient.setMCR_PTO(Double.parseDouble(shaftGenMcrField.getText()));
                    shaftGenMcrField.setBackground(Color.WHITE);
                } else {
                    shaftGenMcrField.setBackground(Color.RED);
                }
            }
        });

        shaftGenMcrField.setToolTipText(language == Language.Russian ? "Мощность валогенераторов (для сохранения значения после ввода нажмите 'Enter')" : "Power of shaft generator (to save the value, when finished, press 'Enter')");

        panelInput.add(shaftGenMcrlabel);
        panelInput.add(shaftGenMcrField);

        JLabel elPropEngCountlabel = new JLabel(language == Language.Russian ? "Количество гребных эл. двигателей" : "Electric propeller engine quantity");
        JTextField elPropEngCountField = new JTextField();

        if (language == Language.Russian) {
            elPropEngCountlabel.setBounds(690, 425, 225, 20);
        } else {
            elPropEngCountlabel.setBounds(715, 425, 190, 20);
        }


        elPropEngCountField.setBounds(910, 425, 90, 20);

        elPropEngCountField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!elPropEngCountField.getText().isEmpty() && isNumeric(elPropEngCountField.getText())) {
                    coefficient.setElPropEngCount(Double.parseDouble(elPropEngCountField.getText()));
                    elPropEngCountField.setBackground(Color.WHITE);
                } else {
                    elPropEngCountField.setBackground(Color.RED);
                }
            }
        });

        elPropEngCountField.setToolTipText(language == Language.Russian ? "Количество гребных эл. двигателей (для сохранения значения после ввода нажмите 'Enter')" : "Electric propeller engine quantity (to save the value, when finished, press 'Enter')");

        panelInput.add(elPropEngCountlabel);
        panelInput.add(elPropEngCountField);

        JLabel elPropEngMcrlabel = new JLabel(language == Language.Russian ? "<html>Мощность гребных эл. двигателей (MCR<sub>PTI</sub>), кВт</html>" : "<html>Power of electric propeller engines (MCR<sub>PTI</sub>), kW</html>");
        JTextField elPropEngMcrField = new JTextField();

        if (language == Language.Russian) {
            elPropEngMcrlabel.setBounds(1035, 425, 290, 20);
        } else {
            elPropEngMcrlabel.setBounds(1042, 425, 290, 20);
        }


        elPropEngMcrField.setBounds(1335, 425, 90, 20);

        elPropEngMcrField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!elPropEngMcrField.getText().isEmpty() && isNumeric(elPropEngMcrField.getText())) {
                    coefficient.setMCR_PTI(Double.parseDouble(elPropEngMcrField.getText()));
                    elPropEngMcrField.setBackground(Color.WHITE);
                } else {
                    elPropEngMcrField.setBackground(Color.RED);
                }
            }
        });

        elPropEngMcrField.setToolTipText(language == Language.Russian ? "Мощность гребных эл. двигателей (для сохранения значения после ввода нажмите 'Enter')" : "Power of electric propeller engines (to save the value, when finished, press 'Enter')");

        panelInput.add(elPropEngMcrlabel);
        panelInput.add(elPropEngMcrField);


        temporaryComponents.add(lengthBetweenPerpendicularsLabel);
        temporaryComponents.add(lengthBetweenPerpendicularsField);
        temporaryComponents.add(breadthLabel);
        temporaryComponents.add(breadthField);
        temporaryComponents.add(draughtLabel);
        temporaryComponents.add(draughtField);
        temporaryComponents.add(deadWeightLabel);
        temporaryComponents.add(deadWeightField);
//        temporaryComponents.add(volumetricDisplacementLabel);
//        temporaryComponents.add(volumetricDisplacementField);
//        temporaryComponents.add(specificCapacityLabel);
//        temporaryComponents.add(specificCapacityField);
    }

    private void changeFieldsVisibleByShipTypeAndCorrectionFactor(CorrectionFactorEnglish correctionFactorEnglish, boolean show) {
        ShipTypeEnglish shipTypeEnglish = coefficient.getShipTypeEnglish();

        switch (correctionFactorEnglish) {
            case MeetsToTheGeneralIACSRules -> {
                if (shipTypeEnglish == ShipTypeEnglish.BulkCarrier) {
                    volumetricDisplacementLabel.setVisible(show);
                    volumetricDisplacementField.setVisible(show);
                }
            }

            case ChemicalTanker -> {
                if (shipTypeEnglish == ShipTypeEnglish.Tanker) {
                    specificCapacityLabel.setVisible(show);
                    specificCapacityField.setVisible(show);
                }
            }
        }
    }

    private void setEngineTableData(JTable table) {
        int maxIndex = table.getRowCount();
        System.out.println("maxIndex: " + maxIndex);
        int c = 0;
        while (c < maxIndex) {
            String engType = (String) table.getValueAt(c, 0);
            setEngineData(table, c, engType);

            int engCount = Integer.parseInt((String) table.getValueAt(c, 1));
            System.out.println("engCount: " + engCount);
            if (engCount == 2) {
                c++;
                setEngineData(table, c, engType);
            }

            c++;
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

        FuelTypeEnglish mainFuelType;
        FuelTypeEnglish pilotFuelType;

        Engine engine = new Engine(fuelTypeCount, mcr_i, p_i);
        System.out.println("mcr_i: " + mcr_i);
        System.out.println("fuelTypeCount: " + fuelTypeCount);
        System.out.println("p_i: " + p_i);
        System.out.println("mainFuelTypeStr: " + mainFuelTypeStr);
        System.out.println("pilotFuelTypeStr: " + pilotFuelTypeStr);
        System.out.println("sfc_main: " + sfcMain);
        System.out.println("sfc_pilot: " + sfcPilot);

        if (language == Language.Russian) {
            FuelTypeRussian mainTypeRussian = FuelTypeRussian.getByTitle(mainFuelTypeStr);
            FuelTypeRussian pilotTypeRussian = FuelTypeRussian.getByTitle(pilotFuelTypeStr);
            mainFuelType = FuelTypeEnglish.valueOf(mainTypeRussian.name());
            if (pilotTypeRussian != null) {
                pilotFuelType = FuelTypeEnglish.valueOf(pilotTypeRussian.name());
                System.out.println("pilotFuelType: " + pilotFuelType);
                engine.addFuelType(pilotFuelType, sfcPilot);
            }

        } else {
            mainFuelType = FuelTypeEnglish.getByTitle(mainFuelTypeStr);
            pilotFuelType = FuelTypeEnglish.getByTitle(pilotFuelTypeStr);
            System.out.println("pilotFuelType: " + pilotFuelType);
            if (pilotFuelType != null) {
                engine.addFuelType(pilotFuelType, sfcPilot);
            }
        }

        System.out.println("mainFuelType: " + mainFuelType);


        engine.addFuelType(mainFuelType, sfcMain);

        if (engType.equals("Главный") || engType.equals("Main")) {
            coefficient.addMainEngine(engine);
        } else {
            coefficient.addAdditionalEngine(engine);
        }
    }

    private void updatePanelEngine(JPanel panel, JTable table) {
        panel.setVisible(false);
        panel.setBounds(520, 150, 1000, getTableHeight(table));
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
                specificCapacityLabel.setVisible(true);
                specificCapacityField.setVisible(true);
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
