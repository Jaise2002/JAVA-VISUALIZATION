package com.example;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class ScatterPlot extends JFrame {

    public ScatterPlot(String title) {
        super(title);

        // Load the data
        String csvFile = "output.csv";
        double[][] yearData = loadData(csvFile, "Year", "Price");
        double[][] kmData = loadData(csvFile, "Kilometers_Driven", "Price");
        double[][] engineData = loadData(csvFile, "Engine CC", "Price");
        double[][] powerData = loadData(csvFile, "Power", "Price");
        double[][] seatsData = loadData(csvFile, "Seats", "Price");
        double[][] mileageData = loadData(csvFile, "Mileage Km/L", "Price");

        // Create the charts
        JFreeChart yearChart = createChart(yearData, "Price by Year", "Year", "Price");
        JFreeChart kmChart = createChart(kmData, "Price by Kilometers Driven", "Kilometers Driven", "Price");
        JFreeChart engineChart = createChart(engineData, "Price by Engine CC", "Engine CC", "Price");
        JFreeChart powerChart = createChart(powerData, "Price by Power", "Power", "Price");
        JFreeChart seatsChart = createChart(seatsData, "Price by Seats", "Seats", "Price");
        JFreeChart mileageChart = createChart(mileageData, "Price by Mileage", "Mileage Km/L", "Price");

        // Create a panel and add charts
        JPanel panel = new JPanel(new GridLayout(2, 3));
        panel.add(new ChartPanel(yearChart));
        panel.add(new ChartPanel(kmChart));
        panel.add(new ChartPanel(engineChart));
        panel.add(new ChartPanel(powerChart));
        panel.add(new ChartPanel(seatsChart));
        panel.add(new ChartPanel(mileageChart));

        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    
    private static double[][] loadData(String csvFile, String xField, String yField) {
        try (Reader reader = new FileReader(csvFile)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
    
            // Convert the iterable to a list to get the size
            List<CSVRecord> recordList = new ArrayList<>();
            for (CSVRecord record : records) {
                recordList.add(record);
            }
    
            int recordCount = recordList.size();
            double[][] data = new double[2][recordCount];
    
            for (int i = 0; i < recordCount; i++) {
                CSVRecord record = recordList.get(i);
                data[0][i] = Double.parseDouble(record.get(xField));
                data[1][i] = Double.parseDouble(record.get(yField));
            }
    
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
    

    private static JFreeChart createChart(double[][] data, String title, String xAxisLabel, String yAxisLabel) {
        XYSeries series = new XYSeries("Data");
        for (int i = 0; i < data[0].length; i++) {
            series.add(data[0][i], data[1][i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createScatterPlot(title, xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
        XYPlot scatterPlot = (XYPlot) chart.getPlot();
        XYItemRenderer scatterRenderer = scatterPlot.getRenderer();
            scatterRenderer.setSeriesPaint(0,Color.GREEN );
      
        return chart;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScatterPlot example = new ScatterPlot("Scatter Plot");
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setVisible(true);
        });
    }
}
