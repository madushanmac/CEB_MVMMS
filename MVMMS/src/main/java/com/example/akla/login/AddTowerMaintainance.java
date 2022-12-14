package com.example.akla.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

public class AddTowerMaintainance extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener  {

    private ProgressDialog progress;
    Spinner SpinnerLeavingStatus;
    Spinner SpinnerBaseConcreteStatus;
    Spinner SpinnerAntiClimbingStatus;
    Spinner SpinnerConductorCondition;
    Spinner SpinnerEarthConductorCondition;
    Spinner SpinnerJumperCondition;
    Spinner SpinnerMaintainanceAttention;
    Spinner SpinnerSpObservaion;
    Spinner SpinnerLegPainting;
    Spinner SpinnerHotLineMaintenance;
    Spinner SpinnerTowerNo;
    Spinner SpinnerTowerID;
    Spinner SpinnerFungusSet;
    //Spinner etTypeOFSwichingDevice1;
    Spinner SpinnerTypeOFSwichingDevice1;
    Spinner SpinnerTypeOFSwichingDevice2;
    Spinner SpinnerTypeOFSwichingDevice3;
    Spinner SpinnerFlashOverSets;
    Spinner SpinnerWPinSet;
    Spinner SpinnerEndFittingSet;

    EditText VisitID;
    EditText TowerID;
    EditText NumberOfTappings;
    Spinner WayLeavingStatus;
    EditText NumberOfMissingParts;
    Spinner NumberOfFlashOverSets;
    Spinner FlashOverSets;
    EditText FlashOverSetNumber;
    EditText SpecialObservations;
    EditText ConductorCondition;
    EditText JumperCondition1;
    EditText EarthConductorCondition1;
    //Spinner FungusSet;
   // EditText FungusSet;
    EditText FungusSet;
  //  Spinner WPinSet;
    EditText WPinSet;
    EditText EndFittingSet;
  //  Spinner EndFittingSet;
    EditText NumberOFPinPole1;
    Spinner TypeOfSwichingDevice1;
    EditText TypeOFSwichingDevice1;
    EditText NumberOFPinPole2;
    Spinner TypeOfSwichingDevice2;
    EditText TypeOFSwichingDevice2;
    EditText NumberOFPinPole3;
    Spinner TypeOfSwichingDevice3;
    EditText TypeOFSwichingDevice3;

    String realTowerId;


    private CheckBox GOOD1, COVERED, Danger1, M_DAMAGE, CORRODED, WATER_LODGE, BACKFILLING;

    private CheckBox GOOD, TOUCH, Danger, CLOSE, ARROUND, ALONG;
    EditText etLeavingStatus;

    public String areaCode;
    Button ButtonSend;
    private EditText editText;
    private Button saveButton;
    private Button listButton;
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    private Spinner SpinnerVisitID;
    private  Spinner Spinnerarea;
    DBHelper mydb;
    File file = null;
    WritableWorkbook workbook;
    WritableSheet sheet;


    private Button readButton;
    private final String fileName = "myData.xls";
    String Tower;
    String towerExcel;
    String existingTapping;


    //for open camera and capture a image
    private static final int RESULT_LOAD_IMAGE = 1;
    private EditText uploadImageName;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private static final String FILE_PROVIDER_AUTHORITY = "com.example.akla.login";
    private ImageView imageToUpload;
    private Button bUploadImage;
    private String mTempPhotoPath;
    private Bitmap mResultsBitmap;


    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    // private String values[] = new String[]{};
    String[] values = new String[]{};
    String massage = "";
    //String valuesLine[] = new String[]{};

    HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
    private ProgressDialog ProgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_tower_maintainance);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//confirm dialog


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*// Check for the external storage permission
        if (ContextCompat.checkSelfPermission(AddTowerMaintainance.this.getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // If you do not have permission, request it
            ActivityCompat.requestPermissions(AddTowerMaintainance.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } *//*else {
            // Save data to excel if the permission exists

        }*/

        /**For image upload*/
//        imageToUpload = (ImageView)findViewById(R.id.imageToUpload);
//        bUploadImage = (Button)findViewById(R.id.bUploadImage);
//        uploadImageName = (EditText)findViewById(R.id.etUploadImage);
//        imageToUpload.setVisibility(View.VISIBLE);
//        bUploadImage.setVisibility(View.VISIBLE);
//
//        bUploadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Check for the external storage permission
//                if (ContextCompat.checkSelfPermission(AddTowerMaintainance.this.getApplicationContext(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//
//                    // If you do not have permission, request it
//                    ActivityCompat.requestPermissions(AddTowerMaintainance.this,
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            REQUEST_STORAGE_PERMISSION);
//                } else {
//                    // Launch the camera if the permission exists
//                    AddTowerMaintainance.this.launchCamera();
//                }
//            }
//        });

        if(!Util.isConnected(AddTowerMaintainance.this)){
            readExcelTowerNo();

        }else {
            new AddTowerMaintainance.loadTowerNo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxhhhhh2");

        }
        System.out.println("hhhhh2");

     /*   new AddTowerMaintainance.loadTowerNo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        System.out.println("hhhhh2");*/

        SpinnerLeavingStatus = findViewById(R.id.SpinnerLeavingStatus);
        SpinnerLeavingStatus.setPrompt("Select Way Leaving Status");

        SpinnerBaseConcreteStatus = findViewById(R.id.SpinnerBaseConcreteStatus1);
        SpinnerBaseConcreteStatus.setPrompt("Select Base Concrete Status");
//
        SpinnerAntiClimbingStatus = findViewById(R.id.SpinnerAntiClimbingStatus1);
        SpinnerAntiClimbingStatus.setPrompt("Select Anti Climbing Status");
