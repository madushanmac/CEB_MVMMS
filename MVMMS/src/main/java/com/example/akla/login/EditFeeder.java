package com.example.akla.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.akla.login.Util.isConnected;

public class EditFeeder extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener  {

    //load Province
    Spinner SpinnerProvince;
    HashMap<Integer, String> spinnerMapProvince = new HashMap<Integer, String>();
    String[] valuesPro = new String[]{};

    //load Area
    Spinner SpinnerArea;
    String province;
    String valuesArea[] = new String[]{};
    HashMap<Integer,String> SpinnerMapArea = new HashMap<Integer, String>();

    //load Line
    Spinner SpinnerLine;
    String area;
    String valuesLine[] = new String[]{};
    HashMap<Integer,String> SpinnerMapLine = new HashMap<Integer, String>();

    //load Gantry
    Spinner SpinnerGantry;
    String line;
    String valuesGantry[] = new String[]{};
    HashMap<Integer,Long> SpinnerMapGantry = new HashMap<Integer, Long>();

    //load Feeder
    Spinner SpinnerFeeder;
    Long gantry;
    String valuesFeeder[] = new String[]{};
    HashMap<Integer,String> SpinnerMapFeeder = new HashMap<Integer, String>();

    //load Conductor Type
    Spinner SpinnerConductorType;
    String contype;
    String valuesConTypes[] = new String[]{};
    HashMap<Integer,String> SpinnerMapConTypes = new HashMap<Integer, String>();

    //Load to spinners locally
    ArrayAdapter<CharSequence> adapter1;

    // Define for variebles for send DB
    MmsAddgantry objAddgantry;
    String code;
    MmsAddfeeder objFeeder;
    String feeder;
    String nextFeederId;
    //Radio Button Feeder Type - (Incoming / Outgoing)
    RadioGroup InOutType;
    Spinner Province;
    Spinner Area;
    Spinner Line;
    Spinner Gantry;
    EditText BayNo;
    EditText Discription;
    RadioGroup OverUnder;
    //Radio Button Feeder Type - (Overhead / Underground)
    Spinner ConductorType;
    EditText NoAR;
    EditText NoLBS;
    EditText NoABS;
    EditText NoDDLO;
    EditText NoSA;

    private ProgressDialog ProgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_feeder);

        //Radio Button Feeder Type = findViewById(R.id.spnProvince);
        InOutType = findViewById(R.id.rdbType);
        Province = findViewById(R.id.spnProvince);
        Area = findViewById(R.id.spnArea);
        //Line = findViewById(R.id.spnLine);
        Gantry = findViewById(R.id.spnGantryCode);
        BayNo = findViewById(R.id.bay_number);
        Discription = findViewById(R.id.description);
        OverUnder = findViewById(R.id.radiogroup);
        ConductorType = findViewById(R.id.conductor_type);
        NoAR = findViewById(R.id.no_ar);
        NoLBS = findViewById(R.id.no_lbs);
        NoABS = findViewById(R.id.no_abs);
        NoDDLO = findViewById(R.id.no_ddlo_sets);
        NoSA = findViewById(R.id.no_surge);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        ConductorType =(Spinner)findViewById(R.id.conductor_type);
