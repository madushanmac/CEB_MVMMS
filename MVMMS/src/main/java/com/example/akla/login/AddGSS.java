package com.example.akla.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.akla.login.Util.isConnected;

public class AddGSS extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button buttonA;



    // Add GSS Transforms
//
    EditText edittext1;
    EditText edittext2;
    EditText edittext3;
    EditText edittext4;
    EditText edittext5;
    EditText edittext6;
    EditText edittext8;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView textView11;
    TextView textView12;
    TextView textView13;
    Button location;
    Spinner spn1;
    Button save;
    Button cancel;

//    Add 33kV Feeder Bay veriables

    EditText edittext14;
    EditText edittext15;
    EditText edittext16;
    EditText edittext17;
    EditText edittext18;
    EditText edittext24;
    EditText edittext25;
    EditText edittext26;
    TextView textView14;
    TextView textView15;
    TextView textView16;
    TextView textView17;
    TextView textView18;
    TextView textView19;
    TextView textView20;
    TextView textView21;
    TextView textView22;
    TextView textView23;
    TextView textView24;
    TextView textView25;
    TextView textView26;
    Button location1;
    Spinner spn2;
    Button save1;
    RadioGroup g1;
    RadioButton r1;
    RadioButton r2;

    // Add Bus Section veribles

    EditText edittext27;
    EditText edittext28;
    EditText edittext29;
    EditText edittext30;
    EditText edittext31;
    EditText edittext32;
    EditText edittext33;
    EditText edittext34;
    EditText edittext35;
    EditText edittext36;
    EditText edittext37;
    EditText edittext38;
    EditText edittext39;
    TextView textView27;
    TextView textView28;
    TextView textView29;
    TextView textView30;
    TextView textView31;
    TextView textView32;
    TextView textView33;
    TextView textView34;
    TextView textView35;
    TextView textView36;
    TextView textView37;
    TextView textView38;
    TextView textView39;
    TextView textView40;
    TextView textView41;
    TextView textView42;
    TextView textView43;
    TextView textView44;
    TextView textView45;
    Button location2;
    Spinner spn3;
    Button save2;
    RadioGroup g2;
    RadioButton r3;
    RadioButton r4;


    // Add Bus Coupler

    EditText edittext46;
    EditText edittext47;
    EditText edittext48;
    EditText edittext49;
    EditText edittext50;
    EditText edittext51;
    EditText edittext52;
    EditText edittext53;
    EditText edittext54;
    TextView textView46;
    TextView textView47;
    TextView textView48;
    TextView textView49;
    TextView textView50;
    TextView textView51;
    TextView textView52;
    TextView textView53;
    TextView textView54;
    TextView textView55;
    TextView textView56;
    TextView textView58;
    TextView textView59;
    TextView textView60;
    TextView textView61;
    Button location3;
    Spinner spn4;
    Button save3;
    RadioGroup g3;
    RadioButton r5;
    RadioButton r6;

    // Circuit Switch


    EditText edittext62;
    EditText edittext63;
    EditText edittext64;
    EditText edittext65;
    EditText edittext66;
    EditText edittext67;
    EditText edittext68;
    EditText edittext69;
    EditText edittext70;
    TextView textView62;
    TextView textView63;
    TextView textView64;
    TextView textView65;
    TextView textView66;
    TextView textView67;
    TextView textView69;
    TextView textView70;
    TextView textView71;
    TextView textView72;
    TextView textView73;
    TextView textView74;
    TextView textView75;
    TextView textView76;
    Button location4;
    Spinner spn5;
    Button save4;
    RadioGroup g4;
    RadioButton r7;
    RadioButton r8;

    // Earth Switch

    EditText edittext77;
    EditText edittext78;
    EditText edittext79;
    EditText edittext80;
    EditText edittext81;
    EditText edittext82;
    EditText edittext83;
    EditText edittext84;
    EditText edittext85;
    TextView textView77;
    TextView textView78;
    TextView textView79;
    TextView textView80;
    TextView textView81;
    TextView textView82;
    TextView textView83;
    TextView textView84;
    TextView textView85;
    TextView textView86;
    TextView textView87;
    TextView textView88;
    TextView textView89;
    TextView textView90;
    TextView textView91;
    Button location5;
    Spinner spn6;
    Button save5;
    RadioGroup g5;
    RadioButton r9;
    RadioButton r10;

    // Isolator Switch

    EditText edittext92;
    EditText edittext93;
    EditText edittext94;
    EditText edittext95;
    EditText edittext96;
    EditText edittext97;
    EditText edittext98;
    EditText edittext99;
    EditText edittext100;
    TextView textView92;
    TextView textView93;
    TextView textView94;
    TextView textView95;
    TextView textView96;
    TextView textView97;
    TextView textView98;
    TextView textView99;
    TextView textView100;
    TextView textView101;
    TextView textView102;
    TextView textView103;
    TextView textView104;
    TextView textView105;
    TextView textView106;
    Button location6;
    Spinner spn7;
    Button save6;
    RadioGroup g6;
    RadioButton r11;
    RadioButton r12;




    int clickcount10;