//
        SpinnerConductorCondition = findViewById(R.id.SpinnerConductorCondition);
        // SpinnerConductorCondition.setPrompt("Select Conductor Condition");
        ConductorCondition = findViewById(R.id.etConductorCondition);
        SpinnerConductorCondition.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if (selectedItem.equals("Other")) {
                    System.out.println("ttttt2:");
                    ConductorCondition.setVisibility(View.VISIBLE);
                    ConductorCondition.setFocusable(true);
                    SpinnerConductorCondition.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });


        SpinnerEarthConductorCondition = findViewById(R.id.SpinnerEarthConductorCondition1);
        //SpinnerEarthConductorCondition.setPrompt("Select Earth Conductor Condition");
        EarthConductorCondition1 = findViewById(R.id.etEarthConductorCondition1);
        SpinnerEarthConductorCondition.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if (selectedItem.equals("Other")) {
                    System.out.println("ttttt1:");
                    EarthConductorCondition1.setVisibility(View.VISIBLE);
                    EarthConductorCondition1.setFocusable(true);
                    SpinnerEarthConductorCondition.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

//
        SpinnerJumperCondition = findViewById(R.id.SpinnerJumperCondition1);
        //SpinnerJumperCondition.setPrompt("Select Jumper Condition");
        JumperCondition1 = findViewById(R.id.etJumperCondition1);
        SpinnerJumperCondition.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if (selectedItem.equals("Other")) {
                    System.out.println("ttttt1:");
                    JumperCondition1.setVisibility(View.VISIBLE);
                    JumperCondition1.setFocusable(true);
                    SpinnerJumperCondition.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

//
        SpinnerMaintainanceAttention = findViewById(R.id.SpinnerMaintainanceAttention1);
        SpinnerMaintainanceAttention.setPrompt("Select Maintainance Attention");


        SpinnerSpObservaion = findViewById(R.id.spinnerSpecialObserve);
        SpecialObservations = findViewById(R.id.etSpecialObserve);
        SpinnerSpObservaion.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if (selectedItem.equals("Other")) {
                    System.out.println("ttttt1:");
                    SpecialObservations.setVisibility(View.VISIBLE);
                    SpecialObservations.setFocusable(true);
                    SpinnerSpObservaion.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

        SpinnerTypeOFSwichingDevice1 = findViewById(R.id.etTypeOFSwichingDevice1);
        TypeOFSwichingDevice1 = findViewById(R.id.TypeOFSwichingDevice1);
        SpinnerTypeOFSwichingDevice1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if (selectedItem.equals("Other")) {
                    System.out.println("ttttt1:");
                    TypeOFSwichingDevice1.setVisibility(View.VISIBLE);
                    TypeOFSwichingDevice1.setFocusable(true);
                    SpinnerTypeOFSwichingDevice1.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });
        SpinnerTypeOFSwichingDevice2 = findViewById(R.id.etTypeOFSwichingDevice2);
        TypeOFSwichingDevice2 = findViewById(R.id.TypeOFSwichingDevice2);
        SpinnerTypeOFSwichingDevice2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if (selectedItem.equals("Other")) {
                    System.out.println("ttttt1:");
                    TypeOFSwichingDevice2.setVisibility(View.VISIBLE);
                    TypeOFSwichingDevice2.setFocusable(true);
                    SpinnerTypeOFSwichingDevice2.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

        SpinnerTypeOFSwichingDevice3 = findViewById(R.id.etTypeOFSwichingDevice3);
        TypeOFSwichingDevice3 = findViewById(R.id.TypeOFSwichingDevice3);
        SpinnerTypeOFSwichingDevice3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if (selectedItem.equals("Other")) {
                    System.out.println("ttttt1:");
                    TypeOFSwichingDevice3.setVisibility(View.VISIBLE);
                    TypeOFSwichingDevice3.setFocusable(true);
                    SpinnerTypeOFSwichingDevice3.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

        NumberOfTappings = findViewById(R.id.etNumberOFTappings);



        SpinnerFlashOverSets = findViewById(R.id.etFlashOverSets);
        FlashOverSetNumber = findViewById(R.id.FlashOverSets);
        SpinnerFlashOverSets.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if (selectedItem.equals("Other")) {
                    System.out.println("ttttt1:");
                    FlashOverSetNumber.setVisibility(View.VISIBLE);
                    FlashOverSetNumber.setFocusable(true);
                    SpinnerFlashOverSets.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });


        SpinnerFungusSet = findViewById(R.id.etFungusSet);
        FungusSet = findViewById(R.id.FungusSet);
        SpinnerFungusSet.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if (selectedItem.equals("Other")) {
                    System.out.println("ttttt1:");
                    FungusSet.setVisibility(View.VISIBLE);
                    FungusSet.setFocusable(true);
                    SpinnerFungusSet.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

        SpinnerWPinSet = findViewById(R.id.etWPinSet);
        WPinSet = findViewById(R.id.WPinSet);
        SpinnerWPinSet.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if (selectedItem.equals("Other")) {
                    System.out.println("ttttt1:");
                    WPinSet.setVisibility(View.VISIBLE);
                    WPinSet.setFocusable(true);
                    SpinnerWPinSet.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });



        SpinnerLeavingStatus.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if (selectedItem.equals("Other")) {
                    SpinnerLeavingStatus.setVisibility(View.VISIBLE);

                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });
        SpinnerTowerNo = findViewById(R.id.spinnerTowerNo);
        SpinnerTowerNo.setPrompt("Select Tower No");


        SpinnerTowerNo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idTower = spinnerMap.get(parent.getSelectedItemPosition());
                Tower = idTower;
                towerExcel = name;
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaidTower: " + Tower);

                new AddTowerMaintainance.loadTapping().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                // Toast.makeText(AddTowerMaintainance.this, "Name" + name, Toast.LENGTH_LONG).show();
                // Toast.makeText(AddTowerMaintainance.this, "idTower" + idTower, Toast.LENGTH_LONG).show();


            /*    if(!Util.isConnected(AddTowerMaintainance.this)){
                    readExcelTowerNo();

                }else {
                    new AddTowerMaintainance.loadTowerNo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    System.out.println("hhhhh2");

                }*/

            }


            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        SpinnerEndFittingSet = findViewById(R.id.etEndFittingSet);
        EndFittingSet = findViewById(R.id.EndFittingSet);
        SpinnerEndFittingSet.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();

                if (selectedItem.equals("Other")) {
                    System.out.println("ttttt1:");
                    EndFittingSet.setVisibility(View.VISIBLE);
                    EndFittingSet.setFocusable(true);
                    SpinnerEndFittingSet.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }


        });


        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TowerID = findViewById(R.id.etTowerID);
                NumberOfTappings = findViewById(R.id.etNumberOFTappings);
                NumberOfMissingParts = findViewById(R.id.etMissingParts);
                NumberOFPinPole1 = findViewById(R.id.etNumberOFPinPole1);
                NumberOFPinPole2 = findViewById(R.id.etNumberOFPinPole2);
                NumberOFPinPole3 = findViewById(R.id.etNumberOFPinPole3);
                SpecialObservations = findViewById(R.id.etSpecialObserve);
                ConductorCondition = findViewById(R.id.etConductorCondition);
                JumperCondition1 = findViewById(R.id.etJumperCondition1);
                EarthConductorCondition1 = findViewById(R.id.etEarthConductorCondition1);
                NumberOfMissingParts = findViewById(R.id.etMissingParts);
                FungusSet = findViewById(R.id.FungusSet);
                FlashOverSetNumber = findViewById(R.id.FlashOverSets);
                TypeOFSwichingDevice1 = findViewById(R.id.TypeOFSwichingDevice1);
                TypeOFSwichingDevice2 = findViewById(R.id.TypeOFSwichingDevice2);
                TypeOFSwichingDevice3 = findViewById(R.id.TypeOFSwichingDevice3);
                WPinSet = findViewById(R.id.WPinSet);
                EndFittingSet = findViewById(R.id.EndFittingSet);
                SpinnerTypeOFSwichingDevice1 = findViewById(R.id.etTypeOFSwichingDevice1);
                SpinnerTypeOFSwichingDevice2 = findViewById(R.id.etTypeOFSwichingDevice2);
                SpinnerTypeOFSwichingDevice3 = findViewById(R.id.etTypeOFSwichingDevice3);


                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.switching_devise, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerTypeOFSwichingDevice1.setAdapter(adapter);
                SpinnerTypeOFSwichingDevice2.setAdapter(adapter);
                SpinnerTypeOFSwichingDevice3.setAdapter(adapter);


                ArrayAdapter<CharSequence> adapterFOS = ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.Flash_over_sets, android.R.layout.simple_spinner_item);
                adapterFOS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerFlashOverSets = findViewById(R.id.etFlashOverSets);
                SpinnerFlashOverSets.setAdapter(adapterFOS);


                ArrayAdapter<CharSequence> adapterStatusAC = ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.Anti_Climbing_Status, android.R.layout.simple_spinner_item);
                adapterStatusAC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerAntiClimbingStatus = findViewById(R.id.SpinnerAntiClimbingStatus1);
                SpinnerAntiClimbingStatus.setAdapter(adapterStatusAC);

                ArrayAdapter<CharSequence> adapterStatusCC = ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.Conductor_Condition, android.R.layout.simple_spinner_item);
                adapterStatusCC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerConductorCondition = findViewById(R.id.SpinnerConductorCondition);
                SpinnerConductorCondition.setAdapter(adapterStatusCC);

                ArrayAdapter<CharSequence> adapterStatusJC = ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.Jumper_Condition, android.R.layout.simple_spinner_item);
                adapterStatusJC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerJumperCondition = findViewById(R.id.SpinnerJumperCondition1);
                SpinnerJumperCondition.setAdapter(adapterStatusJC);

                ArrayAdapter<CharSequence> adapterStatusECC = ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.Earth_Conductor_Condition, android.R.layout.simple_spinner_item);
                adapterStatusECC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerEarthConductorCondition = findViewById(R.id.SpinnerEarthConductorCondition1);
                SpinnerEarthConductorCondition.setAdapter(adapterStatusECC);


                ArrayAdapter<CharSequence> adapterStatusMA = ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.Maintainance_Attention, android.R.layout.simple_spinner_item);
                adapterStatusMA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerMaintainanceAttention = findViewById(R.id.SpinnerMaintainanceAttention1);
                SpinnerMaintainanceAttention.setAdapter(adapterStatusMA);

                ArrayAdapter<CharSequence> adapterStatusFS = ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.Fungus_Set, android.R.layout.simple_spinner_item);
                adapterStatusFS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerFungusSet = findViewById(R.id.etFungusSet);
                SpinnerFungusSet.setAdapter(adapterStatusFS);


                ArrayAdapter<CharSequence> adapterStatusWPIN= ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.W_pin_Set, android.R.layout.simple_spinner_item);
                adapterStatusWPIN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerWPinSet = findViewById(R.id.etWPinSet);
                SpinnerWPinSet.setAdapter(adapterStatusWPIN);

                ArrayAdapter<CharSequence> adapterStatusEF= ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.End_Fitting_Set, android.R.layout.simple_spinner_item);
                adapterStatusEF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerEndFittingSet = findViewById(R.id.etEndFittingSet);
                SpinnerEndFittingSet.setAdapter(adapterStatusEF);

               /* ArrayAdapter<CharSequence> adapterStatusSP= ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.sp_observation, android.R.layout.simple_spinner_item);
                adapterStatusSP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerSpObservaion = findViewById(R.id.spinnerSpecialObserve);
                SpinnerSpObservaion.setAdapter(adapterStatusSP);
*/
                ArrayAdapter<CharSequence> adapterStatusLP= ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.leg_painting, android.R.layout.simple_spinner_item);
                adapterStatusLP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerLegPainting = findViewById(R.id.spinnerLegPainting);
                SpinnerLegPainting.setAdapter(adapterStatusLP);

                ArrayAdapter<CharSequence> adapterStatusHLM= ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.hot_line_Main, android.R.layout.simple_spinner_item);
                adapterStatusHLM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerHotLineMaintenance = findViewById(R.id.spinnerHotLineMain);
                SpinnerHotLineMaintenance.setAdapter(adapterStatusHLM);

                NumberOfMissingParts.setText("0");
                NumberOFPinPole1.setText("0");
                NumberOFPinPole2.setText("0");
                NumberOFPinPole3.setText("0");
                NumberOfMissingParts.setText("0");
                NumberOfTappings.setText("0");
                SpecialObservations.setText("");

                ArrayAdapter<CharSequence> adapterStatusSP= ArrayAdapter.createFromResource(AddTowerMaintainance.this, R.array.sp_observation, android.R.layout.simple_spinner_item);
                adapterStatusSP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerSpObservaion = findViewById(R.id.spinnerSpecialObserve);
                SpinnerSpObservaion.setAdapter(adapterStatusSP);

                SpecialObservations.setVisibility(View.INVISIBLE);
                //SpecialObservations.setFocusable(true);
                SpinnerSpObservaion.setVisibility(View.VISIBLE);
                //uploadImageName.clearAnimation();
               /*

                //FlashOverSetNumber = (EditText) findViewById(R.id.etFlashOverSetNo);



                TowerID.setText("");
                NumberOfTappings.setText("0");
                NumberOfMissingParts.setText("0");
                NumberOFPinPole1.setText("0");
                NumberOFPinPole2.setText("0");
                NumberOFPinPole3.setText("0");
                NumberOfMissingParts.setText("0");
                // EndFittingSet.setText("");
                SpecialObservations.setText("");
                ConductorCondition.setText("");
                JumperCondition1.setText("");
                EarthConductorCondition1.setText("");
                FungusSet.setText("0");
                FlashOverSetNumber.setText("0");
                TypeOFSwichingDevice3.setText("");
                TypeOFSwichingDevice2.setText("");
                TypeOFSwichingDevice1.setText("");
                WPinSet.setText("0");
                EndFittingSet.setText("0");
*/
                CheckBox checkBox1 = findViewById(R.id.chkGOOD);
                checkBox1.setChecked(false);
                CheckBox checkBox2 = findViewById(R.id.chkTOUCH);
                checkBox2.setChecked(false);
                CheckBox checkBox3 = findViewById(R.id.chkDanger);
                checkBox3.setChecked(false);
                CheckBox checkBox4 = findViewById(R.id.chkCLOSE);
                checkBox4.setChecked(false);
                CheckBox checkBox5 = findViewById(R.id.chkARROUND);
                checkBox5.setChecked(false);
                CheckBox checkBox6 = findViewById(R.id.chkALONG);
                checkBox6.setChecked(false);
                CheckBox checkBox7 = findViewById(R.id.chkGOOD1);
                checkBox7.setChecked(false);
                CheckBox checkBox8 = findViewById(R.id.chkCOVERED);
                checkBox8.setChecked(false);
                CheckBox checkBox9 = findViewById(R.id.chkDanger1);
                checkBox9.setChecked(false);
                CheckBox checkBox10 = findViewById(R.id.chkMUFFIN_DAMAGE);
                checkBox10.setChecked(false);
                CheckBox checkBox11 = findViewById(R.id.chkCORRODED);
                checkBox11.setChecked(false);
                CheckBox checkBox12 = findViewById(R.id.chkWATER_LODGE);
                checkBox12.setChecked(false);
                CheckBox checkBox13 = findViewById(R.id.chkBACK_FILLING);
                checkBox13.setChecked(false);

                TextView pinpole1 = findViewById(R.id.textView60);
                pinpole1.setVisibility(View.GONE);
                NumberOFPinPole1 = findViewById(R.id.etNumberOFPinPole1);
                NumberOFPinPole1.setVisibility(View.GONE);
                //NumberOFPinPole1.setFocusable(true);
                TextView swdev1 = findViewById(R.id.textView61);
                swdev1.setVisibility(View.GONE);
                TypeOfSwichingDevice1 = findViewById(R.id.etTypeOFSwichingDevice1);
                TypeOfSwichingDevice1.setVisibility(View.GONE);
                //TypeOfSwichingDevice1.setFocusable(true);
                TextView pinpole2 = findViewById(R.id.textView63);
                pinpole2.setVisibility(View.GONE);

                NumberOFPinPole2 = findViewById(R.id.etNumberOFPinPole2);
                NumberOFPinPole2.setVisibility(View.GONE);
                //NumberOFPinPole2.setFocusable(true);
                TextView swdev2 = findViewById(R.id.textView64);
                swdev2.setVisibility(View.GONE);
                TypeOfSwichingDevice2 = findViewById(R.id.etTypeOFSwichingDevice2);
                TypeOfSwichingDevice2.setVisibility(View.GONE);
                // TypeOfSwichingDevice2.setFocusable(true);
                TextView pinpole3 = findViewById(R.id.textView65);
                pinpole3.setVisibility(View.GONE);
                NumberOFPinPole3 = findViewById(R.id.etNumberOFPinPole3);
                NumberOFPinPole3.setVisibility(View.GONE);
                //NumberOFPinPole3.setFocusable(true);

                TextView swdev3 = findViewById(R.id.textView66);
                swdev3.setVisibility(View.GONE);
                TypeOfSwichingDevice3 = findViewById(R.id.etTypeOFSwichingDevice3);
                TypeOfSwichingDevice3.setVisibility(View.GONE);
                //TypeOfSwichingDevice3.setFocusable(true);

            }
        });


        Button ButtonSendDB = findViewById(R.id.btnDBUpdate);
        ButtonSendDB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Check for the external storage permission
                if (ContextCompat.checkSelfPermission(AddTowerMaintainance.this.getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // If you do not have permission, request it
                    ActivityCompat.requestPermissions(AddTowerMaintainance.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_STORAGE_PERMISSION);
                }


                //     AlertDialog.Builder builder = new AlertDialog.Builder(AddTowerMaintainance.this);
                //   builder.setCancelable(true);
                // builder.setIcon(R.drawable.logo);
                // builder.setMessage("Do you want to save the Tower Maintenance Data?");
                // builder.setTitle("Save TM");
                // builder.setPositiveButton("Confirm",
                //       new DialogInterface.OnClickListener() {
                //         @Override
                //   public void onClick(DialogInterface dialog, int which) {
                //     System.out.println(" onClick " );
                //   TowerID = (EditText) findViewById(R.id.etTowerID);
                // NumberOfTappings = (EditText) findViewById(R.id.etNumberOFTappings);
                // NumberOfMissingParts = (EditText) findViewById(R.id.etMissingParts);
                // if (TowerID.getText().toString().trim().equals("")) {
                //   TowerID.setError("Should add a Tower Number!");
                //} else if (NumberOfTappings.getText().toString().trim().equals("")) {
                //  NumberOfTappings.setError("Should add a Tappings!");
                //} else if (NumberOfMissingParts.getText().toString().trim().equals("")) {
                //  NumberOfMissingParts.setError("Should add a missing parts!");
                //} else {

                // System.out.println(" AddTM " );
                // new AddTM().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                //   createExcel();

                // }


                //  }
                // });
                //builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                //  @Override
                //public void onClick(DialogInterface dialog, int which) {

                //}
                //});

                //AlertDialog dialog = builder.create();
                //dialog.show();

                //   }


                // });



                {

                    NumberOfTappings = findViewById(R.id.etNumberOFTappings);
                    NumberOfMissingParts = findViewById(R.id.etMissingParts);

                    if (NumberOfTappings.getText().toString().trim().equals("")) {
                        NumberOfTappings.setError("Should add a Number Of Tappings!");



                    }else if (Double.parseDouble(NumberOfTappings.getText().toString().trim()) < Double.parseDouble(existingTapping) ) {
                       NumberOfTappings.setError("Should add a Number Of Tappings Greater Than Existing Number of Tapping!  : " + existingTapping);



                } else if (NumberOfMissingParts.getText().toString().trim().equals("")) {
                        NumberOfMissingParts.setError("Should add a Number Of Missing Parts!");
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(AddTowerMaintainance.this);
                        builder.setCancelable(true);
                        builder.setIcon(R.drawable.logo);
                        builder.setMessage("Do you want to save the Tower Maintenance Data?");
                        builder.setTitle("Save TM");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        /*System.out.println(" AddTM " );
                                        new AddTM().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                                        createExcel();*/

                                        if(!isConnected(AddTowerMaintainance.this)){
                                            createExcel();
                                            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            new AddTM().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                            Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                                            createExcel();
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


        NumberOfTappings = findViewById(R.id.etNumberOFTappings);
        NumberOfTappings.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                //Toast.makeText(getApplicationContext(), "Go" + s.toString().equalsIgnoreCase("1"), Toast.LENGTH_SHORT).show();
                if( s.toString().equalsIgnoreCase("1")){
                    Toast.makeText(getApplicationContext(), "Go", Toast.LENGTH_SHORT).show();
                    TextView pinpole1 = findViewById(R.id.textView60);
                    pinpole1.setVisibility(View.VISIBLE);
                    NumberOFPinPole1 = findViewById(R.id.etNumberOFPinPole1);
                    NumberOFPinPole1.setVisibility(View.VISIBLE);
                    NumberOFPinPole1.setFocusable(true);
                    TextView swdev1 = findViewById(R.id.textView61);
                    swdev1.setVisibility(View.VISIBLE);
                    TypeOfSwichingDevice1 = findViewById(R.id.etTypeOFSwichingDevice1);
                    TypeOfSwichingDevice1.setVisibility(View.VISIBLE);
                    TypeOfSwichingDevice1.setFocusable(true);

                }else if(s.toString().equalsIgnoreCase("2")){
                    TextView pinpole1 = findViewById(R.id.textView60);
                    pinpole1.setVisibility(View.VISIBLE);
                    NumberOFPinPole1 = findViewById(R.id.etNumberOFPinPole1);
                    NumberOFPinPole1.setVisibility(View.VISIBLE);
                    NumberOFPinPole1.setFocusable(true);
                    TextView swdev1 = findViewById(R.id.textView61);
                    swdev1.setVisibility(View.VISIBLE);
                    TypeOfSwichingDevice1 = findViewById(R.id.etTypeOFSwichingDevice1);
                    TypeOfSwichingDevice1.setVisibility(View.VISIBLE);
                    TypeOfSwichingDevice1.setFocusable(true);
                    TextView pinpole2 = findViewById(R.id.textView63);
                    pinpole2.setVisibility(View.VISIBLE);

                    NumberOFPinPole2 = findViewById(R.id.etNumberOFPinPole2);
                    NumberOFPinPole2.setVisibility(View.VISIBLE);
                    NumberOFPinPole2.setFocusable(true);
                    TextView swdev2 = findViewById(R.id.textView64);
                    swdev2.setVisibility(View.VISIBLE);
                    TypeOfSwichingDevice2 = findViewById(R.id.etTypeOFSwichingDevice2);
                    TypeOfSwichingDevice2.setVisibility(View.VISIBLE);
                    TypeOfSwichingDevice2.setFocusable(true);
                }else if(s.toString().equalsIgnoreCase("3")){
                    TextView pinpole1 = findViewById(R.id.textView60);
                    pinpole1.setVisibility(View.VISIBLE);
                    NumberOFPinPole1 = findViewById(R.id.etNumberOFPinPole1);
                    NumberOFPinPole1.setVisibility(View.VISIBLE);
                    NumberOFPinPole1.setFocusable(true);
                    TextView swdev1 = findViewById(R.id.textView61);
                    swdev1.setVisibility(View.VISIBLE);
                    TypeOfSwichingDevice1 = findViewById(R.id.etTypeOFSwichingDevice1);
                    TypeOfSwichingDevice1.setVisibility(View.VISIBLE);
                    TypeOfSwichingDevice1.setFocusable(true);
                    TextView pinpole2 = findViewById(R.id.textView63);
                    pinpole2.setVisibility(View.VISIBLE);

                    NumberOFPinPole2 = findViewById(R.id.etNumberOFPinPole2);
                    NumberOFPinPole2.setVisibility(View.VISIBLE);
                    NumberOFPinPole2.setFocusable(true);
                    TextView swdev2 = findViewById(R.id.textView64);
                    swdev2.setVisibility(View.VISIBLE);
                    TypeOfSwichingDevice2 = findViewById(R.id.etTypeOFSwichingDevice2);
                    TypeOfSwichingDevice2.setVisibility(View.VISIBLE);
                    TypeOfSwichingDevice2.setFocusable(true);
                    TextView pinpole3 = findViewById(R.id.textView65);
                    pinpole3.setVisibility(View.VISIBLE);
                    NumberOFPinPole3 = findViewById(R.id.etNumberOFPinPole3);
                    NumberOFPinPole3.setVisibility(View.VISIBLE);
                    NumberOFPinPole3.setFocusable(true);

                    TextView swdev3 = findViewById(R.id.textView66);
                    swdev3.setVisibility(View.VISIBLE);
                    TypeOfSwichingDevice3 = findViewById(R.id.etTypeOFSwichingDevice3);
                    TypeOfSwichingDevice3.setVisibility(View.VISIBLE);
                    TypeOfSwichingDevice3.setFocusable(true);
                }else if(s.toString().equalsIgnoreCase("0")){

                }


                else{
                    Toast.makeText(getApplicationContext(), "Invalid Number", Toast.LENGTH_SHORT).show();
                }

               /* Toast.makeText(getApplicationContext(), s + "3", Toast.LENGTH_SHORT).show();
                if(!s.equals("3") ) {
                    Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                }*/
            }



            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {


            }
        });


        Button buttonSync = findViewById(R.id.btnSync);

        if (!isConnected(AddTowerMaintainance.this)) {
            findViewById(R.id.btnSync).setVisibility(View.INVISIBLE);
        }
        else {
            findViewById(R.id.btnSync).setVisibility(View.VISIBLE);
        }
        buttonSync.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                {


                    if (!isConnected(AddTowerMaintainance.this)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddTowerMaintainance.this);
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(AddTowerMaintainance.this);
                        builder.setCancelable(true);
                        builder.setMessage("Do you want to save the line?");
                        builder.setTitle("Save Support");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new AddTowerMaintainance.SaveTMFromExcel().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        //readTM();
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


        NumberOfTappings = findViewById(R.id.etNumberOFTappings);
        NumberOFPinPole1 = findViewById(R.id.etNumberOFPinPole1);
        TypeOfSwichingDevice1 = findViewById(R.id.etTypeOFSwichingDevice1);

        //   SpinnerLeavingStatus = (Spinner) findViewById(R.id.etTypeOFSwichingDevice1);
        //  SpinnerLeavingStatus.setPrompt("select your switching device1");


        NumberOFPinPole2 = findViewById(R.id.etNumberOFPinPole2);
        TypeOfSwichingDevice2 = findViewById(R.id.etTypeOFSwichingDevice2);

        //SpinnerLeavingStatus = (Spinner) findViewById(R.id.etTypeOFSwichingDevice2);
        //SpinnerLeavingStatus.setPrompt("select your switching device2");

        NumberOFPinPole3 = findViewById(R.id.etNumberOFPinPole3);
        TypeOfSwichingDevice3 = findViewById(R.id.etTypeOFSwichingDevice3);

        //SpinnerLeavingStatus = (Spinner) findViewById(R.id.etTypeOFSwichingDevice3);
        //SpinnerLeavingStatus.setPrompt("select your switching device3");

    }



     //   Button ButtonSend = (Button) findViewById(R.id.btnSave);

       // ButtonSend.setOnClickListener(new View.OnClickListener() {
         //   public void onClick(View v) {
           //     System.out.println("hhhhh1");
               // Toast.makeText(AddTowerMaintainance.this, "btnsave",
                 //       Toast.LENGTH_LONG).show();
//                if ((!VisitID.getText().toString().equals(null)) && (!TowerID.getText().toString().equals(null))
//                        && (!NumberOfTappings.getText().toString().equals("")) && (!NumberOfMissingParts.getText().toString().equals("")//                        && (!NumberOfFlashOverSets.getText().toString().equals("")) && (!SpinnerLeavingStatus.getSelectedItem().toString().equals(""))
//                        && (!SpinnerBaseConcreteStatus.getSelectedItem().toString().equals("")) && (!SpinnerAntiClimbingStatus.getSelectedItem().toString().equals(""))
//                        && (!SpinnerConductorCondition.getSelectedItem().toString().equals("")) && (!SpinnerJumperCondition.getSelectedItem().toString().equals(""))
//                        && (!SpinnerEarthConductorCondition.getSelectedItem().toString().equals("")) && (!SpinnerMaintainanceAttention.getSelectedItem().toString().equals(""))
//                        && (!FungusSet.getText().toString().equals("")) && (!WPinSet.getText().toString().equals("")) && (!EndFittingSet.getText().toString().equals(""))
//                        && (!SpecialObservations.getText().toString().equals(""))) {
//                    emptyMessage();
//                }
                // Do something in response to button click
//                Toast.makeText(AddTowerMaintainance.this, "alertMessage",
//                        Toast.LENGTH_LONG).show();
                //VisitID = (EditText) findViewById(R.id.etVisitID);
               // TowerID = (EditText) findViewById(R.id.etTowerID);
               //NumberOfTappings = (EditText) findViewById(R.id.etNumberOFTappings);
               //NumberOfMissingParts = (EditText) findViewById(R.id.etMissingParts);
              // SpecialObservations = (EditText) findViewById(R.id.etSpecialObserve);
              // ConductorCondition=(EditText)findViewById(R.id.etConductorCondition);
               //JumperCondition1=(EditText)findViewById(R.id.etJumperCondition1);
               //EarthConductorCondition1=(EditText)findViewById(R.id.etEarthConductorCondition1);



                //if (TowerID.getText().toString().trim().equals("")) {
                  //  TowerID.setError("Should add a Tower Number!");
             //    if (NumberOfTappings.getText().toString().trim().equals("")) {
               //    NumberOfTappings.setError("Should add a Tappings!");
              // }
                     // else if (NumberOfMissingParts.getText().toString().trim().equals("")) {
                 //   NumberOfMissingParts.setError("Should add a missing parts!");
                //}else if(SpecialObservations.getText().toString().trim().equals("")) {
                //SpecialObservations.setError("Should add a Special Observations!");
                //}
                //else {


                  //   alertMessage();
                // }
                   // progressSaving();
                //}

           // }
       // });

//        Button ButtonNext = (Button) findViewById(R.id.btnNext);
//
//        ButtonNext.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                // Do something in response to button click
////                Toast.makeText(AddTowerMaintainance.this, "alertMessage",
////                        Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(AddTowerMaintainance.this, TM2.class);
//                startActivity(intent);0
//
//            }
//        });


        //NumberOfTappings = (EditText) findViewById(R.id.etNumberOFTappings);
        //NumberOFPinPole1 = (EditText) findViewById(R.id.etNumberOFPinPole1);
        //TypeOfSwichingDevice1 = (Spinner) findViewById(R.id.etTypeOFSwichingDevice1);

        //SpinnerLeavingStatus = (Spinner) findViewById(R.id.etTypeOFSwichingDevice1);
        //SpinnerLeavingStatus.setPrompt("select your switching device1");


        //NumberOFPinPole2 = (EditText) findViewById(R.id.etNumberOFPinPole2);
        //TypeOfSwichingDevice2 = (Spinner) findViewById(R.id.etTypeOFSwichingDevice2);

        //SpinnerLeavingStatus = (Spinner) findViewById(R.id.etTypeOFSwichingDevice2);
        //SpinnerLeavingStatus.setPrompt("select your switching device2");

        //NumberOFPinPole3 = (EditText) findViewById(R.id.etNumberOFPinPole3);
        //TypeOfSwichingDevice3 = (Spinner) findViewById(R.id.etTypeOFSwichingDevice3);

        //SpinnerLeavingStatus = (Spinner) findViewById(R.id.etTypeOFSwichingDevice3);
        //SpinnerLeavingStatus.setPrompt("select your switching device3");

        //  NumberOfTappings.setOnClickListener(new View.OnClickListener() {


        //     public void onClick(View v) {

        //        String input = NumberOfTappings.getText().toString().trim();
        //      int int_input = 0;

        //        if (input != null) {
        //           int_input = Integer.parseInt(input);

        //       }

        //       if (int_input > 0) {
        //           if (int_input == 1) {
        //               NumberOFPinPole1.setFocusable(true);
        //                TypeOfSwichingDevice1.setFocusable(true);
        //           }else if (int_input == 2) {
        //               NumberOFPinPole1.setFocusable(true);
        //               TypeOfSwichingDevice1.setFocusable(true);
        //               NumberOFPinPole2.setFocusable(true);
        //               TypeOfSwichingDevice2.setFocusable(true);
        //           }else{
        //               NumberOFPinPole1.setFocusable(false);
        //               TypeOfSwichingDevice1.setFocusable(false);
        //               NumberOFPinPole2.setFocusable(false);
        //                TypeOfSwichingDevice2.setFocusable(false);

        //}

        //       }else{
        //            NumberOFPinPole1.setFocusable(false);
        //            TypeOfSwichingDevice1.setFocusable(false);
        //            NumberOFPinPole2.setFocusable(false);
        //            TypeOfSwichingDevice2.setFocusable(false);


        //       }
        //   }

        // });


   // }


    public void emptyMessage() {
        // TODO Auto-generated method stub
        AlertDialog.Builder alert = new AlertDialog.Builder(AddTowerMaintainance.this);
        alert.setTitle("Alert !");
        alert.setMessage("You have to fill all fields");
        alert.setPositiveButton("OK", null);
        alert.show();
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

    public void alertMessage() {
  // Toast.makeText(AddTowerMaintainance.this, "Error",
    //            Toast.LENGTH_LONG).show();

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        // Yes button clicked
                        Toast.makeText(AddTowerMaintainance.this, "Yes Clicked",
                                Toast.LENGTH_LONG).show();



                        // new AddTowerMaintainanceData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                      //  new  AddTM().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        createExcel();


                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked
                        // do nothing
                        Toast.makeText(AddTowerMaintainance.this, "No Clicked",
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


    @Override
    public void onBackPressed() {

        if (SpecialObservations.getVisibility() == View.VISIBLE) {
            SpecialObservations.setVisibility(View.INVISIBLE);
        }
        if (SpecialObservations.getVisibility() == View.INVISIBLE) {
            SpecialObservations.setVisibility(View.VISIBLE);
        }

        if (ConductorCondition.getVisibility() == View.VISIBLE) {
            ConductorCondition.setVisibility(View.INVISIBLE);
        }
        if (ConductorCondition.getVisibility() == View.INVISIBLE) {
            ConductorCondition.setVisibility(View.VISIBLE);
        }

        if (JumperCondition1.getVisibility() == View.VISIBLE) {
            JumperCondition1.setVisibility(View.INVISIBLE);
        }
        if (JumperCondition1.getVisibility() == View.INVISIBLE) {
            JumperCondition1.setVisibility(View.VISIBLE);
        }

        if (EarthConductorCondition1.getVisibility() == View.VISIBLE) {
            EarthConductorCondition1.setVisibility(View.INVISIBLE);
        }
        if (EarthConductorCondition1.getVisibility() == View.INVISIBLE) {
            EarthConductorCondition1.setVisibility(View.VISIBLE);
        }

        if (TypeOfSwichingDevice1.getVisibility() == View.VISIBLE) {
            TypeOfSwichingDevice1.setVisibility(View.INVISIBLE);
        }
        if (TypeOfSwichingDevice1.getVisibility() == View.INVISIBLE) {
            TypeOfSwichingDevice1.setVisibility(View.VISIBLE);
        }
        if (TypeOfSwichingDevice2.getVisibility() == View.VISIBLE) {
            TypeOfSwichingDevice2.setVisibility(View.INVISIBLE);
        }
        if (TypeOfSwichingDevice2.getVisibility() == View.INVISIBLE) {
            TypeOfSwichingDevice2.setVisibility(View.VISIBLE);
        }
        if (TypeOfSwichingDevice3.getVisibility() == View.VISIBLE) {
            TypeOfSwichingDevice3.setVisibility(View.INVISIBLE);
        }
        if (TypeOfSwichingDevice3.getVisibility() == View.INVISIBLE) {
            TypeOfSwichingDevice3.setVisibility(View.VISIBLE);
        }
        if (FlashOverSetNumber.getVisibility() == View.VISIBLE) {
            FlashOverSetNumber.setVisibility(View.INVISIBLE);
        }
        if (FlashOverSetNumber.getVisibility() == View.INVISIBLE) {
            FlashOverSetNumber.setVisibility(View.VISIBLE);
        }
        if (FungusSet.getVisibility() == View.VISIBLE) {
            FungusSet.setVisibility(View.INVISIBLE);
        }
        if (FungusSet.getVisibility() == View.INVISIBLE) {
            FungusSet.setVisibility(View.VISIBLE);
        }
        if (WPinSet.getVisibility() == View.VISIBLE) {
            WPinSet.setVisibility(View.INVISIBLE);
        }
        if (WPinSet.getVisibility() == View.INVISIBLE) {
            WPinSet.setVisibility(View.VISIBLE);
        }
        if (EndFittingSet.getVisibility() == View.VISIBLE) {
            EndFittingSet.setVisibility(View.INVISIBLE);
        }
        if (EndFittingSet.getVisibility() == View.INVISIBLE) {
            EndFittingSet.setVisibility(View.VISIBLE);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if(SpinnerLeavingStatus.getVisibility()==View.VISIBLE){
//            SpinnerLeavingStatus.setVisibility(View.INVISIBLE);
//        }else if(SpinnerBaseConcreteStatus.getVisibility()==View.VISIBLE){
//            SpinnerBaseConcreteStatus.setVisibility(View.INVISIBLE);
//        }else
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
            // super.onBackPressed();
        }


    }


    public void onCheckboxClicked(View view) {


        //GOOD = (CheckBox) findViewById(R.id.chkGOOD);

        // GOOD.setOnClickListener((View.OnClickListener) this);
        // TOUCH = (CheckBox) findViewById(R.id.chkTOUCH);
        // TOUCH.setOnClickListener((View.OnClickListener) this);
        // Danger = (CheckBox) findViewById(R.id.chkDanger);
        // Danger.setOnClickListener((View.OnClickListener) this);
        // CLOSE = (CheckBox) findViewById(R.id.chkCLOSE);
        // CLOSE.setOnClickListener((View.OnClickListener) this);
        // ARROUND = (CheckBox) findViewById(R.id.chkALONG);
        // ARROUND.setOnClickListener((View.OnClickListener) this);


        //public void onClick(View view) {

        //   switch (view.getId()) {
        //      case R.id.chkGOOD:
        //        if (GOOD.isChecked())
        //           Toast.makeText(getApplicationContext(), "Android", Toast.LENGTH_LONG).show();
        //       break;
        //    case R.id.chkTOUCH:
        //       if (TOUCH.isChecked())
        //           Toast.makeText(getApplicationContext(), "Java", Toast.LENGTH_LONG).show();
        //       break;

        // }}


        boolean checked = ((CheckBox) view).isChecked();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_tower_maintainance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//
//        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_settings:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.apphome) {
            Intent intent = new Intent(AddTowerMaintainance.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(AddTowerMaintainance.this, GetNearByTower.class);
            startActivity(intent);

            //////////////////////////////// PHM - LCM ////////////////////////////////////////////

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddTowerMaintainance.this, TM2.class);
            startActivity(intent);

            //////////////////////////////// PHM - SUb ////////////////////////////////////////////

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addBusBar) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddBusBar.class);
            startActivity(intent);

        } else if (id == R.id.nav_addStructual) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddStructural.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantryMore) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddGantryMore.class);
            startActivity(intent);

        } else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSwitches) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddSwitches.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSurge) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddSurgeArrestors.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTransformersG) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddTransformersG.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddEquipment.class);
            startActivity(intent);

