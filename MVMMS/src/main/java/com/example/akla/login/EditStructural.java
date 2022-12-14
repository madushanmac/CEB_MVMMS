package com.example.akla.login;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

import static com.example.akla.login.Util.isConnected;

public class EditStructural extends AppCompatActivity
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

    //Load to spinners locally
    ArrayAdapter<CharSequence> adapter1;

    //Define Variebles for save data in db
    MmsAddgantry objGantry;
    Spinner Code;
    String code;
    Spinner Province;
    Spinner Area;
    Spinner Line;
    Spinner Gantry;
    EditText NoOfPoles;
    Spinner OverheadEarthing;
    EditText GroundRes;
    EditText CUConRes;

    private ProgressDialog ProgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_structural);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Province = findViewById(R.id.province);
        Area = findViewById(R.id.area);
        //Line = findViewById(R.id.spnLine);
        Gantry = findViewById(R.id.spnGantryCode);
        NoOfPoles = findViewById(R.id.no_of_poles);
        OverheadEarthing = findViewById(R.id.overhead_earthing);
        GroundRes = findViewById(R.id.ground_res);
        CUConRes = findViewById(R.id.cu_con_res);

        OverheadEarthing =(Spinner)findViewById(R.id.overhead_earthing);
        adapter1 =ArrayAdapter.createFromResource(this,R.array.overhead_earthing,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        OverheadEarthing.setAdapter(adapter1);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (!isConnected(EditStructural.this)) {
            //readExcel();
            //readExcelLoadtype();
            //readExcelConType();
        } else {
            new EditStructural.loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Area -- Area load by Province
        SpinnerProvince = findViewById(R.id.spnProvince);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province =idprovince;

                if(!Util.isConnected(EditStructural.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditStructural.loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

                if(!Util.isConnected(EditStructural.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditStructural.loadGantrybyArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
//                if(!Util.isConnected(EditStructural.this)){
//                    //readExcelLine();
//                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
//
//                }else {
//                    new EditStructural.loadGantryByLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
                if(!Util.isConnected(EditStructural.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditStructural.loadGantryObj().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
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
                    if(objGantry!=null) {

                        if (objGantry.getSeNoPoles() != null) {
                            NoOfPoles.setText(objGantry.getSeNoPoles().toString());
                        }

                        if (objGantry.getSeGroungRes() != null) {
                            GroundRes.setText(objGantry.getSeGroungRes().toString());
                        }

                        if (objGantry.getSeCuConRes() != null) {
                            CUConRes.setText(objGantry.getSeCuConRes().toString());
                        }
                    }
                }
            }
        });

        /////////////////////////Save to Database ////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////// Edit by Dinith 2019 - 12 - 09 ///////////////////////////////////////////////////////////////////////////////

        Button ButtonSave = findViewById(R.id.savebtn);
        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    if (NoOfPoles.getText().toString().trim().equals("")) {
                        NoOfPoles.setError("Should give a Name !");
                    }  else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditStructural.this);
                        builder.setCancelable(true);
                        builder.setIcon(R.drawable.logo);
                        builder.setMessage("Do you want to save Gantry Data?");
                        builder.setTitle("Save Gantry");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!isConnected(EditStructural.this)){
                                            // createExcel();
                                            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            //TO get MmsAddgantry object
                                            //new AddBusBar.loadGantryObj().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                            new EditStructural.AddStructuralDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
    }

    //////////////////////////////////////// AddGantryDB  Edit Dinith////////////////////////////////////////////////////////////////

    private class AddStructuralDB extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {

            String noPoles = NoOfPoles.getText().toString();
            String overheadEarthing;

            System.out.println(OverheadEarthing.getSelectedItem().toString()+"QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
            if(OverheadEarthing.getSelectedItem().toString()=="Yes"){
                overheadEarthing="1";
            }else{
                overheadEarthing="2";
            }

            String groundRes = GroundRes.getText().toString();
            String cuConRes = CUConRes.getText().toString();

            System.out.println("############################### Test Intialized variable ##############################################");

            System.out.println("No of Poles:" + noPoles);
            System.out.println("Overhead Earthing: "+overheadEarthing);
            System.out.println("Ground Res: "+groundRes);
            System.out.println("CU Con Res: "+cuConRes);

            objGantry.setSeNoPoles(new BigDecimal(noPoles));
            objGantry.setSeOverheadEarthing(overheadEarthing);
            if(groundRes.length()==0){
                objGantry.setSeGroungRes(new BigDecimal(0));
            }else{
                objGantry.setSeGroungRes(new BigDecimal(groundRes));
            }

            if(cuConRes.length()==0){
                objGantry.setSeCuConRes(new BigDecimal(0));
            }else{
                objGantry.setSeCuConRes(new BigDecimal(cuConRes));
            }


            System.out.println("Set object testing :****************************************");
            System.out.println("PHM BRANCH:" + objGantry.getPhmBranch());
            System.out.println("No of Poles: "+objGantry.getSeOverheadEarthing());
            System.out.println("Overhead Earthing: "+objGantry.getSeOverheadEarthing());
            System.out.println("Ground Res: "+objGantry.getSeGroungRes());
            System.out.println("CU Con Res: "+objGantry.getSeCuConRes());

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
        getMenuInflater().inflate(R.menu.add_structural, menu);
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
            Intent intent = new Intent(EditStructural.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(EditStructural.this, GetNearByTower.class);
            startActivity(intent);

            //////////////////////////////// PHM - LCM ////////////////////////////////////////////

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(EditStructural.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(EditStructural.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(EditStructural.this, TM2.class);
            startActivity(intent);

            //////////////////////////////// PHM - SUb ////////////////////////////////////////////

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(EditStructural.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addBusBar) {
            Intent intent = new Intent(EditStructural.this, AddBusBar.class);
            startActivity(intent);

        } else if (id == R.id.nav_addStructual) {
            Intent intent = new Intent(EditStructural.this, AddStructural.class);
            startActivity(intent);
        } else if (id == R.id.nav_editStructual) {
            Intent intent = new Intent(EditStructural.this, EditStructural.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantryMore) {
            Intent intent = new Intent(EditStructural.this, AddGantryMore.class);
            startActivity(intent);

        } else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(EditStructural.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSwitches) {
            Intent intent = new Intent(EditStructural.this, AddSwitches.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSurge) {
            Intent intent = new Intent(EditStructural.this, AddSurgeArrestors.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTransformersG) {
            Intent intent = new Intent(EditStructural.this, AddTransformersG.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(EditStructural.this, AddEquipment.class);
            startActivity(intent);

///////////////////// POLE DETAILS //////////////////////////////////////////////

        } else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(EditStructural.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(EditStructural.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(EditStructural.this, AddTransformers.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(EditStructural.this, Login.class);
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
            ProgDialog= new ProgressDialog(EditStructural.this);
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
//
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

            ProgDialog.dismiss();

            return rest.getForObject(url6, MmsAddgantry.class);
        }
    }
}
