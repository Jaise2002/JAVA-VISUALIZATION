package com.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class PieChart extends JFrame {

    public PieChart(String title) {
        super(title);

        // Load the data and count occurrences
        Map<String, Integer> Manufacturer = loadDataAndCount("output.csv", "Manufacturer");
        Map<String, Integer> Fuel_Type = loadDataAndCount("output.csv", "Fuel_Type");
        Map<String, Integer> Owner_Type = loadDataAndCount("output.csv", "Owner_Type");
        Map<String, Integer> seatsData = loadDataAndCount("output.csv", "Seats");

        // Create the charts
        JFreeChart Manufacturer1 = createChart(Manufacturer, "Year");
        JFreeChart Fuel_TypeChart = createChart(Fuel_Type, "Fuel_Type");
        JFreeChart Owner_TypeChart = createChart(Owner_Type, "Owner_Type");
        JFreeChart seatsChart = createChart(seatsData, "Seats");
        
        File chart3=new File("chart1.png");
        try{
          ChartUtilities.saveChartAsPNG(chart3,seatsChart,620, 480);
        }catch(IOException e){

        }
        // Create a panel and add charts
        JPanel panel = new JPanel(new GridLayout(2, 3));
        panel.add(new ChartPanel(Manufacturer1));
        panel.add(new ChartPanel(Fuel_TypeChart));
        panel.add(new ChartPanel(Owner_TypeChart));
        panel.add(new ChartPanel(seatsChart));


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
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(title, dataset,true,false,false);

        PiePlot plot = (PiePlot) chart.getPlot();
       
        return chart;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PieChart example = new PieChart("Pie Chart Example");
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setVisible(true);
        });
    }
}

