package sunnyOffgrid02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

public class DataHandlingAlgorithms
{
   static class CSVReading
   {
      public int[][] csvReader4(String filePath)
      {
         //LOCAL CONSTANT INITIALIZATION
         final int MONTHS_PER_YEAR = 12; //Number of months per year
         int HOURS_PER_DAY = 24; //Number of hours per day
         int ROW_START = 8;
         int ROW_END = ROW_START + (MONTHS_PER_YEAR * HOURS_PER_DAY);

         //VARIABLE DECLARATION
         int rowCount; //Counter for iterating through the CSV rows
         int[][] csvData; //2D array for extracting CSV data

         //VARIABLE INITIALIZATION
         rowCount = 0;
         csvData = new int[ROW_END - ROW_START][3];

         try
         {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            //Loop through all rows in the whole CSV file.
            while((line = br.readLine()) != null)
            {
               //Read one line from the CSV to a string.
               String[] lineValues = line.split("\\t");
               //Extract rows between ROW_START and ROW_END.
               if((rowCount > ROW_START - 1) && (rowCount < ROW_END))
               {
                  //Extract irradiance values.
                  csvData[rowCount - ROW_START][0] = (int) Double.parseDouble(lineValues[2]);
                  //Extract temperature values.
                  csvData[rowCount - ROW_START][1] = (int) Double.parseDouble(lineValues[8]);
               }
               rowCount = rowCount + 1;
            }
         }
         catch(FileNotFoundException e)
         {
            e.printStackTrace();
         }
         catch(IOException e)
         {
            e.printStackTrace();
         }
         return csvData;
      } //end public int[][]
   } //end static class CSVReading
} //end public class DataHandlingAlgorithms