//    Spinner SpinnerTypeNew;
//    Adapter adapternew;




    String phmBranch;

    //load Province
    Spinner SpinnerProvince;
    HashMap<Integer, String> spinnerMapProvince = new HashMap<Integer, String>();
    String[] valuesPro = new String[]{};

    //load Area
    Spinner SpinnerArea;
    String province;
    String valuesArea[] = new String[]{};
    HashMap<Integer,String> SpinnerMapArea = new HashMap<Integer, String>();

    //load CSC
    Spinner SpinnerCSC;

    String[] valuesCsc = new String[]{};
    HashMap<Integer, String> SpinnerMapCsc = new HashMap<Integer, String>();

    //load Line
    Spinner SpinnerLine;
    String area;
    String valuesLine[] = new String[]{};
    HashMap<Integer,String> SpinnerMapLine = new HashMap<Integer, String>();

    //to get hash map details
    String line;
    String csc;

    Spinner voltage_level;
    Spinner gantry_type;
    Spinner construction_type;
    Spinner gantry_electrcial_type;
    Spinner bus_bar_conductor;
    Spinner bus_bar_insulator;
    Spinner auxillary_system;
    Spinner overhead_earthing;

    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;
    ArrayAdapter<CharSequence> adapter3;
    ArrayAdapter<CharSequence> adapter4;
    ArrayAdapter<CharSequence> adapter5;
    ArrayAdapter<CharSequence> adapter6;
    ArrayAdapter<CharSequence> adapter7;
    ArrayAdapter<CharSequence> adapter8;
    Button btn_date;
    EditText in_date;
    private int mYear, mMonth, mDay;

    String[] values = new String[]{};
    HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();


    //Offline data loading
    String[] values1 = new String[]{};
    String[] values2 = new String[]{};
    String[] values3 = new String[]{};
    String[] values4 = new String[]{};
    HashMap<Integer, String> spinnerMap1 = new HashMap<Integer, String>();
    HashMap<Integer, String> spinnerMap2 = new HashMap<Integer, String>();
    HashMap<Integer, String> spinnerMap3 = new HashMap<Integer, String>();
    HashMap<Integer, String> spinnerMap4 = new HashMap<Integer, String>();


    //Define Variebles for save data in db
    Spinner Province;
    Spinner Area;
    Spinner CSC;
    Spinner Line;
    EditText Name;
    EditText Code;
    EditText ShortCircuitCurrentCapacity;
    EditText EarthFaultCurrentCapacity;
    EditText ConssionDate;
    Spinner Type;
    Spinner ElectricalType;
    EditText NoOfBusBars;
    EditText NoOfBusSections;
    EditText NoOfInBays;
    EditText NoOfOutBays;
    EditText TotalLandArea;
    EditText NoOfAutoRecloser;
    EditText NoOfLBS;
    EditText NoOfABS;
    EditText NoOfSurgeArrestors;
    EditText NoOfDddloLinks;
    EditText NoOfDdloFuses;
    EditText NoOfInFeeders;
    EditText NoOfOutFeeders;

    // To get location details
    private FusedLocationProviderClient client;
    String latitude;
    String longitude;
    private ProgressDialog ProgDialog;

    //Create Excel
    DBHelper mydb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gss);


        textView1 = (TextView)findViewById(R.id.textAsset);
        edittext1 = (EditText)findViewById(R.id.etAsset);
        textView2 = (TextView)findViewById(R.id.textCapacity);
        edittext2 = (EditText)findViewById(R.id.etCapacity);
        textView3 = (TextView)findViewById(R.id.textCommissioned);
        edittext3 = (EditText)findViewById(R.id.etCommissioned);
        textView4 = (TextView)findViewById(R.id.textBrand);
        edittext4 = (EditText)findViewById(R.id.etBrand);
        textView5 = (TextView)findViewById(R.id.textModel);
        edittext5 = (EditText)findViewById(R.id.etModel);
        textView6 = (TextView)findViewById(R.id.textSite);
        edittext6 = (EditText)findViewById(R.id.etSite);
        textView7 = (TextView)findViewById(R.id.textViewLatitude);
        textView11 = (TextView)findViewById(R.id.latitude);
        textView12 = (TextView)findViewById(R.id.textViewLongitude);
        textView13 = (TextView)findViewById(R.id.longitude);
        textView8 = (TextView)findViewById(R.id.textCategory);
        edittext8 = (EditText)findViewById(R.id.etCategory);
        textView9 = (TextView)findViewById(R.id.textStatus);
        spn1 = (Spinner) findViewById(R.id.spnStatus);
        location = (Button)findViewById(R.id.bLocation) ;
        save = (Button)findViewById(R.id.btnSaveDB);




        Button button1 = findViewById(R.id.btnTransform);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickcount10 = clickcount10 + 1;
                if (clickcount10 % 2 == 1) {


                    textView1.setVisibility(View.VISIBLE);
                    edittext1.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                    edittext2.setVisibility(View.VISIBLE);
                    textView3.setVisibility(View.VISIBLE);
                    edittext3.setVisibility(View.VISIBLE);
                    textView4.setVisibility(View.VISIBLE);
                    edittext4.setVisibility(View.VISIBLE);
                    textView5.setVisibility(View.VISIBLE);
                    edittext5.setVisibility(View.VISIBLE);
                    textView6.setVisibility(View.VISIBLE);
                    edittext6.setVisibility(View.VISIBLE);
                    textView7.setVisibility(View.VISIBLE);
                    textView8.setVisibility(View.VISIBLE);
                    edittext8.setVisibility(View.VISIBLE);
                    textView9.setVisibility(View.VISIBLE);
                    textView11.setVisibility(View.VISIBLE);
                    textView12.setVisibility(View.VISIBLE);
                    textView13.setVisibility(View.VISIBLE);
                    spn1.setVisibility(View.VISIBLE);
                    location.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);


                }
            }
        });

        save = (Button) findViewById(R.id.btnSaveDB);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickcount10 = clickcount10 + 1;
                if (clickcount10 % 2 == 1) {


                    textView1.setVisibility(View.GONE);
                    edittext1.setVisibility(View.GONE);
                    textView2.setVisibility(View.GONE);
                    edittext2.setVisibility(View.GONE);
                    textView3.setVisibility(View.GONE);
                    edittext3.setVisibility(View.GONE);
                    textView4.setVisibility(View.GONE);
                    edittext4.setVisibility(View.GONE);
                    textView5.setVisibility(View.GONE);
                    edittext5.setVisibility(View.GONE);
                    textView6.setVisibility(View.GONE);
                    edittext6.setVisibility(View.GONE);
                    textView7.setVisibility(View.GONE);
                    textView8.setVisibility(View.GONE);
                    edittext8.setVisibility(View.GONE);
                    textView9.setVisibility(View.GONE);
                    textView11.setVisibility(View.GONE);
                    textView12.setVisibility(View.GONE);
                    textView13.setVisibility(View.GONE);
                    spn1.setVisibility(View.GONE);
                    location.setVisibility(View.GONE);
                    save.setVisibility(View.GONE);

                }
            }
        });






        // Add 33kV Feeder Bay

        textView14 = (TextView)findViewById(R.id.textAsset1);
        edittext14 = (EditText)findViewById(R.id.etAsset1);
        textView15 = (TextView)findViewById(R.id.textCapacity1);
        edittext15 = (EditText)findViewById(R.id.etCapacity1);
        textView16 = (TextView)findViewById(R.id.textCommissioned1);
        edittext16 = (EditText)findViewById(R.id.etCommissioned1);
        textView15 = (TextView)findViewById(R.id.textBrand1);
        edittext15 = (EditText)findViewById(R.id.etBrand1);
        textView16 = (TextView)findViewById(R.id.textMode1);
        edittext16 = (EditText)findViewById(R.id.etMode1);
        textView17 = (TextView)findViewById(R.id.textSite1);
        edittext17 = (EditText)findViewById(R.id.etSite1);
        textView18 = (TextView)findViewById(R.id.textViewLatitude1);
        textView19 = (TextView)findViewById(R.id.latitude1);
        textView20 = (TextView)findViewById(R.id.textViewLongitude1);
        textView21 = (TextView)findViewById(R.id.longitude1);
        textView22 = (TextView)findViewById(R.id.textCategory1);
        edittext18 = (EditText)findViewById(R.id.etCategory1);
        textView23 = (TextView)findViewById(R.id.textCondition1);
        spn2 = (Spinner) findViewById(R.id.spnCondition1);
        textView24 = (TextView)findViewById(R.id.textBreaker1);
        g1 = (RadioGroup)findViewById(R.id.rbBreaker1) ;
        r1 = (RadioButton)findViewById(R.id.radioButtonO1);
        r2 = (RadioButton)findViewById(R.id.radioButtonC1);
        textView25 = (TextView)findViewById(R.id.textAssociated1);
        edittext25 = (EditText)findViewById(R.id.etAssociated1);
        textView26 = (TextView)findViewById(R.id.textAssociated2);
        edittext26 = (EditText)findViewById(R.id.etAssociated2);
        location1 = (Button)findViewById(R.id.bLocation1) ;
        save1 = (Button)findViewById(R.id.btnSaveDB1);


        button2 = (Button)findViewById(R.id.btnFeeder);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickcount10=clickcount10+1;
                if (clickcount10%2==1) {

                    textView14.setVisibility(View.VISIBLE);
                    edittext14.setVisibility(View.VISIBLE);
                    textView15.setVisibility(View.VISIBLE);
                    edittext15.setVisibility(View.VISIBLE);
                    textView16.setVisibility(View.VISIBLE);
                    edittext16.setVisibility(View.VISIBLE);
                    textView15.setVisibility(View.VISIBLE);
                    edittext15.setVisibility(View.VISIBLE);
                    textView16.setVisibility(View.VISIBLE);
                    edittext16.setVisibility(View.VISIBLE);
                    textView17.setVisibility(View.VISIBLE);
                    edittext17.setVisibility(View.VISIBLE);
                    textView18.setVisibility(View.VISIBLE);
                    textView19.setVisibility(View.VISIBLE);
                    textView20.setVisibility(View.VISIBLE);
                    textView21.setVisibility(View.VISIBLE);
                    textView22.setVisibility(View.VISIBLE);
                    edittext18.setVisibility(View.VISIBLE);
                    textView23.setVisibility(View.VISIBLE);
                    spn2.setVisibility(View.VISIBLE);
                    textView24.setVisibility(View.VISIBLE);
                    g1.setVisibility(View.VISIBLE);
                    r1.setVisibility(View.VISIBLE);
                    r2.setVisibility(View.VISIBLE);
                    textView25.setVisibility(View.VISIBLE);
                    edittext25.setVisibility(View.VISIBLE);
                    textView26.setVisibility(View.VISIBLE);
                    edittext26.setVisibility(View.VISIBLE);
                    location1.setVisibility(View.VISIBLE);
                    save1.setVisibility(View.VISIBLE);

                }

            }
        });


        save1 = (Button) findViewById(R.id.btnSaveDB1);
        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickcount10 = clickcount10 + 1;
                if (clickcount10 % 2 == 1) {


                    textView14.setVisibility(View.GONE);
                    edittext14.setVisibility(View.GONE);
                    textView15.setVisibility(View.GONE);
                    edittext15.setVisibility(View.GONE);
                    textView16.setVisibility(View.GONE);
                    edittext16.setVisibility(View.GONE);
                    textView15.setVisibility(View.GONE);
                    edittext15.setVisibility(View.GONE);
                    textView16.setVisibility(View.GONE);
                    edittext16.setVisibility(View.GONE);
                    textView17.setVisibility(View.GONE);
                    edittext17.setVisibility(View.GONE);
                    textView18.setVisibility(View.GONE);
                    textView19.setVisibility(View.GONE);
                    textView20.setVisibility(View.GONE);
                    textView21.setVisibility(View.GONE);
                    textView22.setVisibility(View.GONE);
                    edittext18.setVisibility(View.GONE);
                    textView23.setVisibility(View.GONE);
                    spn2.setVisibility(View.GONE);
                    textView24.setVisibility(View.GONE);
                    g1.setVisibility(View.GONE);
                    r1.setVisibility(View.GONE);
                    r2.setVisibility(View.GONE);
                    textView25.setVisibility(View.GONE);
                    edittext25.setVisibility(View.GONE);
                    textView26.setVisibility(View.GONE);
                    edittext26.setVisibility(View.GONE);
                    location1.setVisibility(View.GONE);
                    save1.setVisibility(View.GONE);


                }
            }
        });



        // Bus Section

        textView27 = (TextView)findViewById(R.id.textAsset2);
        edittext27 = (EditText)findViewById(R.id.etAsset2);
        textView28 = (TextView)findViewById(R.id.textCapacity2);
        edittext28 = (EditText)findViewById(R.id.etCapacity2);
        textView29 = (TextView)findViewById(R.id.textCommissioned2);
        edittext29 = (EditText)findViewById(R.id.etCommissioned2);
        textView30 = (TextView)findViewById(R.id.textBrand2);
        edittext30 = (EditText)findViewById(R.id.etBrand2);
        textView31 = (TextView)findViewById(R.id.textMode2);
        edittext31 = (EditText)findViewById(R.id.etMode2);
        textView32 = (TextView)findViewById(R.id.textSite2);
        edittext32 = (EditText)findViewById(R.id.etSite2);
        textView33 = (TextView)findViewById(R.id.textViewLatitude2);
        textView34 = (TextView)findViewById(R.id.latitude2);
        textView35 = (TextView)findViewById(R.id.textViewLongitude2);
        textView36 = (TextView)findViewById(R.id.longitude2);
        textView37 = (TextView)findViewById(R.id.textCategory2);
        edittext33 = (EditText)findViewById(R.id.etCategory2);
        textView38 = (TextView)findViewById(R.id.textCondition2);
        spn3 = (Spinner) findViewById(R.id.spnCondition2);
