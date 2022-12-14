package com.example.akla.login;

import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.HashMap;

import static com.example.akla.login.Util.isConnected;

public class AddTransformers extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    //Date Selector
    Button btn_date;
    EditText etDateOfCommission;
    //Load Area
    String[] values = new String[]{};
    HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();
    Spinner SpinnerArea;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transformers);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Load area
        if (!isConnected(AddTransformers.this)) {
            //readExcel();

        } else {
            new AddTransformers.loadarea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        }

        //Date Selector
        btn_date = findViewById(R.id.btn_date_of_commission);
        etDateOfCommission = findViewById(R.id.etDateOfCommission);
        btn_date.setOnClickListener(AddTransformers.this);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_date) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddTransformers.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            etDateOfCommission.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

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
            Intent intent = new Intent(AddTransformers.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddTransformers.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddTransformers.this, AddSupport.class);
            startActivity(intent);


        } else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddTransformers.this, TM2.class);
            startActivity(intent);


        } else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddTransformers.this, AddEquipment.class);
            startActivity(intent);


        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddTransformers.this, Login.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(AddTransformers.this, GetNearByTower.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddTransformers.this, AddGantry.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addAutoRecloser) {
            Intent intent = new Intent(AddTransformers.this, AddAutoRecloser.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addLBSABS) {
            Intent intent = new Intent(AddTransformers.this, AddLBSABS.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(AddTransformers.this, AddFeeder.class);
            startActivity(intent);

        }else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(AddTransformers.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(AddTransformers.this, AddTransformers.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class loadarea extends AsyncTask<String, Void, MmsAddArea[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        protected MmsAddArea[] doInBackground(String... urls) {

            //get deptId from session manager
            SessionManager objS = new SessionManager(getBaseContext());
            String deptId = objS.getPhmBranch();
            System.out.println("****************************************PhmBranch" + deptId.trim());
            deptId = deptId.trim();

            RestTemplate rest = new RestTemplate();
            //String url6 = Util.SRT_URL+"findAllAreaNew";
            String url6 = Util.SRT_URL + "findAllAreaNewMobile/" + deptId + "/";

            System.out.println("ssss" + url6);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url6, MmsAddArea[].class);
        }


        protected void onPostExecute(MmsAddArea[] results) {
            // ListView Item Click Listener
            System.out.println("results" + results);
            System.out.println("results" + results.length);
            String[] area;
            values = new String[results.length];

            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddArea obj = results[c];
                    values[c] = obj.getDeptNm();
                    spinnerMap.put(c, obj.getDeptId());

                }
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerArea = findViewById(R.id.SpinnerArea);
            SpinnerArea.setAdapter(adapterNs);

        }

    }

}
