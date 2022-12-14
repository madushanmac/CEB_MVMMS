package com.example.akla.login;

import android.app.AlertDialog;
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

public class EditSurgeArrestors extends AppCompatActivity
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

    //load Surge
    Spinner SpinnerSurge;
    String gantry;
    String valuesSurge[] = new String[]{};
    HashMap<Integer,String> SpinnerMapSurge = new HashMap<Integer, String >();

    String code;
    String surge;
    String nextSurgeArrestors;

    //Calender Details
    Button btn_date;
    EditText in_date;
    private int mYear, mMonth, mDay;

    //Load object
    MmsAddsurgearrestor objSurge;
    //Define Variebles for save data in db
    Spinner Province;
    Spinner Area;
    Spinner Gantry;
    EditText Brand;
    EditText Rating;
    EditText DateOfManufac;
    EditText Quantity;

    private ProgressDialog ProgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_surge_arrestors);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Province = findViewById(R.id.spnProvince);
        Area = findViewById(R.id.spnArea);
        Gantry = findViewById(R.id.spnGantryCode);
        Brand = findViewById(R.id.brand);
        Rating = findViewById(R.id.rating);
        DateOfManufac = findViewById(R.id.in_date);
        Quantity = findViewById(R.id.quantity);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (!isConnected(EditSurgeArrestors.this)) {
            //readExcel();
            //readExcelLoadtype();
            //readExcelConType();
        } else {
            new EditSurgeArrestors.loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new EditSurgeArrestors.getNextSurgeID().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Area -- Area load by Province
        SpinnerProvince = findViewById(R.id.spnProvince);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province =idprovince;

                if(!Util.isConnected(EditSurgeArrestors.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditSurgeArrestors.loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

                if(!Util.isConnected(EditSurgeArrestors.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditSurgeArrestors.loadGantrybyArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
//                if(!Util.isConnected(EditSurgeArrestors.this)){
//                    //readExcelLine();
//                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
//
//                }else {
//                    new EditSurgeArrestors.loadGantryByLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                }
//            }
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        //Spinner Gantry
        SpinnerGantry = findViewById(R.id.spnGantryCode);
        SpinnerGantry.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                Long idgantry = SpinnerMapGantry.get(parent.getSelectedItemPosition());
                code = idgantry.toString();

                if(!Util.isConnected(EditSurgeArrestors.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditSurgeArrestors.loadSurgebyGantry().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Surge
        SpinnerSurge = findViewById(R.id.spnSurge);
        SpinnerSurge.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String  idsurge = SpinnerMapSurge.get(parent.getSelectedItemPosition());
                surge = idsurge;

                if(!Util.isConnected(EditSurgeArrestors.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditSurgeArrestors.loadSurgeObj().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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


                    DatePickerDialog datePickerDialog = new DatePickerDialog(EditSurgeArrestors.this,
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

        //////////////// G E T   D E T A I L S ///////////////////////////////////////////////
        Button ButtonDetails = findViewById(R.id.btnGetDetails);
        ButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
                    if (objSurge != null) {
                        if (objSurge.getBrand() != null) {
                            Brand.setText(objSurge.getBrand());
                        }

                        if (objSurge.getRating() != null) {
                            Rating.setText(objSurge.getRating());
                        }

                        if (objSurge.getDateOfManufacture() != null) {
                            DateOfManufac.setText((objSurge.getDateOfManufacture().getYear() - 100 + 2000)
                                    + "-" + (objSurge.getDateOfManufacture().getMonth() + 1)
                                    + "-" + (objSurge.getDateOfManufacture().getDate()));
                        }

                        if (objSurge.getQuantity() != null) {
                            Quantity.setText(objSurge.getQuantity().toString());
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
                    if (Brand.getText().toString().trim().equals("")) {
                        Brand.setError("Should give a Brand Name !");
                        Toast.makeText(getApplicationContext(), "Should give a Brand Name !", Toast.LENGTH_SHORT).show();
                    }else if (Rating.getText().toString().trim().equals("")){
                        Rating.setError("Should give a rating !");
                        Toast.makeText(getApplicationContext(), "Should give a Rating !", Toast.LENGTH_SHORT).show();
                    }  else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditSurgeArrestors.this);
                        builder.setCancelable(true);
                        builder.setIcon(R.drawable.logo);
                        builder.setMessage("Do you want to save Surge Arrestors?");
                        builder.setTitle("Save Surge Arrestors");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!isConnected(EditSurgeArrestors.this)){
                                            // createExcel();
                                            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            new EditSurgeArrestors.AddSurgeDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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


        //////////////////////////////////////////////////////////////////////////

    }

    //////////////////////////////////////// AddGantryDB  Edit Dinith////////////////////////////////////////////////////////////////

    private class AddSurgeDB extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... urls) {

            String brand = Brand.getText().toString();
            String rating = Rating.getText().toString();
            String date_of_manufac = DateOfManufac.getText().toString();
            String quantity = Quantity.getText().toString();

            System.out.println("############################### Test Intialized variable ##############################################");

            System.out.println("Province :" + province);
            System.out.println("Area: "+area);
            System.out.println("Brand: "+brand);
            System.out.println("Rating: "+rating);
            System.out.println("Date:"+date_of_manufac);
            System.out.println("Quantity: "+quantity);

            //set values to MmsEditSurgeArrestors object
            //MmsAddsurgearrestor objAddsurge = new MmsAddsurgearrestor();

            if(brand.length()==0){
                objSurge.setBrand("-");;
            }else{
                objSurge.setBrand(brand);;
            }

            Date dateNow = null;
            try {
                dateNow = new SimpleDateFormat("yyyy-MM-dd").parse(date_of_manufac);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objSurge.setDateOfManufacture(dateNow);


            if(quantity.length()==0){
                objSurge.setQuantity(new BigDecimal(0));
            }else{
                objSurge.setQuantity(new BigDecimal(quantity));
            }


            if(rating.length()==0){
                objSurge.setRating("-");
            }else{
                objSurge.setRating(rating);
            }


            System.out.println("Set object testing :****************************************");
            System.out.println("Brand: "+objSurge.getBrand());
            System.out.println("Rating :" +objSurge.getRating());
            System.out.println("Date of Manu :" + objSurge.getDateOfManufacture());
            System.out.println("Quantity :"+objSurge.getQuantity());

            final RestTemplate restTemplate = new RestTemplate();
            final String url1 = Util.SRT_URL + "MMSUpdateSaMobile/";
            System.out.println(" url1 " + url1);
            // trustEveryone();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            String objReturn = restTemplate.postForObject(url1, objSurge, String.class);
            System.out.println(" objReturn: " + objReturn );
            return objReturn;

        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////

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
        getMenuInflater().inflate(R.menu.add_surge_arrestors, menu);
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
            Intent intent = new Intent(EditSurgeArrestors.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(EditSurgeArrestors.this, GetNearByTower.class);
            startActivity(intent);

            //////////////////////////////// PHM - LCM ////////////////////////////////////////////

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(EditSurgeArrestors.this, TM2.class);
            startActivity(intent);

            //////////////////////////////// PHM - SUb ////////////////////////////////////////////

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addBusBar) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddBusBar.class);
            startActivity(intent);

        } else if (id == R.id.nav_addStructual) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddStructural.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantryMore) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddGantryMore.class);
            startActivity(intent);

        } else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSwitches) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddSwitches.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSurge) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddSurgeArrestors.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTransformersG) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddTransformersG.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddEquipment.class);
            startActivity(intent);

///////////////////// POLE DETAILS //////////////////////////////////////////////

        } else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(EditSurgeArrestors.this, AddTransformers.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(EditSurgeArrestors.this, Login.class);
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
            ProgDialog= new ProgressDialog(EditSurgeArrestors.this);
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

    /////////////////////////////////// Load Surge by Gantry ////////////////////////////////////////////

    private class loadSurgebyGantry extends AsyncTask<String, Void, MmsAddsurgearrestor[]>{

        @Override
        protected MmsAddsurgearrestor[] doInBackground(String... strings) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findSaByGantryId/" +code+"/";
            System.out.println("AREAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: "+code);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5, MmsAddsurgearrestor[].class);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(MmsAddsurgearrestor[] results) {
            // ListView Item Click Listener
            System.out.println("results: " + results);
            System.out.println("results: " + results.length);
            //System.out.println("results: " + results[0]);
            System.out.println("RESULTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");

            valuesSurge = new String[results.length];

            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddsurgearrestor obj = results[c];
                    valuesSurge[c] = obj.getId().getSaId();
                    SpinnerMapSurge.put(c, obj.getId().getSaId());
                }
            }
            
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesSurge);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerSurge = findViewById(R.id.spnSurge);
            SpinnerSurge.setAdapter(adapter);
        }
    }


    ////////// L O A D   S U R G E   O B J E C T  /////////////////////////////////////////////////////////
    private class loadSurgeObj extends AsyncTask<String, Void, MmsAddsurgearrestor> {

        @Override
        protected MmsAddsurgearrestor doInBackground(String... urls) {

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "findSaByGantryIdSaId/" + code + "/"+surge + "/";
            System.out.println("CODEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE"+code);
            System.out.println("SURGEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE"+surge);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
            //System.out.println(rest.getForObject(url6, MmsAddgantry.class));

            objSurge =  rest.getForObject(url6, MmsAddsurgearrestor.class);;
            System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE"+objSurge.getId());

            ProgDialog.dismiss();

            return rest.getForObject(url6, MmsAddsurgearrestor.class);
        }
    }

    //////////////////// L O A D  S U R G E   A R E S T O R S  //// R O W D Y ////////////////////////////////
    private class getNextSurgeID extends AsyncTask<String, Void, String > {

        @Override
        protected String doInBackground(String... strings) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "getNextSwitchesId";

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5, String.class);
        }
        protected void onPreExecute() {

            super.onPreExecute();
        }

        protected void onPostExecute(String results) {
            System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZzzz");
            System.out.println("results" + results);
            nextSurgeArrestors = results;
        }
    }

}
