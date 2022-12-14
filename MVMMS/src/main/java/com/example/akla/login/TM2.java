package com.example.akla.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.EditText;
import android.widget.Spinner;

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

public class TM2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Spinner SpinnerAreaTM;
    Spinner SpinnerLineTM;
    Spinner SpinnerCycleTM;
    EditText etAreaTM1;
    EditText etLineTM1;
    EditText etCycleTM1;
    String values[] = new String[]{};
    String valuesLine[] = new String[]{};
    String valuesCycle[] = new String[]{};
    HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>(); //Area
    HashMap<Integer,String> spinnerMap1 = new HashMap<Integer, String>(); //Line
    HashMap<Integer,String> spinnerMap2 = new HashMap<Integer, String>(); //Cycle
    HashMap<Integer,Long> spinnerMap3 = new HashMap<Integer, Long>();
    String area;
    String line;
    String cycle;
    private ProgressDialog ProgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tm2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* new TM2.loadarea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        System.out.println("hhhhh1");
       // new  loadLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        System.out.println("hhhhh2");
      //  new TM2.loadLineByArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        System.out.println("hhhhh3");*/

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });




      if(!Util.isConnected(TM2.this)){
            readExcel();
          //  Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
            readExcelLine();
          //  Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
          readExcelCycle();

      }else {
            new TM2.loadarea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new TM2.loadLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new TM2.loadCycle().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        }
        System.out.println("hhhhh2");
        System.out.println("TMTMTMTMTMTMTMTMTMMTMTMTMTMTMTMTMTTMMTMTMTM");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Button ButtonNext = (Button) findViewById(R.id.btnNext);
        SpinnerCycleTM = (Spinner) findViewById(R.id.SpinnerCycle);

        SpinnerAreaTM = (Spinner) findViewById(R.id.SpinnerAreaTM);
        SpinnerAreaTM.setPrompt("Select Area");
        etAreaTM1 = (EditText) findViewById(R.id.etAreaTM);


        SpinnerAreaTM.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String selectedItem = parent.getSelectedItem().toString();
                //System.out.println("ttttt:");
               // if (selectedItem.equals("Other")) {
                 //   System.out.println("ttttt1:");
                   // etAreaTM1.setVisibility(View.VISIBLE);
                    System.out.println("ttttt:2");
                    String selectedItem = parent.getSelectedItem().toString();
                    String name = parent.getSelectedItem().toString();
                    String idarea = spinnerMap.get(parent.getSelectedItemPosition());
                    area = idarea;
               //     Toast.makeText(TM2.this,"Name" +name,Toast.LENGTH_LONG).show();
               //     Toast.makeText(TM2.this,"idarea"+idarea,Toast.LENGTH_LONG).show();
                   // new loadLineByArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                if(!Util.isConnected(TM2.this)){
                    readExcelLine();
                 //   Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else{
                    new loadLineByArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    //new loadCycleByLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                }

                }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

        SpinnerLineTM = (Spinner) findViewById(R.id.SpinnerLineTM);
        SpinnerLineTM.setPrompt("Select Line");
        etLineTM1 = (EditText) findViewById(R.id.etLineTM);


        SpinnerLineTM.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String selectedItem = parent.getSelectedItem().toString();

               // if (selectedItem.equals("Other")) {
                 //   etLineTM1.setVisibility(View.VISIBLE);
                    //SpinnerLineTM.setVisibility(View.GONE);
                    String selectedItem = parent.getSelectedItem().toString();
                    String name = parent.getSelectedItem().toString();
                    long idLINEID = spinnerMap3.get(parent.getSelectedItemPosition());
                    line=String.valueOf(idLINEID);
                    System.out.println("LINEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE"+line);
                 //   Toast.makeText(TM2.this,"Name" +name,Toast.LENGTH_LONG).show();
                  //  Toast.makeText(TM2.this,"idlineid"+idLINEID,Toast.LENGTH_LONG).show();



                }


            public void onNothingSelected(AdapterView<?> parent) {
            }


        });


        SpinnerCycleTM = (Spinner) findViewById(R.id.SpinnerCycle);
        SpinnerCycleTM.setPrompt("Select Cycle");
        etCycleTM1 = (EditText) findViewById(R.id.etCycleTM);


        SpinnerCycleTM.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String selectedItem = parent.getSelectedItem().toString();

                // if (selectedItem.equals("Other")) {
                //   etLineTM1.setVisibility(View.VISIBLE);
                //SpinnerLineTM.setVisibility(View.GONE);
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idCYCLEID = spinnerMap2.get(parent.getSelectedItemPosition());
                cycle = idCYCLEID;
                //   Toast.makeText(TM2.this,"Name" +name,Toast.LENGTH_LONG).show();
                //  Toast.makeText(TM2.this,"idlineid"+idLINEID,Toast.LENGTH_LONG).show();



            }


            public void onNothingSelected(AdapterView<?> parent) {
            }


        });


        ButtonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               // String area = SpinnerAreaTM.getSelectedItem().toString();
                //if (area.equals("0ther")) {
                   // area= etAreaTM1.getText().toString();

               // }else {
                  //area = area.substring(0, 3);
             //   }

                System.out.println("area" +area);

              //  String line = SpinnerLineTM.getSelectedItem().toString();
              //  if (line.equals("Other")) {
             //       line= etLineTM1.getText().toString();
