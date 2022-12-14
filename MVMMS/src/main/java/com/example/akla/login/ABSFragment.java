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

public class ABSFragment extends Fragment {

    //For get date
    Button btn_date;
    EditText in_date;
    private int mYear, mMonth, mDay;

    Button btn_date2;
    EditText in_date2;
    private int mYear2, mMonth2, mDay2;

    //load spinners
    Spinner  SpinnerBrandname;
    ArrayAdapter<CharSequence> adapter1;

    Spinner SpinnerOperation;
    ArrayAdapter<CharSequence> adapter2;

    Spinner SpinnerPosition;
    ArrayAdapter<CharSequence> adapter3;

    Spinner SpinnerConnectedBus;
    ArrayAdapter<CharSequence> adapter4;


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

    String feeder;
    String nextSwitchId;

    // Define for variebles for send DB
    Spinner Province;
    Spinner Area;
    Spinner Line;
    Spinner Gantry;
    Spinner Feeder;
    EditText Code;
    EditText Name;
    Spinner BrandName;
    Spinner ConnectedBus;
    EditText DateOfManu;
    EditText CommissioningDate;
    EditText ModelNumber;
    EditText SerialNumber;
    Spinner OperationDirection;
    Spinner Position;
    EditText Rating;
    EditText SCC;
    TextView Latitude;
    TextView Longitude;

    private FusedLocationProviderClient client;

    private View inflation;

    private ProgressDialog ProgDialog;

    public ABSFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        System.out.println("ABS::::::::::::::::::::::::::::::::::::::::::::::::::::::::::ABS");
        inflation = inflater.inflate(R.layout.fragment_abs, container, false);

