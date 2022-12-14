package com.example.akla.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import static com.example.akla.login.Util.isConnected;

//import org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString;

public class EditSupport extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText etConType;
    EditText etTowerType;
    EditText etCSC;
    EditText etArea;
  // EditText ConType;

    private Menu mymenu;


    ProgressDialog ProgDialog;
    RadioGroup SupportTypeGroup;
    private RadioButton SupportTypeButton;

    RadioGroup NumberOfCircuitsradioGroup;
    private  RadioButton NoOfCircuitsButton;

    RadioGroup BodyExtensionRadioGroup;
    private RadioButton BodyExtensionButton;

    EditText SupportLineName;
    EditText SupportID;
    EditText TowerNo;
    Spinner SpinnerArea;
    Spinner SpinnerLine;
    Spinner SpinnerTowerNo;
    Spinner SpinnerTowerType;
    Spinner spinnerTowerConfig;
    ImageView imageView;


    Spinner CSC;
    Spinner SpinnerConductorType;
    Spinner EarthConductorType;
    Spinner spinnerTowerType;
    Spinner spinnerConType;
    Spinner SpinnerTowerConfiguration;
    TextView GPSLatitude;
    TextView GPSLongititude;
    TextView a;
    TextView b;


   // Spinner spinnerTowerConfig;

    DBHelper mydb3;

    LocationManager locationManager;
    String provider;
    String aLan;
    String bLon;
    double lat;
    double lon;

    String[] values = new String[]{};
    String[] valuesLine = new String[]{};
    String[] valuesConType = new String[]{};
    String[] valuesTowerType = new String[]{};
    String[] valuesTowerConfig = new String[]{};

    HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
    HashMap<Integer,String> spinnerMap1 = new HashMap<Integer, String>();
    HashMap<Integer,String> spinnerMap2 = new HashMap<Integer, String>();
    HashMap<Integer,String> spinnerMap3 = new HashMap<Integer, String>();
    HashMap<Integer,String> spinnerMap4 = new HashMap<Integer, String>();
    HashMap<Integer,Long> spinnerMap5 = new HashMap<Integer, Long>();

    String area;
    String line;
    String ConType;
    String TowerType;
    String TowerConfig;


    //for open camera and capture a image
    private static final int RESULT_LOAD_IMAGE = 1;
    private EditText uploadImageName;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 2;
    private static final String FILE_PROVIDER_AUTHORITY = "com.example.akla.login";
    private ImageView imageToUpload;
    private Button bUploadImage;
    private String mTempPhotoPath;
    private Bitmap mResultsBitmap;

    String picturePath;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    String ba2;
    byte[] ba;
    private Bitmap myBitmap;
    private String savedImagePath;

    WritableWorkbook workbook;
    WritableSheet sheet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_support);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        System.out.println("addsupport1");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        System.out.println("addsupport2");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        System.out.println("addsupport13");
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /**For image upload*/
        imageToUpload = findViewById(R.id.imageToUpload);
        bUploadImage = findViewById(R.id.bUploadImage);
        //uploadImageName = (EditText)findViewById(R.id.etUploadImage);
        imageToUpload.setVisibility(View.VISIBLE);
        bUploadImage.setVisibility(View.VISIBLE);

        bUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for the camera permission
                if (ContextCompat.checkSelfPermission(EditSupport.this.getApplicationContext(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    // If you do not have permission, request it
                    ActivityCompat.requestPermissions(EditSupport.this,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_IMAGE_CAPTURE);
                }
                // Check for the external storage permission
                if (ContextCompat.checkSelfPermission(EditSupport.this.getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // If you do not have permission, request it
                    ActivityCompat.requestPermissions(EditSupport.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_STORAGE_PERMISSION);
                } else {
                    // Launch the camera if the permission exists
                    EditSupport.this.launchCamera();
                }
            }
        });


        if(!Util.isConnected(EditSupport.this)){
               readExcel();
                   Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
               readExcelLine();
                   Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
               readExcelConType();
                   Toast.makeText(getApplication(),"readExcel2: " , Toast.LENGTH_SHORT).show();
               readExcelTowerType();
                   Toast.makeText(getApplication(),"readExcel3: " , Toast.LENGTH_SHORT).show();
               readExcelTowerConfig();
                   Toast.makeText(getApplication(),"readExcel4: " , Toast.LENGTH_SHORT).show();
        }else {
            new EditSupport.loadarea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            //new AddSupport.loadLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new EditSupport.loadConType().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new EditSupport.loadTowerType().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new EditSupport.loadTowerConfig().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            statusCheck();

        }
        System.out.println("hhhhh2");

    /*    new loadarea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
          System.out.println("hhhhh2");*/
       // new AddSupport.loadLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
       /* new loadConType().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
          System.out.println("hhhhh3");*/
       /* new loadTowerType().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
          System.out.println("hhhhh4");
          new loadTowerConfig().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
          System.out.println("hhhhh5");
*/

        GPSLatitude = findViewById(R.id.etLatitude);
        GPSLongititude = findViewById(R.id.etLongitude);