//            textView39 = (TextView)findViewById(R.id.textBreaker1);
//            g2 = (RadioGroup)findViewById(R.id.rbBreaker1) ;
//            r3 = (RadioButton)findViewById(R.id.radioButtonO1);
//            r4 = (RadioButton)findViewById(R.id.radioButtonC1);
        textView40 = (TextView)findViewById(R.id.textAssociated4);
        edittext34 = (EditText)findViewById(R.id.etAssociated4);
        textView41 = (TextView)findViewById(R.id.textAssociated5);
        edittext35 = (EditText)findViewById(R.id.etAssociated5);
        textView42 = (TextView)findViewById(R.id.textAssociated6);
        edittext36 = (EditText)findViewById(R.id.etAssociated6);
        textView43 = (TextView)findViewById(R.id.textAssociated7);
        edittext37 = (EditText)findViewById(R.id.etAssociated7);
        textView44 = (TextView)findViewById(R.id.textAssociated8);
        edittext38 = (EditText)findViewById(R.id.etAssociated8);
        textView45 = (TextView)findViewById(R.id.textAssociated9);
        edittext39 = (EditText)findViewById(R.id.etAssociated9);
        location2 = (Button)findViewById(R.id.bLocation2) ;
        save2 = (Button)findViewById(R.id.btnSaveDB2);


        button3 = (Button)findViewById(R.id.btnSection);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickcount10=clickcount10+1;
                if (clickcount10%2==1) {

                    textView27.setVisibility(View.VISIBLE);
                    edittext27.setVisibility(View.VISIBLE);
                    textView28.setVisibility(View.VISIBLE);
                    edittext28.setVisibility(View.VISIBLE);
                    textView29.setVisibility(View.VISIBLE);
                    edittext29.setVisibility(View.VISIBLE);
                    textView30.setVisibility(View.VISIBLE);
                    edittext30.setVisibility(View.VISIBLE);
                    textView31.setVisibility(View.VISIBLE);
                    edittext31.setVisibility(View.VISIBLE);
                    textView32.setVisibility(View.VISIBLE);
                    edittext32.setVisibility(View.VISIBLE);
                    textView33.setVisibility(View.VISIBLE);
                    textView34.setVisibility(View.VISIBLE);
                    textView35.setVisibility(View.VISIBLE);
                    textView36.setVisibility(View.VISIBLE);
                    textView37.setVisibility(View.VISIBLE);
                    edittext33.setVisibility(View.VISIBLE);
                    textView38.setVisibility(View.VISIBLE);
                    spn3.setVisibility(View.VISIBLE);
//                        textView39.setVisibility(View.VISIBLE);
//                        g2.setVisibility(View.VISIBLE);
//                        r3.setVisibility(View.VISIBLE);
//                        r4.setVisibility(View.VISIBLE);
                    textView40.setVisibility(View.VISIBLE);
                    edittext34.setVisibility(View.VISIBLE);
                    textView41.setVisibility(View.VISIBLE);
                    edittext35.setVisibility(View.VISIBLE);
                    textView42.setVisibility(View.VISIBLE);
                    edittext36.setVisibility(View.VISIBLE);
                    textView43.setVisibility(View.VISIBLE);
                    edittext37.setVisibility(View.VISIBLE);
                    textView44.setVisibility(View.VISIBLE);
                    edittext38.setVisibility(View.VISIBLE);
                    textView45.setVisibility(View.VISIBLE);
                    edittext39.setVisibility(View.VISIBLE);
                    location2.setVisibility(View.VISIBLE);
                    save2.setVisibility(View.VISIBLE);

                }

            }
        });


        save2 = (Button) findViewById(R.id.btnSaveDB2);
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickcount10 = clickcount10 + 1;
                if (clickcount10 % 2 == 1) {


                    textView27.setVisibility(View.GONE);
                    edittext27.setVisibility(View.GONE);
                    textView28.setVisibility(View.GONE);
                    edittext28.setVisibility(View.GONE);
                    textView29.setVisibility(View.GONE);
                    edittext29.setVisibility(View.GONE);
                    textView30.setVisibility(View.GONE);
                    edittext30.setVisibility(View.GONE);
                    textView31.setVisibility(View.GONE);
                    edittext31.setVisibility(View.GONE);
                    textView32.setVisibility(View.GONE);
                    edittext32.setVisibility(View.GONE);
                    textView33.setVisibility(View.GONE);
                    textView34.setVisibility(View.GONE);
                    textView35.setVisibility(View.GONE);
                    textView36.setVisibility(View.GONE);
                    textView37.setVisibility(View.GONE);
                    edittext33.setVisibility(View.GONE);
                    textView38.setVisibility(View.GONE);
                    spn3.setVisibility(View.GONE );
//                        textView39.setVisibility(View.GONE);
//                        g2.setVisibility(View.GONE);
//                        r3.setVisibility(View.GONE);
//                        r4.setVisibility(View.GONE);
                    textView40.setVisibility(View.GONE);
                    edittext34.setVisibility(View.GONE);
                    textView41.setVisibility(View.GONE);
                    edittext35.setVisibility(View.GONE);
                    textView42.setVisibility(View.GONE);
                    edittext36.setVisibility(View.GONE);
                    textView43.setVisibility(View.GONE);
                    edittext37.setVisibility(View.GONE);
                    textView44.setVisibility(View.GONE);
                    edittext38.setVisibility(View.GONE);
                    textView45.setVisibility(View.GONE);
                    edittext39.setVisibility(View.GONE);
                    location2.setVisibility(View.GONE);
                    save2.setVisibility(View.GONE);

                }
            }
        });






        // Bus Coupler

        textView46 = (TextView)findViewById(R.id.textAsset3);
        edittext46 = (EditText)findViewById(R.id.etAsset3);
        textView47 = (TextView)findViewById(R.id.textCapacity3);
        edittext47 = (EditText)findViewById(R.id.etCapacity3);
        textView48 = (TextView)findViewById(R.id.textCommissioned3);
        edittext48 = (EditText)findViewById(R.id.etCommissioned3);
        textView49 = (TextView)findViewById(R.id.textBrand3);
        edittext49 = (EditText)findViewById(R.id.etBrand3);
        textView50 = (TextView)findViewById(R.id.textMode3);
        edittext50 = (EditText)findViewById(R.id.etMode3);
        textView51 = (TextView)findViewById(R.id.textSite3);
        edittext51 = (EditText)findViewById(R.id.etSite3);
        textView52 = (TextView)findViewById(R.id.textViewLatitude3);
        textView53 = (TextView)findViewById(R.id.latitude2);
        textView54 = (TextView)findViewById(R.id.textViewLongitude2);
        textView55 = (TextView)findViewById(R.id.longitude3);
        textView56 = (TextView)findViewById(R.id.textCategory3);
        edittext52 = (EditText)findViewById(R.id.etCategory3);
        textView58 = (TextView)findViewById(R.id.textCondition3);
        spn4 = (Spinner) findViewById(R.id.spnCondition3);
        textView59 = (TextView)findViewById(R.id.textBreaker3);
        g3 = (RadioGroup)findViewById(R.id.rbBreaker3) ;
        r5 = (RadioButton)findViewById(R.id.radioButtonO3);
        r6 = (RadioButton)findViewById(R.id.radioButtonC3);
        textView60 = (TextView)findViewById(R.id.textAssociated10);
        edittext53 = (EditText)findViewById(R.id.etAssociated10);
        textView61 = (TextView)findViewById(R.id.textAssociated11);
        edittext54 = (EditText)findViewById(R.id.etAssociated11);
        location3 = (Button)findViewById(R.id.bLocation3) ;
        save3 = (Button)findViewById(R.id.btnSaveDB3);


        button4 = (Button)findViewById(R.id.btnCoupler);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickcount10=clickcount10+1;
                if (clickcount10%2==1) {

                    textView46.setVisibility(View.VISIBLE);
                    edittext46.setVisibility(View.VISIBLE);
                    textView47.setVisibility(View.VISIBLE);
                    edittext47.setVisibility(View.VISIBLE);
                    textView48.setVisibility(View.VISIBLE);
                    edittext48.setVisibility(View.VISIBLE);
                    textView49.setVisibility(View.VISIBLE);
                    edittext49.setVisibility(View.VISIBLE);
                    textView50.setVisibility(View.VISIBLE);
                    edittext50.setVisibility(View.VISIBLE);
                    textView51.setVisibility(View.VISIBLE);
                    edittext51.setVisibility(View.VISIBLE);
                    textView52.setVisibility(View.VISIBLE);
                    textView53.setVisibility(View.VISIBLE);
                    textView54.setVisibility(View.VISIBLE);
                    textView55.setVisibility(View.VISIBLE);
                    textView56.setVisibility(View.VISIBLE);
                    edittext52.setVisibility(View.VISIBLE);
                    textView58.setVisibility(View.VISIBLE);
                    spn4.setVisibility(View.VISIBLE);
                    textView59.setVisibility(View.VISIBLE);
                    g3.setVisibility(View.VISIBLE);
                    r5.setVisibility(View.VISIBLE);
                    r6.setVisibility(View.VISIBLE);
                    textView60.setVisibility(View.VISIBLE);
                    edittext53.setVisibility(View.VISIBLE);
                    textView61.setVisibility(View.VISIBLE);
                    edittext54.setVisibility(View.VISIBLE);
                    location3.setVisibility(View.VISIBLE);
                    save3.setVisibility(View.VISIBLE);

                }

            }
        });


        save3 = (Button)findViewById(R.id.btnSaveDB3);
        save3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickcount10=clickcount10+1;
                if (clickcount10%2==1) {

                    textView46.setVisibility(View.GONE);
                    edittext46.setVisibility(View.GONE);
                    textView47.setVisibility(View.GONE);
                    edittext47.setVisibility(View.GONE);
                    textView48.setVisibility(View.GONE);
                    edittext48.setVisibility(View.GONE);
                    textView49.setVisibility(View.GONE);
                    edittext49.setVisibility(View.GONE);
                    textView50.setVisibility(View.GONE);
                    edittext50.setVisibility(View.GONE);
                    textView51.setVisibility(View.GONE);
                    edittext51.setVisibility(View.GONE);
                    textView52.setVisibility(View.GONE);
                    textView53.setVisibility(View.GONE);
                    textView54.setVisibility(View.GONE);
                    textView55.setVisibility(View.GONE);
                    textView56.setVisibility(View.GONE);
                    edittext52.setVisibility(View.GONE);
                    textView58.setVisibility(View.GONE);
                    spn4.setVisibility(View.GONE);
                    textView59.setVisibility(View.GONE);
                    g3.setVisibility(View.GONE);
                    r5.setVisibility(View.GONE);
                    r6.setVisibility(View.GONE);
                    textView60.setVisibility(View.GONE);
                    edittext53.setVisibility(View.GONE);
                    textView61.setVisibility(View.GONE);
                    edittext54.setVisibility(View.GONE);
                    location3.setVisibility(View.GONE);
                    save3.setVisibility(View.GONE);

                }

            }
        });



        // Circuit switch

        textView62 = (TextView)findViewById(R.id.textAsset4);
        edittext62 = (EditText)findViewById(R.id.etAsset4);
        textView63 = (TextView)findViewById(R.id.textCapacity4);
        edittext63 = (EditText)findViewById(R.id.etCapacity4);
        textView64 = (TextView)findViewById(R.id.textCommissioned4);
        edittext64 = (EditText)findViewById(R.id.etCommissioned4);
        textView65 = (TextView)findViewById(R.id.textBrand4);
        edittext65 = (EditText)findViewById(R.id.etBrand4);
        textView66 = (TextView)findViewById(R.id.textMode4);
        edittext66 = (EditText)findViewById(R.id.etMode4);
        textView67 = (TextView)findViewById(R.id.textSite4);
        edittext68 = (EditText)findViewById(R.id.etSite4);
        textView69 = (TextView)findViewById(R.id.textViewLatitude4);
        textView70 = (TextView)findViewById(R.id.latitude4);
        textView71 = (TextView)findViewById(R.id.textViewLongitude4);
        textView72 = (TextView)findViewById(R.id.longitude4);
        textView73 = (TextView)findViewById(R.id.textCategory4);
        edittext69 = (EditText)findViewById(R.id.etCategory4);
        textView74 = (TextView)findViewById(R.id.textCondition4);
        spn5 = (Spinner) findViewById(R.id.spnCondition4);
        textView75 = (TextView)findViewById(R.id.textBreaker4);
        g4 = (RadioGroup)findViewById(R.id.rbBreaker4) ;
        r7 = (RadioButton)findViewById(R.id.radioButtonO4);
        r8 = (RadioButton)findViewById(R.id.radioButtonC4);
        textView76 = (TextView)findViewById(R.id.textAssociated12);
        edittext70 = (EditText)findViewById(R.id.etAssociated12);
        location4 = (Button)findViewById(R.id.bLocation4) ;
        save4 = (Button)findViewById(R.id.btnSaveDB4);


        button5 = (Button)findViewById(R.id.btnCircuit);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickcount10 = clickcount10 + 1;
                if (clickcount10 % 2 == 1) {

                    textView62.setVisibility(View.VISIBLE);
                    edittext62.setVisibility(View.VISIBLE);
                    textView63.setVisibility(View.VISIBLE);
                    edittext63.setVisibility(View.VISIBLE);
                    textView64.setVisibility(View.VISIBLE);
                    edittext64.setVisibility(View.VISIBLE);
                    textView65.setVisibility(View.VISIBLE);
                    edittext65.setVisibility(View.VISIBLE);
                    textView66.setVisibility(View.VISIBLE);
                    edittext66.setVisibility(View.VISIBLE);
                    textView67.setVisibility(View.VISIBLE);
                    edittext68.setVisibility(View.VISIBLE);
                    textView69.setVisibility(View.VISIBLE);
                    textView70.setVisibility(View.VISIBLE);
                    textView71.setVisibility(View.VISIBLE);
                    textView72.setVisibility(View.VISIBLE);
                    textView73.setVisibility(View.VISIBLE);
                    edittext69.setVisibility(View.VISIBLE);
                    textView74.setVisibility(View.VISIBLE);
                    spn5.setVisibility(View.VISIBLE);
                    textView75.setVisibility(View.VISIBLE);
                    g4.setVisibility(View.VISIBLE);
                    r7.setVisibility(View.VISIBLE);
                    r8.setVisibility(View.VISIBLE);
                    textView76.setVisibility(View.VISIBLE);
                    edittext70.setVisibility(View.VISIBLE);
                    location4.setVisibility(View.VISIBLE);
                    save4.setVisibility(View.VISIBLE);


                }
            }
        });

        save4 = (Button)findViewById(R.id.btnSaveDB4);
        save4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickcount10 = clickcount10 + 1;
                if (clickcount10 % 2 == 1) {

                    textView62.setVisibility(View.GONE);
                    edittext62.setVisibility(View.GONE);
                    textView63.setVisibility(View.GONE);
                    edittext63.setVisibility(View.GONE);
                    textView64.setVisibility(View.GONE);
                    edittext64.setVisibility(View.GONE);
                    textView65.setVisibility(View.GONE);
                    edittext65.setVisibility(View.GONE);
                    textView66.setVisibility(View.GONE);
                    edittext66.setVisibility(View.GONE);
                    textView67.setVisibility(View.GONE);
                    edittext68.setVisibility(View.GONE);
                    textView69.setVisibility(View.GONE);
                    textView70.setVisibility(View.GONE);
                    textView71.setVisibility(View.GONE);
                    textView72.setVisibility(View.GONE);
                    textView73.setVisibility(View.GONE);
                    edittext69.setVisibility(View.GONE);
                    textView74.setVisibility(View.GONE);
                    spn5.setVisibility(View.GONE);
                    textView75.setVisibility(View.GONE);
                    g4.setVisibility(View.GONE);
                    r7.setVisibility(View.GONE);
                    r8.setVisibility(View.GONE);
                    textView76.setVisibility(View.GONE);
                    edittext70.setVisibility(View.GONE);
                    location4.setVisibility(View.GONE);
                    save4.setVisibility(View.GONE);


                }
            }
        });


        // Earth Switch


        textView77 = (TextView)findViewById(R.id.textAsset5);
        edittext77 = (EditText)findViewById(R.id.etAsset5);
        textView78 = (TextView)findViewById(R.id.textCapacity5);
        edittext78 = (EditText)findViewById(R.id.etCapacity5);
        textView79 = (TextView)findViewById(R.id.textCommissioned5);
        edittext79 = (EditText)findViewById(R.id.etCommissioned5);
        textView80 = (TextView)findViewById(R.id.textBrand5);
        edittext80 = (EditText)findViewById(R.id.etBrand5);
        textView81 = (TextView)findViewById(R.id.textModel5);
        edittext81 = (EditText)findViewById(R.id.etModel5);
        textView82 = (TextView)findViewById(R.id.textSite5);
        edittext82 = (EditText)findViewById(R.id.etSite5);
        textView83 = (TextView)findViewById(R.id.textViewLatitude5);
        textView84 = (TextView)findViewById(R.id.latitude5);
        textView85 = (TextView)findViewById(R.id.textViewLongitude5);
        textView86 = (TextView)findViewById(R.id.longitude5);
        textView87 = (TextView)findViewById(R.id.textCategory5);
        edittext83 = (EditText)findViewById(R.id.etCategory5);
        textView88 = (TextView)findViewById(R.id.textCondition5);
        spn6 = (Spinner) findViewById(R.id.spnCondition5);
        textView89 = (TextView)findViewById(R.id.textBreaker5);
        g5 = (RadioGroup)findViewById(R.id.rbBreaker5) ;
        r9 = (RadioButton)findViewById(R.id.radioButtonO5);
        r10 = (RadioButton)findViewById(R.id.radioButtonC5);
        textView90 = (TextView)findViewById(R.id.textAssociated13);
        edittext84 = (EditText)findViewById(R.id.etAssociated13);
        location5 = (Button)findViewById(R.id.bLocation5) ;
        save5 = (Button)findViewById(R.id.btnSaveDB5);


        button6 = (Button)findViewById(R.id.btnEarth);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickcount10 = clickcount10 + 1;
                if (clickcount10 % 2 == 1) {

                    textView77.setVisibility(View.VISIBLE);
                    edittext77.setVisibility(View.VISIBLE);
                    textView78.setVisibility(View.VISIBLE);
                    edittext78.setVisibility(View.VISIBLE);
                    textView79.setVisibility(View.VISIBLE);
                    edittext79.setVisibility(View.VISIBLE);
                    textView80.setVisibility(View.VISIBLE);
                    edittext80.setVisibility(View.VISIBLE);
                    textView81.setVisibility(View.VISIBLE);
                    edittext81.setVisibility(View.VISIBLE);
                    textView82.setVisibility(View.VISIBLE);
                    edittext82.setVisibility(View.VISIBLE);
                    textView83.setVisibility(View.VISIBLE);
                    textView84.setVisibility(View.VISIBLE);
                    textView85.setVisibility(View.VISIBLE);
                    textView86.setVisibility(View.VISIBLE);
                    textView87.setVisibility(View.VISIBLE);
                    edittext83.setVisibility(View.VISIBLE);
                    textView88.setVisibility(View.VISIBLE);
                    spn6.setVisibility(View.VISIBLE);
                    textView89.setVisibility(View.VISIBLE);
                    g5.setVisibility(View.VISIBLE);
                    r9.setVisibility(View.VISIBLE);
                    r10.setVisibility(View.VISIBLE);
                    textView90.setVisibility(View.VISIBLE);
                    edittext84.setVisibility(View.VISIBLE);
                    location5.setVisibility(View.VISIBLE);
                    save5.setVisibility(View.VISIBLE);
                }
            }
        });

        save5 = (Button)findViewById(R.id.btnSaveDB5);
        save5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickcount10 = clickcount10 + 1;
                if (clickcount10 % 2 == 1) {

                    textView77.setVisibility(View.GONE);
                    edittext77.setVisibility(View.GONE);
                    textView78.setVisibility(View.GONE);
                    edittext78.setVisibility(View.GONE);
                    textView79.setVisibility(View.GONE);
                    edittext79.setVisibility(View.GONE);
                    textView80.setVisibility(View.GONE);
                    edittext80.setVisibility(View.GONE);
                    textView81.setVisibility(View.GONE);
                    edittext81.setVisibility(View.GONE);
                    textView82.setVisibility(View.GONE);
                    edittext82.setVisibility(View.GONE);
                    textView83.setVisibility(View.GONE);
                    textView84.setVisibility(View.GONE);
                    textView85.setVisibility(View.GONE);
                    textView86.setVisibility(View.GONE);
                    textView87.setVisibility(View.GONE);
                    edittext83.setVisibility(View.GONE);
                    textView88.setVisibility(View.GONE);
                    spn6.setVisibility(View.GONE);
                    textView89.setVisibility(View.GONE);
                    g5.setVisibility(View.GONE);
                    r9.setVisibility(View.GONE);
                    r10.setVisibility(View.GONE);
                    textView90.setVisibility(View.GONE);
                    edittext84.setVisibility(View.GONE);
                    location5.setVisibility(View.GONE);
                    save5.setVisibility(View.GONE);
                }
            }
        });


        // Isolator Switch

        textView92 = (TextView)findViewById(R.id.textAsset6);
        edittext92 = (EditText)findViewById(R.id.etAsset6);
        textView93 = (TextView)findViewById(R.id.textCapacity6);
        edittext93 = (EditText)findViewById(R.id.etCapacity6);
        textView94 = (TextView)findViewById(R.id.textCommissioned6);
        edittext94 = (EditText)findViewById(R.id.etCommissioned6);
        textView95 = (TextView)findViewById(R.id.textBrand6);
        edittext95 = (EditText)findViewById(R.id.etBrand6);
        textView96 = (TextView)findViewById(R.id.textModel6);
        edittext96 = (EditText)findViewById(R.id.etModel6);
        textView97 = (TextView)findViewById(R.id.textSite6);
        edittext97 = (EditText)findViewById(R.id.etSite6);
        textView98 = (TextView)findViewById(R.id.textViewLatitude6);
        textView99 = (TextView)findViewById(R.id.latitude6);
        textView100 = (TextView)findViewById(R.id.textViewLongitude6);
        textView101= (TextView)findViewById(R.id.longitude6);
        textView102 = (TextView)findViewById(R.id.textCategory6);
        edittext98 = (EditText)findViewById(R.id.etCategory6);
        textView103 = (TextView)findViewById(R.id.textCondition6);
        spn7 = (Spinner) findViewById(R.id.spnCondition6);
        textView104 = (TextView)findViewById(R.id.textBreaker6);
        g6 = (RadioGroup)findViewById(R.id.rbBreaker6) ;
        r11 = (RadioButton)findViewById(R.id.radioButtonO6);
        r12 = (RadioButton)findViewById(R.id.radioButtonC6);
        textView105 = (TextView)findViewById(R.id.textAssociated14);
        edittext99 = (EditText)findViewById(R.id.etAssociated14);
        location = (Button)findViewById(R.id.bLocation6) ;
        save6 = (Button)findViewById(R.id.btnSaveDB6);


        button7 = (Button)findViewById(R.id.btnIsolator);
        button7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clickcount10 = clickcount10 + 1;
                if (clickcount10 % 2 == 1) {

                    textView92.setVisibility(View.VISIBLE);
                    edittext92.setVisibility(View.VISIBLE);
                    textView93.setVisibility(View.VISIBLE);
                    edittext93.setVisibility(View.VISIBLE);
                    textView94.setVisibility(View.VISIBLE);
                    edittext94.setVisibility(View.VISIBLE);
                    textView95.setVisibility(View.VISIBLE);
                    edittext95.setVisibility(View.VISIBLE);
                    textView96.setVisibility(View.VISIBLE);
                    edittext96.setVisibility(View.VISIBLE);
                    textView97.setVisibility(View.VISIBLE);
                    edittext97.setVisibility(View.VISIBLE);
                    textView98.setVisibility(View.VISIBLE);
                    textView99.setVisibility(View.VISIBLE);
                    textView100.setVisibility(View.VISIBLE);
                    textView101.setVisibility(View.VISIBLE);
                    textView102.setVisibility(View.VISIBLE);
                    edittext98.setVisibility(View.VISIBLE);
                    textView103.setVisibility(View.VISIBLE);
                    spn7.setVisibility(View.VISIBLE);
                    textView104.setVisibility(View.VISIBLE);
                    g6.setVisibility(View.VISIBLE);
                    r11.setVisibility(View.VISIBLE);
                    r12.setVisibility(View.VISIBLE);
                    textView105.setVisibility(View.VISIBLE);
                    edittext99.setVisibility(View.VISIBLE);
                    location.setVisibility(View.VISIBLE);
                    save6.setVisibility(View.VISIBLE);
                }
            }
        });


        save6 = (Button)findViewById(R.id.btnSaveDB6);
        save6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clickcount10 = clickcount10 + 1;
                if (clickcount10 % 2 == 1) {

                    textView92.setVisibility(View.GONE);
                    edittext92.setVisibility(View.GONE);
                    textView93.setVisibility(View.GONE);
                    edittext93.setVisibility(View.GONE);
                    textView94.setVisibility(View.GONE);
                    edittext94.setVisibility(View.GONE);
                    textView95.setVisibility(View.GONE);
                    edittext95.setVisibility(View.GONE);
                    textView96.setVisibility(View.GONE);
                    edittext96.setVisibility(View.GONE);
                    textView97.setVisibility(View.GONE);
                    edittext97.setVisibility(View.GONE);
                    textView98.setVisibility(View.GONE);
                    textView99.setVisibility(View.GONE);
                    textView100.setVisibility(View.GONE);
                    textView101.setVisibility(View.GONE);
                    textView102.setVisibility(View.GONE);
                    edittext98.setVisibility(View.GONE);
                    textView103.setVisibility(View.GONE);
                    spn7.setVisibility(View.GONE);
                    textView104.setVisibility(View.GONE);
                    g6.setVisibility(View.GONE);
                    r11.setVisibility(View.GONE);
                    r12.setVisibility(View.GONE);
                    textView105.setVisibility(View.GONE);
                    edittext99.setVisibility(View.GONE);
                    location.setVisibility(View.GONE);
                    save6.setVisibility(View.GONE);
                }
            }
        });





















