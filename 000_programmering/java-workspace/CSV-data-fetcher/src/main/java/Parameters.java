package sunnyOffgrid02;

public class Parameters
{

   int hejsan;

   static class OwnBooleans
   {

      //instance variables
      public boolean predecidedSystem = false;
      public boolean batteriesDecided = false;
      public boolean latexEnglish = false;
      public boolean sale = true;
      public boolean autoSlope = true;
      public boolean lithium = false;
      public boolean solarPowerSystemDecided = false; //Samma som predecided?
      public boolean createFolder = true;
      public boolean createLatex = false;
      public boolean runFinalPlotting = true;


   }


   static class OwnConstants
   {

      public int constantInitial = 1;

      public int panelsDecided = constantInitial;
      public int batteriesDecided = constantInitial;
      public static int WORST_MONTH_INDEX;
      //public int worstMonthNumber = worstMonthNumberInitial;

      //Technical parameters
      //public double solarPanelArea = 1.26; //m^2, for 200 W panel
      public double PVPanelSlopeManual = 10;

      public double badDayScalar = 0.1;
      public double goodDayScalar = 1.0;

      public int rainyDayProducedEnergy = constantInitial;
      public int sunnyDayProducedEnergy = constantInitial;
      public int rainyDayProducedEnergyLowestWh = constantInitial;
      public int sunnyDayProducedEnergyLowestWh = constantInitial;

      //SoC percentages
      public int minimumSocMarginFromGoalPercent = 20;
      public int maximumSocGoalPercent = 100;
      public int minimumSocGoalPercent = 50;

      //Efficiency in percent
      public double etaPV = 0.18;
      public double etaMPPT = 0.9;
      public double etaSolar = etaPV * etaMPPT;
      public double etaInverter = 0.9;

      //Rated specifications
      public int panelRatedPowerW = 200;
      public int individualBatteryChargeAh = 72;

      public double PVpanelAreaM2 = 1.26;
      public double PVPanelSlope = constantInitial;

      public double loadPowerW = constantInitial;
      public double loadPowerWGross = constantInitial;

      public int individualBatteryEnergyWh = 12 * individualBatteryChargeAh;
      public int individualBatteryEnergyJ = 3600 * individualBatteryEnergyWh;

      //SoC goals
      public double socMinGoalLeadAcid = 0.5;
      public double socMinGoalLithium = 0.2;

      //PV simulation parameters
      public double simulationDurationHours = 23.0;
      public double simulationLoadStartHours = 0.5;
      public double simulationLoadRising = 20.0;
      public double simulationLoadFalling = 20;
      public double simulationLoadDurationSeconds = 3600 * simulationDurationHours;

      //Programming variables
      public int countLocation = 0;
      public int countLocationLen = constantInitial;

      //Computer program functionality
      public int beepFrequency1 = 300; //Hz
      public int beepFrequency2 = 600; //Hz
      public int beepDuration = 300; //ms


   }

   static class OwnVariables
   {

      public int variableInitial = 1;
      public int irradiationAverageMonthly = variableInitial;
      public int irradiationGoodDay = variableInitial;
      public int irradiationBadDay = variableInitial;
      public int temperatureAverageMonthly = variableInitial;
      public int dayLengthAverageMonthly = variableInitial;

      public int pvPanels = variableInitial;
      public int batteries = variableInitial;
   }


   static class OwnVectors
   {

      public int[] initialVectorInt = new int[]{};
      public String[] initialVectorString = new String[]{};

      public int[] allMonthsNumbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
      public String[] allMonthsTextSvenska = new String[]{"Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"};

      public int[] allMonthsIrradiance = initialVectorInt;
      public int[] allMonthsTemperature = initialVectorInt;
      public int[] allMonthsDayLength = initialVectorInt;
      public int[] chosenMonthsNumbers = initialVectorInt;
      public int[] chosenMonthsDayLength = initialVectorInt;
      public int[] chosenMonthsIrradiance = initialVectorInt;
      public int[] chosenMonthsTemperature = initialVectorInt;

      public String[] chosenMonthsTextSvenska = initialVectorString;

   }

   static class OwnStrings
   {
      public String emptyString = "";
      public String receiptNumber = "21";
      public String csvFolderName = "csvFolder/";
      public String bibliographyFile;
      public String titleFileEndBattery;
      public String titleFileEndPVpanel;
      public String titleIterPlotBattery;
      public String titleIterPlotPVpanel;
      public String imageConnection;
      public String imageBattery;
      public String imageHeading;
      public String imageLogo;
      public String imagePVpanel;

      public String simulationNameInitial = "";
      public String simulationName = simulationNameInitial;
      public String worstMonthTextInitial = "Juni";
      public String worstMonthText = worstMonthTextInitial;
      public String functionFolderName = simulationNameInitial;

   }


}
