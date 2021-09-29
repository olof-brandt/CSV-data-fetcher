package com.example.sunnyoffgrid009;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activityCalculate extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        TextView mytxt = (TextView) findViewById(R.id.activityText);


        //Main loop which runs until all the simulations are finished.
        for(int countLocation = 0; countLocation < constants.countLocationLen; countLocation++)//while(constants.countLocation < constants.countLocationLen)
        {
            //simPar.location = JOptionPane.showInputDialog("Location:");//showConfirmDialog(null, "Hejsan", "Rubrik", JOptionPane.ERROR_MESSAGE);
            //System.out.println();
            //System.out.println("Customer e-mail address: " + simPar.emailAdress);
            //System.out.println("Location: " + simPar.location);

            //Calculate optimum PV panel slope, based on location's latitude
            constants.PVPanelSlope = ta.PVpanelSlopeCalc(simPar.latitude);

            //Get irradiation, temperature and day length data from PVPGIS database
            vectors = pvgis.getAllMonthsData(simPar, constants, vectors);

            //Extract data from the months chosen in the specification
            vectors = ta.extractChosenMonthsData(vectors, constants, simPar);

            //Find which month has the lowest irradiance
            constants.WORST_MONTH_INDEX = ta.worstMonthFinder(vectors.chosenMonthsIrradiance);

            //Calculate electricity generation during the worst month
            constants.rainyDayProducedEnergyLowestWh = (int) (constants.badDayScalar * pvsimulations.dailyElectricityProductionWh(constants, vectors));

            constants.sunnyDayProducedEnergyLowestWh = (int) (constants.goodDayScalar * pvsimulations.dailyElectricityProductionWh(constants, vectors));


            //Initial estimation of required number of batteries and solar panels
            //variables.batteries = (int) pvsimulations.bpCalc(constants, variables, simPar);


            //variables.pvPanels = (int) pvsimulations.ppCalc(constants, variables, simPar);



            //slutligen en första skattning av bp och pp
            //Därefter uppdatera hur jag inhämtar solardata, så att laddar ner csv från pvgis

        }

        mytxt.setText(String.valueOf(constants.PVPanelSlope));//vectors.chosenMonthsIrradiance[0]));

        //Intent i = getIntent();
        //String message = i.getStringExtra("COOL");
        //((TextView)findViewById(R.id.activityText)).setText(message);
    }
}