//        statusCheck();
//        ActivityCompat.requestPermissions(AddSupport.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);

        SpinnerTowerConfiguration = findViewById(R.id.spinnerTowerConfig);
        SpinnerTowerConfiguration.setPrompt("Select Tower Configuration");

        SpinnerTowerConfiguration.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idTowerConfiguration = spinnerMap4.get(parent.getSelectedItemPosition());
                TowerConfig = idTowerConfiguration;
               //  Toast.makeText(AddSupport.this,"Name" +name,Toast.LENGTH_LONG).show();
              //   Toast.makeText(AddSupport.this,"TowerConfiguration"+idTowerConfiguration,Toast.LENGTH_LONG).show();

            }



            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        SpinnerConductorType = findViewById(R.id.spinnerConType);

      // SpinnerLine =(Spinner)findViewById(R.id.spinnerLineName);
         SpinnerConductorType.setPrompt("Select Conductor Type");

        SpinnerConductorType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idConType = spinnerMap2.get(parent.getSelectedItemPosition());
                ConType=idConType;
                // Toast.makeText(AddSupport.this,"Name" +name,Toast.LENGTH_LONG).show();
                // Toast.makeText(AddSupport.this,"ConductorType"+idConType,Toast.LENGTH_LONG).show();

            }



            public void onNothingSelected(AdapterView<?> parent) {
           }
        });




      //  SpinnerConductorType.setPrompt("Select Conductor Type");
        //etConType = (EditText) findViewById(R.id.etConType);


      //  new loadLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //System.out.println("hhhhh1");





      //  SpinnerConductorType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

        //    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          //      String selectedItem = parent.getSelectedItem().toString();

            //    if (selectedItem.equals("Other")) {
              //      etConType.setVisibility(View.VISIBLE);

                //}
            //}

            //public void onNothingSelected(AdapterView<?> parent) {
            //}


        //});

        CSC = findViewById(R.id.spinnerCSC);
        CSC.setPrompt("Select CSC");
        etCSC = findViewById(R.id.EtCSC);

        CSC.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if (selectedItem.equals("Other")) {
                    etCSC.setVisibility(View.VISIBLE);

                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });


        spinnerTowerType = findViewById(R.id.spinnerTowerType);

        spinnerTowerType.setPrompt("Select Tower Type");

        spinnerTowerType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idTowerType = spinnerMap3.get(parent.getSelectedItemPosition());
                TowerType=idTowerType;
               // Toast.makeText(AddSupport.this,"Name" +name,Toast.LENGTH_LONG).show();
               // Toast.makeText(AddSupport.this,"TowerType"+idTowerType,Toast.LENGTH_LONG).show();

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //spinnerTowerType.setPrompt("Select Tower Type");
        //etTowerType = (EditText) findViewById(R.id.etTowerType);


        //spinnerTowerType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

          //  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //    String selectedItem = parent.getSelectedItem().toString();

              //  if (selectedItem.equals("Other")) {
                //    etTowerType.setVisibility(View.VISIBLE);
                //}
            //}

            //public void onNothingSelected(AdapterView<?> parent) {
            //}
        //});
        SpinnerArea = findViewById(R.id.spinnerArea);
        SpinnerArea.setPrompt("Select Area");
        SpinnerLine = findViewById(R.id.spinnerLineName);
        SpinnerLine.setPrompt("Select line");
        SpinnerLine.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idLINEID = spinnerMap1.get(parent.getSelectedItemPosition());
                line=idLINEID;
               // Toast.makeText(AddSupport.this,"Name" +name,Toast.LENGTH_LONG).show();
               // Toast.makeText(AddSupport.this,"idlineid"+idLINEID,Toast.LENGTH_LONG).show();

            }



            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        etArea = findViewById(R.id.etArea);

        SpinnerArea.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idarea = spinnerMap.get(parent.getSelectedItemPosition());
                area =idarea;
                //Toast.makeText(AddSupport.this,"Name" +name,Toast.LENGTH_LONG).show();
                //Toast.makeText(AddSupport.this,"idarea"+idarea,Toast.LENGTH_LONG).show();



                if(!Util.isConnected(EditSupport.this)){
                    readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new loadLineByArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
             //   new loadLineByArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


            }



            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button ButtonSendDB = findViewById(R.id.btnSaveDB);
        ButtonSendDB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                {
                    TowerNo = findViewById(R.id.etTowerNo);
                    SpinnerArea = findViewById(R.id.spinnerArea);
                    SpinnerLine = findViewById(R.id.spinnerLineName);
                    SpinnerConductorType = findViewById(R.id.spinnerConType);
                    SpinnerTowerType = findViewById(R.id.spinnerTowerType);
                    SpinnerTowerConfiguration = findViewById(R.id.spinnerTowerConfig);
                    GPSLongititude = findViewById(R.id.etLongitude);
                    GPSLatitude = findViewById(R.id.etLatitude);





                    if (TowerNo.getText().toString().trim().equals("")) {
                        TowerNo.setError("Should add a Tower Number!");
                    } /*
                    else if (GPSLongititude.getText().toString().trim().equals("")) {
                        GPSLongititude.setError("Should add a GPSLongititude!");
                    } else if (GPSLatitude.getText().toString().trim().equals("")) {
                        GPSLatitude.setError("Should add a GPSLatitude!");
                    }*/ else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EditSupport.this);
                        builder.setCancelable(true);
                        builder.setIcon(R.drawable.logo);
                        builder.setMessage("Do you want to save Support Data?");
                        builder.setTitle("Save Support");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!isConnected(EditSupport.this)){
                                            createExcel();
                                            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            new AddSupportDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                             Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                                            createExcel();
                                        }
                                        /*new EditSupportDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                                        createExcel();*/
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


        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                TowerNo = findViewById(R.id.etTowerNo);
                SpinnerArea = findViewById(R.id.spinnerArea);
                SpinnerLine = findViewById(R.id.spinnerLineName);
                SpinnerConductorType = findViewById(R.id.spinnerConType);
                SpinnerTowerType = findViewById(R.id.spinnerTowerType);
                SpinnerTowerConfiguration = findViewById(R.id.spinnerTowerConfig);
                GPSLongititude = findViewById(R.id.etLongitude);
                GPSLatitude = findViewById(R.id.etLatitude);


                TowerNo.setText("");
                new loadarea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new loadLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new loadConType().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new loadTowerType().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new loadTowerConfig().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                GPSLongititude.setText("");
                GPSLatitude.setText("");
                imageToUpload.setImageBitmap(null);




            }
        });
        Button buttonSync = findViewById(R.id.btnSync);

        if (!isConnected(EditSupport.this)) {

            findViewById(R.id.btnSync).setVisibility(View.INVISIBLE);
        }
        else {
            findViewById(R.id.btnSync).setVisibility(View.VISIBLE);
        }

        buttonSync.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                {


                    if (!isConnected(EditSupport.this)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditSupport.this);
                        builder.setCancelable(true);
                        builder.setMessage("Please Check Your Internet Connection");
                        builder.setTitle("No Internet Connection");
                        builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        // new SaveSupportFromExcel().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                         //Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                                        //createExcel();
                                       // readSupport();
                                    }

                                });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(), "UnSuccessfully!", Toast.LENGTH_SHORT).show();

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();


                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EditSupport.this);
                        builder.setCancelable(true);
                        builder.setMessage("Do you want to save the line?");
                        builder.setTitle("Save Support");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        new SaveSupportFromExcel().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                     //   readSupport();
                                        Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                                        // createExcel();
                                    }

                                });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "UnSuccessfully!", Toast.LENGTH_SHORT).show();

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }


            }


        });


}



    public void alertMessage() {
//        Toast.makeText(AddTowerMaintainance.this, "Error",
//                Toast.LENGTH_LONG).show();
        System.out.println("alertMessagealertMessage");

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        System.out.println("alertMessagealertMessage1");
                        Toast.makeText(EditSupport.this, "Yes Clicked",
                                Toast.LENGTH_LONG).show();
                        // Yes button clicked
                        // Yes button clicked
                        // Toast.makeText(AddSupport.this, "Yes Clicked",
                        //   Toast.LENGTH_LONG).show();
                        //progressSaving();
                        // new AddTowerMaintainanceData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        createExcel();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked
                        // do nothing
                        //  Toast.makeText(AddSupport.this, "No Clicked",
                        //   Toast.LENGTH_LONG).show();
                        Toast.makeText(EditSupport.this, "No Clicked",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want create Excel sheet?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }


    private class SaveSupportFromExcel extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            System.out.println(" SaveSupportFromExcel1 " );
            MmsAddsupport objNew = new MmsAddsupport();

            objNew.setTowerNo("TEST1");
            objNew.setArea("471.00");
            objNew.setLineId(new BigDecimal("69"));
            objNew.setConductorType(new BigDecimal("3"));
            objNew.setSupportType(new BigDecimal("2"));
            objNew.setTowerType(new BigDecimal("2"));
            objNew.setNoOfCircuits(new BigDecimal("2"));
            objNew.setTowerConfiguration(new BigDecimal("2"));
            objNew.setBodyExtension("2");
            objNew.setGpsLongitude(new BigDecimal("0"));
            objNew.setGpsLatitude(new BigDecimal("0"));

            SessionManager objS = new SessionManager(getBaseContext());
            String PhmBranch = objS.getPhmBranch();
            String userNm = objS.getUserName();
            System.out.println("PhmBranch" + PhmBranch.trim());
            PhmBranch =PhmBranch.trim();


           List<MmsAddsupport> obj = readSupport();
          //  List<MmsAddsupport> obj = new ArrayList<MmsAddsupport>(1);
            //obj.add(objNew);
            if(obj != null){
                int size = obj.size();

                for(int i = 1;i<size;i++){


                    MmsAddsupport support = obj.get(i);
                    support.setPhmBranch(PhmBranch);
                    support.setEntBy(userNm);
                    /*final RestTemplate restTemplate = new RestTemplate();
                    String url4 = Util.SRT_URL + "addSupportDB/" +support.getArea() + "/"+ support.getBodyExtension() + "/" + support.getConductorType() + "/" + PhmBranch + "/" +
                            "1"+"/"+ support.getGpsLongitude() + "/" + support.getGpsLatitude() + "/"+support.getLineId() + "/"+ support.getSupportType() +"/" +
                            support.getTowerConfiguration() +"/"+support.getTowerNo() + "/" + support.getTowerType() + "/"+ support.getNoOfCircuits()+"/" + PhmBranch + "/";

//                     Toast.makeText(getApplication(),"url4: " + url4, Toast.LENGTH_SHORT).show();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    result= restTemplate.getForObject(url4, String.class);
                    System.out.println("hiii5555 yyyyyyyyyhhjj" + url4);*/

                    final RestTemplate restTemplate = new RestTemplate();
                    final String url1 = Util.SRT_URL + "MMSAddSupportMobile";
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    String objReturn = restTemplate.postForObject(url1, support, String.class);

                }
            } else{
                System.out.println(" .list is empty " );
                result = " .list is empty ";

            }
            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            try {

                //System.out.println("hiii5555 yyyyyyyyy" + result);

            } catch (Exception e) {
                System.out.println(" error " + e.getMessage());
            }


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
                SpinnerArea = findViewById(R.id.spinnerArea);
                SpinnerArea.setAdapter(adapterNs);

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
                SpinnerLine = findViewById(R.id.spinnerLineName);
                SpinnerLine.setAdapter(adapterNs);

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

 public List<String>  readExcelConType(){
        System.out.println("readExcel");
        // Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
        List<String> resultSet = new ArrayList<String>();
        // Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

        System.out.println("readExcel1");
        File inputWorkbook  = new File(Environment.getExternalStorageDirectory() +File.separator + "Schedule" +File.separator+ "ConductorType.xls");
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
                valuesConType = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);
                    System.out.println(cell.getContents());
                    //          Toast.makeText(getApplication(),"readExcel5: " + cell.getContents(), Toast.LENGTH_SHORT).show();
                    spinnerMap2.put(j,cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    valuesConType[j] = cell1.getContents();

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



                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesConType);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerConductorType = findViewById(R.id.spinnerConType);
                SpinnerConductorType.setAdapter(adapterNs);

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

 public List<String>   readExcelTowerType(){
        System.out.println("readExcel");
        // Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
        List<String> resultSet = new ArrayList<String>();
        // Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

        System.out.println("readExcel1");
        File inputWorkbook  = new File(Environment.getExternalStorageDirectory() +File.separator + "Schedule" +File.separator+ "TowerType.xls");
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
                valuesTowerType = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);
                    System.out.println(cell.getContents());
                    //          Toast.makeText(getApplication(),"readExcel5: " + cell.getContents(), Toast.LENGTH_SHORT).show();
                    spinnerMap3.put(j,cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    valuesTowerType[j] = cell1.getContents();

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



                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesTowerType);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerTowerType = findViewById(R.id.spinnerTowerType);
                SpinnerTowerType.setAdapter(adapterNs);


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

    public List<String>   readExcelTowerConfig(){
        System.out.println("readExcel");
        // Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
        List<String> resultSet = new ArrayList<String>();
        // Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

        System.out.println("readExcel1");
        File inputWorkbook  = new File(Environment.getExternalStorageDirectory() +File.separator + "Schedule" +File.separator+ "TowerConf.xls");
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
                valuesTowerConfig = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);
                    System.out.println(cell.getContents());
                    //          Toast.makeText(getApplication(),"readExcel5: " + cell.getContents(), Toast.LENGTH_SHORT).show();
                    spinnerMap4.put(j,cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    valuesTowerConfig[j] = cell1.getContents();

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



                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesTowerConfig);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerTowerConfiguration = findViewById(R.id.spinnerTowerConfig);
                SpinnerTowerConfiguration.setAdapter(adapterNs);

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

    public void createExcel() {

        System.out.println("createExcel1");

        //tower number
        TowerNo = findViewById(R.id.etTowerNo);
        //final EditText   TowerNo1 = (EditText) findViewById(R.id.etTowerNo);
        final String towerno = TowerNo.getText().toString();

        // if(TowerNo.getText().toString().trim().equals("")){
        //      TowerNo.setError("Should add a Tower Number!");
        // }else {

        /** StyleSpan boldSpan = new StyleSpan(Typeface.NORMAL);
         int start = TowerNo1.getSelectionStart();
         int end = TowerNo1.getSelectionEnd();
         int flag = Spannable.SPAN_INCLUSIVE_INCLUSIVE;
         TowerNo1.getText().setSpan(boldSpan, start, end, flag);*/

        System.out.println("createExcel2");
        //area
        SpinnerArea = findViewById(R.id.spinnerArea);
        String supportArea = SpinnerArea.getSelectedItem().toString();

        etArea = findViewById(R.id.etArea);
        String etArea1 = etArea.getText().toString();

        System.out.println("createExcel3");
        //csc
        CSC = findViewById(R.id.spinnerCSC);
        String suportCSC = CSC.getSelectedItem().toString();

        etCSC = findViewById(R.id.EtCSC);
        String EtCSC1 = etCSC.getText().toString();

        System.out.println("createExcel4");
        //Conductor Type
        SpinnerConductorType = findViewById(R.id.spinnerConType);
        String conductorType = SpinnerConductorType.getSelectedItem().toString();

        etConType = findViewById(R.id.etConType);
        String contypeEt = etConType.getText().toString();

        System.out.println("createExcel5");

        //Earth Conductor Type
        EarthConductorType = findViewById(R.id.spinnerConType);
        final String esrthconductorType = EarthConductorType.getSelectedItem().toString();
        System.out.println("createExcel6");

        //support type
        SupportTypeGroup = findViewById(R.id.radioGroupSupportType);
        final  int supporttype = SupportTypeGroup.getCheckedRadioButtonId();

        SupportTypeButton = findViewById(supporttype);
        String selectedSupportTypeRadio = SupportTypeButton.getText().toString();
        System.out.println("createExcel7");

        //Tower type
        SpinnerTowerType = findViewById(R.id.spinnerTowerType);
        String towerType = SpinnerTowerType.getSelectedItem().toString();

        etTowerType = findViewById(R.id.etTowerType);
        String TowerTypeET = etTowerType.getText().toString();


        System.out.println("createExcel8");

        //Circuit type
        NumberOfCircuitsradioGroup = findViewById(R.id.NumberOfCircuitsradioGroup);
        final int noofcircuits = NumberOfCircuitsradioGroup.getCheckedRadioButtonId();
        NoOfCircuitsButton = findViewById(noofcircuits);
        final String selectedNoOfCircuitsRadio = NoOfCircuitsButton.getText().toString();
        System.out.println("createExcel9");
        //Tower Config
        spinnerTowerConfig = findViewById(R.id.spinnerTowerConfig);
        final String towerConfig = spinnerTowerConfig.getSelectedItem().toString();
        System.out.println("createExcel0");
        //Body Extension
        BodyExtensionRadioGroup = findViewById(R.id.BodyExtensionradioGroup);
        final int bodyextension = BodyExtensionRadioGroup.getCheckedRadioButtonId();
        BodyExtensionButton = findViewById(bodyextension);
        final String selectedBodyExtensionRadio = BodyExtensionButton.getText().toString();
        System.out.println("createExcel11");
        //longitude
        GPSLongititude = findViewById(R.id.etLongitude);
        final String gpsLongititude = GPSLongititude.getText().toString();
        System.out.println("createExcel2");
        //latitude
        GPSLatitude = findViewById(R.id.etLatitude);
        final String gpsLatitude = GPSLatitude.getText().toString();
        System.out.println("createExcel3");
        //Line name
       // SupportLineName = (EditText) findViewById(R.id.etLineName);
       // final String lineName = SupportLineName.getText().toString();
       // System.out.println("createExcel4");

        SpinnerLine = findViewById(R.id.spinnerLineName);
        final String lineName = SpinnerLine.getSelectedItem().toString();
        // System.out.println("createExcel4");


       // SupportID = (EditText) findViewById(R.id.etId);

       //String supportid = SupportID.getText().toString();


        try {

//            Toast.makeText(getApplication(),
//                    "DBHelper: " , Toast.LENGTH_SHORT).show();
            mydb3 = new DBHelper(this);


//            Toast.makeText(getApplication(),
//                   "DBHelper2: " , Toast.LENGTH_SHORT).show();
            //mydb3.onUpgrade(db,1,2);
            // mydb3.insertAddSupport(selectedSupportTypeRadio,supportid,lineName,towerno,supportArea,suportCSC,conductorType,
            //towerType,towerConfig,gpsLatitude,gpsLongititude,selectedNoOfCircuitsRadio,selectedBodyExtensionRadio);

            if(conductorType.equals("Other")){

                conductorType= contypeEt;


            }


            if( towerType.equals("Other")){


                towerType = TowerTypeET;


            }if( suportCSC.equals("Other")) {


                suportCSC = EtCSC1;

            }if(supportArea.equals("Other")){
                supportArea = etArea1;
            }



            selectedSupportTypeRadio = selectedSupportTypeRadio.trim();
            String suType = "";
            if (selectedSupportTypeRadio.equalsIgnoreCase("Tower")) {
                suType = "1";

            } else if (selectedSupportTypeRadio.equalsIgnoreCase("Pole")) {
                suType = "2";
            } else if (selectedSupportTypeRadio.equalsIgnoreCase("Gantry Bay")) {
                suType = "3";
            } else if (selectedSupportTypeRadio.equalsIgnoreCase("GSS Bay")){
                suType = "4";
            }


            String suCircuits="";
            if (selectedNoOfCircuitsRadio.equalsIgnoreCase("Single")) {
                suCircuits = "1";
            }
            else if (selectedNoOfCircuitsRadio.equalsIgnoreCase("Double")) {
                suCircuits = "2";
            }
            else if (selectedNoOfCircuitsRadio.equalsIgnoreCase("Four")) {
                suCircuits = "4";
            }

            String status="";
            if (selectedNoOfCircuitsRadio.equalsIgnoreCase("")) {
                status = "2";
            }

            //get PhmBranch
            SessionManager objSess = new SessionManager(getBaseContext());
            String phm_branch = objSess.getPhmBranch();
            String entBy = objSess.getUserName();
            System.out.println("phm_branch" + phm_branch.trim());
            phm_branch = phm_branch.trim();


            mydb3.insertAddSupportA(towerno, area, suportCSC, ConType, esrthconductorType, suType, TowerType, suCircuits, TowerConfig, selectedBodyExtensionRadio, gpsLongititude, gpsLatitude, line, phm_branch, status, entBy);


//            Toast.makeText(getApplication(),
//                    "DBHelper3: " , Toast.LENGTH_SHORT).show();

            //getSupportByLineId
            //final Cursor res3 = mydb3.getAddSupportDataA();



//            Toast.makeText(getApplication(),
//                  "DBHelper4: " , Toast.LENGTH_SHORT).show();

//file path
            final Cursor res3 = mydb3.getSupportByLineId(line);
            //final Cursor res3 = mydb3.getData();
            File sd = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "Support");

            boolean success = true;
            if (!sd.exists()) {
                success = sd.mkdirs();
            }
            if (success) {
                // Do something on success
            } else {
                // Do something else on failure
            }

            //File sd = Environment.getExternalStorageDirectory();
            //String csvFile = lineName+".xls";
            String csvFile = "Support.xls";
            //Toast.makeText(AddSupport.this,"Excel sheet" + csvFile,Toast.LENGTH_LONG).show();
            File directory = new File(sd.getAbsolutePath());
          //  final Cursor res4 = mydb3.getAddLineData();

            //create directory if not exist
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }


            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            //WritableWorkbook workbook;

            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            //WritableSheet sheet = workbook.createSheet("AddSupportList", 0);
            sheet = workbook.createSheet("AddSupportList", 0);
            // column and row
            String[] columnName = res3.getColumnNames();
//





//            Toast.makeText(getApplication(),
//                  "columnName 1: " + columnName, Toast.LENGTH_SHORT).show();


            //if(columnName ==null) {
            sheet.addCell(new Label(0, 0, "Support_Type"));
            sheet.addCell(new Label(1, 0, "Tower_No"));
            sheet.addCell(new Label(2, 0, "Area"));
            sheet.addCell(new Label(3, 0, "Conductor_Type"));
            sheet.addCell(new Label(4, 0, "Earth_conductor_Type"));
            sheet.addCell(new Label(5, 0, "Tower_Type"));
            sheet.addCell(new Label(6, 0, "Tower_Config"));
            sheet.addCell(new Label(7, 0, "Latitude"));
            sheet.addCell(new Label(8, 0, "Longitude"));
            sheet.addCell(new Label(9, 0, "Circuit_Type"));
            sheet.addCell(new Label(10, 0, "Body_Exten"));
            sheet.addCell(new Label(11, 0, "Line_ID"));
            sheet.addCell(new Label(12, 0, "Tapping"));
            sheet.addCell(new Label(13, 0, "Map_ID"));
            sheet.addCell(new Label(14, 0, "CSC"));
            sheet.addCell(new Label(15, 0, "PHM_Branch"));
            sheet.addCell(new Label(16, 0, "Status"));
            sheet.addCell(new Label(17, 0, "Ent_by"));


            //sheet.addCell(new Label(1, 0, "Line_Name"));
            //sheet.addCell(new Label(2, 0, "ID"));
            //sheet.addCell(new Label(3, 0, "PHM_Branch"));
            // sheet.addCell(new Label(6, 0, "CSC"));
            // sheet.addCell(new Label(8, 0, "Earth_conductor_Type"));
            //sheet.addCell(new Label(11, 0, "Status"));
            //sheet.addCell(new Label(12, 0, "Aproval_Level"));
            //sheet.addCell(new Label(13, 0, "Filepath"));
           // sheet.addCell(new Label(21, 0, "RowID"));
            //sheet.addCell(new Label(12, 0, "Line_Name"));
            //sheet.addCell(new Label(0, 0, "Tower_No"));
            //sheet.addCell(new Label(1, 0, "Area"));
            //sheet.addCell(new Label(2, 0, "CSC"));
            //sheet.addCell(new Label(3, 0, "Conductor_Type"));
            //sheet.addCell(new Label(4, 0, "Earth_conductor_Type"));
            //sheet.addCell(new Label(5, 0, "Support_Type"));
            //sheet.addCell(new Label(6, 0, "Tower_Type"));
            //sheet.addCell(new Label(7, 0, "Circuit_Type"));
            //sheet.addCell(new Label(8, 0, "Tower_Config"));
            //sheet.addCell(new Label(9, 0, "Body_Exten"));
            //sheet.addCell(new Label(10, 0, "Longitude"));
            //sheet.addCell(new Label(11, 0, "Latitude"));
            //sheet.addCell(new Label(12, 0, "Line_Name"));


//            Toast.makeText(getApplication(),
//                        "column created 1", Toast.LENGTH_SHORT).show();

            // sheet.addCell(new Label(1, 0, "phone"));
            //}
//            Toast.makeText(getApplication(),
//                    "res3" +res3, Toast.LENGTH_SHORT).show();

            if (res3.moveToFirst()) {

                do {
//                   Toast.makeText(getApplication(),
//                            "rrrr", Toast.LENGTH_SHORT).show();

                    String supportType = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_TYPE));
                    String supportTowerNo = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_TOWERNO));
                    String supportAreaE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_AREA));
                    String supportConductorType = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_CONDUCTOR_TYPE));
                    String supportEarthConductorType = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_EARTH_CONDUCTOR_TYPE));
                    String supportTowerType = res3.getString(res3.getColumnIndex(DBHelper.SUPPPORT_TOWER_TYPE));
                    String towerConfiguration = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_TOWER_CONFIGURATION));
                    String gpsLatitudeE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_GPS_LATITUDE));
                    String gpsLongititudeE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_GPS_LONGITITUDE));
                    String noofcircuits1 = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_NOOFCIRCUITS));
                    String bodyExtension1 = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_BODY_EXTENSION));
                    String lineNameE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_LINE_NAME));
                    String phmBranchE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_PHM_BRANCH));
                    String statusE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_STATUS));
                    String entByE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_ENT_BY));
                    //String tappingE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_TAPPING));
                    //String mapIdE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_MAP_ID));
                    //String cscE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_CSC));
                    //  String supportCSCE = res3.getString(res3.getColumnIndex(DBHelper.SUPPORT_CSC));


                    //String maindate = res3.getString(res3.getColumnIndex(DBHelper.TM_MAIN_DATE));
                    //String maindate = "";


