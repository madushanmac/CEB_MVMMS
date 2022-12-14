package com.example.akla.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.akla.login.Util.isConnected;

public class EditGantryMore extends AppCompatActivity
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

    //view images
    private ImageView imageHolder;
    private ImageView imageHolder2;
    private ImageView imageHolder3;
    private ImageView imageHolder4;

    Bitmap bitmap;

    private FusedLocationProviderClient client;

    //Define Variebles for save data in db
    MmsAddgantry objAddgantry;
    MmsGantryloc objGantryloc1;
    MmsGantryloc objGantryloc2;
    MmsGantryloc objGantryloc3;
    MmsGantryloc objGantryloc4;
    Spinner Code;
    String code;
    TextView latitude1;
    TextView longitude1;
    TextView latitude2;
    TextView longitude2;
    TextView latitude3;
    TextView longitude3;
    TextView latitude4;
    TextView longitude4;

    private ProgressDialog ProgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gantry_more);

        latitude1 = findViewById(R.id.latitude1);
        latitude2 = findViewById(R.id.latitude2);
        latitude3 = findViewById(R.id.latitude3);
        latitude4 = findViewById(R.id.latitude4);
        longitude1 = findViewById(R.id.longitude1);
        longitude2 = findViewById(R.id.longitude2);
        longitude3 = findViewById(R.id.longitude3);
        longitude4 = findViewById(R.id.longitude4);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (!isConnected(EditGantryMore.this)) {
            //readExcel();
            //readExcelLoadtype();
            //readExcelConType();
        } else {
            new EditGantryMore.loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Area -- Area load by Province
        SpinnerProvince = findViewById(R.id.spnProvince);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province =idprovince;

                if(!Util.isConnected(EditGantryMore.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditGantryMore.loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

                if(!Util.isConnected(EditGantryMore.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditGantryMore.loadGantrybyArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
//                if(!Util.isConnected(EditGantryMore.this)){
//                    //readExcelLine();
//                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
//
//                }else {
//                    new EditGantryMore.loadGantryByLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                }
//            }
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });


        //Gantry object load by gantry code
        SpinnerGantry = findViewById(R.id.spnGantryCode);
        SpinnerGantry.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                Long idgantry = SpinnerMapGantry.get(parent.getSelectedItemPosition());
                code = idgantry.toString();
                System.out.println(idgantry);

                if(!Util.isConnected(EditGantryMore.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new EditGantryMore.loadGantryObj().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new EditGantryMore.loadGantryLocObj1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new EditGantryMore.loadGantryLocObj2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new EditGantryMore.loadGantryLocObj3().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new EditGantryMore.loadGantryLocObj4().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ///////////// R O W D Y \\\\\\\\\\\\\\ I M A G E  S A V E /////////////////////////////////////////

        ////////// C a p t u r e  I m a g e
        imageHolder = (ImageView)findViewById(R.id.imageView);
        Button capturedImageButton = (Button)findViewById(R.id.bUploadImage1);
        capturedImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, 10);

            }
        });

        imageHolder2 = (ImageView)findViewById(R.id.imageView2);
        Button capturedImageButton2 = (Button)findViewById(R.id.bUploadImage2);
        capturedImageButton2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent photoCaptureIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent2, 20);

            }
        });

        imageHolder3 = (ImageView)findViewById(R.id.imageView3);
        Button capturedImageButton3 = (Button)findViewById(R.id.bUploadImage3);
        capturedImageButton3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, 30);

            }
        });

        imageHolder4 = (ImageView)findViewById(R.id.imageView4);
        Button capturedImageButton4 = (Button)findViewById(R.id.bUploadImage4);
        capturedImageButton4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent photoCaptureIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent2, 40);

            }
        });


        //////////////////////// R O W D Y //////////////// G P S Location //////////////////////////////////////
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

        // P O I N T - 1
        Button button = findViewById(R.id.bLocation1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(EditGantryMore.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(EditGantryMore.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){

                            TextView lat1 = findViewById(R.id.latitude1);
                            lat1.setText(String.valueOf((double) location.getLatitude()));

                            TextView long1 = findViewById(R.id.longitude1);
                            long1.setText(String.valueOf((double) location.getLongitude()));
                        }
                    }
                });
            }
        });

        // P O I N T - 2
        Button button2 = findViewById(R.id.bLocation2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(EditGantryMore.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(EditGantryMore.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){

                            TextView lat2 = findViewById(R.id.latitude2);
                            lat2.setText(String.valueOf((double) location.getLatitude()));

                            TextView long2 = findViewById(R.id.longitude2);
                            long2.setText(String.valueOf((double) location.getLongitude()));
                        }
                    }
                });
            }
        });

        // P O I N T - 3
        Button button3 = findViewById(R.id.bLocation3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(EditGantryMore.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(EditGantryMore.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){

                            TextView lat3 = findViewById(R.id.latitude3);
                            lat3.setText(String.valueOf((double) location.getLatitude()));

                            TextView long3 = findViewById(R.id.longitude3);
                            long3.setText(String.valueOf((double) location.getLongitude()));
                        }
                    }
                });
            }
        });

        // P O I N T - 4
        Button button4 = findViewById(R.id.bLocation4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(EditGantryMore.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(EditGantryMore.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){

                            TextView lat4 = findViewById(R.id.latitude4);
                            lat4.setText(String.valueOf((double) location.getLatitude()));

                            TextView long4 = findViewById(R.id.longitude4);
                            long4.setText(String.valueOf((double) location.getLongitude()));
                        }
                    }
                });
            }
        });

        //////////////// G E T   D E T A I L S ///////////////////////////////////////////////
        Button ButtonDetails = findViewById(R.id.detailsbtn);
        ButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
                    //System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL: "+ objGantryloc1.getGpsLatitude());
                    if(objGantryloc1!=null){

                        if(objGantryloc1.getGpsLatitude()!=null){
                            latitude1.setText(objGantryloc1.getGpsLatitude().toString());
                        }

                        if(objGantryloc1.getGpsLongitude()!=null){
                            longitude1.setText(objGantryloc1.getGpsLongitude().toString());
                        }
                    }

                    if(objGantryloc2!=null){

                        if(objGantryloc2.getGpsLatitude()!=null){
                            latitude2.setText(objGantryloc2.getGpsLatitude().toString());
                        }

                        if(objGantryloc2.getGpsLongitude()!=null){
                            longitude2.setText(objGantryloc2.getGpsLongitude().toString());
                        }
                    }

                    if(objGantryloc3!=null){

                        if(objGantryloc3.getGpsLatitude()!=null){
                            latitude3.setText(objGantryloc3.getGpsLatitude().toString());
                        }

                        if(objGantryloc3.getGpsLongitude()!=null){
                            longitude3.setText(objGantryloc3.getGpsLongitude().toString());
                        }
                    }


                    if(objGantryloc4!=null){

                        if(objGantryloc4.getGpsLatitude()!=null){
                            latitude4.setText(objGantryloc4.getGpsLatitude().toString());
                        }

                        if(objGantryloc4.getGpsLongitude()!=null){
                            longitude4.setText(objGantryloc4.getGpsLongitude().toString());
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

                AlertDialog.Builder builder = new AlertDialog.Builder(EditGantryMore.this);
                builder.setCancelable(true);
                builder.setIcon(R.drawable.logo);
                builder.setMessage("Do you want to save Gantry Location Data?");
                builder.setTitle("Save Gantry Location");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AddGantryLocDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

    /////////////// I m a g e   S e t   t o   I m a g e   V i e w e r
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(10 == requestCode && resultCode == RESULT_OK){
            final Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageHolder.setImageBitmap(bitmap);

            saveToInternalStorage(bitmap);

        }else if (20 == requestCode && resultCode == RESULT_OK){
            final Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageHolder2.setImageBitmap(bitmap);

            saveToInternalStorage(bitmap);

        }else if (30 == requestCode && resultCode == RESULT_OK){
            final Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageHolder3.setImageBitmap(bitmap);

            saveToInternalStorage(bitmap);

        }else if (40 == requestCode && resultCode == RESULT_OK){
            final Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageHolder4.setImageBitmap(bitmap);

            saveToInternalStorage(bitmap);
        }
    }

    //////////////////// S a v e  I m a g e  I n   F o l d e r
    private String saveToInternalStorage(Bitmap bitmap){
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "MVMMS Gantry Pics");

        Spinner SpinnerGantry = (Spinner) findViewById(R.id.spnGantryCode);
        String gantryName = SpinnerGantry.getSelectedItem().toString();

        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStamp = s.format(new Date());

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(mediaStorageDir,gantryName+" "+timeStamp+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
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
        getMenuInflater().inflate(R.menu.add_gantry_more, menu);
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
            Intent intent = new Intent(EditGantryMore.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(EditGantryMore.this, GetNearByTower.class);
            startActivity(intent);

            ///////////////////////////////// PHM - LCM ////////////////////////////////////////////

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(EditGantryMore.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(EditGantryMore.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(EditGantryMore.this, TM2.class);
            startActivity(intent);

            //////////////////////////////// PHM - SUb ////////////////////////////////////////////

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(EditGantryMore.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addBusBar) {
            Intent intent = new Intent(EditGantryMore.this, AddBusBar.class);
            startActivity(intent);

        } else if (id == R.id.nav_addStructual) {
            Intent intent = new Intent(EditGantryMore.this, AddStructural.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantryMore) {
            Intent intent = new Intent(EditGantryMore.this, AddGantryMore.class);
            startActivity(intent);
        } else if (id == R.id.nav_editGantryMore) {
            Intent intent = new Intent(EditGantryMore.this, EditGantryMore.class);
            startActivity(intent);

        } else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(EditGantryMore.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSwitches) {
            Intent intent = new Intent(EditGantryMore.this, AddSwitches.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSurge) {
            Intent intent = new Intent(EditGantryMore.this, AddSurgeArrestors.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTransformersG) {
            Intent intent = new Intent(EditGantryMore.this, AddTransformersG.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(EditGantryMore.this, AddEquipment.class);
            startActivity(intent);

            ///////////////////// POLE DETAILS //////////////////////////////////////////////

        } else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(EditGantryMore.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(EditGantryMore.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(EditGantryMore.this, AddTransformers.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(EditGantryMore.this, Login.class);
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
            ProgDialog= new ProgressDialog(EditGantryMore.this);
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
//            String url5 = Util.SRT_URL + "findGantryByAreaLine/"+area+"/"+line+"/";
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

            objAddgantry =  rest.getForObject(url6, MmsAddgantry.class);;
            System.out.println(objAddgantry.getPhmBranch());

            return rest.getForObject(url6, MmsAddgantry.class);
        }
    }

    ////////// L O A D   G A N T R Y L O C   O B J E C T  //////////////////////////////////////////
    /////////// P O I N T  1
    private class loadGantryLocObj1 extends AsyncTask<String, Void, MmsGantryloc> {

        @Override
        protected MmsGantryloc doInBackground(String... urls) {

            //Code = findViewById(R.id.spnGantryCode);
            //String code = Code.getSelectedItem().toString();

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "findLocationById/" + code + "/1/";
            System.out.println(code);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV"+code);
            //System.out.println(rest.getForObject(url6, MmsAddgantry.class));

            objGantryloc1 =  rest.getForObject(url6, MmsGantryloc.class);;

            return rest.getForObject(url6, MmsGantryloc.class);
        }
    }
    /////////// P O I N T  12
    private class loadGantryLocObj2 extends AsyncTask<String, Void, MmsGantryloc> {

        @Override
        protected MmsGantryloc doInBackground(String... urls) {

            //Code = findViewById(R.id.spnGantryCode);
            //String code = Code.getSelectedItem().toString();

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "findLocationById/" + code + "/2/";
            System.out.println(code);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
            //System.out.println(rest.getForObject(url6, MmsAddgantry.class));

            objGantryloc2 =  rest.getForObject(url6, MmsGantryloc.class);;

            return rest.getForObject(url6, MmsGantryloc.class);
        }
    }
    /////////// P O I N T  3
    private class loadGantryLocObj3 extends AsyncTask<String, Void, MmsGantryloc> {

        @Override
        protected MmsGantryloc doInBackground(String... urls) {

            //Code = findViewById(R.id.spnGantryCode);
            //String code = Code.getSelectedItem().toString();

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "findLocationById/" + code + "/3/";
            System.out.println(code);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
            //System.out.println(rest.getForObject(url6, MmsAddgantry.class));

            objGantryloc3 =  rest.getForObject(url6, MmsGantryloc.class);;
            return rest.getForObject(url6, MmsGantryloc.class);
        }
    }
    /////////// P O I N T  4
    private class loadGantryLocObj4 extends AsyncTask<String, Void, MmsGantryloc> {

        @Override
        protected MmsGantryloc doInBackground(String... urls) {

            //Code = findViewById(R.id.spnGantryCode);
            //String code = Code.getSelectedItem().toString();

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "findLocationById/" + code + "/4/";
            System.out.println(code);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
            //System.out.println(rest.getForObject(url6, MmsAddgantry.class));

            objGantryloc4 =  rest.getForObject(url6, MmsGantryloc.class);;

            ProgDialog.dismiss();
            return rest.getForObject(url6, MmsGantryloc.class);
        }
    }

    private class AddGantryLocDB extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... urls) {

            String lati1 = latitude1.getText().toString();
            String lati2 = latitude2.getText().toString();
            String lati3 = latitude3.getText().toString();
            String lati4 = latitude4.getText().toString();
            String longi1 = longitude1.getText().toString();
            String longi2 = longitude2.getText().toString();
            String longi3 = longitude3.getText().toString();
            String longi4 = longitude4.getText().toString();

            System.out.println("############################### Test Intialized variable ##############################################");

            objGantryloc1.setGpsLatitude(new BigDecimal(lati1));
            objGantryloc1.setGpsLongitude(new BigDecimal(longi1));
            objGantryloc2.setGpsLatitude(new BigDecimal(lati2));
            objGantryloc2.setGpsLongitude(new BigDecimal(longi2));
            objGantryloc3.setGpsLatitude(new BigDecimal(lati3));
            objGantryloc3.setGpsLongitude(new BigDecimal(longi3));
            objGantryloc4.setGpsLatitude(new BigDecimal(lati4));
            objGantryloc4.setGpsLongitude(new BigDecimal(longi4));


            System.out.println("Set object testing :****************************************");
            System.out.println("Latitude: "+objGantryloc1.getGpsLatitude());
            System.out.println("Longitude: "+objGantryloc1.getGpsLongitude());
            System.out.println("ID: "+objGantryloc1.getId());

            List<MmsGantryloc> obj = new ArrayList<MmsGantryloc>();
            obj.add(objGantryloc1);
            obj.add(objGantryloc2);
            obj.add(objGantryloc3);
            obj.add(objGantryloc4);

            final RestTemplate restTemplate = new RestTemplate();
            final String url2 = Util.SRT_URL + "MMSUpdateGantryLocMobile";

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            for (int i=0; i<3;i++){
                String objReturn = restTemplate.postForObject(url2, obj.get(i), String.class);
                System.out.println("objReturn::::::::::::::::::::::::::::::::::::::: "+i+" :"+objReturn);
            }

            return null;
        }
    }
}
