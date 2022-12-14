package com.example.akla.login;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.akla.login.Util.isConnected;

public class EditGantry extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

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

    //load Gantry
    Spinner SpinnerGantry;
    String gantry;
    String valuesGantry[] = new String[]{};
    HashMap<Integer,Long> SpinnerMapGantry = new HashMap<Integer, Long>();

    //to get hash map details
    String line;
    String csc;
    String code;
    MmsAddgantry objGantry;

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
    TextView Latitude;
    TextView Longitude;

    // To get location details
    private FusedLocationProviderClient client;
    String latitude;
    String longitude;

    private ProgressDialog ProgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gantry);

        Province = findViewById(R.id.province);
        Area = findViewById(R.id.area);
        CSC = findViewById(R.id.spnCsc);
        //Line = findViewById(R.id.spnLine);
        Name = findViewById(R.id.etName);
        Code = findViewById(R.id.etCode);
        ShortCircuitCurrentCapacity = findViewById(R.id.etShortCircuitCurrent);
        EarthFaultCurrentCapacity = findViewById(R.id.etEarthFaultCurrent);
        ConssionDate = findViewById(R.id.in_date);
        Type = findViewById(R.id.gantry_type);
        ElectricalType = findViewById(R.id.electrcial_gantry_type);
        NoOfBusBars = findViewById(R.id.bus_bars);
        NoOfBusSections = findViewById(R.id.bus_sections);
        NoOfInBays = findViewById(R.id.in_bars);
        NoOfOutBays = findViewById(R.id.out_bars);
        TotalLandArea = findViewById(R.id.total_land_area);
        NoOfAutoRecloser = findViewById(R.id.auto_recloser);
        NoOfLBS = findViewById(R.id.lbs);
        NoOfABS = findViewById(R.id.abs);
        NoOfSurgeArrestors = findViewById(R.id.surge_arrestors);
        NoOfDddloLinks = findViewById(R.id.ddlo);
        NoOfDdloFuses = findViewById(R.id.ddloF);
        NoOfInFeeders = findViewById(R.id.no_of_in_feeders);
        NoOfOutFeeders = findViewById(R.id.no_of_out_feeders);
        Latitude = findViewById(R.id.latitude);
        Longitude = findViewById(R.id.longitude);


