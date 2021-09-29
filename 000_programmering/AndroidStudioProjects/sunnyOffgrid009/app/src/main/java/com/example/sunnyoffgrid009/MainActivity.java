package com.example.sunnyoffgrid009;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
//import com.github.mikephil.charting.formatter.ValueFormatter;


public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);
        setTitle("Home");
    }

    public void goToPage_calculation(View v) {
        Intent i = new Intent(this, activityCalculate.class);
        //String message = ((EditText) findViewById(R.id.nameText)).getText().toString();
        //i.putExtra("COOL", message);
        startActivity(i);

    }

}

/*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextView textView = (TextView) findViewById(R.id.textView2);
        //textView.setText("text you want to display");
    }
}

 */

/*
public class MainActivity extends AppCompatActivity{

    //private static final String TAG = "MainActivity";

    LineChart lineChart;

    //String s = ((OwnParameters) this.getApplication()).getSomeVariable();
    //System.out.println(


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineChart = (LineChart) findViewById(R.id.lineChart);

        ArrayList<String> xAXES = new ArrayList<>();
        ArrayList<Entry> yAXESsin = new ArrayList<>();
        ArrayList<Entry> yAXEScos = new ArrayList<>();
        double x = 0;
        int numDataPoints = 1000;

        for (int i = 0; i < numDataPoints; i++){
            float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
            x = x + 0.1;
            yAXESsin.add(new Entry(sinFunction,i));
            yAXEScos.add(new Entry(cosFunction,i));
            xAXES.add(i, String.valueOf(x));
        }

        String[] xaxes = new String[xAXES.size()];

        for(int i = 0; i < xAXES.size(); i++){
            xaxes[i] = xAXES.get(i).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos, "cos");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);

        LineDataSet lineDataSet2 = new LineDataSet(yAXESsin,"sin");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        lineChart.setData(new LineData(xaxes, lineDataSets));
        lineChart.setVisibleXRangeMaximum(65f);

        //simpleLineChart.setVisibleX







        //Log.d("string:", OwnStrings.bibliographyFile); //Ok, den hittar den variabeln!
    }
*/

