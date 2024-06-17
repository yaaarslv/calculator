package org.ui;

import org.coefficients.EexiCoefficient;
import org.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class PanelResultFiller {
    private final EexiCoefficient coefficient;
    private final Language language;

    public PanelResultFiller(EexiCoefficient coefficient, Language language) {
        this.coefficient = coefficient;
        this.language = language;
    }

    public void addContentToPanelResult(JPanel panelResult) {
        panelResult.setLayout(new BorderLayout());
        panelResult.setBounds(0, 0, 300, 300);

        DefaultTableModel model1 = new DefaultTableModel();

        model1.addColumn(language == Language.Russian ? "Тип двигателя" : "Engine type");
        model1.addColumn(language == Language.Russian ? "Количество двигателей" : "Engine count");
        model1.addColumn(language == Language.Russian ? "Мощность (MCR), кВт" : "Power (MCR), kW");
        model1.addColumn(language == Language.Russian ? "Количество возможных типов топлива" : "Available fuel type count");
        model1.addColumn(language == Language.Russian ? "Мощность (P), кВт" : "Power (P}), kW");
        model1.addColumn(language == Language.Russian ? "Тип топлива главного двигателя" : "Main engine fuel type");
        model1.addColumn(language == Language.Russian ? "Тип запального топлива" : "Pilotfuel type");
        model1.addColumn(language == Language.Russian ? "Удельный расход топлива (SFC), г / кВт * ч" : "Specific consumption fuel oil (SFC), g / kW * h");
        var q = new Vector<>();
        q.add("rffrf");
        model1.addRow(q);
        JTable table = new JTable(model1);

        List<String> russianFuelShipType = Arrays.stream(FuelTypeRussian.values()).map(FuelTypeRussian::getTitle).toList();
        List<String> englishFuelShipType = Arrays.stream(FuelTypeEnglish.values()).map(FuelTypeEnglish::getTitle).toList();
        JComboBox fuelTypeBox = new JComboBox(language == Language.Russian ? russianFuelShipType.toArray() : englishFuelShipType.toArray());

        table.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(fuelTypeBox));
        table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(fuelTypeBox));

        table.getColumnModel().getColumns().asIterator().forEachRemaining(column -> column.setWidth(150));


        panelResult.add(new JScrollPane(table));
    }
}