//                   Toast.makeText(getApplication(),
//                            "nooftappings" +nooftappings, Toast.LENGTH_SHORT).show();

                    // String phoneNumber = res.getString(res.getColumnIndex("phone"));
//                    Toast.makeText(getApplication(),
//                            "rrrr 1", Toast.LENGTH_SHORT).show();
                    int i = res3.getPosition() + 1;
//                    Toast.makeText(getApplication(),
//                            "rrrr 2", Toast.LENGTH_SHORT).show();


                    sheet.addCell(new Label(0, i, supportType));
                    sheet.addCell(new Label(1, i, supportTowerNo));
                    sheet.addCell(new Label(2, i, supportAreaE));
                    sheet.addCell(new Label(3, i, supportConductorType));
                    //sheet.addCell(new Label(4, i, supportEarthConductorType));
                    sheet.addCell(new Label(4, i, "0"));
                    sheet.addCell(new Label(5, i, supportTowerType));
                    sheet.addCell(new Label(6, i, towerConfiguration));
                    sheet.addCell(new Label(7, i, gpsLatitudeE));
                    sheet.addCell(new Label(8, i, gpsLongititudeE));
                    sheet.addCell(new Label(9, i, noofcircuits1));
                    sheet.addCell(new Label(10, i, bodyExtension1));
                    sheet.addCell(new Label(11, i, lineNameE));
                    sheet.addCell(new Label(12, i, "0"));
                    sheet.addCell(new Label(13, i, "0"));
                    sheet.addCell(new Label(14, i, "0"));
                    sheet.addCell(new Label(15, i, phmBranchE));
                    sheet.addCell(new Label(16, i, "2"));
                    sheet.addCell(new Label(17, i, entByE));
                    //sheet.addCell(new Label(14, i, "2"));
                    // sheet.addCell(new Label(6, i, supportCSCE));
                    /* sheet.addCell(new Label(1, i, ""));
                    sheet.addCell(new Label(2, i, " "));
                    sheet.addCell(new Label(3, i, " "));*/


                  //  sheet.addCell(new Label(0, i, supportTowerNo));
                   // sheet.addCell(new Label(1, i, supportAreaE));
                   // sheet.addCell(new Label(2, i, supportCSCE));
                   // sheet.addCell(new Label(3, i, supportConductorType));
                   // sheet.addCell(new Label(4, i, supportEarthConductorType));
                   // sheet.addCell(new Label(5, i, supportType));
                   // sheet.addCell(new Label(6, i, supportTowerType));
                   // sheet.addCell(new Label(7, i, noofcircuits1));
                   // sheet.addCell(new Label(8, i, towerConfiguration));
                   // sheet.addCell(new Label(9, i, bodyExtension1));
                   // sheet.addCell(new Label(10, i, gpsLongititudeE));
                   // sheet.addCell(new Label(11, i, gpsLatitudeE));
                   // sheet.addCell(new Label(12, i, lineNameE));


                    //sheet.addCell(new Label(5, i, maindate));


