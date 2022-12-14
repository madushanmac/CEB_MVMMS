package com.example.akla.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

import static com.example.akla.login.Util.isConnected;

public class AddMVPolesMaintenance extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //load mv poles
    Spinner SpinnerMvPole;
    String mvpole;
    String valuesMvPole[] = new String[]{};
    HashMap<Integer,String> SpinnerMapMvPole = new HashMap<Integer, String>();

    //Define Variebles for save data in db

    EditText StayRequired;
    EditText StayBad;
    EditText StrutStrp;
    EditText StrutRequired;
    EditText StrutBad;
    EditText PoleElignment;
    EditText PoleCondition;
    EditText HorizentalEarth;
    EditText EarthDownConductor;
    EditText LVService;
    EditText LVJump;
    EditText HVJump;
    EditText HVSpan;
    EditText LVSpan;
    EditText LVCrack;
    EditText HVBad1;
    EditText HVBad2;
    EditText CCT1P1;
    EditText CCT1P3;
    EditText CCT2P1;
    EditText CCT2P3;
    EditText CCT3P1;
    EditText CCT3P3;
    EditText CCT4P1;
    EditText CCT4P3;
    EditText LVCCT1Type;
    EditText LVCCT1Name;
    EditText LVCCT1ConType;
    EditText LVCCT2Type;
    EditText LVCCT2Name;
    EditText LVCCT2ConType;
    EditText LVCCT3Type;
    EditText LVCCT3Name;
    EditText LVCCT3ConType;
    EditText LVCCT4Type;
    EditText LVCCT4Name;
    EditText LVCCT4ConType;
    EditText LVCCT5Type;
    EditText LVCCT5Name;
    EditText LVCCT5ConType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mvpoles_maintenance);


        if(!Util.isConnected(AddMVPolesMaintenance.this)){
            //readExcelTowerNo();

        }else {
            new AddMVPolesMaintenance.loadAreaFeederByMvPole().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            System.out.println("ppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");

        }

        StayRequired= findViewById(R.id.etStayRequired);
        StayBad= findViewById(R.id.etStayBad);
        StrutStrp = findViewById(R.id.etStrutStrp);
        StrutRequired= findViewById(R.id.etStrutRequired);
        StrutBad= findViewById(R.id.etStrutBad);
        PoleElignment= findViewById(R.id.etPoleElignment);
        PoleCondition= findViewById(R.id.etPoleCondition);
        HorizentalEarth= findViewById(R.id.etHorizentalEarth);
        EarthDownConductor= findViewById(R.id.etEarthDownConductor);
        LVService= findViewById(R.id.etLVService);
        LVJump= findViewById(R.id.etLVJump);
        HVJump= findViewById(R.id.etHVJump);
        HVSpan= findViewById(R.id.etHVSpan);
        LVSpan= findViewById(R.id.etLVSpan);
        LVCrack= findViewById(R.id.etLVCrack);
        HVBad1= findViewById(R.id.etHVBad1);
        HVBad2= findViewById(R.id.etHVBad2);
        CCT1P1= findViewById(R.id.etCCT1P1);
        CCT1P3= findViewById(R.id.etCCT1P3);
        CCT2P1= findViewById(R.id.etCCT2P1);
        CCT2P3= findViewById(R.id.etCCT2P3);
        CCT3P1= findViewById(R.id.etCCT3P1);
        CCT3P3= findViewById(R.id.etCCT3P3);
        CCT4P1= findViewById(R.id.etCCT4P1);
        CCT4P3= findViewById(R.id.etCCT4P3);
        LVCCT1Type= findViewById(R.id.etLVCCT1Type);
        LVCCT1Name= findViewById(R.id.etLVCCT1Name);
        LVCCT1ConType= findViewById(R.id.etLVCCT1ConType);
        LVCCT2Type= findViewById(R.id.etLVCCT2Type);
        LVCCT2Name= findViewById(R.id.etLVCCT2Name);
        LVCCT2ConType= findViewById(R.id.etLVCCT2ConType);
        LVCCT3Type= findViewById(R.id.etLVCCT3Type);
        LVCCT3Name= findViewById(R.id.etLVCCT3Name);
        LVCCT3ConType= findViewById(R.id.etLVCCT3ConType);
        LVCCT4Type= findViewById(R.id.etLVCCT4Type);
        LVCCT4Name= findViewById(R.id.etLVCCT4Name);
        LVCCT4ConType= findViewById(R.id.etLVCCT4ConType);
        LVCCT5Type= findViewById(R.id.etLVCCT5Type);
        LVCCT5Name= findViewById(R.id.etLVCCT5Name);
        LVCCT5ConType= findViewById(R.id.etLVCCT5ConType);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Spinner Mv Pole
        SpinnerMvPole = findViewById(R.id.spinnerMVPole);
        SpinnerMvPole.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idmvpole = SpinnerMapMvPole.get(parent.getSelectedItemPosition());
                mvpole =idmvpole;
                System.out.println("/////////////////////////////////////////////////////////");
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button savebutton =(Button) findViewById(R.id.btnSaveDB);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddMVPolesMaintenance.this);
                builder.setCancelable(true);
                builder.setIcon(R.drawable.logo);
                builder.setMessage("Do you want to save MV Pole Maintainance Data?");
                builder.setTitle("Save MV Pole Maintainance");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(!isConnected(AddMVPolesMaintenance.this)){
                                    // createExcel();
                                    Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                }else {
                                    new AddMVPolesMaintenance.AddmvpoleMaintainance().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                    Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
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





        });

    }

    ////////// L O A D  A R E A  F E E D E R  B Y  M V   P O L E /////////////////////////////////////////
    private class loadAreaFeederByMvPole extends AsyncTask<String, Void, MmsAddmvpole[]> {

        SessionManager obj = new SessionManager(getBaseContext());
        String area = obj.getKeyArea();
        String feeder = obj.getKeyFeeder();



        @Override
        protected MmsAddmvpole[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            System.out.println("////////////////////////////////////////////////"+area+" "+feeder);
            String url5 = Util.SRT_URL + "findMvPoleByFeederArea/" + area + "/" + feeder;

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5, MmsAddmvpole[].class);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(MmsAddmvpole[] results) {
            // ListView Item Click Listener
            System.out.println("loadAreaFeederByMvPole");
            System.out.println("results" + results);
            System.out.println("results" + results.length);
            String[] line;
            valuesMvPole = new String[results.length];

            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddmvpole obj = results[c];

                    valuesMvPole[c] = obj.getPoleNo();
                    SpinnerMapMvPole.put(c, String.valueOf(obj.getId()));
                    //  System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+obj.getPoleNo());

                }
            }
            ArrayAdapter<String> adapterMvPole = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesMvPole);
            adapterMvPole.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerMvPole = findViewById(R.id.spinnerMVPole);
            SpinnerMvPole.setAdapter(adapterMvPole);
        }
    }


    /////////////////////////////////////////////////////// e d i t  b y  k e n t //////////////////////////////////

    private class AddmvpoleMaintainance extends AsyncTask<Void, Void, String>{

        String poleNo,stayRequired,stayBad,strutStrp,strutRequired,strutBad,poleElignment,poleCondition,horizentalEarth,earthDownConductor,lVService,lVJump,hVJump,
                hVSpan,lVSpan,lVCrack,hVBad1,hVBad2,cCT1P1,cCT1P3,cCT2P1,cCT2P3,cCT3P1,cCT3P3,cCT4P1,cCT4P3,lVCCT1Type,lVCCT1Name,lVCCT1ConType,lVCCT2Type,lVCCT2Name,lVCCT2ConType,lVCCT3Type,lVCCT3Name,lVCCT3ConType,lVCCT4Type,lVCCT4Name,lVCCT4ConType,lVCCT5Type,lVCCT5Name,lVCCT5ConType;
        Long poleNo1;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            String idmvpole = SpinnerMapMvPole.get(SpinnerMvPole.getSelectedItemPosition());
            //poleNo = SpinnerMvPole.getSelectedItem().toString();
            //poleNo1 = Long.valueOf(poleNo);
            //System.out.println("pppppppppppppppppppppppppppppppooooooooooooooooooooooooooooolllllllllllllllllleeeeee"+idmvpole);
            stayBad = StayBad.getText().toString();
            stayRequired = StayRequired.getText().toString();
            strutStrp = StrutStrp.getText().toString();
            strutRequired = StrutRequired.getText().toString();
            strutBad = StrutBad.getText().toString();
            poleElignment = PoleElignment.getText().toString();
            poleCondition = PoleCondition.getText().toString();
            horizentalEarth = HorizentalEarth.getText().toString();
            earthDownConductor = EarthDownConductor.getText().toString();
            lVService = LVService.getText().toString();
            lVJump = LVJump.getText().toString();
            hVJump = HVJump.getText().toString();
            hVSpan = HVSpan.getText().toString();
            lVSpan = LVSpan.getText().toString();
            lVCrack = LVCrack.getText().toString();
            hVBad1 = HVBad1.getText().toString();
            hVBad2 = HVBad2.getText().toString();
            cCT1P1 = CCT1P1.getText().toString();
            cCT1P3 = CCT1P3.getText().toString();
            cCT2P1 = CCT2P1.getText().toString();
            cCT2P3 = CCT2P3.getText().toString();
            cCT3P1 = CCT3P1.getText().toString();
            cCT3P3 = CCT3P3.getText().toString();
            cCT4P1 = CCT4P1.getText().toString();
            cCT4P3 = CCT4P3.getText().toString();
            lVCCT1Type = LVCCT1Type.getText().toString();
            lVCCT1Name = LVCCT1Name.getText().toString();
            lVCCT1ConType = LVCCT1ConType.getText().toString();
            lVCCT2Type = LVCCT2Type.getText().toString();
            lVCCT2Name = LVCCT2Name.getText().toString();
            lVCCT2ConType = LVCCT2ConType.getText().toString();
            lVCCT3Type = LVCCT3Type.getText().toString();
            lVCCT3Name = LVCCT3Name.getText().toString();
            lVCCT3ConType = LVCCT3ConType.getText().toString();
            lVCCT4Type = LVCCT4Type.getText().toString();
            lVCCT4Name = LVCCT4Name.getText().toString();
            lVCCT4ConType = LVCCT4ConType.getText().toString();
            lVCCT5Type = LVCCT5Type.getText().toString();
            lVCCT5Name = LVCCT5Name.getText().toString();
            lVCCT5ConType = LVCCT5ConType.getText().toString();

            System.out.println("strutStrp" + StrutStrp.getText().toString());
            System.out.println("strutRequired"+ StrutRequired.getText().toString());
            System.out.println("strutBad"+ StrutBad.getText().toString());
            System.out.println("poleElignment"+ PoleElignment.getText().toString());
            System.out.println("poleCondition"+ PoleCondition.getText().toString());
            System.out.println("horizentalEarth"+ HorizentalEarth.getText().toString());
            System.out.println("earthDownConductor"+ EarthDownConductor.getText().toString());
            System.out.println("lVService"+ LVService.getText().toString());
            System.out.println("lVJump"+ LVJump.getText().toString());
            System.out.println("hVJump"+ HVJump.getText().toString());
            System.out.println("hVSpan"+ HVSpan.getText().toString());
            System.out.println("lVSpan"+ LVSpan.getText().toString());
            System.out.println("lVCrack"+ LVCrack.getText().toString());
            System.out.println("hVBad"+ HVBad1.getText().toString());
            System.out.println("hVBad2"+ HVBad2.getText().toString());
            System.out.println("cCT1P1"+ CCT1P1.getText().toString());
            System.out.println("cCT1P3"+ CCT1P3.getText().toString());
            System.out.println("cCT2P1"+ CCT2P1.getText().toString());
            System.out.println("cCT2P3"+ CCT2P3.getText().toString());
            System.out.println("cCT3P1"+ CCT3P1.getText().toString());
            System.out.println("cCT3P3"+ CCT3P3.getText().toString());
            System.out.println("cCT4P1"+ CCT4P1.getText().toString());
            System.out.println("cCT4P3"+ CCT4P3.getText().toString());
            System.out.println("lVCCT1Type"+ LVCCT1Type.getText().toString());
            System.out.println("lVCCT1Name"+ LVCCT1Name.getText().toString());
            System.out.println("lVCCT1ConType"+ LVCCT1ConType.getText().toString());
            System.out.println("lVCCT2Type"+ LVCCT2Type.getText().toString());
            System.out.println("lVCCT2Name"+ LVCCT2Name.getText().toString());
            System.out.println("lVCCT2ConType"+ LVCCT2ConType.getText().toString());
            System.out.println("lVCCT3Type"+ LVCCT3Type.getText().toString());
            System.out.println("lVCCT3Name"+ LVCCT3Name.getText().toString());
            System.out.println("lVCCT3ConType"+ LVCCT3ConType.getText().toString());
            System.out.println("lVCCT4Type"+ LVCCT4Type.getText().toString());
            System.out.println("lVCCT4Name"+ LVCCT4Name.getText().toString());
            System.out.println("lVCCT4ConType"+ LVCCT4ConType.getText().toString());
            System.out.println("lVCCT5Type"+ LVCCT5Type.getText().toString());
            System.out.println("lVCCT5Name"+ LVCCT5Name.getText().toString());
            System.out.println("lVCCT5ConType"+ LVCCT5ConType.getText().toString());

        }
        @Override
        protected String doInBackground(Void... urls) {
            System.out.println("Addmvpolemaintainance");

            SessionManager obj = new SessionManager(getBaseContext());
            String cycle = obj.getKeyCycle();

            //set values to MmsTxntowermaintenancemv object
            MmsTxntowermaintenancemv objAddgmvpolem = new MmsTxntowermaintenancemv();
            MmsTxntowermaintenancemvPK objAddgmvpolempk = new MmsTxntowermaintenancemvPK();

            System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM"+mvpole);
            System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"+Long.parseLong(mvpole));
            objAddgmvpolempk.setPoleid(Long.parseLong(mvpole));
            objAddgmvpolempk.setVisitid(Long.parseLong(cycle));
            objAddgmvpolem.setId(objAddgmvpolempk);
            objAddgmvpolem.setStaybad(new BigDecimal(stayBad));
            objAddgmvpolem.setStayRequired(new BigDecimal(stayRequired));
            objAddgmvpolem.setStrutStrp(new BigDecimal(strutStrp));
            objAddgmvpolem.setStrutRequired(new BigDecimal(strutRequired));
            objAddgmvpolem.setStrutBad(new BigDecimal(strutBad));
            objAddgmvpolem.setPoleAlignment(new BigDecimal(poleElignment));
            objAddgmvpolem.setPoleCondition(new BigDecimal(poleCondition));
            objAddgmvpolem.setHorizontalEarth(new BigDecimal(horizentalEarth));
            objAddgmvpolem.setEarthDownConductor(new BigDecimal(earthDownConductor));
            objAddgmvpolem.setLvServiceTbCrimp(new BigDecimal(lVService));
            objAddgmvpolem.setLvJumpTbCrimped(new BigDecimal(lVJump));
            objAddgmvpolem.setHvJumpTbCrimped(new BigDecimal(hVJump));
            objAddgmvpolem.setHvSpanTbCrimped(new BigDecimal(hVSpan));
            objAddgmvpolem.setLvSpanTbCrimped(new BigDecimal(lVSpan));
            objAddgmvpolem.setLvCrackInsulators(new BigDecimal(lVCrack));
            objAddgmvpolem.setHvBadInsulators(new BigDecimal(hVBad1));
            objAddgmvpolem.setHvBadInsulators2(new BigDecimal(hVBad2));
            objAddgmvpolem.setCct11pService(new BigDecimal(cCT1P1));
            objAddgmvpolem.setCct13pService(new BigDecimal(cCT1P3));
            objAddgmvpolem.setCct21pService(new BigDecimal(cCT2P1));
            objAddgmvpolem.setCct23pService(new BigDecimal(cCT2P3));
            objAddgmvpolem.setCct31pService(new BigDecimal(cCT3P1));
            objAddgmvpolem.setCct33pService(new BigDecimal(cCT3P3));
            objAddgmvpolem.setCct41pService(new BigDecimal(cCT4P1));
            objAddgmvpolem.setCct43pService(new BigDecimal(cCT4P3));
            objAddgmvpolem.setLvCct1Type(new BigDecimal(lVCCT1Type));
            objAddgmvpolem.setLvCct1Name(new BigDecimal(lVCCT1Name));
            objAddgmvpolem.setCct1LvConType(new BigDecimal(lVCCT1ConType));
            objAddgmvpolem.setLvCct2Type(new BigDecimal(lVCCT2Type));
            objAddgmvpolem.setLvCct2Name(new BigDecimal(lVCCT2Name));
            objAddgmvpolem.setCct2LvConType(new BigDecimal(lVCCT2ConType));
            objAddgmvpolem.setLvCct3Type(new BigDecimal(lVCCT3Type));
            objAddgmvpolem.setLvCct3Name(new BigDecimal(lVCCT3Name));
            objAddgmvpolem.setCct3LvConType(new BigDecimal(lVCCT3ConType));
            objAddgmvpolem.setLvCct4Type(new BigDecimal(lVCCT4Type));
            objAddgmvpolem.setLvCct4Name(new BigDecimal(lVCCT4Name));
            objAddgmvpolem.setCct4LvConType(new BigDecimal(lVCCT4ConType));
            objAddgmvpolem.setLvCct5Type(new BigDecimal(lVCCT5Type));
            objAddgmvpolem.setLvCct5Name(new BigDecimal(lVCCT5Name));
            objAddgmvpolem.setCct5LvConType(new BigDecimal(lVCCT5ConType));


            System.out.println("//////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
            /*System.out.println("Area"+objAddgmvpolem.getArea());
            System.out.println("CSC"+objAddgmvpolem.getCsc());
            System.out.println("PoleNo"+objAddgmvpolem.getPoleNo());
            System.out.println("Latitude"+objAddgmvpolem.getGpsLatitude());
            System.out.println("Longitude"+objAddgmvpolem.getGpsLongitude());
            System.out.println("Feeder No"+objAddgmvpolem.getFeederNo());
            System.out.println("Heigth"+objAddgmvpolem.getPoleHeight());
            System.out.println("Type"+objAddgmvpolem.getPoleType());
            System.out.println("Working"+objAddgmvpolem.getWorkingLoad());
            System.out.println("HvLvCombineRun"+objAddgmvpolem.getHvLvCombineRun());
            System.out.println("NoOf33kvcircuits"+objAddgmvpolem.getNoOf33Kvcircuits());
            System.out.println("NoOf11kvcircuits"+objAddgmvpolem.getNoOf11Kvcircuits());
            System.out.println("Nooflvcct"+objAddgmvpolem.getNoOfLvCct());
            System.out.println("Conductor33_1"+objAddgmvpolem.getKv33ConductorCct1());
            System.out.println("Conductor33_2"+objAddgmvpolem.getKv33ConductorCct2());
            System.out.println("Conductor33_3"+objAddgmvpolem.getKv33ConductorCct3());
            System.out.println("Conductor11_1"+objAddgmvpolem.getKv11ConductorCct1());
            System.out.println("Conductor11_2"+objAddgmvpolem.getKv11ConductorCct2());
            System.out.println("Conductor11_3"+objAddgmvpolem.getKv11ConductorCct3());
            System.out.println("LvConductorType"+objAddgmvpolem.getLvConductorType());
            System.out.println("PinShackle"+objAddgmvpolem.getPinShackle());
            System.out.println("StreetLight"+objAddgmvpolem.getStreetLight());
            System.out.println("stay"+objAddgmvpolem.getStay());
            System.out.println("strut"+objAddgmvpolem.getStrut());
            System.out.println("crossarm"+objAddgmvpolem.getCrossArm());
            System.out.println("earth"+objAddgmvpolem.getEarthConductor());
            System.out.println("line end"+objAddgmvpolem.getLineEnd());
            System.out.println("1_1"+objAddgmvpolem.getKv33Cct1Ph1());
            System.out.println("1_2"+objAddgmvpolem.getKv33Cct1Ph2());
            System.out.println("1_3"+objAddgmvpolem.getKv33Cct1Ph3());
            System.out.println("2_1"+objAddgmvpolem.getKv33Cct2Ph1());
            System.out.println("2_2"+objAddgmvpolem.getKv33Cct2Ph2());
            System.out.println("2_3"+objAddgmvpolem.getKv33Cct2Ph3());
            System.out.println("3_1"+objAddgmvpolem.getKv33Cct3Ph1());
            System.out.println("3_2"+objAddgmvpolem.getKv33Cct3Ph2());
            System.out.println("3_3"+objAddgmvpolem.getKv33Cct3Ph3());
            System.out.println("MV switch"+objAddgmvpolem.getMvSwitch());
            System.out.println("transformerc"+objAddgmvpolem.getTransformerCapacity());
            System.out.println("transformert"+objAddgmvpolem.getTransformerType());*/

            final RestTemplate restTemplate = new RestTemplate();


            String url1 = Util.SRT_URL + "MMSAddPoleMntMvMobile";
            System.out.println(" url1 " + url1);
            // trustEveryone();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            //return restTemplate.getForObject(url1, String.class);
            String objReturn = restTemplate.postForObject(url1, objAddgmvpolem, String.class);
            System.out.println(" objReturn: " + objReturn );
            return objReturn;
        }


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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.add_mvpole_mainteinance, menu);
//        return true;
//    }

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
            Intent intent = new Intent(AddMVPolesMaintenance.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddMVPolesMaintenance.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddMVPolesMaintenance.this, AddSupport.class);
            startActivity(intent);


        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddMVPolesMaintenance.this, TM2.class);
            startActivity(intent);


        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddMVPolesMaintenance.this, AddEquipment.class);
            startActivity(intent);


        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddMVPolesMaintenance.this, Login.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(AddMVPolesMaintenance.this, GetNearByTower.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddMVPolesMaintenance.this, AddGantry.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addAutoRecloser) {
            Intent intent = new Intent(AddMVPolesMaintenance.this, AddAutoRecloser.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_addLBSABS) {
            Intent intent = new Intent(AddMVPolesMaintenance.this, AddLBSABS.class);
            startActivity(intent);

        }


        else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(AddMVPolesMaintenance.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(AddMVPolesMaintenance.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(AddMVPolesMaintenance.this, AddTransformers.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(AddMVPolesMaintenance.this, AddMVPoles.class);
            startActivity(intent);

//        }else if (id == R.id.nav_selectMvPoleMantenance) {
//            Intent intent = new Intent(AddMVPolesMaintenance.this, AddMVPolesMaintenance.class);
//            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}