        //spinner brandname
        SpinnerBrandname =(Spinner)inflation.findViewById(R.id.spnBrandName);
        adapter1 =ArrayAdapter.createFromResource(inflation.getContext(),R.array.brand_name,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerBrandname.setAdapter(adapter1);

        //spinner operationdirection
        SpinnerOperation =(Spinner)inflation.findViewById(R.id.spnOperation);
        adapter2 =ArrayAdapter.createFromResource(inflation.getContext(),R.array.operation_direction,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerOperation.setAdapter(adapter2);

        //spinner position
        SpinnerPosition =(Spinner)inflation.findViewById(R.id.spnPosition);
        adapter3 =ArrayAdapter.createFromResource(inflation.getContext(),R.array.position,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPosition.setAdapter(adapter3);

        //spinner Conected Bus
        SpinnerConnectedBus =(Spinner)inflation.findViewById(R.id.spnConnectedBus);
        adapter4 =ArrayAdapter.createFromResource(inflation.getContext(),R.array.connectedBus,android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerConnectedBus.setAdapter(adapter4);


        //Date picking \\\\\\\\\\\\ R O W D Y ///////////2012 12 17 ////////////////
        //Date of Manufacture
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

        //ommissioning date
        btn_date2 = (Button)inflation.findViewById(R.id.btn_date1);
        in_date2 = (EditText) inflation.findViewById(R.id.in_date1);
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


        if(!isConnected(ABSFragment.this)){
            //readExcel();
            //readExcelLoadtype();
            //readExcelConType();
        }else{
            new ABSFragment.loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Area -- Area load by Province
        SpinnerProvince = inflation.findViewById(R.id.spnProvince);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province =idprovince;

                if(!isConnected(ABSFragment.this)){
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new ABSFragment.loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

                if(!isConnected(ABSFragment.this)){
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new ABSFragment.loadGantrybyArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
//                if(!isConnected(ABSFragment.this)){
//                    //readExcelLine();
//                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
//
//                }else {
//                    new ABSFragment.loadGantryByLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

                if(!isConnected(ABSFragment.this)){
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new ABSFragment.loadFeederByGantry().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Feeder
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

                if(!isConnected(ABSFragment.this)){
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {

                    new ABSFragment.getNextSwitchID().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
                if(ActivityCompat.checkSelfPermission(ABSFragment.this.getActivity(),ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(ABSFragment.this.getActivity(), new OnSuccessListener<Location>() {
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



        /////////////////////////Save to Database //////////////////////////////////////////////////////////////////////
        //////////////////////// Edit by Dinith 2019 - 12 - 17 /////////////////////////////////////////////////////////

        Button ButtonSave = inflation.findViewById(R.id.savebtn);
        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
                    Province = inflation.findViewById(R.id.spnProvince);
                    Area = inflation.findViewById(R.id.spnArea);
                    Gantry = inflation.findViewById(R.id.spnGantryCode);
                    Feeder = inflation.findViewById(R.id.spnFeeder);
                    Code = inflation.findViewById(R.id.code);
                    Name = inflation.findViewById(R.id.name);
                    BrandName = inflation.findViewById(R.id.spnBrandName);
                    ConnectedBus = inflation.findViewById(R.id.spnConnectedBus);
                    DateOfManu = inflation.findViewById(R.id.in_date);
                    CommissioningDate = inflation.findViewById(R.id.in_date1);
                    ModelNumber = inflation.findViewById(R.id.model_number);
                    SerialNumber = inflation.findViewById(R.id.serial_number);
                    OperationDirection = inflation.findViewById(R.id.spnOperation);
                    Position = inflation.findViewById(R.id.spnPosition);
                    Rating = inflation.findViewById(R.id.rating);
                    SCC = inflation.findViewById(R.id.scc);
                    Latitude = inflation.findViewById(R.id.latitude);
                    Longitude = inflation.findViewById(R.id.longitude);

                    if (Code.getText().toString().trim().equals("")) {
                        Code.setError("Should give a Code !");

                    }else if(Name.getText().toString().trim().equals("")){
                        Name.setError("Should give a Name !");

                    }  else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(inflation.getContext());
                        builder.setCancelable(true);
                        builder.setIcon(R.drawable.logo);
                        builder.setMessage("Do you want to save Switch Data?");
                        builder.setTitle("Save Switch");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!isConnected(ABSFragment.this)){
                                            // createExcel();
                                            Toast.makeText(getActivity().getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            new ABSFragment.AddABSDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

        return inflation;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private class AddABSDB extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... urls) {

            String code = Code.getText().toString();
            String name = Name.getText().toString();

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

            int connectedbus;
            if(ConnectedBus.getSelectedItem().toString()=="Top"){
                connectedbus=1;
            }else if (ConnectedBus.getSelectedItem().toString()=="Bottom"){
                connectedbus=2;
            }else{
                connectedbus=3;
            }


            String datemanu = DateOfManu.getText().toString();
            String comdate = CommissioningDate.getText().toString();
            String model = ModelNumber.getText().toString();
            String serial = SerialNumber.getText().toString();
            String operation = OperationDirection.getSelectedItem().toString();
            String position = Position.getSelectedItem().toString();
            String rating = Rating.getText().toString();
            String scc = SCC.getText().toString();

            String latitude = Latitude.getText().toString();
            String longitude = Longitude.getText().toString();


            //set values to MmsAddSwitch object
            MmsAddswitch objAddswitch = new MmsAddswitch();
            objAddswitch.setSwitchCode(code);
            objAddswitch.setSwitchName(name);
            objAddswitch.setBrandName(new BigDecimal(brandname));
            objAddswitch.setConnectedBus(new BigDecimal(connectedbus));

            Date manu = null;
            try {
                manu = new SimpleDateFormat("yyyy-MM-dd").parse(datemanu);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objAddswitch.setCpDateManufature(manu);

            Date com = null;
            try {
                com = new SimpleDateFormat("yyyy-MM-dd").parse(comdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objAddswitch.setCpDateCommitioning(com);

            if(model.length()==0){
                objAddswitch.setCpModelNumber("-");
            }else{
                objAddswitch.setCpModelNumber(model);
            }

            if(serial.length()==0){
                objAddswitch.setCpSerialNo("-");
            }else{
                objAddswitch.setCpSerialNo(serial);
            }

            objAddswitch.setOperationDirection(operation);
            objAddswitch.setPosition(position);

            if(rating.length()==0){
                objAddswitch.setSgRating(new BigDecimal(0));
            }else{
                objAddswitch.setSgRating(new BigDecimal(rating));
            }

            if(scc.length()==0){
                objAddswitch.setSgSccLevelOfEquipment(new BigDecimal(0));
            }else{
                objAddswitch.setSgSccLevelOfEquipment(new BigDecimal(scc));
            }

            objAddswitch.setGpsLatitude(new BigDecimal(latitude));
            objAddswitch.setGpsLongitude(new BigDecimal(longitude));

            objAddswitch.setSwitchType(new BigDecimal(3));

            MmsAddswitchPK objAddswitchPK = new MmsAddswitchPK();
            objAddswitchPK.setGantryId(new Long(gantry));
            objAddswitchPK.setFeederId(feeder);
            objAddswitchPK.setSwitchId(nextSwitchId);

            objAddswitch.setId(objAddswitchPK);

            System.out.println("***********************************************************");
            System.out.println(objAddswitchPK.getFeederId()+" "+objAddswitchPK.getSwitchId()+" "+objAddswitchPK.getGantryId());
            System.out.println("Id"+objAddswitch.getId());
            System.out.println("Code :"+objAddswitch.getSwitchCode());
            System.out.println("Name :"+objAddswitch.getSwitchName());
            System.out.println("Brand Name :"+objAddswitch.getBrandName());
            System.out.println("Connected Bus: "+objAddswitch.getConnectedBus());
            System.out.println("Date Manu1 :"+objAddswitch.getCpDateManufature());
            System.out.println("Com date1 :"+objAddswitch.getCpDateCommitioning());
            System.out.println("Model Num :"+objAddswitch.getCpModelNumber());
            System.out.println("Serial :"+objAddswitch.getCpSerialNo());
            System.out.println("Batteries :"+objAddswitch.getCpNoOfBatteries());
            System.out.println("Remote :"+objAddswitch.getCpRemoteOperation());
            System.out.println("Arrangement :"+objAddswitch.getSgArrangement());
            System.out.println("Date Manu2 :"+objAddswitch.getSgDateManufature());
            System.out.println("Comdate2 :"+objAddswitch.getSgDateCommitioning());
            System.out.println("Model2 :"+objAddswitch.getSgModelNumber());
            System.out.println("Serial2 :"+objAddswitch.getSgSerialNo());
            System.out.println("Rating :"+objAddswitch.getSgRating());;
            System.out.println("SCC :"+objAddswitch.getSgSccLevelOfEquipment());
            System.out.println("Insulation :"+objAddswitch.getSgInsulationMedium());
            System.out.println("Latitude: "+objAddswitch.getGpsLatitude());
            System.out.println("Longitude: "+objAddswitch.getGpsLongitude());

            final RestTemplate restTemplate = new RestTemplate();
            final String url1 = Util.SRT_URL + "MMSAddSwitcheMobile/";
            System.out.println(" url1 " + url1);
            // trustEveryone();
            System.out.println(" ...........................url1tttttttttttttt " );
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println(" url1ttttttttttttttgggggg " );
            //return restTemplate.getForObject(url1, String.class);
            String objReturn = restTemplate.postForObject(url1, objAddswitch, String.class);
            System.out.println(" objReturn: " + objReturn );
            return objReturn;
        }
    }
    ////////// isConnected/////////////////////////// R O W D Y //////////////////////////
    private boolean isConnected(ABSFragment absFragment) {
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
            ProgDialog= new ProgressDialog(ABSFragment.this.getActivity());
            ProgDialog.setMessage("Please wait...\n(This may take some time, depending on your network connection)");
            ProgDialog.setIndeterminate(false);
            ProgDialog.setTitle(Util.alert_header);
            ProgDialog.setIcon(R.drawable.logo);
            ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            ProgDialog.setCancelable(true);
            ProgDialog.show();
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

            ProgDialog.dismiss();
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
