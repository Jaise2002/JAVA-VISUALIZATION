package com.example;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class LineChart extends JFrame {

    public LineChart(String title) {
        super(title);

        // Load the data and count occurrences
        Map<String, Integer> yearData = loadDataAndCount("output.csv", "Year");
        Map<String, Integer> Location = loadDataAndCount("output.csv", "Location");
        Map<String, Integer> Manufacturer= loadDataAndCount("output.csv", "Manufacturer");
      

        // Create the charts
        JFreeChart yearChart = createChart(yearData, "Count by Year");
        JFreeChart LocationChart = createChart(Location, "count by Location");
        JFreeChart ManufacturerChart = createChart(Manufacturer, "Count by Manufacturer");
        // Create a panel and add charts
        JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.add(new ChartPanel(yearChart));
        panel.add(new ChartPanel(LocationChart));
        panel.add(new ChartPanel(ManufacturerChart));
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

    private static JFreeChart createChart(Map<String, Integer> data, String title) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Count", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createLineChart(
                title,       // chart title
                "Category",  // domain axis label
                "Count",     // range axis label
                dataset,PlotOrientation.VERTICAL,true, true, false
                 // data
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);

        return chart;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LineChart example = new LineChart("Line Chart Example");
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setVisible(true);
        });
    }
}

