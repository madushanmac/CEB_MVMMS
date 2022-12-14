package com.example.akla.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import static com.example.akla.login.Util.isConnected;

public class AddMVPolesMaintenanceSelect extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String phmBranch;

    private FusedLocationProviderClient client;

    //Offline data loading
    String[] values1 = new String[]{};
    String[] values2 = new String[]{};
    String[] values3 = new String[]{};
    HashMap<Integer, String> spinnerMap1 = new HashMap<Integer, String>();
    HashMap<Integer, String> spinnerMap2 = new HashMap<Integer, String>();
    HashMap<Integer, String> spinnerMap3 = new HashMap<Integer, String>();

    //load Province
    Spinner SpinnerProvince;
    String province;
    HashMap<Integer, String> spinnerMapProvince = new HashMap<Integer, String>();
    String[] valuesPro = new String[]{};

    //load Area
    Spinner SpinnerArea;
    String area;
    String valuesArea[] = new String[]{};
    HashMap<Integer,String> SpinnerMapArea = new HashMap<Integer, String>();

    //load gantry
    Spinner SpinnerGantry;
    Long gantry;
    String[] valuesGantry = new String[]{};
    HashMap<Integer,Long> SpinnerMapGantry = new HashMap<Integer, Long>();

    //load feeder
    Spinner SpinnerFeeder;
    String feeder;
    String[] valuesFeeder = new String[]{};
    HashMap<Integer, String> SpinnerMapFeeder = new HashMap<Integer, String>();

    //load Province
    Spinner SpinnerCycle;
    String cycle;
    HashMap<Integer, String> SpinnerMapCycle = new HashMap<Integer, String>();
    String[] valuesCycle = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mvpoles_maintenance_select);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Button ButtonNext = (Button) findViewById(R.id.btnNext);

        ButtonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("area" +feeder);
                SessionManager obj = new SessionManager(getBaseContext());
                obj.createKeyFeeder(feeder);
                obj.createKeyArea(area);
                obj.createKeyCycle(cycle);
                Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, AddMVPolesMaintenance.class);
                startActivity(intent);




            }
        });


        if (!isConnected(AddMVPolesMaintenanceSelect.this)) {
            readExcelArea();
            readExcelProvince();
        } else {
            new AddMVPolesMaintenanceSelect.loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new AddMVPolesMaintenanceSelect.loadCycle().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        //Spinner Area -- Area load by Province
        SpinnerProvince = findViewById(R.id.SpinnerProvince);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province =idprovince;
                if(!Util.isConnected(AddMVPolesMaintenanceSelect.this)){
                    Toast.makeText(getApplication(),"readExcel: Area " , Toast.LENGTH_SHORT).show();
                }else {
                    new AddMVPolesMaintenanceSelect.loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Line -- Line load by Area
        SpinnerArea = findViewById(R.id.SpinnerArea);
        SpinnerArea.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idarea = SpinnerMapArea.get(parent.getSelectedItemPosition());
                area = idarea;

                if(!Util.isConnected(AddMVPolesMaintenanceSelect.this)){

                    Toast.makeText(getApplication(),"readExcel: Line" , Toast.LENGTH_SHORT).show();

                }else {
                    new AddMVPolesMaintenanceSelect.loadGantrybyArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Feeder -- Feeder load by Gantry
        SpinnerGantry = findViewById(R.id.SpinnerGantry);
        SpinnerGantry.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                Long idgantry = SpinnerMapGantry.get(parent.getSelectedItemPosition());
                gantry = idgantry;
                System.out.println(idgantry);

                if(!Util.isConnected(AddMVPolesMaintenanceSelect.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new AddMVPolesMaintenanceSelect.loadFeederByGantry().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //Spinner Feeder
        SpinnerFeeder = findViewById(R.id.SpinnerFeeder);
        SpinnerFeeder.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idfeeder = SpinnerMapFeeder.get(parent.getSelectedItemPosition());
                feeder = idfeeder;
                System.out.println(idfeeder);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Cycle
        SpinnerCycle = findViewById(R.id.SpinnerCycle);
        SpinnerCycle.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idcycle = SpinnerMapCycle.get(parent.getSelectedItemPosition());
                cycle = idcycle;
                System.out.println(idcycle);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /////////////////e d i t  b y  K e n t //////////////////////////

    ////////// L O A D  P R O V I N C E /////////////////////////////////////////////////////
    private class loadProvince extends AsyncTask<String, Void, Glcompm[]> {
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
            SpinnerProvince = findViewById(R.id.SpinnerProvince);
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
            SpinnerArea = findViewById(R.id.SpinnerArea);
            SpinnerArea.setAdapter(adapterArea);
        }
    }

    /////////////////////////////////// L O A D  G A N T R Y   B Y  A R E A ////////////////////////////////////////////
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
            SpinnerGantry = findViewById(R.id.SpinnerGantry);
            SpinnerGantry.setAdapter(adapterGantry);
        }
    }

    ////////// L O A D   F E E D E R   B Y    G A N T R Y  /////////////////////////////////////////
    private class loadFeederByGantry extends AsyncTask<String, Void, MmsAddfeeder[]> {
        @Override
        protected MmsAddfeeder[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findFeederyById/" +gantry + "/";

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
                        valueFeederList.add(obj.getName());
                        SpinnerMapFeeder.put(c, obj.getId().getFeederId());
                    }
                }
            }
            ArrayAdapter<String> adapterFeeder = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valueFeederList.toArray(new String[]{}));
            adapterFeeder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerFeeder = findViewById(R.id.SpinnerFeeder);
            SpinnerFeeder.setAdapter(adapterFeeder);
        }
    }

    private class loadCycle extends AsyncTask<String, Void, MmsCycle[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected MmsCycle[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL+"findAllCycle";

            System.out.println("ssss" +url5);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5,MmsCycle[].class);
            //return List<MmsAddline>null;
        }


        protected void onPostExecute(MmsCycle[] results) {
            // ListView Item Click Listener
            System.out.println("results" +results);
            System.out.println("results" +results.length);
            String[] cycleName;
            valuesCycle = new String[results.length];

            System.out.println("resultsyyyyyyy");
            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    MmsCycle  obj = results[c];
                    System.out.println("resultsyyyyyyyw :" +c);
                    if(obj != null){
                        valuesCycle[c] = obj.getCycleName();
                        SpinnerMapCycle.put(c,obj.getId().get("cycleId").toString()) ;

                    }

                }
                System.out.println("resultsyyy:" );
            }

            ArrayAdapter<String> adapterNc = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesCycle);
            adapterNc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerCycle = (Spinner)findViewById(R.id.SpinnerCycle);
            SpinnerCycle.setAdapter(adapterNc);
            System.out.println("resultsyyy10:" );

        }

    }
    // Spinner Province
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
                SpinnerProvince = findViewById(R.id.SpinnerProvince);
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

    // Spinner Area
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
                SpinnerArea = findViewById(R.id.SpinnerArea);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.select_mv_pole_maintenance, menu);
//        return true;
//    }

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
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, AddSupport.class);
            startActivity(intent);


        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, TM2.class);
            startActivity(intent);


        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, AddEquipment.class);
            startActivity(intent);


        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, Login.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, GetNearByTower.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, AddGantry.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addAutoRecloser) {
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, AddAutoRecloser.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addLBSABS) {
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, AddLBSABS.class);
            startActivity(intent);

        }


        else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, AddTransformers.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(AddMVPolesMaintenanceSelect.this, AddMVPoles.class);
            startActivity(intent);
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}