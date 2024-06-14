import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Merge Cells Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel(5, 5);
        JTable table = new JTable(model);

        // Объединение ячеек
        mergeCells(table, 4, 3, 4, 4);
        mergeCells(table, 2, 1, 3, 4);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    // Метод для объединения ячеек
    public static void mergeCells(JTable table, int startRow, int startCol, int endRow, int endCol) {
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if (i == startRow && j == startCol) {
                    continue;
                }
                table.setValueAt("", i, j);
                table.getCellRect(i, j, true);
            }
        }
        Rectangle cellRect = table.getCellRect(startRow, startCol, true);
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if (i == startRow && j == startCol) {
                    continue;
                }
                cellRect.add(table.getCellRect(i, j, true));
            }
        }
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        renderer.setVerticalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, renderer);
    }
}
