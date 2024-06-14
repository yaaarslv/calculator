package org.models;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MyTableModel extends AbstractTableModel {

    private List<TableModel> data;

    public MyTableModel(List<TableModel> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TableModel rowData = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> rowData.getEngineTypeEnglish();
            case 1 -> rowData.getEngineCount();
            case 2 -> rowData.getMcr();
            case 3 -> rowData.getFuelTypesCount();
            case 4 -> rowData.getP();
            case 5 -> rowData.getFuelTypeMainEngine();
            case 6 -> rowData.getFuelTypeAdditionalEngine();
            case 7 -> rowData.getSfc();
            default -> null;
        };
    }
}
