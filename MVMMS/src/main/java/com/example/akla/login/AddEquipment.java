package com.example.akla.login;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AddEquipment extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    EditText ReferenceNo;
    EditText EquipmentId;
    EditText Division;
    EditText Brnach;
    Spinner SpinnerCondition;
    Spinner SpinnerSeal;
    Spinner SpinnerPrimary;
    Spinner SpinnerType;
    Spinner SpinnerDivision;
    EditText Unit;
    EditText IdNo;
    EditText SerialNo;
    EditText Voltage;
    EditText WeightofTransformer;
    EditText OilWeight;
    EditText VolumeofOil;
    EditText PhotoTaken;
    EditText PhotoRef;
    EditText Capacity;
    EditText ManufactureDate;
    EditText ManufactureBrand;
    EditText ManufactureLTL;
    Button btn_date;
    EditText in_date;

    TextView GPSLongitude;
    TextView GPSLatitude;
    EditText LocationDescription;
    Spinner SpinnerTypeofLocated;
    Spinner SpinnerMounting;

    Spinner SpinnerOilLeaksPresent;
    Spinner SpinnerCorrosion;
    Spinner SpinnerBurnMarks;
    Spinner Spinnerattention;
    Spinner SpinnerEarthingConnection;
    Spinner SpinnerBreather;
    Spinner SpinnerOverload;
    Spinner SpinnerLighting;

    EditText SamplingPort;
    EditText Extracted_from_top;
    EditText SamplingLogicSatisfied;
    EditText SampleNumber;
    EditText SampleDate;
    EditText PCBTestResults;
    EditText PCBTestdate;
    EditText EPF_Numbers_of_the_Test_Group;
    EditText Remarks;
    EditText EPF_Numbers_of_the_group;


    TextView a;
    TextView b;
    double lat;
    double lon;
    LocationManager locationManager;


    String[] valuesDivision = new String[]{};

    HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();

    String division;

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);


        btn_date = findViewById(R.id.btn_date);
        in_date = findViewById(R.id.in_date);
        btn_date.setOnClickListener(AddEquipment.this);

      //  new loadDiv().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        statusCheck();


        GPSLatitude = findViewById(R.id.etLatitude);
        GPSLongitude = findViewById(R.id.etLongitude);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


      /*  SpinnerDivision = (Spinner) findViewById(R.id.SpinnerDivision);
        SpinnerDivision.setPrompt("Select Division");
        SpinnerDivision.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String iddivision = spinnerMap.get(parent.getSelectedItemPosition());
                division = iddivision;
                //  Toast.makeText(AddLine.this,"Name" +name,Toast.LENGTH_LONG).show();
                // Toast.makeText(AddLine.this,"Type"+idtype,Toast.LENGTH_LONG).show();


            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        Button ButtonSave = findViewById(R.id.btnEquipment);
        ButtonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                {
                    // Do something in response to button click
//                Toast.makeText(AddTowerMaintainance.this, "alertMessage",
//                        Toast.LENGTH_LONG).show();
                    ReferenceNo = findViewById(R.id.etReference);
                    EquipmentId = findViewById(R.id.etEquipmentId);
                    //   Division = (EditText) findViewById(R.id.etDivision);
                    Brnach = findViewById(R.id.etBrnach);
                    SpinnerCondition = findViewById(R.id.SpinnerCondition);
                    SpinnerPrimary = findViewById(R.id.SpinnerPrimary);
                    SpinnerSeal = findViewById(R.id.SpinnerSeal);
                    SpinnerType = findViewById(R.id.SpinnerType);
                    SpinnerDivision = findViewById(R.id.SpinnerDivision);
                    Unit = findViewById(R.id.etUnit);
                    IdNo = findViewById(R.id.etIdNo);
                    SerialNo = findViewById(R.id.etSerialNo);
                    Voltage = findViewById(R.id.etVoltage);
                    WeightofTransformer = findViewById(R.id.etWeightofTransformer);
                    OilWeight = findViewById(R.id.etOilWeight);
                    VolumeofOil = findViewById(R.id.etVolumeofOil);
                    PhotoTaken = findViewById(R.id.etPhotoTaken);
                    PhotoRef = findViewById(R.id.etPhotoRef);
                    Capacity = findViewById(R.id.etCapacity);
                    ManufactureBrand = findViewById(R.id.etManuBrand);
                    ManufactureDate = findViewById(R.id.in_date);
                    ManufactureLTL = findViewById(R.id.etManuLTL);

                    GPSLongitude = findViewById(R.id.etLongitude);
                    GPSLatitude = findViewById(R.id.etLatitude);
                    LocationDescription = findViewById(R.id.etLocationDescription);
                    SpinnerTypeofLocated = findViewById(R.id.SpinnerTypeofLocated);
                    SpinnerMounting = findViewById(R.id.SpinnerMounting);

                    SpinnerOilLeaksPresent = findViewById(R.id.SpinnerOilLeaksPresent);
                    SpinnerCorrosion = findViewById(R.id.SpinnerCorrosion);
                    SpinnerBurnMarks = findViewById(R.id.SpinnerBurnMarks);
                    Spinnerattention = findViewById(R.id.Spinnerattention);
                    SpinnerEarthingConnection = findViewById(R.id.SpinnerEarthingConnection);
                    SpinnerBreather = findViewById(R.id.SpinnerBreather);
                    SpinnerOverload = findViewById(R.id.SpinnerOverload);
                    SpinnerLighting = findViewById(R.id.SpinnerLighting);

                    SamplingPort = findViewById(R.id.etSamplingPort);
                    Extracted_from_top = findViewById(R.id.etExtracted_from_top);
                    SamplingLogicSatisfied = findViewById(R.id.etSamplingLogicSatisfied);
                    SampleNumber = findViewById(R.id.etSampleNumber);
                    SampleDate = findViewById(R.id.etSampleDate);
                    PCBTestResults = findViewById(R.id.etPCBTestResults);
                    PCBTestdate = findViewById(R.id.etPCBTestdate);
                    EPF_Numbers_of_the_Test_Group = findViewById(R.id.etEPF_Numbers_of_the_Test_Group);
                    Remarks = findViewById(R.id.etRemarks);
                    EPF_Numbers_of_the_group = findViewById(R.id.etEPF_Numbers_of_the_group);
                    SpinnerType = findViewById(R.id.SpinnerType);

                    if (ReferenceNo.getText().toString().trim().equals("")) {
                        ReferenceNo.setError("Should add a Reference!");
                    } else if (Brnach.getText().toString().trim().equals("")) {
                        Brnach.setError("Should add a Brnach!");
                    } else if (EquipmentId.getText().toString().trim().equals("")) {
                        EquipmentId.setError("Should add a Equipment Id!");
                    } else if (Unit.getText().toString().trim().equals("")) {
                        Unit.setError("Should add a Unit!");
                    } else if (IdNo.getText().toString().trim().equals("")) {
                        IdNo.setError("Should add a Id No!");
                    } else if (SerialNo.getText().toString().trim().equals("")) {
                        SerialNo.setError("Should add a Serial No!");
                    } else if (Voltage.getText().toString().trim().equals("")) {
                        Voltage.setError("Should add a Voltage!");
                    } else if (WeightofTransformer.getText().toString().trim().equals("")) {
                        WeightofTransformer.setError("Should add a Weight of Transformer!");
                    } else if (OilWeight.getText().toString().trim().equals("")) {
                        OilWeight.setError("Should add a Oil Weight!");
                    } else if (VolumeofOil.getText().toString().trim().equals("")) {
                        VolumeofOil.setError("Should add a Volume of Oil!");
                    } else if (PhotoTaken.getText().toString().trim().equals("")) {
                        PhotoTaken.setError("Should add a Photo Taken!");
                    } else if (PhotoRef.getText().toString().trim().equals("")) {
                        PhotoRef.setError("Should add a Photo Ref!");
                    } else if (Capacity.getText().toString().trim().equals("")) {
                        Capacity.setError("Should add a Capacity!");
                    } else if (ManufactureBrand.getText().toString().trim().equals("")) {
                        ManufactureBrand.setError("Should add a Capacity!");
                    } else if (ManufactureDate.getText().toString().trim().equals("")) {
                        ManufactureDate.setError("Should add a Capacity!");
                    } else if (ManufactureLTL.getText().toString().trim().equals("")) {
                        ManufactureLTL.setError("Should add a Capacity!");
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddEquipment.this);
                        builder.setCancelable(true);
                        builder.setMessage("Do you want to save the Equipment?");
                        builder.setTitle("Save Equipment");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new SaveEquipment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        Toast.makeText(getApplicationContext(), "Successfully!", Toast.LENGTH_SHORT).show();

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


        Button ButtonSearch = findViewById(R.id.btnSearch);
        ButtonSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                {

                   new GetEquipment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }


            }


        });


    }

    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.apphome) {
            Intent intent = new Intent(AddEquipment.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddEquipment.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddEquipment.this, AddSupport.class);
            startActivity(intent);


        } else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddEquipment.this, TM2.class);
            startActivity(intent);


        } else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddEquipment.this, AddEquipment.class);
            startActivity(intent);


        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddEquipment.this, Login.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(AddEquipment.this, GetNearByTower.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddEquipment.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(AddEquipment.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(AddEquipment.this, AddTransformers.class);
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
                        Intent intent = new Intent(AddEquipment.this, MainActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onClick(View v) {
        if (v == btn_date) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(AddEquipment.this,
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

    private class SaveEquipment extends AsyncTask<String, Void, String> {
        @TargetApi(Build.VERSION_CODES.N)
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected String doInBackground(String... urls) {


            System.out.println(" doInBackground ");

            ReferenceNo = findViewById(R.id.etReference);
            EquipmentId = findViewById(R.id.etEquipmentId);
            //Division = (EditText) findViewById(R.id.etDivision);
            Brnach = findViewById(R.id.etBrnach);
            SpinnerCondition = findViewById(R.id.SpinnerCondition);
            SpinnerType = findViewById(R.id.SpinnerType);
            SpinnerPrimary = findViewById(R.id.SpinnerPrimary);
            SpinnerSeal = findViewById(R.id.SpinnerSeal);
            SpinnerDivision = findViewById(R.id.SpinnerDivision);
            Unit = findViewById(R.id.etUnit);
            IdNo = findViewById(R.id.etIdNo);
            SerialNo = findViewById(R.id.etSerialNo);
            Voltage = findViewById(R.id.etVoltage);
            WeightofTransformer = findViewById(R.id.etWeightofTransformer);
            OilWeight = findViewById(R.id.etOilWeight);
            VolumeofOil = findViewById(R.id.etVolumeofOil);
            PhotoTaken = findViewById(R.id.etPhotoTaken);
            PhotoRef = findViewById(R.id.etPhotoRef);
            Capacity = findViewById(R.id.etCapacity);
            ManufactureBrand = findViewById(R.id.etManuBrand);
            ManufactureDate = findViewById(R.id.in_date);
            ManufactureLTL = findViewById(R.id.etManuLTL);

            String reference = ReferenceNo.getText().toString();
            String equipmentId = EquipmentId.getText().toString();
           /* String division = SpinnerDivision.getSelectedItem().toString();
            String Division = "";
            if (division.equalsIgnoreCase("Generation Transformer")) {
                Division = "GT";
            } else if (division.equalsIgnoreCase("Transmission Transformer")) {
                Division = "TT";
            } else if (division.equalsIgnoreCase("Distribution Transformer ")) {
                Division = "DT";
            } else if (division.equalsIgnoreCase("Instrument Transformer  ")) {
                Division = "IT";
            } else if (division.equalsIgnoreCase("Capacitor and Circuit Breakers ")) {
                Division = "CC";
            }*/

            String type = SpinnerType.getSelectedItem().toString();

            String brnach = Brnach.getText().toString();
            String condition = SpinnerCondition.getSelectedItem().toString();
            String primary = SpinnerPrimary.getSelectedItem().toString();
            String seal = SpinnerSeal.getSelectedItem().toString();
            String division = SpinnerDivision.getSelectedItem().toString();

            String Type = "";
            if (type.equalsIgnoreCase("Transformer")) {
                Type = "TR";
            } else if (type.equalsIgnoreCase("Single Barrel")) {
                Type = "SB";
            } else if (type.equalsIgnoreCase("Multi Barrel")) {
                Type = "MB";
            }

            String unit = Unit.getText().toString();
            String idNo = IdNo.getText().toString();
            String serialNo = SerialNo.getText().toString();
            String voltage = Voltage.getText().toString();
            String weightofTransformer = WeightofTransformer.getText().toString();
            String oilWeight = OilWeight.getText().toString();
            String volumeofOil = VolumeofOil.getText().toString();
            String photoTaken = PhotoTaken.getText().toString();
            String photoRef = PhotoRef.getText().toString();
            String capacity = Capacity.getText().toString();
            String manufactureBrand = ManufactureBrand.getText().toString();
            String manufactureDate = ManufactureDate.getText().toString();
            String manufactureLTL = ManufactureLTL.getText().toString();


            PcbModel pcbModel = new PcbModel();
            PcbEquipment eqiObj = new PcbEquipment();
            //  eqiObj.setEquipmentId(equipmentId);
            eqiObj.setReferenceNo(reference);
            eqiObj.setDivison(division);
            eqiObj.setBranch(brnach);
            eqiObj.setCondition(condition);
            eqiObj.setSeriaType(seal);
            eqiObj.setUnit(unit);
            eqiObj.setEquipmentId(idNo);
            eqiObj.setSerialNo(serialNo);
            eqiObj.setVoltage(new BigDecimal(voltage));
            eqiObj.setWeightTransformer(new BigDecimal(weightofTransformer));
            eqiObj.setOilWeight(new BigDecimal(oilWeight));
            eqiObj.setVolumeOfOil(volumeofOil);
            eqiObj.setPhotoTaken(photoTaken);
            eqiObj.setPhotoRef(photoRef);
            eqiObj.setCapacity(new BigDecimal(capacity));
            eqiObj.setManufactureBrand(manufactureBrand);
            eqiObj.setManufactureLtl(manufactureLTL);
            eqiObj.setPrimarySub(primary);
            eqiObj.setType(Type);

            LocationDescription = findViewById(R.id.etLocationDescription);
            SpinnerTypeofLocated = findViewById(R.id.SpinnerTypeofLocated);
            SpinnerMounting = findViewById(R.id.SpinnerMounting);
            GPSLongitude = findViewById(R.id.etLongitude);
            GPSLatitude = findViewById(R.id.etLatitude);

            String locationDescription = LocationDescription.getText().toString();
            String typeofLocated = SpinnerTypeofLocated.getSelectedItem().toString();
            String mounting = SpinnerMounting.getSelectedItem().toString();
            final String gpslongitude = GPSLongitude.getText().toString();
            final String gpsLatitude = GPSLatitude.getText().toString();


            PcbLocation locObj = new PcbLocation();
            locObj.setGpsLatitude(gpsLatitude);
            locObj.setGpsLongitude(gpslongitude);
            locObj.setLocationDescription(locationDescription);
            locObj.setTypeOfLocated(typeofLocated);
            locObj.setMounting(mounting);


            SpinnerOilLeaksPresent = findViewById(R.id.SpinnerOilLeaksPresent);
            SpinnerCorrosion = findViewById(R.id.SpinnerCorrosion);
            SpinnerBurnMarks = findViewById(R.id.SpinnerBurnMarks);
            Spinnerattention = findViewById(R.id.Spinnerattention);
            SpinnerEarthingConnection = findViewById(R.id.SpinnerEarthingConnection);
            SpinnerBreather = findViewById(R.id.SpinnerBreather);
            SpinnerOverload = findViewById(R.id.SpinnerOverload);
            SpinnerLighting = findViewById(R.id.SpinnerLighting);



            String oilLeaksPresent = SpinnerOilLeaksPresent.getSelectedItem().toString();
            String corrosion = SpinnerCorrosion.getSelectedItem().toString();
            String burnMarks  = SpinnerBurnMarks.getSelectedItem().toString();
            String attention = Spinnerattention.getSelectedItem().toString();
            String earthingConnection = SpinnerEarthingConnection.getSelectedItem().toString();
            String breather = SpinnerBreather.getSelectedItem().toString();
            String overload = SpinnerOverload.getSelectedItem().toString();
            String lighting = SpinnerLighting.getSelectedItem().toString();

            PcbCondition conObj = new PcbCondition();
            conObj.setOilLeaksPresent(oilLeaksPresent);
            conObj.setCorrosion(corrosion);
            conObj.setBurnMask(burnMarks);
            conObj.setTerminalAttention(attention);
            conObj.setEarthConnection(earthingConnection);
            conObj.setBreatherIsGoodConnection(breather);
            conObj.setOverloadPresent(overload);
            conObj.setLightingArrestersDone(lighting);


            SamplingPort = findViewById(R.id.etSamplingPort);
            Extracted_from_top = findViewById(R.id.etExtracted_from_top);
            SamplingLogicSatisfied = findViewById(R.id.etSamplingLogicSatisfied);
            SampleNumber = findViewById(R.id.etSampleNumber);
            SampleDate = findViewById(R.id.etSampleDate);
            PCBTestResults = findViewById(R.id.etPCBTestResults);
            PCBTestdate = findViewById(R.id.etPCBTestdate);
            EPF_Numbers_of_the_Test_Group = findViewById(R.id.etEPF_Numbers_of_the_Test_Group);
            Remarks = findViewById(R.id.etRemarks);
            EPF_Numbers_of_the_group = findViewById(R.id.etEPF_Numbers_of_the_group);


            String samplingPort = SamplingPort.getText().toString();
            String extracted_from_top = Extracted_from_top.getText().toString();
            String samplingLogicSatisfied = SamplingLogicSatisfied.getText().toString();
            String sampleNumber = SampleNumber.getText().toString();
            String sampleDate = SampleDate.getText().toString();
            String pCBTestResults = PCBTestResults.getText().toString();
            String pCBTestdate = PCBTestdate.getText().toString();
            String ePF_Numbers_of_the_Test_Group = EPF_Numbers_of_the_Test_Group.getText().toString();
            String remarks = Remarks.getText().toString();
            String ePF_Numbers_of_the_group = EPF_Numbers_of_the_group.getText().toString();


            PcbSample samObj = new PcbSample();
            samObj.setSamplingPort(samplingPort);
            samObj.setExtractedTop(extracted_from_top);
            samObj.setSampleSatisified(samplingLogicSatisfied);
            samObj.setSamplingNu(sampleNumber);
          //  samObj.setSampleDate(sampleDate);
            samObj.setPcbTestResults(pCBTestResults);
            //samObj.setPcbTestDate(pCBTestdate);
            samObj.setEpfNoTestGroup(ePF_Numbers_of_the_Test_Group);
            samObj.setRemarks(remarks);
            samObj.setEpfNoTestGroup(ePF_Numbers_of_the_group);




            //eqiObj.setManufactureDate(new Date(manufactureDate));


            Date date = null;

            try {
                DateFormat format = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
                date = format.parse(String.valueOf(ManufactureDate));
            } catch (Exception e) {

            }

            eqiObj.setManufactureDate(date);




            pcbModel.setPcbEquipment(eqiObj);
            pcbModel.setPcbLocation(locObj);
            pcbModel.setPcbCondition(conObj);
            pcbModel.setPcbSample(samObj);


            //eqiObj.setType("TR");

            final RestTemplate restTemplate = new RestTemplate();
            //final String url1 = Util.SRT_URL + "MMSaddEquipmentMobile/" + reference + "/" + type +"/"+ division + "/" + brnach + "/" + condition + "/" + unit + "/" + idNo + "/" + serialNo + "/" + voltage + "/" + weightofTransformer + "/"+oilWeight+"/"+volumeofOil+"/"+photoTaken+"/"+photoRef+"/"+capacity+"/"+manufactureDate+"/"+manufactureBrand+"/"+manufactureLTL+"/";
            final String url1 = Util.SRT_URL + "MMSManageEquipmentMobile";
            //System.out.println(" url1 " + url1);
            System.out.println(" ...........................url1tttttttttttttt ");
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //String massage = restTemplate.getForObject(url1, String.class);
            System.out.println(" url1ttttttttttttttgggggg ");
            String obj = restTemplate.postForObject(url1, pcbModel, String.class);
            return obj;


        }

        // @Override
        protected void onPostExecute(String result) {
            try {

                System.out.println("hiii5555 yyyyyyyyy" + result);
                // DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                // InputStream is = new ByteArrayInputStream(result.getBytes("UTF-8"));
                //  Document dom = builder.parse(is);
                //  final String token = dom.getElementsByTagName("string").item(0).getChildNodes().item(0).getTextContent();

                //android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(AddComplain.this);
                // builder1.setIcon(R.drawable.logo);
                // builder1.setMessage("Complaint successfully saved.Your reference number is: "+ token)
                //   .setCancelable(false)
                //   .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                // public void onClick(DialogInterface dialog, int id) {
                //do things
                //      }
                // });
                //android.app.AlertDialog alert = builder1.create();
                // alert.show();


//
            } catch (Exception e) {
                System.out.println(" error " + e.getMessage());
            }


        }


    }



    private class GetEquipment extends AsyncTask<PcbModel, Void, PcbModel> {
        @TargetApi(Build.VERSION_CODES.N)
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected PcbModel doInBackground(PcbModel... urls) {
            System.out.println(" doInBackground ");

           // ReferenceNo = (EditText) findViewById(R.id.etReference);
            EquipmentId = findViewById(R.id.etEquipmentId);
            //Division = (EditText) findViewById(R.id.etDivision);
            /*Brnach = (EditText) findViewById(R.id.etBrnach);
            SpinnerCondition = (Spinner) findViewById(R.id.SpinnerCondition);
            SpinnerType = (Spinner) findViewById(R.id.SpinnerType);
            SpinnerPrimary = (Spinner) findViewById(R.id.SpinnerPrimary);
            SpinnerSeal = (Spinner) findViewById(R.id.SpinnerSeal);
            SpinnerDivision = (Spinner) findViewById(R.id.SpinnerDivision);
            Unit = (EditText) findViewById(R.id.etUnit);
            IdNo = (EditText) findViewById(R.id.etIdNo);
            SerialNo = (EditText) findViewById(R.id.etSerialNo);
            Voltage = (EditText) findViewById(R.id.etVoltage);
            WeightofTransformer = (EditText) findViewById(R.id.etWeightofTransformer);
            OilWeight = (EditText) findViewById(R.id.etOilWeight);
            VolumeofOil = (EditText) findViewById(R.id.etVolumeofOil);
            PhotoTaken = (EditText) findViewById(R.id.etPhotoTaken);
            PhotoRef = (EditText) findViewById(R.id.etPhotoRef);
            Capacity = (EditText) findViewById(R.id.etCapacity);
            ManufactureBrand = (EditText) findViewById(R.id.etManuBrand);
            ManufactureDate = (EditText) findViewById(R.id.in_date);
            ManufactureLTL = (EditText) findViewById(R.id.etManuLTL);

            String reference = ReferenceNo.getText().toString();*/
            String equipmentId = EquipmentId.getText().toString();
            /*String division = SpinnerDivision.getSelectedItem().toString();
            String Division = "";
            if (division.equalsIgnoreCase("Generation Transformer")) {
                Division = "GT";
            } else if (division.equalsIgnoreCase("Transmission Transformer")) {
                Division = "TT";
            } else if (division.equalsIgnoreCase("Distribution Transformer ")) {
                Division = "DT";
            } else if (division.equalsIgnoreCase("Instrument Transformer  ")) {
                Division = "IT";
            } else if (division.equalsIgnoreCase("Capacitor and Circuit Breakers ")) {
                Division = "CC";
            }

            String brnach = Brnach.getText().toString();
            String condition = SpinnerCondition.getSelectedItem().toString();
            String primary = SpinnerPrimary.getSelectedItem().toString();
            String seal = SpinnerSeal.getSelectedItem().toString();
            String type = SpinnerType.getSelectedItem().toString();

            String Type = "";
            if (type.equalsIgnoreCase("Transformer")) {
                Type = "TR";
            } else if (type.equalsIgnoreCase("Single Barrel")) {
                Type = "SB";
            } else if (type.equalsIgnoreCase("Multi Barrel")) {
                Type = "MB";
            }

            String unit = Unit.getText().toString();
            String idNo = IdNo.getText().toString();
            String serialNo = SerialNo.getText().toString();
            String voltage = Voltage.getText().toString();
            String weightofTransformer = WeightofTransformer.getText().toString();
            String oilWeight = OilWeight.getText().toString();
            String volumeofOil = VolumeofOil.getText().toString();
            String photoTaken = PhotoTaken.getText().toString();
            String photoRef = PhotoRef.getText().toString();
            String capacity = Capacity.getText().toString();
            String manufactureBrand = ManufactureBrand.getText().toString();
            String manufactureDate = ManufactureDate.getText().toString();
            String manufactureLTL = ManufactureLTL.getText().toString();


            PcbEquipment eqiObj = new PcbEquipment();
            eqiObj.setEquipmentId(equipmentId);
            eqiObj.setReferenceNo(reference);
            eqiObj.setDivison(Division);
            eqiObj.setBranch(brnach);
            eqiObj.setCondition(condition);
            eqiObj.setSeriaType(seal);
            eqiObj.setUnit(unit);
            eqiObj.setEquipmentId(idNo);
            eqiObj.setSerialNo(new BigDecimal(serialNo));
            eqiObj.setVoltage(new BigDecimal(voltage));
            eqiObj.setWeightTransformer(new BigDecimal(weightofTransformer));
            eqiObj.setOilWeight(new BigDecimal(oilWeight));
            eqiObj.setVolumeOfOil(volumeofOil);
            eqiObj.setPhotoTaken(photoTaken);
            eqiObj.setPhotoRef(photoRef);
            eqiObj.setCapacity(new BigDecimal(capacity));
            eqiObj.setManufactureBrand(manufactureBrand);
            eqiObj.setManufactureLtl(manufactureLTL);
            eqiObj.setPrimarySub(primary);
            eqiObj.setType(Type);
            //eqiObj.setManufactureDate(new Date(manufactureDate));


            Date date = null;

            try {
                DateFormat format = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
                date = format.parse(String.valueOf(ManufactureDate));
            } catch (Exception e) {

            }

            eqiObj.setManufactureDate(date);


            //eqiObj.setType("TR");
*/
            final RestTemplate restTemplate = new RestTemplate();
            //final String url1 = Util.SRT_URL + "MMSaddEquipmentMobile/" + reference + "/" + type +"/"+ division + "/" + brnach + "/" + condition + "/" + unit + "/" + idNo + "/" + serialNo + "/" + voltage + "/" + weightofTransformer + "/"+oilWeight+"/"+volumeofOil+"/"+photoTaken+"/"+photoRef+"/"+capacity+"/"+manufactureDate+"/"+manufactureBrand+"/"+manufactureLTL+"/";
            final String url1 = Util.SRT_URL + "GetEquipmentMobile/" +equipmentId;
            final String url2 = Util.SRT_URL + "GetLocationMobile/" +equipmentId;
            final String url3 = Util.SRT_URL + "GetSampleMobile/" +equipmentId;
            final String url4 = Util.SRT_URL + "GetConditionMobile/" +equipmentId;
            //System.out.println(" url1 " + url1);
            System.out.println(" ...........................url1tttttttttttttt " + url1 +" hhhhh: "+equipmentId);
            System.out.println(" ...........................url1tttttttttttttt " + url2 +" hhhhh: "+equipmentId);
            System.out.println(" ...........................url1tttttttttttttt " + url3 +" hhhhh: "+equipmentId);
            System.out.println(" ...........................url1tttttttttttttt " + url4 +" hhhhh: "+equipmentId);
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //String massage = restTemplate.getForObject(url1, String.class);
             System.out.println(" url1ttttttttttttttgggggg ");
             PcbModel pcbModel = new PcbModel();
             pcbModel = restTemplate.getForObject(url1, PcbModel.class);
             pcbModel = restTemplate.getForObject(url2, PcbModel.class);
             pcbModel = restTemplate.getForObject(url3, PcbModel.class);
             pcbModel = restTemplate.getForObject(url4, PcbModel.class);
             System.out.println(" url1ttttttttttttttgggggg " +pcbModel);

             return pcbModel;



        }

        // @Override
        protected void onPostExecute(PcbModel pcbModel) {
            try {

                System.out.println("hiii5555 yyyyyyyyy" + pcbModel);


                EquipmentId.setText(pcbModel.getPcbEquipment().getEquipmentId());
              //  EquipmentId.setText(pcbModel.getPcbLocation().getEquipmentId());
                EquipmentId.setText(pcbModel.getPcbCondition().getEquipmentId());
                EquipmentId.setText(pcbModel.getPcbSample().getEquipmentId());


                ReferenceNo = findViewById(R.id.etReference);
                Brnach = findViewById(R.id.etBrnach);
                SpinnerCondition = findViewById(R.id.SpinnerCondition);
                SpinnerType = findViewById(R.id.SpinnerType);
                SpinnerPrimary = findViewById(R.id.SpinnerPrimary);
                SpinnerSeal = findViewById(R.id.SpinnerSeal);
                SpinnerDivision = findViewById(R.id.SpinnerDivision);
                Unit = findViewById(R.id.etUnit);
                IdNo = findViewById(R.id.etIdNo);
                SerialNo = findViewById(R.id.etSerialNo);
                Voltage = findViewById(R.id.etVoltage);
                WeightofTransformer = findViewById(R.id.etWeightofTransformer);
                OilWeight = findViewById(R.id.etOilWeight);
                VolumeofOil = findViewById(R.id.etVolumeofOil);
                PhotoTaken = findViewById(R.id.etPhotoTaken);
                PhotoRef = findViewById(R.id.etPhotoRef);
                Capacity = findViewById(R.id.etCapacity);
                ManufactureBrand = findViewById(R.id.etManuBrand);
                ManufactureDate = findViewById(R.id.in_date);
                ManufactureLTL = findViewById(R.id.etManuLTL);

               /* GPSLongitude = (TextView) findViewById(R.id.etLongitude);
                GPSLatitude = (TextView) findViewById(R.id.etLatitude);
                LocationDescription = (EditText) findViewById(R.id.etLocationDescription);
                SpinnerTypeofLocated = (Spinner) findViewById(R.id.SpinnerTypeofLocated);
                SpinnerMounting=(Spinner)findViewById(R.id.SpinnerMounting);*/

                SpinnerOilLeaksPresent = findViewById(R.id.SpinnerOilLeaksPresent);
                SpinnerCorrosion = findViewById(R.id.SpinnerCorrosion);
                SpinnerBurnMarks = findViewById(R.id.SpinnerBurnMarks);
                Spinnerattention = findViewById(R.id.Spinnerattention);
                SpinnerEarthingConnection = findViewById(R.id.SpinnerEarthingConnection);
                SpinnerBreather = findViewById(R.id.SpinnerBreather);
                SpinnerOverload = findViewById(R.id.SpinnerOverload);
                SpinnerLighting = findViewById(R.id.SpinnerLighting);

                SamplingPort = findViewById(R.id.etSamplingPort);
                Extracted_from_top = findViewById(R.id.etExtracted_from_top);
                SamplingLogicSatisfied = findViewById(R.id.etSamplingLogicSatisfied);
                SampleNumber = findViewById(R.id.etSampleNumber);
                SampleDate = findViewById(R.id.etSampleDate);
                PCBTestResults = findViewById(R.id.etPCBTestResults);
                PCBTestdate = findViewById(R.id.etPCBTestdate);
                EPF_Numbers_of_the_Test_Group = findViewById(R.id.etEPF_Numbers_of_the_Test_Group);
                Remarks = findViewById(R.id.etRemarks);
                EPF_Numbers_of_the_group = findViewById(R.id.etEPF_Numbers_of_the_group);


                ReferenceNo.setText(pcbModel.getPcbEquipment().getReferenceNo());
                Brnach.setText(pcbModel.getPcbEquipment().getBranch());
                Unit.setText(pcbModel.getPcbEquipment().getUnit());
                IdNo.setText(pcbModel.getPcbEquipment().getEquipmentId());
                PhotoTaken.setText(pcbModel.getPcbEquipment().getPhotoTaken());
                PhotoRef.setText(pcbModel.getPcbEquipment().getPhotoRef());
                ManufactureBrand.setText(pcbModel.getPcbEquipment().getManufactureBrand());
                ManufactureLTL.setText(pcbModel.getPcbEquipment().getManufactureLtl());
                Voltage.setText(String.valueOf(pcbModel.getPcbEquipment().getVoltage()));
                SerialNo.setText(String.valueOf(pcbModel.getPcbEquipment().getSerialNo()));
                Capacity.setText(String.valueOf(pcbModel.getPcbEquipment().getCapacity()));
                OilWeight.setText(String.valueOf(pcbModel.getPcbEquipment().getOilWeight()));
                WeightofTransformer.setText(String.valueOf(pcbModel.getPcbEquipment().getWeightTransformer()));
                VolumeofOil.setText(String.valueOf(pcbModel.getPcbEquipment().getVolumeOfOil()));


                /*GPSLatitude.setText(pcbModel.getPcbLocation().getGpsLatitude());
                GPSLongitude.setText(pcbModel.getPcbLocation().getGpsLongitude());
                LocationDescription.setText(pcbModel.getPcbLocation().getLocationDescription());*/

                SamplingPort.setText(pcbModel.getPcbSample().getSamplingPort());
                Extracted_from_top.setText(pcbModel.getPcbSample().getExtractedTop());
                SamplingLogicSatisfied.setText(pcbModel.getPcbSample().getSampleSatisified());
                SampleNumber.setText(pcbModel.getPcbSample().getSamplingNu());
                PCBTestResults.setText(pcbModel.getPcbSample().getPcbTestResults());
                EPF_Numbers_of_the_group.setText(pcbModel.getPcbSample().getEpfNoGroup());
                Remarks.setText(pcbModel.getPcbSample().getRemarks());
                EPF_Numbers_of_the_Test_Group.setText(pcbModel.getPcbSample().getEpfNoTestGroup());






                // DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                // InputStream is = new ByteArrayInputStream(result.getBytes("UTF-8"));
                //  Document dom = builder.parse(is);
                //  final String token = dom.getElementsByTagName("string").item(0).getChildNodes().item(0).getTextContent();

                //android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(AddComplain.this);
                // builder1.setIcon(R.drawable.logo);
                // builder1.setMessage("Complaint successfully saved.Your reference number is: "+ token)
                //   .setCancelable(false)
                //   .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                // public void onClick(DialogInterface dialog, int id) {
                //do things
                //      }
                // });
                //android.app.AlertDialog alert = builder1.create();
                // alert.show();


//
            } catch (Exception e) {
                System.out.println(" error " + e.getMessage());
            }


        }



    }




  /*  public void onClick(View v) {

        if (v == btn_date) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(AddEquipment.this,
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
    */


    private class loadDivision extends AsyncTask<PCB_Division[], Void, PCB_Division[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        protected PCB_Division[] doInBackground(PCB_Division[]... urls) {
            RestTemplate rest = new RestTemplate();
            String url8 = Util.SRT_URL+"getAllDivision";

            System.out.println("ssss" +url8);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            PCB_Division[] list = rest.getForObject(url8,PCB_Division[].class);
            return list;
           // return List<PCBDivision>null;
        }


        protected void onPostExecute(PCB_Division[] results) {
            // ListView Item Click Listener
            System.out.println("results" +results);
            System.out.println("results" +results.length);
            String[] division;
            valuesDivision = new String[results.length];
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
                    PCB_Division  obj = results[c];
                    valuesDivision[c] = obj.getName();
                    spinnerMap.put(c,obj.getId());


                }
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesDivision);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerDivision = findViewById(R.id.SpinnerDivision);
            SpinnerDivision.setAdapter(adapterNs);
//





            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }



    private class loadDiv extends AsyncTask<String, Void,PCB_Division[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        protected PCB_Division[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url8 = Util.SRT_URL+"getAllDivision";

            System.out.println("ssss" +url8);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url8,PCB_Division[].class);
            //return List<MmsAddArea>null;
        }


        protected void onPostExecute(PCB_Division[] results) {
            // ListView Item Click Listener
            System.out.println("results" +results);
            System.out.println("results" +results.length);
            String[] area;
          //  valuesType = new String[results.length];
            //  String[] spinnerArray = new String[Province_ID.size()];
            //  HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
            //  for (int i = 0; i < Province_ID.size(); i++)
            //  {
            //      spinnerMap.put(i,Province_ID.get(i));
            //      spinnerArray[i] = Province_NAME.get(i);
            //  }


//
            /*if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    MmsAddlinetype  obj = results[c];
                    valuesType[c] = obj.getLineType();
                    spinnerMap1.put(c,obj.getId());


                }
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesType);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerType = (Spinner)findViewById(R.id.SpinnerType);
            SpinnerType.setAdapter(adapterNs);
//
*/




            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }


}