//        voltage_level =(Spinner)findViewById(R.id.voltage_level);
//        adapter1 =ArrayAdapter.createFromResource(this,R.array.voltage_level,android.R.layout.simple_spinner_item);
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        voltage_level.setAdapter(adapter1);

        gantry_type =(Spinner)findViewById(R.id.gantry_type);
        adapter2 =ArrayAdapter.createFromResource(this,R.array.gantry_type,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gantry_type.setAdapter(adapter2);

//        construction_type =(Spinner)findViewById(R.id.construction_type);
//        adapter3 =ArrayAdapter.createFromResource(this,R.array.construction_type,android.R.layout.simple_spinner_item);
//        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        construction_type.setAdapter(adapter3);

        gantry_electrcial_type =(Spinner)findViewById(R.id.electrcial_gantry_type);
        adapter4 =ArrayAdapter.createFromResource(this,R.array.gantry_electrcial_type,android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gantry_electrcial_type.setAdapter(adapter4);

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


        if (!isConnected(EditGantry.this)) {

            Toast.makeText(getApplication(),"readExcel: Province " , Toast.LENGTH_SHORT).show();


        } else {
            new EditGantry.loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Area -- Area load by Province
        SpinnerProvince = findViewById(R.id.spnProvince);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province =idprovince;

                if(!Util.isConnected(EditGantry.this)){

                    Toast.makeText(getApplication(),"readExcel: Area " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditGantry.loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

                if(!Util.isConnected(EditGantry.this)){

                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    //new EditGantry.loadLineByArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new EditGantry.loadCscbyArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    //new EditGantry.loadGantryByLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new EditGantry.loadGantrybyArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        //Spinner Gantry -- Gantry load by Line
//        SpinnerLine = findViewById(R.id.spnLine);
//        SpinnerLine.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = parent.getSelectedItem().toString();
//                String name = parent.getSelectedItem().toString();
//                String idline = SpinnerMapLine.get(parent.getSelectedItemPosition());
//
//                line = idline;
//
//                if(!Util.isConnected(EditGantry.this)){
//                    //readExcelLine();
//                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
//
//                }else {
//                    //new EditGantry.loadGantryByLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                    new EditGantry.loadGantrybyAea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                }
//            }
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

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

        //Gantry object load by gantry code
        SpinnerGantry = findViewById(R.id.spnGantry);
        SpinnerGantry.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                Long idgantry1 = SpinnerMapGantry.get(parent.getSelectedItemPosition());
                code = idgantry1.toString();

                if(!Util.isConnected(EditGantry.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditGantry.loadGantryObj().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        btn_date = (Button)findViewById(R.id.btn_date);
        in_date = (EditText) findViewById(R.id.in_date);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btn_date) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(EditGantry.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    in_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });


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
                if(ActivityCompat.checkSelfPermission(EditGantry.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(EditGantry.this, new OnSuccessListener<Location>() {
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


        //////////////// G E T   D E T A I L S ///////////////////////////////////////////////
        Button ButtonDetails = findViewById(R.id.detailsbtn);
        ButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    if(objGantry!=null) {
                        if (objGantry.getName() != null) {
                            Name.setText(objGantry.getName());
                        }

                        if (objGantry.getCode() != null) {
                            Code.setText(objGantry.getCode());
                        }

                        if (objGantry.getShortCctCurntCapacity() != null) {
                            ShortCircuitCurrentCapacity.setText(objGantry.getShortCctCurntCapacity().toString());
                        }

                        if (objGantry.getEarthFaultCurntCapacity() != null) {
                            EarthFaultCurrentCapacity.setText(objGantry.getEarthFaultCurntCapacity().toString());
                        }

                        if (objGantry.getDateOfComm() != null) {
                            ConssionDate.setText((objGantry.getDateOfComm().getYear() - 100 + 2000)
                                    + "-" + (objGantry.getDateOfComm().getMonth() + 1)
                                    + "-" + objGantry.getDateOfComm().getDate());

                        }
                        //Type.setPromptId(2);
                        //Spinner ElectricalType;
                        if (objGantry.getNoBusBar() != null) {
                            NoOfBusBars.setText(objGantry.getNoBusBar().toString());
                        }

                        if (objGantry.getNoBusSec() != null) {
                            NoOfBusSections.setText(objGantry.getNoBusSec().toString());
                        }

                        if (objGantry.getNoInBays() != null) {
                            NoOfInBays.setText(objGantry.getNoInBays().toString());
                        }

                        if (objGantry.getNoOutBays() != null) {
                            NoOfOutBays.setText(objGantry.getNoOutBays().toString());
                        }

                        if (objGantry.getTotalLandArea() != null) {
                            TotalLandArea.setText(objGantry.getTotalLandArea().toString());
                        }

                        if (objGantry.getNoAr() != null) {
                            NoOfAutoRecloser.setText(objGantry.getNoAr().toString());
                        }

                        if (objGantry.getNoLbs() != null) {
                            NoOfLBS.setText(objGantry.getNoLbs().toString());
                        }

                        if (objGantry.getNoAbs() != null) {
                            NoOfABS.setText(objGantry.getNoAbs().toString());
                        }

                        if (objGantry.getNoSa() != null) {
                            NoOfSurgeArrestors.setText(objGantry.getNoSa().toString());
                        }

                        if (objGantry.getNoDdloLinks() != null) {
                            NoOfDddloLinks.setText(objGantry.getNoDdloLinks().toString());
                        }

                        if (objGantry.getNoDdloFuses() != null) {
                            NoOfDdloFuses.setText(objGantry.getNoDdloFuses().toString());
                        }

                        if (objGantry.getNoIncomingFeeder() != null) {
                            NoOfInFeeders.setText(objGantry.getNoIncomingFeeder().toString());
                        }

                        if (objGantry.getNoOutgoingFeeder() != null) {
                            NoOfOutFeeders.setText(objGantry.getNoOutgoingFeeder().toString());
                        }

                        if (objGantry.getGpsLatitude() != null) {
                            Latitude.setText(objGantry.getGpsLatitude().toString());
                        }

                        if (objGantry.getGpsLongitude() != null) {
                            Longitude.setText(objGantry.getGpsLongitude().toString());
                        }
                    }
                }
            }
        });

        /////////////////////////Save to Database ////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////// Edit by Dinith 2019 - 10 - 24 ///////////////////////////////////////////////////////////////////////////////

        Button ButtonSave = findViewById(R.id.savebtn);
        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    if (Name.getText().toString().trim().equals("")) {
                        Name.setError("Should give a Name !");
                        Toast.makeText(getApplicationContext(), "Should give a Name !", Toast.LENGTH_SHORT).show();
                    }else if (Code.getText().toString().trim().equals("")){
                        Code.setError("Should give a Code !");
                        Toast.makeText(getApplicationContext(), "Should give a Code !", Toast.LENGTH_SHORT).show();
                    }  else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EditGantry.this);
                        builder.setCancelable(true);
                        builder.setIcon(R.drawable.logo);
                        builder.setMessage("Do you want to save Gantry Data?");
                        builder.setTitle("Save Gantry");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!isConnected(EditGantry.this)){
                                            // createExcel();
                                            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            new EditGantry.AddGantryDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                            Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                                            // createExcel();
                                        }

                                    }
                                });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }

            }
        });


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
            //MmsAddgantry objAddgantry = new MmsAddgantry();

            objGantry.setPhmBranch(phmBranch);

            objGantry.setProvince(province);
            objGantry.setArea(area);
            objGantry.setCsc(csc);
            //objGantry.setLineId(new BigDecimal(line));
            objGantry.setName(name);
            objGantry.setCode(code);

            if(short_circuit.length()==0){
                objGantry.setShortCctCurntCapacity(new BigDecimal(0));
            }else{
                objGantry.setShortCctCurntCapacity(new BigDecimal(short_circuit));
            }

            if(earth_fault.length()==0){
                objGantry.setEarthFaultCurntCapacity(new BigDecimal(0));
            }else{
                objGantry.setEarthFaultCurntCapacity(new BigDecimal(earth_fault));
            }

