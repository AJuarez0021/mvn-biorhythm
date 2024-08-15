package com.work.biorhythm;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author linux
 */
public class Main extends JFrame {

    public Main(String title, long days) throws HeadlessException {
        super(title);
        createPanel(days);
    }

    public static void main(String[] args) {
        int year = getInt("Introduce tu año de nacimiento (AAAA):");
        int month = getInt("Introduce tu mes de nacimiento (MM):");
        int day = getInt("Introduce tu día de nacimiento (DD):");

        if (year <= 1900 || year == -1) {
            showMessage("El año es incorrecto");
            return;
        }

        if (!(month >= 1 && month <= 12) || month == -1) {
            showMessage("El mes es incorrecto");
            return;
        }

        if (!(day >= 1 && day <= 31) || day == -1) {
            showMessage("El dia es incorrecto");
            return;
        }

        long days = Biorhythm.getDays(year, month, day);
        if (days == -1) {
            showMessage("La fecha es invalida");
            return;
        }

        System.out.println("Dias Vividos: " + days);

        javax.swing.SwingUtilities.invokeLater(() -> {
            Main example = new Main("Gráfica de Biorritmo", days);
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }

    private static int getInt(String message) {
        try {
            return Integer.parseInt(JOptionPane.showInputDialog(message));
        } catch (NumberFormatException ex) {
            System.err.println("Msg: " + ex.getMessage());
            return -1;
        }
    }

    private static void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    private void createPanel(long days) {
        DefaultCategoryDataset dataset = createDataset(days);
        JFreeChart chart = ChartFactory.createLineChart(
                "Biorritmo", // Título del gráfico
                "Días", // Etiqueta del eje X
                "Valor del Biorritmo", // Etiqueta del eje Y
                dataset, // Dataset
                PlotOrientation.VERTICAL,
                true, // Incluir leyenda
                true,
                false
        );
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private DefaultCategoryDataset createDataset(long days) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        long init = days - 30;
        for (long d = init; d <= days; d++) {
            double biorhythmPhysical = Biorhythm.getPhysical(d);
            double biorhythmEmotional = Biorhythm.getEmotional(d);
            double biorhythmIntellectual = Biorhythm.getIntellectual(d);

            dataset.addValue(biorhythmPhysical, "Físico (23 días)", Long.toString(d));
            dataset.addValue(biorhythmEmotional, "Emocional (28 días)", Long.toString(d));
            dataset.addValue(biorhythmIntellectual, "Intelectual (33 días)", Long.toString(d));
        }
        return dataset;
    }

}
