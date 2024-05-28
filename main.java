package com.example;
import java.util.Scanner;
public class main {
    public static void main(String[] args) {
    Scanner scanner=new Scanner(System.in);
    CSVProcessor csvProcessor=new CSVProcessor();
    CSVReader csvReader=new CSVReader();
    NULLReader nullReader=new NULLReader();
        while(true){
             System.out.println("1.read csv :");
             System.out.println("2.check null value count :");
             System.out.println("3.remove null values :");
             System.out.println("4.Bar chart :");
             System.out.println("5.Scatter plot :");
             System.out.println("6.Line Chart :");
             System.out.println("7.PieChart :");
             System.out.println("8.Exit :");
             int choice=Integer.parseInt(scanner.nextLine());
            switch(choice){
                case 1:
                csvReader.reader();
                break;
                case 2:
                nullReader.NulREAD();
                break;
                case 3:
                String inputCsvFile = "indian-auto-mpg (1).csv";
                String outputCsvFile = "output.csv";
                csvProcessor.removeNullRows(inputCsvFile, outputCsvFile);
                break;
                case 4:
                Barchart bar=new Barchart("Bar chart");
                break;
                case 5:
                ScatterPlot scatter=new ScatterPlot("Scatter Plot");
                break;
                case 6:
                LineChart linePlot=new LineChart("LineChart");
                break;
                case 7:
                PieChart pieChart=new PieChart("PieChart");
                break;
                case 8:
                System.exit(0);
                break;
                default:
                break;
            }
        }
       
        
    }
}
