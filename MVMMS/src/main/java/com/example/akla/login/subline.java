package com.example.akla.login;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import jxl.Cell;


public class subline extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;
    private static final String[] paths = {
            "AREA ELECTRICAL ENGINEER - TRINCOMALEE..",
            "AREA ELECTRICAL ENGINEER - BATTICALO.",
            "ARFA ELECTRICAL ENGINEER - AMPARA",
            "AREA ELECTRICAL ENGINEER - KALMUNA",
            "AREA ELECTRICAL ENGINEER - VALAICHCHEN",
            "AREA ELECTRICAL ENGINEER- GALAGEDARA",
            "AREA ELECTRICAL ENGINEER - DAMBULLA",
            "AREA ELECTRICAL ENGINEER - HASALAKA",
            "AREA ELECTRICAL ENGINEER - NAWALAPITIY",
            "AREA ELECTRICAL ENGINEER - NUWARAELIYA",
            "AREA ELECTRICAL ENGINEER - KEGALLE CP2",
            "AREA ELECTRICAL ENGINEER - MAWANELLA",
            "AREA ELECTRICAL ENGINEER - GINIGATHHEN",
            "AREA ELECTRICAL ENGINEER - PERADENIYA",
            "AREA ELECTRICAL ENGINIEER HANGURANKE.",
    };



    private Spinner spnArea;
    private EditText TSNet;
    private EditText SINet;
    private EditText Capacityet;
    private EditText voltageet;
    private EditText EarthFaultCurrentet;
    private EditText Makeet;
    private EditText tranfomerTypeet;
    private EditText MountTypeet;
    private EditText Yearet;
    private EditText LTFeederset;
    private EditText DayFeederet;
    private EditText FeederLoadet;
    private EditText DayLoadinget;
    private EditText NightLoadinget;
    private EditText EarthResistanceet;
    private Button btnsave;




    private String xTSNet;
    private String  xSINet;
    private String  xCapacityet;
    private String  xvoltage;
    private String  xEarthFaultCurrentet;
    private String  xMakeet;
    private String  xtransfomerTypeet;
    private String  xMountType;
    private String  xYearet;
    private String  xLTFeederset;
    private String  xDayFeederet;
    private String  xFeederLoadet;
    private String  xDayLoadinget;
    private String  xNightLoadinget;
    private String  xEarthResistanceet;








    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subline);

        spnArea = (Spinner) findViewById(R.id.spnArea);
        TSNet = (EditText) findViewById(R.id.TSNet);
        SINet = (EditText) findViewById(R.id.SINet);
        Capacityet = (EditText) findViewById(R.id.Capacityet);
        voltageet = (EditText) findViewById(R.id.voltageet);
        EarthFaultCurrentet = (EditText) findViewById(R.id.etEarthFaultCurrentet);
        Makeet = (EditText) findViewById(R.id.Makeet);
        //tranfomerTypeet = (EditText) findViewById(R.id.tranfomerTypeet);//this should be change according to backend process
       // MountTypeet = (EditText) findViewById(R.id.Mount_Typeet);
        Yearet = (EditText) findViewById(R.id.Yearet);
        LTFeederset = (EditText) findViewById(R.id.LT_Feederset);
        DayFeederet = (EditText) findViewById(R.id.Day_Feederet);
        FeederLoadet = (EditText) findViewById(R.id.Feeder_Loadet);
        DayLoadinget = (EditText) findViewById(R.id.Day_Loadinget);
        NightLoadinget = (EditText) findViewById(R.id.Night_loadinget);
        EarthResistanceet = (EditText) findViewById(R.id.Earth_resistanceet);


        btnsave = (Button) findViewById(R.id.btnsave);

        btnsave.setOnClickListener(v -> {

            xTSNet = TSNet.getText().toString();
            xSINet= SINet.getText().toString();
            xCapacityet= Capacityet.getText().toString();
            xvoltage = voltageet.getText().toString();
            xEarthFaultCurrentet = EarthFaultCurrentet.getText().toString();
            xMakeet= Makeet.getText().toString();
            xtransfomerTypeet = tranfomerTypeet.getText().toString();
            xMountType = MountTypeet.getText().toString();
            xYearet = Yearet.getText().toString();
            xLTFeederset =LTFeederset.getText().toString();
            xDayFeederet = DayFeederet.getText().toString();
            xFeederLoadet = FeederLoadet.getText().toString();
            xDayLoadinget = DayLoadinget.getText().toString();
            xNightLoadinget = NightLoadinget.getText().toString();
            xEarthResistanceet =  EarthResistanceet .getText().toString();
            xEarthFaultCurrentet = EarthFaultCurrentet.getText().toString();


            AlertDialog.Builder builder1 = new AlertDialog.Builder(subline.this);
            builder1.setTitle("Display");
            builder1.setMessage(xTSNet + "\n\n"+xSINet + "\n\n" + xCapacityet + "\n\n" +xvoltage + "\n\n" + xEarthFaultCurrentet
            + "\n\n" +Makeet + "\n\n" + xtransfomerTypeet + "\n\n" + xMountType + "\n\n" +  xYearet + "\n\n" +  xLTFeederset
            + "\n\n" + xDayFeederet + "\n\n" + xDayLoadinget + "\n\n" + xNightLoadinget + "\n\n" + xEarthResistanceet + "\n\n" + xEarthFaultCurrentet );
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();


        });



        spinner = (Spinner)findViewById(R.id.spnArea);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(subline.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

}