//        voltage_level =(Spinner)findViewById(R.id.voltage_level);
//        adapter1 =ArrayAdapter.createFromResource(this,R.array.voltage_level,android.R.layout.simple_spinner_item);
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        voltage_level.setAdapter(adapter1);

//            gantry_type =(Spinner)findViewById(R.id.gantry_type);
//            adapter2 =ArrayAdapter.createFromResource(this,R.array.gantry_type,android.R.layout.simple_spinner_item);
//            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            gantry_type.setAdapter(adapter2);

//        construction_type =(Spinner)findViewById(R.id.construction_type);
//        adapter3 =ArrayAdapter.createFromResource(this,R.array.construction_type,android.R.layout.simple_spinner_item);
//        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        construction_type.setAdapter(adapter3);

//            gantry_electrcial_type =(Spinner)findViewById(R.id.electrcial_gantry_type);
//            adapter4 =ArrayAdapter.createFromResource(this,R.array.gantry_electrcial_type,android.R.layout.simple_spinner_item);
//            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            gantry_electrcial_type.setAdapter(adapter4);

//        bus_bar_conductor =(Spinner)findViewById(R.id.bus_bar_conductor);
//        adapter5 =ArrayAdapter.createFromResource(this,R.array.bus_bar_conductor,android.R.layout.simple_spinner_item);
//        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        bus_bar_conductor.setAdapter(adapter5);

