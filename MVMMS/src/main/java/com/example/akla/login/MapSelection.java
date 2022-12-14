package com.example.akla.login;

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
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static com.example.akla.login.Util.isConnected;

public class MapSelection  extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //load Province
    Spinner SpinnerProvince;
    HashMap<Integer, String> spinnerMapProvince = new HashMap<Integer, String>();
    String[] valuesPro = new String[]{};

    //load Area
    Spinner SpinnerArea;
    String valuesArea[] = new String[]{};
    HashMap<Integer,String> SpinnerMapArea = new HashMap<Integer, String>();

    //load Line
    Spinner SpinnerLine;
    String valuesLine[] = new String[]{};
    HashMap<Integer,Long> SpinnerMapLine = new HashMap<Integer, Long>();

    String province;
    String area;
    String line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selection);
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
                SessionManager obj = new SessionManager(getBaseContext());
                obj.createKeyMapProvince(province);
                obj.createKeyMapArea(area);
                obj.createKeyMapLine(line);
                System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                System.out.println("PRO "+province+" : "+obj.getKeyMapProvince());
                System.out.println("Area "+area+" : "+obj.getKeyMapArea());
                System.out.println("Line: "+line+" : "+obj.getKeyMapLine());

                Intent intent = new Intent(MapSelection.this, MarkerDemoActivity.class);
                startActivity(intent);


            }
        });

        if (!isConnected(MapSelection.this)) {
            //readExcel();
            //readExcelLoadtype();
            //readExcelConType();
        } else {
            new MapSelection.loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Area -- Area load by Province
        SpinnerProvince = findViewById(R.id.spnProvince);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province =idprovince;

                if(!Util.isConnected(MapSelection.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new MapSelection.loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

                if(!Util.isConnected(MapSelection.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new MapSelection.loadLineByArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

                //Spinner Gantry -- Gantry load by Line
        SpinnerLine = findViewById(R.id.spnLine);
        SpinnerLine.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                long idline = SpinnerMapLine.get(parent.getSelectedItemPosition());
                line = String.valueOf(idline);

                if(!Util.isConnected(MapSelection.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    //new MapSelection.loadLineByArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

        @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.apphome) {
                Intent intent = new Intent(MapSelection.this, MainActivity.class);
                startActivity(intent);

            } else if (id == R.id.nearby) {
                Intent intent = new Intent(MapSelection.this, GetNearByTower.class);
                startActivity(intent);

            } else if (id == R.id.mapselection) {
                Intent intent = new Intent(MapSelection.this, MapSelection.class);
                startActivity(intent);

                //////////////////////////////// PHM - LCM ////////////////////////////////////////////

            } else if (id == R.id.nav_addLine) {
                Intent intent = new Intent(MapSelection.this, AddLine.class);
                startActivity(intent);

            } else if (id == R.id.nav_addSupport) {
                Intent intent = new Intent(MapSelection.this, AddSupport.class);
                startActivity(intent);

            }else if (id == R.id.nav_addTowerMaintainance) {
                Intent intent = new Intent(MapSelection.this, TM2.class);
                startActivity(intent);

                //////////////////////////////// PHM - SUb ////////////////////////////////////////////

            } else if (id == R.id.nav_addGantry) {
                Intent intent = new Intent(MapSelection.this, AddGantry.class);
                startActivity(intent);

            } else if (id == R.id.nav_addBusBar) {
                Intent intent = new Intent(MapSelection.this, AddBusBar.class);
                startActivity(intent);

            } else if (id == R.id.nav_addStructual) {
                Intent intent = new Intent(MapSelection.this, AddStructural.class);
                startActivity(intent);
            } else if (id == R.id.nav_editStructual) {
                Intent intent = new Intent(MapSelection.this, EditStructural.class);
                startActivity(intent);

            } else if (id == R.id.nav_addGantryMore) {
                Intent intent = new Intent(MapSelection.this, AddGantryMore.class);
                startActivity(intent);

            } else if (id == R.id.nav_addFeeder) {
                Intent intent = new Intent(MapSelection.this, AddFeeder.class);
                startActivity(intent);

            } else if (id == R.id.nav_addSwitches) {
                Intent intent = new Intent(MapSelection.this, AddSwitches.class);
                startActivity(intent);

            } else if (id == R.id.nav_addSurge) {
                Intent intent = new Intent(MapSelection.this, AddSurgeArrestors.class);
                startActivity(intent);

            } else if (id == R.id.nav_addTransformersG) {
                Intent intent = new Intent(MapSelection.this, AddTransformersG.class);
                startActivity(intent);

            }else if (id == R.id.nav_addEquipment) {
                Intent intent = new Intent(MapSelection.this, AddEquipment.class);
                startActivity(intent);

///////////////////// POLE DETAILS //////////////////////////////////////////////

            } else if (id == R.id.nav_addMVPoles) {
                Intent intent = new Intent(MapSelection.this, AddMVPoles.class);
                startActivity(intent);

            } else if (id == R.id.nav_addPoles) {
                Intent intent = new Intent(MapSelection.this, AddPoles.class);
                startActivity(intent);

            } else if (id == R.id.nav_addTowers) {
                Intent intent = new Intent(MapSelection.this, AddTransformers.class);
                startActivity(intent);

            } else if (id == R.id.nav_Login) {
                Intent intent = new Intent(MapSelection.this, Login.class);
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

    ////////// L O A D   L I N E   B Y   P R O V I N C E /////////////////////////////////////////
    private class loadLineByArea extends AsyncTask<String, Void, MmsAddline[]> {
        @Override

        protected MmsAddline[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findLineByArea/" + area + "/";

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5, MmsAddline[].class);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(MmsAddline[] results) {
            // ListView Item Click Listener
            System.out.println("results" + results);
            System.out.println("results" + results.length);
            String[] line;
            valuesLine = new String[results.length];

            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddline obj = results[c];
                    valuesLine[c] = obj.getLineName();
                    SpinnerMapLine.put(c, obj.getId());
                }
            }
            ArrayAdapter<String> adapterLine = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesLine);
            adapterLine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerLine = findViewById(R.id.spnLine);
            SpinnerLine.setAdapter(adapterLine);
        }
    }
}