//            objGantry.setDateOfComm(date);
            Date dateNow = null;
            try {
                dateNow = new SimpleDateFormat("yyyy-MM-dd").parse(comdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objGantry.setDateOfComm(dateNow);

            objGantry.setGantryType(new BigDecimal(type));
            objGantry.setGantryEleType(new BigDecimal(elec_type));

            if(bus_bars.length()==0){
                objGantry.setNoBusBar(new BigDecimal(0));
            }else{
                objGantry.setNoBusBar(new BigDecimal(bus_bars));
            }

            if(bus_sections.length()==0){
                objGantry.setNoBusSec(new BigDecimal(0));
            }else{
                objGantry.setNoBusSec(new BigDecimal(bus_sections));
            }

            if(in_bays.length()==0){
                objGantry.setNoInBays(new BigDecimal(0));
            }else{
                objGantry.setNoInBays(new BigDecimal(in_bays));
            }

            if(out_bays.length()==0){
                objGantry.setNoOutBays(new BigDecimal(0));
            }else{
                objGantry.setNoOutBays(new BigDecimal(out_bays));
            }

            if(land_area.length()==0){
                objGantry.setTotalLandArea(new BigDecimal(0));
            }else{
                objGantry.setTotalLandArea(new BigDecimal(land_area));
            }

            if(auto_reclosers.length()==0){
                objGantry.setNoAr(new BigDecimal(0));
            }else{
                objGantry.setNoAr(new BigDecimal(auto_reclosers));
            }

            if(lbs.length()==0){
                objGantry.setNoLbs(new BigDecimal(0));
            }else{
                objGantry.setNoLbs(new BigDecimal(lbs));
            }

            if(abs.length()==0){
                objGantry.setNoAbs(new BigDecimal(0));
            }else{
                objGantry.setNoAbs(new BigDecimal(abs));
            }

            if(surge_arrestors.length()==0){
                objGantry.setNoSa(new BigDecimal(0));
            }else{
                objGantry.setNoSa(new BigDecimal(surge_arrestors));
            }

            if(ddlo_links.length()==0){
                objGantry.setNoDdloLinks(new BigDecimal(0));
            }else{
                objGantry.setNoDdloLinks(new BigDecimal(ddlo_links));
            }

            if(ddlo_fuses.length()==0){
                objGantry.setNoDdloFuses(new BigDecimal(0));
            }else{
                objGantry.setNoDdloFuses(new BigDecimal(ddlo_fuses));
            }

            if(in_feeders.length()==0){
                objGantry.setNoIncomingFeeder(new BigDecimal(0));
            }else{
                objGantry.setNoIncomingFeeder(new BigDecimal(in_feeders));
            }

            if(out_feeders.length()==0){
                objGantry.setNoOutgoingFeeder(new BigDecimal(0));
            }else{
                objGantry.setNoOutgoingFeeder(new BigDecimal(out_feeders));
            }

            if(latitude.length()==0){
                objGantry.setGpsLatitude(new BigDecimal(0));
            }else{
                objGantry.setGpsLatitude(new BigDecimal(latitude));
            }

            if(longitude.length()==0){
                objGantry.setGpsLongitude(new BigDecimal(0));
            }else{
                objGantry.setGpsLongitude(new BigDecimal(longitude));
            }



            System.out.println("Set object testing :****************************************");
            System.out.println("PHM Branch: "+objGantry.getPhmBranch());
            System.out.println("Province :" + objGantry.getProvince());
            System.out.println("Area :" + objGantry.getArea());
            System.out.println("CSC :"+objGantry.getCsc());
            //System.out.println("Line :"+objGantry.getLineId());
            System.out.println("Name :" + objGantry.getName());
            //System.out.println("Code :" + objGantry.getCode());
            System.out.println("Short Circuit :"+objGantry.getShortCctCurntCapacity());
            System.out.println("Earth Fault :"+objGantry.getEarthFaultCurntCapacity());

            System.out.println("Date"+objGantry.getDateOfComm());

            System.out.println("Gantry Type :" + objGantry.getGantryType());
            System.out.println("G Elec Type :" + objGantry.getGantryEleType());
            System.out.println("No Bus bars :" + objGantry.getNoBusBar());
            System.out.println("no of bus Secs :" + objGantry.getNoBusSec());
            System.out.println("In Bays :" + objGantry.getNoInBays());
            System.out.println("Out Bays :" + objGantry.getNoOutBays());
            System.out.println("Land Area :" + objGantry.getTotalLandArea());
            System.out.println("Auto Recloser :"+objGantry.getNoAr());
            System.out.println("LBS :"+objGantry.getNoLbs());
            System.out.println("ABS :"+objGantry.getNoAbs());
            System.out.println("Surge Arrestors :"+objGantry.getNoSa());
            System.out.println("DDLO Links :"+objGantry.getNoDdloLinks());
            System.out.println("DDLO Fuses:"+objGantry.getNoDdloFuses());
            System.out.println("In feeders :" + objGantry.getNoIncomingFeeder());
            System.out.println("Out Feeders :" + objGantry.getNoOutgoingFeeder());

            System.out.println("Latitude :" + objGantry.getGpsLatitude());
            System.out.println("Longitude :" + objGantry.getGpsLongitude());

            final RestTemplate restTemplate = new RestTemplate();
            final String url1 = Util.SRT_URL + "MMSUpdateGantryMobile/";
            System.out.println(" url1 " + url1);
            // trustEveryone();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            //return restTemplate.getForObject(url1, String.class);
            String objReturn = restTemplate.postForObject(url1, objGantry, String.class);
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
            Intent intent = new Intent(EditGantry.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(EditGantry.this, GetNearByTower.class);
            startActivity(intent);

            //////////////////////////////// PHM - LCM ////////////////////////////////////////////

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(EditGantry.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(EditGantry.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(EditGantry.this, TM2.class);
            startActivity(intent);

            //////////////////////////////// PHM - SUb ////////////////////////////////////////////

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(EditGantry.this, AddGantry.class);
            startActivity(intent);
        } else if (id == R.id.nav_editGantry) {
            Intent intent = new Intent(EditGantry.this, EditGantry.class);
            startActivity(intent);


        } else if (id == R.id.nav_addBusBar) {
            Intent intent = new Intent(EditGantry.this, AddBusBar.class);
            startActivity(intent);

        } else if (id == R.id.nav_addStructual) {
            Intent intent = new Intent(EditGantry.this, AddStructural.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantryMore) {
            Intent intent = new Intent(EditGantry.this, AddGantryMore.class);
            startActivity(intent);

        } else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(EditGantry.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSwitches) {
            Intent intent = new Intent(EditGantry.this, AddSwitches.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSurge) {
            Intent intent = new Intent(EditGantry.this, AddSurgeArrestors.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTransformersG) {
            Intent intent = new Intent(EditGantry.this, AddTransformersG.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(EditGantry.this, AddEquipment.class);
            startActivity(intent);

///////////////////// POLE DETAILS //////////////////////////////////////////////

        } else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(EditGantry.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(EditGantry.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(EditGantry.this, AddTransformers.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(EditGantry.this, Login.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

    }



    private class loadarea extends AsyncTask<String, Void, MmsAddArea[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(MmsAddArea[] results) {
            // ListView Item Click Listener
            System.out.println("results" +results);
            System.out.println("results" +results.length);
            String[] area;
            values = new String[results.length];

//
            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    MmsAddArea  obj = results[c];
                    values[c] = obj.getDeptNm();
                    spinnerMap.put(c,obj.getDeptId());
                    //String deptId = spinnerMap.put(c,obj.getDeptId());


                }
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerArea = findViewById(R.id.area);
            SpinnerArea.setAdapter(adapterNs);
//
            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();
        }

        @Override
        protected MmsAddArea[] doInBackground(String... strings) {
            //get deptId from session manager
            SessionManager objS = new SessionManager(getBaseContext());
            String deptId = objS.getPhmBranch();
            System.out.println("****************************************PhmBranch" + deptId.trim());
            deptId = deptId.trim();


            RestTemplate rest = new RestTemplate();
            //String url6 = Util.SRT_URL+"findAllAreaNew";
            String url6 = Util.SRT_URL+"findAllAreaNewMobile/" + deptId + "/";

            System.out.println("ssss" +url6);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url6,MmsAddArea[].class);
            //return List<MmsAddArea>null;

        }
    }
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
            ProgDialog= new ProgressDialog(EditGantry.this);
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

//    ////////// L O A D   L I N E   B Y   P R O V I N C E /////////////////////////////////////////
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
    private class loadCscbyArea extends AsyncTask<String, Void, Gldeptm[]>{
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
        }
    }

    /////////////////////////////////// Load Gantry by Area ////////////////////////////////////////////
    private class loadGantrybyArea extends AsyncTask<String, Void, MmsAddgantry[]>{

        @Override
        protected MmsAddgantry[] doInBackground(String... strings) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findGantryByArea/" + area+ "/";
            System.out.println("AREAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: "+area);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("KKKKKKKKKKKKKKKKKKKKKKK"+rest.getForObject(url5, MmsAddgantry[].class));
            return rest.getForObject(url5, MmsAddgantry[].class);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(MmsAddgantry[] results) {
            // ListView Item Click Listener
            System.out.println("results: " + results);
            System.out.println("results: " + results.length);
            //System.out.println("results: " + results[0]);
            System.out.println("RESULTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");

            valuesGantry = new String[results.length];

            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddgantry obj = results[c];
                    valuesGantry[c] = obj.getName();
                    SpinnerMapGantry.put(c, obj.getId());
                }
            }
            ArrayAdapter<String> adapterGantry = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesGantry);
            adapterGantry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerGantry = findViewById(R.id.spnGantry);
            SpinnerGantry.setAdapter(adapterGantry);
        }
    }

//    ////////// L O A D   G A N T R Y   B Y   L I N E /////////////////////////////////////////
//    private class loadGantryByLine extends AsyncTask<String, Void, MmsAddgantry[]> {
//        @Override
//        protected MmsAddgantry[] doInBackground(String... urls) {
//            RestTemplate rest = new RestTemplate();
//            String url5 = Util.SRT_URL + "findGantryByAreaLine/" + area + "/" + line + "/";
//
//            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//            return rest.getForObject(url5, MmsAddgantry[].class);
//        }
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        protected void onPostExecute(MmsAddgantry[] results) {
//            // ListView Item Click Listener
//            System.out.println("results" + results);
//            System.out.println("results" + results.length);
//            String[] line;
//            valuesGantry = new String[results.length];
//
//            if (results != null) {
//                int count = results.length - 1;
//                for (int c = 0; c <= count; c++) {
//                    MmsAddgantry obj = results[c];
//                    valuesGantry[c] = obj.getName();
//                    SpinnerMapGantry.put(c, obj.getId());
//                }
//            }
//            ArrayAdapter<String> adapterGantry = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesGantry);
//            adapterGantry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            SpinnerGantry = findViewById(R.id.spnGantry);
//            SpinnerGantry.setAdapter(adapterGantry);
//        }
//    }

    ////////// L O A D   G A N T R Y /////////////////////////////////////////////////////////
    private class loadGantryObj extends AsyncTask<String, Void, MmsAddgantry> {

        @Override
        protected MmsAddgantry doInBackground(String... urls) {

            //Code = findViewById(R.id.spnGantryCode);
            //String code = Code.getSelectedItem().toString();

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "findGantryById/" + code + "/";
            System.out.println(code);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
            //System.out.println(rest.getForObject(url6, MmsAddgantry.class));

            objGantry =  rest.getForObject(url6, MmsAddgantry.class);;
            System.out.println(objGantry.getPhmBranch());

            ProgDialog.dismiss();
            return rest.getForObject(url6, MmsAddgantry.class);
        }
    }
}