//
  //              }else {
                    System.out.println("line" +line);
    //                String[] lineArray = line.split("-");
      //              line =lineArray[0];
        //            System.out.println("line" +line);
                //}

              //String spinnerCycle = SpinnerCycleTM.getSelectedItem().toString();



                SessionManager obj = new SessionManager(getBaseContext());
                obj.createKeyArea(area);
                obj.createKeyLine(line);
                obj.createKeyCycle(cycle);

                Intent intent = new Intent(TM2.this, AddTowerMaintainance.class);
                startActivity(intent);




            }
        });
    }
    public void emptyMessage ( ) {
        // TODO Auto-generated method stub
        AlertDialog.Builder alert = new AlertDialog.Builder(TM2.this);
        alert.setTitle("Alert !");
        alert.setMessage("You have to fill all fields");
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(etAreaTM1.getVisibility()==View.VISIBLE){
            etAreaTM1.setVisibility(View.INVISIBLE);
        }else if(etLineTM1.getVisibility()==View.VISIBLE){
            etLineTM1.setVisibility(View.INVISIBLE);
        }
         if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
             AlertDialog.Builder builder = new AlertDialog.Builder(this);
             builder.setCancelable(false);
             builder.setMessage("Do you want to Exit?");
             builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     //if user pressed "yes", then he is allowed to exit from application

//            Intent intent = new Intent(AddTowerMaintainance.this, TM2.class);
//            startActivity(intent);

                     finish();
                     finish();

                 }
             });
             builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     //if user select "No", just cancel this dialog and continue with app
                     dialog.cancel();
                 }
             });
             AlertDialog alert = builder.create();
             alert.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tm2, menu);
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

        if (id == R.id.nav_addLine) {
            Intent intent = new Intent(TM2.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(TM2.this, AddSupport.class);
            startActivity(intent);


        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(TM2.this, TM2.class);
            startActivity(intent);


        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(TM2.this, AddEquipment.class);
            startActivity(intent);


        }
        else if (id == R.id.nav_Login) {
            Intent intent = new Intent(TM2.this, Login.class);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private class loadarea extends AsyncTask<String, Void, MmsAddArea[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /*ProgDialog= new ProgressDialog(TM2.this);
            //message should be changed according to the requirement
            ProgDialog.setMessage("Please wait...\n(This may take some time, depending on your network connection)");
            ProgDialog.setIndeterminate(false);
            ProgDialog.setTitle(Util.alert_header);
            ProgDialog.setIcon(R.drawable.logo);
            ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            ProgDialog.setCancelable(true);
            ProgDialog.show();*/

        }



        protected MmsAddArea[] doInBackground(String... urls) {


            //get deptId from session manager
            SessionManager objS = new SessionManager(getBaseContext());
            String deptId = objS.getPhmBranch();
            System.out.println("PhmBranch" + deptId.trim());
            deptId = deptId.trim();


            RestTemplate rest = new RestTemplate();
            //String url6 = Util.SRT_URL+"findAllAreaNew";
            String url6 = Util.SRT_URL+"findAllAreaNewMobile/" + deptId + "/";

            System.out.println("ssss" +url6);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url6,MmsAddArea[].class);
            //return List<MmsAddArea>null;
        }


        protected void onPostExecute(MmsAddArea[] results) {
            // ListView Item Click Listener
            System.out.println("results" +results);
            System.out.println("results" +results.length);
            String[] area;
            values = new String[results.length];
            //  String[] spinnerArray = new String[Province_ID.size()];
            //  HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
            //  for (int i = 0; i < Province_ID.size(); i++)
            //  {
            //      spinnerMap.put(i,Province_ID.get(i));
            //      spinnerArray[i] = Province_NAME.get(i);
            //  }


//
            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    MmsAddArea  obj = results[c];
                    values[c] = obj.getDeptNm();
                    spinnerMap.put(c,obj.getDeptId());


                }
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerAreaTM = (Spinner)findViewById(R.id.SpinnerAreaTM);
            SpinnerAreaTM.setAdapter(adapterNs);
//

           // ProgDialog.dismiss();




            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }
    private class loadLine extends AsyncTask<String, Void, MmsAddline[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*ProgDialog= new ProgressDialog(TM2.this);
//message should be changed according to the requirement
            ProgDialog.setMessage("Please wait...\n(This may take some time, depending on your network connection)");
            ProgDialog.setIndeterminate(false);
            ProgDialog.setTitle(Util.alert_header);
            ProgDialog.setIcon(R.drawable.logo);
            ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            ProgDialog.setCancelable(true);
            ProgDialog.show();*/

        }



        protected MmsAddline[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL+"findAllLine";

            System.out.println("ssss" +url5);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5,MmsAddline[].class);
            //return List<MmsAddline>null;
        }


        protected void onPostExecute(MmsAddline[] results) {
            // ListView Item Click Listener
            System.out.println("results" +results);
            System.out.println("results" +results.length);
            String[] lineName;
            valuesLine = new String[results.length];

            System.out.println("resultsyyyyyyy");
            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    MmsAddline  obj = results[c];
                    System.out.println("resultsyyyyyyyw :" +c);
                    if(obj != null){
                        valuesLine[c] = obj.getLineName();
                        spinnerMap3.put(c,obj.getId()) ;

                    }

                }
                System.out.println("resultsyyy:" );
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesLine);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerLineTM = (Spinner)findViewById(R.id.SpinnerLineTM);
            SpinnerLineTM.setAdapter(adapterNs);
            System.out.println("resultsyyy10:" );
           // ProgDialog.dismiss();