///////////////////// POLE DETAILS //////////////////////////////////////////////

        } else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(AddTowerMaintainance.this, AddTransformers.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddTowerMaintainance.this, Login.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageToUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
                break;
            case R.id.bUploadImage:
                break;
        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    if (requestCode==RESULT_LOAD_IMAGE&& resultCode==RESULT_OK&& data!=null){
//        Uri selectedImage=data.getData();
//        imageToUpload.setImageURI(selectedImage);
//    }
//
//    }

    private class AddTowerMintenance extends AsyncTask<Void, Void, String[]> {
        protected String[] doInBackground(Void... urls) {
            String toweridd = TowerID.getText().toString();
            String numberoftappings = NumberOfTappings.getText().toString();
            String numberofmissingparts = NumberOfMissingParts.getText().toString();
            String numberofflashoversets = NumberOfFlashOverSets.getSelectedItem().toString();
            String wayleavingstatus = SpinnerLeavingStatus.getSelectedItem().toString();
            String baseconcretestatus = SpinnerBaseConcreteStatus.getSelectedItem().toString();
            String anticlimbingstatus = SpinnerAntiClimbingStatus.getSelectedItem().toString();
            String conductorcondition = SpinnerConductorCondition.getSelectedItem().toString();
            String jumpercondition = SpinnerJumperCondition.getSelectedItem().toString();
            String earthconductorcondition = SpinnerEarthConductorCondition.getSelectedItem().toString();
            String maintainanceattention = SpinnerMaintainanceAttention.getSelectedItem().toString();
            String fungusSet = SpinnerFungusSet.getSelectedItem().toString();
            String wPinSet = SpinnerWPinSet.getSelectedItem().toString();
            String endFittingSet = SpinnerEndFittingSet.getSelectedItem().toString();
            //String specialobservations = SpecialObservations.getText().toString();
            String specialobservations = SpinnerSpObservaion.getSelectedItem().toString();
            String numberofpinpole1 = NumberOFPinPole1.getText().toString();
            String typeOfSwichingDevice1 = TypeOfSwichingDevice1.getSelectedItem().toString();
            String numberofpinpol2 = NumberOFPinPole2.getText().toString();
            String typeOfSwichingDevice2 = TypeOfSwichingDevice2.getSelectedItem().toString();
            String numberofpinpol3 = NumberOFPinPole3.getText().toString();
            String typeOfSwichingDevice3 = TypeOfSwichingDevice3.getSelectedItem().toString();

            if (specialobservations.equals("Other")) {
                specialobservations = SpecialObservations.getText().toString();
            }

            if (conductorcondition.equals("Other")) {
                conductorcondition = ConductorCondition.getText().toString();
            }
            if (jumpercondition.equals("Other")) {
                jumpercondition = JumperCondition1.getText().toString();
            }
            if (earthconductorcondition.equals("Other")) {
                earthconductorcondition = EarthConductorCondition1.getText().toString();
            }

            if (typeOfSwichingDevice1.equals("Other")) {
                typeOfSwichingDevice1 =TypeOFSwichingDevice1.getText().toString();
            }

            if (typeOfSwichingDevice2.equals("Other")) {
                typeOfSwichingDevice2 =TypeOFSwichingDevice2.getText().toString();
            }

            if (numberofflashoversets.equals("Other")) {
                numberofflashoversets =FlashOverSetNumber.getText().toString();
            }
            if (fungusSet.equals("Other")) {
                fungusSet =FungusSet.getText().toString();
            }

            if (wPinSet.equals("Other")) {
                wPinSet =WPinSet.getText().toString();
            }
            if (endFittingSet.equals("Other")) {
                endFittingSet =EndFittingSet.getText().toString();
            }

            String legPainting = SpinnerLegPainting.getSelectedItem().toString();
            String hotlinemaint = SpinnerHotLineMaintenance.getSelectedItem().toString();


            RestTemplate rest = new RestTemplate();
            String url4 = Util.SRT_URL + "addComplain/" + toweridd + "/" + numberoftappings + "/";
            System.out.println("ssss" + url4);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            massage = rest.getForObject(url4, String.class);
            return values;
        }


        protected void onPostExecute(String[] results) {
            // ListView Item Click Listener


        }
    }

    public List<String> readExcelTowerNo(){
        System.out.println("readExcel");
         //Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
        List<String> resultSet = new ArrayList<String>();
         //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

        System.out.println("readExcel1");
        File inputWorkbook  = new File(Environment.getExternalStorageDirectory() +File.separator + "Schedule" +File.separator+ "TowerNo.xls");
        System.out.println("readExcel2");
         //Toast.makeText(getApplication(),"readExcel2: " , Toast.LENGTH_SHORT).show();

        if(inputWorkbook.exists()) {
            System.out.println("readExcel3");
           // Toast.makeText(getApplication(),"readExcel3: " , Toast.LENGTH_SHORT).show();

            Workbook w;
            try {
                System.out.println("readExcel4");

                w = Workbook.getWorkbook(inputWorkbook);
                System.out.println("readExce5");
                // Toast.makeText(getApplication(),"readExcel4: " , Toast.LENGTH_SHORT).show();

                // Get the first sheet
                Sheet sheet = w.getSheet(0);
                System.out.println("readExce6");

                // Loop over column and lines
                int coloumn = sheet.getRows();
                values = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);
                    System.out.println(cell.getContents());
                      //  Toast.makeText(getApplication(),"readExcel5: " + cell.getContents(), Toast.LENGTH_SHORT).show();
                    spinnerMap.put(j,cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    values[j] = cell1.getContents();
                    //Toast.makeText(getApplication(),"readExcel6: " + cell1.getContents(), Toast.LENGTH_SHORT).show();
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
                SpinnerTowerNo = findViewById(R.id.spinnerTowerNo);
                SpinnerTowerNo.setAdapter(adapterNs);
//


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
        //

        //VisitID = (EditText) findViewById(R.id.etVisitID);
        //TowerID=(EditText)findViewById(R.id.etTowerID);
        SpinnerTowerNo = findViewById(R.id.spinnerTowerNo);
        NumberOfTappings = findViewById(R.id.etNumberOFTappings);
        NumberOfMissingParts = findViewById(R.id.etMissingParts);
        NumberOfFlashOverSets = findViewById(R.id.etFlashOverSets);
        NumberOFPinPole1 = findViewById(R.id.etNumberOFPinPole1);
        TypeOfSwichingDevice1 = findViewById(R.id.etTypeOFSwichingDevice1);
        NumberOFPinPole2 = findViewById(R.id.etNumberOFPinPole2);
        TypeOfSwichingDevice2 = findViewById(R.id.etTypeOFSwichingDevice2);
        NumberOFPinPole3 = findViewById(R.id.etNumberOFPinPole3);
        TypeOfSwichingDevice3 = findViewById(R.id.etTypeOFSwichingDevice3);
        SpinnerLeavingStatus = findViewById(R.id.SpinnerLeavingStatus);
        SpinnerBaseConcreteStatus = findViewById(R.id.SpinnerBaseConcreteStatus1);
        SpinnerAntiClimbingStatus = findViewById(R.id.SpinnerAntiClimbingStatus1);
        SpinnerConductorCondition = findViewById(R.id.SpinnerConductorCondition);
        SpinnerJumperCondition = findViewById(R.id.SpinnerJumperCondition1);
        SpinnerEarthConductorCondition = findViewById(R.id.SpinnerEarthConductorCondition1);
        SpinnerJumperCondition = findViewById(R.id.SpinnerJumperCondition1);
        SpinnerMaintainanceAttention = findViewById(R.id.SpinnerMaintainanceAttention1);
        SpinnerSpObservaion = findViewById(R.id.spinnerSpecialObserve);
        SpinnerLegPainting = findViewById(R.id.spinnerLegPainting);
        SpinnerHotLineMaintenance = findViewById(R.id.spinnerHotLineMain);

        //FlashOverSetNumber = (EditText) findViewById(R.id.etFlashOverSetNo);
        SpinnerFungusSet = findViewById(R.id.etFungusSet);
        SpinnerWPinSet = findViewById(R.id.etWPinSet);
        SpinnerEndFittingSet = findViewById(R.id.etEndFittingSet);
        SpecialObservations = findViewById(R.id.etSpecialObserve);
        ConductorCondition = findViewById(R.id.etConductorCondition);
        JumperCondition1 = findViewById(R.id.etJumperCondition1);
        EarthConductorCondition1 = findViewById(R.id.etEarthConductorCondition1);

        //String visitid = VisitID.getText().toString();
        //  String toweridd = TowerID.getText().toString();
        String toweridd = SpinnerTowerNo.getSelectedItem().toString();
        System.out.println("createExcel2");
        //  Toast.makeText(getApplication(),
        //     "Tower No: "+ toweridd , Toast.LENGTH_SHORT).show();
        String numberoftappings = NumberOfTappings.getText().toString();
        System.out.println("createExcel3");
        // Toast.makeText(getApplication(),
        //       "numberoftappings No: "+ numberoftappings , Toast.LENGTH_SHORT).show();
        String numberofpinpole1 = NumberOFPinPole1.getText().toString();
        System.out.println("createExcel4");
        // Toast.makeText(getApplication(),
        //       " numberofpinpole1: "+ numberofpinpole1 , Toast.LENGTH_SHORT).show();
        String typeOfSwichingDevice1 = TypeOfSwichingDevice1.getSelectedItem().toString();
        // Toast.makeText(getApplication(),
        //       " TypeOfSwichingDevice1: "+ typeOfSwichingDevice1 , Toast.LENGTH_SHORT).show();
        System.out.println("createExcel5");
        String numberofpinpol2 = NumberOFPinPole2.getText().toString();
        // Toast.makeText(getApplication(),
        //    " numberofpinpol2: "+ numberofpinpol2 , Toast.LENGTH_SHORT).show();
        System.out.println("createExcel6");
        String typeOfSwichingDevice2 = TypeOfSwichingDevice2.getSelectedItem().toString();
        //Toast.makeText(getApplication(),
        //      " typeOfSwichingDevice2: "+ typeOfSwichingDevice2 , Toast.LENGTH_SHORT).show();
        System.out.println("createExcel7");
        String numberofpinpol3 = NumberOFPinPole3.getText().toString();
        //  Toast.makeText(getApplication(),
        //        " numberofpinpol3: "+ numberofpinpol3 , Toast.LENGTH_SHORT).show();
        System.out.println("createExcel8");
        String typeOfSwichingDevice3 = TypeOfSwichingDevice3.getSelectedItem().toString();
        System.out.println("createExcel9");
        // Toast.makeText(getApplication(),
        //       " typeOfSwichingDevice3: "+ typeOfSwichingDevice3 , Toast.LENGTH_SHORT).show();
//       Toast.makeText(getApplication(),
//                "numberoftappings: "+ numberoftappings , Toast.LENGTH_SHORT).show();
        String numberofmissingparts = NumberOfMissingParts.getText().toString();
        // Toast.makeText(getApplication(),
        //       " numberofmissingparts: "+ numberofmissingparts , Toast.LENGTH_SHORT).show();
        String numberofflashoversets = NumberOfFlashOverSets.getSelectedItem().toString();
        if (numberofflashoversets.equals("Other")) {
            numberofflashoversets = FlashOverSetNumber.getText().toString();

        }
        System.out.println("createExce20");
        // Toast.makeText(getApplication(),
        //       " numberofflashoversets: "+ numberofflashoversets , Toast.LENGTH_SHORT).show();
        String wayleavingstatus = SpinnerLeavingStatus.getSelectedItem().toString();
        //Toast.makeText(getApplication(),
        //      " wayleavingstatus: "+ wayleavingstatus , Toast.LENGTH_SHORT).show();
        String baseconcretestatus = SpinnerBaseConcreteStatus.getSelectedItem().toString();
        // Toast.makeText(getApplication(),
        //      " wayleavingstatus: "+ wayleavingstatus , Toast.LENGTH_SHORT).show();
        String anticlimbingstatus = SpinnerAntiClimbingStatus.getSelectedItem().toString();
        String conductorcondition = SpinnerConductorCondition.getSelectedItem().toString();
        if (conductorcondition.equals("Other")) {
            conductorcondition = ConductorCondition.getText().toString();

        }
        System.out.println("createExce2l");
        String jumpercondition = SpinnerJumperCondition.getSelectedItem().toString();
        if (jumpercondition.equals("Other")) {
            jumpercondition = JumperCondition1.getText().toString();

        }
        System.out.println("createExce22");
        String earthconductorcondition = SpinnerEarthConductorCondition.getSelectedItem().toString();
        if (earthconductorcondition.equals("Other")) {
            earthconductorcondition = EarthConductorCondition1.getText().toString();

        }
        System.out.println("createExce23");
        String maintainanceattention = SpinnerMaintainanceAttention.getSelectedItem().toString();

        String fungusSet = SpinnerFungusSet.getSelectedItem().toString();
        if (fungusSet.equals("Other")) {
            fungusSet = FungusSet.getText().toString();

        }
        System.out.println("createExce24");
        String wPinSet = SpinnerWPinSet.getSelectedItem().toString();
        if (wPinSet.equals("Other")) {
            wPinSet = WPinSet.getText().toString();

        }
        System.out.println("createExce25");
        String endFittingSet = SpinnerEndFittingSet.getSelectedItem().toString();
        if (endFittingSet.equals("Other")) {
            endFittingSet = EndFittingSet.getText().toString();

        }
        System.out.println("createExce26");
        //String specialobservations = SpecialObservations.getText().toString();
        String specialobservations = SpinnerSpObservaion.getSelectedItem().toString();
        if (specialobservations.equals("Other")) {
            specialobservations = SpecialObservations.getText().toString();

        }
        System.out.println("createExce26");
        String legPainting = SpinnerLegPainting.getSelectedItem().toString();
        String hotlinemaint = SpinnerHotLineMaintenance.getSelectedItem().toString();


        String pinPole1 = NumberOFPinPole1.getText().toString();
        String divType1 = TypeOfSwichingDevice1.getSelectedItem().toString();
        if (divType1.equals("Other")) {
            divType1 = TypeOFSwichingDevice1.getText().toString();

        }
        String pinpole2 = NumberOFPinPole2.getText().toString();
        String divType2 = TypeOfSwichingDevice2.getSelectedItem().toString();
        if (divType2.equals("Other")) {
            divType2 = TypeOFSwichingDevice2.getText().toString();

        }
        String pinpole3 = NumberOFPinPole3.getText().toString();
        String divType3 = TypeOfSwichingDevice3.getSelectedItem().toString();
        if (divType3.equals("Other")) {
            divType3 = TypeOFSwichingDevice3.getText().toString();

        }

        // Toast.makeText(getApplication(),
        //   "pinPole1: "+ pinPole1 , Toast.LENGTH_SHORT).show();

        //String flashoversetnumber = FlashOverSetNumber.getText().toString();
        String checkBox1 =  "";
        String checkBox2 = "";
        String checkBox3 = "";
        String checkBox4 = "";
        String checkBox5 = "";
        String checkBox6 = "";
        GOOD = findViewById(R.id.chkGOOD1);
        TOUCH = findViewById(R.id.chkTOUCH);
        Danger = findViewById(R.id.chkDanger);
        CLOSE = findViewById(R.id.chkCLOSE);
        ARROUND = findViewById(R.id.chkARROUND);
        ALONG = findViewById(R.id.chkALONG);

        if (GOOD.isChecked()) {
            checkBox1 = "GOOD";
        }
        //Toast.makeText(getApplication(), "value1 : " + checkBox + "," + checkBox1, Toast.LENGTH_SHORT).show();
        if (TOUCH.isChecked()) {
            checkBox2 = " TOUCH";
            //Toast.makeText(getApplication(), "value2 : " + checkBox + "," + checkBox1, Toast.LENGTH_SHORT).show();
        }
        if (Danger.isChecked()) {
            checkBox3 = "DANGER";
            //Toast.makeText(getApplication(), "value3 : " + checkBox + "," + checkBox1 + "," + checkBox2, Toast.LENGTH_SHORT).show();
        }
        if (CLOSE.isChecked()) {
            checkBox4 = "CLOSE";
            //Toast.makeText(getApplication(), "value4 : " + checkBox + "," + checkBox1 + "," + checkBox2 + "," + checkBox3, Toast.LENGTH_SHORT).show();
        }
        if (ARROUND.isChecked()) {
            checkBox5 = "ARROUND";
            //Toast.makeText(getApplication(), "value5: " + checkBox + "," + checkBox1 + "," + checkBox2 + "," + checkBox3 + "," + checkBox4, Toast.LENGTH_SHORT).show();
        }
        if (ALONG.isChecked()) {
            checkBox6 = "ALONG";
            //Toast.makeText(getApplication(), "value6 : " + checkBox + "," + checkBox1 + "," + checkBox2 + "," + checkBox3 + "," + checkBox4 + "," + checkBox5, Toast.LENGTH_SHORT).show();
        }

        String way_leaving_status = "," + checkBox1 + "," + checkBox2 + "," + checkBox3 + "," + checkBox4 + "," + checkBox5 + "," + checkBox6;
//            System.out.println("####################################################way_leaving_status CreateExcel: " + way_leaving_status);
        way_leaving_status = way_leaving_status.replaceAll(",+", ",");
        way_leaving_status = way_leaving_status.replaceFirst("," , "");
//            System.out.println("####################################################way_leaving_status CreateExcel replace: " + way_leaving_status);
        way_leaving_status = way_leaving_status.replaceAll(",$","");
        way_leaving_status = way_leaving_status.replaceAll(","," , ");
        way_leaving_status = way_leaving_status.trim();
//            System.out.println("####################################################way_leaving_status CreateExcel trim: " + way_leaving_status);

        String checkBox7 = "";
        String checkBox8 = "";
        String checkBox9 = "";
        String checkBox10 = "";
        String checkBox11 = "";
        String checkBox12 = "";
        String checkBox13 = "";

        GOOD1 = findViewById(R.id.chkGOOD1);
        COVERED = findViewById(R.id.chkCOVERED);
        Danger1 = findViewById(R.id.chkDanger1);
        M_DAMAGE = findViewById(R.id.chkMUFFIN_DAMAGE);
        CORRODED = findViewById(R.id.chkCORRODED);
        WATER_LODGE = findViewById(R.id.chkWATER_LODGE);
        BACKFILLING = findViewById(R.id.chkBACK_FILLING);
        if (GOOD1.isChecked()) {
            checkBox7 = "GOOD";
        }   //Toast.makeText(getApplication(), "value7 : " + checkBox6, Toast.LENGTH_SHORT).show();
        if (COVERED.isChecked()) {
            checkBox8 = " COVERED";
            //  Toast.makeText(getApplication(), "value8 : " + checkBox6 + "," + checkBox7, Toast.LENGTH_SHORT).show();
        }
        if (Danger1.isChecked()) {
            checkBox9 = "DANGER";
            //Toast.makeText(getApplication(), "value9: " + checkBox6 + "," + checkBox7 + "," + checkBox8, Toast.LENGTH_SHORT).show();
        }
        if (M_DAMAGE.isChecked()) {
            checkBox10 = "M_DAMAGE";
            //Toast.makeText(getApplication(), "value10 : " + checkBox6 + "," + checkBox7 + "," + checkBox8 + "," + checkBox9, Toast.LENGTH_SHORT).show();
        }
        if (CORRODED.isChecked()) {
            checkBox11 = "CORRODED";
            //Toast.makeText(getApplication(), "value11: " + checkBox6 + "," + checkBox7 + "," + checkBox8 + "," + checkBox9 + "," + checkBox10, Toast.LENGTH_SHORT).show();
        }
        if (WATER_LODGE.isChecked()) {
            checkBox12 = "WATER_LODGE";
            //Toast.makeText(getApplication(), "value12 : " + checkBox6 + "," + checkBox7 + "," + checkBox8 + "," + checkBox9 + "," + checkBox10 + "," + checkBox11, Toast.LENGTH_SHORT).show();
        }
        if (BACKFILLING.isChecked()) {
            checkBox13 = "BACKFILLING";
            //Toast.makeText(getApplication(), "value13 : " + checkBox6 + "," + checkBox7 + "," + checkBox8 + "," + checkBox9 + "," + checkBox10 + "," + checkBox11 + "," + checkBox12, Toast.LENGTH_SHORT).show();
        }

        String Base_concrete_status = "," + checkBox7 + "," + checkBox8 + "," + checkBox9 + "," + checkBox10 + "," + checkBox11 + "," + checkBox12 + "," + checkBox13;
//                System.out.println("####################################################Base_concrete_status CreateExcel: " + Base_concrete_status);
        Base_concrete_status = Base_concrete_status.replaceAll(",+", ",");
        Base_concrete_status = Base_concrete_status.replaceFirst("," , "");
//                System.out.println("####################################################Base_concrete_status CreateExcel replace: " + Base_concrete_status);
        Base_concrete_status = Base_concrete_status.replaceAll(",$"," ");
        Base_concrete_status = Base_concrete_status.replaceAll(","," , ");
        Base_concrete_status =Base_concrete_status.trim();
//                System.out.println("####################################################Base_concrete_status CreateExcel trim: " + Base_concrete_status);

        try {
//           Toast.makeText(getApplication(),
//                    "DBHelper: " , Toast.LENGTH_SHORT).show();

            // Toast.makeText(getApplication(),
            // "pinpole2: "+ pinpole2 , Toast.LENGTH_SHORT).show();
            mydb = new DBHelper(this);
//            Toast.makeText(getApplication(),
//                    "DBHelper2: " , Toast.LENGTH_SHORT).show();
            //mydb.onUpgrade(db,1,2);
            SessionManager obj = new SessionManager(getBaseContext());
            String area = obj.getKeyArea();
            String line = obj.getKeyLine();
            System.out.println("areaarea" + area);
            System.out.println("lineline" + line);
            System.out.println("towerExcel" + towerExcel);

            //spinnerMap.get(1);
            //mydb.insertTowerMnt(toweridd, numberoftappings, numberofmissingparts, numberofflashoversets, wayleavingstatus,
            //      baseconcretestatus, anticlimbingstatus, conductorcondition, jumpercondition, earthconductorcondition,
            //      maintainanceattention, fungusSet, wPinSet, endFittingSet, specialobservations, legPainting, hotlinemaint, line, area, pinPole1, divType1, pinpole2, divType2);

            //  String towerNo, String noOfTappings, String noofmissingparts, String noofflashoversets,
            //    String wayLeavingStatus, String baseConcreteStatus, String antiClimbingStatus, String conductorCondition, String jumperConductorCondition,
            //  String earthConductorCondition, String maintainanceAttention, String fungusSet, String wpinSet, String endFittingSet, String specialObservations, String leg_painting, String hot_line_main, String line, String area,String numerofPinpole1,String typeOfSwichingDevice1,String numerofPinpole2,String typeOfSwichingDevice2,String numerofPinpole3,String typeOfSwichingDevice3) {


            mydb.insertTowerMnt(towerExcel, numberoftappings, numberofmissingparts, numberofflashoversets, way_leaving_status,
                    Base_concrete_status , anticlimbingstatus, conductorcondition, jumpercondition, earthconductorcondition,
                    maintainanceattention,fungusSet, wPinSet, endFittingSet, specialobservations, legPainting,hotlinemaint, area,  line,  pinPole1, divType1, pinpole2, divType2,pinpole3, divType3);
//
//
//
// Toast.makeText(getApplication(),
//                    "DBHelper3: " , Toast.LENGTH_SHORT).show();
            //final Cursor res = mydb.getData();
//            Toast.makeText(getApplication(),
//                    "DBHelper4: " , Toast.LENGTH_SHORT).show();
            //File sd = Environment.getExternalStorageDirectory();
            final Cursor res = mydb.getData();
            File sd = new File(Environment.getExternalStorageDirectory().toString() +
                    File.separator + "TowerMaintenance");

            boolean success = true;
            if (!sd.exists()) {
                success = sd.mkdirs();
            }
            if (success) {
                // Do something on success
            } else {
                // Do something else on failure
            }


            String csvFile = "TowerMaintenance.xls";
            // String csvFile = area + "-" + line + ".xls";
            // final Cursor res = mydb.getDataTM(line, area);
            File directory = new File(sd.getAbsolutePath());
            System.out.println("directory: " + directory);

            //create directory if not exist
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }

            //file path

            //if(file != null && file.exists() ){

            // }else{
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            sheet = workbook.createSheet("tmList", 0);
            // column and row
            String[] columnName = res.getColumnNames();
            //    Toast.makeText(getApplication(),
            //   "columnName 1: " + columnName, Toast.LENGTH_SHORT).show();
            //if(columnName ==null) {
            //sheet.addCell(new Label(0, 0, "VisitID"));
            sheet.addCell(new Label(0, 0, "Tower_No"));
            sheet.addCell(new Label(1, 0, "Tappings"));
            sheet.addCell(new Label(2, 0, "Missing_Parts"));
            sheet.addCell(new Label(3, 0, "Flashover_Sets"));
            sheet.addCell(new Label(4, 0, "Main_Date"));
            sheet.addCell(new Label(5, 0, "Way_Leave"));
            sheet.addCell(new Label(6, 0, "Base_Concrete"));
            sheet.addCell(new Label(7, 0, "Anti_Climbing"));
            sheet.addCell(new Label(8, 0, "Conductor"));
            sheet.addCell(new Label(9, 0, "Jumper_Status"));
            sheet.addCell(new Label(10, 0, "Earth_Conductor"));
            sheet.addCell(new Label(11, 0, "Attention"));
            sheet.addCell(new Label(12, 0, "Fungus_Set"));
            sheet.addCell(new Label(13, 0, " W Pin_Set"));
            sheet.addCell(new Label(14, 0, "End Fitting_Set"));
            sheet.addCell(new Label(15, 0, "Tower_special"));
            sheet.addCell(new Label(16, 0, "Leg Painting"));
            sheet.addCell(new Label(17, 0, "Hot Line Maintenance"));
            sheet.addCell(new Label(18, 0, "Line"));
            sheet.addCell(new Label(19, 0, "Area"));
            sheet.addCell(new Label(20, 0, "Numer_of_pinpole1"));
            sheet.addCell(new Label(21, 0, "Type_Of_SwichingDevice1"));
            sheet.addCell(new Label(22, 0, "Numer_of_pinpole2"));
            sheet.addCell(new Label(23, 0, "Type_Of_SwichingDevice2"));
            sheet.addCell(new Label(24, 0, "Numer_of_pinpole3"));
            sheet.addCell(new Label(25, 0, "Type_Of_SwichingDevice3"));
            // }
//            Toast.makeText(getApplication(),
//                        "column created 1", Toast.LENGTH_SHORT).show();

            // sheet.addCell(new Label(1, 0, "phone"));
            //}
//            Toast.makeText(getApplication(),
//                    "res" +res, Toast.LENGTH_SHORT).show();

            if (res.moveToFirst()) {
                do {
//                    Toast.makeText(getApplication(),
//                            "rrrr", Toast.LENGTH_SHORT).show();

                    //String vid = res.getString(res.getColumnIndex(DBHelper.TM_VISITID));
                    String towerno = res.getString(res.getColumnIndex(DBHelper.TM_TOWERNO));
                    String nooftappings = res.getString(res.getColumnIndex(DBHelper.TM_NOOFTAPPINGS));
                    String noofmissingparts = res.getString(res.getColumnIndex(DBHelper.TM_NUMBEROFMISSINGPARTS));
                    String noofflashoversets = res.getString(res.getColumnIndex(DBHelper.TM_NUMBEROFFLASHOVERSETS));
                    //String maindate = res.getString(res.getColumnIndex(DBHelper.TM_MAIN_DATE));
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
                    String maindate = dateFormat.format(date);
                    String wayleavingstatus1 = res.getString(res.getColumnIndex(DBHelper.TM_WAYLEAVINGSTATUS));
                    String baseconcretestatus1 = res.getString(res.getColumnIndex(DBHelper.TM_BASECONCRETESTATUS));
                    String anticlimbingstatus1 = res.getString(res.getColumnIndex(DBHelper.TM_ANTICLIMBINGSTATUS));
                    String conductorcondition1 = res.getString(res.getColumnIndex(DBHelper.TM_CONDUCTORCONDITION));
                    String jumpercondition1 = res.getString(res.getColumnIndex(DBHelper.TM_JUMPERCONDITION));
                    String earthconductorcondition1 = res.getString(res.getColumnIndex(DBHelper.TM_EARTHCONDUCTORCONDITION));
                    String maintainanceattention1 = res.getString(res.getColumnIndex(DBHelper.TM_MAINTAINANCEATTENTION));
                    String fungusset = res.getString(res.getColumnIndex(DBHelper.TM_FUNGUS_SET));
                    String wpinset = res.getString(res.getColumnIndex(DBHelper.TM_W_PIN_SET));
                    String endfittingset = res.getString(res.getColumnIndex(DBHelper.TM_END_FITTINGSET));
                    String specialobservations1 = res.getString(res.getColumnIndex(DBHelper.TM_SPECIALOBSERVATIONS));
                    String leg_Painting = res.getString(res.getColumnIndex(DBHelper.TM_LEG_PAINTING));
                    String hot_line_maint = res.getString(res.getColumnIndex(DBHelper.TM_HOTLINE_MAIN));
                    String tmline = res.getString(res.getColumnIndex(DBHelper.TM_LINE));
                    String tmarea = res.getString(res.getColumnIndex(DBHelper.TM_AREA));
                    String nopin1 = res.getString(res.getColumnIndex(DBHelper.TM_NUMBEROFPINPOLE1));
                    // Toast.makeText(getApplication(),
                    //       "nooftappings1" +nopin1, Toast.LENGTH_SHORT).show();
                    String type1 = res.getString(res.getColumnIndex(DBHelper.TM_TYPEOFSWICHINGDEVICE1));
                    //Toast.makeText(getApplication(),
                    //      "type1" +type1, Toast.LENGTH_SHORT).show();
                    String nopin2 = res.getString(res.getColumnIndex(DBHelper.TM_NUMBEROFPINPOLE2));
                    //Toast.makeText(getApplication(),
                    //      "nooftappings2" +nopin2, Toast.LENGTH_SHORT).show();
                    String type2 = res.getString(res.getColumnIndex(DBHelper.TM_TYPEOFSWICHINGDEVICE2));
                    //Toast.makeText(getApplication(),
                    //      "type2" +type2, Toast.LENGTH_SHORT).show();
                    String nopin3 = res.getString(res.getColumnIndex(DBHelper.TM_NUMBEROFPINPOLE3));
                    //Toast.makeText(getApplication(),
                    //      "nooftappings3" +nopin3, Toast.LENGTH_SHORT).show();
                    String type3 = res.getString(res.getColumnIndex(DBHelper.TM_TYPEOFSWICHINGDEVICE3));
                    //Toast.makeText(getApplication(),
                    //      "type3" +type3, Toast.LENGTH_SHORT).show();


                    // String phoneNumber = res.getString(res.getColumnIndex("phone"));
//                             Toast.makeText(getApplication(),
//                            "rrrr 1", Toast.LENGTH_SHORT).show();

                    int i = res.getPosition() + 1;
//                          Toast.makeText(getApplication(),
//                          "rrrr 2", Toast.LENGTH_SHORT).show();
                    sheet.addCell(new Label(0, i, "1"));
                    sheet.addCell(new Label(0, i, towerno));
                    sheet.addCell(new Label(1, i, nooftappings));
                    sheet.addCell(new Label(2, i, noofmissingparts));
                    sheet.addCell(new Label(3, i, noofflashoversets));
                    sheet.addCell(new Label(4, i, maindate));
                    sheet.addCell(new Label(5, i, wayleavingstatus1));
                    sheet.addCell(new Label(6, i, baseconcretestatus1));
                    sheet.addCell(new Label(7, i, anticlimbingstatus1));
                    sheet.addCell(new Label(8, i, conductorcondition1));
                    sheet.addCell(new Label(9, i, jumpercondition1));
                    sheet.addCell(new Label(10, i, earthconductorcondition1));
                    sheet.addCell(new Label(11, i, maintainanceattention1));
                    sheet.addCell(new Label(12, i, fungusset));
                    sheet.addCell(new Label(13, i, wpinset));
                    sheet.addCell(new Label(14, i, endfittingset));
                    sheet.addCell(new Label(15, i, specialobservations1));
                    sheet.addCell(new Label(16, i, leg_Painting));
                    sheet.addCell(new Label(17, i, hot_line_maint));
                    sheet.addCell(new Label(18, i, tmline));
                    sheet.addCell(new Label(19, i, tmarea));
                    sheet.addCell(new Label(20, i, numberofpinpole1));
                    sheet.addCell(new Label(21, i, typeOfSwichingDevice1));
                    sheet.addCell(new Label(22, i, numberofpinpol2));
                    sheet.addCell(new Label(23, i, typeOfSwichingDevice2));
                    sheet.addCell(new Label(24, i, numberofpinpol3));
                    sheet.addCell(new Label(25, i, typeOfSwichingDevice3));

                    //      Toast.makeText(getApplication(), "towerno ", Toast.LENGTH_SHORT).show();

//                          Toast.makeText(getApplication(),
//                          "rrrr 3", Toast.LENGTH_SHORT).show();
                    //sheet.addCell(new Label(1, i, phoneNumber));
                } while (res.moveToNext());
            }

            //closing cursor
            res.close();
            workbook.write();
            workbook.close();
            // mydb.deleteTM();
//            Toast.makeText(getApplication(),
//                    "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
            //progressSaving();


        } catch (WriteException e) {
            e.printStackTrace();
            Toast.makeText(getApplication(),
                    " Error while generating excel sheet : ", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplication(),
                    "error while generating excel sheet: ", Toast.LENGTH_SHORT).show();
        }


//        Toast.makeText(AddTowerMaintainance.this, "Visit ID"  +VisitID.getText() +"weyleavrs status : " + wayleavingstatus ,
//                Toast.LENGTH_LONG).show();

        // VisitID.setText("");

        // TowerID.setText("");
        //NumberOfTappings.setText("");
        //NumberOfMissingParts.setText("");
        //NumberOfFlashOverSets.setSelection();
        //TowerID.setText("");
        //TowerID.setText("");
        //TowerID.setText("");
        //TowerID.setText("");
        //TowerID.setText("");
        //TowerID.setText("");
        //TowerID.setText("");
        //FungusSet.setText("");
        //WPinSet.setText("");
        //EndFittingSet.setText("");
        //SpecialObservations.setText("");
        //NumberOFPinPole1.setText("");
               /* android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(AddTowerMaintainance.this);
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
                progressSaving();*/


        //   }

        // }

    }

    private class AddTowerMaintainanceData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            VisitID = findViewById(R.id.etTowerID);
            System.out.println("hiii5555 ttt " + VisitID.getText());
            final RestTemplate restTemplate = new RestTemplate();
            System.out.println("hiii5555 yyy");
            final String url1 = Util.URL + "getArea?access_token=" + Util.Access_Token + "&acct_number=" + VisitID.getText();
            System.out.println("hiii5555 yyy1" + url1);
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("hiii5555 str" + restTemplate.getForObject(url1, String.class));
            return restTemplate.getForObject(url1, String.class);


        }

        @Override
        protected void onPostExecute(String result) {
            try {
                System.out.println("hiii5555 yyyyyyyyy" + result);
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputStream is = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
                Document dom = builder.parse(is);
                areaCode = dom.getElementsByTagName("string").item(0).getChildNodes().item(0).getTextContent();
                System.out.println("hiii5555 yyyyyy" + areaCode);


            } catch (Exception e) {
                System.out.println(" error " + e.getMessage());
            }
        }

    }


    public List<MmsTxntowermaintenance> readTM() {
        System.out.println("readExcel#");
        List<MmsTxntowermaintenance> objs = null;
//         Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
        List<String> resultSet = new ArrayList<String>();
        //      Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
        String result = "";

        System.out.println("readExcel1#");
        File inputWorkbook = new File(Environment.getExternalStorageDirectory() + File.separator + "TowerMaintenance" + File.separator + "TowerMaintenance.xls");
        System.out.println("readExcel2#");
        //      Toast.makeText(getApplication(),"readExcel2: " , Toast.LENGTH_SHORT).show();

        if (inputWorkbook.exists()) {
            System.out.println("readExcel3#");
            //         Toast.makeText(getApplication(),"readExcel3: " , Toast.LENGTH_SHORT).show();

            Workbook w;
            try {
                //          System.out.println("readExcel4");

                w = Workbook.getWorkbook(inputWorkbook);
                System.out.println("readExcel4");
                //                Toast.makeText(getApplication(),"readExcel4: " , Toast.LENGTH_SHORT).show();

                // Get the first sheet
                Sheet sheet = w.getSheet(0);
                System.out.println("readExcel5");

                // Loop over column and lines
                int coloumn = sheet.getRows();
                //values = new String[coloumn];
                objs = new ArrayList<MmsTxntowermaintenance>(coloumn);


                for (int j = 1; j < sheet.getRows(); j++) {
                    MmsTxntowermaintenance objSu = new MmsTxntowermaintenance();
                   /* Cell cell = sheet.getCell(0, j);
                    System.out.println(cell.getContents());
                    Toast.makeText(getApplication(),"readExcelSupport: " + cell.getContents(), Toast.LENGTH_SHORT).show();*/
                    // spinnerMap.put(j, cell.getContents());
                    Cell cell = sheet.getCell(0, j);
                    System.out.println(cell.getContents());
                    //  Toast.makeText(getApplication(),"Tower No: " + cell.getContents(), Toast.LENGTH_SHORT).show();
                    String TowerNo = cell.getContents();
                    //objSu.setTowerNo(TowerNo);


                    Cell cell1 = sheet.getCell(1, j);
                    System.out.println(cell1.getContents());
                    //  Toast.makeText(getApplication(),"Tapping: " + cell1.getContents(), Toast.LENGTH_SHORT).show();
                    String NoOfTapping = cell1.getContents();
                    objSu.setNooftappings(new BigDecimal(NoOfTapping));


                    Cell cell2 = sheet.getCell(2, j);
                    System.out.println(cell2.getContents());
                    //  Toast.makeText(getApplication(),"Missing Parts: " + cell2.getContents(), Toast.LENGTH_SHORT).show();
                    String MissingParts = cell2.getContents();
                    objSu.setNoofmissingparts(new BigDecimal(MissingParts));


                    Cell cell3 = sheet.getCell(3, j);
                    System.out.println(cell3.getContents());
                    //  Toast.makeText(getApplication(),"Flashover Set: " + cell3.getContents(), Toast.LENGTH_SHORT).show();
                    String FlashoverSet = cell3.getContents();
                    objSu.setFlashoversetno(FlashoverSet);


                    Cell cell4 = sheet.getCell(4, j);
                    System.out.println(cell4.getContents());
                    //  Toast.makeText(getApplication(),"Main Date: " + cell4.getContents(), Toast.LENGTH_SHORT).show();
                    String MainDate = cell4.getContents();
                    objSu.setMaintenancedate(new Date(MainDate));


                    Cell cell5 = sheet.getCell(5, j);
                    System.out.println(cell5.getContents());
                    //  Toast.makeText(getApplication(),"Way Leave: " + cell5.getContents(), Toast.LENGTH_SHORT).show();
                    String WayLeave = cell5.getContents();
                    objSu.setWayleavestatus(WayLeave);

                    Cell cell6 = sheet.getCell(6, j);
                    System.out.println(cell6.getContents());
                    //  Toast.makeText(getApplication(),"Base Concrete: " + cell6.getContents(), Toast.LENGTH_SHORT).show();
                    String BaseConcrete = cell6.getContents();
                    objSu.setBaseconcretestatus(BaseConcrete);


                    Cell cell7 = sheet.getCell(7, j);
                    System.out.println(cell7.getContents());
                    //   Toast.makeText(getApplication(),"Anti Climbing: " + cell7.getContents(), Toast.LENGTH_SHORT).show();
                    String AntiClimbing = cell7.getContents();
                    objSu.setAnticlimbingstatus(AntiClimbing);


                    Cell cell8 = sheet.getCell(8, j);
                    System.out.println(cell8.getContents());
                    //   Toast.makeText(getApplication(),"Conductor: " + cell8.getContents(), Toast.LENGTH_SHORT).show();
                    String Conductor = cell8.getContents();
                    objSu.setConductorstatus(Conductor);


                    Cell cell9 = sheet.getCell(9, j);
                    System.out.println(cell9.getContents());
                    //   Toast.makeText(getApplication(),"Jumper Status: " + cell9.getContents(), Toast.LENGTH_SHORT).show();
                    String JumperStatus = cell9.getContents();
                    objSu.setJumperstatus(JumperStatus);


                    Cell cell10 = sheet.getCell(10, j);
                    System.out.println(cell10.getContents());
                    //   Toast.makeText(getApplication(),"Earth Conductor: " + cell10.getContents(), Toast.LENGTH_SHORT).show();
                    String EarthConductor = cell10.getContents();
                    objSu.setEarthconductorstatus(EarthConductor);


                    Cell cell11 = sheet.getCell(11, j);
                    System.out.println(cell11.getContents());
                    //   Toast.makeText(getApplication(),"Attention: " + cell11.getContents(), Toast.LENGTH_SHORT).show();
                    String Attention = cell11.getContents();
                    objSu.setAttentionstatus(Attention);


                    Cell cell12 = sheet.getCell(12, j);
                    System.out.println(cell12.getContents());
                    //  Toast.makeText(getApplication(),"Fungus set: " + cell12.getContents(), Toast.LENGTH_SHORT).show();
                    String FungusSet = cell12.getContents();
                    objSu.setFungussetno(new BigDecimal(FungusSet));
                    //   Toast.makeText(getApplication(),"Fungus set: " + cell12.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell13 = sheet.getCell(13, j);
                    System.out.println(cell13.getContents());
                    //  Toast.makeText(getApplication(),"W Pin Set: " + cell13.getContents(), Toast.LENGTH_SHORT).show();
                    String WPinSet = cell13.getContents();
                    objSu.setWpinset(new BigDecimal(WPinSet));

                 /*   Cell cell13 = sheet.getCell(13, j);
                    System.out.println(cell13.getContents());
                    Toast.makeText(getApplication(),"W Pin Set: " + cell13.getContents(), Toast.LENGTH_SHORT).show();
                    String WPinSet=cell13.getContents();
                    objSu.setWpinset(new BigDecimal(WPinSet));
*/
                    Cell cell14 = sheet.getCell(14, j);
                    System.out.println(cell14.getContents());
                    //   Toast.makeText(getApplication(),"End Fitting: " + cell14.getContents(), Toast.LENGTH_SHORT).show();
                    String EndFitting = cell14.getContents();
                    objSu.setEndfittingset(new BigDecimal(EndFitting));


                    Cell cell15 = sheet.getCell(15, j);
                    System.out.println(cell15.getContents());
                    //   Toast.makeText(getApplication(),"Tower special: " + cell15.getContents(), Toast.LENGTH_SHORT).show();
                    String TowerSpecial = cell15.getContents();
                    objSu.setTowerspecial(TowerSpecial);


                    Cell cell16 = sheet.getCell(16, j);
                    System.out.println(cell16.getContents());
                    //   Toast.makeText(getApplication(),"Leg Painting " + cell16.getContents(), Toast.LENGTH_SHORT).show();
                    String LegPainting = cell16.getContents();
                    objSu.setLegPainting(LegPainting);


                    Cell cell17 = sheet.getCell(17, j);
                    System.out.println(cell17.getContents());
                    //   Toast.makeText(getApplication(),"Hot Line Maintenance: " + cell17.getContents(), Toast.LENGTH_SHORT).show();
                    String HotLineMaintenance = cell17.getContents();
                    objSu.setHotLineMnt(HotLineMaintenance);

                    Cell cell18 = sheet.getCell(18, j);
                    System.out.println(cell18.getContents());
                    //String Line= cell18.getContents();
                    //  Toast.makeText(getApplication(),"Line: " + cell18.getContents(), Toast.LENGTH_SHORT).show();


                    Cell cell19 = sheet.getCell(19, j);
                    System.out.println(cell19.getContents());
                    //String Area=cell19.getContents();
                    //   Toast.makeText(getApplication(),"Area: " + cell19.getContents(), Toast.LENGTH_SHORT).show();


                    Cell cell20 = sheet.getCell(20, j);
                    System.out.println(cell20.getContents());
                    //   Toast.makeText(getApplication(),"Number of pinpole 1: " + cell20.getContents(), Toast.LENGTH_SHORT).show();
                    String NuOfPinpole1 = cell20.getContents();
                    objSu.setPinpole1(new BigDecimal(NuOfPinpole1));


                    Cell cell21 = sheet.getCell(21, j);
                    System.out.println(cell21.getContents());
                    //    Toast.makeText(getApplication(),"Type of Switching Device 1: " + cell21.getContents(), Toast.LENGTH_SHORT).show();
                    String SwitchingDev1 = cell21.getContents();
                    objSu.setSwitchdev1(SwitchingDev1);


                    Cell cell22 = sheet.getCell(22, j);
                    System.out.println(cell22.getContents());
                    //    Toast.makeText(getApplication(),"Number of pinpole 2: " + cell22.getContents(), Toast.LENGTH_SHORT).show();
                    String NuOfPinpole2 = cell22.getContents();
                    objSu.setPinpole2(new BigDecimal(NuOfPinpole2));


                    Cell cell23 = sheet.getCell(23, j);
                    System.out.println(cell23.getContents());
                    //   Toast.makeText(getApplication(),"Type of Switching Device 2: " + cell23.getContents(), Toast.LENGTH_SHORT).show();
                    String SwitchingDev2 = cell23.getContents();
                    objSu.setSwitchdev2(SwitchingDev2);

                    Cell cell24 = sheet.getCell(24, j);
                    System.out.println(cell24.getContents());
                    //  Toast.makeText(getApplication(),"Number of pinpole 3: " + cell24.getContents(), Toast.LENGTH_SHORT).show();
                    String NuOfPinpole3 = cell24.getContents();
                    objSu.setPinpole3(new BigDecimal(NuOfPinpole3));


                    Cell cell25 = sheet.getCell(25, j);
                    System.out.println(cell25.getContents());
                    //    Toast.makeText(getApplication(),"Type of Switching Device 3: " + cell25.getContents(), Toast.LENGTH_SHORT).show();
                    String SwitchingDev3 = cell25.getContents();
                    objSu.setSwitchdev3(SwitchingDev3);


                /*    SessionManager obj = new SessionManager(getBaseContext());
                    String area = obj.getKeyArea();
                    String line = obj.getKeyLine();
                    String cycle = obj.getKeyCycle();

                    final RestTemplate restTemplate = new RestTemplate();
                    String url4 = Util.SRT_URL + "addTowerMnt/"+cycle+"/"+objSu.getTowerNo()+"/"+ objSu.getNooftappings() + "/" + objSu.getNoofmissingparts() + "/"+ objSu.getFlashoversetno() + "/"
                            +objSu.getWayleavestatus()+"/" +objSu.getBaseconcretestatus()  + "/"+objSu.getAnticlimbingstatus()+"/"+objSu.getConductorstatus()+"/"+ objSu.getJumperstatus()+"/"+
                            objSu.getEarthconductorstatus()+"/"+objSu.getAttentionstatus()+"/"+objSu.getLegPainting()+"/"+ objSu.getHotLineMnt()+"/"+objSu.getFungussetno()+"/"+
                            objSu.getWpinset()+"/"+objSu.getEndfittingset()+"/"+objSu.getTowerspecial()+"/"+area+"/"+line+"/"+objSu.getPinpole1()+"/"+objSu.getSwitchdev1()+"/"+objSu.getPinpole2()+"/"+objSu.getSwitchdev2()+"/"+objSu.getPinpole3()+"/"+objSu.getSwitchdev3()+"/";
//


                    Toast.makeText(getApplication(),"url4: " + url4, Toast.LENGTH_SHORT).show();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    Toast.makeText(getApplication(),"url5: " + url4, Toast.LENGTH_SHORT).show();
                    result= restTemplate.getForObject(url4, String.class);
                    // System.out.println("hiii5555 yyyyyyyyyhhjj" + result);
                    Toast.makeText(getApplication(),"url6: " + url4, Toast.LENGTH_SHORT).show();*/


                    objs.add(objSu);




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
        return objs;
    }



    private class loadTapping extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgDialog = new ProgressDialog(AddTowerMaintainance.this);
//message should be changed according to the requirement
            ProgDialog.setMessage("Please wait...\n(This may take some time, depending on your network connection)");
            ProgDialog.setIndeterminate(false);
            ProgDialog.setTitle(Util.alert_header);
            ProgDialog.setIcon(R.drawable.logo);
            ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            ProgDialog.setCancelable(true);
            ProgDialog.show();

        }


        protected String doInBackground(String... urls) {

           // SessionManager obj = new SessionManager(getBaseContext());
           // String area = obj.getKeyArea();
           // String line = obj.getKeyLine();
           // System.out.println("areaarea" + area);
           // System.out.println("lineline" + line);

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "getTapping?id="+Tower;

            System.out.println("ssss" + url6);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url6, String.class);
            //return List<MmsAddArea>null;
        }


        protected void onPostExecute(String results) {
            // ListView Item Click Listener
            System.out.println("results" + results);
            existingTapping = results;
            NumberOfTappings = findViewById(R.id.etNumberOFTappings);
            NumberOfTappings.setText(results);


            //System.out.println("results" + results.length);
            //String[] Tower;
            //values = new String[results.length];
            //  String[] spinnerArray = new String[Province_ID.size()];
            //  HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
            //  for (int i = 0; i < Province_ID.size(); i++)
            //  {
            //      spinnerMap.put(i,Province_ID.get(i));
            //      spinnerArray[i] = Province_NAME.get(i);
            //  }


//
            /*if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddsupport obj = results[c];
                    values[c] = obj.getTowerNo();
                    spinnerMap.put(c, String.valueOf(obj.getId()));
                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx: " + spinnerMap);
                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx: " + values[c]);
                    System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy: " + obj.getId());

                }
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerTowerNo = findViewById(R.id.spinnerTowerNo);
            SpinnerTowerNo.setAdapter(adapterNs);
            *///System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz: " + SpinnerTowerNo.getSelectedItem().toString());
//
            ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }


    }

    private class loadTowerNo extends AsyncTask<String, Void, MmsAddsupport[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgDialog = new ProgressDialog(AddTowerMaintainance.this);
//message should be changed according to the requirement
            ProgDialog.setMessage("Please wait...\n(This may take some time, depending on your network connection)");
            ProgDialog.setIndeterminate(false);
            ProgDialog.setTitle(Util.alert_header);
            ProgDialog.setIcon(R.drawable.logo);
            ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            ProgDialog.setCancelable(true);
            ProgDialog.show();

        }


        protected MmsAddsupport[] doInBackground(String... urls) {

            SessionManager obj = new SessionManager(getBaseContext());
            String area = obj.getKeyArea();
            String line = obj.getKeyLine();
            System.out.println("areaarea" + area);
            System.out.println("lineline" + line);

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "getSupport/" + area + "/" + line + "/";

            System.out.println("ssss" + url6);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url6, MmsAddsupport[].class);
            //return List<MmsAddArea>null;
        }


        protected void onPostExecute(MmsAddsupport[] results) {
            // ListView Item Click Listener
            System.out.println("results" + results);
            System.out.println("results" + results.length);
            String[] Tower;
            values = new String[results.length];
            //  String[] spinnerArray = new String[Province_ID.size()];
            //  HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
            //  for (int i = 0; i < Province_ID.size(); i++)
            //  {
            //      spinnerMap.put(i,Province_ID.get(i));
            //      spinnerArray[i] = Province_NAME.get(i);
            //  }


//
            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddsupport obj = results[c];
                    values[c] = obj.getTowerNo();
                    spinnerMap.put(c, String.valueOf(obj.getId()));
                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx: " + spinnerMap);
                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx: " + values[c]);
                    System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy: " + obj.getId());

                }
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerTowerNo = findViewById(R.id.spinnerTowerNo);
            SpinnerTowerNo.setAdapter(adapterNs);
            //System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz: " + SpinnerTowerNo.getSelectedItem().toString());
