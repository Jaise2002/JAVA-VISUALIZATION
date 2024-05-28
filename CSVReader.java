package com.example;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class CSVReader {
   public static void mian(String[] arg){
    CSVReader csvReader=new CSVReader();
    csvReader.reader();
   }
    public  void reader() {
        String csvFile = "indian-auto-mpg (1).csv"; //READING CSV FILE
        String line = "";
        String cvsSplitBy = ",";
        int maxRows = 10;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int rowNum = 0;
            while ((line = br.readLine()) != null && rowNum < maxRows) {
                if(rowNum == 0) {
                    // Print column names
                    String[] columnNames = line.split(cvsSplitBy);
                    for (String columnName : columnNames) {
                        System.out.printf("%-12s", columnName);
                    }
                    System.out.println(); // Move to the next line after printing column names
                } else {
                    String[] data = line.split(cvsSplitBy);
                    for (String value : data) {
                        System.out.printf("%-13s", value);
                    }
                    System.out.println(); // Move to the next line for the next row
                }
                rowNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