//        bus_bar_insulator =(Spinner)findViewById(R.id.bus_bar_insulator);
//        adapter6 =ArrayAdapter.createFromResource(this,R.array.bus_bar_insulator,android.R.layout.simple_spinner_item);
//        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        bus_bar_insulator.setAdapter(adapter6);

//        auxillary_system =(Spinner)findViewById(R.id.auxillary_system);
//        adapter7 =ArrayAdapter.createFromResource(this,R.array.auxillary_system,android.R.layout.simple_spinner_item);
//        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        auxillary_system.setAdapter(adapter7);

//        overhead_earthing =(Spinner)findViewById(R.id.overhead_earthing);
//        adapter8 =ArrayAdapter.createFromResource(this,R.array.overhead_earthing,android.R.layout.simple_spinner_item);
//        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        overhead_earthing.setAdapter(adapter8);


        if (!isConnected(com.example.akla.login.AddGSS.this)) {
            readExcelArea();
            readExcelProvince();
            readExcelCsc();
        } else {
            new com.example.akla.login.AddGSS.loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Area -- Area load by Province
        SpinnerProvince = findViewById(R.id.spnProvince);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province =idprovince;

                if(!Util.isConnected(com.example.akla.login.AddGSS.this)){

                    Toast.makeText(getApplication(),"readExcel: Area " , Toast.LENGTH_SHORT).show();

                }else {
                    new com.example.akla.login.AddGSS.loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Line -- Line load by Area
        SpinnerArea = findViewById(R.id.spnArea);
        SpinnerArea.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idarea = SpinnerMapArea.get(parent.getSelectedItemPosition());
                area = idarea;

                if(!Util.isConnected(com.example.akla.login.AddGSS.this)){

                    Toast.makeText(getApplication(),"readExcel: Line" , Toast.LENGTH_SHORT).show();

                }else {
                    //new AddGantry.loadLineByArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new com.example.akla.login.AddGSS.loadCscbyAea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Get Item selected from Line
//        SpinnerLine = findViewById(R.id.spnLine);
//        SpinnerLine.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String selectedItem = adapterView.getSelectedItem().toString();
//                String name = adapterView.getSelectedItem().toString();
//                String idLine = SpinnerMapLine.get(adapterView.getSelectedItemPosition());
//                line = idLine;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        // Get Item selected from CSC
        SpinnerCSC = findViewById(R.id.spnCsc);
        SpinnerCSC.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getSelectedItem().toString();
                String name = adapterView.getSelectedItem().toString();
                String idCsc = SpinnerMapCsc.get(adapterView.getSelectedItemPosition());
                csc = idCsc;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//            btn_date = (Button)findViewById(R.id.btn_date);
//            in_date = (EditText) findViewById(R.id.in_date);
//            btn_date.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (view == btn_date) {
//
//                        // Get Current Date
//                        final Calendar c = Calendar.getInstance();
//                        mYear = c.get(Calendar.YEAR);
//                        mMonth = c.get(Calendar.MONTH);
//                        mDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//                        DatePickerDialog datePickerDialog = new DatePickerDialog(com.example.akla.login.Add_GSS.this,
//                                new DatePickerDialog.OnDateSetListener() {
//
//                                    @Override
//                                    public void onDateSet(DatePicker view, int year,
//                                                          int monthOfYear, int dayOfMonth) {
//
//                                        in_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                                    }
//                                }, mYear, mMonth, mDay);
//                        datePickerDialog.show();
//                    }
//                }
//            });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //////////////////////// R O W D Y //////////////// G P S Location //////////////////////////////////////
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

        // P O I N T - 1
        Button button = findViewById(R.id.bLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(com.example.akla.login.AddGSS.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(com.example.akla.login.AddGSS.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){

                            TextView lati = findViewById(R.id.latitude);
                            lati.setText(String.valueOf((double) location.getLatitude()));
                            latitude = String.valueOf((double) location.getLatitude());

                            TextView longi = findViewById(R.id.longitude);
                            longi.setText(String.valueOf((double) location.getLongitude()));
                            longitude = String.valueOf((double) location.getLongitude());
                        }
                    }
                });
            }
        });


        /////////////////////////Save to Database ////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////// Edit by Dinith 2019 - 10 - 24 ///////////////////////////////////////////////////////////////////////////////



        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    //////////////////////////////////////// AddGantryDB  Edit Dinith////////////////////////////////////////////////////////////////

    private class AddGantryDB extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... urls) {

            String name = Name.getText().toString();
            String code = Code.getText().toString();
            String short_circuit = ShortCircuitCurrentCapacity.getText().toString();
            String earth_fault = EarthFaultCurrentCapacity.getText().toString();

            String comdate = ConssionDate.getText().toString();

            int type;
            if(Type.getSelectedItem().toString() == "Pole"){
                type = 1;
            }else{
                type = 2;
            }

            int elec_type;
            if(ElectricalType.getSelectedItem() == "SBB"){
                elec_type = 1;
            }else{
                elec_type =2;
            }

            String bus_bars = NoOfBusBars.getText().toString();
            String bus_sections = NoOfBusSections.getText().toString();
            String in_bays = NoOfInBays.getText().toString();
            String out_bays = NoOfOutBays.getText().toString();
            String land_area = TotalLandArea.getText().toString();
            String auto_reclosers = NoOfAutoRecloser.getText().toString();
            String lbs = NoOfLBS.getText().toString();
            String abs = NoOfABS.getText().toString();
            String surge_arrestors = NoOfSurgeArrestors.getText().toString();
            String ddlo_links = NoOfDddloLinks.getText().toString();
            String ddlo_fuses = NoOfDdloFuses.getText().toString();
            String in_feeders = NoOfInFeeders.getText().toString();
            String out_feeders = NoOfOutFeeders.getText().toString();

            System.out.println("############################### Test Intialized variable ##############################################");

            System.out.println("Province A:" + province);
            System.out.println("Area B: "+area);
            System.out.println("CSC C: "+csc);
            //System.out.println("Line: "+line);
            System.out.println("Name:"+name);
            System.out.println("Code:"+code);
            System.out.println("Short circuit: "+short_circuit);
            System.out.println("Earth Fault: "+earth_fault);
            System.out.println("Com Date: "+comdate);
            System.out.println("Type : "+type);
            System.out.println("Electrical Type : "+elec_type);
            System.out.println("Bus Bars : "+bus_bars);
            System.out.println("Bus Sections : "+bus_sections);
            System.out.println("In bays : "+in_bays);
            System.out.println("Out bays : "+out_bays);
            System.out.println("Land area : "+land_area);
            System.out.println("Auto recloser : "+auto_reclosers);
            System.out.println("LBS : "+lbs);
            System.out.println("ABS : "+abs);
            System.out.println("Surge Arrestors : "+surge_arrestors);
            System.out.println("DDLO Links : "+ddlo_links);
            System.out.println("DDLO Fuses : "+ddlo_fuses);
            System.out.println("In Feeders : "+in_feeders);
            System.out.println("Out Feeders : "+out_feeders);

            //set values to MmsAddgantry object
            MmsAddgantry objAddgantry = new MmsAddgantry();

            objAddgantry.setPhmBranch(phmBranch);

            objAddgantry.setProvince(province);
            objAddgantry.setArea(area);
            objAddgantry.setCsc(csc);
            //objAddgantry.setLineId(new BigDecimal(line));
            objAddgantry.setName(name);
            objAddgantry.setCode(code);

            if(short_circuit.length()==0){
                objAddgantry.setShortCctCurntCapacity(new BigDecimal(0));
            }else{
                objAddgantry.setShortCctCurntCapacity(new BigDecimal(short_circuit));
            }

            if(earth_fault.length()==0){
                objAddgantry.setEarthFaultCurntCapacity(new BigDecimal(0));
            }else{
                objAddgantry.setEarthFaultCurntCapacity(new BigDecimal(earth_fault));
            }

//            objAddgantry.setDateOfComm(date);
            Date dateNow = null;
            try {
                dateNow = new SimpleDateFormat("yyyy-MM-dd").parse(comdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objAddgantry.setDateOfComm(dateNow);

            objAddgantry.setGantryType(new BigDecimal(type));
            objAddgantry.setGantryEleType(new BigDecimal(elec_type));

            if(bus_bars.length()==0){
                objAddgantry.setNoBusBar(new BigDecimal(0));
            }else{
                objAddgantry.setNoBusBar(new BigDecimal(bus_bars));
            }

            if(bus_sections.length()==0){
                objAddgantry.setNoBusSec(new BigDecimal(0));
            }else{
                objAddgantry.setNoBusSec(new BigDecimal(bus_sections));
            }

            if(in_bays.length()==0){
                objAddgantry.setNoInBays(new BigDecimal(0));
            }else{
                objAddgantry.setNoInBays(new BigDecimal(in_bays));
            }

            if(out_bays.length()==0){
                objAddgantry.setNoOutBays(new BigDecimal(0));
            }else{
                objAddgantry.setNoOutBays(new BigDecimal(out_bays));
            }

            if(land_area.length()==0){
                objAddgantry.setTotalLandArea(new BigDecimal(0));
            }else{
                objAddgantry.setTotalLandArea(new BigDecimal(land_area));
            }

            if(auto_reclosers.length()==0){
                objAddgantry.setNoAr(new BigDecimal(0));
            }else{
                objAddgantry.setNoAr(new BigDecimal(auto_reclosers));
            }

            if(lbs.length()==0){
                objAddgantry.setNoLbs(new BigDecimal(0));
            }else{
                objAddgantry.setNoLbs(new BigDecimal(lbs));
            }

            if(abs.length()==0){
                objAddgantry.setNoAbs(new BigDecimal(0));
            }else{
                objAddgantry.setNoAbs(new BigDecimal(abs));
            }

            if(surge_arrestors.length()==0){
                objAddgantry.setNoSa(new BigDecimal(0));
            }else{
                objAddgantry.setNoSa(new BigDecimal(surge_arrestors));
            }

            if(ddlo_links.length()==0){
                objAddgantry.setNoDdloLinks(new BigDecimal(0));
            }else{
                objAddgantry.setNoDdloLinks(new BigDecimal(ddlo_links));
            }

            if(ddlo_fuses.length()==0){
                objAddgantry.setNoDdloFuses(new BigDecimal(0));
            }else{
                objAddgantry.setNoDdloFuses(new BigDecimal(ddlo_fuses));
            }

            if(in_feeders.length()==0){
                objAddgantry.setNoIncomingFeeder(new BigDecimal(0));
            }else{
                objAddgantry.setNoIncomingFeeder(new BigDecimal(in_feeders));
            }

            if(out_feeders.length()==0){
                objAddgantry.setNoOutgoingFeeder(new BigDecimal(0));
            }else{
                objAddgantry.setNoOutgoingFeeder(new BigDecimal(out_feeders));
            }

            if(latitude==null){
                objAddgantry.setGpsLatitude(new BigDecimal(0));
            }else{
                objAddgantry.setGpsLatitude(new BigDecimal(latitude));
            }

            if(longitude==null){
                objAddgantry.setGpsLongitude(new BigDecimal(0));
            }else{
                objAddgantry.setGpsLongitude(new BigDecimal(longitude));
            }


            System.out.println("Set object testing :****************************************");
            System.out.println("PHM Branch: "+objAddgantry.getPhmBranch());
            System.out.println("Province :" + objAddgantry.getProvince());
            System.out.println("Area :" + objAddgantry.getArea());
            System.out.println("CSC :"+objAddgantry.getCsc());
            //System.out.println("Line :"+objAddgantry.getLineId());
            System.out.println("Name :" + objAddgantry.getName());
            System.out.println("Code :" + objAddgantry.getCode());
            System.out.println("Short Circuit :"+objAddgantry.getShortCctCurntCapacity());
            System.out.println("Earth Fault :"+objAddgantry.getEarthFaultCurntCapacity());

            System.out.println("Date"+objAddgantry.getDateOfComm());

            System.out.println("Gantry Type :" + objAddgantry.getGantryType());
            System.out.println("G Elec Type :" + objAddgantry.getGantryEleType());
            System.out.println("No Bus bars :" + objAddgantry.getNoBusBar());
            System.out.println("no of bus Secs :" + objAddgantry.getNoBusSec());
            System.out.println("In Bays :" + objAddgantry.getNoInBays());
            System.out.println("Out Bays :" + objAddgantry.getNoOutBays());
            System.out.println("Land Area :" + objAddgantry.getTotalLandArea());
            System.out.println("Auto Recloser :"+objAddgantry.getNoAr());
            System.out.println("LBS :"+objAddgantry.getNoLbs());
            System.out.println("ABS :"+objAddgantry.getNoAbs());
            System.out.println("Surge Arrestors :"+objAddgantry.getNoSa());
            System.out.println("DDLO Links :"+objAddgantry.getNoDdloLinks());
            System.out.println("DDLO Fuses:"+objAddgantry.getNoDdloFuses());
            System.out.println("In feeders :" + objAddgantry.getNoIncomingFeeder());
            System.out.println("Out Feeders :" + objAddgantry.getNoOutgoingFeeder());

            System.out.println("Latitude :" + objAddgantry.getGpsLatitude());
            System.out.println("Longitude :" + objAddgantry.getGpsLongitude());

            final RestTemplate restTemplate = new RestTemplate();
            final String url1 = Util.SRT_URL + "MMSAddGantryMobile/";
            System.out.println(" url1 " + url1);
            // trustEveryone();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            //return restTemplate.getForObject(url1, String.class);
            String objReturn = restTemplate.postForObject(url1, objAddgantry, String.class);
            System.out.println(" objReturn: " + objReturn );
            return objReturn;

        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_gantry, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.apphome) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, GetNearByTower.class);
            startActivity(intent);

            //////////////////////////////// PHM - LCM ////////////////////////////////////////////

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, TM2.class);
            startActivity(intent);

            //////////////////////////////// PHM - SUb ////////////////////////////////////////////

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, com.example.akla.login.AddGSS.class);
            startActivity(intent);
        } else if (id == R.id.nav_editGantry) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, EditGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addBusBar) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, AddBusBar.class);
            startActivity(intent);

        } else if (id == R.id.nav_addStructual) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, AddStructural.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantryMore) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, AddGantryMore.class);
            startActivity(intent);

        } else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSwitches) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, AddSwitches.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSurge) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, AddSurgeArrestors.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTransformersG) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, AddTransformersG.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, AddEquipment.class);
            startActivity(intent);

