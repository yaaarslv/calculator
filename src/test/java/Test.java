import javax.swing.*; // подключаем все средства java Swing
import java.awt.*;

public class Test {
    public static void main(String[] args) {

        JFrame frame = new JFrame("");
        // добавляем панель
        JPanel buttonsPanel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null); // окно в центре экрана
        JButton start = new JButton("Старт");
        JButton stop = new JButton("Стоп");
        // добавляем кнопки на панель
        buttonsPanel.add(start);
        buttonsPanel.add(stop);
        // размещаем панель на Frame (верхняя часть)
        frame.getContentPane().add(BorderLayout.NORTH, buttonsPanel);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
    }
}