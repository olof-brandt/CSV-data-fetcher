
package sunnyOffgrid02;

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
         //Calculate optimum PV panel slope, based on location's latitude
         constants.PVPanelSlope = ta.PVpanelSlopeCalc(simPar.latitude);
         //Get irradiation, temperature and day length data from PVPGIS database
         vectors = pvgis.getAllMonthsData(simPar, constants, vectors);
         System.out.println(Arrays.toString(vectors.allMonthsIrradiance));

         //Extract data from the months chosen in the specification
         vectors = ta.extractChosenMonthsData(vectors, constants, simPar);
         System.out.println(Arrays.toString(vectors.chosenMonthsIrradiance));
      }
   }
}