///////////////////// POLE DETAILS //////////////////////////////////////////////

        } else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, AddTransformers.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(com.example.akla.login.AddGSS.this, Login.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    ////////////Offline spinner loading\\\\\\\\\\\ 2019 12 17////////////////////

    // Spiner Area
    public List<String> readExcelProvince() {

        List<String> resultSet = new ArrayList<String>();
        File inputWorkbook = new File(Environment.getExternalStorageDirectory() + File.separator + "Schedule" + File.separator + "Province.xls");

        if (inputWorkbook.exists()) {

            Workbook w;
            try {

                w = Workbook.getWorkbook(inputWorkbook);

                Sheet sheet = w.getSheet(0);

                // Loop over column and lines
                int coloumn = sheet.getRows();
                values1 = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);

                    spinnerMap1.put(j, cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    values1[j] = cell1.getContents();

                    continue;
                }

                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values1);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerProvince = findViewById(R.id.spnProvince);
                SpinnerProvince.setAdapter(adapterNs);

            } catch (BiffException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            resultSet.add("File not found..!");
        }
        if (resultSet.size() == 0) {
            resultSet.add("Data not found..!");
        }
        return resultSet;
    }

    // Spiner Area
    public List<String> readExcelArea() {

        List<String> resultSet = new ArrayList<String>();
        File inputWorkbook = new File(Environment.getExternalStorageDirectory() + File.separator + "Schedule" + File.separator + "Area.xls");

        if (inputWorkbook.exists()) {

            Workbook w;
            try {

                w = Workbook.getWorkbook(inputWorkbook);

                Sheet sheet = w.getSheet(0);

                // Loop over column and lines
                int coloumn = sheet.getRows();
                values2 = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);

                    spinnerMap2.put(j, cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    values2[j] = cell1.getContents();

                    continue;
                }

                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values2);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerArea = findViewById(R.id.spnArea);
                SpinnerArea.setAdapter(adapterNs);

            } catch (BiffException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            resultSet.add("File not found..!");
        }
        if (resultSet.size() == 0) {
            resultSet.add("Data not found..!");
        }
        return resultSet;
    }

    // Spiner Csc
    public List<String> readExcelCsc() {

        List<String> resultSet = new ArrayList<String>();
        File inputWorkbook = new File(Environment.getExternalStorageDirectory() + File.separator + "Schedule" + File.separator + "Csc.xls");

        if (inputWorkbook.exists()) {

            Workbook w;
            try {

                w = Workbook.getWorkbook(inputWorkbook);

                Sheet sheet = w.getSheet(0);

                // Loop over column and lines
                int coloumn = sheet.getRows();
                values3 = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);

                    spinnerMap3.put(j, cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    values3[j] = cell1.getContents();

                    continue;
                }

                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values3);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerCSC = findViewById(R.id.spnCsc);
                SpinnerCSC.setAdapter(adapterNs);

            } catch (BiffException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            resultSet.add("File not found..!");
        }
        if (resultSet.size() == 0) {
            resultSet.add("Data not found..!");
        }
        return resultSet;
    }

    ///////////////////// Create Excel for off line time //////////////////////////////////////
