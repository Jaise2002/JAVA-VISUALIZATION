package com.example;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVProcessor {

    public void removeNullRows(String inputCsvFile, String outputCsvFile) {
        String line = "";
        String cvsSplitBy = ",";
        List<List<String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputCsvFile))) {
            // Read CSV into 2D List
            while ((line = br.readLine()) != null) {
                String[] row = line.split(cvsSplitBy);
                List<String> rowData = new ArrayList<>();
                for (String value : row) {
                    rowData.add(value.trim());
                }
                data.add(rowData);
            }

            // Remove rows containing null values
            List<List<String>> newData = new ArrayList<>();
            for (List<String> row : data) {
                boolean containsNull = false;
                for (String value : row) {
                    if (value.isEmpty()) {
                        containsNull = true;
                        break;
                    }
                }
                if (!containsNull) {
                    newData.add(row);
                }
            }

            // Write modified data to output CSV
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputCsvFile))) {
                for (List<String> row : newData) {
                    bw.write(String.join(",", row));
                    bw.newLine();
                }
            }

            System.out.println("Rows containing null values removed successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