//                    Toast.makeText(getApplication(),
//                           "rrrr 3", Toast.LENGTH_SHORT).show();
                    //sheet.addCell(new Label(1, i, phoneNumber));
                } while (res3.moveToNext());
            }

            //closing cursor
            res3.close();
            workbook.write();
            workbook.close();
//                Toast.makeText(getApplication(),
//                        "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
        } catch (WriteException e) {
            e.printStackTrace();
            //Toast.makeText(getApplication(),
              //      " Error while generating excel sheet : ", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(getApplication(),
              //      "error while generating excel sheet: ", Toast.LENGTH_SHORT).show();


        }

      // TowerNo.setText("");
       // SupportLineName.setText("");
       // etTowerType.setVisibility(View.INVISIBLE);
       // etTowerType.setText("");
       // etConType.setVisibility(View.INVISIBLE);
       // etConType.setText("");
       // etCSC.setVisibility(View.INVISIBLE);
       // etCSC.setText("");
       // CSC.setPrompt("Select CSC");
       // etArea.setVisibility(View.INVISIBLE);
       // etArea.setText("");
        //SupportID.setText("");
        //SupportLineName.setText("");

        TowerNo.setText("");
        GPSLatitude.setText("");
        GPSLatitude.setText("");

        //}

        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(EditSupport.this);
        builder1.setTitle("MV-MMS");
        builder1.setIcon(R.drawable.ceb_logo2);
        builder1.setMessage("Data Saved in Excel Sheet")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        android.app.AlertDialog alert = builder1.create();
        alert.show();
        progressSaving();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
       // if(etConType.getVisibility()==View.VISIBLE){
         //   etConType.setVisibility(View.INVISIBLE);
        //}
       // else
            //if(etTowerType.getVisibility()==View.VISIBLE){
            //etTowerType.setVisibility(View.INVISIBLE);
        //}else
            if(etCSC.getVisibility()==View.VISIBLE){
            etCSC.setVisibility(View.INVISIBLE);
        }else if(etArea.getVisibility()==View.VISIBLE){
            etArea.setVisibility(View.INVISIBLE);
        }else if(drawer.isDrawerOpen(GravityCompat.START)) {
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.apphome) {
            Intent intent = new Intent(EditSupport.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(EditSupport.this, GetNearByTower.class);
            startActivity(intent);

            //////////////////////////////// PHM - LCM ////////////////////////////////////////////

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(EditSupport.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(EditSupport.this, EditSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(EditSupport.this, TM2.class);
            startActivity(intent);

            //////////////////////////////// PHM - SUb ////////////////////////////////////////////

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(EditSupport.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addBusBar) {
            Intent intent = new Intent(EditSupport.this, AddBusBar.class);
            startActivity(intent);

        } else if (id == R.id.nav_addStructual) {
            Intent intent = new Intent(EditSupport.this, AddStructural.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantryMore) {
            Intent intent = new Intent(EditSupport.this, AddGantryMore.class);
            startActivity(intent);

        } else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(EditSupport.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSwitches) {
            Intent intent = new Intent(EditSupport.this, AddSwitches.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSurge) {
            Intent intent = new Intent(EditSupport.this, AddSurgeArrestors.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTransformersG) {
            Intent intent = new Intent(EditSupport.this, AddTransformersG.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(EditSupport.this, AddEquipment.class);
            startActivity(intent);

///////////////////// POLE DETAILS //////////////////////////////////////////////

        } else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(EditSupport.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(EditSupport.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(EditSupport.this, AddTransformers.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(EditSupport.this, Login.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void statusCheck() {
        System.out.println("??????????????????????????????????????????????????????????????????????");
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else {

            GPStracker g = new GPStracker(getApplicationContext());
            Location l = g.getLocation();

            if (l != null) {
                lat = l.getLatitude();
                lon = l.getLongitude();

                a = findViewById(R.id.etLongitude);
                b = findViewById(R.id.etLatitude);

//                String a = String.valueOf(lat);
//                String b = String.valueOf(lon);
                DecimalFormat df2 = new DecimalFormat(".##########");

                a.setText(df2.format(lon));
                b.setText(df2.format(lat));

                System.out.println("................................" + lat);
                System.out.println("................................" + lon);

            }
        }
    }

    private class AddSupportDB extends AsyncTask<Void, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        protected String[] doInBackground(Void... urls) {
            TowerNo = findViewById(R.id.etTowerNo);
            String towerno = TowerNo.getText().toString();
            towerno = towerno.replace("/", "-");
            System.out.println("AddSupportDB");

            //area
            SpinnerArea = findViewById(R.id.spinnerArea);
            String supportArea = SpinnerArea.getSelectedItem().toString();
            // String idarea = spinnerMap.get(supportArea);

            SpinnerLine = findViewById(R.id.spinnerLineName);
            String supportLine = SpinnerLine.getSelectedItem().toString();
            //String idline = spinnerMap1.get(supportLine);


            etArea = findViewById(R.id.etArea);
            String etArea1 = etArea.getText().toString();

            System.out.println("createExcel3");
            //csc
            CSC = findViewById(R.id.spinnerCSC);
            String suportCSC = CSC.getSelectedItem().toString();

            etCSC = findViewById(R.id.EtCSC);
            String EtCSC1 = etCSC.getText().toString();

            System.out.println("createExcel4");
            //Conductor Type
            SpinnerConductorType = findViewById(R.id.spinnerConType);
            String conductorType = SpinnerConductorType.getSelectedItem().toString();

            etConType = findViewById(R.id.etConType);
            String contypeEt = etConType.getText().toString();

            System.out.println("createExcel5");

            //Earth Conductor Type
            EarthConductorType = findViewById(R.id.spinnerConType);
            final String esrthconductorType = EarthConductorType.getSelectedItem().toString();
            System.out.println("createExcel6");

            //support type
            SupportTypeGroup = findViewById(R.id.radioGroupSupportType);
            final int supporttype = SupportTypeGroup.getCheckedRadioButtonId();

            SupportTypeButton = findViewById(supporttype);
            String selectedSupportTypeRadio = SupportTypeButton.getText().toString();
            System.out.println("createExcel7");

            //Tower type
            spinnerTowerType = findViewById(R.id.spinnerTowerType);
            String towerType = spinnerTowerType.getSelectedItem().toString();

            etTowerType = findViewById(R.id.etTowerType);
            String TowerTypeET = etTowerType.getText().toString();


            System.out.println("createExcel8");

            //Circuit type
            NumberOfCircuitsradioGroup = findViewById(R.id.NumberOfCircuitsradioGroup);
            final int noofcircuits = NumberOfCircuitsradioGroup.getCheckedRadioButtonId();
            System.out.println("NumberOfCircuitsradioGroup" + NumberOfCircuitsradioGroup);
            NoOfCircuitsButton = findViewById(noofcircuits);
            String selectedNoOfCircuitsRadio = NoOfCircuitsButton.getText().toString();
            System.out.println("NoOfCircuitsButton" + NoOfCircuitsButton);
            System.out.println("createExcel9");


            //Tower Config
            spinnerTowerConfig = findViewById(R.id.spinnerTowerConfig);
            final String towerConfig = spinnerTowerConfig.getSelectedItem().toString();
            System.out.println("createExcel0");
            //Body Extension
            BodyExtensionRadioGroup = findViewById(R.id.BodyExtensionradioGroup);
            final int bodyextension = BodyExtensionRadioGroup.getCheckedRadioButtonId();
            BodyExtensionButton = findViewById(bodyextension);
            final String selectedBodyExtensionRadio = BodyExtensionButton.getText().toString();
            System.out.println("createExcel11");
            //longitude
            GPSLongititude = findViewById(R.id.etLongitude);
            final String gpsLongititude = GPSLongititude.getText().toString();
            System.out.println("createExcel2");
            //latitude
            GPSLatitude = findViewById(R.id.etLatitude);
            final String gpsLatitude = GPSLatitude.getText().toString();
            System.out.println("createExcel3");
            //Line name
            SupportLineName = findViewById(R.id.etLineName);
            final String lineName = SupportLineName.getText().toString();
            System.out.println("createExcel4");
            if (conductorType.equals("Other")) {

                conductorType = contypeEt;

            }

            if (towerType.equals("Other")) {

                towerType = TowerTypeET;

            }
            if (suportCSC.equals("Other")) {

                suportCSC = EtCSC1;

            }
            if (supportArea.equals("Other")) {
                supportArea = etArea1;
            }


            /*SessionManager obj = new SessionManager(getBaseContext());
            String PhmBranch = obj.getPhmBranch();
            System.out.println("PhmBranch" + PhmBranch.trim());*/
            System.out.println("selectedSupportTypeRadio" + selectedSupportTypeRadio + "test");
            selectedSupportTypeRadio = selectedSupportTypeRadio.trim();
            String suType = "";
            if (selectedSupportTypeRadio.equalsIgnoreCase("Tower")) {
                suType = "1";
            } else if (selectedSupportTypeRadio.equalsIgnoreCase("Pole")) {
                suType = "2";
            } else if (selectedSupportTypeRadio.equalsIgnoreCase("Gantry Bay")) {
                suType = "3";
            } else if (selectedSupportTypeRadio.equalsIgnoreCase("GSS Bay")) {
                suType = "4";
            }

            System.out.println("selectedSupportTypeRadio" + suType);

            String suCircuits = "";
            if (selectedNoOfCircuitsRadio.equalsIgnoreCase("Single")) {
                suCircuits = "1";
            } else if (selectedNoOfCircuitsRadio.equalsIgnoreCase("Double")) {
                suCircuits = "2";
            }
        else if (selectedNoOfCircuitsRadio.equalsIgnoreCase("Four")) {
            suCircuits = "4";
        }else {
                suCircuits = "3";
            }
            System.out.println("selectedNoOfCircuitsRadio" + suCircuits);



            // String url4 = Util.SRT_URL+"addSupportDB/"+ area +"/"+ towerno + "/"  + line+"/"+  conductorType+ "/" + selectedSupportTypeRadio+ "/" + towerType + "/"+ towerConfig + "/"
            //     +selectedBodyExtensionRadio +"/" +
            //     "000000" + "/" +"0000000"+ "/";

//            MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
//            try {
//                StringBuilder stringBuilder = new StringBuilder(URLEncoder.encode(mTempPhotoPath, "UTF-8"));
//                params.set("content", stringBuilder.toString());
//            }
//            catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }

            System.out.println("*************************************************************Starting convert image");
            convertImage();
            System.out.println("*************************************************************Ending convert image");

            //get PhmBranch
            SessionManager obj = new SessionManager(getBaseContext());
            String PhmBranch = obj.getPhmBranch();
            String userName = obj.getUserName();
            System.out.println("PhmBranch" + PhmBranch.trim());
            PhmBranch = PhmBranch.trim();

            //set values to MmsAddsupport object
            MmsAddsupport objAddsupport = new MmsAddsupport();
            objAddsupport.setArea(area);
            objAddsupport.setTowerNo(towerno);
            objAddsupport.setBodyExtension(selectedBodyExtensionRadio);
            objAddsupport.setConductorType(new BigDecimal(ConType));
            //objAddsupport.setPhmBranch(deptId);
            objAddsupport.setEarthConductorType(new BigDecimal(1));
            objAddsupport.setGpsLatitude(new BigDecimal(gpsLatitude));
            objAddsupport.setGpsLongitude(new BigDecimal(gpsLongititude));
            objAddsupport.setLineId(new BigDecimal(line));
            objAddsupport.setSupportType(new BigDecimal(suType));
            objAddsupport.setTowerConfiguration(new BigDecimal(TowerConfig));
            //objAddsupport.setTowerNo(towerno);
            objAddsupport.setTowerType(new BigDecimal(TowerType));
            objAddsupport.setNoOfCircuits(new BigDecimal(suCircuits));
            objAddsupport.setPhmBranch(PhmBranch);
            objAddsupport.setStatus(new BigDecimal(2));
            if (ba != null) {
                objAddsupport.setFilepath(ba.toString());
            }
            objAddsupport.setEntBy(userName);



            System.out.println(":****************************** " + objAddsupport.getArea());
            System.out.println("selectedBodyExtensionRadio:****************************** " + objAddsupport.getBodyExtension());
            System.out.println("ConType:****************************** " + objAddsupport.getConductorType());
            System.out.println("EarthConductorType:****************************** " + objAddsupport.getEarthConductorType());
            System.out.println("gpsLatitude:****************************** " + objAddsupport.getGpsLatitude());
            System.out.println("gpsLongititude:****************************** " + objAddsupport.getGpsLongitude());
            System.out.println("lineId:****************************** " + objAddsupport.getLineId());
            System.out.println("suType:****************************** " + objAddsupport.getSupportType());
            System.out.println("TowerConfig:****************************** " + objAddsupport.getTowerConfiguration());
            System.out.println("towerno:****************************** " + objAddsupport.getTowerNo());
            System.out.println("TowerType:****************************** " + objAddsupport.getTowerType());
            System.out.println("NoOfCircuits:****************************** " + objAddsupport.getNoOfCircuits());
            System.out.println("Filepath:****************************** " + objAddsupport.getFilepath());
            System.out.println("Filepath:****************************** " + objAddsupport.getFilepath());
            System.out.println("PhmBranch:****************************** " + objAddsupport.getPhmBranch());
            System.out.println("Status:****************************** " + objAddsupport.getStatus());
            System.out.println("Ent By:****************************** " + objAddsupport.getEntBy());


            final RestTemplate restTemplate = new RestTemplate();
            final String url1 = Util.SRT_URL + "MMSAddSupportMobile";
            System.out.println(" url1 " + url1);
            System.out.println(" ...........................url1tttttttttttttt " );
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println(" url1ttttttttttttttgggggg " );
            String objReturn = restTemplate.postForObject(url1, objAddsupport, String.class);
            System.out.println("objReturn: " + objReturn);
            return null;


            /*RestTemplate rest = new RestTemplate();
            try {
                String url4 = Util.SRT_URL + "addSupportDBNew/" + area + "/" + selectedBodyExtensionRadio + "/" + ConType + "/" + "000000" + "/" +
                        "1" + "/" + gpsLongititude + "/" + gpsLatitude + "/" + line + "/" + suType + "/" +
                        TowerConfig + "/" + towerno + "/" + TowerType + "/" + suCircuits + "/" + PhmBranch + "/" + ba + "/";


                //System.out.println("#############################ba1: " + ba1);

                System.out.println("ssss" + url4);
                //System.out.println("****************************ba1 length: " + ba1.length());
                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                // System.out.println("hiii5555 str" + rest.getForObject(url4, String.class));
                Looper.myLooper();
                String massage = rest.getForObject(url4, String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
*/
        }


        //  protected void onPostExecute(String[] results) {
        // ListView Item Click Listener
        //    System.out.println("results" +results);
        //  ProgDialog.dismiss();
        //  Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        //}
    }

    private class loadLine extends AsyncTask<String, Void, MmsAddline[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        protected MmsAddline[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findAllLine";

            System.out.println("ssss" + url5);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5, MmsAddline[].class);
            //return List<MmsAddline>null;
        }


        protected void onPostExecute(MmsAddline[] results) {
            // ListView Item Click Listener
            System.out.println("results" + results);
            System.out.println("results" + results.length);
            String[] lineName;
            valuesLine = new String[results.length];

            System.out.println("resultsyyyyyyy");
            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddline obj = results[c];
                    System.out.println("resultsyyyyyyyw :" + c);
                    if (obj != null) {
                        valuesLine[c] = obj.getLineName();
                        spinnerMap5.put(c, obj.getId());

                    }

                }
                System.out.println("resultsyyy:");
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesLine);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerLine = findViewById(R.id.spinnerLineName);
            SpinnerLine.setAdapter(adapterNs);
            System.out.println("resultsyyy10:");

//


            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }

    private class loadLineByArea extends AsyncTask<String, Void, MmsAddline[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        protected MmsAddline[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findLineByArea/" + area + "/";

            System.out.println("ssss" + url5);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5, MmsAddline[].class);
            //return List<MmsAddline>null;
        }


        protected void onPostExecute(MmsAddline[] results) {
            // ListView Item Click Listener
            System.out.println("results" + results);
            System.out.println("results" + results.length);
            String[] lineName;
            valuesLine = new String[results.length];


            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddline obj = results[c];
                    valuesLine[c] = obj.getLineName();
                    spinnerMap5.put(c, obj.getId());

                }
            }


            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesLine);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerLine = findViewById(R.id.spinnerLineName);
            SpinnerLine.setAdapter(adapterNs);
//


            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }


    public List<MmsAddsupport> readSupport() {
        System.out.println("readExcel#");
        List<MmsAddsupport> obj = null;
        // Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
        List<String> resultSet = new ArrayList<String>();
        // Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
        String result = "";

        System.out.println("readExcel1#");
        File inputWorkbook = new File(Environment.getExternalStorageDirectory() + File.separator + "Support" + File.separator + "Support.xls");
        System.out.println("readExcel2#");
        //  Toast.makeText(getApplication(),"readExcel2: " , Toast.LENGTH_SHORT).show();

        if (inputWorkbook.exists()) {
            System.out.println("readExcel3#");
            //    Toast.makeText(getApplication(),"readExcel3: " , Toast.LENGTH_SHORT).show();

            Workbook w;
            try {
                //          System.out.println("readExcel4");

                w = Workbook.getWorkbook(inputWorkbook);
                System.out.println("readExcel4");
                //        Toast.makeText(getApplication(),"readExcel4: " , Toast.LENGTH_SHORT).show();

                // Get the first sheet
                Sheet sheet = w.getSheet(0);
                System.out.println("readExcel5");

                // Loop over column and lines
                int coloumn = sheet.getRows();
                //values = new String[coloumn];
                obj = new ArrayList<MmsAddsupport>(coloumn);


                for (int j = 1; j < sheet.getRows(); j++) {
                    MmsAddsupport objSu = new MmsAddsupport();
                    Cell cell = sheet.getCell(0, j);
                    System.out.println("Support type: " + cell.getContents());
                    //Toast.makeText(getApplication(), "Support type: " + cell.getContents(), Toast.LENGTH_SHORT).show();
                    String suType = cell.getContents();
                    objSu.setSupportType(new BigDecimal(suType));

                    /*Cell cell1 = sheet.getCell(1, j);
                    System.out.println("Line name" + cell1.getContents());
                    //Toast.makeText(getApplication(), "LineName: " + cell1.getContents(), Toast.LENGTH_SHORT).show();
                    String LineName = cell1.getContents();
                    objSu.setLineName(LineName);

                    Cell cell2 = sheet.getCell(2, j);
                    System.out.println("ID: " + cell2.getContents());
                    //Toast.makeText(getApplication(), "ID: " + cell2.getContents(), Toast.LENGTH_SHORT).show();
                    String ID = cell2.getContents();
                    objSu.setId(new Long(ID));

                    Cell cell3 = sheet.getCell(3, j);
                    System.out.println("PhmBranch" + cell3.getContents());
                    //Toast.makeText(getApplication(), "PhmBranch: " + cell3.getContents(), Toast.LENGTH_SHORT).show();
                    //String PhmBranch =cell3.getContents();
                    //objSu.setPhmBranch(PhmBranch);
                    SessionManager objS = new SessionManager(getBaseContext());
                    String PhmBranch = objS.getPhmBranch();
                    System.out.println("PhmBranch" + PhmBranch.trim());
                    PhmBranch = PhmBranch.trim();
                    objSu.setPhmBranch(PhmBranch);*/

                    Cell cell1 = sheet.getCell(1, j);
                    System.out.println("Tower no: " + cell1.getContents());
                    //Toast.makeText(getApplication(), "towerno: " + cell4.getContents(), Toast.LENGTH_SHORT).show();
                    String TowerNo = cell1.getContents();
                    objSu.setTowerNo(TowerNo);

                    Cell cell2 = sheet.getCell(2, j);
                    System.out.println("Area: " +cell2.getContents());
                    //Toast.makeText(getApplication(), "area: " + cell5.getContents(), Toast.LENGTH_SHORT).show();
                    String area = cell2.getContents();
                    objSu.setArea(area);

                    Cell cell3 = sheet.getCell(3, j);
                    System.out.println("Conduct type: " + cell3.getContents());
                    //Toast.makeText(getApplication(), "ConType: " + cell6.getContents(), Toast.LENGTH_SHORT).show();
                    String ConType = cell3.getContents();
                    objSu.setConductorType(new BigDecimal(ConType));

                    Cell cell4 = sheet.getCell(4, j);
                    System.out.println("Earth Conduct type: " + cell4.getContents());
                    //Toast.makeText(getApplication(), "ConType: " + cell6.getContents(), Toast.LENGTH_SHORT).show();
                    String EarthConType = cell4.getContents();
                    objSu.setEarthConductorType(new BigDecimal(EarthConType));

                    Cell cell5 = sheet.getCell(5, j);
                    System.out.println("Tower type: " + cell5.getContents());
                    //Toast.makeText(getApplication(), "TowerType: " + cell7.getContents(), Toast.LENGTH_SHORT).show();
                    String TowerType = cell5.getContents();
                    objSu.setTowerType(new BigDecimal(TowerType));

                    Cell cell6 = sheet.getCell(6, j);
                    System.out.println("Tower config: " + cell6.getContents());
                    //Toast.makeText(getApplication(), "TowerConfig: " + cell8.getContents(), Toast.LENGTH_SHORT).show();
                    String TowerConfig = cell6.getContents();
                    objSu.setTowerConfiguration(new BigDecimal(TowerConfig));

                    Cell cell7 = sheet.getCell(7, j);
                    System.out.println("Latitude: " + cell7.getContents());
                    //Toast.makeText(getApplication(), "gpsLatitude: " + cell9.getContents(), Toast.LENGTH_SHORT).show();
                    String gpsLatitude = cell7.getContents();
                    objSu.setGpsLatitude(new BigDecimal(gpsLatitude));

                    Cell cell8 = sheet.getCell(8, j);
                    System.out.println("Longitude: " + cell8.getContents());
                    //Toast.makeText(getApplication(), "gpsLongititude: " + cell10.getContents(), Toast.LENGTH_SHORT).show();
                    String gpsLongititude = cell8.getContents();
                    objSu.setGpsLongitude(new BigDecimal(gpsLongititude));

                    Cell cell9 = sheet.getCell(9, j);
                    System.out.println("Circuit type: " + cell9.getContents());
                    //Toast.makeText(getApplication(), "suCircuits: " + cell11.getContents(), Toast.LENGTH_SHORT).show();
                    String suCircuits = cell9.getContents();
                    objSu.setNoOfCircuits(new BigDecimal(suCircuits));

                    Cell cell10 = sheet.getCell(10, j);
                    System.out.println("Body extension: " + cell10.getContents());
                    //Toast.makeText(getApplication(), "selectedBodyExtensionRadio: " + cell12.getContents(), Toast.LENGTH_SHORT).show();
                    String selectedBodyExtensionRadio = cell10.getContents();
                    objSu.setBodyExtension(selectedBodyExtensionRadio);

                    Cell cell11 = sheet.getCell(11, j);
                    System.out.println("Line id: " + cell11.getContents());
                    String lineId = cell11.getContents();
                    objSu.setLineId(new BigDecimal(lineId));
                    //Toast.makeText(getApplication(), "lineId: " + cell13.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell12 = sheet.getCell(12, j);
                    System.out.println("Tapping: " + cell12.getContents());
                    String tapping = cell12.getContents();
                    objSu.setTapping(new BigDecimal(tapping));
                    //Toast.makeText(getApplication(), "lineId: " + cell13.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell13 = sheet.getCell(13, j);
                    System.out.println("Map id: " + cell13.getContents());
                    String mapId = cell13.getContents();
                    objSu.setMapId(new BigDecimal(mapId));
                    //Toast.makeText(getApplication(), "lineId: " + cell13.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell14 = sheet.getCell(14, j);
                    System.out.println("CSC: " + cell14.getContents());
                    String csc = cell14.getContents();
                    objSu.setCsc(csc);
                    //Toast.makeText(getApplication(), "lineId: " + cell13.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell15 = sheet.getCell(15, j);
                    System.out.println("phm branch: " + cell15.getContents());
                    String phmBranch = cell15.getContents();
                    objSu.setPhmBranch(phmBranch);
                    //Toast.makeText(getApplication(), "lineId: " + cell13.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell16 = sheet.getCell(16, j);
                    System.out.println("Status: " + cell16.getContents());
                    String status = cell16.getContents();
                    objSu.setStatus(new BigDecimal(2));
                    //Toast.makeText(getApplication(), "lineId: " + cell13.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell17 = sheet.getCell(17, j);
                    System.out.println("ent by: " + cell17.getContents());
                    String entBy = cell17.getContents();
                    objSu.setEntBy(entBy);
                    //Toast.makeText(getApplication(), "lineId: " + cell13.getContents(), Toast.LENGTH_SHORT).show();

                    /*Cell cell14 = sheet.getCell(14, j);
                    System.out.println("Status: " + cell14.getContents());
                    String Status = cell14.getContents();
                    objSu.setStatus(new BigDecimal(Status));
                    //Toast.makeText(getApplication(), "Status: " + cell14.getContents(), Toast.LENGTH_SHORT).show();*/


                  /* final RestTemplate restTemplate = new RestTemplate();
                    String url4 = Util.SRT_URL + "addSupportDB/" +objSu.getArea() + "/"+ objSu.getBodyExtension() + "/" + objSu.getConductorType() + "/" + objSu.getPhmBranch() + "/" +
                            "1"+"/"+ objSu.getGpsLongitude() + "/" + objSu.getGpsLatitude() + "/"+objSu.getLineId() + "/"+ objSu.getSupportType() +"/" +
                            objSu.getTowerConfiguration() +"/"+objSu.getTowerNo() + "/" + objSu.getTowerType() + "/"+ objSu.getNoOfCircuits()+"/" + objSu.getPhmBranch()+"/" ;


                    Toast.makeText(getApplication(),"url4: " + url4, Toast.LENGTH_SHORT).show();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    Toast.makeText(getApplication(),"url5: " + url4, Toast.LENGTH_SHORT).show();
                    result= restTemplate.getForObject(url4, String.class);
                   // System.out.println("hiii5555 yyyyyyyyyhhjj" + result);
                    Toast.makeText(getApplication(),"url6: " + url4, Toast.LENGTH_SHORT).show();*/

                    obj.add(objSu);




                   /* final RestTemplate restTemplate = new RestTemplate();
//            System.out.println(" areaCode " + areaCode);
                    final String url1 = Util.SRT_URL + "addLineDB/" + suType +"/"+ towerno +"/" + area +"/" + suportCSC +"/"+ ConType +"/"+ esrthconductorType +"/"+ TowerType  +"/"+TowerConfig+"/"+gpsLatitude+"/"+gpsLongititude+"/"+line+"/";
                    System.out.println(" url1 " + url1);
                    Toast.makeText(getApplication(),"url1: " + url1, Toast.LENGTH_SHORT).show();
                    // trustEveryone();
                    System.out.println(" ...........................url1tttttttttttttt " );
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    Toast.makeText(getApplication(),"url1: " + url1, Toast.LENGTH_SHORT).show();
                    System.out.println(" url1ttttttttttttttgggggg " );
                    //return restTemplate.getForObject(url1, String.class);
                    //return null;*/


                    // values[j] = cell1.getContents();

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

/*
                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerArea = (Spinner) findViewById(R.id.spinnerArea);
                SpinnerArea.setAdapter(adapterNs);
*/

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
        return obj;
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
            System.out.println("PhmBranch" + deptId.trim());
            deptId = deptId.trim();


            RestTemplate rest = new RestTemplate();
            //String url6 = Util.SRT_URL+"findAllAreaNew";
            String url6 = Util.SRT_URL + "findAllAreaNewMobile/" + deptId + "/";

            System.out.println("ssss" + url6);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url6, MmsAddArea[].class);
            //return List<MmsAddArea>null;
        }


        protected void onPostExecute(MmsAddArea[] results) {
            // ListView Item Click Listener
            System.out.println("results" + results);
            System.out.println("results" + results.length);
            String[] area;
            values = new String[results.length];
            //  String[] spinnerArray = new String[Province_ID.size()];
            //  HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
            //  for (int i = 0; i < Province_ID.size(); i++)
            //  {
            //      spinnerMap.put(i,Province_ID.get(i));
            //      spinnerArray[i] = Province_NAME.get(i);
            //  }



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
            SpinnerArea = findViewById(R.id.spinnerArea);
            SpinnerArea.setAdapter(adapterNs);


            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }


    public void progressSaving() {
        //Show the dialog
        final ProgressDialog dialog = ProgressDialog.show(this, "Saving Data", "Please wait....", true);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    dialog.dismiss();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    protected void ProgressBar2() {
        //super.onPreExecute();


        ProgDialog = new ProgressDialog(EditSupport.this);

//message should be changed according to the requirement
        ProgDialog.setMessage("Saving Data \n(This may take some time,Please wait....)");
        ProgDialog.setIndeterminate(false);
        ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        ProgDialog.setCancelable(true);
        ProgDialog.show();
        ProgDialog.dismiss();

    }

    private class loadConType extends AsyncTask<String, Void, MmsAddconductortype[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        protected MmsAddconductortype[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url8 = Util.SRT_URL + "findActiveConductorTypes";

            System.out.println("ssss" + url8);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url8, MmsAddconductortype[].class);
            //    return List<MmsAddArea>null;
        }


        protected void onPostExecute(MmsAddconductortype[] results) {
            // ListView Item Click Listener
            System.out.println("results" + results);
            System.out.println("results" + results.length);
            String[] ConType;
            valuesConType = new String[results.length];
            //String[] spinnerArray = new String[Province_ID.size()];
            //HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
            //for (int i = 0; i < Province_ID.size(); i++)
            //{
            //   spinnerMap.put(i,Province_ID.get(i));
            // spinnerArray[i] = Province_NAME.get(i);
            //}


//
            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddconductortype obj = results[c];
                    System.out.println("resultsyyyyyyywer :" + c);
                    if (obj != null) {
                        valuesConType[c] = String.valueOf(obj.getType());
                        spinnerMap2.put(c, obj.getId());

                        System.out.println("resultsyyyyyyywergh ");
                    }
                }
                System.out.println("resultsyyyyyyywerh ");
            }


            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesConType);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerConductorType = findViewById(R.id.spinnerConType);
            SpinnerConductorType.setAdapter(adapterNs);


            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_support, menu);
        mymenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_refresh){
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView iv = (ImageView)inflater.inflate(R.layout.iv_refresh, null);
            Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_refresh);
            rotation.setRepeatCount(Animation.INFINITE);
            iv.startAnimation(rotation);
            item.setActionView(iv);
            new UpdateTask(this).execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void resetUpdating()
    {
        // Get our refresh item from the menu
        MenuItem m = mymenu.findItem(R.id.action_refresh);
        if(m.getActionView()!=null)
        {
            // Remove the animation.
            m.getActionView().clearAnimation();
            m.setActionView(null);


        }
    }
    public class UpdateTask extends AsyncTask<Void, Void, Void> {

        private Context mCon;

        public UpdateTask(Context con)
        {
            mCon = con;
        }

        @Override
        protected Void doInBackground(Void... nope) {
            try {
                // Set a time to simulate a long update process.
                Thread.sleep(4000);
                return null;
            } catch (Exception e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(Void nope) {
            // Give some feedback on the UI.
            statusCheck();


            // Change the menu back
            ((EditSupport) mCon).resetUpdating();
        }
    }

    private class loadTowerType extends AsyncTask<String, Void, MmsTowertype[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        protected MmsTowertype[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url9 = Util.SRT_URL + "findAllTowerType";

            System.out.println("ssss" + url9);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url9, MmsTowertype[].class);
            //    return List<MmsAddArea>null;
        }


        protected void onPostExecute(MmsTowertype[] results) {
            // ListView Item Click Listener
            System.out.println("resultsTowerType" + results);
            System.out.println("resultsTowerType" + results.length);
            String[] TowerType;
            valuesTowerType = new String[results.length];
            //String[] spinnerArray = new String[Province_ID.size()];
            //HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
            //for (int i = 0; i < Province_ID.size(); i++)
            //{
            //   spinnerMap.put(i,Province_ID.get(i));
            // spinnerArray[i] = Province_NAME.get(i);
            //}


//
            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsTowertype obj = results[c];
                    System.out.println("resultsyyyyyyywertyu :" + c);
                    if (obj != null) {
                        valuesTowerType[c] = obj.getName();
                        spinnerMap3.put(c, String.valueOf(obj.getId()));
                        System.out.println("resultsyyyyyyywergh ");
                    }
                }
                System.out.println("resultsyyyyyyywerh ");
            }


            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesTowerType);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerTowerType = findViewById(R.id.spinnerTowerType);
            SpinnerTowerType.setAdapter(adapterNs);


            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }

    private class loadTowerConfig extends AsyncTask<String, Void, MmsAddtowerconfiguration[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        protected MmsAddtowerconfiguration[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url10 = Util.SRT_URL + "findActiveTowerConfig";

            System.out.println("ssss" + url10);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url10, MmsAddtowerconfiguration[].class);
            //    return List<MmsAddArea>null;
        }


        protected void onPostExecute(MmsAddtowerconfiguration[] results) {
            // ListView Item Click Listener
            System.out.println("resultsTowerType" + results);
            System.out.println("resultsTowerType" + results.length);
            String[] TowerConfig;
            valuesTowerConfig = new String[results.length];
            //String[] spinnerArray = new String[Province_ID.size()];
            //HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
            //for (int i = 0; i < Province_ID.size(); i++)
            //{
            //   spinnerMap.put(i,Province_ID.get(i));
            // spinnerArray[i] = Province_NAME.get(i);
            //}


//
            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddtowerconfiguration obj = results[c];
                    System.out.println("resultsyyyyyyywertyu :" + c);
                    if (obj != null) {
                        valuesTowerConfig[c] = obj.getName();
                        spinnerMap4.put(c, String.valueOf(obj.getId()));
                        System.out.println("resultsyyyyyyywergh ");
                    }
                }
                System.out.println("resultsyyyyyyywerh ");
            }


            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesTowerConfig);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerTowerConfiguration = findViewById(R.id.spinnerTowerConfig);
            SpinnerTowerConfiguration.setAdapter(adapterNs);


            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }

    private void buildAlertMessageNoGps() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent = new Intent(EditSupport.this, MainActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }


    /**Convert image to Base64*/
    private byte[] convertImage() {

        if (mTempPhotoPath != null) {
//            Bitmap bitmap = BitmapFactory.decodeFile(mTempPhotoPath);
//            BitmapUtils.saveImage(this, bitmap);
//            savedImagePath = BitmapUtils.saveImage(this, bitmap);
//            System.out.println("savedImagePath: " + savedImagePath);
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//            ba = byteArrayOutputStream.toByteArray();
//            System.out.println("*********************************************byte[]: " + ba);
//            ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
//            ba1 = Base64.encodeBase64URLSafeString(ba, Base64.DEFAULT);
            //ba1 = ba.toString();
            //ba1 = Base64.encodeToString(ba, 1);
//        String ba2 = String.valueOf(Base64.decode(ba1.toString(), 1));

            Bitmap bitmap = BitmapFactory.decodeFile(mTempPhotoPath);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            ba = bos.toByteArray();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + ba.length);
            //System.out.println();


            //Convert image to Base64
            /*Bitmap bitmap = BitmapFactory.decodeFile(mTempPhotoPath);
            BitmapUtils.saveImage(this, bitmap);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] ba = byteArrayOutputStream.toByteArray();
            ba1 = Base64.encodeToString(ba, Base64.NO_WRAP);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + ba1.length());
            System.out.println(ba1);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            File root = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            + "/MVMMS");
            //File root = new File(DIRECTORY_PATH);
            File gpxfile = new File(root, "Base64Code_" + timeStamp + ".txt");
            FileWriter writer = null;
            try {
                writer = new FileWriter(gpxfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.append(ba1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //byte[] data = Base64.decode(ba1, Base64.DEFAULT);
            //aBlob b = new SerialBlob(data);
            //Blob blob = new javax.sql.rowset.serial.SerialBlob(ba1);
            //bitmap.recycle();*/

        }
        return ba;


//        if (mTempPhotoPath != null) {
//            FileInputStream imageInFile = new FileInputStream(mTempPhotoPath);
//            byte imageData[] = new byte[(int) mTempPhotoPath.length()];
//            imageInFile.read(imageData);
//            Base64.encodeBase64URLSafeString(imageData);
//        }



//        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
//        map.add("expenseId", "1");
//        map.add("file", new FileSystemResource(mTempPhotoPath));



    }



    /**Requesting the permission for camera*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Called when you request permission to read and write to external storage
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If you get permission, launch the camera
                    launchCamera();
                } else {
                    // If you do not get permission, show a Toast
                    Toast.makeText(this, "permission_denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the image capture activity was called and was successful
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Process the image and set it to the ImageView
            processAndSetImage();
        } else {

            // Otherwise, delete the temporary image file
            BitmapUtils.deleteImageFile(this, mTempPhotoPath);
        }
    }

    /**Creates a temporary image file and captures a picture to store in it*/
    private void launchCamera() {

        // Create the capture image intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the temporary File where the photo should go
            File photoFile = null;
            try {
                photoFile = BitmapUtils.createTempImageFile(this);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the path of the temporary file
                mTempPhotoPath = photoFile.getAbsolutePath();

                // Get the content URI for the image file
                Uri photoURI = FileProvider.getUriForFile(this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile);

                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Launch the camera activity
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    /**Method for processing the captured image and setting it to the ImageView*/
    private void processAndSetImage() {

        // Toggle Visibility of the views
        bUploadImage.setVisibility(View.VISIBLE);
        imageToUpload.setVisibility(View.VISIBLE);


        // Resample the saved image to fit the ImageView
        mResultsBitmap = BitmapUtils.resamplePic(this, mTempPhotoPath);

//        // Set the new bitmap to the ImageView
        imageToUpload.setImageBitmap(mResultsBitmap);

//        imageToUpload.setImageBitmap(myBitmap);

//        if(mTempPhotoPath .length() > 0) {
//            final File image = new File(mTempPhotoPath);
//            if (image != null) {
//                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), image);
//                MultipartBody.Part body = MultipartBody.Part.createFormData("image", image.getName(), requestBody);
//                Call<JSONObject> call = api.uploadImage(body);
//            }
//        }

//        //Convert image to Base64
//        Bitmap bitmap = BitmapFactory.decodeFile(mTempPhotoPath);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
//        byte[] ba = byteArrayOutputStream.toByteArray();
//        ba1 = Base64.encodeToString(ba, Base64.NO_WRAP);
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + ba1.length());
//
////        String[] filePathColumn = {MediaStore.Images.Media.DATA};
////        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
////        cursor.close();
//
////        Bitmap photo = (Bitmap) data.getExtras().get("data");
//        File sourceFile = new File(mTempPhotoPath);
    }

}
