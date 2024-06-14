package org.ui;

import org.coefficients.EexiCoefficient;
import org.models.EngineTypeEnglish;
import org.models.FuelTypeEnglish;
import org.models.MyTableModel;
import org.models.TableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.List;

public class PanelResultFiller {
    private final EexiCoefficient coefficient;
    private final Language language;

    public PanelResultFiller(EexiCoefficient coefficient, Language language) {
        this.coefficient = coefficient;
        this.language = language;
    }

    public void addContentToPanelResult(JPanel panelResult) {
        TableModel model1 = new TableModel(EngineTypeEnglish.Main, 1, 15000, 1, 8250, FuelTypeEnglish.diesel, null, 166.5);
        TableModel model2 = new TableModel(EngineTypeEnglish.Additional, 1, 15000, 1, 8250, FuelTypeEnglish.diesel, null, 166.5);
        List<TableModel> modelList = new ArrayList<>();
        modelList.add(model1);
        modelList.add(model2);
        JTable table = new JTable(new MyTableModel(modelList));

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);

        panelResult.add(table);
    }
}
