package com.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NULLReader {

    public void NulREAD() {
        String csvFile = "indian-auto-mpg (1).csv";
        String line = "";
        String cvsSplitBy = ",";
        int maxRows = 10; // Maximum rows to process

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int rowNum = 0;
            int numColumns = 0;
            int[] nullCountByColumn=new int[300];
            String[] columnNames = null;

            while ((line = br.readLine()) != null && rowNum < maxRows) {
                String[] data = line.split(cvsSplitBy);
                if (rowNum == 0) {
                    numColumns = data.length;
                    columnNames = data;
                    nullCountByColumn = new int[numColumns];
                } else {
                    for (int i = 0; i < numColumns && i < data.length; i++) {
                        if (data[i].isEmpty() || data[i].equalsIgnoreCase("null")) {
                            nullCountByColumn[i]++;
                        }
                    }
                }
                rowNum++;
            }

            // Print column names
            for (String columnName : columnNames) {
                System.out.printf("%-12s", columnName); // Adjust width as needed
            }
            System.out.println(); // Move to the next line after printing column names

            // Print sum of null values for each column
            for (int i = 0; i < numColumns; i++) {
                System.out.printf("%-13d", nullCountByColumn[i]);
            }
            System.out.println(); // Move to the next line

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