//    public void createExcel() {
//
//        System.out.println("createExcel1");
//
//        String name = Name.getText().toString();
//        String code = Code.getText().toString();
//        String short_circuit = ShortCircuitCurrentCapacity.getText().toString();
//        String earth_fault = EarthFaultCurrentCapacity.getText().toString();
//
//        String comdate = ConssionDate.getText().toString();
//
//        int type;
//        if(Type.getSelectedItem().toString() == "Pole"){
//            type = 1;
//        }else{
//            type = 2;
//        }
//
//        int elec_type;
//        if(ElectricalType.getSelectedItem() == "SBB"){
//            elec_type = 1;
//        }else{
//            elec_type =2;
//        }
//
//        String bus_bars = NoOfBusBars.getText().toString();
//        String bus_sections = NoOfBusSections.getText().toString();
//        String in_bays = NoOfInBays.getText().toString();
//        String out_bays = NoOfOutBays.getText().toString();
//        String land_area = TotalLandArea.getText().toString();
//        String auto_reclosers = NoOfAutoRecloser.getText().toString();
//        String lbs = NoOfLBS.getText().toString();
//        String abs = NoOfABS.getText().toString();
//        String surge_arrestors = NoOfSurgeArrestors.getText().toString();
//        String ddlo_links = NoOfDddloLinks.getText().toString();
//        String ddlo_fuses = NoOfDdloFuses.getText().toString();
//        String in_feeders = NoOfInFeeders.getText().toString();
//        String out_feeders = NoOfOutFeeders.getText().toString();
//        System.out.println("createExcel2");
//
//        try {
//
////            Toast.makeText(getApplication(),
////                    "DBHelper: " , Toast.LENGTH_SHORT).show();
//            mydb3 = new DBHelper(this);
//
//
//
//            //get PhmBranch
//            SessionManager objSess = new SessionManager(getBaseContext());
//            String phm_branch = objSess.getPhmBranch();
//            String entBy = objSess.getUserName();
//            System.out.println("phm_branch" + phm_branch.trim());
//            phm_branch = phm_branch.trim();
//
//
//            mydb3.insertGantry();
//
//
////            Toast.makeText(getApplication(),
////                    "DBHelper3: " , Toast.LENGTH_SHORT).show();
//
//            //getSupportByLineId
//            //final Cursor res3 = mydb3.getAddSupportDataA();
//
//
//
////            Toast.makeText(getApplication(),
////                  "DBHelper4: " , Toast.LENGTH_SHORT).show();
//
////file path
//            final Cursor res3 = mydb3.getSupportByLineId(line);
//            //final Cursor res3 = mydb3.getData();
//            File sd = new File(Environment.getExternalStorageDirectory() +
//                    File.separator + "Support");
//
//            boolean success = true;
//            if (!sd.exists()) {
//                success = sd.mkdirs();
//            }
//            if (success) {
//                // Do something on success
//            } else {
//                // Do something else on failure
//            }
//
//            //File sd = Environment.getExternalStorageDirectory();
//            //String csvFile = lineName+".xls";
//            String csvFile = "Support.xls";
//            //Toast.makeText(AddSupport.this,"Excel sheet" + csvFile,Toast.LENGTH_LONG).show();
//            File directory = new File(sd.getAbsolutePath());
//            //  final Cursor res4 = mydb3.getAddLineData();
//
//            //create directory if not exist
//            if (!directory.isDirectory()) {
//                directory.mkdirs();
//            }
//
//
//            File file = new File(directory, csvFile);
//            WorkbookSettings wbSettings = new WorkbookSettings();
//            wbSettings.setLocale(new Locale("en", "EN"));
//            //WritableWorkbook workbook;
//
//            workbook = Workbook.createWorkbook(file, wbSettings);
//            //Excel sheet name. 0 represents first sheet
//            //WritableSheet sheet = workbook.createSheet("AddSupportList", 0);
//            sheet = workbook.createSheet("AddSupportList", 0);
//            // column and row
//            String[] columnName = res3.getColumnNames();
////
//
//
//
//
//
////            Toast.makeText(getApplication(),
////                  "columnName 1: " + columnName, Toast.LENGTH_SHORT).show();
//
//
//            //if(columnName ==null) {
//            sheet.addCell(new Label(0, 0, "Support_Type"));
//            sheet.addCell(new Label(1, 0, "Tower_No"));
//            sheet.addCell(new Label(2, 0, "Area"));
//            sheet.addCell(new Label(3, 0, "Conductor_Type"));
//            sheet.addCell(new Label(4, 0, "Earth_conductor_Type"));
//            sheet.addCell(new Label(5, 0, "Tower_Type"));
//            sheet.addCell(new Label(6, 0, "Tower_Config"));
//            sheet.addCell(new Label(7, 0, "Latitude"));
//            sheet.addCell(new Label(8, 0, "Longitude"));
//            sheet.addCell(new Label(9, 0, "Circuit_Type"));
//            sheet.addCell(new Label(10, 0, "Body_Exten"));
//            sheet.addCell(new Label(11, 0, "Line_ID"));
//            sheet.addCell(new Label(12, 0, "Tapping"));
//            sheet.addCell(new Label(13, 0, "Map_ID"));
//            sheet.addCell(new Label(14, 0, "CSC"));
//            sheet.addCell(new Label(15, 0, "PHM_Branch"));
//            sheet.addCell(new Label(16, 0, "Status"));
//            sheet.addCell(new Label(17, 0, "Ent_by"));
//
//
//            //sheet.addCell(new Label(1, 0, "Line_Name"));
//            //sheet.addCell(new Label(2, 0, "ID"));
//            //sheet.addCell(new Label(3, 0, "PHM_Branch"));
//            // sheet.addCell(new Label(6, 0, "CSC"));
//            // sheet.addCell(new Label(8, 0, "Earth_conductor_Type"));
//            //sheet.addCell(new Label(11, 0, "Status"));
//            //sheet.addCell(new Label(12, 0, "Aproval_Level"));
//            //sheet.addCell(new Label(13, 0, "Filepath"));
//            // sheet.addCell(new Label(21, 0, "RowID"));
//            //sheet.addCell(new Label(12, 0, "Line_Name"));
//            //sheet.addCell(new Label(0, 0, "Tower_No"));
//            //sheet.addCell(new Label(1, 0, "Area"));
//            //sheet.addCell(new Label(2, 0, "CSC"));
//            //sheet.addCell(new Label(3, 0, "Conductor_Type"));
//            //sheet.addCell(new Label(4, 0, "Earth_conductor_Type"));
//            //sheet.addCell(new Label(5, 0, "Support_Type"));
//            //sheet.addCell(new Label(6, 0, "Tower_Type"));
//            //sheet.addCell(new Label(7, 0, "Circuit_Type"));
//            //sheet.addCell(new Label(8, 0, "Tower_Config"));
//            //sheet.addCell(new Label(9, 0, "Body_Exten"));
//            //sheet.addCell(new Label(10, 0, "Longitude"));
//            //sheet.addCell(new Label(11, 0, "Latitude"));
//            //sheet.addCell(new Label(12, 0, "Line_Name"));
//
//
////            Toast.makeText(getApplication(),
////                        "column created 1", Toast.LENGTH_SHORT).show();
//
//            // sheet.addCell(new Label(1, 0, "phone"));
//            //}
////            Toast.makeText(getApplication(),
////                    "res3" +res3, Toast.LENGTH_SHORT).show();
//
//            if (res3.moveToFirst()) {
//
//                do {
////                   Toast.makeText(getApplication(),
////                            "rrrr", Toast.LENGTH_SHORT).show();
//
//                    String supportType = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_TYPE));
//                    String supportTowerNo = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_TOWERNO));
//                    String supportAreaE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_AREA));
//                    String supportConductorType = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_CONDUCTOR_TYPE));
//                    String supportEarthConductorType = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_EARTH_CONDUCTOR_TYPE));
//                    String supportTowerType = res3.getString(res3.getColumnIndex(DBHelper.SUPPPORT_TOWER_TYPE));
//                    String towerConfiguration = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_TOWER_CONFIGURATION));
//                    String gpsLatitudeE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_GPS_LATITUDE));
//                    String gpsLongititudeE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_GPS_LONGITITUDE));
//                    String noofcircuits1 = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_NOOFCIRCUITS));
//                    String bodyExtension1 = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_BODY_EXTENSION));
//                    String lineNameE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_LINE_NAME));
//                    String phmBranchE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_PHM_BRANCH));
//                    String statusE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_STATUS));
//                    String entByE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_ENT_BY));
//                    //String tappingE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_TAPPING));
//                    //String mapIdE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_MAP_ID));
//                    //String cscE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_CSC));
//                    //  String supportCSCE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_CSC));
//
//
//                    //String maindate = res3.getString(res3.getColumnIndex(DBHelper.TM_MAIN_DATE));
//                    //String maindate = "";
//
//
////                   Toast.makeText(getApplication(),
////                            "nooftappings" +nooftappings, Toast.LENGTH_SHORT).show();
//
//                    // String phoneNumber = res.getString(res.getColumnIndex("phone"));
////                    Toast.makeText(getApplication(),
////                            "rrrr 1", Toast.LENGTH_SHORT).show();
//                    int i = res3.getPosition() + 1;
////                    Toast.makeText(getApplication(),
////                            "rrrr 2", Toast.LENGTH_SHORT).show();
//
//
//                    sheet.addCell(new Label(0, i, supportType));
//                    sheet.addCell(new Label(1, i, supportTowerNo));
//                    sheet.addCell(new Label(2, i, supportAreaE));
//                    sheet.addCell(new Label(3, i, supportConductorType));
//                    //sheet.addCell(new Label(4, i, supportEarthConductorType));
//                    sheet.addCell(new Label(4, i, "0"));
//                    sheet.addCell(new Label(5, i, supportTowerType));
//                    sheet.addCell(new Label(6, i, towerConfiguration));
//                    sheet.addCell(new Label(7, i, gpsLatitudeE));
//                    sheet.addCell(new Label(8, i, gpsLongititudeE));
//                    sheet.addCell(new Label(9, i, noofcircuits1));
//                    sheet.addCell(new Label(10, i, bodyExtension1));
//                    sheet.addCell(new Label(11, i, lineNameE));
//                    sheet.addCell(new Label(12, i, "0"));
//                    sheet.addCell(new Label(13, i, "0"));
//                    sheet.addCell(new Label(14, i, "0"));
//                    sheet.addCell(new Label(15, i, phmBranchE));
//                    sheet.addCell(new Label(16, i, "2"));
//                    sheet.addCell(new Label(17, i, entByE));
//                    //sheet.addCell(new Label(14, i, "2"));
//                    // sheet.addCell(new Label(6, i, supportCSCE));
//                    /* sheet.addCell(new Label(1, i, ""));
//                    sheet.addCell(new Label(2, i, " "));
//                    sheet.addCell(new Label(3, i, " "));*/
//
//
//                    //  sheet.addCell(new Label(0, i, supportTowerNo));
//                    // sheet.addCell(new Label(1, i, supportAreaE));
//                    // sheet.addCell(new Label(2, i, supportCSCE));
//                    // sheet.addCell(new Label(3, i, supportConductorType));
//                    // sheet.addCell(new Label(4, i, supportEarthConductorType));
//                    // sheet.addCell(new Label(5, i, supportType));
//                    // sheet.addCell(new Label(6, i, supportTowerType));
//                    // sheet.addCell(new Label(7, i, noofcircuits1));
//                    // sheet.addCell(new Label(8, i, towerConfiguration));
//                    // sheet.addCell(new Label(9, i, bodyExtension1));
//                    // sheet.addCell(new Label(10, i, gpsLongititudeE));
//                    // sheet.addCell(new Label(11, i, gpsLatitudeE));
//                    // sheet.addCell(new Label(12, i, lineNameE));
//
//
//                    //sheet.addCell(new Label(5, i, maindate));
//
//
////                    Toast.makeText(getApplication(),
////                           "rrrr 3", Toast.LENGTH_SHORT).show();
//                    //sheet.addCell(new Label(1, i, phoneNumber));
//                } while (res3.moveToNext());
//            }
//
//            //closing cursor
//            res3.close();
//            workbook.write();
//            workbook.close();
////                Toast.makeText(getApplication(),
////                        "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
//        } catch (WriteException e) {
//            e.printStackTrace();
//            //Toast.makeText(getApplication(),
//            //      " Error while generating excel sheet : ", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            //Toast.makeText(getApplication(),
//            //      "error while generating excel sheet: ", Toast.LENGTH_SHORT).show();
//
//
//        }
//
//        // TowerNo.setText("");
//        // SupportLineName.setText("");
//        // etTowerType.setVisibility(View.INVISIBLE);
//        // etTowerType.setText("");
//        // etConType.setVisibility(View.INVISIBLE);
//        // etConType.setText("");
//        // etCSC.setVisibility(View.INVISIBLE);
//        // etCSC.setText("");
//        // CSC.setPrompt("Select CSC");
//        // etArea.setVisibility(View.INVISIBLE);
//        // etArea.setText("");
//        //SupportID.setText("");
//        //SupportLineName.setText("");
//
//        TowerNo.setText("");
//        GPSLatitude.setText("");
//        GPSLatitude.setText("");
//
//        //}
//
//        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(AddSupport.this);
//        builder1.setTitle("MV-MMS");
//        builder1.setIcon(R.drawable.ceb_logo2);
//        builder1.setMessage("Data Saved in Excel Sheet")
//                .setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                });
//        android.app.AlertDialog alert = builder1.create();
//        alert.show();
//        progressSaving();
//    }

