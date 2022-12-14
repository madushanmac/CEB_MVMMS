package com.example.akla.login;

import android.app.AlertDialog;
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
import java.util.HashMap;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.akla.login.Util.isConnected;

public class AddPoles extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    //load Area
    Spinner SpinnerArea;
    String[] values = new String[]{};
    HashMap<Integer, String> SpinnerMapArea = new HashMap<Integer, String>();

    //load CSC
    Spinner SpinnerCSC;
    String[] valuesCsc = new String[]{};
    HashMap<Integer, String> SpinnerMapCsc = new HashMap<Integer, String>();

    //load pole type
    Spinner SpinnerPoleType;
    String[] valuesPoleType = new String[]{};
    HashMap<Integer, String> SpinnerMappoleType = new HashMap<Integer, String>();

    //load pole type
    Spinner SpinnerTransformer;
    String[] valuesTransformer = new String[]{};
    HashMap<Integer, String> SpinnerMapTransformer = new HashMap<Integer, String>();

    Spinner SpinnerConsumerType;
    String area;
    String csc;
    String tranformer;
    String pole;

    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;
    ArrayAdapter<CharSequence> adapter3;
    //Intialize Variebles

    EditText PoleNumber;
    Spinner PoleMaterial;
    Spinner Area;
    Spinner CSC;
    EditText NoOfStay;
    EditText NoOfStrut;
    Spinner LineDetails;
    Spinner ConsumerType;
    EditText NoOfConsumers1ph;
    EditText NoOfConsumers3ph;
    TextView GpsLatitude;
    TextView GpsLongitude;

    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poles);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LineDetails =(Spinner)findViewById(R.id.spnLineDetails);
        adapter1 =ArrayAdapter.createFromResource(this,R.array.pole_line_deltails,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LineDetails.setAdapter(adapter1);

        ConsumerType =(Spinner)findViewById(R.id.spnConsumerType);
        adapter2 =ArrayAdapter.createFromResource(this,R.array.consumer_type,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ConsumerType.setAdapter(adapter2);

//        PoleMaterial = (Spinner)findViewById(R.id.spnPoleMaterial);
//        adapter3 =ArrayAdapter.createFromResource(this,R.array.pole_material,android.R.layout.simple_spinner_item);
//        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        PoleMaterial.setAdapter(adapter3);

        if (!isConnected(AddPoles.this)) {
        } else {
            new AddPoles.loadarea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Line -- Line load by Area
        SpinnerArea = findViewById(R.id.spnPoleArea);
        SpinnerArea.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idarea = SpinnerMapArea.get(parent.getSelectedItemPosition());
                area = idarea;

                if(!Util.isConnected(AddPoles.this)){
                }else {
                    new AddPoles.loadCscbyArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new AddPoles.findActivePoleTypes().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new AddPoles.findTransformersByArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner CSC
        SpinnerCSC = findViewById(R.id.spnCSC);
        SpinnerCSC.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idcsc = SpinnerMapCsc.get(parent.getSelectedItemPosition());
                csc = idcsc;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        //Spinner Transformer
        SpinnerTransformer = findViewById(R.id.spnTransformer);
        SpinnerTransformer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idtrn = SpinnerMapTransformer.get(parent.getSelectedItemPosition());
                tranformer = idtrn;
                System.out.println("TrANS TRANS TRANS TRANS LLLLLL"+tranformer);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //Spinner Pole Type
        SpinnerPoleType = findViewById(R.id.spnPoleMaterial);
        SpinnerPoleType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idpole = SpinnerMappoleType.get(parent.getSelectedItemPosition());
                pole = idpole;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        SpinnerConsumerType=findViewById(R.id.spnConsumerType);
        NoOfConsumers1ph=findViewById(R.id.etNoOfCon1ph);
        NoOfConsumers3ph=findViewById(R.id.etNoOfCon3ph);
        SpinnerConsumerType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getSelectedItem().toString();

                if(adapterView.getSelectedItemId()==0){
                    System.out.println("IFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
                    NoOfConsumers1ph.setEnabled(true);
                    NoOfConsumers3ph.setEnabled(false);

                }else{
                    System.out.println("ELSEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                    NoOfConsumers1ph.setEnabled(false);
                    NoOfConsumers3ph.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Get Location

        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

        // P O I N T - 1
        Button button = findViewById(R.id.bLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(AddPoles.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(AddPoles.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){

                            TextView lat1 = findViewById(R.id.latitude);
                            lat1.setText(String.valueOf((double) location.getLatitude()));

                            TextView long1 = findViewById(R.id.longitude);
                            long1.setText(String.valueOf((double) location.getLongitude()));
                        }
                    }
                });
            }
        });
        /////////////////////////Save to Database ////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////// R O W D Y  ////////// 2019 - 12 - 29 ///////////////////////////////////////////////////////////////////////////////

        Button ButtonSave = findViewById(R.id.btnSaveDB);
        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
                    PoleNumber = findViewById(R.id.etNumber);
                    PoleMaterial = findViewById(R.id.spnPoleMaterial);
                    Area = findViewById(R.id.spnPoleArea);
                    CSC = findViewById(R.id.spnCSC);
                    NoOfStay = findViewById(R.id.etNoOfStay);
                    NoOfStrut = findViewById(R.id.etNoOfStrut);
                    LineDetails = findViewById(R.id.spnLineDetails);
                    ConsumerType = findViewById(R.id.spnConsumerType);
                    NoOfConsumers1ph = findViewById(R.id.etNoOfCon1ph);
                    NoOfConsumers3ph = findViewById(R.id.etNoOfCon3ph);
                    GpsLatitude = findViewById(R.id.latitude);
                    GpsLongitude = findViewById(R.id.longitude);

                    if (PoleNumber.getText().toString().trim().equals("")) {
                        PoleNumber.setError("Should give a Number !");
                        Toast.makeText(getApplicationContext(), "Should give a Name !", Toast.LENGTH_SHORT).show();

                    }  else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddPoles.this);
                        builder.setCancelable(true);
                        builder.setIcon(R.drawable.logo);
                        builder.setMessage("Do you want to save Pole Data?");
                        builder.setTitle("Save Pole");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!isConnected(AddPoles.this)){
                                            // createExcel();
                                            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            new AddPoles.AddPoleDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

        ///////////////////////////////////////////////////////////////////////////////////
    }
    private class AddPoleDB extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... urls) {

            String pole_number = PoleNumber.getText().toString();
            String no_of_stay = NoOfStay.getText().toString();
            String no_of_strut = NoOfStrut.getText().toString();

            int line_details;
            if (LineDetails.getSelectedItem().toString().equals("AAC 1ph")) {
                line_details = 1;
            } else if (LineDetails.getSelectedItem().toString().equals("AAC 2ph")) {
                line_details = 2;
            } else if (LineDetails.getSelectedItem().toString().equals("AAC 3ph")) {
                line_details = 3;
            } else if (LineDetails.getSelectedItem().toString().equals("AAC S/L")) {
                line_details = 4;
            } else if (LineDetails.getSelectedItem().toString().equals("ABC 1ph")) {
                line_details = 5;
            } else if (LineDetails.getSelectedItem().toString().equals("ABC 2ph")) {
                line_details = 6;
            } else if (LineDetails.getSelectedItem().toString().equals("ABC 3ph-4core")) {
                line_details = 7;
            }else{
                line_details=8;
            }

            int consumer_type;
            if(ConsumerType.getSelectedItem().toString()=="Single Phase"){
                consumer_type=1;
            }else{
                consumer_type=2;
            }

//            int material_type;
//            if(PoleMaterial.getSelectedItem().toString()=="a"){
//                material_type=1;
//            }else{
//                material_type=2;
//            }

            String no_of_con_1ph = NoOfConsumers1ph.getText().toString();
            String no_of_con_3ph = NoOfConsumers3ph.getText().toString();

            String gps_latitude = GpsLatitude.getText().toString();
            String gps_longitude = GpsLongitude.getText().toString();


            System.out.println("############################### Test Intialized variable ##############################################");

            System.out.println("Area: " + area);
            System.out.println("Nuber: "+ pole_number);
            System.out.println("Material: "+pole);
            System.out.println("CSC: "+csc);
            System.out.println("Transformer: "+tranformer);
            System.out.println("No of stay: "+no_of_stay);
            System.out.println("No of Strut: "+no_of_strut);
            System.out.println("Line details: "+line_details);
            System.out.println("Consumer Type 1: "+no_of_con_1ph);
            System.out.println("Consumer Type 3: "+no_of_con_3ph);
            System.out.println("Lati: "+gps_latitude);
            System.out.println("Longitude: "+gps_longitude);

            //set values to MmsAddpole  object
            MmsAddPole objAddpole = new MmsAddPole();

            objAddpole.setPoleNo(pole_number);
            objAddpole.setStatus(new BigDecimal(2));
            //objAddpole.setPoleType(pole_material);
            objAddpole.setArea(area);
            objAddpole.setCsc(csc);
            objAddpole.setTransformerId(tranformer);

            objAddpole.setPoleType(new BigDecimal(pole));

            if (no_of_stay.length() == 0) {
                objAddpole.setNoOfStay(new BigDecimal(0));
            } else {
                objAddpole.setNoOfStay(new BigDecimal(no_of_stay));
            }

            if (no_of_strut.length() == 0) {
                objAddpole.setNoOfStruts(new BigDecimal(0));
            } else {
                objAddpole.setNoOfStruts(new BigDecimal(no_of_strut));
            }

            objAddpole.setConductorType(new BigDecimal(line_details));
            objAddpole.setConductorType(new BigDecimal(consumer_type));

            if (no_of_con_1ph.length() == 0) {
                objAddpole.setNoOfConsumers1ph(new BigDecimal(0));
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+no_of_con_1ph);
            } else {
                objAddpole.setNoOfConsumers1ph(new BigDecimal(no_of_con_1ph));
                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB: "+no_of_con_1ph);
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX "+objAddpole.getNoOfConsumers1ph());
            }

            if (no_of_con_3ph.length() ==  0) {
                objAddpole.setNoOfConsumers3ph(new BigDecimal(0));
                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP "+no_of_con_3ph+" P "+objAddpole.getNoOfConsumers3ph());
            } else {
                objAddpole.setNoOfConsumers3ph(new BigDecimal(no_of_con_3ph));
                System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ"+no_of_con_3ph);
            }

            if (gps_latitude.length() == 0) {
                objAddpole.setGpsLatitude(new BigDecimal(0));
            } else {
                objAddpole.setGpsLatitude(new BigDecimal(gps_latitude));
            }

            if (gps_longitude.length() == 0) {
                objAddpole.setGpsLongitude(new BigDecimal(0));
            } else {
                objAddpole.setGpsLongitude(new BigDecimal(gps_longitude));
            }


            System.out.println("Set object testing :****************************************");
            System.out.println("Area: " + objAddpole.getArea());
            System.out.println("Nuber: "+ objAddpole.getPoleNo());
            System.out.println("Material: "+objAddpole.getPoleType());
            System.out.println("CSC: "+objAddpole.getCsc());
            System.out.println("No of stay: "+objAddpole.getNoOfStay());
            System.out.println("No of Strut: "+ objAddpole.getNoOfStruts());
            System.out.println("Line details: "+objAddpole.getPoleType());
            System.out.println("Consumer Type 1: "+objAddpole.getNoOfConsumers1ph());
            System.out.println("Consumer Type 3: "+objAddpole.getNoOfConsumers3ph());
            System.out.println("Lati: "+objAddpole.getGpsLatitude());
            System.out.println("Longitude: "+objAddpole.getGpsLongitude());

            final RestTemplate restTemplate = new RestTemplate();
            final String url1 = Util.SRT_URL + "MMSAddPoleMobile/";
            System.out.println(" url1 " + url1);
            // trustEveryone();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            //return restTemplate.getForObject(url1, String.class);
            String objReturn = restTemplate.postForObject(url1, objAddpole, String.class);
            System.out.println(" objReturn: " + objReturn);
            return objReturn;

            }
        }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_transformers, menu);
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
            Intent intent = new Intent(AddPoles.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddPoles.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddPoles.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddPoles.this, TM2.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddPoles.this, AddEquipment.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddPoles.this, Login.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(AddPoles.this, GetNearByTower.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddPoles.this, AddGantry.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addAutoRecloser) {
            Intent intent = new Intent(AddPoles.this, AddAutoRecloser.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addLBSABS) {
            Intent intent = new Intent(AddPoles.this, AddLBSABS.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(AddPoles.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(AddPoles.this, AddPoles.class);
            startActivity(intent);
        } else if (id == R.id.nav_editPoles) {
            Intent intent = new Intent(AddPoles.this, EditPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(AddPoles.this, AddTransformers.class);
            startActivity(intent);
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    ////////////\\\\\\\\\\\\\\\\\\\\\\\ L O A D  A R E A ///////////////////\\\\\\\\\\\\\\\\\\\\\\
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

            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    MmsAddArea  obj = results[c];
                    values[c] = obj.getDeptNm();
                    SpinnerMapArea.put(c,obj.getDeptId());
                }
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerArea = findViewById(R.id.spnPoleArea);
            SpinnerArea.setAdapter(adapterNs);
        }

        @Override
        protected MmsAddArea[] doInBackground(String... strings) {
            //get deptId from session manager
            SessionManager objS = new SessionManager(getBaseContext());
            String deptId = objS.getPhmBranch();
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
            SpinnerCSC = findViewById(R.id.spnCSC);
            SpinnerCSC.setAdapter(adapterPr);
        }
    }


    /////////////////////////Loac Transformers By Area ///////////////////
    private class findTransformersByArea extends AsyncTask<String, Void, PcbEquipment[]>{

        @Override
        protected PcbEquipment[] doInBackground(String... strings) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findEquiByArea/"+area+"/";
            System.out.println(url5);
            System.out.println("AREAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: "+area);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("KKKKKKKKKKKKKKKKKKKKKKK"+rest.getForObject(url5, PcbEquipment[].class));
            return rest.getForObject(url5,  PcbEquipment[].class);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(PcbEquipment[] results) {
            // ListView Item Click Listener
            System.out.println("results: " + results);
            System.out.println("results: " + results.length);
            //System.out.println("results: " + results[0]);
            System.out.println("RESULTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");

            valuesTransformer = new String[results.length];

            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    PcbEquipment obj = results[c];
                    valuesTransformer[c] = obj.getReferenceNo();
                    SpinnerMapTransformer.put(c, obj.getEquipmentId());
                }
            }
            ArrayAdapter<String> adapterTr = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesTransformer);
            adapterTr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerTransformer = findViewById(R.id.spnTransformer);
            SpinnerTransformer.setAdapter(adapterTr);
        }
    }




    /////////////////////////////////// Load Active pole types ////////////////////////////////////////////
    private class findActivePoleTypes extends AsyncTask<String, Void, MmsAddpoletype[]>{

        @Override
        protected MmsAddpoletype[] doInBackground(String... strings) {

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL+"findActivePoleTypes";
            System.out.println("FIND ACTIVEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url6,MmsAddpoletype[].class);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(MmsAddpoletype[]  results) {
            //super.onPostExecute(results);
            // ListView Item Click Listener
            String[] province;
            valuesPoleType = new String[results.length];

            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    MmsAddpoletype obj = results[c];
                    valuesPoleType[c] = obj.getPoleType();
                    SpinnerMappoleType.put(c,obj.getId());
                }
            }
            ArrayAdapter<String> adapterPoleType = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesPoleType);
            adapterPoleType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerPoleType = findViewById(R.id.spnPoleMaterial);
            SpinnerPoleType.setAdapter(adapterPoleType);
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}
