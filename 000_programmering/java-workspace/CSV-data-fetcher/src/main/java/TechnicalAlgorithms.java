package sunnyOffgrid02;

import java.io.File;
import java.util.Arrays;

import sunnyOffgrid02.DataHandlingAlgorithms;
import sunnyOffgrid02.Parameters;
import sunnyOffgrid02.SimulationParameters;

//Java parent class containing child classes and methods for
//technical solar power calculations
public class TechnicalAlgorithms
{
   //Extract data from chosen months
   public Parameters.OwnVectors extractChosenMonthsData(Parameters.OwnVectors vectors, Parameters.OwnConstants constants, SimulationParameters simPar)
   {
      int chosenMonthVectorIndex = 0;
      vectors.chosenMonthsIrradiance = new int[simPar.lastUsageMonth - simPar.firstUsageMonth + 1];
      vectors.chosenMonthsTemperature = new int[simPar.lastUsageMonth - simPar.firstUsageMonth + 1];
      vectors.chosenMonthsDayLength = new int[simPar.lastUsageMonth - simPar.firstUsageMonth + 1];
      vectors.chosenMonthsTextSvenska = new String[simPar.lastUsageMonth - simPar.firstUsageMonth + 1];
      vectors.chosenMonthsNumbers = new int[simPar.lastUsageMonth - simPar.firstUsageMonth + 1];

      for(int k = simPar.firstUsageMonth - 1; k < simPar.lastUsageMonth; k++)
      {
         vectors.chosenMonthsIrradiance[chosenMonthVectorIndex] = vectors.allMonthsIrradiance[k];
         vectors.chosenMonthsTemperature[chosenMonthVectorIndex] = vectors.allMonthsTemperature[k];
         vectors.chosenMonthsDayLength[chosenMonthVectorIndex] = vectors.allMonthsDayLength[k];
         vectors.chosenMonthsTextSvenska[chosenMonthVectorIndex] = vectors.allMonthsTextSvenska[k];
         vectors.chosenMonthsNumbers[chosenMonthVectorIndex] = vectors.allMonthsNumbers[k];

         chosenMonthVectorIndex = chosenMonthVectorIndex + 1;
      } //end for
      return vectors;
   } //end method extractChosenMonthsData

   //Method which finds the month with the lowest irradiance.
   public int worstMonthFinder(int[] allMonthsIrradiance)
   {
      int irradianceNow;
      int irradiancePrev;
      int irradianceWorst;
      int kWorst;

      kWorst = 0;
      irradianceWorst = 1000;

      for(int k = 0; k < allMonthsIrradiance.length; k++)
      {
         irradianceNow = allMonthsIrradiance[k];
         if(irradianceNow < irradianceWorst)
         {
            irradianceWorst = irradianceNow;
            kWorst = k;
         }
      }
      return kWorst;
   } //end public int worstMonthFinder

   //Method which calculates the optimum angle of
   //the PV panels, dependent of the site's latitude.
   //@param latitude - The latitude of the solar power system
   //@param return - The PV panel angle as a double
   public double PVpanelSlopeCalc(double latitude)
   {
      double PVPanelSlope;

      if(Math.abs(latitude) > 30)
      {
         PVPanelSlope = 30;
      }
      else if(Math.abs(latitude) < 10)
      {
         PVPanelSlope = 0;
      }
      else
      {
         PVPanelSlope = Math.ceil(latitude / 10.0) * 10;
         PVPanelSlope = Math.abs(PVPanelSlope);
      }
      if(Math.abs(latitude) > 60)
      {
         PVPanelSlope = 40;
      }
      return PVPanelSlope;
   }