//





            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }
    private class loadLineByArea extends AsyncTask<String, Void, MmsAddline[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgDialog= new ProgressDialog(TM2.this);
//message should be changed according to the requirement
            ProgDialog.setMessage("Please wait...\n(This may take some time, depending on your network connection)");
            ProgDialog.setIndeterminate(false);
            ProgDialog.setTitle(Util.alert_header);
            ProgDialog.setIcon(R.drawable.logo);
            ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            ProgDialog.setCancelable(true);
            ProgDialog.show();

        }



        protected MmsAddline[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url7 = Util.SRT_URL+"findLineByArea/"+area+"/";

            System.out.println("ssss" +url7);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url7,MmsAddline[].class);
            //return List<MmsAddline>null;
        }


        protected void onPostExecute(MmsAddline[] results) {
            // ListView Item Click Listener
            System.out.println("resultsss" +results);
            System.out.println("results123" +results.length);
            String[] lineName;
            valuesLine = new String[results.length];


            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    MmsAddline  obj = results[c];
                    valuesLine[c] = obj.getLineName();
                    spinnerMap3.put(c,obj.getId()) ;

                }
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesLine);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerLineTM = (Spinner)findViewById(R.id.SpinnerLineTM);
            SpinnerLineTM.setAdapter(adapterNs);
            System.out.println("resultsyyy10:" );
            ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }


    private class loadCycle extends AsyncTask<String, Void, MmsCycle[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*ProgDialog= new ProgressDialog(TM2.this);
//message should be changed according to the requirement
            ProgDialog.setMessage("Please wait...\n(This may take some time, depending on your network connection)");
            ProgDialog.setIndeterminate(false);
            ProgDialog.setTitle(Util.alert_header);
            ProgDialog.setIcon(R.drawable.logo);
            ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            ProgDialog.setCancelable(true);
            ProgDialog.show();*/

        }



        protected MmsCycle[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();

            SessionManager obj = new SessionManager(getBaseContext());
            String deptId = obj.getPhmBranch();
            System.out.println("deptId" +deptId);

            String url5 = Util.SRT_URL+"findAllCycleMobile?deptid="+deptId ;

            System.out.println("ssss" +url5);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5,MmsCycle[].class);
            //return List<MmsAddline>null;
        }


        protected void onPostExecute(MmsCycle[] results) {
            // ListView Item Click Listener
            System.out.println("resultscycle" +results);
            System.out.println("resultscycle1" +results.length);
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
                        spinnerMap2.put(c,obj.getId().get("cycleId").toString()) ;

                    }

                }
                System.out.println("resultsyyy:" );
            }

            ArrayAdapter<String> adapterNc = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesCycle);
            adapterNc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerCycleTM = (Spinner)findViewById(R.id.SpinnerCycle);
            SpinnerCycleTM.setAdapter(adapterNc);
            System.out.println("resultsyyy10:" );
            // ProgDialog.dismiss();