//        @Override
//        public void onClick(View view) {
//
//        }
//
//
//
//        private class loadarea extends AsyncTask<String, Void, MmsAddArea[]>{
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(MmsAddArea[] results) {
//                // ListView Item Click Listener
//                System.out.println("results" +results);
//                System.out.println("results" +results.length);
//                String[] area;
//                values = new String[results.length];
//
////
//                if(results != null){
//                    int count =  results.length -1;
//                    for(int c =0; c <=count; c++){
//                        MmsAddArea  obj = results[c];
//                        values[c] = obj.getDeptNm();
//                        spinnerMap.put(c,obj.getDeptId());
//                        //String deptId = spinnerMap.put(c,obj.getDeptId());
//
//
//                    }
//                }
//
//                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values);
//                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                SpinnerArea = findViewById(R.id.area);
//                SpinnerArea.setAdapter(adapterNs);
////
//                //ProgDialog.dismiss();
//                //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            protected MmsAddArea[] doInBackground(String... strings) {
//                //get deptId from session manager
//                SessionManager objS = new SessionManager(getBaseContext());
//                String deptId = objS.getPhmBranch();
//                System.out.println("****************************************PhmBranch" + deptId.trim());
//                deptId = deptId.trim();
//
//
//                RestTemplate rest = new RestTemplate();
//                //String url6 = Util.SRT_URL+"findAllAreaNew";
//                String url6 = Util.SRT_URL+"findAllAreaNewMobile/" + deptId + "/";
//
//                System.out.println("ssss" +url6);
//                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//                return rest.getForObject(url6,MmsAddArea[].class);
//                //return List<MmsAddArea>null;
//
//            }
//        }
    /////////////////edit Dinith & Kent //////////////////////////

    ////////// L O A D  P R O V I N C E /////////////////////////// R O W D Y //////////////////////////
    private class loadProvince extends AsyncTask<String, Void, Glcompm[]>{
        @Override
        protected Glcompm[] doInBackground(String... strings) {
            //get deptId from session manager
            SessionManager objS = new SessionManager(getBaseContext());
            String compId = objS.getPhmBranch();
            compId = compId.trim();

            phmBranch = compId;

            RestTemplate rest = new RestTemplate();
            //String url6 = Util.SRT_URL+"findAllAreaNew";
            String url6 = Util.SRT_URL+"findAllProvincesNewMobile/" + compId + "/";

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url6,Glcompm[].class);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgDialog= new ProgressDialog(com.example.akla.login.AddGSS.this);
//message should be changed according to the requirement
            ProgDialog.setMessage("Please wait...\n(This may take some time, depending on your network connection)");
            ProgDialog.setIndeterminate(false);
            ProgDialog.setTitle(Util.alert_header);
            ProgDialog.setIcon(R.drawable.logo);
            ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            ProgDialog.setCancelable(true);
            ProgDialog.show();
        }

        @Override
        protected void onPostExecute(Glcompm[]  results) {
            //super.onPostExecute(results);
            // ListView Item Click Listener
            String[] province;
            valuesPro = new String[results.length];

            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    Glcompm obj = results[c];
                    valuesPro[c] = obj.getCompNm();
                    spinnerMapProvince.put(c,obj.getCompId());
                    System.out.println(spinnerMapProvince);
                    System.out.println(valuesPro);
                }
            }
            ArrayAdapter<String> adapterPr = new ArrayAdapter< String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesPro);
            adapterPr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerProvince = findViewById(R.id.spnProvince);
            SpinnerProvince.setAdapter(adapterPr);
        }
    }

    ////////// L O A D  A R E A  B Y  P R O V I N C E /////////////////////////////////////////
    private class loadAreaByProvince extends AsyncTask<String, Void, Gldeptm[]> {
        @Override

        protected Gldeptm[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findAllAreaByProvinceNew/" + province + "/";

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5, Gldeptm[].class);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(Gldeptm[] results) {
            // ListView Item Click Listener
            System.out.println("results" + results);
            System.out.println("results" + results.length);
            String[] line;
            valuesArea = new String[results.length];

            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    Gldeptm obj = results[c];
                    valuesArea[c] = obj.getDeptNm();
                    SpinnerMapArea.put(c, obj.getDeptId());
                }
            }
            ArrayAdapter<String> adapterArea = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesArea);
            adapterArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerArea = findViewById(R.id.spnArea);
            SpinnerArea.setAdapter(adapterArea);
        }
    }

    ////////// L O A D   L I N E   B Y   P R O V I N C E /////////////////////////////////////////
//    private class loadLineByArea extends AsyncTask<String, Void, MmsAddline[]> {
//        @Override
//
//        protected MmsAddline[] doInBackground(String... urls) {
//            RestTemplate rest = new RestTemplate();
//            String url5 = Util.SRT_URL + "findLineByArea/" + area + "/";
//
//            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//            return rest.getForObject(url5, MmsAddline[].class);
//        }
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        protected void onPostExecute(MmsAddline[] results) {
//            // ListView Item Click Listener
//            System.out.println("results" + results);
//            System.out.println("results" + results.length);
//            String[] line;
//            valuesLine = new String[results.length];
//
//            if (results != null) {
//                int count = results.length - 1;
//                for (int c = 0; c <= count; c++) {
//                    MmsAddline obj = results[c];
//                    valuesLine[c] = obj.getLineName();
//                    SpinnerMapLine.put(c, obj.getId());
//                }
//            }
//            ArrayAdapter<String> adapterLine = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesLine);
//            adapterLine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            SpinnerLine = findViewById(R.id.spnLine);
//            SpinnerLine.setAdapter(adapterLine);
//        }
//    }


    /////////////////////////////////// Load CSC ////////////////////////////////////////////
    private class loadCscbyAea extends AsyncTask<String, Void, Gldeptm[]>{
        @Override
        protected Gldeptm[] doInBackground(String... strings) {
            //get deptId from session manager
            SessionManager objS = new SessionManager(getBaseContext());
            String deptId = objS.getPhmBranch();
            deptId = deptId.trim();

            RestTemplate rest = new RestTemplate();
            //String url6 = Util.SRT_URL+"findAllAreaNew";
            String url6 = Util.SRT_URL+"findAllCSCByArea/"+area+"/";

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url6,Gldeptm[].class);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Gldeptm[]  results) {
            //super.onPostExecute(results);
            // ListView Item Click Listener
            String[] province;
            valuesCsc = new String[results.length];
//
            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    Gldeptm obj = results[c];
                    valuesCsc[c] = obj.getDeptNm();
                    SpinnerMapCsc.put(c,obj.getDeptId());
                }
            }
            ArrayAdapter<String> adapterPr = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesCsc);
            adapterPr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerCSC = findViewById(R.id.spnCsc);
            SpinnerCSC.setAdapter(adapterPr);
            ProgDialog.dismiss();
        }
    }
}



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add__gss);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        navigationView.setNavigationItemSelectedListener(this);
//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.add__gs, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_home) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_tools) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
