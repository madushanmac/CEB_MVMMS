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

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.akla.login.Util.isConnected;

public class AddGantry extends AppCompatActivity
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
        setContentView(R.layout.activity_add_gantry);

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


        if (!isConnected(AddGantry.this)) {
            readExcelArea();
            readExcelProvince();
            readExcelCsc();
        } else {
            new AddGantry.loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Area -- Area load by Province
        SpinnerProvince = findViewById(R.id.spnProvince);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province =idprovince;

                if(!Util.isConnected(AddGantry.this)){

                    Toast.makeText(getApplication(),"readExcel: Area " , Toast.LENGTH_SHORT).show();

                }else {
                    new AddGantry.loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

                if(!Util.isConnected(AddGantry.this)){

                    Toast.makeText(getApplication(),"readExcel: Line" , Toast.LENGTH_SHORT).show();

                }else {
                    //new AddGantry.loadLineByArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new AddGantry.loadCscbyAea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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


                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddGantry.this,
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
                if(ActivityCompat.checkSelfPermission(AddGantry.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(AddGantry.this, new OnSuccessListener<Location>() {
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

        Button ButtonSave = findViewById(R.id.savebtn);
        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
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


                    if (Name.getText().toString().trim().equals("")) {
                        Name.setError("Should give a Name !");
                        Toast.makeText(getApplicationContext(), "Should give a Name !", Toast.LENGTH_SHORT).show();
                    }else if (Code.getText().toString().trim().equals("")){
                        Code.setError("Should give a Code !");
                        Toast.makeText(getApplicationContext(), "Should give a Code !", Toast.LENGTH_SHORT).show();
                    }  else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddGantry.this);
                        builder.setCancelable(true);
                        builder.setIcon(R.drawable.logo);
                        builder.setMessage("Do you want to save Gantry Data?");
                        builder.setTitle("Save Gantry");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!isConnected(AddGantry.this)){
                                           // createExcel();
                                            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            new AddGantry.AddGantryDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
            Intent intent = new Intent(AddGantry.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(AddGantry.this, GetNearByTower.class);
            startActivity(intent);

            //////////////////////////////// PHM - LCM ////////////////////////////////////////////

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddGantry.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddGantry.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddGantry.this, TM2.class);
            startActivity(intent);

            //////////////////////////////// PHM - SUb ////////////////////////////////////////////

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddGantry.this, AddGantry.class);
            startActivity(intent);
        } else if (id == R.id.nav_editGantry) {
            Intent intent = new Intent(AddGantry.this, EditGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addBusBar) {
            Intent intent = new Intent(AddGantry.this, AddBusBar.class);
            startActivity(intent);

        } else if (id == R.id.nav_addStructual) {
            Intent intent = new Intent(AddGantry.this, AddStructural.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantryMore) {
            Intent intent = new Intent(AddGantry.this, AddGantryMore.class);
            startActivity(intent);

        } else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(AddGantry.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSwitches) {
            Intent intent = new Intent(AddGantry.this, AddSwitches.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSurge) {
            Intent intent = new Intent(AddGantry.this, AddSurgeArrestors.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTransformersG) {
            Intent intent = new Intent(AddGantry.this, AddTransformersG.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddGantry.this, AddEquipment.class);
            startActivity(intent);

///////////////////// POLE DETAILS //////////////////////////////////////////////

        } else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(AddGantry.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(AddGantry.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(AddGantry.this, AddTransformers.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddGantry.this, Login.class);
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
            ProgDialog= new ProgressDialog(AddGantry.this);
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