//
            ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }


    }
/*
    private class AddTM extends AsyncTask<Void, Void, String> {


        protected String doInBackground(Void... urls) {
            System.out.println(" doInBackground " );
            //  TowerID = (EditText) findViewById(R.id.etTowerID);
            SpinnerTowerID=(Spinner)findViewById(R.id.spinnerTowerNo);
            SpinnerVisitID=(Spinner)findViewById(R.id.SpinnerCycle);
            SpinnerTowerID=(Spinner)findViewById(R.id.spinnerTowerNo);
            NumberOfTappings = (EditText) findViewById(R.id.etNumberOFTappings);
            NumberOfMissingParts = (EditText) findViewById(R.id.etMissingParts);
            NumberOfFlashOverSets = (Spinner) findViewById(R.id.etFlashOverSets);
            SpinnerLeavingStatus = (Spinner) findViewById(R.id.SpinnerLeavingStatus);
            SpinnerBaseConcreteStatus = (Spinner) findViewById(R.id.SpinnerBaseConcreteStatus1);
            SpinnerAntiClimbingStatus = (Spinner) findViewById(R.id.SpinnerAntiClimbingStatus1);
            SpinnerConductorCondition = (Spinner) findViewById(R.id.SpinnerConductorCondition);
            SpinnerJumperCondition = (Spinner) findViewById(R.id.SpinnerJumperCondition1);
            SpinnerEarthConductorCondition = (Spinner) findViewById(R.id.SpinnerEarthConductorCondition1);
            SpinnerJumperCondition = (Spinner) findViewById(R.id.SpinnerJumperCondition1);
            SpinnerMaintainanceAttention = (Spinner) findViewById(R.id.SpinnerMaintainanceAttention1);
            SpinnerSpObservaion = (Spinner) findViewById(R.id.spinnerSpecialObserve);
            SpinnerLegPainting = (Spinner) findViewById(R.id.spinnerLegPainting);
            SpinnerHotLineMaintenance = (Spinner) findViewById(R.id.spinnerHotLineMain);
            NumberOFPinPole1 = (EditText) findViewById(R.id.etNumberOFPinPole1);
            SpinnerFungusSet= (Spinner) findViewById(R.id.etFungusSet);
            Spinnerarea= (Spinner)findViewById(R.id.spinnerArea);

            // SpinnerArea = (Spinner) findViewById(R.id.spinnerArea);
            // String supportArea = SpinnerArea.getSelectedItem().toString();
            SpinnerWPinSet = (Spinner) findViewById(R.id.etWPinSet);
            SpinnerEndFittingSet = (Spinner) findViewById(R.id.etEndFittingSet);
            SpecialObservations = (EditText) findViewById(R.id.etSpecialObserve);
            ConductorCondition=(EditText)findViewById(R.id.etConductorCondition);
            JumperCondition1=(EditText)findViewById(R.id.etJumperCondition1);
            EarthConductorCondition1=(EditText)findViewById(R.id.etEarthConductorCondition1);
            NumberOFPinPole1 = (EditText) findViewById(R.id.etNumberOFPinPole1);
            TypeOfSwichingDevice1 = (Spinner) findViewById(R.id.etTypeOFSwichingDevice1);
            NumberOFPinPole2 = (EditText) findViewById(R.id.etNumberOFPinPole2);
            TypeOfSwichingDevice2 = (Spinner) findViewById(R.id.etTypeOFSwichingDevice2);
            NumberOFPinPole3 = (EditText) findViewById(R.id.etNumberOFPinPole3);
            TypeOfSwichingDevice3 = (Spinner) findViewById(R.id.etTypeOFSwichingDevice3);


            // String toweridd = TowerID.getText().toString();
            //String TowerID= SpinnerTowerNo.getSelectedItem().toString();
            String toweridd= SpinnerTowerID.getSelectedItem().toString();

            String numberoftappings = NumberOfTappings.getText().toString();

            String numberofmissingparts = NumberOfMissingParts.getText().toString();

            String numberofflashoversets = NumberOfFlashOverSets.getSelectedItem().toString();
            if (numberofflashoversets.equals("Other")) {
                numberofflashoversets = FlashOverSetNumber.getText().toString();

            }
            String checkBox1 = "";
            String checkBox2 = "";
            String checkBox3 = "";
            String checkBox4 = "";
            String checkBox5 = "";
            String checkBox6 = "";
            GOOD = (CheckBox) findViewById(R.id.chkGOOD1);
            TOUCH = (CheckBox) findViewById(R.id.chkTOUCH);
            Danger = (CheckBox) findViewById(R.id.chkDanger);
            CLOSE = (CheckBox) findViewById(R.id.chkCLOSE);
            ARROUND = (CheckBox) findViewById(R.id.chkARROUND);
            ALONG = (CheckBox) findViewById(R.id.chkALONG);

            if (GOOD.isChecked()) {
                checkBox1 = "GOOD";
            }
            //Toast.makeText(getApplication(), "value1 : " + checkBox + "," + checkBox1, Toast.LENGTH_SHORT).show();
            if (TOUCH.isChecked()) {
                checkBox2 = "TOUCH";
                //Toast.makeText(getApplication(), "value2 : " + checkBox + "," + checkBox1, Toast.LENGTH_SHORT).show();
            }
            if (Danger.isChecked()) {
                checkBox3 = "DANGER";
                //Toast.makeText(getApplication(), "value3 : " + checkBox + "," + checkBox1 + "," + checkBox2, Toast.LENGTH_SHORT).show();
            }
            if (CLOSE.isChecked()) {
                checkBox4 = "CLOSE";
                //Toast.makeText(getApplication(), "value4 : " + checkBox + "," + checkBox1 + "," + checkBox2 + "," + checkBox3, Toast.LENGTH_SHORT).show();
            }
            if (ARROUND.isChecked()) {
                checkBox5 = "ARROUND";
                //Toast.makeText(getApplication(), "value5: " + checkBox + "," + checkBox1 + "," + checkBox2 + "," + checkBox3 + "," + checkBox4, Toast.LENGTH_SHORT).show();
            }
            if (ALONG.isChecked()) {
                checkBox6 = "ALONG";
                //Toast.makeText(getApplication(), "value6 : " + checkBox + "," + checkBox1 + "," + checkBox2 + "," + checkBox3 + "," + checkBox4 + "," + checkBox5, Toast.LENGTH_SHORT).show();
            }

            String way_leaving_status =checkBox1 + "," + checkBox2 + "," + checkBox3 + "," + checkBox4 + "," + checkBox5 + "," + checkBox6;

            way_leaving_status = way_leaving_status.replace(",","");
            way_leaving_status = way_leaving_status.trim();
            String checkBox7 = "";
            String checkBox8 = "";
            String checkBox9 = "";
            String checkBox10 = "";
            String checkBox11 = "";
            String checkBox12 = "";
            String checkBox13 = "";

            GOOD1 =   (CheckBox) findViewById(R.id.chkGOOD1);
            COVERED = (CheckBox) findViewById(R.id.chkCOVERED);
            Danger1 = (CheckBox) findViewById(R.id.chkDanger1);
            M_DAMAGE = (CheckBox) findViewById(R.id.chkMUFFIN_DAMAGE);
            CORRODED = (CheckBox) findViewById(R.id.chkCORRODED);
            WATER_LODGE = (CheckBox) findViewById(R.id.chkWATER_LODGE);
            BACKFILLING = (CheckBox) findViewById(R.id.chkBACK_FILLING);
            if (GOOD1.isChecked()) {
                checkBox7 = "GOOD1";
            }
            //Toast.makeText(getApplication(), "value7 : " + checkBox6, Toast.LENGTH_SHORT).show();
            if (COVERED.isChecked()) {
                checkBox8 = "COVERED";
                //  Toast.makeText(getApplication(), "value8 : " + checkBox6 + "," + checkBox7, Toast.LENGTH_SHORT).show();
            }
            if (Danger1.isChecked()) {
                checkBox9 = "DANGER1";
                //Toast.makeText(getApplication(), "value9: " + checkBox6 + "," + checkBox7 + "," + checkBox8, Toast.LENGTH_SHORT).show();
            }
            if (M_DAMAGE.isChecked()) {
                checkBox10 = "M_DAMAGE";
                //Toast.makeText(getApplication(), "value10 : " + checkBox6 + "," + checkBox7 + "," + checkBox8 + "," + checkBox9, Toast.LENGTH_SHORT).show();
            }
            if (CORRODED.isChecked()) {
                checkBox11 = "CORRODED";
                //Toast.makeText(getApplication(), "value11: " + checkBox6 + "," + checkBox7 + "," + checkBox8 + "," + checkBox9 + "," + checkBox10, Toast.LENGTH_SHORT).show();
            }
            if (WATER_LODGE.isChecked()) {
                checkBox12 = "WATER_LODGE";
                //Toast.makeText(getApplication(), "value12 : " + checkBox6 + "," + checkBox7 + "," + checkBox8 + "," + checkBox9 + "," + checkBox10 + "," + checkBox11, Toast.LENGTH_SHORT).show();
            }
            if (BACKFILLING.isChecked()) {
                checkBox13 = "BACKFILLING";
                //Toast.makeText(getApplication(), "value13 : " + checkBox6 + "," + checkBox7 + "," + checkBox8 + "," + checkBox9 + "," + checkBox10 + "," + checkBox11 + "," + checkBox12, Toast.LENGTH_SHORT).show();
            }

            String Base_concrete_status=checkBox7 + "," + checkBox8 + "," + checkBox9 + "," + checkBox10 + "," + checkBox11 + "," + checkBox12 + "," + checkBox13;
            Base_concrete_status = Base_concrete_status.replace(","," ");
            Base_concrete_status =Base_concrete_status.trim();
            // String wayleavingstatus = WayLeavingStatus.getSelectedItem().toString();
            // String baseconcretestatus = SpinnerBaseConcreteStatus.getSelectedItem().toString();
            String anticlimbingstatus = SpinnerAntiClimbingStatus.getSelectedItem().toString();
            String conductorcondition = SpinnerConductorCondition.getSelectedItem().toString();
            if (conductorcondition.equals("Other")) {
                conductorcondition = ConductorCondition.getText().toString();

            }
            String jumpercondition = SpinnerJumperCondition.getSelectedItem().toString();
            if (jumpercondition.equals("Other")) {
                jumpercondition = JumperCondition1.getText().toString();

            }
            String earthconductorcondition = SpinnerEarthConductorCondition.getSelectedItem().toString();
            if (earthconductorcondition.equals("Other")) {
                earthconductorcondition = EarthConductorCondition1.getText().toString();

            }
            String maintainanceattention = SpinnerMaintainanceAttention.getSelectedItem().toString();
            String legPainting = SpinnerLegPainting.getSelectedItem().toString();
            String hotlinemaint = SpinnerHotLineMaintenance.getSelectedItem().toString();
            String fungusSet = SpinnerFungusSet.getSelectedItem().toString();
            if (fungusSet.equals("Other")) {
                fungusSet = FungusSet.getText().toString();

            }
            String wPinSet = SpinnerWPinSet.getSelectedItem().toString();
            String endFittingSet = SpinnerEndFittingSet.getSelectedItem().toString();
            //  String area = Spinnerarea.getSelectedItem().toString();


            String specialobservations = SpinnerSpObservaion.getSelectedItem().toString();
            if (specialobservations.equals("Other")) {
                specialobservations = SpecialObservations.getText().toString();

            }


            String pinPole1 = NumberOFPinPole1.getText().toString();
            String divType1 = TypeOfSwichingDevice1.getSelectedItem().toString();
            if (divType1.equals("Other")) {
                divType1 = TypeOFSwichingDevice1.getText().toString();

            }
            String pinpole2 = NumberOFPinPole2.getText().toString();
            String divType2 = TypeOfSwichingDevice2.getSelectedItem().toString();
            if (divType2.equals("Other")) {
                divType2 = TypeOFSwichingDevice2.getText().toString();

            }
            String pinpole3 = NumberOFPinPole3.getText().toString();
            String divType3 = TypeOfSwichingDevice3.getSelectedItem().toString();
            if (divType3.equals("Other")) {
                divType3 = TypeOFSwichingDevice3.getText().toString();
            }



            SessionManager obj = new SessionManager(getBaseContext());
            String area = obj.getKeyArea();
            String line = obj.getKeyLine();
            String cycle = obj.getKeyCycle();
            System.out.println("areaarea" + area);
            System.out.println("lineline" + line);
            System.out.println("cyclecycle" + cycle);


            RestTemplate rest = new RestTemplate();

            MmsTxntowermaintenance obje = new MmsTxntowermaintenance();
            MmsTxntowermaintenancePK objePK = new MmsTxntowermaintenancePK();
            MmsTxntowermaintenance objeExisting = null;

            System.out.println("hhhhhhhh");

            //objePK.setTowerid(String.valueOf(Long.parseLong(Tower)));
            objePK.setVisitid(Long.parseLong(cycle));
            objePK.setTowerid(Tower);
            System.out.println("hhhhhhhh Tower:"+ Tower);
            obje.setId(objePK);

            obje.setNooftappings(new BigDecimal(numberoftappings));
            obje.setNoofmissingparts(new BigDecimal(numberofmissingparts));
            //obj.setFlashoversetno(numberOfFlashOverSets);
            obje.setNofflashoversets(new BigDecimal(numberofflashoversets));

            obje.setWayleavestatus(way_leaving_status);
            obje.setBaseconcretestatus(Base_concrete_status);
            obje.setAnticlimbingstatus(anticlimbingstatus);
            obje.setConductorstatus(conductorcondition);
            obje.setJumperstatus(jumpercondition);
            obje.setEarthconductorstatus(earthconductorcondition);
            obje.setAttentionstatus(maintainanceattention);
            obje.setComments(specialobservations);
            obje.setEndfittingset(new BigDecimal(endFittingSet));
            obje.setFungussetno(new BigDecimal(fungusSet));

            hotlinemaint =hotlinemaint.trim();
            if(hotlinemaint.equals("Possible")){
                obje.setHotpossible(new BigDecimal("1"));
            }else{
                obje.setHotpossible(new BigDecimal("0"));

            }
            //obj.setHotLineMnt(spinnerHotLineMaintenance);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date today = Calendar.getInstance().getTime();
            String reportDate = df.format(today);
            Date dateNow=null;
try {
    dateNow = new SimpleDateFormat("yyyy-MM-dd").parse(reportDate);
}catch(Exception e){

}


            //String currentDate = dateNow.format(date);

            obje.setInsDate(dateNow);
            obje.setLegPainting(legPainting);
            //obj.setLudt(dateNow);
            obje.setMaintenancedate(dateNow);
            obje.setPinpole1(new BigDecimal(pinPole1));
            obje.setPinpole2(new BigDecimal(pinpole2));
            obje.setPinpole3(new BigDecimal(pinpole3));
            obje.setSwitchdev1(divType1);
            obje.setSwitchdev2(divType2);
            obje.setSwitchdev3(divType3);
            obje.setWpinset(new BigDecimal(wPinSet));
            obje.setTowerspecial(specialobservations);
            obje.setStatus(new BigDecimal("2"));
            obje.setApprovalLevel("60041ES1");





            *//*obje.setAnticlimbingstatus(anticlimbingstatus);
            obje.setWayleavestatus(way_leaving_status);
            obje.setBaseconcretestatus(Base_concrete_status);
            obje.setConductorstatus(conductorcondition);
            obje.setJumperstatus(jumpercondition);
            obje.setEarthconductorstatus(earthconductorcondition);
            obje.setNofflashoversets(new BigDecimal(numberofflashoversets));
            obje.setAttentionstatus(maintainanceattention);*//*
            //obje.setHotLineMnt(hotlinemaint);
            //obje.setLegPainting(legPainting);
            // obje.setHotpossible(new BigDecimal(hotpossible));
            //obje.setWpinset(new BigDecimal(wPinSet));
            //obje.setNooftappings(new BigDecimal(numberoftappings));
            //System.out.println("missing parts : " +numberofmissingparts);
            //obje.setNoofmissingparts(new BigDecimal(numberofmissingparts));
            //obje.setEndfittingset(new BigDecimal(endFittingSet));
            //obje.setSwitchdev2(divType2);
            //obje.setSwitchdev1(divType1);
            //obje.setSwitchdev3(divType3);
            //obje.setPinpole1(new BigDecimal(pinPole1));
            //obje.setPinpole2(new BigDecimal(pinpole2));
            //obje.setPinpole3(new BigDecimal(pinpole3));
            //obje.setFungussetno(new BigDecimal(fungusSet));



            //String url8 = Util.SRT_URL +"MmsTxtMobile?SaveTowermaintenanceData="+obje;

        *//*    String url4 = Util.SRT_URL + "addTowerMnt/"+cycle+"/"+toweridd+"/"+ numberoftappings + "/" + numberofmissingparts + "/"+ numberofflashoversets + "/"
                    +way_leaving_status+"/" +Base_concrete_status  + "/"+anticlimbingstatus+"/"+conductorcondition+"/"+ jumpercondition+"/"+
                    earthconductorcondition+"/"+maintainanceattention+"/"+legPainting+"/"+ hotlinemaint+"/"+fungusSet+"/"+
                    wPinSet+"/"+endFittingSet+"/"+specialobservations+"/"+area+"/"+line+"/"+pinPole1+"/"+divType1+"/"+pinpole2+"/"+divType2+"/"+pinpole3+"/"+divType3+"/";
       *//*     //  + cycle + "/"  +++
            // + "/" + baseconcretestatus + "/" + anticlimbingstatus + "/" + conductorcondition + "/" +
            //jumpercondition + "/" + earthconductorcondition + "/" + maintainanceattention + "/" + legPainting + "/" +
            // hotlinemaint + "/" + fungusSet + "/" + wPinSet + "/" + endFittingSet + "/" +
            //specialobservations + "/" + area + "/" + line + "/";



            //addTowerMnt/{visitId}/{towerNo}/{numberOfTappings}/{numberOfMissingParts}
            //{numberOfFlashOverSets}/{spinnerLeavingStatus}/{

            //spinnerBaseConcreteStatus}/{spinnerAntiClimbingStatus}/{spinnerConductorCondition}
            //{spinnerJumperCondition}/{spinnerEarthConductorCondition}/{

            // spinnerMaintainanceAttention}/{spinnerLegPainting}/{spinnerHotLineMaintenance}/{fungusSet}/
            //{wPinSet}/{endFittingSet}/{specialObservations}/{area}/{line}


            //String url4 = Util.SRT_URL+"addTowerMntTest/" + toweridd +"/" +numberoftappings;

            //System.out.println("ssss" + url4);
          //  final String url1 = Util.SRT_URL + "MMSManageEquipmentMobile";

           // rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //System.out.println("hiii7896 str" +url4);
           // return rest.getForObject(url4, String.class);
            // String massage = rest.getForObject(url4, String.class);
            // String massage = rest.getForObject(url4,String.class);
            // return null;
            // return rest.getForObject(url4, String.class);

            // String massage = rest.getForObject(url4, String.class);


            // return null;


            final RestTemplate restTemplate = new RestTemplate();
            //final String url1 = Util.SRT_URL + "MMSaddEquipmentMobile/" + reference + "/" + type +"/"+ division + "/" + brnach + "/" + condition + "/" + unit + "/" + idNo + "/" + serialNo + "/" + voltage + "/" + weightofTransformer + "/"+oilWeight+"/"+volumeofOil+"/"+photoTaken+"/"+photoRef+"/"+capacity+"/"+manufactureDate+"/"+manufactureBrand+"/"+manufactureLTL+"/";
            final String url1 = Util.SRT_URL + "addTowerMntMobile";
            //System.out.println(" url1 " + url1);
            System.out.println(" ...........................url1tttttttttttttt ");
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //String massage = restTemplate.getForObject(url1, String.class);
            System.out.println(" url1ttttttttttttttgggggg ");
            String r = restTemplate.postForObject(url1, obje, String.class);
            return r;




        }


        protected void onPostExecute(String results) {
            // ListView Item Click Listener
            System.out.println("results" + results);
            if (results.equalsIgnoreCase("1")) {
                Toast.makeText(AddTowerMaintainance.this, " Tower No not existed ", Toast.LENGTH_LONG).show();
            } else {
                // Toast.makeText(AddTowerMaintainance.this, " Successfully Saved. ", Toast.LENGTH_LONG).show();
            }

        }
    }*/

    private class AddTM extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* ProgDialog= new ProgressDialog(AddTowerMaintainance.this);
