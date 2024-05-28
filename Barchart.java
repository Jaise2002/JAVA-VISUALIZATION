package com.example;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class Barchart extends JFrame {

    public Barchart(String title) {
        super(title);

        // Load the data and count occurrences
        Map<String, Integer> Manufacturer = loadDataAndCount("output.csv", "Manufacturer");
        Map<String, Integer> Year = loadDataAndCount("output.csv", "Year");
        Map<String, Integer> Location = loadDataAndCount("output.csv", "Location");
        Map<String, Integer> Transmission= loadDataAndCount("output.csv", "Transmission");
        Map<String, Integer> Fuel_Type = loadDataAndCount("output.csv", "Fuel_Type");
        Map<String, Integer> seatsData = loadDataAndCount("output.csv", "Seats");

        // Create the charts
        JFreeChart yearChart = createChart(Year, "Count by Year", "Year", "Car Count");
        JFreeChart TransmissionChart = createChart(Transmission, "Count by Transmission", "Transmission", "Car Count");
        JFreeChart LocationChart = createChart(Location, "Count by Location", "Location", "Car Count");
        JFreeChart facturerChart = createChart(Manufacturer, "Count by Manufacturer", "Manufacturer", "Car Count");
        JFreeChart Fuel_TypeChart = createChart(Fuel_Type, "Count by Fuel Type", "Fuel Type", "Car Count");
        JFreeChart seatsChart = createChart(seatsData, "Count by Seats", "Seats", "Car Count");
        
        // Create a panel and add charts
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new ChartPanel(yearChart));
        panel.add(new ChartPanel(Fuel_TypeChart));
        panel.add(new ChartPanel(seatsChart));
        panel.add(new ChartPanel(TransmissionChart));
        panel.add(new ChartPanel(LocationChart));
        panel.add(new ChartPanel(facturerChart));

        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private static Map<String, Integer> loadDataAndCount(String csvFile, String field) {
        Map<String, Integer> dataCount = new HashMap<>();
        try (Reader reader = new FileReader(csvFile)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                String key = record.get(field);
                dataCount.put(key, dataCount.getOrDefault(key, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataCount;
    }

    private static JFreeChart createChart(Map<String, Integer> data, String title, String xAxisLabel, String yAxisLabel) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Count", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                title,           // chart title
                xAxisLabel,      // domain axis label
                yAxisLabel,      // range axis label
                dataset,         // data
                PlotOrientation.VERTICAL, // orientation
                true,            // include legend
                true,            // tooltips
                false            // URLs?
        );

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
         BarRenderer renderer = (BarRenderer) plot.getRenderer();
        Color[] colors = new Color[] {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.CYAN };
        for (int i = 0; i < dataset.getColumnCount(); i++) {
            renderer.setSeriesPaint(0, colors[i % colors.length]);
            renderer.setBarPainter(new StandardBarPainter()); // Ensure solid colors without gradient
        }
        return barChart;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Barchart example = new Barchart("Bar Chart");
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setVisible(true);
        });
    }
}
