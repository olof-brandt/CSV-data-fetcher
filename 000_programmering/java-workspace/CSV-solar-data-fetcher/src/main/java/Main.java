/** This Java program calculates a solution to how many
 * solar panels and batteries which can be used for an
 * off-grid solar power system. The program takes the following
 * input parameters:
 * - Location
 * - GPS coordinates
 * - Months
 * - Number of rainy days before the battery bank is discharged
 * - Number of sunny days before the battery bank is charged up again
 *
 * Based on these input parameters, solar irradiance and
 * temperature data is collected from the PVGIS database.
 * The generated electrical energy per solar panel is then calculated,
 * for a rainy day and for a sunny day during the
 * "worst month of the year" (with the least solar irradiation).
 * The solar power system is then sized based on this worst case scenario,
 * so that the battery bank is not discharged too much during the
 * specified number of rainy days. The battery bank should be
 * fully charged again when the specified number of sunny days
 * follow the rainy days.
 */

package solarDataFetcher;

import java.util.Arrays;
import javax.swing.JOptionPane;

public class Main
{
   public static void main(String[] args)
   {
      //Class objects containing global variables are instantiated. ----
      Parameters p = new Parameters();
      Parameters.OwnBooleans booleans = new Parameters.OwnBooleans();
      Parameters.OwnConstants constants = new Parameters.OwnConstants();
      Parameters.OwnVectors vectors = new Parameters.OwnVectors();
      Parameters.OwnStrings strings = new Parameters.OwnStrings();
      Parameters.OwnVariables variables = new Parameters.OwnVariables();
      SimulationParameters simPar = new SimulationParameters();
      TechnicalAlgorithms ta = new TechnicalAlgorithms();
      TechnicalAlgorithms.PVGIS pvgis = new TechnicalAlgorithms.PVGIS();
      TechnicalAlgorithms.PVsimulations pvsimulations = new TechnicalAlgorithms.PVsimulations();

      //Main loop which runs until all the simulations are finished.
      for(int countLocation = 0; countLocation < constants.countLocationLen; countLocation++)//while(constants.countLocation < constants.countLocationLen)
      {
         //simPar.location = JOptionPane.showInputDialog("Location:");//showConfirmDialog(null, "Hejsan", "Rubrik", JOptionPane.ERROR_MESSAGE);
         //System.out.println();
         System.out.println("Customer e-mail address: " + simPar.emailAdress);
         System.out.println("Location: " + simPar.location);

         //Calculate optimum PV panel slope, based on location's latitude
         constants.PVPanelSlope = ta.PVpanelSlopeCalc(simPar.latitude);
         //Get irradiation, temperature and day length data from PVPGIS database
         vectors = pvgis.getAllMonthsData(simPar, constants, vectors);
         System.out.println(Arrays.toString(vectors.allMonthsIrradiance));

         //Extract data from the months chosen in the specification
         vectors = ta.extractChosenMonthsData(vectors, constants, simPar);
         System.out.println(Arrays.toString(vectors.chosenMonthsIrradiance));

         //Find which month has the lowest irradiance
         constants.WORST_MONTH_INDEX = ta.worstMonthFinder(vectors.chosenMonthsIrradiance);

         //Calculate electricity generation during the worst month
         constants.rainyDayProducedEnergyLowestWh = (int) (constants.badDayScalar * pvsimulations.dailyElectricityProductionWh(constants, vectors));
         System.out.println("Rainy day electricity generation in " + vectors.chosenMonthsTextSvenska[constants.WORST_MONTH_INDEX] + " [Wh]: " + constants.rainyDayProducedEnergyLowestWh);
         constants.sunnyDayProducedEnergyLowestWh = (int) (constants.goodDayScalar * pvsimulations.dailyElectricityProductionWh(constants, vectors));
         System.out.println("Sunny day electricity generation in " + vectors.chosenMonthsTextSvenska[constants.WORST_MONTH_INDEX] + " [Wh]: " + constants.sunnyDayProducedEnergyLowestWh);

         //Initial estimation of required number of batteries and solar panels
         variables.batteries = (int) pvsimulations.bpCalc(constants, variables, simPar);
         System.out.println("Number of batteries: " + variables.batteries);

         variables.pvPanels = (int) pvsimulations.ppCalc(constants, variables, simPar);
         System.out.println("Number of PV panels: " + variables.pvPanels);


         //slutligen en första skattning av bp och pp
         //Därefter uppdatera hur jag inhämtar solardata, så att laddar ner csv från pvgis



      }


   }

}
