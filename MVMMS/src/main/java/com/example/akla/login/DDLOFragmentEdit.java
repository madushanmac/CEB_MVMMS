package com.example.akla.login;

import android.app.AlertDialog;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class DDLOFragmentEdit extends Fragment {

    //load spinners
    Spinner SpinnerPosition;
    ArrayAdapter<CharSequence> adapter1;

    Spinner SpinnerCarrierType;
    ArrayAdapter<CharSequence> adapter2;

    Spinner SpinnerConnectedBus;
    ArrayAdapter<CharSequence> adapter3;

    //load Province
    Spinner SpinnerProvince;
    HashMap<Integer, String> spinnerMapProvince = new HashMap<Integer, String>();
    String[] valuesPro = new String[]{};

    //load Area
    Spinner SpinnerArea;
    String province;
    String valuesArea[] = new String[]{};
    HashMap<Integer, String> SpinnerMapArea = new HashMap<Integer, String>();

    //load Line
    Spinner SpinnerLine;
    String area;
    String valuesLine[] = new String[]{};
    HashMap<Integer, String> SpinnerMapLine = new HashMap<Integer, String>();

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
    private FusedLocationProviderClient client;

    // Define for variebles for send DB
    Spinner Province;
    Spinner Area;
    Spinner Line;
    Spinner Gantry;
    Spinner Feeder;
    EditText Code;
    EditText Name;
    Spinner Position;
    EditText Quantity;
    Spinner CarrierType;
    EditText Rating;
    Spinner ConnectedBus;
    TextView Latitude;
    TextView Longitude;

    private View inflation;

    private ProgressDialog ProgDialog;

    public DDLOFragmentEdit() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("DDLO:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::DDLO");

        inflation = inflater.inflate(R.layout.fragment_ddlo_edit, container, false);

        Province = inflation.findViewById(R.id.spnProvince);
        Area = inflation.findViewById(R.id.spnArea);
        //Line = inflation.findViewById(R.id.spnLine);
        Gantry = inflation.findViewById(R.id.spnGantryCode);
        Feeder = inflation.findViewById(R.id.spnFeeder);
        Code = inflation.findViewById(R.id.code);
        Name = inflation.findViewById(R.id.name);
        Position = inflation.findViewById(R.id.spnPosition);
        Quantity = inflation.findViewById(R.id.quantity);
        CarrierType = inflation.findViewById(R.id.spnCarrierType);
        Rating = inflation.findViewById(R.id.rating);
        ConnectedBus = inflation.findViewById(R.id.spnConnectedBus);
        Latitude = inflation.findViewById(R.id.latitude);
        Longitude = inflation.findViewById(R.id.longitude);


        //spinner position
        SpinnerPosition =(Spinner)inflation.findViewById(R.id.spnPosition);
        adapter1 =ArrayAdapter.createFromResource(inflation.getContext(),R.array.position,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPosition.setAdapter(adapter1);

        //spinner carrier type
        SpinnerCarrierType =(Spinner)inflation.findViewById(R.id.spnCarrierType);
        adapter2 =ArrayAdapter.createFromResource(inflation.getContext(),R.array.carrier_type,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCarrierType.setAdapter(adapter2);

        //spinner Conected Bus
        SpinnerConnectedBus =(Spinner)inflation.findViewById(R.id.spnConnectedBus);
        adapter3 =ArrayAdapter.createFromResource(inflation.getContext(),R.array.connectedBus,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerConnectedBus.setAdapter(adapter3);

        if (!isConnected(DDLOFragmentEdit.this)) {
            //readExcel();
            //readExcelLoadtype();
            //readExcelConType();
        } else {
            new DDLOFragmentEdit.loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        //Spinner Area -- Area load by Province
        SpinnerProvince = inflation.findViewById(R.id.spnProvince);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province = idprovince;

                if (!isConnected(DDLOFragmentEdit.this)) {
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                } else {
                    new DDLOFragmentEdit.loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

                if (!isConnected(DDLOFragmentEdit.this)) {
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                } else {
                    new DDLOFragmentEdit.loadGantrybyArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
//                if (!isConnected(DDLOFragmentEdit.this)) {
//                    //readExcelLine();
//                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
//
//                } else {
//                    new DDLOFragmentEdit.loadGantryByLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

                if(!isConnected(DDLOFragmentEdit.this)){
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new DDLOFragmentEdit.loadFeederByGantry().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

                if(!isConnected(DDLOFragmentEdit.this)){
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new DDLOFragmentEdit.loadSwitchesByFeeder().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new DDLOFragmentEdit.getNextSwitchID().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Switch object load by switch code
        SpinnerSwitch = inflation.findViewById(R.id.spnSwitch);
        SpinnerSwitch.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idswitch = SpinnerMapSwitch.get(parent.getSelectedItemPosition());
                switchid = idswitch.toString();

                if(!isConnected(DDLOFragmentEdit.this)){
                    //readExcelLine();
                    //Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new DDLOFragmentEdit.getSwitchObj().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
                if(ActivityCompat.checkSelfPermission(DDLOFragmentEdit.this.getActivity(),ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(DDLOFragmentEdit.this.getActivity(), new OnSuccessListener<Location>() {
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
                    System.out.println("XXXXXXXXXXXXXXXXXXX"+objSwitch.getSwitchCode());

                    if(objSwitch.getSwitchCode()!=null){
                        Code.setText(objSwitch.getSwitchCode());
                    }

                    if(objSwitch.getSwitchName()!=null){
                        Name.setText(objSwitch.getSwitchName());
                    }

                    if(objSwitch.getQuantity()!=null){
                        Quantity.setText(objSwitch.getQuantity().toString());
                    }

                    if(objSwitch.getSgRating()!=null){
                        Rating.setText(objSwitch.getSgRating().toString());
                    }

                    if(objSwitch.getGpsLatitude()!=null){
                        Latitude.setText(objSwitch.getGpsLatitude().toString());
                    }

                    if(objSwitch.getGpsLongitude()!=null){
                        Longitude.setText(objSwitch.getGpsLongitude().toString());
                    }

                }
            }
        });

        /////////////////////////Save to Database //////////////////////////////////////////////////////////////////////
        //////////////////////// Edit by Dinith 2019 - 12 - 14 /////////////////////////////////////////////////////////

        Button ButtonSave = inflation.findViewById(R.id.savebtn);
        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
                    if (Code.getText().toString().trim().equals("")) {
                        Code.setError("Should give a Code !");
                    }  else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(inflation.getContext());
                        builder.setCancelable(true);
                        builder.setIcon(R.drawable.logo);
                        builder.setMessage("Do you want to save Switch - DDLO Data?");
                        builder.setTitle("Save Switch");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!isConnected(DDLOFragmentEdit.this)){
                                            // createExcel();
                                            Toast.makeText(getActivity().getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            new DDLOFragmentEdit.AddDDLODB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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


    private class AddDDLODB extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... urls) {

            String code = Code.getText().toString();
            String name = Name.getText().toString();
            String position = Position.getSelectedItem().toString();
            String quantity = Quantity.getText().toString();
            String carriertype = CarrierType.getSelectedItem().toString();
            String rating = Rating.getText().toString();

            int connectedbus;
            if(ConnectedBus.getSelectedItem().toString()=="Top"){
                connectedbus=1;
            }else if (ConnectedBus.getSelectedItem().toString()=="Bottom"){
                connectedbus=2;
            }else{
                connectedbus=3;
            }

            String latitude = Latitude.getText().toString();
            String longitude = Longitude.getText().toString();

            //set values to MmsAddSwitch object

            objSwitch.setSwitchCode(code);
            objSwitch.setSwitchName(name);
            objSwitch.setPosition(position);

            if(quantity.length()==0){
                objSwitch.setQuantity(new BigDecimal(0));
            }else{
                objSwitch.setQuantity(new BigDecimal(quantity));
            }

            objSwitch.setCarrierType(carriertype);

            if(rating.length()==0){
                objSwitch.setSgRating(new BigDecimal(0));
            }else{
                objSwitch.setSgRating(new BigDecimal(rating));
            }

            objSwitch.setConnectedBus(new BigDecimal(connectedbus));

            objSwitch.setGpsLatitude(new BigDecimal(latitude));
            objSwitch.setGpsLongitude(new BigDecimal(longitude));

            objSwitch.setSwitchType(new BigDecimal(4));


            System.out.println("***********************************************************");
            //System.out.println(objSwitchPK.getFeederId()+" "+objAddswitchPK.getSwitchId()+" "+objAddswitchPK.getGantryId());
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

    private boolean isConnected(DDLOFragmentEdit ddloFragment) {
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
            ProgDialog= new ProgressDialog(DDLOFragmentEdit.this.getActivity());
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
            String url5 = Util.SRT_URL + "findSwitchByFeederId/" + feeder + "/4/";

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
            System.out.println(objSwitch.getSwitchName());

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