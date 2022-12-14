package com.example.akla.login;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.example.akla.login.Util.isConnected;

public class AddLVFeeders extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    //load Area
    Spinner SpinnerArea;
    String[] values = new String[]{};
    HashMap<Integer, String> SpinnerMapArea = new HashMap<Integer, String>();

    //load pole type
    Spinner SpinnerTransformer;
    String[] valuesTransformer = new String[]{};
    HashMap<Integer, String> SpinnerMapTransformer = new HashMap<Integer, String>();

    //load  type
    Spinner SpinnerType;
    String[] valuesTypes = new String[]{};
    HashMap<Integer, String> SpinnerMapTypes = new HashMap<Integer, String>();

    //load conductor type
    Spinner SpinnerConductorType;
    String[] valuesConTypes = new String[]{};
    HashMap<Integer, String> SpinnerMapConTypes = new HashMap<Integer, String>();

    String area;
    String tranformer;
    String type;
    String contype;
    long trn;
    String nextFeederId;

    //Intialize Variebles
    Spinner Area;
    Spinner Transformer;
    EditText FeederCode;
    EditText FeederName;
    Spinner FeederType;
    EditText Length;
    EditText NoOfPoles;
    EditText NoOfTowers;
    EditText ComDate;
    Spinner ConductorType;
    Spinner FeederIdentification;

    //Circuit type
    RadioGroup radioGroupCircuitType;
    private RadioButton radioButtonCircuitType;

    Spinner SpinnerFeederIdentification;
    EditText EnterFeeder;
    String feeder;

    //Calender Details
    Button btn_date;
    EditText in_date;
    private int mYear, mMonth, mDay;

    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lvfeeders);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (!isConnected(AddLVFeeders.this)) {
        } else {
            new AddLVFeeders.loadarea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new AddLVFeeders.loadtype().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new AddLVFeeders.loadConType().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Line -- Line load by Area
        SpinnerArea = findViewById(R.id.spnPoleArea);
        SpinnerArea.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idarea = SpinnerMapArea.get(parent.getSelectedItemPosition());
                area = idarea;

                if(!Util.isConnected(AddLVFeeders.this)){
                }else {
                    new AddLVFeeders.findTransformersByArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    //new AddLVFeeders.getNextFeederID().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
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
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Type
        SpinnerType = findViewById(R.id.SpinnerType);
        SpinnerType.setPrompt("Select Type");
        SpinnerType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idtype = SpinnerMapTypes.get(parent.getSelectedItemPosition());
                type = idtype;
            }


            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //Spinner Conductor Type
        SpinnerConductorType = findViewById(R.id.spinnerConType);
        SpinnerConductorType.setPrompt("Select Conductor Type");
        SpinnerConductorType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idConType = SpinnerMapConTypes.get(parent.getSelectedItemPosition());
                contype = idConType;

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Feeder Identification
        SpinnerFeederIdentification = findViewById(R.id.spinnerFeeder);
        EnterFeeder = findViewById(R.id.etFeederHide);
        feeder = SpinnerFeederIdentification.getSelectedItem().toString();
        SpinnerFeederIdentification.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(SpinnerFeederIdentification.getSelectedItem().toString().equals("Other")) {
                    EnterFeeder.setVisibility(View.VISIBLE);

                }
                else {
                    EnterFeeder.setVisibility(View.INVISIBLE);
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Com date
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


                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddLVFeeders.this,
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



        /////////////////////////Save to Database ////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////// R O W D Y  ////////// 2019 - 12 - 29 ///////////////////////////////////////////////////////////////////////////////

        Button ButtonSave = findViewById(R.id.btnSaveDB);
        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
                    Area = findViewById(R.id.spnPoleArea);
                    Transformer = findViewById(R.id.spnTransformer);
                    FeederCode = findViewById(R.id.etFeederCode);
                    FeederName = findViewById(R.id.etFeederName);
                    FeederType = findViewById(R.id.SpinnerType);
                    Length = findViewById(R.id.etLength);
                    NoOfPoles = findViewById(R.id.etNoOfPoles);
                    NoOfTowers = findViewById(R.id.etNoOfTowers);
                    ComDate = findViewById(R.id.in_date);
                    ConductorType = findViewById(R.id.spinnerConType);
                    FeederIdentification = findViewById(R.id.spinnerFeeder);


                    if (FeederCode.getText().toString().trim().equals("")) {
                        FeederCode.setError("Should give a Code !");
                        Toast.makeText(getApplicationContext(), "Should give a Code !", Toast.LENGTH_SHORT).show();

                    }  else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddLVFeeders.this);
                        builder.setCancelable(true);
                        builder.setIcon(R.drawable.logo);
                        builder.setMessage("Do you want to save LV Feeder Details ?");
                        builder.setTitle("Save Pole");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!isConnected(AddLVFeeders.this)){
                                            // createExcel();
                                            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            new AddLVFeeders.AddFeederDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
    private class AddFeederDB extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... urls) {

            String feeder_code = FeederCode.getText().toString();
            String feeder_name = FeederName.getText().toString();
            String length = Length.getText().toString();
            String no_of_poles = NoOfPoles.getText().toString();
            String no_of_towers = NoOfTowers.getText().toString();
            String com_date = ComDate.getText().toString();

            //Circuit type
            radioGroupCircuitType = findViewById(R.id.rgCircuitType);
            final int radioButtonId = radioGroupCircuitType.getCheckedRadioButtonId();
            System.out.println("radioGroupCircuitType" + radioGroupCircuitType);
            radioButtonCircuitType = findViewById(radioButtonId);
            String selectedCircuitType = radioButtonCircuitType.getText().toString();


            Date dateNow = null;
            try {
                dateNow = new SimpleDateFormat("yyyy-MM-dd").parse(com_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String CircuitType = "";
            if (selectedCircuitType.equalsIgnoreCase("Single")) {
                CircuitType = "1";
            }
            else if(selectedCircuitType.equalsIgnoreCase("Double")){
                CircuitType = "2";
            }else if(selectedCircuitType.equalsIgnoreCase("Four")){
                CircuitType = "4";
            }else{
                CircuitType = "3";
            }
            System.out.println("selectedNoOfCircuitsRadio" + CircuitType);



            System.out.println("############################### Test Intialized variable ##############################################");

            System.out.println("Area: " + area);
            System.out.println("Transormer: "+tranformer);
            System.out.println("Name: "+feeder_name);
            System.out.println("Code: "+feeder_code);
            System.out.println("Type: "+type);
            System.out.println("Length: "+length);
            System.out.println("No of Poles: "+no_of_poles);
            System.out.println("No of Towers: "+no_of_towers);
            System.out.println("Com Date: "+dateNow);
            System.out.println("Conductor Type: "+contype);
            System.out.println("Circuit Type: ");
            System.out.println("Feeder Identification: ");

            //set values to MmsAddpole  object
            MmsAddline objAddlvfeeder = new MmsAddline();

            objAddlvfeeder.setArea(area);
            objAddlvfeeder.setCode(feeder_code);
            objAddlvfeeder.setName(feeder_name);
            objAddlvfeeder.setStatus(new BigDecimal(2));
            objAddlvfeeder.setType(new BigDecimal(1));
            objAddlvfeeder.setLineType(type);
            objAddlvfeeder.setLength(new BigDecimal(length));
            objAddlvfeeder.setNoofpoles(new BigDecimal(no_of_poles));
            objAddlvfeeder.setNooftowers(new BigDecimal(no_of_towers));
            objAddlvfeeder.setComdate(dateNow);
            objAddlvfeeder.setConductorType(new BigDecimal(contype));
            objAddlvfeeder.setCircuitType(new BigDecimal(selectedCircuitType));
            objAddlvfeeder.setFeederIdentification(feeder);

            MmsAddlvfeederPK objId = new MmsAddlvfeederPK();
            objId.setFeederId(nextFeederId);
            objId.setSubId(tranformer);

            //objAddlvfeeder.setId(objId);


            System.out.println("Set object testing :****************************************");
            System.out.println("Area: " + objAddlvfeeder.getArea());
            System.out.println("Code: "+ objAddlvfeeder.getCode());
            System.out.println("Name: "+objAddlvfeeder.getName());
            System.out.println("Status: "+objAddlvfeeder.getStatus());
            System.out.println("Id: "+objAddlvfeeder.getId());


            final RestTemplate restTemplate = new RestTemplate();
            final String url1 = Util.SRT_URL + "MMSAddLineMobile/";
            System.out.println(" url1 " + url1);
            // trustEveryone();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            //return restTemplate.getForObject(url1, String.class);
            String objReturn = restTemplate.postForObject(url1, objAddlvfeeder, String.class);
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
            Intent intent = new Intent(AddLVFeeders.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddLVFeeders.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddLVFeeders.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddLVFeeders.this, TM2.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddLVFeeders.this, AddEquipment.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddLVFeeders.this, Login.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(AddLVFeeders.this, GetNearByTower.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddLVFeeders.this, AddGantry.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addAutoRecloser) {
            Intent intent = new Intent(AddLVFeeders.this, AddAutoRecloser.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addLBSABS) {
            Intent intent = new Intent(AddLVFeeders.this, AddLBSABS.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(AddLVFeeders.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(AddLVFeeders.this, AddPoles.class);
            startActivity(intent);
        } else if (id == R.id.nav_editPoles) {
            Intent intent = new Intent(AddLVFeeders.this, EditPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(AddLVFeeders.this, AddTransformers.class);
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


    ////////////////////////// L O A D     T Y P E  //////////////////////////////
    private class loadtype extends AsyncTask<String, Void, MmsAddlinetype[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected MmsAddlinetype[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url8 = Util.SRT_URL+"findActiveLineTypes";

            System.out.println("ssss" +url8);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url8,MmsAddlinetype[].class);
            //return List<MmsAddArea>null;
        }


        protected void onPostExecute(MmsAddlinetype[] results) {
            // ListView Item Click Listener
            System.out.println("results" +results);
            System.out.println("results" +results.length);
            String[] area;
            valuesTypes = new String[results.length];

            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    MmsAddlinetype  obj = results[c];
                    valuesTypes[c] = obj.getLineType();
                    SpinnerMapTypes.put(c,obj.getId());


                }
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesTypes);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerType = findViewById(R.id.SpinnerType);
            SpinnerType.setAdapter(adapterNs);

        }

    }

    ////////////// LOAD CON TYPE ///////////////////////////////////

    private class loadConType extends AsyncTask<String, Void, MmsAddconductortype[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected MmsAddconductortype[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url8 = Util.SRT_URL+"findActiveConductorTypes";

            System.out.println("ssss" +url8);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url8,MmsAddconductortype[].class);
            //    return List<MmsAddArea>null;
        }

        protected void onPostExecute(MmsAddconductortype[] results) {
            // ListView Item Click Listener
            System.out.println("results" +results);
            System.out.println("results" +results.length);
            String[] ConType;
            valuesConTypes = new String[results.length];


            if(results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddconductortype obj = results[c];
                    System.out.println("resultsyyyyyyywer :" + c);
                    if (obj != null) {
                        valuesConTypes[c] = String.valueOf(obj.getType());
                        SpinnerMapConTypes.put(c, obj.getId());

                        System.out.println("resultsyyyyyyywergh " );
                    }
                }
                System.out.println("resultsyyyyyyywerh " );
            }


            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesConTypes);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerConductorType = findViewById(R.id.spinnerConType);
            SpinnerConductorType.setAdapter(adapterNs);


        }

    }


    //////////////////// L O A D  NEXT FEEDER ID \\\\\  R O W D Y ////////////////////////////////
    private class getNextFeederID extends AsyncTask<String, Void, String > {

        @Override
        protected String doInBackground(String... strings) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "getNextLVFeederId";

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5, String.class);
        }
        protected void onPreExecute() {

            super.onPreExecute();
        }

        protected void onPostExecute(String results) {
            System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZzzz");
            System.out.println("results" + results);
            nextFeederId = results;
        }
    }

}