//message should be changed according to the requirement
            ProgDialog.setMessage("Please wait...\n(This may take some time, depending on your network connection)");
            ProgDialog.setIndeterminate(false);
            ProgDialog.setTitle(Util.alert_header);
            ProgDialog.setIcon(R.drawable.logo);
            ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            ProgDialog.setCancelable(true);
            ProgDialog.show();*/

        }
        protected String doInBackground(Void... urls) {
            System.out.println(" doInBackground " );
          //  TowerID = (EditText) findViewById(R.id.etTowerID);
            SpinnerTowerID = findViewById(R.id.spinnerTowerNo);
            SpinnerVisitID = findViewById(R.id.SpinnerCycle);
            SpinnerTowerID = findViewById(R.id.spinnerTowerNo);
            NumberOfTappings = findViewById(R.id.etNumberOFTappings);
            NumberOfMissingParts = findViewById(R.id.etMissingParts);
            NumberOfFlashOverSets = findViewById(R.id.etFlashOverSets);
            SpinnerLeavingStatus = findViewById(R.id.SpinnerLeavingStatus);
            SpinnerBaseConcreteStatus = findViewById(R.id.SpinnerBaseConcreteStatus1);
            SpinnerAntiClimbingStatus = findViewById(R.id.SpinnerAntiClimbingStatus1);
            SpinnerConductorCondition = findViewById(R.id.SpinnerConductorCondition);
            SpinnerJumperCondition = findViewById(R.id.SpinnerJumperCondition1);
            SpinnerEarthConductorCondition = findViewById(R.id.SpinnerEarthConductorCondition1);
            SpinnerJumperCondition = findViewById(R.id.SpinnerJumperCondition1);
            SpinnerMaintainanceAttention = findViewById(R.id.SpinnerMaintainanceAttention1);
            SpinnerSpObservaion = findViewById(R.id.spinnerSpecialObserve);
            SpinnerLegPainting = findViewById(R.id.spinnerLegPainting);
            SpinnerHotLineMaintenance = findViewById(R.id.spinnerHotLineMain);
            NumberOFPinPole1 = findViewById(R.id.etNumberOFPinPole1);
            SpinnerFungusSet = findViewById(R.id.etFungusSet);
            Spinnerarea = findViewById(R.id.spinnerArea);

           // SpinnerArea = (Spinner) findViewById(R.id.spinnerArea);
           // String supportArea = SpinnerArea.getSelectedItem().toString();
            SpinnerWPinSet = findViewById(R.id.etWPinSet);
            SpinnerEndFittingSet = findViewById(R.id.etEndFittingSet);
            SpecialObservations = findViewById(R.id.etSpecialObserve);
            ConductorCondition = findViewById(R.id.etConductorCondition);
            JumperCondition1 = findViewById(R.id.etJumperCondition1);
            EarthConductorCondition1 = findViewById(R.id.etEarthConductorCondition1);
            NumberOFPinPole1 = findViewById(R.id.etNumberOFPinPole1);
            TypeOfSwichingDevice1 = findViewById(R.id.etTypeOFSwichingDevice1);
            NumberOFPinPole2 = findViewById(R.id.etNumberOFPinPole2);
            TypeOfSwichingDevice2 = findViewById(R.id.etTypeOFSwichingDevice2);
            NumberOFPinPole3 = findViewById(R.id.etNumberOFPinPole3);
            TypeOfSwichingDevice3 = findViewById(R.id.etTypeOFSwichingDevice3);


           // String toweridd = TowerID.getText().toString();
            //String TowerID= SpinnerTowerNo.getSelectedItem().toString();
            String toweridd= SpinnerTowerID.getSelectedItem().toString();
            System.out.println("toweridd: " + toweridd);

            String numberoftappings = NumberOfTappings.getText().toString();
            System.out.println("numberoftappings: " + numberoftappings);

            String numberofmissingparts = NumberOfMissingParts.getText().toString();
            System.out.println("numberofmissingparts: " + numberofmissingparts);

            String numberofflashoversets = NumberOfFlashOverSets.getSelectedItem().toString();
            System.out.println("numberofflashoversets: " + numberofflashoversets);
            if (numberofflashoversets.equals("Other")) {
                numberofflashoversets = FlashOverSetNumber.getText().toString();
                System.out.println("numberofflashoversets: " + numberofflashoversets);

            }
            String checkBox1 = "";
            String checkBox2 = "";
            String checkBox3 = "";
            String checkBox4 = "";
            String checkBox5 = "";
            String checkBox6 = "";
            GOOD = findViewById(R.id.chkGOOD);
            TOUCH = findViewById(R.id.chkTOUCH);
            Danger = findViewById(R.id.chkDanger);
            CLOSE = findViewById(R.id.chkCLOSE);
            ARROUND = findViewById(R.id.chkARROUND);
            ALONG = findViewById(R.id.chkALONG);

            if (GOOD.isChecked()) {
                checkBox1 = "GOOD";
            }
                //Toast.makeText(getApplication(), "value1 : " + checkBox + "," + checkBox1, Toast.LENGTH_SHORT).show();
                if (TOUCH.isChecked()) {
                    checkBox2 = "TOUCH";
                    //Toast.makeText(getApplication(), "value2 : " + checkBox + "," + checkBox1, Toast.LENGTH_SHORT).show();
                }
                if (Danger.isChecked()) {
                    checkBox3 = "DANGER";
                    //Toast.makeText(getApplication(), "value3 : " + checkBox + "," + checkBox1 + "," + checkBox2, Toast.LENGTH_SHORT).show();
                }
                if (CLOSE.isChecked()) {
                    checkBox4 = "CLOSE";
                    //Toast.makeText(getApplication(), "value4 : " + checkBox + "," + checkBox1 + "," + checkBox2 + "," + checkBox3, Toast.LENGTH_SHORT).show();
                }
                if (ARROUND.isChecked()) {
                    checkBox5 = "AROUND";
                    //Toast.makeText(getApplication(), "value5: " + checkBox + "," + checkBox1 + "," + checkBox2 + "," + checkBox3 + "," + checkBox4, Toast.LENGTH_SHORT).show();
                }
                if (ALONG.isChecked()) {
                    checkBox6 = "ALONG";
                    //Toast.makeText(getApplication(), "value6 : " + checkBox + "," + checkBox1 + "," + checkBox2 + "," + checkBox3 + "," + checkBox4 + "," + checkBox5, Toast.LENGTH_SHORT).show();
                }

                String way_leaving_status = "," + checkBox1 + "," + checkBox2 + "," + checkBox3 + "," + checkBox4 + "," + checkBox5 + "," + checkBox6;
//                System.out.println("###########################################way_leaving_status  AddTM: " + way_leaving_status);
                 way_leaving_status = way_leaving_status.replaceAll(",+", ",");
                 way_leaving_status = way_leaving_status.replaceFirst("," , "");
//                 System.out.println("###########################################way_leaving_status  AddTM replace: " + way_leaving_status);
                way_leaving_status = way_leaving_status.replaceAll(",$"," ");
                way_leaving_status = way_leaving_status.replaceAll(","," , ");
                 way_leaving_status = way_leaving_status.trim();
//                System.out.println("###########################################way_leaving_status  AddTM trim: " + way_leaving_status);

                String checkBox7 = "";
                String checkBox8 = "";
                String checkBox9 = "";
                String checkBox10 = "";
                String checkBox11 = "";
                String checkBox12 = "";
                String checkBox13 = "";

            GOOD1 = findViewById(R.id.chkGOOD1);
            COVERED = findViewById(R.id.chkCOVERED);
            Danger1 = findViewById(R.id.chkDanger1);
            M_DAMAGE = findViewById(R.id.chkMUFFIN_DAMAGE);
            CORRODED = findViewById(R.id.chkCORRODED);
            WATER_LODGE = findViewById(R.id.chkWATER_LODGE);
            BACKFILLING = findViewById(R.id.chkBACK_FILLING);
                if (GOOD1.isChecked()) {
                    checkBox7 = "GOOD";
                }
                    //Toast.makeText(getApplication(), "value7 : " + checkBox6, Toast.LENGTH_SHORT).show();
                    if (COVERED.isChecked()) {
                        checkBox8 = "COVERED";
                        //  Toast.makeText(getApplication(), "value8 : " + checkBox6 + "," + checkBox7, Toast.LENGTH_SHORT).show();
                    }
                    if (Danger1.isChecked()) {
                        checkBox9 = "DANGER";
                        //Toast.makeText(getApplication(), "value9: " + checkBox6 + "," + checkBox7 + "," + checkBox8, Toast.LENGTH_SHORT).show();
                    }
                    if (M_DAMAGE.isChecked()) {
                        checkBox10 = "M_DAMAGE";
                        //Toast.makeText(getApplication(), "value10 : " + checkBox6 + "," + checkBox7 + "," + checkBox8 + "," + checkBox9, Toast.LENGTH_SHORT).show();
                    }
                    if (CORRODED.isChecked()) {
                        checkBox11 = "CORRODED";
                        //Toast.makeText(getApplication(), "value11: " + checkBox6 + "," + checkBox7 + "," + checkBox8 + "," + checkBox9 + "," + checkBox10, Toast.LENGTH_SHORT).show();
                    }
                    if (WATER_LODGE.isChecked()) {
                        checkBox12 = "WATER_LODGE";
                        //Toast.makeText(getApplication(), "value12 : " + checkBox6 + "," + checkBox7 + "," + checkBox8 + "," + checkBox9 + "," + checkBox10 + "," + checkBox11, Toast.LENGTH_SHORT).show();
                    }
                    if (BACKFILLING.isChecked()) {
                        checkBox13 = "BACKFILLING";
                        //Toast.makeText(getApplication(), "value13 : " + checkBox6 + "," + checkBox7 + "," + checkBox8 + "," + checkBox9 + "," + checkBox10 + "," + checkBox11 + "," + checkBox12, Toast.LENGTH_SHORT).show();
                    }

                    String Base_concrete_status= "," + checkBox7 + "," + checkBox8 + "," + checkBox9 + "," + checkBox10 + "," + checkBox11 + "," + checkBox12 + "," + checkBox13;

                    System.out.println("###############################Base_concrete_status AddTM: " + Base_concrete_status);
                    Base_concrete_status = Base_concrete_status.replaceAll(",+", ",");
                    Base_concrete_status = Base_concrete_status.replaceFirst("," , "");
                    System.out.println("###############################Base_concrete_status AddTM replace ,,,: " + Base_concrete_status);
                    Base_concrete_status = Base_concrete_status.replaceAll(",$"," ");
                    Base_concrete_status = Base_concrete_status.replaceAll(","," , ");
                    System.out.println("###############################Base_concrete_status AddTM replace last ,: " + Base_concrete_status);
                    Base_concrete_status =Base_concrete_status.trim();
                    System.out.println("###############################Base_concrete_status AddTM trim: " + Base_concrete_status);


           // String wayleavingstatus = WayLeavingStatus.getSelectedItem().toString();
           // String baseconcretestatus = SpinnerBaseConcreteStatus.getSelectedItem().toString();
            String anticlimbingstatus = SpinnerAntiClimbingStatus.getSelectedItem().toString();
            System.out.println("anticlimbingstatus: " + anticlimbingstatus);
            String conductorcondition = SpinnerConductorCondition.getSelectedItem().toString();
            System.out.println("conductorcondition: " + conductorcondition);
            if (conductorcondition.equals("Other")) {
                conductorcondition = ConductorCondition.getText().toString();

            }
            String jumpercondition = SpinnerJumperCondition.getSelectedItem().toString();
            if (jumpercondition.equals("Other")) {
                jumpercondition = JumperCondition1.getText().toString();

            }
            String earthconductorcondition = SpinnerEarthConductorCondition.getSelectedItem().toString();
            if (earthconductorcondition.equals("Other")) {
                earthconductorcondition = EarthConductorCondition1.getText().toString();

            }
            String maintainanceattention = SpinnerMaintainanceAttention.getSelectedItem().toString();
            System.out.println("maintainanceattention: " + maintainanceattention);
            String legPainting = SpinnerLegPainting.getSelectedItem().toString();
            System.out.println("legPainting: " + legPainting);
            String hotlinemaint = SpinnerHotLineMaintenance.getSelectedItem().toString();
            System.out.println("hotlinemaint: " + hotlinemaint);
            String fungusSet = SpinnerFungusSet.getSelectedItem().toString();
            System.out.println("fungusSet: " + fungusSet);
            if (fungusSet.equals("Other")) {
                fungusSet = FungusSet.getText().toString();

            }
            String wPinSet = SpinnerWPinSet.getSelectedItem().toString();
            System.out.println("wPinSet: " + wPinSet);
            String endFittingSet = SpinnerEndFittingSet.getSelectedItem().toString();
            System.out.println("endFittingSet: " + endFittingSet);
          //  String area = Spinnerarea.getSelectedItem().toString();


            String specialobservations = SpinnerSpObservaion.getSelectedItem().toString();
            System.out.println("specialobservations: " + specialobservations);
            if (specialobservations.equals("Other")) {
                specialobservations = SpecialObservations.getText().toString();

            }


            String pinPole1 = NumberOFPinPole1.getText().toString();
            System.out.println("pinPole1: " + pinPole1);
            String divType1 = TypeOfSwichingDevice1.getSelectedItem().toString();
            System.out.println("divType1: " + divType1);
            if (divType1.equals("Other")) {
                divType1 = TypeOFSwichingDevice1.getText().toString();

            }
            String pinpole2 = NumberOFPinPole2.getText().toString();
            System.out.println("pinpole2: " + pinpole2);
            String divType2 = TypeOfSwichingDevice2.getSelectedItem().toString();
            System.out.println("divType2: " + divType2);
            if (divType2.equals("Other")) {
                divType2 = TypeOFSwichingDevice2.getText().toString();

            }
            String pinpole3 = NumberOFPinPole3.getText().toString();
            System.out.println("pinpole3: " + pinpole3);
            String divType3 = TypeOfSwichingDevice3.getSelectedItem().toString();
            System.out.println("divType3: " + divType3);
            if (divType3.equals("Other")) {
                divType3 = TypeOFSwichingDevice3.getText().toString();
                }



            SessionManager obj = new SessionManager(getBaseContext());
            String area = obj.getKeyArea();
            String line = obj.getKeyLine();
            String cycle = obj.getKeyCycle();
            System.out.println("areaarea" + area);
            System.out.println("lineline" + line);
            System.out.println("cyclecycle" + cycle);




           //MmsTxntowermaintenance obje = new MmsTxntowermaintenance();
           //MmsTxntowermaintenancePK objePK = new MmsTxntowermaintenancePK();
           // MmsTxntowermaintenance objeExisting = null;

            //System.out.println("hhhhhhhh");

            //objePK.setTowerid(String.valueOf(Long.parseLong(Tower)));
            //objePK.setVisitid(Long.parseLong(cycle));
            //objePK.setTowerid(Tower);
            //obje.setId(objePK);


            //obje.setAnticlimbingstatus(anticlimbingstatus);
            //obje.setWayleavestatus(wayleavingstatus);
            //obje.setBaseconcretestatus(baseconcretestatus);
            //obje.setConductorstatus(conductorcondition);
            //obje.setJumperstatus(jumpercondition);
            //obje.setEarthconductorstatus(earthconductorcondition);
            //obje.setFlashoversetno(numberofflashoversets);
            //obje.setAttentionstatus(maintainanceattention);
            //obje.setHotLineMnt(hotlinemaint);
            //obje.setLegPainting(legPainting);
           // obje.setHotpossible(new BigDecimal(hotpossible));
            //obje.setWpinset(new BigDecimal(wPinSet));
            //obje.setNooftappings(new BigDecimal(numberoftappings));
            //System.out.println("missing parts : " +numberofmissingparts);
            //obje.setNoofmissingparts(new BigDecimal(numberofmissingparts));
            //obje.setEndfittingset(new BigDecimal(endFittingSet));
            //obje.setSwitchdev2(divType2);
            //obje.setSwitchdev1(divType1);
            //obje.setSwitchdev3(divType3);
            //obje.setPinpole1(new BigDecimal(pinPole1));
            //obje.setPinpole2(new BigDecimal(pinpole2));
            //obje.setPinpole3(new BigDecimal(pinpole3));
            //obje.setFungussetno(new BigDecimal(fungusSet));

            //String url8 = Util.SRT_URL +"MmsTxtMobile?SaveTowermaintenanceData="+obje;




            //get deptId from session manager
            SessionManager objS = new SessionManager(getBaseContext());
            String deptId = objS.getPhmBranch();
            System.out.println("PhmBranch" + deptId.trim());
            deptId = deptId.trim();

            //String towerID = addSupport.getTowerIDByTowerNo(towerNo);
            String towerID = SpinnerTowerNo.getSelectedItem().toString();
            //String realTOwerId = spinnerMap.get(towerID);
            //System.out.println("realTOwerId : " + realTOwerId);

            System.out.println("towerID true or false : " + towerID.equalsIgnoreCase(""));
            System.out.println("cccccccccccccctowerID: " + towerID);
            if(towerID.equalsIgnoreCase("")){
                System.out.println("towerID : " + towerID);
                return "1";
            }
            System.out.println("towerID " + towerID);


            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date today = Calendar.getInstance().getTime();
            String reportDate = df.format(today);
            Date dateNow = null;

            DateFormat df2 = new SimpleDateFormat("HH-MM-SS");
            String insTime = df2.format(today);
            Date insTimeDate = null;

            try {
                insTimeDate = new SimpleDateFormat("HH-MM-SS").parse(insTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                dateNow = new SimpleDateFormat("yyyy-MM-dd").parse(reportDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            //set values to MmsTxntowermaintenance object
            MmsTxntowermaintenance objMmsTxntowermaintenance = new MmsTxntowermaintenance();
            MmsTxntowermaintenancePK objPKMmsTxntowermaintenance = new MmsTxntowermaintenancePK();
            MmsTxntowermaintenance objExisting = null;

            //objExisting = towerTxtMaintenance.findByID(objPKMmsTxntowermaintenance);

            //String visitId = String.valueOf(objPKMmsTxntowermaintenance.getVisitid());
           // System.out.println("visitId: " + visitId);

            objPKMmsTxntowermaintenance.setVisitid(new Long(cycle));
            objPKMmsTxntowermaintenance.setTowerid(new Long(Tower));
            //objMmsTxntowermaintenance.setTowerNo(Tower);  //set tower ID
            objMmsTxntowermaintenance.setId(objPKMmsTxntowermaintenance);
            //objMmsTxntowermaintenance.setTowerNo(toweridd);
            objMmsTxntowermaintenance.setPhmBranch(deptId);
            objMmsTxntowermaintenance.setNooftappings(new BigDecimal(numberoftappings));
            objMmsTxntowermaintenance.setNoofmissingparts(new BigDecimal(numberofmissingparts));
            objMmsTxntowermaintenance.setFlashoversetno(numberofflashoversets);
            objMmsTxntowermaintenance.setNofflashoversets(new BigDecimal(numberofflashoversets));
            objMmsTxntowermaintenance.setWayleavestatus(way_leaving_status);
            objMmsTxntowermaintenance.setBaseconcretestatus(Base_concrete_status);
            objMmsTxntowermaintenance.setAnticlimbingstatus(anticlimbingstatus);
            objMmsTxntowermaintenance.setConductorstatus(conductorcondition);
            objMmsTxntowermaintenance.setJumperstatus(jumpercondition);
            objMmsTxntowermaintenance.setEarthconductorstatus(earthconductorcondition);
            objMmsTxntowermaintenance.setAttentionstatus(maintainanceattention);
            objMmsTxntowermaintenance.setComments(specialobservations);
            objMmsTxntowermaintenance.setEndfittingset(new BigDecimal(endFittingSet));
            objMmsTxntowermaintenance.setFungussetno(new BigDecimal(fungusSet));

            if(hotlinemaint.equals("Possible")){
                objMmsTxntowermaintenance.setHotpossible(new BigDecimal("1"));
            }else{
                objMmsTxntowermaintenance.setHotpossible(new BigDecimal("0"));
            }

            //objMmsTxntowermaintenance.setInsDate(dateNow);
            //objMmsTxntowermaintenance.setInsTime(String.valueOf(insTimeDate));
            objMmsTxntowermaintenance.setLegPainting(legPainting);
            objMmsTxntowermaintenance.setMaintenancedate(dateNow);
            objMmsTxntowermaintenance.setPinpole1(new BigDecimal(pinPole1));
            objMmsTxntowermaintenance.setPinpole2(new BigDecimal(pinpole2));
            objMmsTxntowermaintenance.setPinpole3(new BigDecimal(pinpole3));
            objMmsTxntowermaintenance.setSwitchdev1(divType1);
            objMmsTxntowermaintenance.setSwitchdev2(divType2);
            objMmsTxntowermaintenance.setSwitchdev3(divType3);
            objMmsTxntowermaintenance.setWpinset(new BigDecimal(wPinSet));
            objMmsTxntowermaintenance.setTowerspecial(specialobservations);
            objMmsTxntowermaintenance.setStatus(new BigDecimal(2));
            objMmsTxntowermaintenance.setApprovalLevel("60041ES1");
            objMmsTxntowermaintenance.setCycle(cycle);
            //objMmsTxntowermaintenance.setHotLineMnt(hotlinemaint);

            System.out.println("ID:****************************** " + objMmsTxntowermaintenance.getId());
            System.out.println("Tower:****************************** " + Tower);
            System.out.println("TowerID:****************************** " + objPKMmsTxntowermaintenance.getTowerid());
            System.out.println("tappings:****************************** " + objMmsTxntowermaintenance.getNooftappings());
            System.out.println("missing:****************************** " + objMmsTxntowermaintenance.getNoofmissingparts());
            System.out.println("flashoversetno:****************************** " + objMmsTxntowermaintenance.getFlashoversetno());
            System.out.println("noflashoverset:****************************** " + objMmsTxntowermaintenance.getNofflashoversets());
            System.out.println("wayleaving:****************************** " + objMmsTxntowermaintenance.getWayleavestatus());
            System.out.println("base concreate:****************************** " + objMmsTxntowermaintenance.getBaseconcretestatus());
            System.out.println("anti climbing:****************************** " + objMmsTxntowermaintenance.getAnticlimbingstatus());
            System.out.println("conductor status:****************************** " + objMmsTxntowermaintenance.getConductorstatus());
            System.out.println("jumper:****************************** " + objMmsTxntowermaintenance.getJumperstatus());
            System.out.println("earth conductor:****************************** " + objMmsTxntowermaintenance.getEarthconductorstatus());
            System.out.println("Attention:****************************** " + objMmsTxntowermaintenance.getAttentionstatus());
            System.out.println("comments:****************************** " + objMmsTxntowermaintenance.getComments());
            System.out.println("end fitting:****************************** " + objMmsTxntowermaintenance.getEndfittingset());
            System.out.println("fungus:****************************** " + objMmsTxntowermaintenance.getFungussetno());
            System.out.println("hot possible:****************************** " + objMmsTxntowermaintenance.getHotpossible());
            //System.out.println("insdate:****************************** " + objMmsTxntowermaintenance.getInsDate());
            //System.out.println("instime:****************************** " + objMmsTxntowermaintenance.getInsTime());
            System.out.println("leg painting:****************************** " + objMmsTxntowermaintenance.getLegPainting());
            System.out.println("maintain date:****************************** " + objMmsTxntowermaintenance.getMaintenancedate());
            System.out.println("pinpole1:****************************** " + objMmsTxntowermaintenance.getPinpole1());
            System.out.println("pinpole2:****************************** " + objMmsTxntowermaintenance.getPinpole2());
            System.out.println("pinpole3:****************************** " + objMmsTxntowermaintenance.getPinpole3());
            System.out.println("switch1:****************************** " + objMmsTxntowermaintenance.getSwitchdev1());
            System.out.println("switch2:****************************** " + objMmsTxntowermaintenance.getSwitchdev3());
            System.out.println("switch3:****************************** " + objMmsTxntowermaintenance.getSwitchdev3());
            System.out.println("wpin:****************************** " + objMmsTxntowermaintenance.getWpinset());
            System.out.println("special:****************************** " + objMmsTxntowermaintenance.getTowerspecial());
            System.out.println("approval:****************************** " + objMmsTxntowermaintenance.getApprovalLevel());
            System.out.println("cycle:****************************** " + objMmsTxntowermaintenance.getCycle());


            /*if(objExisting == null){
                System.out.println("new");
                String resultobj = towerTxtMaintenance.AddTowerMaintainanceData(objMmsTxntowermaintenance);
                return "NEW";
            } else{
                System.out.println("update");
                String resultobj1 = towerTxtMaintenance.update(objMmsTxntowermaintenance);
                return "UPDATE";
            }*/



            final RestTemplate restTemplate = new RestTemplate();
            final String url1 = Util.SRT_URL + "MMSAddMntMobile";
            System.out.println(" url1 " + url1);
            System.out.println(" ...........................url1tttttttttttttt " );
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println(" url1ttttttttttttttgggggg " );
            String objReturn = restTemplate.postForObject(url1, objMmsTxntowermaintenance, String.class);
            System.out.println("objReturn: " + objReturn);
            return objReturn;


            /*RestTemplate rest = new RestTemplate();
            String url4 = Util.SRT_URL + "addTowerMntNew/"+cycle+"/"+toweridd+"/"+ numberoftappings + "/" + numberofmissingparts + "/"+ numberofflashoversets + "/"
                  +way_leaving_status+"/" +Base_concrete_status  + "/"+anticlimbingstatus+"/"+conductorcondition+"/"+ jumpercondition+"/"+
                    earthconductorcondition+"/"+maintainanceattention+"/"+legPainting+"/"+ hotlinemaint+"/"+fungusSet+"/"+
                   wPinSet+"/"+endFittingSet+"/"+specialobservations+"/"+area+"/"+line+"/"+pinPole1+"/"+divType1+"/"+pinpole2+"/"+divType2+"/"+pinpole3+"/"+divType3+"/"+deptId+"/";


            System.out.println("ssss" + url4);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("hiii7896 str" +url4);
            return rest.getForObject(url4, String.class);*/

        }


        protected void onPostExecute(String results) {
            // ListView Item Click Listener

            System.out.println("results" + results);
            if (results.equalsIgnoreCase("1")) {
                Toast.makeText(AddTowerMaintainance.this, " Tower No not existed ", Toast.LENGTH_LONG).show();
            } else {
               // Toast.makeText(AddTowerMaintainance.this, " Successfully Saved. ", Toast.LENGTH_LONG).show();
            }
            ProgDialog.dismiss();
        }
    }
    //   public class MainActivity extends AppCompatActivity {


    private class SaveTMFromExcel extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgDialog= new ProgressDialog(AddTowerMaintainance.this);
//message should be changed according to the requirement
            ProgDialog.setMessage("Please wait...\n(This may take some time, depending on your network connection)");
            ProgDialog.setIndeterminate(false);
            ProgDialog.setTitle(Util.alert_header);
            ProgDialog.setIcon(R.drawable.logo);
            ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            ProgDialog.setCancelable(true);
            ProgDialog.show();

        }


        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            System.out.println(" SaveFromExcel1 " );
            MmsTxntowermaintenance objNew = new MmsTxntowermaintenance();
            System.out.println(" SaveFromExcel2 " );

            //objNew.setTowerNo("HY A KH A 14");
            objNew.setNooftappings(new BigDecimal("7"));
            objNew.setNoofmissingparts(new BigDecimal("3"));
            objNew.setFlashoversetno("5");
            objNew.setWayleavestatus("Touch");
            objNew.setBaseconcretestatus("Covered");
            objNew.setAnticlimbingstatus("Good");
            objNew.setConductorstatus("Good");
            objNew.setJumperstatus("Good");
            objNew.setEarthconductorstatus("Good");
            objNew.setAttentionstatus("Not Urgent");
            objNew.setLegPainting("7");
            objNew.setHotLineMnt("None");
            objNew.setFungussetno(new BigDecimal("3"));
            objNew.setWpinset(new BigDecimal("9"));
            objNew.setEndfittingset(new BigDecimal("7"));
            objNew.setTowerspecial("0");
            objNew.setPinpole1(new BigDecimal("6"));
            objNew.setSwitchdev1("LBS");
            objNew.setPinpole2(new BigDecimal("6"));
            objNew.setSwitchdev2("ABS");
            objNew.setPinpole3(new BigDecimal("8"));
            objNew.setSwitchdev3("LBS");
            objNew.setMaintenancedate(new Date(2018-12-06));


           /* SessionManager objS = new SessionManager(getBaseContext());
            String PhmBranch = objS.getPhmBranch();
            System.out.println("PhmBranch" + PhmBranch.trim());
            PhmBranch =PhmBranch.trim();*/

            SessionManager obj = new SessionManager(getBaseContext());
            String area = obj.getKeyArea();
            String line = obj.getKeyLine();
            String cycle = obj.getKeyCycle();


             List<MmsTxntowermaintenance> objs = readTM();
          //    List<MmsTxntowermaintenance> objs = new ArrayList<MmsTxntowermaintenance>(1);
           //  objs.add(objNew);
            System.out.println(" SaveFromExcel3 " );
               if(objs != null){
                   System.out.println("Inside objs.............................");
                   int size = objs.size();

                   for(int i = 1;i<size;i++){


                    MmsTxntowermaintenance TM = objs.get(i);

                    /*final RestTemplate restTemplate = new RestTemplate();
                    String url4 = Util.SRT_URL + "addTowerMnt/"+cycle+"/"+TM.getTowerNo()+"/"+ TM.getNooftappings() + "/" + TM.getNoofmissingparts() + "/"+ TM.getFlashoversetno() + "/"
                            +TM.getWayleavestatus()+"/" +TM.getBaseconcretestatus()  + "/"+TM.getAnticlimbingstatus()+"/"+TM.getConductorstatus()+"/"+ TM.getJumperstatus()+"/"+
                            TM.getEarthconductorstatus()+"/"+TM.getAttentionstatus()+"/"+TM.getLegPainting()+"/"+ TM.getHotLineMnt()+"/"+TM.getFungussetno()+"/"+
                            TM.getWpinset()+"/"+TM.getEndfittingset()+"/"+TM.getTowerspecial()+"/"+area+"/"+line+"/"+TM.getPinpole1()+"/"+TM.getSwitchdev1()+"/"+TM.getPinpole2()+"/"+TM.getSwitchdev2()+"/"+TM.getPinpole3()+"/"+TM.getSwitchdev3()+"/";
//
//   Toast.makeText(getApplication(),"url4: " + url4, Toast.LENGTH_SHORT).show();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                     result= restTemplate.getForObject(url4, String.class);
                     System.out.println("hiii5555 yyyyyyyyyhhjj" + url4);*/

                    final RestTemplate restTemplate = new RestTemplate();
                    final String url1 = Util.SRT_URL + "MMSAddMntMobile";
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    String objReturn = restTemplate.postForObject(url1, TM, String.class);



                }
            } else{
                System.out.println(" .list is empty " );
                result = " .list is empty ";

            }
            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            ProgDialog.dismiss();
            try {

                System.out.println("hiii5555 yyyyyyyyy" + result);

            } catch (Exception e) {
                System.out.println(" error " + e.getMessage());
            }


        }

    }



}








