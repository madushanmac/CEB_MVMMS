package com.example.akla.login;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class AutoRecloserFragmentEdit extends Fragment {

    //For get date
    Button btn_date;
    EditText in_date;
    private int mYear, mMonth, mDay;

    Button btn_date2;
    EditText in_date2;
    private int mYear2, mMonth2, mDay2;

    Button btn_date3;
    EditText in_date3;
    private int mYear3, mMonth3, mDay3;

    Button btn_date4;
    EditText in_date4;
    private int mYear4, mMonth4, mDay4;

    //load spinners
    Spinner SpinnerMounting;
    ArrayAdapter<CharSequence> adapter1;

    Spinner SpinnerBrandname;
    ArrayAdapter<CharSequence> adapter2;

    Spinner SpinnerArrangement;
    ArrayAdapter<CharSequence> adapter3;

    Spinner SpinnerInsulationMedium;
    ArrayAdapter<CharSequence> adapter4;

    Spinner SpinnerLocation;
    ArrayAdapter<CharSequence> adapter5;

    Spinner SpinnerConnectedBus;
    ArrayAdapter<CharSequence> adapter6;

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
    HashMap<Integer, Long> SpinnerMapGantry = new HashMap<Integer, Long>();

    //load Feeder
    Spinner SpinnerFeeder;
    Long gantry;
    String valuesFeeder[] = new String[]{};
    HashMap<Integer,String> SpinnerMapFeeder = new HashMap<Integer, String>();

    //load Switch
    Spinner SpinnerSwitch;
    String feeder;
    String valuesSwitch[] = new String[]{};
    HashMap<Integer,String> SpinnerMapSwitch = new HashMap<Integer, String>();

    String switchid;
    String nextSwitchId;
    MmsAddswitch objSwitch;

    // Define for variebles for send DB
    Spinner Province;
    Spinner Area;
    Spinner Line;
    Spinner Gantry;
    Spinner Feeder;
    EditText Code;
    EditText Name;
    Spinner Mounting;
    Spinner ConnectedBus;
    Spinner BrandName;
    EditText DateOfManu1;
    EditText CommissioningDate1;
    EditText ModelNumber1;
    EditText SerialNumber1;
    EditText NoOfBatteries;
    EditText BatteryCapacity;
    EditText BatteryVoltage;
    EditText AuxiliaryDCVoltage;
    EditText OneBatteryVoltage;
    EditText RemoteOperation;
    Spinner Arangement;
    EditText DateOfManu2;
    EditText CommissioningDate2;
    EditText ModelNumber2;
    EditText SerialNumber2;
    EditText Rating;
    EditText SCC;
    Spinner InsulationMedium;

    TextView Latitude;
    TextView Longitude;

    private View inflation;

    private FusedLocationProviderClient client;

    private ProgressDialog ProgDialog;

    public AutoRecloserFragmentEdit(){

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        System.out.println("ARC EDIT:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::ARCEDIT");
        System.out.println(" >>>> onCreateView called for AutoRecloserFragment");

        inflation = inflater.inflate(R.layout.fragment_auto_recloser_edit,container,false);;

        Province = inflation.findViewById(R.id.spnProvince);
        Area = inflation.findViewById(R.id.spnArea);
        //Line = inflation.findViewById(R.id.spnLine);
        Gantry = inflation.findViewById(R.id.spnGantryCode);
        Feeder = inflation.findViewById(R.id.spnFeeder);
        Code = inflation.findViewById(R.id.txt_code);
        Name = inflation.findViewById(R.id.txt_name);
        Mounting = inflation.findViewById(R.id.spn_mounting);
        ConnectedBus = inflation.findViewById(R.id.spn_connected_bus);
        BrandName = inflation.findViewById(R.id.spn_brand_name);
        DateOfManu1 = inflation.findViewById(R.id.in_date);
        CommissioningDate1 = inflation.findViewById(R.id.in_date2);
        ModelNumber1 = inflation.findViewById(R.id.txt_model_number);
        SerialNumber1 = inflation.findViewById(R.id.txt_serial_number);
        NoOfBatteries = inflation.findViewById(R.id.txt_no_of_batteries);
        BatteryCapacity = inflation.findViewById(R.id.txt_battery_capacity);
        BatteryVoltage = inflation.findViewById(R.id.txt_battery_voltage);
        AuxiliaryDCVoltage = inflation.findViewById(R.id.txt_auxiliary_dc_voltage);
        OneBatteryVoltage = inflation.findViewById(R.id.txt_one_battery_voltage);
        RemoteOperation = inflation.findViewById(R.id.txt_remote_operation);
        Arangement = inflation.findViewById(R.id.spn_arangement);
        DateOfManu2 = inflation.findViewById(R.id.in_date_1);
        CommissioningDate2 = inflation.findViewById(R.id.in_date_2);
        ModelNumber2 = inflation.findViewById(R.id.txt_model_number_2);
        SerialNumber2 = inflation.findViewById(R.id.txt_serial_number_2);
        Rating = inflation.findViewById(R.id.txt_rating);
        SCC = inflation.findViewById(R.id.txt_scc);
        InsulationMedium = inflation.findViewById(R.id.spn_insulation_medium);
        Latitude = inflation.findViewById(R.id.latitude);
        Longitude = inflation.findViewById(R.id.longitude);

        //spinner mounting
        SpinnerMounting =(Spinner)inflation.findViewById(R.id.spn_mounting);
        adapter1 =ArrayAdapter.createFromResource(inflation.getContext(),R.array.mounting,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerMounting.setAdapter(adapter1);

        //spinner brandname
        SpinnerBrandname =(Spinner)inflation.findViewById(R.id.spn_brand_name);
        adapter2 =ArrayAdapter.createFromResource(inflation.getContext(),R.array.brand_name,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerBrandname.setAdapter(adapter2);

        //spinner arrangement
        SpinnerArrangement =(Spinner)inflation.findViewById(R.id.spn_arangement);
        adapter3 =ArrayAdapter.createFromResource(inflation.getContext(),R.array.arangement,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerArrangement.setAdapter(adapter3);

        //spinner insulationmedium
        SpinnerInsulationMedium =(Spinner)inflation.findViewById(R.id.spn_insulation_medium);
        adapter4 =ArrayAdapter.createFromResource(inflation.getContext(),R.array.insulation_medium,android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulationMedium.setAdapter(adapter4);

        //spinner Location
        SpinnerLocation =(Spinner)inflation.findViewById(R.id.spn_location);
        adapter5 =ArrayAdapter.createFromResource(inflation.getContext(),R.array.locationAutoRecloser,android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerLocation.setAdapter(adapter5);

        //spinner Conected Bus
        SpinnerConnectedBus =(Spinner)inflation.findViewById(R.id.spn_connected_bus);
        adapter6 =ArrayAdapter.createFromResource(inflation.getContext(),R.array.connectedBus,android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerConnectedBus.setAdapter(adapter6);


        //Date picking \\\\\\\\\\\\ R O W D Y ///////////2012 12 17 ////////////////
        //ControlPanel -> Date of Manufacture
        btn_date = (Button)inflation.findViewById(R.id.btn_date);
        in_date = (EditText) inflation.findViewById(R.id.in_date);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btn_date) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(inflation.getContext(),
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

        //ControlPanel -> Commissioning date
        btn_date2 = (Button)inflation.findViewById(R.id.btn_date2);
        in_date2 = (EditText) inflation.findViewById(R.id.in_date2);
        btn_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btn_date2) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear2 = c.get(Calendar.YEAR);
                    mMonth2 = c.get(Calendar.MONTH);
                    mDay2 = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(inflation.getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    in_date2.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                }
                            }, mYear2, mMonth2, mDay2);
                    datePickerDialog.show();
                }
            }
        });

        //Switch Gear -> Date of Manufacture
        btn_date3 = (Button)inflation.findViewById(R.id.btn_date_1);
        in_date3 = (EditText) inflation.findViewById(R.id.in_date_1);
        btn_date3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btn_date3) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear3 = c.get(Calendar.YEAR);
                    mMonth3 = c.get(Calendar.MONTH);
                    mDay3 = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(inflation.getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    in_date3.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                }
                            }, mYear3, mMonth3, mDay3);
                    datePickerDialog.show();
                }
            }
        });

        //Switch Gear -> Commissioning date
        btn_date4 = (Button)inflation.findViewById(R.id.btn_date_2);
        in_date4 = (EditText) inflation.findViewById(R.id.in_date_2);
        btn_date4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btn_date4) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear4 = c.get(Calendar.YEAR);
                    mMonth4 = c.get(Calendar.MONTH);
                    mDay4 = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(inflation.getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    in_date4.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                }
                            }, mYear4, mMonth4, mDay4);
                    datePickerDialog.show();
                }
            }
        });


        if(!isConnected(AutoRecloserFragmentEdit.this)){
            //readExcel();
            //readExcelLoadtype();
            //readExcelConType();
        }else{
            new AutoRecloserFragmentEdit.loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Area -- Area load by Province
        SpinnerProvince = inflation.findViewById(R.id.spnProvince);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province =idprovince;

                if(!isConnected(AutoRecloserFragmentEdit.this)){
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new AutoRecloserFragmentEdit.loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Line -- Line load by Area
        SpinnerArea = inflation.findViewById(R.id.spnArea);
        SpinnerArea.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idarea = SpinnerMapArea.get(parent.getSelectedItemPosition());
                area = idarea;

                if(!isConnected(AutoRecloserFragmentEdit.this)){
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new AutoRecloserFragmentEdit.loadGantrybyArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        //Spinner Gantry -- Gantry load by Line
//        SpinnerLine = inflation.findViewById(R.id.spnLine);
//        SpinnerLine.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = parent.getSelectedItem().toString();
//                String name = parent.getSelectedItem().toString();
//                String idline = SpinnerMapLine.get(parent.getSelectedItemPosition());
//                line = idline;
//
//                if(!isConnected(AutoRecloserFragmentEdit.this)){
//                    //readExcelLine();
//                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
//
//                }else {
//                    //new AutoRecloserFragmentEdit.loadGantryByLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                }
//            }
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        //Spinner Feeder -- feeder load by Gantry
        SpinnerGantry = inflation.findViewById(R.id.spnGantryCode);
        SpinnerGantry.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                Long idgantry = SpinnerMapGantry.get(parent.getSelectedItemPosition());
                gantry = idgantry;

                if(!isConnected(AutoRecloserFragmentEdit.this)){
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new AutoRecloserFragmentEdit.loadFeederByGantry().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Feeder -- switches load by Feeder
        SpinnerFeeder = inflation.findViewById(R.id.spnFeeder);
        SpinnerFeeder.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String  idgantry = SpinnerMapFeeder.get(parent.getSelectedItemPosition());
                if(idgantry != null){
                    feeder = idgantry.toString();
                    System.out.println(idgantry);
                }

                if(!isConnected(AutoRecloserFragmentEdit.this)){
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new AutoRecloserFragmentEdit.loadSwitchesByFeeder().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new AutoRecloserFragmentEdit.getNextSwitchID().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Gantry object load by gantry code
        SpinnerSwitch = inflation.findViewById(R.id.spnSwitch);
        SpinnerSwitch.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idswitch = SpinnerMapSwitch.get(parent.getSelectedItemPosition());
                switchid = idswitch.toString();

                if(!isConnected(AutoRecloserFragmentEdit.this)){
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new AutoRecloserFragmentEdit.getSwitchObj().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //////////////////////// R O W D Y //////////////// G P S Location //////////////////////////////////////
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this.getActivity());


        Button button = inflation.findViewById(R.id.bLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(AutoRecloserFragmentEdit.this.getActivity(),ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(AutoRecloserFragmentEdit.this.getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){

                            TextView latitude = inflation.findViewById(R.id.latitude);
                            latitude.setText(String.valueOf((double) location.getLatitude()));
                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX: "+ location.getLatitude());

                            TextView longitude = inflation.findViewById(R.id.longitude);
                            longitude.setText(String.valueOf((double) location.getLongitude()));
                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX: "+ location.getLongitude());
                        }
                    }
                });
            }
        });
        //////////////// G E T   D E T A I L S ///////////////////////////////////////////////
        Button ButtonDetails = inflation.findViewById(R.id.detailsbtn);
        ButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
//                    System.out.println("XXXXXXXXXXXXXXXXXXX"+objSwitch.getSwitchCode());
                    if(objSwitch !=null) {

                        if (objSwitch.getSwitchCode() != null) {
                            Code.setText(objSwitch.getSwitchCode());
                        }

                        if (objSwitch.getSwitchName() != null) {
                            Name.setText(objSwitch.getSwitchName());
                        }

                        if (objSwitch.getCpDateManufature() != null) {
                            CommissioningDate1.setText((objSwitch.getCpDateCommitioning().getYear() - 100 + 2000) + "-"
                                    + (objSwitch.getCpDateCommitioning().getMonth() + 1) + "-"
                                    + objSwitch.getCpDateCommitioning().getDate());
                        }

                        if (objSwitch.getCpModelNumber() != null) {
                            ModelNumber1.setText(objSwitch.getCpModelNumber());
                        }

                        if (objSwitch.getCpSerialNo() != null) {
                            SerialNumber1.setText(objSwitch.getCpSerialNo());
                        }

                        if (objSwitch.getCpNoOfBatteries() != null) {
                            NoOfBatteries.setText(objSwitch.getCpNoOfBatteries().toString());
                        }

                        if (objSwitch.getCpBatteryCapacity() != null) {
                            BatteryCapacity.setText(objSwitch.getCpBatteryCapacity().toString());
                        }

                        if (objSwitch.getCpBatteryValtage() != null) {
                            BatteryVoltage.setText(objSwitch.getCpBatteryValtage().toString());
                        }

                        if (objSwitch.getAuxiliryDcVolatge() != null) {
                            AuxiliaryDCVoltage.setText(objSwitch.getAuxiliryDcVolatge().toString());
                        }

                        if (objSwitch.getOneBatteryVoltage() != null) {
                            OneBatteryVoltage.setText(objSwitch.getOneBatteryVoltage().toString());
                        }

                        if (objSwitch.getCpRemoteOperation() != null) {
                            RemoteOperation.setText(objSwitch.getCpRemoteOperation());
                        }

                        if (objSwitch.getSgDateManufature() != null) {
                            DateOfManu2.setText((objSwitch.getSgDateManufature().getYear() - 100 + 2000) + "-"
                                    + (objSwitch.getSgDateManufature().getMonth() + 1) + "-"
                                    + objSwitch.getSgDateManufature().getDate());
                        }

                        if (objSwitch.getSgDateCommitioning() != null) {
                            CommissioningDate2.setText((objSwitch.getSgDateCommitioning().getYear() - 100 + 2000) + "-"
                                    + (objSwitch.getSgDateCommitioning().getMonth() + 1) + "-"
                                    + objSwitch.getSgDateCommitioning().getDate());
                        }

                        if (objSwitch.getSgModelNumber() != null) {
                            ModelNumber2.setText(objSwitch.getSgModelNumber());
                        }

                        if (objSwitch.getSgSerialNo() != null) {
                            SerialNumber2.setText(objSwitch.getSgSerialNo());
                        }

                        if (objSwitch.getSgRating() != null) {
                            Rating.setText(objSwitch.getSgRating().toString());
                        }

                        if (objSwitch.getSgSccLevelOfEquipment() != null) {
                            SCC.setText(objSwitch.getSgSccLevelOfEquipment().toString());
                        }

                        if (objSwitch.getGpsLatitude() != null) {
                            Latitude.setText(objSwitch.getGpsLatitude().toString());
                        }

                        if (objSwitch.getGpsLongitude() != null) {
                            Longitude.setText(objSwitch.getGpsLongitude().toString());
                        }
                    }

                }
            }
        });
        /////////////////////////Save to Database ///////////////////////////////////////////////////////////////////
        //////////////////////// Edit by Dinith 2019 - 12 - 14 //////////////////////////////////////////////////////

        Button ButtonSave = inflation.findViewById(R.id.savebtn);
        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    if (Code.getText().toString().trim().equals("")) {
                        Code.setError("Should give a Bay Number !");
                    }  else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(inflation.getContext());
                        builder.setCancelable(true);
                        builder.setIcon(R.drawable.logo);
                        builder.setMessage("Do you want to save Feeder Data?");
                        builder.setTitle("Save Switch");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!isConnected(AutoRecloserFragmentEdit.this)){
                                            // createExcel();
                                            Toast.makeText(getActivity().getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            new AutoRecloserFragmentEdit.AddArDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                            Toast.makeText(getActivity().getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
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
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return  inflation;
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private class AddArDB extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... urls) {

            String code = Code.getText().toString();
            String name = Name.getText().toString();

            int mounting;
            if(Mounting.getSelectedItem().toString() == "Pole"){
                mounting = 1;
            }else{
                mounting = 2;
            }

            int connectedbus;
            if(ConnectedBus.getSelectedItem().toString()=="Top"){
                connectedbus=1;
            }else if (ConnectedBus.getSelectedItem().toString()=="Bottom"){
                connectedbus=2;
            }else{
                connectedbus=3;
            }

            int brandname;
            if(BrandName.getSelectedItem().toString()=="NOJA"){
                brandname = 1;
            }else if(BrandName.getSelectedItem().toString()=="ADVC"){
                brandname = 2;
            }else if(BrandName.getSelectedItem().toString()=="Jing Kvang"){
                brandname = 3;
            }else if(BrandName.getSelectedItem().toString()=="Nulec"){
                brandname = 4;
            }else if (BrandName.getSelectedItem().toString()=="BN System"){
                brandname = 5;
            }else{
                brandname = 6;
            }
            String datemanu1 = DateOfManu1.getText().toString();
            String comdate1 = CommissioningDate1.getText().toString();
            String model1 = ModelNumber1.getText().toString();
            String serial1 = SerialNumber1.getText().toString();
            String batteries = NoOfBatteries.getText().toString();
            String batterycapacity = BatteryCapacity.getText().toString();
            String batteryvoltage = BatteryVoltage.getText().toString();
            String auxiliarydcvoltage = AuxiliaryDCVoltage.getText().toString();
            String onebatteryvoltage = OneBatteryVoltage.getText().toString();
            String remote = RemoteOperation.getText().toString();
            String arrangement = Arangement.getSelectedItem().toString();
            String datemanu2 = DateOfManu2.getText().toString();
            String comdate2 = CommissioningDate2.getText().toString();
            String model2 = ModelNumber2.getText().toString();
            String serial2 = SerialNumber2.getText().toString();
            String rating = Rating.getText().toString();
            String scc = SCC.getText().toString();
            String insulationmedium = InsulationMedium.getSelectedItem().toString();
            String latitude = Latitude.getText().toString();
            String longitude = Longitude.getText().toString();


            objSwitch.setSwitchName(name);
            objSwitch.setMounting(new BigDecimal(mounting));
            objSwitch.setConnectedBus(new BigDecimal(connectedbus));
            objSwitch.setBrandName(new BigDecimal(brandname));

            Date manu1 = null;
            try {
                manu1 = new SimpleDateFormat("yyyy-MM-dd").parse(datemanu1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objSwitch.setCpDateManufature(manu1);

            Date com1 = null;
            try {
                com1 = new SimpleDateFormat("yyyy-MM-dd").parse(comdate1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objSwitch.setCpDateCommitioning(com1);

            if(model1.length()==0){
                objSwitch.setCpModelNumber("-");
            }else{
                objSwitch.setCpModelNumber(model1);
            }

            if(serial1.length()==0){
                objSwitch.setCpSerialNo("-");
            }else{
                objSwitch.setCpSerialNo(serial1);
            }

            if(batteries.length()==0){
                objSwitch.setCpNoOfBatteries(new BigDecimal(0));
            }else{
                objSwitch.setCpNoOfBatteries(new BigDecimal(batteries));
            }


            if(batterycapacity.length()==0){
                objSwitch.setCpBatteryCapacity(new BigDecimal(0));
            }else{
                objSwitch.setCpBatteryCapacity(new BigDecimal(batterycapacity));
            }

            if(batteryvoltage.length()==0){
                objSwitch.setCpBatteryValtage(new BigDecimal(0));
            }else{
                objSwitch.setCpBatteryValtage(new BigDecimal(batteryvoltage));
            }

            if(auxiliarydcvoltage.length()==0){
                objSwitch.setAuxiliryDcVolatge(new BigDecimal(0));
            }else{
                objSwitch.setAuxiliryDcVolatge(new BigDecimal(auxiliarydcvoltage));
            }

            if(onebatteryvoltage.length()==0){
                objSwitch.setOneBatteryVoltage(new BigDecimal(0));
            }else{
                objSwitch.setOneBatteryVoltage(new BigDecimal(onebatteryvoltage));
            }

            if(remote.length()==0){
                objSwitch.setCpRemoteOperation("-");
            }else{
                objSwitch.setCpRemoteOperation(remote);
            }


            objSwitch.setSgArrangement(arrangement);

            Date manu2 = null;
            try {
                manu2 = new SimpleDateFormat("yyyy-MM-dd").parse(datemanu2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objSwitch.setSgDateManufature(manu2);

            Date com2 = null;
            try {
                com2 = new SimpleDateFormat("yyyy-MM-dd").parse(comdate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objSwitch.setSgDateCommitioning(com2);

            if(model2.length()==0){
                objSwitch.setSgModelNumber("-");
            }else{
                objSwitch.setSgModelNumber(model2);
            }

            if(serial2.length()==0){
                objSwitch.setSgSerialNo("-");
            }else{
                objSwitch.setSgSerialNo(serial2);
            }

            if(rating.length()==0){
                objSwitch.setSgRating(new BigDecimal(0));
            }else{
                objSwitch.setSgRating(new BigDecimal(rating));
            }

            if(rating.length()==0){
                objSwitch.setSgRating(new BigDecimal(0));
            }else{
                objSwitch.setSgRating(new BigDecimal(rating));
            }

            if(scc.length()==0){
                objSwitch.setSgSccLevelOfEquipment(new BigDecimal(0));
            }else{
                objSwitch.setSgSccLevelOfEquipment(new BigDecimal(scc));
            }

            objSwitch.setSgInsulationMedium(insulationmedium);

            objSwitch.setGpsLatitude(new BigDecimal(latitude));
            objSwitch.setGpsLongitude(new BigDecimal(longitude));

            objSwitch.setSwitchType(new BigDecimal(1));


            System.out.println("***********************************************************");
            //System.out.println(objSwitchPK.getFeederId()+" "+objSwitchPK.getSwitchId()+" "+objSwitchPK.getGantryId());
            System.out.println("Id"+objSwitch.getId());
            System.out.println("Code :"+objSwitch.getSwitchCode());
            System.out.println("Name :"+objSwitch.getSwitchName());
            System.out.println("Mounting :"+objSwitch.getMounting());
            System.out.println("Brand Name :"+objSwitch.getBrandName());
            System.out.println("Date Manu1 :"+objSwitch.getCpDateManufature());
            System.out.println("Com date1 :"+objSwitch.getCpDateCommitioning());
            System.out.println("Model Num :"+objSwitch.getCpModelNumber());
            System.out.println("Serial :"+objSwitch.getCpSerialNo());
            System.out.println("Batteries :"+objSwitch.getCpNoOfBatteries());
            System.out.println("Remote :"+objSwitch.getCpRemoteOperation());
            System.out.println("Arrangement :"+objSwitch.getSgArrangement());
            System.out.println("Date Manu2 :"+objSwitch.getSgDateManufature());
            System.out.println("Comdate2 :"+objSwitch.getSgDateCommitioning());
            System.out.println("Model2 :"+objSwitch.getSgModelNumber());
            System.out.println("Serial2 :"+objSwitch.getSgSerialNo());
            System.out.println("Rating :"+objSwitch.getSgRating());;
            System.out.println("SCC :"+objSwitch.getSgSccLevelOfEquipment());
            System.out.println("Insulation :"+objSwitch.getSgInsulationMedium());

            final RestTemplate restTemplate = new RestTemplate();
            final String url1 = Util.SRT_URL + "MMSUpdateSwitcheMobile/";
            System.out.println(" url1 " + url1);
            // trustEveryone();
            System.out.println(" ...........................url1tttttttttttttt " );
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println(" url1ttttttttttttttgggggg " );
            //return restTemplate.getForObject(url1, String.class);
            String objReturn = restTemplate.postForObject(url1, objSwitch, String.class);
            System.out.println(" objReturn: " + objReturn );
            return objReturn;
        }
    }


    private boolean isConnected(AutoRecloserFragmentEdit autoRecloserFragment) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    ////////// L O A D  P R O V I N C E /////////////////////////// R O W D Y //////////////////////////
    private class loadProvince extends AsyncTask<String, Void, Glcompm[]> {
        @Override
        protected Glcompm[] doInBackground(String... strings) {
            //get deptId from session manager
            SessionManager objS = new SessionManager(getActivity().getBaseContext());
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
            ProgDialog= new ProgressDialog(AutoRecloserFragmentEdit.this.getActivity());
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
            ArrayAdapter<String> adapterPr = new ArrayAdapter< String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesPro);
            adapterPr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerProvince = inflation.findViewById(R.id.spnProvince);
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
            ArrayAdapter<String> adapterArea = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesArea);
            adapterArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerArea = inflation.findViewById(R.id.spnArea);
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
//            ArrayAdapter<String> adapterLine = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesLine);
//            adapterLine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            SpinnerLine = inflation.findViewById(R.id.spnLine);
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
//
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
//            ArrayAdapter<String> adapterGantry = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesGantry);
//            adapterGantry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            SpinnerGantry = inflation.findViewById(R.id.spnGantryCode);
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
            ArrayAdapter<String> adapterGantry = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesGantry);
            adapterGantry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerGantry = inflation.findViewById(R.id.spnGantryCode);
            SpinnerGantry.setAdapter(adapterGantry);
        }
    }


    ////////// L O A D   F E E D E R   B Y    G A N T R Y  /////////////////////////////////////////
    private class loadFeederByGantry extends AsyncTask<String, Void, MmsAddfeeder[]> {
        @Override
        protected MmsAddfeeder[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findFeederyById/" + gantry + "/";

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
                    System.out.println("name = " + obj.getName() + ", code = " + obj.getCode());
//                    valuesFeeder[c] = obj.getName();
                    if (obj.getName() != null && obj.getCode() != null) {
                        valueFeederList.add(obj.getName());
                        SpinnerMapFeeder.put(c, obj.getId().getFeederId());
                    }
                }
            }
            ArrayAdapter<String> adapterFeeder = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valueFeederList.toArray(new String[]{}));
            adapterFeeder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerFeeder = inflation.findViewById(R.id.spnFeeder);
            SpinnerFeeder.setAdapter(adapterFeeder);
        }
    }

    ////////// L O A D   S W I T C H E S   B Y    F E E D E R  /////////////////////////////////////////
    private class loadSwitchesByFeeder extends AsyncTask<String, Void, MmsAddswitch[]> {
        @Override
        protected MmsAddswitch[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findSwitchByFeederId/" + feeder + "/1/";

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5, MmsAddswitch[].class);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(MmsAddswitch[] results) {
            // ListView Item Click Listener
            System.out.println("results" + results);
            System.out.println("results length" + results.length);
            String[] gantry;
            valuesSwitch = new String[results.length];

            List<String> valueFeederList = new ArrayList<>();
            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddswitch obj = results[c];
                    System.out.println("name = " + obj.getSwitchName() + ", code = " + obj.getSwitchCode());
//                    valuesFeeder[c] = obj.getName();
                    if (obj.getSwitchName() != null && obj.getSwitchCode() != null) {
                        valueFeederList.add(obj.getSwitchName());
                        SpinnerMapSwitch.put(c, obj.getId().getSwitchId());
                    }
                }
            }
            ArrayAdapter<String> adapterFeeder = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valueFeederList.toArray(new String[]{}));
            adapterFeeder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerSwitch = inflation.findViewById(R.id.spnSwitch);
            SpinnerSwitch.setAdapter(adapterFeeder);
        }
    }

    ////////// L O A D   S W I T C H   O B J E C T  /////////////////////////////////////////////////////////
    private class getSwitchObj extends AsyncTask<String, Void, MmsAddswitch> {

        @Override
        protected MmsAddswitch doInBackground(String... urls) {

            //Code = findViewById(R.id.spnGantryCode);
            //String code = Code.getSelectedItem().toString();

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "getSwitchObj/" + switchid+ "/";
            System.out.println(switchid);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
            //System.out.println(rest.getForObject(url6, MmsAddgantry.class));

            objSwitch =  rest.getForObject(url6, MmsAddswitch.class);;
            //System.out.println(objSwitch.getSwitchCode());
            //System.out.println(objSwitch.getSwitchName());

            ProgDialog.dismiss();

            return rest.getForObject(url6, MmsAddswitch.class);
        }
    }

    //////////////////// L O A D  NEXT SWITCH ID \\\\\ 2019 12 17 //// R O W D Y ////////////////////////////////
    private class getNextSwitchID extends AsyncTask<String, Void, String > {

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
            nextSwitchId = results;
        }
    }

}