   //Parent class containing child classes for obtaining
   //weather data from the PVGIS database.
   static class PVGIS
   {
      public Parameters.OwnVectors getAllMonthsData(SimulationParameters simPar, Parameters.OwnConstants constants, Parameters.OwnVectors vectors)
      {
         //CONSTANTS
         final int MONTHS_PER_YEAR = 12;
         final int HOURS_PER_DAY = 24;

         //VARIABLE DECLARATION
         int[] allMonthsIrradiance;
         int[] allMonthsTemperature;
         int[] allMonthsDayLength;
         int[][] csvDataIrradiance;
         int[][] csvDataTemperature;
         int[][] csvData;// = new int[MONTHS_PER_YEAR * HOURS_PER_DAY][3];
         int noRowsCsvData;
         int monthIrradianceMax;
         int monthIrradianceNow;
         int monthTemperatureMax;
         int monthTemperatureNow;
         double monthDayLengthNow;
         double irradianceThresholdMonth;
         double hourIrradianceNow;
         double hourIrradiancePrev;
         String solarDataFilePath;

         //VARIABLE INITIALIZATION
         allMonthsIrradiance = new int[12];
         allMonthsTemperature = new int[12];
         allMonthsDayLength = new int[12];
         csvDataIrradiance = new int[HOURS_PER_DAY][MONTHS_PER_YEAR];
         csvDataTemperature = new int[HOURS_PER_DAY][MONTHS_PER_YEAR];
         boolean dayStartBool = false;
         boolean dayEndBool = false;
         int rowCount = 0;
         int hourCount = 0;
         int monthCount = 0;
         double timeNow = 0;
         double timeDayStart = 0;
         double timeDayEnd = 0;
         double timeDayPeak = 0;
         double irradianceThresholdPercentage = 0.25;

         //Create csvData object and import solar data from chosen location
         solarDataFilePath = new File("src/main/java/sunnyOffgrid02").getAbsolutePath() + File.separator + "solarData.csv";
         DataHandlingAlgorithms.CSVReading csvObj = new DataHandlingAlgorithms.CSVReading();
         csvData = csvObj.csvReader4(solarDataFilePath);
         noRowsCsvData = csvData.length;

         //Iterate through csvData matrix and convert columns into vectors.
         while(rowCount < noRowsCsvData)
         {
            csvDataIrradiance[hourCount][monthCount] = csvData[rowCount][0];
            csvDataTemperature[hourCount][monthCount] = csvData[rowCount][1];
            rowCount = rowCount + 1;
            hourCount = hourCount + 1;
            if(hourCount > HOURS_PER_DAY - 1)
            {
               hourCount = 0;
               monthCount = monthCount + 1;
            } //end if
         } //end while

         //FIND PEAK IRRADIANCE AND TEMPERATURE EACH MONTH
         monthCount = 0;
         hourCount = 0;
         //Iterate through months
         while(monthCount < MONTHS_PER_YEAR)
         {
            monthIrradianceNow = csvDataIrradiance[hourCount][monthCount];
            monthIrradianceMax = monthIrradianceNow;
            monthTemperatureNow = csvDataTemperature[hourCount][monthCount];
            monthTemperatureMax = monthTemperatureNow;
            //Iterate through hours
            while(hourCount < HOURS_PER_DAY)
            {
               monthIrradianceNow = csvDataIrradiance[hourCount][monthCount];
               monthTemperatureNow = csvDataTemperature[hourCount][monthCount];
               if(monthIrradianceNow > monthIrradianceMax)
               {
                  monthIrradianceMax = monthIrradianceNow;
               } //end if
               if(monthTemperatureNow > monthTemperatureMax)
               {
                  monthTemperatureMax = monthTemperatureNow;
               } //end if
               hourCount = hourCount + 1;
            } //end while
            allMonthsIrradiance[monthCount] = monthIrradianceMax;
            allMonthsTemperature[monthCount] = monthTemperatureMax;
            //Increase counters
            monthCount = monthCount + 1;
            hourCount = 0;
         } //end while

         //FIND AVERAGE DAY LENGTH FOR EACH MONTH
         hourCount = 0;
         monthCount = 0;
         while(monthCount < MONTHS_PER_YEAR)
         {
            irradianceThresholdMonth = irradianceThresholdPercentage * allMonthsIrradiance[monthCount];
            hourIrradianceNow = csvDataIrradiance[hourCount][monthCount];
            hourIrradiancePrev = hourIrradianceNow;
            while(hourCount < HOURS_PER_DAY)
            {
               hourIrradianceNow = csvDataIrradiance[hourCount][monthCount];
               if((hourIrradianceNow > irradianceThresholdMonth) && (hourIrradiancePrev < irradianceThresholdMonth))
               {
                  timeDayStart = timeNow - 0.5;
                  dayStartBool = true;
               } //end if
               if((hourIrradianceNow < irradianceThresholdMonth) && (hourIrradiancePrev > irradianceThresholdMonth))
               {
                  timeDayEnd = timeNow - 0.5;
                  dayEndBool = true;
                  if(timeDayEnd < 10)
                  {
                     timeDayEnd = timeDayEnd + 24;
                  } //end if
               } //end if
               if(hourIrradianceNow > hourIrradiancePrev)
               {
                  timeDayPeak = timeNow - 0.5;
               } //end if
               if((dayStartBool == true) && (dayEndBool == false))
               {
                  timeDayEnd = timeDayStart + 2 * (timeDayPeak - timeDayStart);
               } //end if
               hourIrradiancePrev = hourIrradianceNow;

               hourCount = hourCount + 1;
               timeNow = timeNow + 1;
            } //end while
            monthDayLengthNow = timeDayEnd - timeDayStart;
            allMonthsDayLength[monthCount] = (int) monthDayLengthNow;

            monthCount = monthCount + 1;
            hourCount = 0;
            timeNow = 0;
         } //end while

         //Update vector class instance with output data
         vectors.allMonthsIrradiance = allMonthsIrradiance;
         vectors.allMonthsTemperature = allMonthsTemperature;
         vectors.allMonthsDayLength = allMonthsDayLength;

         return vectors;
      } //end method getAllMonthsData
   } //end class PVGIS
} //end class TechnicalAlgorithms