//        adapter1 =ArrayAdapter.createFromResource(this,R.array.bus_bar_conductor,android.R.layout.simple_spinner_item);
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ConductorType.setAdapter(adapter1);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (!isConnected(EditFeeder.this)) {
            //readExcel();
            //readExcelLoadtype();
            //readExcelConType();
        } else {
            new EditFeeder.loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new EditFeeder.loadConType().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Area -- Area load by Province
        SpinnerProvince = findViewById(R.id.spnProvince);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province =idprovince;

                if(!Util.isConnected(EditFeeder.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditFeeder.loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

                if(!Util.isConnected(EditFeeder.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditFeeder.loadGantrybyArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
//                line = idline;
//
//                if(!Util.isConnected(EditFeeder.this)){
//                    //readExcelLine();
//                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
//
//                }else {
//                    new EditFeeder.loadGantryByLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                }
//            }
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        //Spinner Feeder -- Feeder load by Gantry
        SpinnerGantry = findViewById(R.id.spnGantryCode);
        SpinnerGantry.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                Long idgantry = SpinnerMapGantry.get(parent.getSelectedItemPosition());
                gantry = idgantry;
                System.out.println(idgantry);

                if(!Util.isConnected(EditFeeder.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditFeeder.loadFeederByGantry().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new EditFeeder.loadGantryObj().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Feeder
        SpinnerFeeder = findViewById(R.id.spnFeeder);
        SpinnerFeeder.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String  idgantry = SpinnerMapFeeder.get(parent.getSelectedItemPosition());
                feeder = idgantry.toString();
                System.out.println(idgantry);

                if(!Util.isConnected(EditFeeder.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditFeeder.loadFeederObj().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Conductor Type
        SpinnerConductorType = findViewById(R.id.conductor_type);
        SpinnerConductorType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String  idcontype = SpinnerMapConTypes.get(parent.getSelectedItemPosition());
                contype = idcontype;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //////////////// G E T   D E T A I L S ///////////////////////////////////////////////
        Button ButtonDetails = findViewById(R.id.detailsbtn);
        ButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
                    if(objFeeder!=null) {

                        if (objFeeder.getCode() != null) {
                            BayNo.setText(objFeeder.getCode());
                        }

                        if (objFeeder.getName() != null) {
                            Discription.setText(objFeeder.getName());
                        }

                        if (objFeeder.getNoAr() != null) {
                            NoAR.setText(objFeeder.getNoAr().toString());
                        }

                        if (objFeeder.getNoLbs() != null) {
                            NoLBS.setText(objFeeder.getNoLbs().toString());
                        }

                        if (objFeeder.getNoAbs() != null) {
                            NoABS.setText(objFeeder.getNoAbs().toString());
                        }

                        if (objFeeder.getNoDdlo() != null) {
                            NoDDLO.setText(objFeeder.getNoDdlo().toString());
                        }

                        if (objFeeder.getNoSa() != null) {
                            NoSA.setText(objFeeder.getNoSa().toString());
                        }
                    }
                }
            }
        });

        /////////////////////////Save to Database //////////////////////////////////////////////////////////////////////
        //////////////////////// Edit by Dinith 2019 - 10 - 24 /////////////////////////////////////////////////////////

        Button ButtonSave = findViewById(R.id.savebtn);
        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    if (BayNo.getText().toString().trim().equals("")) {
                        BayNo.setError("Should give a Bay Number !");
                    }  else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EditFeeder.this);
                        builder.setCancelable(true);
                        builder.setIcon(R.drawable.logo);
                        builder.setMessage("Do you want to save Feeder Data?");
                        builder.setTitle("Save Feeder");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!isConnected(EditFeeder.this)){
                                            // createExcel();
                                            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            new EditFeeder.AddFeederDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////// S A V E  D A T A ///////////// R O W D Y  - 2019 - 12 -20 //////////////////////////////////
    private class AddFeederDB extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... urls) {

            int inouttype;
            int InOutId = InOutType.getCheckedRadioButtonId();
            if(InOutId == 2131296266){
                inouttype=1;
            }else{
                inouttype=2;
            }

            String bayno = BayNo.getText().toString();

            String description = Discription.getText().toString();

            int overunder;
            int OverUnderId = OverUnder.getCheckedRadioButtonId();
            if(OverUnderId == 2131296801){
                overunder=1;
            }else{
                overunder=2;
            }


            String ar = NoAR.getText().toString();
            String lbs = NoLBS.getText().toString();
            String abs = NoABS.getText().toString();
            String ddlo = NoDDLO.getText().toString();
            String sa = NoSA.getText().toString();

            System.out.println("############################### Test Intialized variable ##############################################");
            System.out.println("In Out: "+inouttype);
            System.out.println("Province:" +province);
            System.out.println("Area: "+area);
            System.out.println("Line: "+line);
            System.out.println("Gantry:"+gantry);
            System.out.println("Con type: "+contype);
            System.out.println("Description:"+description);
            System.out.println("AR:"+ar);
            System.out.println("LBS:"+lbs);
            System.out.println("ABS:"+abs);
            System.out.println("DDLO:"+ddlo);
            System.out.println("SA:"+sa);



            objFeeder.setCode(bayno);
            objFeeder.setFeederType(new BigDecimal(overunder));
            objFeeder.setName(description);
            objFeeder.setConductor(new BigDecimal(contype));
            objFeeder.setNoAr(new BigDecimal(ar));
            objFeeder.setNoLbs(new BigDecimal(lbs));
            objFeeder.setNoAbs(new BigDecimal(abs));
            objFeeder.setNoDdlo(new BigDecimal(ddlo));
            objFeeder.setNoSa(new BigDecimal(sa));

//            MmsAddfeederPK objAddfeederPK = new MmsAddfeederPK();
//            objAddfeederPK.setGantryId(new Long(gantry));
//            objAddfeederPK.setFeederId(nextFeederId);
//            objAddfeeder.setId(objAddfeederPK);

            System.out.println("Set object testing :**********************************************");
            //System.out.println("Province :" + objAddfeeder.getProvince());
            //System.out.println("Area :" + objAddfeeder.getArea());
            //System.out.println("Line :"+objAddfeeder.getLine());
            //System.out.println("Gantry :" + objAddfeeder.getGantry());
            //System.out.println("Bay Number :" + objAddfeeder.get);
            System.out.println("Code: "+gantry);
            System.out.println("Ar: "+objFeeder.getNoAr());
            System.out.println("LBS: "+objFeeder.getNoLbs());
            System.out.println("ABS: "+objFeeder.getNoAbs());
            System.out.println("DDLO: "+objFeeder.getNoDdlo());
            System.out.println("SA: "+objFeeder.getNoSa());
            System.out.println("ID: "+objFeeder.getId());

            final RestTemplate restTemplate = new RestTemplate();
            final String url1 = Util.SRT_URL + "MMSUpdateFeederMobile";
            System.out.println(" url1 " + url1);
            // trustEveryone();
            System.out.println(" ...........................url1tttttttttttttt " );
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println(" url1ttttttttttttttgggggg " );
            //return restTemplate.getForObject(url1, String.class);
            String objReturn = restTemplate.postForObject(url1, objFeeder, String.class);
            System.out.println(" objReturn: " + objReturn );
            return objReturn;
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.apphome) {
            Intent intent = new Intent(EditFeeder.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(EditFeeder.this, GetNearByTower.class);
            startActivity(intent);

            ///////////////////////////////// PHM - LCM ////////////////////////////////////////////

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(EditFeeder.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(EditFeeder.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(EditFeeder.this, TM2.class);
            startActivity(intent);

            //////////////////////////////// PHM - SUb ////////////////////////////////////////////

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(EditFeeder.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addBusBar) {
            Intent intent = new Intent(EditFeeder.this, AddBusBar.class);
            startActivity(intent);

        } else if (id == R.id.nav_addStructual) {
            Intent intent = new Intent(EditFeeder.this, AddStructural.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantryMore) {
            Intent intent = new Intent(EditFeeder.this, AddGantryMore.class);
            startActivity(intent);

        } else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(EditFeeder.this, AddFeeder.class);
            startActivity(intent);
        } else if (id == R.id.nav_editFeeder) {
            Intent intent = new Intent(EditFeeder.this, EditFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSwitches) {
            Intent intent = new Intent(EditFeeder.this, AddSwitches.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSurge) {
            Intent intent = new Intent(EditFeeder.this, AddSurgeArrestors.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTransformersG) {
            Intent intent = new Intent(EditFeeder.this, AddTransformersG.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(EditFeeder.this, AddEquipment.class);
            startActivity(intent);

////////////////////////////////////// POLE DETAILS //////////////////////////////////////////////

        } else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(EditFeeder.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(EditFeeder.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(EditFeeder.this, AddTransformers.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(EditFeeder.this, Login.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

    }

    ////////// L O A D  P R O V I N C E /////////////////////////// R O W D Y //////////////////////////
    private class loadProvince extends AsyncTask<String, Void, Glcompm[]>{
        @Override
        protected Glcompm[] doInBackground(String... strings) {
            //get deptId from session manager
            SessionManager objS = new SessionManager(getBaseContext());
            String compId = objS.getPhmBranch();
            compId = compId.trim();

            RestTemplate rest = new RestTemplate();
            //String url6 = Util.SRT_URL+"findAllAreaNew";
            String url6 = Util.SRT_URL+"findAllProvincesNewMobile/" + compId + "/";

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url6,Glcompm[].class);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgDialog= new ProgressDialog(EditFeeder.this);
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

    /////////////////////////////////// Load Gantry by Area ////////////////////////////////////////////
    private class loadGantrybyArea extends AsyncTask<String, Void, MmsAddgantry[]>{

        @Override
        protected MmsAddgantry[] doInBackground(String... strings) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findGantryByArea/" + area+"/";
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
            SpinnerGantry = findViewById(R.id.spnGantryCode);
            SpinnerGantry.setAdapter(adapterGantry);
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
//
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
//            SpinnerGantry = findViewById(R.id.spnGantryCode);
//            SpinnerGantry.setAdapter(adapterGantry);
//        }
//    }

    ////////// L O A D   F E E D E R   B Y    G A N T R Y  /////////////////////////////////////////
    private class loadFeederByGantry extends AsyncTask<String, Void, MmsAddfeeder[]> {
        @Override
        protected MmsAddfeeder[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findFeederyById/" +gantry ;
            System.out.println("GANTRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY     : "+gantry);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5, MmsAddfeeder[].class);
        }

        protected void onPreExecute() {

            super.onPreExecute();
        }

        protected void onPostExecute(MmsAddfeeder[] results) {
            // ListView Item Click Listener
            System.out.println("results" + results);
            System.out.println("results length" + results.length);
            String[] gantry;
            valuesFeeder = new String[results.length];

            List<String> valueFeederList = new ArrayList<>();
            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddfeeder obj = results[c];
                    System.out.println("name = " + obj.getName() + ", gantry = " + obj.getCode());
//                    valuesFeeder[c] = obj.getName();
                    if (obj.getName() != null && obj.getCode() != null) {
                        System.out.println("FEEDEERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: "+obj.getCode());
                        valueFeederList.add(obj.getCode());
                        SpinnerMapFeeder.put(c, obj.getId().getFeederId());
                    }
                }
            }
            ArrayAdapter<String> adapterFeeder = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valueFeederList.toArray(new String[]{}));
            adapterFeeder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerFeeder = findViewById(R.id.spnFeeder);
            SpinnerFeeder.setAdapter(adapterFeeder);
        }
    }

    /////// L O A D   C O N D U C T O R   T Y P E ///////////////////////////////////
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
            SpinnerConductorType = findViewById(R.id.conductor_type);
            SpinnerConductorType.setAdapter(adapterNs);

        }

    }


    ////////// L O A D   G A N T R Y /////////////////////////////////////////////////////////
    private class loadGantryObj extends AsyncTask<String, Void, MmsAddgantry> {

        @Override
        protected MmsAddgantry doInBackground(String... urls) {

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "findGantryById/" + gantry + "/";
            System.out.println(gantry);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            objAddgantry =  rest.getForObject(url6, MmsAddgantry.class);;
            System.out.println(objAddgantry.getPhmBranch());

            return rest.getForObject(url6, MmsAddgantry.class);
        }
    }

    ////////// L O A D   F E E D E R  O B J E C T  /////////////////////////////////////////////////////////
    private class loadFeederObj extends AsyncTask<String, Void, MmsAddfeeder> {

        @Override
        protected MmsAddfeeder doInBackground(String... urls) {

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "findFeederById/" + feeder;
            System.out.println(gantry);

            System.out.println("FEDEERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: "+feeder);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            objFeeder =  rest.getForObject(url6, MmsAddfeeder.class);;
            System.out.println("XXXXXXXXXXXXXXXXXXX"+objFeeder.getCode());

            ProgDialog.dismiss();

            return rest.getForObject(url6, MmsAddfeeder.class);
        }
    }
}