//





            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }


    private class loadCycleByLine extends AsyncTask<String, Void, MmsCycle[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgDialog= new ProgressDialog(TM2.this);
            //message should be changed according to the requirement
            ProgDialog.setMessage("Please wait...\n(This may take some time, depending on your network connection)");
            ProgDialog.setIndeterminate(false);
            ProgDialog.setTitle(Util.alert_header);
            ProgDialog.setIcon(R.drawable.logo);
            ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            ProgDialog.setCancelable(true);
            ProgDialog.show();

        }



        protected MmsCycle[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url8 = Util.SRT_URL+"findCycleByLine/"+line+"/";

            System.out.println("ssss" +url8);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url8,MmsCycle[].class);
            //return List<MmsAddline>null;
        }


        protected void onPostExecute(MmsCycle[] results) {
            // ListView Item Click Listener
            System.out.println("resultsss" +results);
            System.out.println("results123" +results.length);
            String[] cycle;
            valuesCycle = new String[results.length];


            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    MmsCycle obj = results[c];
                    valuesCycle[c] = obj.getCycleName();
                    spinnerMap2.put(c,obj.getId().get("cycleId").toString()) ;

                }
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesCycle);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerCycleTM = (Spinner)findViewById(R.id.SpinnerCycle);
            SpinnerCycleTM.setAdapter(adapterNs);
            System.out.println("resultsyyy10:" );
            ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }


    public List<String> readExcel(){
        System.out.println("readExcel");
        // Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
        List<String> resultSet = new ArrayList<String>();
        // Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

        System.out.println("readExcel1");
        File inputWorkbook  = new File(Environment.getExternalStorageDirectory() +File.separator + "Schedule" +File.separator+ "Area.xls");
        System.out.println("readExcel2");
        //  Toast.makeText(getApplication(),"readExcel2: " , Toast.LENGTH_SHORT).show();

        if(inputWorkbook.exists()) {
            System.out.println("readExcel3");
            //    Toast.makeText(getApplication(),"readExcel3: " , Toast.LENGTH_SHORT).show();

            Workbook w;
            try {
                //          System.out.println("readExcel4");

                w = Workbook.getWorkbook(inputWorkbook);
                System.out.println("readExce5");
                //        Toast.makeText(getApplication(),"readExcel4: " , Toast.LENGTH_SHORT).show();

                // Get the first sheet
                Sheet sheet = w.getSheet(0);
                System.out.println("readExce6");

                // Loop over column and lines
                int coloumn = sheet.getRows();
                values = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);
                    System.out.println(cell.getContents());
                    //          Toast.makeText(getApplication(),"readExcel5: " + cell.getContents(), Toast.LENGTH_SHORT).show();
                    spinnerMap.put(j,cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    values[j] = cell1.getContents();

                    //if(cell.getContents().equalsIgnoreCase(key)){
//                            for (int i = 0; i < sheet.getColumns(); i++) {
//                                Cell cel = sheet.getCell(i, j);
//                                Toast.makeText(getApplication(),"readExcel6: " + cel.getContents(), Toast.LENGTH_SHORT).show();
//
//                                resultSet.add(cel.getContents());
//                            }
                    //}
                    continue;
                }


                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerAreaTM = (Spinner)findViewById(R.id.SpinnerAreaTM);
                SpinnerAreaTM.setAdapter(adapterNs);
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            resultSet.add("File not found..!");
        }
        if(resultSet.size()==0){
            resultSet.add("Data not found..!");
        }
        return resultSet;
    }

    public List<String> readExcelLine(){
        System.out.println("readExcel");
        // Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
        List<String> resultSet = new ArrayList<String>();
        // Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

        System.out.println("readExcel1");
        File inputWorkbook  = new File(Environment.getExternalStorageDirectory() +File.separator + "Schedule" +File.separator+ "LineCode.xls");
        System.out.println("readExcel2");
        //  Toast.makeText(getApplication(),"readExcel2: " , Toast.LENGTH_SHORT).show();

        if(inputWorkbook.exists()) {
            System.out.println("readExcel3");
            //    Toast.makeText(getApplication(),"readExcel3: " , Toast.LENGTH_SHORT).show();

            Workbook w;
            try {
                //          System.out.println("readExcel4");

                w = Workbook.getWorkbook(inputWorkbook);
                System.out.println("readExce5");
                //        Toast.makeText(getApplication(),"readExcel4: " , Toast.LENGTH_SHORT).show();

                // Get the first sheet
                Sheet sheet = w.getSheet(0);
                System.out.println("readExce6");

                // Loop over column and lines
                int coloumn = sheet.getRows();
                valuesLine = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);
                    System.out.println(cell.getContents());
                    //          Toast.makeText(getApplication(),"readExcel5: " + cell.getContents(), Toast.LENGTH_SHORT).show();
                    spinnerMap1.put(j,cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    valuesLine[j] = cell1.getContents();

                    //if(cell.getContents().equalsIgnoreCase(key)){
//                            for (int i = 0; i < sheet.getColumns(); i++) {
//                                Cell cel = sheet.getCell(i, j);
//                                Toast.makeText(getApplication(),"readExcel6: " + cel.getContents(), Toast.LENGTH_SHORT).show();
//
//                                resultSet.add(cel.getContents());
//                            }
                    //}
                    continue;
                }



                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesLine);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerLineTM = (Spinner)findViewById(R.id.SpinnerLineTM);
                SpinnerLineTM.setAdapter(adapterNs);
                System.out.println("resultsyyy10:" );

            } catch (BiffException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            resultSet.add("File not found..!");
        }
        if(resultSet.size()==0){
            resultSet.add("Data not found..!");
        }
        return resultSet;
    }


    public List<String> readExcelCycle(){
        System.out.println("readExcelCycle");
        // Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
        List<String> resultSet = new ArrayList<String>();
        // Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

        System.out.println("readExcelCycle1");
        File inputWorkbook  = new File(Environment.getExternalStorageDirectory() +File.separator + "Schedule" +File.separator+ "Cycle.xls");
        System.out.println("readExcelCycle2");
        //  Toast.makeText(getApplication(),"readExcel2: " , Toast.LENGTH_SHORT).show();

        if(inputWorkbook.exists()) {
            System.out.println("readExcelCycle3");
            //    Toast.makeText(getApplication(),"readExcel3: " , Toast.LENGTH_SHORT).show();

            Workbook w;
            try {
                //          System.out.println("readExcel4");

                w = Workbook.getWorkbook(inputWorkbook);
                System.out.println("readExceCycle5");
                //        Toast.makeText(getApplication(),"readExcel4: " , Toast.LENGTH_SHORT).show();

                // Get the first sheet
                Sheet sheet = w.getSheet(0);
                System.out.println("readExceCycle6");

                // Loop over column and rows
                int coloumn = sheet.getRows();
                values = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);
                    System.out.println(cell.getContents());
                    //          Toast.makeText(getApplication(),"readExcel5: " + cell.getContents(), Toast.LENGTH_SHORT).show();
                    spinnerMap2.put(j,cell.getContents());
                    //Cell cell1 = sheet.getCell(1, j);
                    values[j] = cell.getContents();

                    //if(cell.getContents().equalsIgnoreCase(key)){
//                            for (int i = 0; i < sheet.getColumns(); i++) {
//                                Cell cel = sheet.getCell(i, j);
//                                Toast.makeText(getApplication(),"readExcel6: " + cel.getContents(), Toast.LENGTH_SHORT).show();
//
//                                resultSet.add(cel.getContents());
//                            }
                    //}
                    continue;
                }


                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerCycleTM = (Spinner)findViewById(R.id.SpinnerCycle);
                SpinnerCycleTM.setAdapter(adapterNs);
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            resultSet.add("File not found..!");
        }
        if(resultSet.size()==0){
            resultSet.add("Data not found..!");
        }
        return resultSet;
    }





}

