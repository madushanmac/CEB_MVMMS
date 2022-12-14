package com.example.akla.login;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.example.akla.login.Util.isConnected;

public class EditTransformersG extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

    //load Trans
    Spinner SpinnerTrans;
    String gantry;
    String valuesTrans[] = new String[]{};
    HashMap<Integer,String> SpinnerMapTrans = new HashMap<Integer, String >();

    String trans;

    Button btn_date;
    EditText in_date;
    private int mYear, mMonth, mDay;

    Button btn_date1;
    EditText in_date1;
    private int mYear1, mMonth1, mDay1;

    String code;
    String nextTransformer;
    MmsAddtransformer objTrans;

    Spinner Province;
    Spinner Area;
    Spinner Gantry;
    EditText TransformersID;
    EditText BrandName;
    EditText Model;
    EditText SerialNo;
    EditText DateOfManu;
    EditText DateOfComm;
    EditText kVA;
    EditText VoltageRatio;

    private ProgressDialog ProgDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transformers_g);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Province = findViewById(R.id.spnProvince);
        Area = findViewById(R.id.spnArea);
        Gantry = findViewById(R.id.spnGantryCode);
        TransformersID = findViewById(R.id.etID);
        BrandName = findViewById(R.id.etBrandName);
        Model = findViewById(R.id.etModel);
        SerialNo = findViewById(R.id.etSerialNo);
        DateOfManu = findViewById(R.id.in_date);
        DateOfComm = findViewById(R.id.in_date2);
        kVA = findViewById(R.id.etkVA);
        VoltageRatio = findViewById(R.id.etVoltageRatio);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (!isConnected(EditTransformersG.this)) {
            //readExcel();
            //readExcelLoadtype();
            //readExcelConType();
        } else {
            new EditTransformersG.loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new EditTransformersG.getNextTransformerID().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Area -- Area load by Province
        SpinnerProvince = findViewById(R.id.spnProvince);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province =idprovince;

                if(!Util.isConnected(EditTransformersG.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditTransformersG.loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

                if(!Util.isConnected(EditTransformersG.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditTransformersG.loadGantrybyArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //Spinner Gantry
        SpinnerGantry = findViewById(R.id.spnGantryCode);
        SpinnerGantry.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                Long idgantry = SpinnerMapGantry.get(parent.getSelectedItemPosition());
                code = idgantry.toString();

                if(!Util.isConnected(EditTransformersG.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditTransformersG.loadTransbyGantry().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Surge
        SpinnerTrans = findViewById(R.id.spnTransformer);
        SpinnerTrans.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String  idtrans = SpinnerMapTrans.get(parent.getSelectedItemPosition());
                trans = idtrans;

                if(!Util.isConnected(EditTransformersG.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditTransformersG.loadtransObj().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
//                if(!Util.isConnected(AddTransformersG.this)){
//                    //readExcelLine();
//                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
//
//                }else {
//                    new AddTransformersG.loadGantryByLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                }
//            }
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        btn_date = (Button)findViewById(R.id.btnDateManufacturing);
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


                    DatePickerDialog datePickerDialog = new DatePickerDialog(EditTransformersG.this,
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

        btn_date1 = (Button)findViewById(R.id.btnDateCommissioning);
        in_date1 = (EditText) findViewById(R.id.in_date2);
        btn_date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btn_date1) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear1 = c.get(Calendar.YEAR);
                    mMonth1 = c.get(Calendar.MONTH);
                    mDay1 = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(EditTransformersG.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    in_date1.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                }
                            }, mYear1, mMonth1, mDay1);
                    datePickerDialog.show();
                }
            }
        });

        //////////////// G E T   D E T A I L S ///////////////////////////////////////////////
        Button ButtonDetails = findViewById(R.id.btnGetDetails);
        ButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
                    if(objTrans!=null) {
                        if (objTrans.getBrandName() != null) {
                            BrandName.setText(objTrans.getBrandName().toString());
                        }

                        if (objTrans.getModel() != null) {
                            Model.setText(objTrans.getModel().toString());
                        }

                        if (objTrans.getSerialNo() != null) {
                            SerialNo.setText(objTrans.getSerialNo());
                        }

                        if (objTrans.getDateOfManufacturing() != null) {
                            DateOfManu.setText((objTrans.getDateOfManufacturing().getYear() - 100 + 2000)
                                    + "-" + (objTrans.getDateOfManufacturing().getMonth() + 1)
                                    + "-" + (objTrans.getDateOfManufacturing().getDate()));
                        }

                        if (objTrans.getDateOfCommissioning() != null) {
                            DateOfComm.setText((objTrans.getDateOfCommissioning().getYear() - 100 + 2000)
                                    + "-" + (objTrans.getDateOfCommissioning().getMonth() + 1)
                                    + "-" + (objTrans.getDateOfCommissioning().getDate()));
                        }

                        if (objTrans.getKva() != null) {
                            kVA.setText(objTrans.getKva().toString());
                        }

                        if (objTrans.getVoltageRatio() != null) {
                            VoltageRatio.setText(objTrans.getVoltageRatio().toString());
                        }
                    }
                }
            }
        });

        ///////////////////////////////// S E N D   T O   D B /////////////////////////////////////////////////////////
        Button ButtonSendDB = findViewById(R.id.savebtn);
        ButtonSendDB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                System.out.println("SEND TO DB BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");

                AlertDialog.Builder builder = new AlertDialog.Builder(EditTransformersG.this);
                builder.setCancelable(true);
                builder.setIcon(R.drawable.logo);
                builder.setMessage("Do you want to save Transformer Data?");
                builder.setTitle("Save Transformer Details");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new EditTransformersG.AddTransformerDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
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
        });
    }

    //////////////////////////////////////// AddGantryDB  Edit Dinith////////////////////////////////////////////////////////////////

    private class AddTransformerDB extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {

            String brandName = BrandName.getText().toString();
            String model = Model.getText().toString();
            String serialNo = SerialNo.getText().toString();
            String dateOfManu = DateOfManu.getText().toString();
            String dateOfComm = DateOfComm.getText().toString();
            String kva = kVA.getText().toString();
            String voltageRatio = VoltageRatio.getText().toString();


            System.out.println("############################### Test Intialized variable ##############################################");

            System.out.println("Brand Name:" + brandName);
            System.out.println("Model: "+ model);
            System.out.println("Serial No: "+ serialNo);
            System.out.println("Date of Manu: "+ dateOfManu);
            System.out.println("Date of Comm: " + dateOfComm);
            System.out.println("kVA: "+ kva);
            System.out.println("Voltage Ratio: "+ voltageRatio);

            //set values to MmsAddgantry object
            //MmsAddtransformer objAddtransformer = new MmsAddtransformer();

            if(brandName.length()==0){
                objTrans.setBrandName(new BigDecimal(0));;
            }else{
                objTrans.setBrandName(new BigDecimal(brandName));
            }

            if(model.length()==0){
                objTrans.setModel(new BigDecimal(0));;
            }else{
                objTrans.setModel(new BigDecimal(model));
            }

            if(serialNo.length()==0){
                objTrans.setSerialNo("-");;
            }else{
                objTrans.setSerialNo(serialNo);
            }


            Date dateNow = null;
            try {
                dateNow = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfManu);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objTrans.setDateOfManufacturing(dateNow);


            Date dateNow1 = null;
            try {
                dateNow1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfComm);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objTrans.setDateOfCommissioning(dateNow1);


            if(kva.length()==0){
                objTrans.setKva(new BigDecimal(0));
            }else{
                objTrans.setKva(new BigDecimal(kva));
            }

            if(voltageRatio.length()==0){
                objTrans.setVoltageRatio(new BigDecimal(0));
            }else{
                objTrans.setVoltageRatio(new BigDecimal(voltageRatio));
            }


            System.out.println("Set object testing :****************************************");
            System.out.println("Brand Name:" + objTrans.getBrandName());
            System.out.println("Model: "+objTrans.getModel());
            System.out.println("Serial No: "+objTrans.getSerialNo());
            System.out.println("Date of Manu: "+objTrans.getDateOfManufacturing());
            System.out.println("Date of Comm: "+objTrans.getDateOfCommissioning());
            System.out.println("kVA: "+objTrans.getKva());
            System.out.println("Voltage Ratio: "+objTrans.getVoltageRatio());
            System.out.println("ID: "+objTrans.getId());

            final RestTemplate restTemplate = new RestTemplate();
            final String url1 = Util.SRT_URL + "MMSUpdateTransformerMobile";
            System.out.println(" url1 " + url1);
            // trustEveryone();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            //return restTemplate.getForObject(url1, String.class);
            String objReturn = restTemplate.postForObject(url1, objTrans, String.class);
            System.out.println(" objReturn: " + objReturn );
            return objReturn;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_transformers_g, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.apphome) {
            Intent intent = new Intent(EditTransformersG.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(EditTransformersG.this, GetNearByTower.class);
            startActivity(intent);

            //////////////////////////////// PHM - LCM ////////////////////////////////////////////

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(EditTransformersG.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(EditTransformersG.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(EditTransformersG.this, TM2.class);
            startActivity(intent);

            //////////////////////////////// PHM - SUb ////////////////////////////////////////////

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(EditTransformersG.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addBusBar) {
            Intent intent = new Intent(EditTransformersG.this, AddBusBar.class);
            startActivity(intent);

        } else if (id == R.id.nav_addStructual) {
            Intent intent = new Intent(EditTransformersG.this, AddStructural.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantryMore) {
            Intent intent = new Intent(EditTransformersG.this, AddGantryMore.class);
            startActivity(intent);

        } else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(EditTransformersG.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSwitches) {
            Intent intent = new Intent(EditTransformersG.this, AddSwitches.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSurge) {
            Intent intent = new Intent(EditTransformersG.this, AddSurgeArrestors.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTransformersG) {
            Intent intent = new Intent(EditTransformersG.this, AddTransformersG.class);
            startActivity(intent);

        } else if (id == R.id.nav_editTransformersG) {
            Intent intent = new Intent(EditTransformersG.this, EditTransformersG.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(EditTransformersG.this, AddEquipment.class);
            startActivity(intent);

///////////////////// POLE DETAILS //////////////////////////////////////////////

        } else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(EditTransformersG.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(EditTransformersG.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(EditTransformersG.this, AddTransformers.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(EditTransformersG.this, Login.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            ProgDialog= new ProgressDialog(EditTransformersG.this);
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
//                    SpinnerMapGantry.put(c, obj.getCode());
//                }
//            }
//            ArrayAdapter<String> adapterGantry = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesGantry);
//            adapterGantry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            SpinnerGantry = findViewById(R.id.spnGantryCode);
//            SpinnerGantry.setAdapter(adapterGantry);
//        }
//    }

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

    /////////////////////////////////// Load transformer by Gantry ////////////////////////////////////////////

    private class loadTransbyGantry extends AsyncTask<String, Void, MmsAddtransformer[]>{

        @Override
        protected MmsAddtransformer[] doInBackground(String... strings) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findTrByGantryId/" +code+"/";
            System.out.println("AREAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: "+code);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5, MmsAddtransformer[].class);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(MmsAddtransformer[] results) {
            // ListView Item Click Listener
            System.out.println("results: " + results);
            System.out.println("results: " + results.length);
            //System.out.println("results: " + results[0]);
            System.out.println("RESULTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");

            valuesTrans = new String[results.length];

            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddtransformer obj = results[c];
                    valuesTrans[c] = obj.getId().getTrId();
                    SpinnerMapTrans.put(c, obj.getId().getTrId());
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesTrans);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerTrans = findViewById(R.id.spnTransformer);
            SpinnerTrans.setAdapter(adapter);
        }
    }


    ////////// L O A D   T R A N S    O B J E C T  /////////////////////////////////////////////////////////
    private class loadtransObj extends AsyncTask<String, Void, MmsAddtransformer> {

        @Override
        protected MmsAddtransformer doInBackground(String... urls) {

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "findTrByGantryIdTrId/" + code + "/"+trans + "/";
            System.out.println("CODEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE"+code);
            System.out.println("SURGEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE"+trans);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
            //System.out.println(rest.getForObject(url6, MmsAddgantry.class));

            objTrans =  rest.getForObject(url6, MmsAddtransformer.class);;
            System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE"+objTrans.getId());

            ProgDialog.dismiss();
            return rest.getForObject(url6, MmsAddtransformer.class);
        }
    }


    //////////////////// L O A D  Transformer  //// R O W D Y ////////////////////////////////
    private class getNextTransformerID extends AsyncTask<String, Void, String > {

        @Override
        protected String doInBackground(String... strings) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "getNextTrId";


            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5, String.class);
        }
        protected void onPreExecute() {

            super.onPreExecute();
        }

        protected void onPostExecute(String results) {
            System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZzzz");
            System.out.println("results" + results);
            nextTransformer = results;
            System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK"+nextTransformer);

            TransformersID = findViewById(R.id.etID);
            //TransformersID.setText(nextTransformer);
        }
    }
}
