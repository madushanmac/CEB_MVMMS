package com.example.akla.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.akla.login.Util.isConnected;

public class EditMVPoles extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MmsAddmvpole objMvPole;
    MmsAddmvpolecct objMvPoleCct[];

    String phmBranch;

    private FusedLocationProviderClient client;

    //Offline data loading
    String[] values1 = new String[]{};
    String[] values2 = new String[]{};
    String[] values3 = new String[]{};
    HashMap<Integer, String> spinnerMap1 = new HashMap<Integer, String>();
    HashMap<Integer, String> spinnerMap2 = new HashMap<Integer, String>();
    HashMap<Integer, String> spinnerMap3 = new HashMap<Integer, String>();

    //load Province
    Spinner SpinnerProvince;
    String province;
    HashMap<Integer, String> spinnerMapProvince = new HashMap<Integer, String>();
    String[] valuesPro = new String[]{};

    //load Area
    Spinner SpinnerArea;
    String area;
    String valuesArea[] = new String[]{};
    HashMap<Integer,String> SpinnerMapArea = new HashMap<Integer, String>();

    //load CSC
    Spinner SpinnerCSC;
    String csc;
    String[] valuesCsc = new String[]{};
    HashMap<Integer, String> SpinnerMapCsc = new HashMap<Integer, String>();

    //load gantry
    Spinner SpinnerGantry;
    Long gantry;
    String[] valuesGantry = new String[]{};
    HashMap<Integer,Long> SpinnerMapGantry = new HashMap<Integer, Long>();

    //load feeder
    Spinner SpinnerFeeder;
    String feeder;
    String[] valuesFeeder = new String[]{};
    HashMap<Integer, String> SpinnerMapFeeder = new HashMap<Integer, String>();

    //load gantry
    Spinner SpinnerGantry1;
    Long gantry1;
    String[] valuesGantry1 = new String[]{};
    HashMap<Integer,Long> SpinnerMapGantry1 = new HashMap<Integer, Long>();

    //load feeder
    Spinner SpinnerFeeder1;
    String feeder1;
    String[] valuesFeeder1 = new String[]{};
    HashMap<Integer, String> SpinnerMapFeeder1 = new HashMap<Integer, String>();

    //load gantry
    Spinner SpinnerGantry2;
    Long gantry2;
    String[] valuesGantry2 = new String[]{};
    HashMap<Integer,Long> SpinnerMapGantry2 = new HashMap<Integer, Long>();

    //load feeder
    Spinner SpinnerFeeder2;
    String feeder2;
    String[] valuesFeeder2 = new String[]{};
    HashMap<Integer, String> SpinnerMapFeeder2 = new HashMap<Integer, String>();

    //load gantry
    Spinner SpinnerGantry3;
    Long gantry3;
    String[] valuesGantry3 = new String[]{};
    HashMap<Integer,Long> SpinnerMapGantry3 = new HashMap<Integer, Long>();

    //load feeder
    Spinner SpinnerFeeder3;
    String feeder3;
    String[] valuesFeeder3 = new String[]{};
    HashMap<Integer, String> SpinnerMapFeeder3 = new HashMap<Integer, String>();

    Spinner SpinnerTypeNew;
    String typeNew;
    ArrayAdapter<CharSequence> adapternew;

    //load Pole Height
    Spinner SpinnerPoleheight;
    String poleheight;
    ArrayAdapter<CharSequence> adapter1;

    //load Pole Type
    Spinner SpinnerPoletype;
    String poletype="0";
    ArrayAdapter<CharSequence> adapterpoletype;

    //load Working Load
    Spinner SpinnerWorkingload;
    String workingload;
    ArrayAdapter<CharSequence> adapter3;

    //load Strut Height
    Spinner SpinnerStrutheight;
    String strutheight;
    ArrayAdapter<CharSequence> adapterstrutheight;

    //load Strut Type
    Spinner SpinnerStruttype;
    String struttype;
    ArrayAdapter<CharSequence> adapterstruttype;

    //load Strut Working Load
    Spinner SpinnerStrutWorkingload;
    String strutworkingload;
    ArrayAdapter<CharSequence> adapterstrutworkingload;

    //load Conductor
    Spinner SpinnerConductor;
    String conductor;
    ArrayAdapter<CharSequence> adapterconductor;

    //load Conductor Copper Size
    Spinner SpinnerCopperSize;
    String coppersize;
    ArrayAdapter<CharSequence> adaptercoppersize;

    //load Conductor AAAC Size
    Spinner SpinnerAAACSize;
    String aaacsize;
    ArrayAdapter<CharSequence> adapteraaacsize;

    //load Conductor ACSR Size
    Spinner SpinnerACSRSize;
    String acsrsize;
    ArrayAdapter<CharSequence> adapteracsrsize;

    //load Conductor MV ABC Type
    Spinner SpinnerMVABCType;
    String mvabctype;
    ArrayAdapter<CharSequence> adaptermvabctype;

    //load Conductor MV ABC Size
    Spinner SpinnerMVABCSize;
    String mvabcsize;
    ArrayAdapter<CharSequence> adaptermvabcsize;

    //load Insulator
    Spinner SpinnerInsulator;
    String insulator;
    ArrayAdapter<CharSequence> adapterinsulator;

    //load Insulator Type
    Spinner SpinnerInsulatorPin;
    String insulatorpin;
    ArrayAdapter<CharSequence> adapterinsulatorpin;

    //load Insulator Type
    Spinner SpinnerInsulatorShackle;
    String insulatorshackle;
    ArrayAdapter<CharSequence> adapterinsulatorshackle;

    //load Equipment
    Spinner SpinnerEquipment;
    String equipment;
    ArrayAdapter<CharSequence> adapterequipment;


    //load Cross Arm
    Spinner SpinnerEarth;
    String earth;
    ArrayAdapter<CharSequence> adapterearth;

    //load Cross Arm Type
    Spinner SpinnerEarthType;
    String earthtype;
    ArrayAdapter<CharSequence> adapterearthtype;

    //load strut size
    Spinner SpinnerVoltage;
    String voltage;
    ArrayAdapter<CharSequence> adaptervoltage;

    //load strut size
    Spinner SpinnerPhase;
    String phase;
    ArrayAdapter<CharSequence> adapterphase;

    //load insulator type
    Spinner SpinnerInsulatorType;
    String insulatortype;
    ArrayAdapter<CharSequence> adapterinsulatortype;

    //load type
    Spinner SpinnerType;
    String type;
    ArrayAdapter<CharSequence> adaptertype;

    //load Cross Arm
    Spinner SpinnerCrossArmType;
    String crossarm;
    ArrayAdapter<CharSequence> adaptercrossarm;

    //load Cross Arm Type
    Spinner SpinnerCAType;
    String catype;
    ArrayAdapter<CharSequence> adaptercatype;

    //load Cross Arm
    Spinner Spinner1CrossArmType;
    String crossarm1;
    ArrayAdapter<CharSequence> adapter1crossarm;

    //load Cross Arm Type
    Spinner Spinner1CAType;
    String catype1;
    ArrayAdapter<CharSequence> adapter1catype;

    //load Cross Arm
    Spinner Spinner1CrossArmType1;
    String crossarm11;
    ArrayAdapter<CharSequence> adapter1crossarm1;

    //load Cross Arm Type
    Spinner Spinner1CAType1;
    String catype11;
    ArrayAdapter<CharSequence> adapter1catype1;

    //load verticle position
    Spinner SpinnerVerticlePosition;
    String verticleposition;
    ArrayAdapter<CharSequence> adapterverticleposition;

    //load Conductor Type
    Spinner SpinnerConductorType;
    String conductortype;
    ArrayAdapter<CharSequence> adapterconductortype;

    //load Circuit Formation
    Spinner SpinnerCircuitFormation;
    String circuitformation;
    ArrayAdapter<CharSequence> adaptercircuitformation;

    //load strut size
    Spinner SpinnerVoltage1;
    String voltage1;
    ArrayAdapter<CharSequence> adaptervoltage1;

    //load strut size
    Spinner SpinnerPhase1;
    String phase1;
    ArrayAdapter<CharSequence> adapterphase1;

    //load insulator type
    Spinner SpinnerInsulatorType1;
    String insulatortype1;
    ArrayAdapter<CharSequence> adapterinsulatortype1;

    //load type
    Spinner SpinnerType1;
    String type1;
    ArrayAdapter<CharSequence> adaptertype1;

    //load strut size
    Spinner SpinnerVoltage4;
    String voltage4;
    ArrayAdapter<CharSequence> adaptervoltage4;

    //load strut size
    Spinner SpinnerPhase4;
    String phase4;
    ArrayAdapter<CharSequence> adapterphase4;

    //load insulator type
    Spinner SpinnerInsulatorType4;
    String insulatortype4;
    ArrayAdapter<CharSequence> adapterinsulatortype4;

    //load type
    Spinner SpinnerType4;
    String type4;
    ArrayAdapter<CharSequence> adaptertype4;

    //load strut size
    Spinner SpinnerVoltage5;
    String voltage5;
    ArrayAdapter<CharSequence> adaptervoltage5;

    //load strut size
    Spinner SpinnerPhase5;
    String phase5;
    ArrayAdapter<CharSequence> adapterphase5;

    //load insulator type
    Spinner SpinnerInsulatorType5;
    String insulatortype5;
    ArrayAdapter<CharSequence> adapterinsulatortype5;

    //load type
    Spinner SpinnerType5;
    String type5;
    ArrayAdapter<CharSequence> adaptertype5;

    //load strut size
    Spinner SpinnerVoltage6;
    String voltage6;
    ArrayAdapter<CharSequence> adaptervoltage6;

    //load strut size
    Spinner SpinnerPhase6;
    String phase6;
    ArrayAdapter<CharSequence> adapterphase6;

    //load insulator type
    Spinner SpinnerInsulatorType6;
    String insulatortype6;
    ArrayAdapter<CharSequence> adapterinsulatortype6;

    //load type
    Spinner SpinnerType6;
    String type6;
    ArrayAdapter<CharSequence> adaptertype6;

    //load Cross Arm
    Spinner SpinnerCrossArmType1;
    //String crossarm1;
    ArrayAdapter<CharSequence> adaptercrossarm1;

    //load Cross Arm Type
    Spinner SpinnerCAType1;
    //String catype1;
    ArrayAdapter<CharSequence> adaptercatype1;

    //load Cross Arm
    Spinner Spinner2CrossArmType1;
    String crossarm21;
    ArrayAdapter<CharSequence> adapter2crossarm1;

    //load Cross Arm Type
    Spinner Spinner2CAType1;
    String catype21;
    ArrayAdapter<CharSequence> adapter2catype1;

    //load verticle position
    Spinner SpinnerVerticlePosition1;
    String verticleposition1;
    ArrayAdapter<CharSequence> adapterverticleposition1;

    //load Conductor Type
    Spinner SpinnerConductorType1;
    String conductortype1;
    ArrayAdapter<CharSequence> adapterconductortype1;

    //load Conductor Copper Size
    Spinner SpinnerCopperSize1;
    String coppersize1;
    ArrayAdapter<CharSequence> adaptercoppersize1;

    //load Conductor AAAC Size
    Spinner SpinnerAAACSize1;
    String aaacsize1;
    ArrayAdapter<CharSequence> adapteraaacsize1;

    //load Conductor ACSR Size
    Spinner SpinnerACSRSize1;
    String acsrsize1;
    ArrayAdapter<CharSequence> adapteracsrsize1;

    //load Conductor MV ABC Type
    Spinner SpinnerMVABCType1;
    String mvabctype1;
    ArrayAdapter<CharSequence> adaptermvabctype1;

    //load Conductor MV ABC Size
    Spinner SpinnerMVABCSize1;
    String mvabcsize1;
    ArrayAdapter<CharSequence> adaptermvabcsize1;

    //load Circuit Formation
    Spinner SpinnerCircuitFormation1;
    String circuitformation1;
    ArrayAdapter<CharSequence> adaptercircuitformation1;

    //load Source
    Spinner SpinnerSource;
    String circuitsource;
    ArrayAdapter<CharSequence> adaptersource;

    //load Source
    Spinner SpinnerSource1;
    String circuitsource1;
    ArrayAdapter<CharSequence> adaptersource1;

    //load strut size
    Spinner SpinnerVoltage2;
    String voltage2;
    ArrayAdapter<CharSequence> adaptervoltage2;

    //load strut size
    Spinner SpinnerPhase2;
    String phase2;
    ArrayAdapter<CharSequence> adapterphase2;

    //load insulator type
    Spinner SpinnerInsulatorType2;
    String insulatortype2;
    ArrayAdapter<CharSequence> adapterinsulatortype2;

    //load type
    Spinner SpinnerType2;
    String type2;
    ArrayAdapter<CharSequence> adaptertype2;

    //load strut size
    Spinner SpinnerVoltage7;
    String voltage7;
    ArrayAdapter<CharSequence> adaptervoltage7;

    //load strut size
    Spinner SpinnerPhase7;
    String phase7;
    ArrayAdapter<CharSequence> adapterphase7;

    //load insulator type
    Spinner SpinnerInsulatorType7;
    String insulatortype7;
    ArrayAdapter<CharSequence> adapterinsulatortype7;

    //load type
    Spinner SpinnerType7;
    String type7;
    ArrayAdapter<CharSequence> adaptertype7;

    //load strut size
    Spinner SpinnerVoltage8;
    String voltage8;
    ArrayAdapter<CharSequence> adaptervoltage8;

    //load strut size
    Spinner SpinnerPhase8;
    String phase8;
    ArrayAdapter<CharSequence> adapterphase8;

    //load insulator type
    Spinner SpinnerInsulatorType8;
    String insulatortype8;
    ArrayAdapter<CharSequence> adapterinsulatortype8;

    //load type
    Spinner SpinnerType8;
    String type8;
    ArrayAdapter<CharSequence> adaptertype8;

    //load strut size
    Spinner SpinnerVoltage9;
    String voltage9;
    ArrayAdapter<CharSequence> adaptervoltage9;

    //load strut size
    Spinner SpinnerPhase9;
    String phase9;
    ArrayAdapter<CharSequence> adapterphase9;

    //load insulator type
    Spinner SpinnerInsulatorType9;
    String insulatortype9;
    ArrayAdapter<CharSequence> adapterinsulatortype9;

    //load type
    Spinner SpinnerType9;
    String type9;
    ArrayAdapter<CharSequence> adaptertype9;

    //load Cross Arm
    Spinner SpinnerCrossArmType2;
    String crossarm2;
    ArrayAdapter<CharSequence> adaptercrossarm2;

    //load Cross Arm Type
    Spinner SpinnerCAType2;
    String catype2;
    ArrayAdapter<CharSequence> adaptercatype2;

    //load Cross Arm
    Spinner Spinner3CrossArmType1;
    String crossarm31;
    ArrayAdapter<CharSequence> adapter3crossarm1;

    //load Cross Arm Type
    Spinner Spinner3CAType1;
    String catype31;
    ArrayAdapter<CharSequence> adapter3catype1;

    //load verticle position
    Spinner SpinnerVerticlePosition2;
    String verticleposition2;
    ArrayAdapter<CharSequence> adapterverticleposition2;

    //load Conductor Type
    Spinner SpinnerConductorType2;
    String conductortype2;
    ArrayAdapter<CharSequence> adapterconductortype2;

    //load Conductor Copper Size
    Spinner SpinnerCopperSize2;
    String coppersize2;
    ArrayAdapter<CharSequence> adaptercoppersize2;

    //load Conductor AAAC Size
    Spinner SpinnerAAACSize2;
    String aaacsize2;
    ArrayAdapter<CharSequence> adapteraaacsize2;

    //load Conductor ACSR Size
    Spinner SpinnerACSRSize2;
    String acsrsize2;
    ArrayAdapter<CharSequence> adapteracsrsize2;

    //load Conductor MV ABC Type
    Spinner SpinnerMVABCType2;
    String mvabctype2;
    ArrayAdapter<CharSequence> adaptermvabctype2;

    //load Conductor MV ABC Size
    Spinner SpinnerMVABCSize2;
    String mvabcsize2;
    ArrayAdapter<CharSequence> adaptermvabcsize2;

    //load Circuit Formation
    Spinner SpinnerCircuitFormation2;
    String circuitformation2;
    ArrayAdapter<CharSequence> adaptercircuitformation2;

    //load Source
    Spinner SpinnerSource2;
    String circuitsource2;
    ArrayAdapter<CharSequence> adaptersource2;

    //load strut size
    Spinner SpinnerVoltage3;
    String voltage3;
    ArrayAdapter<CharSequence> adaptervoltage3;

    //load strut size
    Spinner SpinnerPhase3;
    String phase3;
    ArrayAdapter<CharSequence> adapterphase3;

    //load strut size
    Spinner SpinnerVoltage10;
    String voltage10;
    ArrayAdapter<CharSequence> adaptervoltage10;

    //load strut size
    Spinner SpinnerPhase10;
    String phase10;
    ArrayAdapter<CharSequence> adapterphase10;

    //load insulator type
    Spinner SpinnerInsulatorType10;
    String insulatortype10;
    ArrayAdapter<CharSequence> adapterinsulatortype10;

    //load type
    Spinner SpinnerType10;
    String type10;
    ArrayAdapter<CharSequence> adaptertype10;

    //load strut size
    Spinner SpinnerVoltage11;
    String voltage11;
    ArrayAdapter<CharSequence> adaptervoltage11;

    //load strut size
    Spinner SpinnerPhase11;
    String phase11;
    ArrayAdapter<CharSequence> adapterphase11;

    //load insulator type
    Spinner SpinnerInsulatorType11;
    String insulatortype11;
    ArrayAdapter<CharSequence> adapterinsulatortype11;

    //load type
    Spinner SpinnerType11;
    String type11;
    ArrayAdapter<CharSequence> adaptertype11;

    //load strut size
    Spinner SpinnerVoltage12;
    String voltage12;
    ArrayAdapter<CharSequence> adaptervoltage12;

    //load strut size
    Spinner SpinnerPhase12;
    String phase12;
    ArrayAdapter<CharSequence> adapterphase12;

    //load insulator type
    Spinner SpinnerInsulatorType12;
    String insulatortype12;
    ArrayAdapter<CharSequence> adapterinsulatortype12;

    //load type
    Spinner SpinnerType12;
    String type12;
    ArrayAdapter<CharSequence> adaptertype12;

    //load insulator type
    Spinner SpinnerInsulatorType3;
    String insulatortype3;
    ArrayAdapter<CharSequence> adapterinsulatortype3;

    //load type
    Spinner SpinnerType3;
    String type3;
    ArrayAdapter<CharSequence> adaptertype3;

    //load Cross Arm
    Spinner SpinnerCrossArmType3;
    String crossarm3;
    ArrayAdapter<CharSequence> adaptercrossarm3;

    //load Cross Arm Type
    Spinner SpinnerCAType3;
    String catype3;
    ArrayAdapter<CharSequence> adaptercatype3;

    //load verticle position
    Spinner SpinnerVerticlePosition3;
    String verticleposition3;
    ArrayAdapter<CharSequence> adapterverticleposition3;

    //load Conductor Type
    Spinner SpinnerConductorType3;
    String conductortype3;
    ArrayAdapter<CharSequence> adapterconductortype3;

    //load Conductor Copper Size
    Spinner SpinnerCopperSize3;
    String coppersize3;
    ArrayAdapter<CharSequence> adaptercoppersize3;

    //load Conductor AAAC Size
    Spinner SpinnerAAACSize3;
    String aaacsize3;
    ArrayAdapter<CharSequence> adapteraaacsize3;

    //load Conductor ACSR Size
    Spinner SpinnerACSRSize3;
    String acsrsize3;
    ArrayAdapter<CharSequence> adapteracsrsize3;

    //load Conductor MV ABC Type
    Spinner SpinnerMVABCType3;
    String mvabctype3;
    ArrayAdapter<CharSequence> adaptermvabctype3;

    //load Conductor MV ABC Size
    Spinner SpinnerMVABCSize3;
    String mvabcsize3;
    ArrayAdapter<CharSequence> adaptermvabcsize3;

    //load Circuit Formation
    Spinner SpinnerCircuitFormation3;
    String circuitformation3;
    ArrayAdapter<CharSequence> adaptercircuitformation3;

    //load Source
    Spinner SpinnerSource3;
    String circuitsource3;
    ArrayAdapter<CharSequence> adaptersource3;

    //load SpinnerStrutTypeNew
    Spinner SpinnerStrutTypeNew;
    String StrutTypeNew;
    ArrayAdapter<CharSequence> adapterstruttypenew;

    //load Stay Voltage
    Spinner SpinnerStayVoltage;
    String stayvoltage;
    ArrayAdapter<CharSequence> adapterstayvoltage;

    //load strut size
    Spinner SpinnerVoltage13;
    String voltage13;
    ArrayAdapter<CharSequence> adaptervoltage13;

    //load strut size
    Spinner SpinnerPhase13;
    String phase13;
    ArrayAdapter<CharSequence> adapterphase13;

    //load insulator type
    Spinner SpinnerInsulatorType13;
    String insulatortype13;
    ArrayAdapter<CharSequence> adapterinsulatortype13;

    //load type
    Spinner SpinnerType13;
    String type13;
    ArrayAdapter<CharSequence> adaptertype13;

    //load strut size
    Spinner SpinnerVoltage14;
    String voltage14;
    ArrayAdapter<CharSequence> adaptervoltage14;

    //load strut size
    Spinner SpinnerPhase14;
    String phase14;
    ArrayAdapter<CharSequence> adapterphase14;

    //load insulator type
    Spinner SpinnerInsulatorType14;
    String insulatortype14;
    ArrayAdapter<CharSequence> adapterinsulatortype14;

    //load type
    Spinner SpinnerType14;
    String type14;
    ArrayAdapter<CharSequence> adaptertype14;

    //load strut size
    Spinner SpinnerVoltage15;
    String voltage15;
    ArrayAdapter<CharSequence> adaptervoltage15;

    //load strut size
    Spinner SpinnerPhase15;
    String phase15;
    ArrayAdapter<CharSequence> adapterphase15;

    //load insulator type
    Spinner SpinnerInsulatorType15;
    String insulatortype15;
    ArrayAdapter<CharSequence> adapterinsulatortype15;

    //load type
    Spinner SpinnerType15;
    String type15;
    ArrayAdapter<CharSequence> adaptertype15;

    //load Stay Voltage
    Spinner SpinnerPoleNo;
    String poleno;
    String poleName;
    String[] valuesPoleNo = new String[]{};
    HashMap<Integer, String> SpinnerMapPoleNo = new HashMap<Integer, String>();
    ArrayAdapter<CharSequence> adapterpoleno;

    //Define Variebles for save data in db

    EditText poleNo;
    TextView latitude;
    TextView longitude;
    EditText noofcircuits;

    //Define Variebles for TextView

    TextView txpoleHeight;
    TextView txworkingload;
    TextView txpoletype;
    TextView txstrutHeight;
    TextView txstrutworkingload;
    TextView txstrutpoletype;
    TextView txstrutpoletype1;
    TextView txstayvoltage;

    TextView txcoppersize;
    TextView txaaacsize;
    TextView txacsrsize;
    TextView txmvabctype;
    TextView txmvabcsize;
    TextView txtinsulatorpin;
    TextView txinsulatorshackle;
    TextView txearthtype;
    TextView txstrutsize;
    TextView txvoltage;
    TextView txphase;
    TextView txinsulatortype;
    TextView txtype;
    TextView txvoltage4;
    TextView txphase4;
    TextView txinsulatortype4;
    TextView txtype4;
    TextView txvoltage5;
    TextView txphase5;
    TextView txinsulatortype5;
    TextView txtype5;
    TextView txvoltage6;
    TextView txphase6;
    TextView txinsulatortype6;
    TextView txtype6;
    TextView txcrossarm;
    TextView txcrossarmtype;
    TextView tx1crossarm;
    TextView tx1crossarmtype;
    TextView txverticleposition;
    TextView txcircuitformation;
    TextView txconductortype;
    TextView txsource;
    TextView txCircuit1;
    TextView txgantry;
    TextView txfeeder;

    TextView txvoltage1;
    TextView txphase1;
    TextView txinsulatortype1;
    TextView txtype1;
    TextView txvoltage7;
    TextView txphase7;
    TextView txinsulatortype7;
    TextView txtype7;
    TextView txvoltage8;
    TextView txphase8;
    TextView txinsulatortype8;
    TextView txtype8;
    TextView txvoltage9;
    TextView txphase9;
    TextView txinsulatortype9;
    TextView txtype9;
    TextView txcrossarm1;
    TextView txcrossarmtype1;
    TextView tx1crossarm1;
    TextView tx1crossarmtype1;
    TextView txverticleposition1;
    TextView txcircuitformation1;
    TextView txconductortype1;
    TextView txsource1;
    TextView txPoleType1;
    TextView txCircuit2;
    TextView txcoppersize1;
    TextView txaaacsize1;
    TextView txacsrsize1;
    TextView txmvabctype1;
    TextView txmvabcsize1;
    TextView txgantry1;
    TextView txfeeder1;

    TextView txvoltage2;
    TextView txphase2;
    TextView txinsulatortype2;
    TextView txtype2;
    TextView txvoltage10;
    TextView txphase10;
    TextView txinsulatortype10;
    TextView txtype10;
    TextView txvoltage11;
    TextView txphase11;
    TextView txinsulatortype11;
    TextView txtype11;
    TextView txvoltage12;
    TextView txphase12;
    TextView txinsulatortype12;
    TextView txtype12;
    TextView txcrossarm2;
    TextView txcrossarmtype2;
    TextView tx2crossarm1;
    TextView tx2crossarmtype1;
    TextView tx3crossarm;
    TextView tx3crossarmtype;
    TextView txverticleposition2;
    TextView txcircuitformation2;
    TextView txconductortype2;
    TextView txsource2;
    TextView txCircuit3;
    TextView txcoppersize2;
    TextView txaaacsize2;
    TextView txacsrsize2;
    TextView txmvabctype2;
    TextView txmvabcsize2;
    TextView txgantry2;
    TextView txfeeder2;

    TextView txvoltage3;
    TextView txphase3;
    TextView txinsulatortype3;
    TextView txtype3;
    TextView txvoltage13;
    TextView txphase13;
    TextView txinsulatortype13;
    TextView txtype13;
    TextView txvoltage14;
    TextView txphase14;
    TextView txinsulatortype14;
    TextView txtype14;
    TextView txvoltage15;
    TextView txphase15;
    TextView txinsulatortype15;
    TextView txtype15;
    TextView txcrossarm3;
    TextView txcrossarmtype3;
    TextView tx3crossarm1;
    TextView tx3crossarmtype1;
    TextView txverticleposition3;
    TextView txcircuitformation3;
    TextView txconductortype3;
    TextView txsource3;
    TextView txCircuit4;
    TextView txcoppersize3;
    TextView txaaacsize3;
    TextView txacsrsize3;
    TextView txmvabctype3;
    TextView txmvabcsize3;
    TextView txgantry3;
    TextView txfeeder3;

    TextView txpoletypenew;

    CheckBox LineEarth;
    CheckBox DownEarth;

    Button savebutton1;
    Button savebutton2;
    Button savebutton3;
    Button savebutton4;
    Button savebutton5;


    //Adapter
    ArrayAdapter<CharSequence> adapter30;
    ArrayAdapter<CharSequence> adapter31;
    ArrayAdapter<CharSequence> adapter32;
    ArrayAdapter<CharSequence> adapter33;
    ArrayAdapter<CharSequence> adapter34;
    ArrayAdapter<CharSequence> adapter35;
    ArrayAdapter<CharSequence> adapter36;


    String poletypeNew="0";
    String struttypeNew;


    int poletypeNewposition;
    int struttypeNewposition;
    int poleheightposition;
    int strutheightposition;
    int poletypeposition;
    int struttypeposition;
    int poleworkloadposition;
    int strutworkloadposition;

    int clickcount=0;
    int clickcount1=0;
    int clickcount2=0;
    int clickcount3=0;
    int clickcount4=0;
    int clickcount5=0;
    int clickcount6=0;
    int clickcount7=0;
    int clickcount8=0;
    int clickcount9=0;
    int clickcount10=0;
    int clickcount11=0;

    TextView TextCrossArm;
    TextView TextCrossArm1;
    TextView TextCrossArm2;
    TextView TextCrossArm3;

    EditText noofcrossarm;
    EditText noofcrossarm1;
    EditText noofcrossarm2;
    EditText noofcrossarm3;

    TextView TextCrossArmType;
    TextView TextCrossArmType1;
    TextView TextCrossArmType2;
    TextView TextCrossArmType3;
    TextView TextCrossArmType4;
    TextView TextCrossArmType5;
    TextView TextCrossArmType6;
    TextView TextCrossArmType7;

    TextView TextNoofInsulator;
    TextView TextNoofInsulator1;
    TextView TextNoofInsulator2;
    TextView TextNoofInsulator3;

    TextView textViewInsulatorTypeType;
    TextView textViewInsulatorTypeType1;
    TextView textViewInsulatorTypeType2;
    TextView textViewInsulatorTypeType3;

    TextView textViewInsulatorTypeType4;
    TextView textViewInsulatorTypeType5;
    TextView textViewInsulatorTypeType6;
    TextView textViewInsulatorTypeType7;

    TextView textViewInsulatorTypeType8;
    TextView textViewInsulatorTypeType9;
    TextView textViewInsulatorTypeType10;
    TextView textViewInsulatorTypeType11;

    TextView textViewInsulatorTypeType12;
    TextView textViewInsulatorTypeType13;
    TextView textViewInsulatorTypeType14;
    TextView textViewInsulatorTypeType15;

    EditText noofinsulator;
    EditText noofinsulator1;
    EditText noofinsulator2;
    EditText noofinsulator3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mvpoles);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        SpinnerArea = findViewById(R.id.area);
        SpinnerCSC = findViewById(R.id.spnCSC);
        SpinnerPoleNo = findViewById(R.id.spnPoleno);
        latitude =  findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);


        SpinnerPoleheight = findViewById(R.id.spnPoleHeight);
        SpinnerPoletype = findViewById(R.id.spnPoleType);
        SpinnerWorkingload =findViewById(R.id.spnWorkingLoad);

        SpinnerStrutheight = findViewById(R.id.spnStrutHeight);
        SpinnerStruttype = findViewById(R.id.spnStrutType);
        SpinnerStrutWorkingload =findViewById(R.id.spnStrutWorkingLoad);

        SpinnerTypeNew  = findViewById(R.id.spnTypeNew);

        SpinnerStayVoltage = findViewById(R.id.spnStayVoltage);
        txstayvoltage = findViewById(R.id.textViewStayvoltage);

        LineEarth = findViewById(R.id.chkLE);
        DownEarth = findViewById(R.id.chkDE);

        txpoleHeight = findViewById(R.id.textViewPoleHeight);
        txpoletype = findViewById(R.id.textViewPoleType);
        txPoleType1  = findViewById(R.id.textViewPoleType1);
        txworkingload = findViewById(R.id.textViewWorkingLoad);

        txstrutHeight = findViewById(R.id.textViewStrutHeight);
        txstrutpoletype = findViewById(R.id.textViewStrutType);
        txstrutpoletype  = findViewById(R.id.textViewStrutType);
        txstrutworkingload = findViewById(R.id.textViewStrutWorkingLoad);
        txstrutpoletype1 = findViewById(R.id.textViewStrutTypeNew);


        // SpinnerStrutSize = findViewById(R.id.spnStrutSize);
       // txstrutsize = findViewById(R.id.textViewStrut);
        txpoletypenew = findViewById(R.id.textViewPoleTypeNew);

        //no of circuits equals 1

        //no of insulator 1

        textViewInsulatorTypeType = findViewById(R.id.textViewInsulatorTypeType);
        SpinnerVoltage = findViewById(R.id.spnVoltage);
        txvoltage = findViewById(R.id.textViewVoltage);

        SpinnerPhase = findViewById(R.id.spnPhase);
        txphase = findViewById(R.id.textViewPhase);

        SpinnerInsulatorType = findViewById(R.id.spnInsulatorType);
        txinsulatortype = findViewById(R.id.textViewInsulatorType);

        SpinnerType = findViewById(R.id.spnType);
        txtype = findViewById(R.id.textViewType);

        //no of insulator 2
        textViewInsulatorTypeType1 = findViewById(R.id.textViewInsulatorTypeType2);
        SpinnerVoltage4 = findViewById(R.id.spnVoltage4);
        txvoltage4 = findViewById(R.id.textViewVoltage4);

        SpinnerPhase4 = findViewById(R.id.spnPhase4);
        txphase4 = findViewById(R.id.textViewPhase4);

        SpinnerInsulatorType4 = findViewById(R.id.spnInsulatorType4);
        txinsulatortype4 = findViewById(R.id.textViewInsulatorType4);

        SpinnerType4 = findViewById(R.id.spnType4);
        txtype4 = findViewById(R.id.textViewType4);

        //no of insulator 3
        textViewInsulatorTypeType2 = findViewById(R.id.textViewInsulatorTypeType3);
        SpinnerVoltage5 = findViewById(R.id.spnVoltage5);
        txvoltage5 = findViewById(R.id.textViewVoltage5);

        SpinnerPhase5 = findViewById(R.id.spnPhase5);
        txphase5 = findViewById(R.id.textViewPhase5);

        SpinnerInsulatorType5 = findViewById(R.id.spnInsulatorType5);
        txinsulatortype5 = findViewById(R.id.textViewInsulatorType5);

        SpinnerType5 = findViewById(R.id.spnType5);
        txtype5 = findViewById(R.id.textViewType5);

        //no of insulator 4
        textViewInsulatorTypeType3 = findViewById(R.id.textViewInsulatorTypeType4);
        SpinnerVoltage6 = findViewById(R.id.spnVoltage6);
        txvoltage6 = findViewById(R.id.textViewVoltage6);

        SpinnerPhase6 = findViewById(R.id.spnPhase6);
        txphase6 = findViewById(R.id.textViewPhase6);

        SpinnerInsulatorType6 = findViewById(R.id.spnInsulatorType6);
        txinsulatortype6 = findViewById(R.id.textViewInsulatorType6);

        SpinnerType6 = findViewById(R.id.spnType6);
        txtype6 = findViewById(R.id.textViewType6);

        SpinnerCrossArmType = findViewById(R.id.spnCrossArmType);
        txcrossarm = findViewById(R.id.textViewCrossArmType);

        SpinnerCAType = findViewById(R.id.spnCAType);
        txcrossarmtype = findViewById(R.id.textViewCAType);

        Spinner1CrossArmType = findViewById(R.id.spn1CrossArmType);
        tx1crossarm = findViewById(R.id.textView1CrossArmType);

        Spinner1CAType = findViewById(R.id.spn1CAType);
        tx1crossarmtype = findViewById(R.id.textView1CAType);

        noofcircuits = findViewById(R.id.etnoofcircuits);
        noofcircuits.setText("0");

        txverticleposition = findViewById(R.id.textViewVerticleposition);
        txcircuitformation =findViewById(R.id.textViewCircuitformation);
        txconductortype = findViewById(R.id.textViewConductortype);
        txsource = findViewById(R.id.textViewSource);
        txCircuit1 = findViewById(R.id.textViewCircuit1);

        txcoppersize = findViewById(R.id.textViewCopperSize);
        txaaacsize = findViewById(R.id.textViewAACSize);
        txacsrsize = findViewById(R.id.textViewACSRSize);
        txmvabctype = findViewById(R.id.textViewMVABCtype);
        txmvabcsize = findViewById(R.id.textViewMVABCSize);

        txgantry = findViewById(R.id.textViewGantry);
        txfeeder = findViewById(R.id.textViewFeeder);

        TextCrossArm= findViewById(R.id.textView1CrossArm);
        noofcrossarm=findViewById(R.id.etcrossArm);

        TextCrossArmType = findViewById(R.id.textViewCrossArmTypeType);
        TextCrossArmType1 = findViewById(R.id.textViewCrossArmTypeType1);

        TextNoofInsulator = findViewById(R.id.textViewNoofInsulator);
        noofinsulator = findViewById(R.id.etnoofInsulator);

        // no of circuits equals 2

        //no of insulator 1

        textViewInsulatorTypeType4 = findViewById(R.id.textViewInsulatorTypeType5);
        SpinnerVoltage1 = findViewById(R.id.spnVoltage1);
        txvoltage1 = findViewById(R.id.textViewVoltage1);

        SpinnerPhase1 = findViewById(R.id.spnPhase1);
        txphase1 = findViewById(R.id.textViewPhase1);

        SpinnerInsulatorType1 = findViewById(R.id.spnInsulatorType1);
        txinsulatortype1 = findViewById(R.id.textViewInsulatorType1);

        SpinnerType1 = findViewById(R.id.spnType1);
        txtype1 = findViewById(R.id.textViewType1);

        //no of insulator 2

        textViewInsulatorTypeType5 = findViewById(R.id.textViewInsulatorTypeType6);
        SpinnerVoltage7 = findViewById(R.id.spnVoltage7);
        txvoltage7 = findViewById(R.id.textViewVoltage7);

        SpinnerPhase7 = findViewById(R.id.spnPhase7);
        txphase7 = findViewById(R.id.textViewPhase7);

        SpinnerInsulatorType7 = findViewById(R.id.spnInsulatorType7);
        txinsulatortype7 = findViewById(R.id.textViewInsulatorType7);

        SpinnerType7 = findViewById(R.id.spnType7);
        txtype7 = findViewById(R.id.textViewType7);

        //no of insulator 3

        textViewInsulatorTypeType6 = findViewById(R.id.textViewInsulatorTypeType7);
        SpinnerVoltage8 = findViewById(R.id.spnVoltage8);
        txvoltage8 = findViewById(R.id.textViewVoltage8);

        SpinnerPhase8 = findViewById(R.id.spnPhase8);
        txphase8 = findViewById(R.id.textViewPhase8);

        SpinnerInsulatorType8 = findViewById(R.id.spnInsulatorType8);
        txinsulatortype8 = findViewById(R.id.textViewInsulatorType8);

        SpinnerType8 = findViewById(R.id.spnType8);
        txtype8 = findViewById(R.id.textViewType8);

        //no of insulator 4

        textViewInsulatorTypeType7 = findViewById(R.id.textViewInsulatorTypeType8);
        SpinnerVoltage9 = findViewById(R.id.spnVoltage9);
        txvoltage9 = findViewById(R.id.textViewVoltage9);

        SpinnerPhase9 = findViewById(R.id.spnPhase9);
        txphase9 = findViewById(R.id.textViewPhase9);

        SpinnerInsulatorType9 = findViewById(R.id.spnInsulatorType9);
        txinsulatortype9 = findViewById(R.id.textViewInsulatorType9);

        SpinnerType9 = findViewById(R.id.spnType9);
        txtype9 = findViewById(R.id.textViewType9);

        SpinnerCrossArmType1 = findViewById(R.id.spnCrossArmType1);
        txcrossarm1 = findViewById(R.id.textViewCrossArmType1);

        SpinnerCAType1 = findViewById(R.id.spnCAType1);
        txcrossarmtype1 = findViewById(R.id.textViewCAType1);

        Spinner1CrossArmType1 = findViewById(R.id.spn1CrossArmType1);
        tx1crossarm1 = findViewById(R.id.textView1CrossArmType1);

        Spinner1CAType1 = findViewById(R.id.spn1CAType1);
        tx1crossarmtype1 = findViewById(R.id.textView1CAType1);

        txverticleposition1 = findViewById(R.id.textViewVerticleposition1);
        txcircuitformation1 =findViewById(R.id.textViewCircuitformation1);
        txconductortype1 = findViewById(R.id.textViewConductortype1);
        txsource1 = findViewById(R.id.textViewSource1);
        txCircuit2 = findViewById(R.id.textViewCircuit2);

        txcoppersize1 = findViewById(R.id.textViewCopperSize1);
        txaaacsize1 = findViewById(R.id.textViewAACSize1);
        txacsrsize1 = findViewById(R.id.textViewACSRSize1);
        txmvabctype1 = findViewById(R.id.textViewMVABCtype1);
        txmvabcsize1 = findViewById(R.id.textViewMVABCSize1);

        txgantry1 = findViewById(R.id.textViewGantry1);
        txfeeder1 = findViewById(R.id.textViewFeeder1);

        TextCrossArm1= findViewById(R.id.textView2CrossArm);
        noofcrossarm1=findViewById(R.id.etcrossArm1);

        TextCrossArmType2 = findViewById(R.id.textViewCrossArmTypeType2);
        TextCrossArmType3 = findViewById(R.id.textViewCrossArmTypeType3);

        TextNoofInsulator1 = findViewById(R.id.textViewNoofInsulator1);
        noofinsulator1 = findViewById(R.id.etnoofInsulator1);
        //no of circuits 3
        //no of insulator 1

        textViewInsulatorTypeType8 = findViewById(R.id.textViewInsulatorTypeType9);
        SpinnerVoltage2 = findViewById(R.id.spnVoltage2);
        txvoltage2 = findViewById(R.id.textViewVoltage2);

        SpinnerPhase2 = findViewById(R.id.spnPhase2);
        txphase2 = findViewById(R.id.textViewPhase2);

        SpinnerInsulatorType2 = findViewById(R.id.spnInsulatorType2);
        txinsulatortype2 = findViewById(R.id.textViewInsulatorType2);

        SpinnerType2 = findViewById(R.id.spnType2);
        txtype2 = findViewById(R.id.textViewType2);

        //no of insulator 2

        textViewInsulatorTypeType9 = findViewById(R.id.textViewInsulatorTypeType10);
        SpinnerVoltage10 = findViewById(R.id.spnVoltage10);
        txvoltage10 = findViewById(R.id.textViewVoltage10);

        SpinnerPhase10 = findViewById(R.id.spnPhase10);
        txphase10 = findViewById(R.id.textViewPhase10);

        SpinnerInsulatorType10 = findViewById(R.id.spnInsulatorType10);
        txinsulatortype10 = findViewById(R.id.textViewInsulatorType10);

        SpinnerType10 = findViewById(R.id.spnType10);
        txtype10 = findViewById(R.id.textViewType10);

        //no of insulator 3

        textViewInsulatorTypeType10 = findViewById(R.id.textViewInsulatorTypeType11);
        SpinnerVoltage11 = findViewById(R.id.spnVoltage11);
        txvoltage11 = findViewById(R.id.textViewVoltage11);

        SpinnerPhase11 = findViewById(R.id.spnPhase11);
        txphase11 = findViewById(R.id.textViewPhase11);

        SpinnerInsulatorType11 = findViewById(R.id.spnInsulatorType11);
        txinsulatortype11 = findViewById(R.id.textViewInsulatorType11);

        SpinnerType11 = findViewById(R.id.spnType11);
        txtype11 = findViewById(R.id.textViewType11);

        //no of insulator 4

        textViewInsulatorTypeType11 = findViewById(R.id.textViewInsulatorTypeType12);
        SpinnerVoltage12 = findViewById(R.id.spnVoltage12);
        txvoltage12 = findViewById(R.id.textViewVoltage12);

        SpinnerPhase12 = findViewById(R.id.spnPhase12);
        txphase12 = findViewById(R.id.textViewPhase12);

        SpinnerInsulatorType12 = findViewById(R.id.spnInsulatorType12);
        txinsulatortype12 = findViewById(R.id.textViewInsulatorType12);

        SpinnerType12 = findViewById(R.id.spnType12);
        txtype12 = findViewById(R.id.textViewType12);

        SpinnerCrossArmType2 = findViewById(R.id.spnCrossArmType2);
        txcrossarm2 = findViewById(R.id.textViewCrossArmType2);

        SpinnerCAType2 = findViewById(R.id.spnCAType2);
        txcrossarmtype2 = findViewById(R.id.textViewCAType2);

        Spinner2CrossArmType1 = findViewById(R.id.spn2CrossArmType1);
        tx2crossarm1 = findViewById(R.id.textView2CrossArmType1);

        Spinner2CAType1 = findViewById(R.id.spn2CAType1);
        tx2crossarmtype1 = findViewById(R.id.textView2CAType1);

        txverticleposition2 = findViewById(R.id.textViewVerticleposition2);
        txcircuitformation2 =findViewById(R.id.textViewCircuitformation2);
        txconductortype2 = findViewById(R.id.textViewConductortype2);
        txsource2 = findViewById(R.id.textViewSource2);
        txCircuit3 = findViewById(R.id.textViewCircuit3);

        txcoppersize2 = findViewById(R.id.textViewCopperSize2);
        txaaacsize2 = findViewById(R.id.textViewAACSize2);
        txacsrsize2 = findViewById(R.id.textViewACSRSize2);
        txmvabctype2 = findViewById(R.id.textViewMVABCtype2);
        txmvabcsize2 = findViewById(R.id.textViewMVABCSize2);

        txgantry2 = findViewById(R.id.textViewGantry2);
        txfeeder2 = findViewById(R.id.textViewFeeder2);

        TextCrossArm2= findViewById(R.id.textView3CrossArm);
        noofcrossarm2=findViewById(R.id.etcrossArm2);

        TextCrossArmType4 = findViewById(R.id.textViewCrossArmTypeType4);
        TextCrossArmType6 = findViewById(R.id.textViewCrossArmTypeType6);

        TextNoofInsulator2 = findViewById(R.id.textViewNoofInsulator2);
        noofinsulator2 = findViewById(R.id.etnoofInsulator2);

        //no of circuits 4

        //no of insulator 1

        textViewInsulatorTypeType12 = findViewById(R.id.textViewInsulatorTypeType13);
        SpinnerVoltage3 = findViewById(R.id.spnVoltage3);
        txvoltage3 = findViewById(R.id.textViewVoltage3);

        SpinnerPhase3 = findViewById(R.id.spnPhase3);
        txphase3 = findViewById(R.id.textViewPhase3);

        SpinnerInsulatorType3 = findViewById(R.id.spnInsulatorType3);
        txinsulatortype3 = findViewById(R.id.textViewInsulatorType3);

        SpinnerType3 = findViewById(R.id.spnType3);
        txtype3 = findViewById(R.id.textViewType3);

        //no of insulator 2

        textViewInsulatorTypeType13 = findViewById(R.id.textViewInsulatorTypeType14);
        SpinnerVoltage13 = findViewById(R.id.spnVoltage13);
        txvoltage13 = findViewById(R.id.textViewVoltage13);

        SpinnerPhase13 = findViewById(R.id.spnPhase13);
        txphase13 = findViewById(R.id.textViewPhase13);

        SpinnerInsulatorType13 = findViewById(R.id.spnInsulatorType13);
        txinsulatortype13 = findViewById(R.id.textViewInsulatorType13);

        SpinnerType13 = findViewById(R.id.spnType13);
        txtype13 = findViewById(R.id.textViewType13);

        //no of insulator 3

        textViewInsulatorTypeType14 = findViewById(R.id.textViewInsulatorTypeType15);
        SpinnerVoltage14 = findViewById(R.id.spnVoltage14);
        txvoltage14 = findViewById(R.id.textViewVoltage14);

        SpinnerPhase14 = findViewById(R.id.spnPhase14);
        txphase14 = findViewById(R.id.textViewPhase14);

        SpinnerInsulatorType14 = findViewById(R.id.spnInsulatorType14);
        txinsulatortype14 = findViewById(R.id.textViewInsulatorType14);

        SpinnerType14 = findViewById(R.id.spnType14);
        txtype14 = findViewById(R.id.textViewType14);

        //no of insulator 4

        textViewInsulatorTypeType15 = findViewById(R.id.textViewInsulatorTypeType16);
        SpinnerVoltage15 = findViewById(R.id.spnVoltage15);
        txvoltage15 = findViewById(R.id.textViewVoltage15);

        SpinnerPhase15 = findViewById(R.id.spnPhase15);
        txphase15 = findViewById(R.id.textViewPhase15);

        SpinnerInsulatorType15 = findViewById(R.id.spnInsulatorType15);
        txinsulatortype15 = findViewById(R.id.textViewInsulatorType15);

        SpinnerType15 = findViewById(R.id.spnType15);
        txtype15 = findViewById(R.id.textViewType15);

        SpinnerCrossArmType3 = findViewById(R.id.spnCrossArmType3);
        txcrossarm3 = findViewById(R.id.textViewCrossArmType3);

        SpinnerCAType3 = findViewById(R.id.spnCAType3);
        txcrossarmtype3 = findViewById(R.id.textViewCAType3);

        Spinner3CrossArmType1 = findViewById(R.id.spn3CrossArmType1);
        tx3crossarm1 = findViewById(R.id.textView3CrossArmType1);

        Spinner3CAType1 = findViewById(R.id.spn3CAType1);
        tx3crossarmtype1 = findViewById(R.id.textView3CAType1);

        txverticleposition3 = findViewById(R.id.textViewVerticleposition3);
        txcircuitformation3 =findViewById(R.id.textViewCircuitformation3);
        txconductortype3 = findViewById(R.id.textViewConductortype3);
        txsource3 = findViewById(R.id.textViewSource3);
        txCircuit4 = findViewById(R.id.textViewCircuit4);

        txcoppersize3 = findViewById(R.id.textViewCopperSize3);
        txaaacsize3 = findViewById(R.id.textViewAACSize3);
        txacsrsize3 = findViewById(R.id.textViewACSRSize3);
        txmvabctype3 = findViewById(R.id.textViewMVABCtype3);
        txmvabcsize3 = findViewById(R.id.textViewMVABCSize3);

        txgantry3 = findViewById(R.id.textViewGantry3);
        txfeeder3 = findViewById(R.id.textViewFeeder3);

        TextCrossArm3= findViewById(R.id.textView4CrossArm);
        noofcrossarm3=findViewById(R.id.etcrossArm3);

        TextCrossArmType5 = findViewById(R.id.textViewCrossArmTypeType5);
        TextCrossArmType7 = findViewById(R.id.textViewCrossArmTypeType7);

        TextNoofInsulator3 = findViewById(R.id.textViewNoofInsulator3);
        noofinsulator3 = findViewById(R.id.etnoofInsulator3);

        noofcircuits = findViewById(R.id.etnoofcircuits);
        noofcircuits.setText("0");

        txverticleposition = findViewById(R.id.textViewVerticleposition);
        txcircuitformation =findViewById(R.id.textViewCircuitformation);
        txconductortype = findViewById(R.id.textViewConductortype);
        txsource = findViewById(R.id.textViewSource);

        savebutton1 = findViewById(R.id.btnSaveDB);

        SpinnerStayVoltage = findViewById(R.id.spnStayVoltage);
        adapterstayvoltage =ArrayAdapter.createFromResource(this,R.array.Cross_Arm_type,android.R.layout.simple_spinner_item);
        adapterstayvoltage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerStayVoltage.setAdapter(adapterstayvoltage);


        SpinnerStrutTypeNew =(Spinner)findViewById(R.id.spnStrutTypeNew);
        adapterstruttypenew =ArrayAdapter.createFromResource(this,R.array.Pole_type_new,android.R.layout.simple_spinner_item);
        adapterstruttypenew.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerStrutTypeNew.setAdapter(adapterstruttypenew);

        SpinnerTypeNew =(Spinner)findViewById(R.id.spnTypeNew);
        adapternew =ArrayAdapter.createFromResource(this,R.array.Pole_type_new,android.R.layout.simple_spinner_item);
        adapternew.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerTypeNew.setAdapter(adapternew);

        SpinnerPoleheight = findViewById(R.id.spnPoleHeight);
        adapter1 =ArrayAdapter.createFromResource(this,R.array.Pole_height,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPoleheight.setAdapter(adapter1);

        adapter30 =ArrayAdapter.createFromResource(this,R.array.Pole_height_wood,android.R.layout.simple_spinner_item);
        adapter30.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter33 =ArrayAdapter.createFromResource(this,R.array.Pole_height_rcpole,android.R.layout.simple_spinner_item);
        adapter33.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SpinnerPoletype = findViewById(R.id.spnPoleType1);
        adapterpoletype =ArrayAdapter.createFromResource(this,R.array.Pole_type_steel,android.R.layout.simple_spinner_item);
        adapterpoletype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPoletype.setAdapter(adapterpoletype);

        adapter31 =ArrayAdapter.createFromResource(this,R.array.Pole_type_steel,android.R.layout.simple_spinner_item);
        adapter31.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter32 =ArrayAdapter.createFromResource(this,R.array.Pole_type_concrete,android.R.layout.simple_spinner_item);
        adapter32.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterpoletype =ArrayAdapter.createFromResource(this,R.array.Pole_type_steel,android.R.layout.simple_spinner_item);
        adapterpoletype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SpinnerWorkingload = findViewById(R.id.spnWorkingLoad);
        adapterstrutworkingload =ArrayAdapter.createFromResource(this,R.array.Working_load,android.R.layout.simple_spinner_item);
        adapterstrutworkingload.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerWorkingload.setAdapter(adapterstrutworkingload);

        adapter34 =ArrayAdapter.createFromResource(this,R.array.Working_load_10m,android.R.layout.simple_spinner_item);
        adapter34.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter35 =ArrayAdapter.createFromResource(this,R.array.Working_load_11m,android.R.layout.simple_spinner_item);
        adapter35.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter36 =ArrayAdapter.createFromResource(this,R.array.Working_load_10m_only,android.R.layout.simple_spinner_item);
        adapter36.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SpinnerStruttype =(Spinner)findViewById(R.id.spnStrutType);
        adapterstruttype =ArrayAdapter.createFromResource(this,R.array.Pole_type,android.R.layout.simple_spinner_item);
        adapterstruttype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerStruttype.setAdapter(adapterstruttype);

        SpinnerStrutWorkingload =findViewById(R.id.spnStrutWorkingLoad);
        adapter3 =ArrayAdapter.createFromResource(this,R.array.Working_load,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerStrutWorkingload.setAdapter(adapter3);



        SpinnerPoletype =(Spinner)findViewById(R.id.spnPoleType);
        adapterpoletype =ArrayAdapter.createFromResource(this,R.array.Pole_type_new,android.R.layout.simple_spinner_item);
        adapterpoletype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPoletype.setAdapter(adapterpoletype);

        SpinnerVoltage =(Spinner)findViewById(R.id.spnVoltage);
        adaptervoltage =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage.setAdapter(adaptervoltage);

        SpinnerPhase =(Spinner)findViewById(R.id.spnPhase);
        adapterphase =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase.setAdapter(adapterphase);

        SpinnerInsulatorType =(Spinner)findViewById(R.id.spnInsulatorType);
        adapterinsulatortype =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType.setAdapter(adapterinsulatortype);

        SpinnerType =(Spinner)findViewById(R.id.spnType);
        adaptertype =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType.setAdapter(adaptertype);

        SpinnerVoltage4 =(Spinner)findViewById(R.id.spnVoltage4);
        adaptervoltage4 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage4.setAdapter(adaptervoltage4);

        SpinnerPhase4 =(Spinner)findViewById(R.id.spnPhase4);
        adapterphase4 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase4.setAdapter(adapterphase4);

        SpinnerInsulatorType4 =(Spinner)findViewById(R.id.spnInsulatorType4);
        adapterinsulatortype4 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType4.setAdapter(adapterinsulatortype4);

        SpinnerType4 =(Spinner)findViewById(R.id.spnType4);
        adaptertype4 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType4.setAdapter(adaptertype4);

        SpinnerVoltage5 =(Spinner)findViewById(R.id.spnVoltage5);
        adaptervoltage5 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage5.setAdapter(adaptervoltage5);

        SpinnerPhase5 =(Spinner)findViewById(R.id.spnPhase5);
        adapterphase5 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase5.setAdapter(adapterphase5);

        SpinnerInsulatorType5 =(Spinner)findViewById(R.id.spnInsulatorType5);
        adapterinsulatortype5 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType5.setAdapter(adapterinsulatortype5);

        SpinnerType5 =(Spinner)findViewById(R.id.spnType5);
        adaptertype5 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType5.setAdapter(adaptertype5);

        SpinnerVoltage6 =(Spinner)findViewById(R.id.spnVoltage6);
        adaptervoltage6 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage6.setAdapter(adaptervoltage6);

        SpinnerPhase6 =(Spinner)findViewById(R.id.spnPhase6);
        adapterphase6 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase6.setAdapter(adapterphase6);

        SpinnerInsulatorType6 =(Spinner)findViewById(R.id.spnInsulatorType6);
        adapterinsulatortype6 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType6.setAdapter(adapterinsulatortype6);

        SpinnerType6 =(Spinner)findViewById(R.id.spnType6);
        adaptertype6 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType6.setAdapter(adaptertype6);

        SpinnerVerticlePosition =(Spinner)findViewById(R.id.spnVerticlePosition);
        adapterverticleposition =ArrayAdapter.createFromResource(this,R.array.verticleposition,android.R.layout.simple_spinner_item);
        adapterverticleposition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVerticlePosition.setAdapter(adapterverticleposition);

        SpinnerCircuitFormation=(Spinner)findViewById(R.id.spnCircuitformation);
        adaptercircuitformation =ArrayAdapter.createFromResource(this,R.array.circuitformation,android.R.layout.simple_spinner_item);
        adaptercircuitformation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCircuitFormation.setAdapter(adaptercircuitformation);

        SpinnerConductorType=(Spinner)findViewById(R.id.spnConductortype);
        adapterconductortype =ArrayAdapter.createFromResource(this,R.array.conductor,android.R.layout.simple_spinner_item);
        adapterconductortype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerConductorType.setAdapter(adapterconductortype);

        SpinnerCopperSize=(Spinner)findViewById(R.id.spnCopperSize);
        adaptercoppersize =ArrayAdapter.createFromResource(this,R.array.copper_size,android.R.layout.simple_spinner_item);
        adaptercoppersize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCopperSize.setAdapter(adaptercoppersize);

        SpinnerAAACSize=(Spinner)findViewById(R.id.spnAAACSize);
        adapteraaacsize =ArrayAdapter.createFromResource(this,R.array.AAAC_size,android.R.layout.simple_spinner_item);
        adapteraaacsize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerAAACSize.setAdapter(adapteraaacsize);

        SpinnerACSRSize=(Spinner)findViewById(R.id.spnACSRSize);
        adapteracsrsize =ArrayAdapter.createFromResource(this,R.array.ACSR_size,android.R.layout.simple_spinner_item);
        adapteracsrsize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerACSRSize.setAdapter(adapteracsrsize);

        SpinnerMVABCType=(Spinner)findViewById(R.id.spnMVABCtype);
        adaptermvabctype =ArrayAdapter.createFromResource(this,R.array.MV_ABC_type,android.R.layout.simple_spinner_item);
        adaptermvabctype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerMVABCType.setAdapter(adaptermvabctype);

        SpinnerMVABCSize=(Spinner)findViewById(R.id.spnMVABCSize);
        adaptermvabcsize =ArrayAdapter.createFromResource(this,R.array.MV_ABC_size,android.R.layout.simple_spinner_item);
        adaptermvabcsize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerMVABCSize.setAdapter(adaptermvabcsize);

        SpinnerCrossArmType=(Spinner)findViewById(R.id.spnCrossArmType);
        adaptercrossarm =ArrayAdapter.createFromResource(this,R.array.Cross_Arm,android.R.layout.simple_spinner_item);
        adaptercrossarm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCrossArmType.setAdapter(adaptercrossarm);

        SpinnerCAType=(Spinner)findViewById(R.id.spnCAType);
        adaptercatype =ArrayAdapter.createFromResource(this,R.array.Cross_Arm_type,android.R.layout.simple_spinner_item);
        adaptercatype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCAType.setAdapter(adaptercatype);

        Spinner1CrossArmType=(Spinner)findViewById(R.id.spn1CrossArmType);
        adapter1crossarm =ArrayAdapter.createFromResource(this,R.array.Cross_Arm,android.R.layout.simple_spinner_item);
        adapter1crossarm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner1CrossArmType.setAdapter(adapter1crossarm);

        Spinner1CAType=(Spinner)findViewById(R.id.spn1CAType);
        adapter1catype =ArrayAdapter.createFromResource(this,R.array.Cross_Arm_type,android.R.layout.simple_spinner_item);
        adapter1catype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner1CAType.setAdapter(adapter1catype);

        SpinnerSource=(Spinner)findViewById(R.id.spnSource);
        adaptersource =ArrayAdapter.createFromResource(this,R.array.Source,android.R.layout.simple_spinner_item);
        adaptersource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerSource.setAdapter(adaptersource);

        SpinnerSource1=(Spinner)findViewById(R.id.spnSource1);
        adaptersource1 =ArrayAdapter.createFromResource(this,R.array.Source,android.R.layout.simple_spinner_item);
        adaptersource1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerSource1.setAdapter(adaptersource1);


        SpinnerVoltage1 =(Spinner)findViewById(R.id.spnVoltage1);
        adaptervoltage1 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage1.setAdapter(adaptervoltage1);

        SpinnerPhase1 =(Spinner)findViewById(R.id.spnPhase1);
        adapterphase1 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase1.setAdapter(adapterphase1);

        SpinnerInsulatorType1 =(Spinner)findViewById(R.id.spnInsulatorType1);
        adapterinsulatortype1 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType1.setAdapter(adapterinsulatortype1);

        SpinnerType1 =(Spinner)findViewById(R.id.spnType1);
        adaptertype1 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType1.setAdapter(adaptertype1);

        SpinnerVoltage7 =(Spinner)findViewById(R.id.spnVoltage7);
        adaptervoltage7 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage7.setAdapter(adaptervoltage7);

        SpinnerPhase7 =(Spinner)findViewById(R.id.spnPhase7);
        adapterphase7 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase7.setAdapter(adapterphase7);

        SpinnerInsulatorType7 =(Spinner)findViewById(R.id.spnInsulatorType7);
        adapterinsulatortype7 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType7.setAdapter(adapterinsulatortype7);

        SpinnerType7 =(Spinner)findViewById(R.id.spnType7);
        adaptertype7 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType7.setAdapter(adaptertype7);

        SpinnerVoltage8 =(Spinner)findViewById(R.id.spnVoltage8);
        adaptervoltage8 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage8.setAdapter(adaptervoltage8);

        SpinnerPhase8 =(Spinner)findViewById(R.id.spnPhase8);
        adapterphase8 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase8.setAdapter(adapterphase8);

        SpinnerInsulatorType8 =(Spinner)findViewById(R.id.spnInsulatorType8);
        adapterinsulatortype8 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType8.setAdapter(adapterinsulatortype8);

        SpinnerType8 =(Spinner)findViewById(R.id.spnType8);
        adaptertype8 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType8.setAdapter(adaptertype8);

        SpinnerVoltage9 =(Spinner)findViewById(R.id.spnVoltage9);
        adaptervoltage9 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage9.setAdapter(adaptervoltage9);

        SpinnerPhase9 =(Spinner)findViewById(R.id.spnPhase9);
        adapterphase9 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase9.setAdapter(adapterphase9);

        SpinnerInsulatorType9 =(Spinner)findViewById(R.id.spnInsulatorType9);
        adapterinsulatortype9 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType9.setAdapter(adapterinsulatortype9);

        SpinnerType9 =(Spinner)findViewById(R.id.spnType9);
        adaptertype9 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType9.setAdapter(adaptertype9);

        SpinnerVerticlePosition1 =(Spinner)findViewById(R.id.spnVerticlePosition1);
        adapterverticleposition1 =ArrayAdapter.createFromResource(this,R.array.verticleposition,android.R.layout.simple_spinner_item);
        adapterverticleposition1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVerticlePosition1.setAdapter(adapterverticleposition1);

        SpinnerCircuitFormation1=(Spinner)findViewById(R.id.spnCircuitformation1);
        adaptercircuitformation1 =ArrayAdapter.createFromResource(this,R.array.circuitformation,android.R.layout.simple_spinner_item);
        adaptercircuitformation1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCircuitFormation1.setAdapter(adaptercircuitformation1);

        SpinnerConductorType1=(Spinner)findViewById(R.id.spnConductortype1);
        adapterconductortype1 =ArrayAdapter.createFromResource(this,R.array.conductor,android.R.layout.simple_spinner_item);
        adapterconductortype1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerConductorType1.setAdapter(adapterconductortype1);

        SpinnerCopperSize1=(Spinner)findViewById(R.id.spnCopperSize1);
        adaptercoppersize1 =ArrayAdapter.createFromResource(this,R.array.copper_size,android.R.layout.simple_spinner_item);
        adaptercoppersize1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCopperSize1.setAdapter(adaptercoppersize1);

        SpinnerAAACSize1=(Spinner)findViewById(R.id.spnAAACSize1);
        adapteraaacsize1 =ArrayAdapter.createFromResource(this,R.array.AAAC_size,android.R.layout.simple_spinner_item);
        adapteraaacsize1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerAAACSize1.setAdapter(adapteraaacsize1);

        SpinnerACSRSize1=(Spinner)findViewById(R.id.spnACSRSize1);
        adapteracsrsize1 =ArrayAdapter.createFromResource(this,R.array.ACSR_size,android.R.layout.simple_spinner_item);
        adapteracsrsize1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerACSRSize1.setAdapter(adapteracsrsize1);

        SpinnerMVABCType1=(Spinner)findViewById(R.id.spnMVABCtype1);
        adaptermvabctype1 =ArrayAdapter.createFromResource(this,R.array.MV_ABC_type,android.R.layout.simple_spinner_item);
        adaptermvabctype1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerMVABCType1.setAdapter(adaptermvabctype1);

        SpinnerMVABCSize1=(Spinner)findViewById(R.id.spnMVABCSize1);
        adaptermvabcsize1 =ArrayAdapter.createFromResource(this,R.array.MV_ABC_size,android.R.layout.simple_spinner_item);
        adaptermvabcsize1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerMVABCSize1.setAdapter(adaptermvabcsize1);

        SpinnerCrossArmType1=(Spinner)findViewById(R.id.spnCrossArmType1);
        adaptercrossarm1 =ArrayAdapter.createFromResource(this,R.array.Cross_Arm,android.R.layout.simple_spinner_item);
        adaptercrossarm1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCrossArmType1.setAdapter(adaptercrossarm1);

        SpinnerCAType1=(Spinner)findViewById(R.id.spnCAType1);
        adaptercatype1 =ArrayAdapter.createFromResource(this,R.array.Cross_Arm_type,android.R.layout.simple_spinner_item);
        adaptercatype1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCAType1.setAdapter(adaptercatype1);

        Spinner1CrossArmType1=(Spinner)findViewById(R.id.spn1CrossArmType1);
        adapter1crossarm1 =ArrayAdapter.createFromResource(this,R.array.Cross_Arm,android.R.layout.simple_spinner_item);
        adapter1crossarm1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner1CrossArmType1.setAdapter(adapter1crossarm1);

        Spinner1CAType1=(Spinner)findViewById(R.id.spn1CAType1);
        adapter1catype1 =ArrayAdapter.createFromResource(this,R.array.Cross_Arm_type,android.R.layout.simple_spinner_item);
        adapter1catype1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner1CAType1.setAdapter(adapter1catype1);

        SpinnerSource2=(Spinner)findViewById(R.id.spnSource2);
        adaptersource2 =ArrayAdapter.createFromResource(this,R.array.Source,android.R.layout.simple_spinner_item);
        adaptersource2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerSource2.setAdapter(adaptersource2);


        SpinnerVoltage2 =(Spinner)findViewById(R.id.spnVoltage2);
        adaptervoltage2 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage2.setAdapter(adaptervoltage2);

        SpinnerPhase2 =(Spinner)findViewById(R.id.spnPhase2);
        adapterphase2 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase2.setAdapter(adapterphase2);

        SpinnerInsulatorType2 =(Spinner)findViewById(R.id.spnInsulatorType2);
        adapterinsulatortype2 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType2.setAdapter(adapterinsulatortype2);

        SpinnerType2 =(Spinner)findViewById(R.id.spnType2);
        adaptertype2 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType2.setAdapter(adaptertype2);

        SpinnerVoltage10 =(Spinner)findViewById(R.id.spnVoltage10);
        adaptervoltage10 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage10.setAdapter(adaptervoltage10);

        SpinnerPhase10 =(Spinner)findViewById(R.id.spnPhase10);
        adapterphase10 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase10.setAdapter(adapterphase10);

        SpinnerInsulatorType10 =(Spinner)findViewById(R.id.spnInsulatorType10);
        adapterinsulatortype10 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType10.setAdapter(adapterinsulatortype10);

        SpinnerType10 =(Spinner)findViewById(R.id.spnType10);
        adaptertype10 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType10.setAdapter(adaptertype10);

        SpinnerVoltage11 =(Spinner)findViewById(R.id.spnVoltage11);
        adaptervoltage11 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage11.setAdapter(adaptervoltage11);

        SpinnerPhase11 =(Spinner)findViewById(R.id.spnPhase11);
        adapterphase11 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase11.setAdapter(adapterphase11);

        SpinnerInsulatorType11 =(Spinner)findViewById(R.id.spnInsulatorType11);
        adapterinsulatortype11 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType11.setAdapter(adapterinsulatortype11);

        SpinnerType11 =(Spinner)findViewById(R.id.spnType11);
        adaptertype11 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType11.setAdapter(adaptertype11);

        SpinnerVoltage12 =(Spinner)findViewById(R.id.spnVoltage12);
        adaptervoltage12 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage12.setAdapter(adaptervoltage12);

        SpinnerPhase12 =(Spinner)findViewById(R.id.spnPhase12);
        adapterphase12 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase12.setAdapter(adapterphase12);

        SpinnerInsulatorType12 =(Spinner)findViewById(R.id.spnInsulatorType12);
        adapterinsulatortype12 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType12.setAdapter(adapterinsulatortype12);

        SpinnerType12 =(Spinner)findViewById(R.id.spnType12);
        adaptertype12 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType12.setAdapter(adaptertype12);

        SpinnerVerticlePosition2 =(Spinner)findViewById(R.id.spnVerticlePosition2);
        adapterverticleposition2 =ArrayAdapter.createFromResource(this,R.array.verticleposition,android.R.layout.simple_spinner_item);
        adapterverticleposition2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVerticlePosition2.setAdapter(adapterverticleposition2);

        SpinnerCircuitFormation2=(Spinner)findViewById(R.id.spnCircuitformation2);
        adaptercircuitformation2 =ArrayAdapter.createFromResource(this,R.array.circuitformation,android.R.layout.simple_spinner_item);
        adaptercircuitformation2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCircuitFormation2.setAdapter(adaptercircuitformation2);

        SpinnerConductorType2=(Spinner)findViewById(R.id.spnConductortype2);
        adapterconductortype2 =ArrayAdapter.createFromResource(this,R.array.conductor,android.R.layout.simple_spinner_item);
        adapterconductortype2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerConductorType2.setAdapter(adapterconductortype2);

        SpinnerCopperSize2=(Spinner)findViewById(R.id.spnCopperSize2);
        adaptercoppersize2 =ArrayAdapter.createFromResource(this,R.array.copper_size,android.R.layout.simple_spinner_item);
        adaptercoppersize2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCopperSize2.setAdapter(adaptercoppersize2);

        SpinnerAAACSize2=(Spinner)findViewById(R.id.spnAAACSize2);
        adapteraaacsize2 =ArrayAdapter.createFromResource(this,R.array.AAAC_size,android.R.layout.simple_spinner_item);
        adapteraaacsize2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerAAACSize2.setAdapter(adapteraaacsize2);

        SpinnerACSRSize2=(Spinner)findViewById(R.id.spnACSRSize2);
        adapteracsrsize2 =ArrayAdapter.createFromResource(this,R.array.ACSR_size,android.R.layout.simple_spinner_item);
        adapteracsrsize2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerACSRSize2.setAdapter(adapteracsrsize2);

        SpinnerMVABCType2=(Spinner)findViewById(R.id.spnMVABCtype2);
        adaptermvabctype2 =ArrayAdapter.createFromResource(this,R.array.MV_ABC_type,android.R.layout.simple_spinner_item);
        adaptermvabctype2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerMVABCType2.setAdapter(adaptermvabctype2);

        SpinnerMVABCSize2=(Spinner)findViewById(R.id.spnMVABCSize2);
        adaptermvabcsize2 =ArrayAdapter.createFromResource(this,R.array.MV_ABC_size,android.R.layout.simple_spinner_item);
        adaptermvabcsize2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerMVABCSize2.setAdapter(adaptermvabcsize2);

        SpinnerCrossArmType2=(Spinner)findViewById(R.id.spnCrossArmType2);
        adaptercrossarm2 =ArrayAdapter.createFromResource(this,R.array.Cross_Arm,android.R.layout.simple_spinner_item);
        adaptercrossarm2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCrossArmType2.setAdapter(adaptercrossarm2);

        SpinnerCAType2=(Spinner)findViewById(R.id.spnCAType2);
        adaptercatype2 =ArrayAdapter.createFromResource(this,R.array.Cross_Arm_type,android.R.layout.simple_spinner_item);
        adaptercatype2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCAType2.setAdapter(adaptercatype2);

        Spinner2CrossArmType1=(Spinner)findViewById(R.id.spn2CrossArmType1);
        adapter2crossarm1 =ArrayAdapter.createFromResource(this,R.array.Cross_Arm,android.R.layout.simple_spinner_item);
        adapter2crossarm1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner2CrossArmType1.setAdapter(adapter2crossarm1);

        Spinner2CAType1=(Spinner)findViewById(R.id.spn2CAType1);
        adapter2catype1 =ArrayAdapter.createFromResource(this,R.array.Cross_Arm_type,android.R.layout.simple_spinner_item);
        adapter2catype1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner2CAType1.setAdapter(adapter2catype1);

        SpinnerSource3=(Spinner)findViewById(R.id.spnSource3);
        adaptersource3 =ArrayAdapter.createFromResource(this,R.array.Source,android.R.layout.simple_spinner_item);
        adaptersource3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerSource3.setAdapter(adaptersource3);


        SpinnerVoltage3 =(Spinner)findViewById(R.id.spnVoltage3);
        adaptervoltage3 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage3.setAdapter(adaptervoltage3);

        SpinnerPhase3 =(Spinner)findViewById(R.id.spnPhase3);
        adapterphase3 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase3.setAdapter(adapterphase3);

        SpinnerInsulatorType3 =(Spinner)findViewById(R.id.spnInsulatorType3);
        adapterinsulatortype3 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType3.setAdapter(adapterinsulatortype3);

        SpinnerType3 =(Spinner)findViewById(R.id.spnType3);
        adaptertype3 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType3.setAdapter(adaptertype3);

        SpinnerVoltage13 =(Spinner)findViewById(R.id.spnVoltage13);
        adaptervoltage13 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage13.setAdapter(adaptervoltage13);

        SpinnerPhase13 =(Spinner)findViewById(R.id.spnPhase13);
        adapterphase13 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase13.setAdapter(adapterphase13);

        SpinnerInsulatorType13 =(Spinner)findViewById(R.id.spnInsulatorType13);
        adapterinsulatortype13 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType13.setAdapter(adapterinsulatortype13);

        SpinnerType13 =(Spinner)findViewById(R.id.spnType13);
        adaptertype13 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType13.setAdapter(adaptertype13);

        SpinnerVoltage14 =(Spinner)findViewById(R.id.spnVoltage14);
        adaptervoltage14 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage14.setAdapter(adaptervoltage14);

        SpinnerPhase14 =(Spinner)findViewById(R.id.spnPhase14);
        adapterphase14 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase14.setAdapter(adapterphase14);

        SpinnerInsulatorType14 =(Spinner)findViewById(R.id.spnInsulatorType14);
        adapterinsulatortype14 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType14.setAdapter(adapterinsulatortype14);

        SpinnerVoltage15 =(Spinner)findViewById(R.id.spnVoltage15);
        adaptervoltage15 =ArrayAdapter.createFromResource(this,R.array.voltage,android.R.layout.simple_spinner_item);
        adaptervoltage15.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVoltage15.setAdapter(adaptervoltage15);

        SpinnerPhase15 =(Spinner)findViewById(R.id.spnPhase15);
        adapterphase15 =ArrayAdapter.createFromResource(this,R.array.phase,android.R.layout.simple_spinner_item);
        adapterphase15.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPhase15.setAdapter(adapterphase15);

        SpinnerInsulatorType15 =(Spinner)findViewById(R.id.spnInsulatorType15);
        adapterinsulatortype15 =ArrayAdapter.createFromResource(this,R.array.insulator_type,android.R.layout.simple_spinner_item);
        adapterinsulatortype15.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerInsulatorType15.setAdapter(adapterinsulatortype15);

        SpinnerType15 =(Spinner)findViewById(R.id.spnType15);
        adaptertype15 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype15.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType15.setAdapter(adaptertype15);

        SpinnerType14 =(Spinner)findViewById(R.id.spnType14);
        adaptertype14 =ArrayAdapter.createFromResource(this,R.array.intype,android.R.layout.simple_spinner_item);
        adaptertype14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerType14.setAdapter(adaptertype14);

        SpinnerVerticlePosition3 =(Spinner)findViewById(R.id.spnVerticlePosition3);
        adapterverticleposition3 =ArrayAdapter.createFromResource(this,R.array.verticleposition,android.R.layout.simple_spinner_item);
        adapterverticleposition3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerVerticlePosition3.setAdapter(adapterverticleposition3);

        SpinnerCircuitFormation3=(Spinner)findViewById(R.id.spnCircuitformation3);
        adaptercircuitformation3 =ArrayAdapter.createFromResource(this,R.array.circuitformation,android.R.layout.simple_spinner_item);
        adaptercircuitformation3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCircuitFormation3.setAdapter(adaptercircuitformation3);

        SpinnerConductorType3=(Spinner)findViewById(R.id.spnConductortype3);
        adapterconductortype3 =ArrayAdapter.createFromResource(this,R.array.conductor,android.R.layout.simple_spinner_item);
        adapterconductortype3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerConductorType3.setAdapter(adapterconductortype3);

        SpinnerCopperSize3=(Spinner)findViewById(R.id.spnCopperSize3);
        adaptercoppersize3 =ArrayAdapter.createFromResource(this,R.array.copper_size,android.R.layout.simple_spinner_item);
        adaptercoppersize3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCopperSize3.setAdapter(adaptercoppersize3);

        SpinnerAAACSize3=(Spinner)findViewById(R.id.spnAAACSize3);
        adapteraaacsize3 =ArrayAdapter.createFromResource(this,R.array.AAAC_size,android.R.layout.simple_spinner_item);
        adapteraaacsize3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerAAACSize3.setAdapter(adapteraaacsize3);

        SpinnerACSRSize3=(Spinner)findViewById(R.id.spnACSRSize3);
        adapteracsrsize3 =ArrayAdapter.createFromResource(this,R.array.ACSR_size,android.R.layout.simple_spinner_item);
        adapteracsrsize3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerACSRSize3.setAdapter(adapteracsrsize3);

        SpinnerMVABCType3=(Spinner)findViewById(R.id.spnMVABCtype3);
        adaptermvabctype3 =ArrayAdapter.createFromResource(this,R.array.MV_ABC_type,android.R.layout.simple_spinner_item);
        adaptermvabctype3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerMVABCType3.setAdapter(adaptermvabctype3);

        SpinnerMVABCSize3=(Spinner)findViewById(R.id.spnMVABCSize3);
        adaptermvabcsize3 =ArrayAdapter.createFromResource(this,R.array.MV_ABC_size,android.R.layout.simple_spinner_item);
        adaptermvabcsize3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerMVABCSize3.setAdapter(adaptermvabcsize3);

        SpinnerCrossArmType3=(Spinner)findViewById(R.id.spnCrossArmType3);
        adaptercrossarm3 =ArrayAdapter.createFromResource(this,R.array.Cross_Arm,android.R.layout.simple_spinner_item);
        adaptercrossarm3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCrossArmType3.setAdapter(adaptercrossarm3);

        SpinnerCAType3=(Spinner)findViewById(R.id.spnCAType3);
        adaptercatype3 =ArrayAdapter.createFromResource(this,R.array.Cross_Arm_type,android.R.layout.simple_spinner_item);
        adaptercatype3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCAType3.setAdapter(adaptercatype3);

        Spinner3CrossArmType1=(Spinner)findViewById(R.id.spn3CrossArmType1);
        adapter3crossarm1 =ArrayAdapter.createFromResource(this,R.array.Cross_Arm,android.R.layout.simple_spinner_item);
        adapter3crossarm1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner3CrossArmType1.setAdapter(adapter3crossarm1);

        Spinner3CAType1=(Spinner)findViewById(R.id.spn3CAType1);
        adapter3catype1 =ArrayAdapter.createFromResource(this,R.array.Cross_Arm_type,android.R.layout.simple_spinner_item);
        adapter3catype1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner3CAType1.setAdapter(adapter3catype1);

        if (!isConnected(EditMVPoles.this)) {
            readExcelArea();
            readExcelProvince();
            readExcelCsc();
        } else {
            new loadProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        //Spinner Area -- Area load by Province
        SpinnerProvince = findViewById(R.id.province);
        SpinnerProvince.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idprovince = spinnerMapProvince.get(parent.getSelectedItemPosition());
                province =idprovince;
                if(!Util.isConnected(EditMVPoles.this)){
                    Toast.makeText(getApplication(),"readExcel: Area " , Toast.LENGTH_SHORT).show();
                }else {
                    new loadAreaByProvince().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Line -- Line load by Area
        SpinnerArea = findViewById(R.id.area);
        SpinnerArea.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idarea = SpinnerMapArea.get(parent.getSelectedItemPosition());
                area = idarea;

                if(!Util.isConnected(EditMVPoles.this)){

                    Toast.makeText(getApplication(),"readExcel: Line" , Toast.LENGTH_SHORT).show();

                }else {
                    new loadCscbyAea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new loadAreaFeederByMvPole().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //load csc -- by area
        SpinnerCSC = findViewById(R.id.spnCSC);
        SpinnerCSC.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getSelectedItem().toString();
                String name = adapterView.getSelectedItem().toString();
                String idCsc = SpinnerMapCsc.get(adapterView.getSelectedItemPosition());
                csc = idCsc;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Spinner Feeder -- Feeder load by Gantry
        SpinnerGantry = findViewById(R.id.gantry);
        SpinnerGantry.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                Long idgantry = SpinnerMapGantry.get(parent.getSelectedItemPosition());
                gantry = idgantry;
                System.out.println(idgantry);

                if(!Util.isConnected(EditMVPoles.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new loadFeederByGantry().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Feeder
        SpinnerFeeder = findViewById(R.id.feeder);
        SpinnerFeeder.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idfeeder = SpinnerMapFeeder.get(parent.getSelectedItemPosition());
                feeder = idfeeder;
                System.out.println(idfeeder);


            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Source
        SpinnerSource = findViewById(R.id.spnSource);
        SpinnerSource.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                if(selectedItem.equals("Gantry")){
                    SpinnerGantry.setVisibility(view.VISIBLE);
                    txgantry.setVisibility(view.VISIBLE);
                    SpinnerFeeder.setVisibility(view.VISIBLE);
                    txfeeder.setVisibility(view.VISIBLE);
                }else{
                    SpinnerGantry.setVisibility(view.GONE);
                    txgantry.setVisibility(view.GONE);
                    SpinnerFeeder.setVisibility(view.GONE);
                    txfeeder.setVisibility(view.GONE);

                }


                if(!Util.isConnected(EditMVPoles.this)){
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new loadGantrybyArea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }


            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SpinnerGantry1 = findViewById(R.id.gantry1);
        SpinnerGantry1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                Long idgantry = SpinnerMapGantry.get(parent.getSelectedItemPosition());
                gantry1= idgantry;
                System.out.println(idgantry);

                if(!Util.isConnected(EditMVPoles.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new loadFeederByGantry1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SpinnerFeeder1 = findViewById(R.id.feeder1);
        SpinnerFeeder1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idfeeder = SpinnerMapFeeder.get(parent.getSelectedItemPosition());
                feeder1 = idfeeder;
                System.out.println(idfeeder);


            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Source 1
        SpinnerSource1 = findViewById(R.id.spnSource1);
        SpinnerSource1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                if(selectedItem.equals("Gantry")){
                    SpinnerGantry1.setVisibility(view.VISIBLE);
                    txgantry1.setVisibility(view.VISIBLE);
                    SpinnerFeeder1.setVisibility(view.VISIBLE);
                    txfeeder1.setVisibility(view.VISIBLE);
                }else{
                    SpinnerGantry1.setVisibility(view.GONE);
                    txgantry1.setVisibility(view.GONE);
                    SpinnerFeeder1.setVisibility(view.GONE);
                    txfeeder1.setVisibility(view.GONE);

                }

                if(!Util.isConnected(EditMVPoles.this)){
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new loadGantrybyArea1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }


            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SpinnerGantry2 = findViewById(R.id.gantry2);
        SpinnerGantry2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("ooooooooooooooooooooooooooooooooooooo");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                Long idgantry = SpinnerMapGantry.get(parent.getSelectedItemPosition());
                gantry2 = idgantry;
                System.out.println(idgantry);

                if(!Util.isConnected(EditMVPoles.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new loadFeederByGantry2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SpinnerFeeder2 = findViewById(R.id.feeder2);
        SpinnerFeeder2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idfeeder = SpinnerMapFeeder.get(parent.getSelectedItemPosition());
                feeder2 = idfeeder;
                System.out.println(idfeeder);


            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //Spinner Source 2
        SpinnerSource2 = findViewById(R.id.spnSource2);
        SpinnerSource2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                if(selectedItem.equals("Gantry")){
                    SpinnerGantry2.setVisibility(view.VISIBLE);
                    txgantry2.setVisibility(view.VISIBLE);
                    SpinnerFeeder2.setVisibility(view.VISIBLE);
                    txfeeder2.setVisibility(view.VISIBLE);
                }else{
                    SpinnerGantry2.setVisibility(view.GONE);
                    txgantry2.setVisibility(view.GONE);
                    SpinnerFeeder2.setVisibility(view.GONE);
                    txfeeder2.setVisibility(view.GONE);

                }
                if(!Util.isConnected(EditMVPoles.this)){
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new loadGantrybyArea2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }


            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SpinnerGantry3 = findViewById(R.id.gantry3);
        SpinnerGantry3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                Long idgantry = SpinnerMapGantry.get(parent.getSelectedItemPosition());
                gantry3 = idgantry;
                System.out.println(idgantry);

                if(!Util.isConnected(EditMVPoles.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new loadFeederByGantry3().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SpinnerFeeder3 = findViewById(R.id.feeder3);
        SpinnerFeeder3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idfeeder = SpinnerMapFeeder.get(parent.getSelectedItemPosition());
                feeder3 = idfeeder;
                System.out.println(idfeeder);


            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Source 3
        SpinnerSource3 = findViewById(R.id.spnSource3);
        SpinnerSource3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                if(selectedItem.equals("Gantry")){
                    SpinnerGantry3.setVisibility(view.VISIBLE);
                    txgantry3.setVisibility(view.VISIBLE);
                    SpinnerFeeder3.setVisibility(view.VISIBLE);
                    txfeeder3.setVisibility(view.VISIBLE);
                }else{
                    SpinnerGantry3.setVisibility(view.GONE);
                    txgantry3.setVisibility(view.GONE);
                    SpinnerFeeder3.setVisibility(view.GONE);
                    txfeeder3.setVisibility(view.GONE);

                }
                if(!Util.isConnected(EditMVPoles.this)){
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new loadGantrybyArea3().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }


            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner mv pole -- load by Area
        SpinnerPoleNo = findViewById(R.id.spnPoleno);
        SpinnerPoleNo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                poleName = selectedItem;
                String idmvpole = SpinnerMapPoleNo.get(parent.getSelectedItemPosition());
                poleno = idmvpole;
                System.out.println(poleno+"KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                if(!Util.isConnected(EditMVPoles.this)){
                    //readExcelLine();
                    Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

                }else {
                    new loadMvPoleObj().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    new loadMvPoleCirciutsObj().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }


            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        SpinnerStrutTypeNew =(Spinner)findViewById(R.id.spnStrutTypeNew);
        SpinnerStrutTypeNew.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
                String selectedItem = parent.getSelectedItem().toString();
                System.out.println(selectedItem);
                struttypeNew = selectedItem;
                struttypeNewposition=position;

                if(selectedItem.equals("Wood")){


                    SpinnerStrutheight.setAdapter(adapter30);

                    SpinnerStrutheight.setVisibility(view.VISIBLE);
                    txstrutHeight.setVisibility(view.VISIBLE);
                    SpinnerStrutWorkingload.setVisibility(view.GONE);
                    SpinnerStruttype.setVisibility(view.GONE);
                    txstrutpoletype.setVisibility(view.GONE);
                    txstrutworkingload.setVisibility(view.GONE);


                }else if(selectedItem.equals("Steel")){

                    SpinnerStruttype.setAdapter(adapter31);

                    SpinnerStruttype.setVisibility(view.VISIBLE);
                    txstrutpoletype.setVisibility(view.VISIBLE);
                    SpinnerStrutWorkingload.setVisibility(view.GONE);
                    txstrutworkingload.setVisibility(view.GONE);
                    SpinnerStrutheight.setVisibility(view.GONE);
                    txstrutHeight.setVisibility(view.GONE);

                }else if(selectedItem.equals("Concrete")){
                    SpinnerStruttype.setAdapter(adapter32);

                    SpinnerStrutheight.setVisibility(view.VISIBLE);
                    SpinnerStrutWorkingload.setVisibility(view.VISIBLE);
                    SpinnerStruttype.setVisibility(view.VISIBLE);
                    txstrutHeight.setVisibility(view.VISIBLE);
                    txstrutpoletype.setVisibility(view.VISIBLE);
                    txstrutworkingload.setVisibility(view.VISIBLE);

                }else{
                    SpinnerStruttype.setVisibility(view.GONE);
                    txstrutpoletype.setVisibility(view.GONE);
                    SpinnerStrutWorkingload.setVisibility(view.GONE);
                    txstrutworkingload.setVisibility(view.GONE);
                    SpinnerStrutheight.setVisibility(view.GONE);
                    txstrutHeight.setVisibility(view.GONE);

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        //Spinner
        SpinnerTypeNew = findViewById(R.id.spnTypeNew);
        SpinnerTypeNew.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHH"+position);
                String selectedItem = parent.getSelectedItem().toString();
                System.out.println("HHHHHHHHHHH"+selectedItem);
                poletypeNew = selectedItem;
                poletypeNewposition = position;

                if(selectedItem.equals("Wood")){


                    SpinnerPoleheight.setAdapter(adapter30);

                    SpinnerPoleheight.setVisibility(view.VISIBLE);
                    txpoleHeight.setVisibility(view.VISIBLE);
                    SpinnerWorkingload.setVisibility(view.GONE);
                    SpinnerPoletype.setVisibility(view.GONE);
                    txPoleType1.setVisibility(view.GONE);
                    txworkingload.setVisibility(view.GONE);


                }else if(selectedItem.equals("Steel")){

                    SpinnerPoletype.setAdapter(adapter31);

                    SpinnerPoletype.setVisibility(view.VISIBLE);
                    txPoleType1.setVisibility(view.VISIBLE);
                    SpinnerWorkingload.setVisibility(view.GONE);
                    txworkingload.setVisibility(view.GONE);
                    SpinnerPoleheight.setVisibility(view.GONE);
                    txpoleHeight.setVisibility(view.GONE);

                }else if(selectedItem.equals("Concrete")){
                    SpinnerPoletype.setAdapter(adapter32);

                    SpinnerPoleheight.setVisibility(view.VISIBLE);
                    SpinnerWorkingload.setVisibility(view.VISIBLE);
                    SpinnerPoletype.setVisibility(view.VISIBLE);
                    txpoleHeight.setVisibility(view.VISIBLE);
                    txPoleType1.setVisibility(view.VISIBLE);
                    txworkingload.setVisibility(view.VISIBLE);

                }else{
                    SpinnerPoletype.setVisibility(view.GONE);
                    txPoleType1.setVisibility(view.GONE);
                    SpinnerWorkingload.setVisibility(view.GONE);
                    txworkingload.setVisibility(view.GONE);
                    SpinnerPoleheight.setVisibility(view.GONE);
                    txpoleHeight.setVisibility(view.GONE);

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Srut Type

        SpinnerStruttype =(Spinner)findViewById(R.id.spnStrutType);
        SpinnerStruttype.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                struttype = parent.getSelectedItem().toString();
                System.out.println(selectedItem+"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
                System.out.println(struttype+"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
                struttypeposition=position;
                if(selectedItem.equals("RC Pole")) {
                    SpinnerStrutheight.setAdapter(adapter30);
                }else{
                    SpinnerStrutheight.setAdapter(adapter33);
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Pole Type
        SpinnerPoletype =(Spinner)findViewById(R.id.spnPoleType1);
        SpinnerPoletype.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                poletype = parent.getSelectedItem().toString();
                System.out.println(selectedItem+"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
                System.out.println(poletype+"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
                poletypeposition=position;
                if(selectedItem.equals("RC Pole")) {
                    SpinnerPoleheight.setAdapter(adapter30);
                }else{
                    SpinnerPoleheight.setAdapter(adapter33);
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        //Spinner Strut height
        SpinnerStrutheight = findViewById(R.id.spnStrutHeight);
        SpinnerStrutheight.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println(struttypeNew+"QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
                String selectedItem = parent.getSelectedItem().toString();
                strutheightposition = position;
                if(struttypeNew.equals("Wood")) {
                    //SpinnerWorkingload.setAdapter(adapter34);
                }else if(selectedItem.equals("10m") && struttype.equals("RC Pole")){
                    SpinnerStrutWorkingload.setAdapter(adapter34);
                    SpinnerStrutWorkingload.setVisibility(view.VISIBLE);
                    txstrutworkingload.setVisibility(view.VISIBLE);
                }else if(selectedItem.equals("10m") && struttype.equals("Pre Stressed Pole")){
                    SpinnerStrutWorkingload.setAdapter(adapter36);
                    SpinnerStrutWorkingload.setVisibility(view.VISIBLE);
                    txstrutworkingload.setVisibility(view.VISIBLE);
                }else if(selectedItem.equals("11m")){
                    SpinnerStrutWorkingload.setAdapter(adapter35);
                    SpinnerStrutWorkingload.setVisibility(view.VISIBLE);
                    txstrutworkingload.setVisibility(view.VISIBLE);
                }else if(selectedItem.equals("13m")){
                    SpinnerStrutWorkingload.setAdapter(adapter35);
                    SpinnerStrutWorkingload.setVisibility(view.VISIBLE);
                    txstrutworkingload.setVisibility(view.VISIBLE);
                }else if(selectedItem.equals("9m") && struttype.equals("RC Pole")){
                    SpinnerStrutWorkingload.setVisibility(view.GONE);
                    txstrutworkingload.setVisibility(view.GONE);
                }else{

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //Spinner Pole height
        SpinnerPoleheight =(Spinner)findViewById(R.id.spnPoleHeight);
        SpinnerPoleheight.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println(poletypeNew+"QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
                System.out.println(position+"GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
                String selectedItem = parent.getSelectedItem().toString();
                poleheight = selectedItem;
                poleheightposition = position;
                if(poletypeNew.equals("Wood")) {
                    //SpinnerWorkingload.setAdapter(adapter34);
                }else if(selectedItem.equals("10m") && poletype.equals("RC Pole")){
                    SpinnerWorkingload.setAdapter(adapter34);
                    SpinnerWorkingload.setVisibility(view.VISIBLE);
                    txworkingload.setVisibility(view.VISIBLE);
                }else if(selectedItem.equals("10m") && poletype.equals("Pre Stressed Pole")){
                    SpinnerWorkingload.setAdapter(adapter36);
                    SpinnerWorkingload.setVisibility(view.VISIBLE);
                    txworkingload.setVisibility(view.VISIBLE);
                }else if(selectedItem.equals("11m")){
                    SpinnerWorkingload.setAdapter(adapter35);
                    SpinnerWorkingload.setVisibility(view.VISIBLE);
                    txworkingload.setVisibility(view.VISIBLE);
                }else if(selectedItem.equals("13m")){
                    SpinnerWorkingload.setAdapter(adapter35);
                    SpinnerWorkingload.setVisibility(view.VISIBLE);
                    txworkingload.setVisibility(view.VISIBLE);
                }else if(selectedItem.equals("9m") && poletype.equals("RC Pole")){
                    SpinnerWorkingload.setVisibility(view.GONE);
                    txworkingload.setVisibility(view.GONE);
                }else{

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner Pole Type
        SpinnerWorkingload =(Spinner)findViewById(R.id.spnWorkingLoad);
        SpinnerWorkingload.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                poleworkloadposition=position;

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SpinnerConductorType = findViewById(R.id.spnConductortype);
        SpinnerConductorType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                if(selectedItem.equals("Copper")){

                    SpinnerCopperSize.setVisibility(view.VISIBLE);
                    txcoppersize.setVisibility(view.VISIBLE);
                    SpinnerAAACSize.setVisibility(view.GONE);
                    txaaacsize.setVisibility(view.GONE);
                    SpinnerACSRSize.setVisibility(view.GONE);
                    txacsrsize.setVisibility(view.GONE);
                    SpinnerMVABCSize.setVisibility(view.GONE);
                    txmvabcsize.setVisibility(view.GONE);
                    SpinnerMVABCType.setVisibility(view.GONE);
                    txmvabctype.setVisibility(view.GONE);


                }else if(selectedItem.equals("AAAC")){

                    SpinnerCopperSize.setVisibility(view.GONE);
                    txcoppersize.setVisibility(view.GONE);
                    SpinnerAAACSize.setVisibility(view.VISIBLE);
                    txaaacsize.setVisibility(view.VISIBLE);
                    SpinnerACSRSize.setVisibility(view.GONE);
                    txacsrsize.setVisibility(view.GONE);
                    SpinnerMVABCSize.setVisibility(view.GONE);
                    txmvabcsize.setVisibility(view.GONE);
                    SpinnerMVABCType.setVisibility(view.GONE);
                    txmvabctype.setVisibility(view.GONE);

                }else if(selectedItem.equals("ACSR")){

                    SpinnerCopperSize.setVisibility(view.GONE);
                    txcoppersize.setVisibility(view.GONE);
                    SpinnerAAACSize.setVisibility(view.GONE);
                    txaaacsize.setVisibility(view.GONE);
                    SpinnerACSRSize.setVisibility(view.VISIBLE);
                    txacsrsize.setVisibility(view.VISIBLE);
                    SpinnerMVABCSize.setVisibility(view.GONE);
                    txmvabcsize.setVisibility(view.GONE);
                    SpinnerMVABCType.setVisibility(view.GONE);
                    txmvabctype.setVisibility(view.GONE);

                }else if(selectedItem.equals("MV ABC")){

                    SpinnerCopperSize.setVisibility(view.GONE);
                    txcoppersize.setVisibility(view.GONE);
                    SpinnerAAACSize.setVisibility(view.GONE);
                    txaaacsize.setVisibility(view.GONE);
                    SpinnerACSRSize.setVisibility(view.GONE);
                    txacsrsize.setVisibility(view.GONE);
                    SpinnerMVABCSize.setVisibility(view.VISIBLE);
                    txmvabcsize.setVisibility(view.VISIBLE);
                    SpinnerMVABCType.setVisibility(view.VISIBLE);
                    txmvabctype.setVisibility(view.VISIBLE);

                }else{

                    SpinnerCopperSize.setVisibility(view.GONE);
                    txcoppersize.setVisibility(view.GONE);
                    SpinnerAAACSize.setVisibility(view.GONE);
                    txaaacsize.setVisibility(view.GONE);
                    SpinnerACSRSize.setVisibility(view.GONE);
                    txacsrsize.setVisibility(view.GONE);
                    SpinnerMVABCSize.setVisibility(view.GONE);
                    txmvabcsize.setVisibility(view.GONE);
                    SpinnerMVABCType.setVisibility(view.GONE);
                    txmvabctype.setVisibility(view.GONE);

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SpinnerConductorType1 = findViewById(R.id.spnConductortype1);
        SpinnerConductorType1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                if(selectedItem.equals("Copper")){

                    SpinnerCopperSize1.setVisibility(view.VISIBLE);
                    txcoppersize1.setVisibility(view.VISIBLE);
                    SpinnerAAACSize1.setVisibility(view.GONE);
                    txaaacsize1.setVisibility(view.GONE);
                    SpinnerACSRSize1.setVisibility(view.GONE);
                    txacsrsize1.setVisibility(view.GONE);
                    SpinnerMVABCSize1.setVisibility(view.GONE);
                    txmvabcsize1.setVisibility(view.GONE);
                    SpinnerMVABCType1.setVisibility(view.GONE);
                    txmvabctype1.setVisibility(view.GONE);


                }else if(selectedItem.equals("AAAC")){

                    SpinnerCopperSize1.setVisibility(view.GONE);
                    txcoppersize1.setVisibility(view.GONE);
                    SpinnerAAACSize1.setVisibility(view.VISIBLE);
                    txaaacsize1.setVisibility(view.VISIBLE);
                    SpinnerACSRSize1.setVisibility(view.GONE);
                    txacsrsize1.setVisibility(view.GONE);
                    SpinnerMVABCSize1.setVisibility(view.GONE);
                    txmvabcsize1.setVisibility(view.GONE);
                    SpinnerMVABCType1.setVisibility(view.GONE);
                    txmvabctype1.setVisibility(view.GONE);

                }else if(selectedItem.equals("ACSR")){

                    SpinnerCopperSize1.setVisibility(view.GONE);
                    txcoppersize1.setVisibility(view.GONE);
                    SpinnerAAACSize1.setVisibility(view.GONE);
                    txaaacsize1.setVisibility(view.GONE);
                    SpinnerACSRSize1.setVisibility(view.VISIBLE);
                    txacsrsize1.setVisibility(view.VISIBLE);
                    SpinnerMVABCSize1.setVisibility(view.GONE);
                    txmvabcsize1.setVisibility(view.GONE);
                    SpinnerMVABCType1.setVisibility(view.GONE);
                    txmvabctype1.setVisibility(view.GONE);

                }else if(selectedItem.equals("MV ABC")){

                    SpinnerCopperSize1.setVisibility(view.GONE);
                    txcoppersize1.setVisibility(view.GONE);
                    SpinnerAAACSize1.setVisibility(view.GONE);
                    txaaacsize1.setVisibility(view.GONE);
                    SpinnerACSRSize1.setVisibility(view.GONE);
                    txacsrsize1.setVisibility(view.GONE);
                    SpinnerMVABCSize1.setVisibility(view.VISIBLE);
                    txmvabcsize1.setVisibility(view.VISIBLE);
                    SpinnerMVABCType1.setVisibility(view.VISIBLE);
                    txmvabctype1.setVisibility(view.VISIBLE);

                }else{

                    SpinnerCopperSize1.setVisibility(view.GONE);
                    txcoppersize1.setVisibility(view.GONE);
                    SpinnerAAACSize1.setVisibility(view.GONE);
                    txaaacsize1.setVisibility(view.GONE);
                    SpinnerACSRSize1.setVisibility(view.GONE);
                    txacsrsize1.setVisibility(view.GONE);
                    SpinnerMVABCSize1.setVisibility(view.GONE);
                    txmvabcsize1.setVisibility(view.GONE);
                    SpinnerMVABCType1.setVisibility(view.GONE);
                    txmvabctype1.setVisibility(view.GONE);

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SpinnerConductorType2 = findViewById(R.id.spnConductortype2);
        SpinnerConductorType2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                if(selectedItem.equals("Copper")){

                    SpinnerCopperSize2.setVisibility(view.VISIBLE);
                    txcoppersize2.setVisibility(view.VISIBLE);
                    SpinnerAAACSize2.setVisibility(view.GONE);
                    txaaacsize2.setVisibility(view.GONE);
                    SpinnerACSRSize2.setVisibility(view.GONE);
                    txacsrsize2.setVisibility(view.GONE);
                    SpinnerMVABCSize2.setVisibility(view.GONE);
                    txmvabcsize2.setVisibility(view.GONE);
                    SpinnerMVABCType2.setVisibility(view.GONE);
                    txmvabctype2.setVisibility(view.GONE);


                }else if(selectedItem.equals("AAAC")){

                    SpinnerCopperSize2.setVisibility(view.GONE);
                    txcoppersize2.setVisibility(view.GONE);
                    SpinnerAAACSize2.setVisibility(view.VISIBLE);
                    txaaacsize2.setVisibility(view.VISIBLE);
                    SpinnerACSRSize2.setVisibility(view.GONE);
                    txacsrsize2.setVisibility(view.GONE);
                    SpinnerMVABCSize2.setVisibility(view.GONE);
                    txmvabcsize2.setVisibility(view.GONE);
                    SpinnerMVABCType2.setVisibility(view.GONE);
                    txmvabctype2.setVisibility(view.GONE);

                }else if(selectedItem.equals("ACSR")){

                    SpinnerCopperSize2.setVisibility(view.GONE);
                    txcoppersize2.setVisibility(view.GONE);
                    SpinnerAAACSize2.setVisibility(view.GONE);
                    txaaacsize2.setVisibility(view.GONE);
                    SpinnerACSRSize2.setVisibility(view.VISIBLE);
                    txacsrsize2.setVisibility(view.VISIBLE);
                    SpinnerMVABCSize2.setVisibility(view.GONE);
                    txmvabcsize2.setVisibility(view.GONE);
                    SpinnerMVABCType2.setVisibility(view.GONE);
                    txmvabctype2.setVisibility(view.GONE);

                }else if(selectedItem.equals("MV ABC")){

                    SpinnerCopperSize2.setVisibility(view.GONE);
                    txcoppersize2.setVisibility(view.GONE);
                    SpinnerAAACSize2.setVisibility(view.GONE);
                    txaaacsize2.setVisibility(view.GONE);
                    SpinnerACSRSize2.setVisibility(view.GONE);
                    txacsrsize2.setVisibility(view.GONE);
                    SpinnerMVABCSize2.setVisibility(view.VISIBLE);
                    txmvabcsize2.setVisibility(view.VISIBLE);
                    SpinnerMVABCType2.setVisibility(view.VISIBLE);
                    txmvabctype2.setVisibility(view.VISIBLE);

                }else{

                    SpinnerCopperSize2.setVisibility(view.GONE);
                    txcoppersize2.setVisibility(view.GONE);
                    SpinnerAAACSize2.setVisibility(view.GONE);
                    txaaacsize2.setVisibility(view.GONE);
                    SpinnerACSRSize2.setVisibility(view.GONE);
                    txacsrsize2.setVisibility(view.GONE);
                    SpinnerMVABCSize2.setVisibility(view.GONE);
                    txmvabcsize2.setVisibility(view.GONE);
                    SpinnerMVABCType2.setVisibility(view.GONE);
                    txmvabctype2.setVisibility(view.GONE);

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SpinnerConductorType3 = findViewById(R.id.spnConductortype3);
        SpinnerConductorType3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String selectedItem = parent.getSelectedItem().toString();
                if(selectedItem.equals("Copper")){

                    SpinnerCopperSize3.setVisibility(view.VISIBLE);
                    txcoppersize3.setVisibility(view.VISIBLE);
                    SpinnerAAACSize3.setVisibility(view.GONE);
                    txaaacsize3.setVisibility(view.GONE);
                    SpinnerACSRSize3.setVisibility(view.GONE);
                    txacsrsize3.setVisibility(view.GONE);
                    SpinnerMVABCSize3.setVisibility(view.GONE);
                    txmvabcsize3.setVisibility(view.GONE);
                    SpinnerMVABCType3.setVisibility(view.GONE);
                    txmvabctype3.setVisibility(view.GONE);


                }else if(selectedItem.equals("AAAC")){

                    SpinnerCopperSize3.setVisibility(view.GONE);
                    txcoppersize3.setVisibility(view.GONE);
                    SpinnerAAACSize3.setVisibility(view.VISIBLE);
                    txaaacsize3.setVisibility(view.VISIBLE);
                    SpinnerACSRSize3.setVisibility(view.GONE);
                    txacsrsize3.setVisibility(view.GONE);
                    SpinnerMVABCSize3.setVisibility(view.GONE);
                    txmvabcsize3.setVisibility(view.GONE);
                    SpinnerMVABCType3.setVisibility(view.GONE);
                    txmvabctype3.setVisibility(view.GONE);

                }else if(selectedItem.equals("ACSR")){

                    SpinnerCopperSize3.setVisibility(view.GONE);
                    txcoppersize3.setVisibility(view.GONE);
                    SpinnerAAACSize3.setVisibility(view.GONE);
                    txaaacsize3.setVisibility(view.GONE);
                    SpinnerACSRSize3.setVisibility(view.VISIBLE);
                    txacsrsize3.setVisibility(view.VISIBLE);
                    SpinnerMVABCSize3.setVisibility(view.GONE);
                    txmvabcsize3.setVisibility(view.GONE);
                    SpinnerMVABCType3.setVisibility(view.GONE);
                    txmvabctype3.setVisibility(view.GONE);

                }else if(selectedItem.equals("MV ABC")){

                    SpinnerCopperSize3.setVisibility(view.GONE);
                    txcoppersize3.setVisibility(view.GONE);
                    SpinnerAAACSize3.setVisibility(view.GONE);
                    txaaacsize3.setVisibility(view.GONE);
                    SpinnerACSRSize3.setVisibility(view.GONE);
                    txacsrsize3.setVisibility(view.GONE);
                    SpinnerMVABCSize3.setVisibility(view.VISIBLE);
                    txmvabcsize3.setVisibility(view.VISIBLE);
                    SpinnerMVABCType3.setVisibility(view.VISIBLE);
                    txmvabctype3.setVisibility(view.VISIBLE);

                }else{

                    SpinnerCopperSize3.setVisibility(view.GONE);
                    txcoppersize3.setVisibility(view.GONE);
                    SpinnerAAACSize3.setVisibility(view.GONE);
                    txaaacsize3.setVisibility(view.GONE);
                    SpinnerACSRSize3.setVisibility(view.GONE);
                    txacsrsize3.setVisibility(view.GONE);
                    SpinnerMVABCSize3.setVisibility(view.GONE);
                    txmvabcsize3.setVisibility(view.GONE);
                    SpinnerMVABCType3.setVisibility(view.GONE);
                    txmvabctype3.setVisibility(view.GONE);

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button button = findViewById(R.id.bLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(EditMVPoles.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(EditMVPoles.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){

                            TextView lat1 = findViewById(R.id.latitude);
                            lat1.setText(String.valueOf((double) location.getLatitude()));

                            TextView long1 = findViewById(R.id.longitude);
                            long1.setText(String.valueOf((double) location.getLongitude()));
                        }
                    }
                });
            }
        });

        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

        final Button addstaybutton = findViewById(R.id.btnAddStay);
        addstaybutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickcount11=clickcount11+1;
                if(clickcount11%2==1) {
                    SpinnerStayVoltage.setVisibility(view.VISIBLE);
                    txstayvoltage.setVisibility(view.VISIBLE);

                }else{
                    SpinnerStayVoltage.setVisibility(view.GONE);
                    txstayvoltage.setVisibility(view.GONE);

                }
            }
        });


        final Button addpolebutton = findViewById(R.id.btnAddPole);
        addpolebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickcount10=clickcount10+1;
                if(clickcount10%2==1) {
                    SpinnerTypeNew.setVisibility(view.VISIBLE);
                    SpinnerTypeNew.setAdapter(adapternew);
                    SpinnerTypeNew.setSelection(poletypeNewposition);
                    // SpinnerPoleheight.setAdapter(adapter30);
                    SpinnerPoleheight.setSelection(1);
                   /* if(poletype.equals("0")){

                    }else if(poletype.equals("RC Pole")) {
                        SpinnerPoleheight.setAdapter(adapter30);
                        SpinnerPoleheight.setSelection(poleheightposition);
                    }else{
                        SpinnerPoleheight.setAdapter(adapter33);
                        SpinnerPoleheight.setSelection(poleheightposition);
                    }*/

                    if(poletypeNew.equals("Wood")){
                        SpinnerPoleheight.setAdapter(adapter30);
                        SpinnerPoleheight.setSelection(1);
                        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK"+poleheightposition);
                    }else if(poletypeNew.equals("Steel")){

                        // SpinnerPoletype.setAdapter(adapter31);
                        //SpinnerPoletype.setSelection(poletypeposition);

                    }else if(poletypeNew.equals("Concrete")){
                        //SpinnerPoletype.setAdapter(adapter32);
                        //SpinnerPoletype.setSelection(poletypeposition);

                    }else{

                    }

                    /*if(poletypeNew.equals("Wood")) {
                        //SpinnerWorkingload.setAdapter(adapter34);
                    }else if(poleheight.equals("10m") && poletype.equals("RC Pole")){
                        SpinnerWorkingload.setAdapter(adapter34);
                        SpinnerWorkingload.setSelection(poleworkloadposition);
                    }else if(poleheight.equals("10m") && poletype.equals("Pre Stressed Pole")){
                        SpinnerWorkingload.setAdapter(adapter36);
                        SpinnerWorkingload.setSelection(poleworkloadposition);
                    }else if(poleheight.equals("11m")){
                        SpinnerWorkingload.setAdapter(adapter35);
                        SpinnerWorkingload.setSelection(poleworkloadposition);
                    }else if(poleheight.equals("13m")){
                        SpinnerWorkingload.setAdapter(adapter35);
                        SpinnerWorkingload.setSelection(poleworkloadposition);
                    }else if(poleheight.equals("9m") && poletype.equals("RC Pole")){
                        SpinnerWorkingload.setVisibility(view.GONE);
                        txworkingload.setVisibility(view.GONE);
                    }else{

                    }*/
                    /* SpinnerPoleheight.setAdapter(adapter1);
                    SpinnerPoleheight.setSelection(poleheightposition);
                    SpinnerWorkingload.setAdapter(adapter3);
                    SpinnerWorkingload.setSelection(poleworkloadposition);
                    SpinnerPoletype.setAdapter(adapterpoletype);
                    SpinnerPoletype.setSelection(poletypeposition);*/
                    txpoletypenew.setVisibility(view.VISIBLE);


                }else{
                    SpinnerTypeNew.setVisibility(view.GONE);
                    txpoletypenew.setVisibility(view.GONE);
                    SpinnerPoleheight.setVisibility(view.GONE);
                    SpinnerWorkingload.setVisibility(view.GONE);
                    SpinnerPoletype.setVisibility(view.GONE);
                    txpoleHeight.setVisibility(view.GONE);
                    txPoleType1.setVisibility(view.GONE);
                    txworkingload.setVisibility(view.GONE);

                    //SpinnerTypeNew.setSelection(poletypeNewposition);
                }
            }
        });


        final Button addstrutbutton = findViewById(R.id.btnAddStrut);
        addstrutbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickcount=clickcount+1;
                if(clickcount%2==1) {
                    txstrutpoletype1.setVisibility(view.VISIBLE);
                    SpinnerStrutTypeNew.setVisibility(view.VISIBLE);
                    SpinnerStrutTypeNew.setAdapter(adapterstruttypenew);
                    SpinnerStrutTypeNew.setSelection(struttypeNewposition);


                }else{
                    txstrutpoletype1.setVisibility(view.GONE);
                    SpinnerStrutTypeNew.setVisibility(view.GONE);

                    SpinnerStrutheight.setVisibility(view.GONE);
                    SpinnerStrutWorkingload.setVisibility(view.GONE);
                    SpinnerStruttype.setVisibility(view.GONE);
                    txstrutHeight.setVisibility(view.GONE);
                    txstrutpoletype.setVisibility(view.GONE);
                    txstrutworkingload.setVisibility(view.GONE);
                }
            }
        });

        final Button addinsulatorbutton = findViewById(R.id.btnAddInsulator);
        addinsulatorbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickcount1=clickcount1+1;
                if(clickcount1%2==1) {
                    if(noofinsulator.getText().toString().trim().equals("1")) {
                        textViewInsulatorTypeType.setVisibility(view.VISIBLE);
                        SpinnerVoltage.setVisibility(view.VISIBLE);
                        txvoltage.setVisibility(view.VISIBLE);
                        SpinnerPhase.setVisibility(view.VISIBLE);
                        txphase.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType.setVisibility(view.VISIBLE);
                        txinsulatortype.setVisibility(view.VISIBLE);
                        SpinnerType.setVisibility(view.VISIBLE);
                        txtype.setVisibility(view.VISIBLE);

                    }else if(noofinsulator.getText().toString().trim().equals("2")){

                        textViewInsulatorTypeType.setVisibility(view.VISIBLE);
                        SpinnerVoltage.setVisibility(view.VISIBLE);
                        txvoltage.setVisibility(view.VISIBLE);
                        SpinnerPhase.setVisibility(view.VISIBLE);
                        txphase.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType.setVisibility(view.VISIBLE);
                        txinsulatortype.setVisibility(view.VISIBLE);
                        SpinnerType.setVisibility(view.VISIBLE);
                        txtype.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType1.setVisibility(view.VISIBLE);
                        SpinnerVoltage4.setVisibility(view.VISIBLE);
                        txvoltage4.setVisibility(view.VISIBLE);
                        SpinnerPhase4.setVisibility(view.VISIBLE);
                        txphase4.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType4.setVisibility(view.VISIBLE);
                        txinsulatortype4.setVisibility(view.VISIBLE);
                        SpinnerType4.setVisibility(view.VISIBLE);
                        txtype4.setVisibility(view.VISIBLE);

                    }else if(noofinsulator.getText().toString().trim().equals("3")){
                        textViewInsulatorTypeType.setVisibility(view.VISIBLE);
                        SpinnerVoltage.setVisibility(view.VISIBLE);
                        txvoltage.setVisibility(view.VISIBLE);
                        SpinnerPhase.setVisibility(view.VISIBLE);
                        txphase.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType.setVisibility(view.VISIBLE);
                        txinsulatortype.setVisibility(view.VISIBLE);
                        SpinnerType.setVisibility(view.VISIBLE);
                        txtype.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType1.setVisibility(view.VISIBLE);
                        SpinnerVoltage4.setVisibility(view.VISIBLE);
                        txvoltage4.setVisibility(view.VISIBLE);
                        SpinnerPhase4.setVisibility(view.VISIBLE);
                        txphase4.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType4.setVisibility(view.VISIBLE);
                        txinsulatortype4.setVisibility(view.VISIBLE);
                        SpinnerType4.setVisibility(view.VISIBLE);
                        txtype4.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType2.setVisibility(view.VISIBLE);
                        SpinnerVoltage5.setVisibility(view.VISIBLE);
                        txvoltage5.setVisibility(view.VISIBLE);
                        SpinnerPhase5.setVisibility(view.VISIBLE);
                        txphase5.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType5.setVisibility(view.VISIBLE);
                        txinsulatortype5.setVisibility(view.VISIBLE);
                        SpinnerType5.setVisibility(view.VISIBLE);
                        txtype5.setVisibility(view.VISIBLE);
                    }else{
                        textViewInsulatorTypeType.setVisibility(view.VISIBLE);
                        SpinnerVoltage.setVisibility(view.VISIBLE);
                        txvoltage.setVisibility(view.VISIBLE);
                        SpinnerPhase.setVisibility(view.VISIBLE);
                        txphase.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType.setVisibility(view.VISIBLE);
                        txinsulatortype.setVisibility(view.VISIBLE);
                        SpinnerType.setVisibility(view.VISIBLE);
                        txtype.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType1.setVisibility(view.VISIBLE);
                        SpinnerVoltage4.setVisibility(view.VISIBLE);
                        txvoltage4.setVisibility(view.VISIBLE);
                        SpinnerPhase4.setVisibility(view.VISIBLE);
                        txphase4.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType4.setVisibility(view.VISIBLE);
                        txinsulatortype4.setVisibility(view.VISIBLE);
                        SpinnerType4.setVisibility(view.VISIBLE);
                        txtype4.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType2.setVisibility(view.VISIBLE);
                        SpinnerVoltage5.setVisibility(view.VISIBLE);
                        txvoltage5.setVisibility(view.VISIBLE);
                        SpinnerPhase5.setVisibility(view.VISIBLE);
                        txphase5.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType5.setVisibility(view.VISIBLE);
                        txinsulatortype5.setVisibility(view.VISIBLE);
                        SpinnerType5.setVisibility(view.VISIBLE);
                        txtype5.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType3.setVisibility(view.VISIBLE);
                        SpinnerVoltage6.setVisibility(view.VISIBLE);
                        txvoltage6.setVisibility(view.VISIBLE);
                        SpinnerPhase6.setVisibility(view.VISIBLE);
                        txphase6.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType6.setVisibility(view.VISIBLE);
                        txinsulatortype6.setVisibility(view.VISIBLE);
                        SpinnerType6.setVisibility(view.VISIBLE);
                        txtype6.setVisibility(view.VISIBLE);
                    }

                }else{
                    textViewInsulatorTypeType.setVisibility(view.GONE);
                    SpinnerVoltage.setVisibility(view.GONE);
                    txvoltage.setVisibility(view.GONE);
                    SpinnerPhase.setVisibility(view.GONE);
                    txphase.setVisibility(view.GONE);
                    SpinnerInsulatorType.setVisibility(view.GONE);
                    txinsulatortype.setVisibility(view.GONE);
                    SpinnerType.setVisibility(view.GONE);
                    txtype.setVisibility(view.GONE);

                    textViewInsulatorTypeType1.setVisibility(view.GONE);
                    SpinnerVoltage4.setVisibility(view.GONE);
                    txvoltage4.setVisibility(view.GONE);
                    SpinnerPhase4.setVisibility(view.GONE);
                    txphase4.setVisibility(view.GONE);
                    SpinnerInsulatorType4.setVisibility(view.GONE);
                    txinsulatortype4.setVisibility(view.GONE);
                    SpinnerType4.setVisibility(view.GONE);
                    txtype4.setVisibility(view.GONE);

                    textViewInsulatorTypeType2.setVisibility(view.GONE);
                    SpinnerVoltage5.setVisibility(view.GONE);
                    txvoltage5.setVisibility(view.GONE);
                    SpinnerPhase5.setVisibility(view.GONE);
                    txphase5.setVisibility(view.GONE);
                    SpinnerInsulatorType5.setVisibility(view.GONE);
                    txinsulatortype5.setVisibility(view.GONE);
                    SpinnerType5.setVisibility(view.GONE);
                    txtype5.setVisibility(view.GONE);

                    textViewInsulatorTypeType3.setVisibility(view.GONE);
                    SpinnerVoltage6.setVisibility(view.GONE);
                    txvoltage6.setVisibility(view.GONE);
                    SpinnerPhase6.setVisibility(view.GONE);
                    txphase6.setVisibility(view.GONE);
                    SpinnerInsulatorType6.setVisibility(view.GONE);
                    txinsulatortype6.setVisibility(view.GONE);
                    SpinnerType6.setVisibility(view.GONE);
                    txtype6.setVisibility(view.GONE);
                }
            }
        });

        final Button addinsulatorbutton1 = findViewById(R.id.btnAddInsulator1);
        addinsulatorbutton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickcount2=clickcount2+1;
                if(clickcount2%2==1) {
                    if(noofinsulator1.getText().toString().trim().equals("1")) {
                        textViewInsulatorTypeType4.setVisibility(view.VISIBLE);
                        SpinnerVoltage1.setVisibility(view.VISIBLE);
                        txvoltage1.setVisibility(view.VISIBLE);
                        SpinnerPhase1.setVisibility(view.VISIBLE);
                        txphase1.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType1.setVisibility(view.VISIBLE);
                        txinsulatortype1.setVisibility(view.VISIBLE);
                        SpinnerType1.setVisibility(view.VISIBLE);
                        txtype1.setVisibility(view.VISIBLE);

                    }else if(noofinsulator1.getText().toString().trim().equals("2")){

                        textViewInsulatorTypeType4.setVisibility(view.VISIBLE);
                        SpinnerVoltage1.setVisibility(view.VISIBLE);
                        txvoltage1.setVisibility(view.VISIBLE);
                        SpinnerPhase1.setVisibility(view.VISIBLE);
                        txphase1.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType1.setVisibility(view.VISIBLE);
                        txinsulatortype1.setVisibility(view.VISIBLE);
                        SpinnerType1.setVisibility(view.VISIBLE);
                        txtype1.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType5.setVisibility(view.VISIBLE);
                        SpinnerVoltage7.setVisibility(view.VISIBLE);
                        txvoltage7.setVisibility(view.VISIBLE);
                        SpinnerPhase7.setVisibility(view.VISIBLE);
                        txphase7.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType7.setVisibility(view.VISIBLE);
                        txinsulatortype7.setVisibility(view.VISIBLE);
                        SpinnerType7.setVisibility(view.VISIBLE);
                        txtype7.setVisibility(view.VISIBLE);

                    }else if(noofinsulator1.getText().toString().trim().equals("3")){
                        textViewInsulatorTypeType4.setVisibility(view.VISIBLE);
                        SpinnerVoltage1.setVisibility(view.VISIBLE);
                        txvoltage1.setVisibility(view.VISIBLE);
                        SpinnerPhase1.setVisibility(view.VISIBLE);
                        txphase1.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType1.setVisibility(view.VISIBLE);
                        txinsulatortype1.setVisibility(view.VISIBLE);
                        SpinnerType1.setVisibility(view.VISIBLE);
                        txtype1.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType5.setVisibility(view.VISIBLE);
                        SpinnerVoltage7.setVisibility(view.VISIBLE);
                        txvoltage7.setVisibility(view.VISIBLE);
                        SpinnerPhase7.setVisibility(view.VISIBLE);
                        txphase7.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType7.setVisibility(view.VISIBLE);
                        txinsulatortype7.setVisibility(view.VISIBLE);
                        SpinnerType7.setVisibility(view.VISIBLE);
                        txtype7.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType6.setVisibility(view.VISIBLE);
                        SpinnerVoltage8.setVisibility(view.VISIBLE);
                        txvoltage8.setVisibility(view.VISIBLE);
                        SpinnerPhase8.setVisibility(view.VISIBLE);
                        txphase8.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType8.setVisibility(view.VISIBLE);
                        txinsulatortype8.setVisibility(view.VISIBLE);
                        SpinnerType8.setVisibility(view.VISIBLE);
                        txtype8.setVisibility(view.VISIBLE);
                    }else{
                        textViewInsulatorTypeType4.setVisibility(view.VISIBLE);
                        SpinnerVoltage1.setVisibility(view.VISIBLE);
                        txvoltage1.setVisibility(view.VISIBLE);
                        SpinnerPhase1.setVisibility(view.VISIBLE);
                        txphase1.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType1.setVisibility(view.VISIBLE);
                        txinsulatortype1.setVisibility(view.VISIBLE);
                        SpinnerType1.setVisibility(view.VISIBLE);
                        txtype1.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType5.setVisibility(view.VISIBLE);
                        SpinnerVoltage7.setVisibility(view.VISIBLE);
                        txvoltage7.setVisibility(view.VISIBLE);
                        SpinnerPhase7.setVisibility(view.VISIBLE);
                        txphase7.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType7.setVisibility(view.VISIBLE);
                        txinsulatortype7.setVisibility(view.VISIBLE);
                        SpinnerType7.setVisibility(view.VISIBLE);
                        txtype7.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType6.setVisibility(view.VISIBLE);
                        SpinnerVoltage8.setVisibility(view.VISIBLE);
                        txvoltage8.setVisibility(view.VISIBLE);
                        SpinnerPhase8.setVisibility(view.VISIBLE);
                        txphase8.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType8.setVisibility(view.VISIBLE);
                        txinsulatortype8.setVisibility(view.VISIBLE);
                        SpinnerType8.setVisibility(view.VISIBLE);
                        txtype8.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType7.setVisibility(view.VISIBLE);
                        SpinnerVoltage9.setVisibility(view.VISIBLE);
                        txvoltage9.setVisibility(view.VISIBLE);
                        SpinnerPhase9.setVisibility(view.VISIBLE);
                        txphase9.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType9.setVisibility(view.VISIBLE);
                        txinsulatortype9.setVisibility(view.VISIBLE);
                        SpinnerType9.setVisibility(view.VISIBLE);
                        txtype9.setVisibility(view.VISIBLE);
                    }

                }else{
                    textViewInsulatorTypeType4.setVisibility(view.GONE);
                    SpinnerVoltage1.setVisibility(view.GONE);
                    txvoltage1.setVisibility(view.GONE);
                    SpinnerPhase1.setVisibility(view.GONE);
                    txphase1.setVisibility(view.GONE);
                    SpinnerInsulatorType1.setVisibility(view.GONE);
                    txinsulatortype1.setVisibility(view.GONE);
                    SpinnerType1.setVisibility(view.GONE);
                    txtype1.setVisibility(view.GONE);

                    textViewInsulatorTypeType5.setVisibility(view.GONE);
                    SpinnerVoltage7.setVisibility(view.GONE);
                    txvoltage7.setVisibility(view.GONE);
                    SpinnerPhase7.setVisibility(view.GONE);
                    txphase7.setVisibility(view.GONE);
                    SpinnerInsulatorType7.setVisibility(view.GONE);
                    txinsulatortype7.setVisibility(view.GONE);
                    SpinnerType7.setVisibility(view.GONE);
                    txtype7.setVisibility(view.GONE);

                    textViewInsulatorTypeType6.setVisibility(view.GONE);
                    SpinnerVoltage8.setVisibility(view.GONE);
                    txvoltage8.setVisibility(view.GONE);
                    SpinnerPhase8.setVisibility(view.GONE);
                    txphase8.setVisibility(view.GONE);
                    SpinnerInsulatorType8.setVisibility(view.GONE);
                    txinsulatortype8.setVisibility(view.GONE);
                    SpinnerType8.setVisibility(view.GONE);
                    txtype8.setVisibility(view.GONE);

                    textViewInsulatorTypeType7.setVisibility(view.GONE);
                    SpinnerVoltage9.setVisibility(view.GONE);
                    txvoltage9.setVisibility(view.GONE);
                    SpinnerPhase9.setVisibility(view.GONE);
                    txphase9.setVisibility(view.GONE);
                    SpinnerInsulatorType9.setVisibility(view.GONE);
                    txinsulatortype9.setVisibility(view.GONE);
                    SpinnerType9.setVisibility(view.GONE);
                    txtype9.setVisibility(view.GONE);
                }
            }
        });

        final Button addinsulatorbutton2 = findViewById(R.id.btnAddInsulator2);
        addinsulatorbutton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickcount3=clickcount3+1;
                if(clickcount3%2==1) {
                    if(noofinsulator2.getText().toString().trim().equals("1")) {
                        textViewInsulatorTypeType8.setVisibility(view.VISIBLE);
                        SpinnerVoltage2.setVisibility(view.VISIBLE);
                        txvoltage2.setVisibility(view.VISIBLE);
                        SpinnerPhase2.setVisibility(view.VISIBLE);
                        txphase2.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType2.setVisibility(view.VISIBLE);
                        txinsulatortype2.setVisibility(view.VISIBLE);
                        SpinnerType2.setVisibility(view.VISIBLE);
                        txtype2.setVisibility(view.VISIBLE);

                    }else if(noofinsulator2.getText().toString().trim().equals("2")){

                        textViewInsulatorTypeType8.setVisibility(view.VISIBLE);
                        SpinnerVoltage2.setVisibility(view.VISIBLE);
                        txvoltage2.setVisibility(view.VISIBLE);
                        SpinnerPhase2.setVisibility(view.VISIBLE);
                        txphase2.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType2.setVisibility(view.VISIBLE);
                        txinsulatortype2.setVisibility(view.VISIBLE);
                        SpinnerType2.setVisibility(view.VISIBLE);
                        txtype2.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType9.setVisibility(view.VISIBLE);
                        SpinnerVoltage10.setVisibility(view.VISIBLE);
                        txvoltage10.setVisibility(view.VISIBLE);
                        SpinnerPhase10.setVisibility(view.VISIBLE);
                        txphase10.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType10.setVisibility(view.VISIBLE);
                        txinsulatortype10.setVisibility(view.VISIBLE);
                        SpinnerType10.setVisibility(view.VISIBLE);
                        txtype10.setVisibility(view.VISIBLE);

                    }else if(noofinsulator2.getText().toString().trim().equals("3")){
                        textViewInsulatorTypeType8.setVisibility(view.VISIBLE);
                        SpinnerVoltage2.setVisibility(view.VISIBLE);
                        txvoltage2.setVisibility(view.VISIBLE);
                        SpinnerPhase2.setVisibility(view.VISIBLE);
                        txphase2.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType2.setVisibility(view.VISIBLE);
                        txinsulatortype2.setVisibility(view.VISIBLE);
                        SpinnerType2.setVisibility(view.VISIBLE);
                        txtype2.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType9.setVisibility(view.VISIBLE);
                        SpinnerVoltage10.setVisibility(view.VISIBLE);
                        txvoltage10.setVisibility(view.VISIBLE);
                        SpinnerPhase10.setVisibility(view.VISIBLE);
                        txphase10.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType10.setVisibility(view.VISIBLE);
                        txinsulatortype10.setVisibility(view.VISIBLE);
                        SpinnerType10.setVisibility(view.VISIBLE);
                        txtype10.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType10.setVisibility(view.VISIBLE);
                        SpinnerVoltage11.setVisibility(view.VISIBLE);
                        txvoltage11.setVisibility(view.VISIBLE);
                        SpinnerPhase11.setVisibility(view.VISIBLE);
                        txphase11.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType11.setVisibility(view.VISIBLE);
                        txinsulatortype11.setVisibility(view.VISIBLE);
                        SpinnerType11.setVisibility(view.VISIBLE);
                        txtype11.setVisibility(view.VISIBLE);
                    }else{
                        textViewInsulatorTypeType8.setVisibility(view.VISIBLE);
                        SpinnerVoltage2.setVisibility(view.VISIBLE);
                        txvoltage2.setVisibility(view.VISIBLE);
                        SpinnerPhase2.setVisibility(view.VISIBLE);
                        txphase2.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType2.setVisibility(view.VISIBLE);
                        txinsulatortype2.setVisibility(view.VISIBLE);
                        SpinnerType2.setVisibility(view.VISIBLE);
                        txtype2.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType9.setVisibility(view.VISIBLE);
                        SpinnerVoltage10.setVisibility(view.VISIBLE);
                        txvoltage10.setVisibility(view.VISIBLE);
                        SpinnerPhase10.setVisibility(view.VISIBLE);
                        txphase10.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType10.setVisibility(view.VISIBLE);
                        txinsulatortype10.setVisibility(view.VISIBLE);
                        SpinnerType10.setVisibility(view.VISIBLE);
                        txtype10.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType10.setVisibility(view.VISIBLE);
                        SpinnerVoltage11.setVisibility(view.VISIBLE);
                        txvoltage11.setVisibility(view.VISIBLE);
                        SpinnerPhase11.setVisibility(view.VISIBLE);
                        txphase11.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType11.setVisibility(view.VISIBLE);
                        txinsulatortype11.setVisibility(view.VISIBLE);
                        SpinnerType11.setVisibility(view.VISIBLE);
                        txtype11.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType11.setVisibility(view.VISIBLE);
                        SpinnerVoltage12.setVisibility(view.VISIBLE);
                        txvoltage12.setVisibility(view.VISIBLE);
                        SpinnerPhase12.setVisibility(view.VISIBLE);
                        txphase12.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType12.setVisibility(view.VISIBLE);
                        txinsulatortype12.setVisibility(view.VISIBLE);
                        SpinnerType12.setVisibility(view.VISIBLE);
                        txtype12.setVisibility(view.VISIBLE);
                    }

                }else{
                    textViewInsulatorTypeType8.setVisibility(view.GONE);
                    SpinnerVoltage2.setVisibility(view.GONE);
                    txvoltage2.setVisibility(view.GONE);
                    SpinnerPhase2.setVisibility(view.GONE);
                    txphase2.setVisibility(view.GONE);
                    SpinnerInsulatorType2.setVisibility(view.GONE);
                    txinsulatortype2.setVisibility(view.GONE);
                    SpinnerType2.setVisibility(view.GONE);
                    txtype2.setVisibility(view.GONE);

                    textViewInsulatorTypeType9.setVisibility(view.GONE);
                    SpinnerVoltage10.setVisibility(view.GONE);
                    txvoltage10.setVisibility(view.GONE);
                    SpinnerPhase10.setVisibility(view.GONE);
                    txphase10.setVisibility(view.GONE);
                    SpinnerInsulatorType10.setVisibility(view.GONE);
                    txinsulatortype10.setVisibility(view.GONE);
                    SpinnerType10.setVisibility(view.GONE);
                    txtype10.setVisibility(view.GONE);

                    textViewInsulatorTypeType10.setVisibility(view.GONE);
                    SpinnerVoltage11.setVisibility(view.GONE);
                    txvoltage11.setVisibility(view.GONE);
                    SpinnerPhase11.setVisibility(view.GONE);
                    txphase11.setVisibility(view.GONE);
                    SpinnerInsulatorType11.setVisibility(view.GONE);
                    txinsulatortype11.setVisibility(view.GONE);
                    SpinnerType11.setVisibility(view.GONE);
                    txtype11.setVisibility(view.GONE);

                    textViewInsulatorTypeType11.setVisibility(view.GONE);
                    SpinnerVoltage12.setVisibility(view.GONE);
                    txvoltage12.setVisibility(view.GONE);
                    SpinnerPhase12.setVisibility(view.GONE);
                    txphase12.setVisibility(view.GONE);
                    SpinnerInsulatorType12.setVisibility(view.GONE);
                    txinsulatortype12.setVisibility(view.GONE);
                    SpinnerType12.setVisibility(view.GONE);
                    txtype12.setVisibility(view.GONE);
                }
            }
        });

        final Button addinsulatorbutton3 = findViewById(R.id.btnAddInsulator3);
        addinsulatorbutton3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickcount4=clickcount4+1;
                if(clickcount4%2==1) {
                    if(noofinsulator3.getText().toString().trim().equals("1")) {
                        textViewInsulatorTypeType12.setVisibility(view.VISIBLE);
                        SpinnerVoltage3.setVisibility(view.VISIBLE);
                        txvoltage3.setVisibility(view.VISIBLE);
                        SpinnerPhase3.setVisibility(view.VISIBLE);
                        txphase3.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType3.setVisibility(view.VISIBLE);
                        txinsulatortype3.setVisibility(view.VISIBLE);
                        SpinnerType3.setVisibility(view.VISIBLE);
                        txtype3.setVisibility(view.VISIBLE);

                    }else if(noofinsulator3.getText().toString().trim().equals("2")){

                        textViewInsulatorTypeType12.setVisibility(view.VISIBLE);
                        SpinnerVoltage3.setVisibility(view.VISIBLE);
                        txvoltage3.setVisibility(view.VISIBLE);
                        SpinnerPhase3.setVisibility(view.VISIBLE);
                        txphase3.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType3.setVisibility(view.VISIBLE);
                        txinsulatortype3.setVisibility(view.VISIBLE);
                        SpinnerType3.setVisibility(view.VISIBLE);
                        txtype3.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType13.setVisibility(view.VISIBLE);
                        SpinnerVoltage13.setVisibility(view.VISIBLE);
                        txvoltage13.setVisibility(view.VISIBLE);
                        SpinnerPhase13.setVisibility(view.VISIBLE);
                        txphase13.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType13.setVisibility(view.VISIBLE);
                        txinsulatortype13.setVisibility(view.VISIBLE);
                        SpinnerType13.setVisibility(view.VISIBLE);
                        txtype13.setVisibility(view.VISIBLE);

                    }else if(noofinsulator3.getText().toString().trim().equals("3")){
                        textViewInsulatorTypeType12.setVisibility(view.VISIBLE);
                        SpinnerVoltage3.setVisibility(view.VISIBLE);
                        txvoltage3.setVisibility(view.VISIBLE);
                        SpinnerPhase3.setVisibility(view.VISIBLE);
                        txphase3.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType3.setVisibility(view.VISIBLE);
                        txinsulatortype3.setVisibility(view.VISIBLE);
                        SpinnerType3.setVisibility(view.VISIBLE);
                        txtype3.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType13.setVisibility(view.VISIBLE);
                        SpinnerVoltage13.setVisibility(view.VISIBLE);
                        txvoltage13.setVisibility(view.VISIBLE);
                        SpinnerPhase13.setVisibility(view.VISIBLE);
                        txphase13.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType13.setVisibility(view.VISIBLE);
                        txinsulatortype13.setVisibility(view.VISIBLE);
                        SpinnerType13.setVisibility(view.VISIBLE);
                        txtype13.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType14.setVisibility(view.VISIBLE);
                        SpinnerVoltage14.setVisibility(view.VISIBLE);
                        txvoltage14.setVisibility(view.VISIBLE);
                        SpinnerPhase14.setVisibility(view.VISIBLE);
                        txphase14.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType14.setVisibility(view.VISIBLE);
                        txinsulatortype14.setVisibility(view.VISIBLE);
                        SpinnerType14.setVisibility(view.VISIBLE);
                        txtype14.setVisibility(view.VISIBLE);
                    }else{
                        textViewInsulatorTypeType12.setVisibility(view.VISIBLE);
                        SpinnerVoltage3.setVisibility(view.VISIBLE);
                        txvoltage3.setVisibility(view.VISIBLE);
                        SpinnerPhase3.setVisibility(view.VISIBLE);
                        txphase3.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType3.setVisibility(view.VISIBLE);
                        txinsulatortype3.setVisibility(view.VISIBLE);
                        SpinnerType3.setVisibility(view.VISIBLE);
                        txtype3.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType13.setVisibility(view.VISIBLE);
                        SpinnerVoltage13.setVisibility(view.VISIBLE);
                        txvoltage13.setVisibility(view.VISIBLE);
                        SpinnerPhase13.setVisibility(view.VISIBLE);
                        txphase13.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType13.setVisibility(view.VISIBLE);
                        txinsulatortype13.setVisibility(view.VISIBLE);
                        SpinnerType13.setVisibility(view.VISIBLE);
                        txtype13.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType14.setVisibility(view.VISIBLE);
                        SpinnerVoltage14.setVisibility(view.VISIBLE);
                        txvoltage14.setVisibility(view.VISIBLE);
                        SpinnerPhase14.setVisibility(view.VISIBLE);
                        txphase14.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType14.setVisibility(view.VISIBLE);
                        txinsulatortype14.setVisibility(view.VISIBLE);
                        SpinnerType14.setVisibility(view.VISIBLE);
                        txtype14.setVisibility(view.VISIBLE);

                        textViewInsulatorTypeType15.setVisibility(view.VISIBLE);
                        SpinnerVoltage15.setVisibility(view.VISIBLE);
                        txvoltage15.setVisibility(view.VISIBLE);
                        SpinnerPhase15.setVisibility(view.VISIBLE);
                        txphase15.setVisibility(view.VISIBLE);
                        SpinnerInsulatorType15.setVisibility(view.VISIBLE);
                        txinsulatortype15.setVisibility(view.VISIBLE);
                        SpinnerType15.setVisibility(view.VISIBLE);
                        txtype15.setVisibility(view.VISIBLE);
                    }

                }else{
                    textViewInsulatorTypeType12.setVisibility(view.GONE);
                    SpinnerVoltage3.setVisibility(view.GONE);
                    txvoltage3.setVisibility(view.GONE);
                    SpinnerPhase3.setVisibility(view.GONE);
                    txphase3.setVisibility(view.GONE);
                    SpinnerInsulatorType3.setVisibility(view.GONE);
                    txinsulatortype3.setVisibility(view.GONE);
                    SpinnerType3.setVisibility(view.GONE);
                    txtype3.setVisibility(view.GONE);

                    textViewInsulatorTypeType13.setVisibility(view.GONE);
                    SpinnerVoltage13.setVisibility(view.GONE);
                    txvoltage13.setVisibility(view.GONE);
                    SpinnerPhase13.setVisibility(view.GONE);
                    txphase13.setVisibility(view.GONE);
                    SpinnerInsulatorType13.setVisibility(view.GONE);
                    txinsulatortype13.setVisibility(view.GONE);
                    SpinnerType13.setVisibility(view.GONE);
                    txtype13.setVisibility(view.GONE);

                    textViewInsulatorTypeType14.setVisibility(view.GONE);
                    SpinnerVoltage14.setVisibility(view.GONE);
                    txvoltage14.setVisibility(view.GONE);
                    SpinnerPhase14.setVisibility(view.GONE);
                    txphase14.setVisibility(view.GONE);
                    SpinnerInsulatorType14.setVisibility(view.GONE);
                    txinsulatortype14.setVisibility(view.GONE);
                    SpinnerType14.setVisibility(view.GONE);
                    txtype14.setVisibility(view.GONE);

                    textViewInsulatorTypeType15.setVisibility(view.GONE);
                    SpinnerVoltage15.setVisibility(view.GONE);
                    txvoltage15.setVisibility(view.GONE);
                    SpinnerPhase15.setVisibility(view.GONE);
                    txphase15.setVisibility(view.GONE);
                    SpinnerInsulatorType15.setVisibility(view.GONE);
                    txinsulatortype15.setVisibility(view.GONE);
                    SpinnerType15.setVisibility(view.GONE);
                    txtype15.setVisibility(view.GONE);
                }
            }
        });

        final Button addcrossarmbutton = findViewById(R.id.btnAddCrossArm);
        addcrossarmbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickcount5=clickcount5+1;
                if(clickcount5%2==1) {
                    if(noofcrossarm.getText().toString().trim().equals("1")){
                        TextCrossArmType.setVisibility(view.VISIBLE);
                        SpinnerCrossArmType.setVisibility(view.VISIBLE);
                        txcrossarm.setVisibility(view.VISIBLE);
                        SpinnerCAType.setVisibility(view.VISIBLE);
                        txcrossarmtype.setVisibility(view.VISIBLE);

                        TextCrossArmType1.setVisibility(view.GONE);
                        Spinner1CrossArmType.setVisibility(view.GONE);
                        tx1crossarm.setVisibility(view.GONE);
                        Spinner1CAType.setVisibility(view.GONE);
                        tx1crossarmtype.setVisibility(view.GONE);

                    }else if(noofcrossarm.getText().toString().trim().equals("2")){
                        TextCrossArmType.setVisibility(view.VISIBLE);
                        SpinnerCrossArmType.setVisibility(view.VISIBLE);
                        txcrossarm.setVisibility(view.VISIBLE);
                        SpinnerCAType.setVisibility(view.VISIBLE);
                        txcrossarmtype.setVisibility(view.VISIBLE);

                        TextCrossArmType1.setVisibility(view.VISIBLE);
                        Spinner1CrossArmType.setVisibility(view.VISIBLE);
                        tx1crossarm.setVisibility(view.VISIBLE);
                        Spinner1CAType.setVisibility(view.VISIBLE);
                        tx1crossarmtype.setVisibility(view.VISIBLE);
                    }else{
                        TextCrossArmType.setVisibility(view.GONE);
                        SpinnerCrossArmType.setVisibility(view.GONE);
                        txcrossarm.setVisibility(view.GONE);
                        SpinnerCAType.setVisibility(view.GONE);
                        txcrossarmtype.setVisibility(view.GONE);

                        TextCrossArmType1.setVisibility(view.GONE);
                        Spinner1CrossArmType.setVisibility(view.GONE);
                        tx1crossarm.setVisibility(view.GONE);
                        Spinner1CAType.setVisibility(view.GONE);
                        tx1crossarmtype.setVisibility(view.GONE);
                    }

                }else{
                    TextCrossArmType.setVisibility(view.GONE);
                    SpinnerCrossArmType.setVisibility(view.GONE);
                    txcrossarm.setVisibility(view.GONE);
                    SpinnerCAType.setVisibility(view.GONE);
                    txcrossarmtype.setVisibility(view.GONE);

                    TextCrossArmType1.setVisibility(view.GONE);
                    Spinner1CrossArmType.setVisibility(view.GONE);
                    tx1crossarm.setVisibility(view.GONE);
                    Spinner1CAType.setVisibility(view.GONE);
                    tx1crossarmtype.setVisibility(view.GONE);
                }
            }
        });

        final Button addcrossarmbutton1 = findViewById(R.id.btnAddCrossArm1);
        addcrossarmbutton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickcount6=clickcount6+1;
                if(clickcount6%2==1) {
                    if(noofcrossarm1.getText().toString().trim().equals("1")){
                        TextCrossArmType2.setVisibility(view.VISIBLE);
                        SpinnerCrossArmType1.setVisibility(view.VISIBLE);
                        txcrossarm1.setVisibility(view.VISIBLE);
                        SpinnerCAType1.setVisibility(view.VISIBLE);
                        txcrossarmtype1.setVisibility(view.VISIBLE);

                        TextCrossArmType3.setVisibility(view.GONE);
                        Spinner1CrossArmType1.setVisibility(view.GONE);
                        tx1crossarm1.setVisibility(view.GONE);
                        Spinner1CAType1.setVisibility(view.GONE);
                        tx1crossarmtype1.setVisibility(view.GONE);

                    }else if(noofcrossarm1.getText().toString().trim().equals("2")){
                        TextCrossArmType2.setVisibility(view.VISIBLE);
                        SpinnerCrossArmType1.setVisibility(view.VISIBLE);
                        txcrossarm1.setVisibility(view.VISIBLE);
                        SpinnerCAType1.setVisibility(view.VISIBLE);
                        txcrossarmtype1.setVisibility(view.VISIBLE);

                        TextCrossArmType3.setVisibility(view.VISIBLE);
                        Spinner1CrossArmType1.setVisibility(view.VISIBLE);
                        tx1crossarm1.setVisibility(view.VISIBLE);
                        Spinner1CAType1.setVisibility(view.VISIBLE);
                        tx1crossarmtype1.setVisibility(view.VISIBLE);
                    }else{
                        TextCrossArmType2.setVisibility(view.GONE);
                        SpinnerCrossArmType1.setVisibility(view.GONE);
                        txcrossarm1.setVisibility(view.GONE);
                        SpinnerCAType1.setVisibility(view.GONE);
                        txcrossarmtype1.setVisibility(view.GONE);

                        TextCrossArmType3.setVisibility(view.GONE);
                        Spinner1CrossArmType1.setVisibility(view.GONE);
                        tx1crossarm1.setVisibility(view.GONE);
                        Spinner1CAType1.setVisibility(view.GONE);
                        tx1crossarmtype1.setVisibility(view.GONE);
                    }

                }else{
                    TextCrossArmType2.setVisibility(view.GONE);
                    SpinnerCrossArmType1.setVisibility(view.GONE);
                    txcrossarm1.setVisibility(view.GONE);
                    SpinnerCAType1.setVisibility(view.GONE);
                    txcrossarmtype1.setVisibility(view.GONE);

                    TextCrossArmType3.setVisibility(view.GONE);
                    Spinner1CrossArmType1.setVisibility(view.GONE);
                    tx1crossarm1.setVisibility(view.GONE);
                    Spinner1CAType1.setVisibility(view.GONE);
                    tx1crossarmtype1.setVisibility(view.GONE);
                }
            }
        });

        final Button addcrossarmbutton2 = findViewById(R.id.btnAddCrossArm2);
        addcrossarmbutton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickcount7 = clickcount7 + 1;
                if (clickcount7 % 2 == 1) {
                    if (noofcrossarm2.getText().toString().trim().equals("1")) {
                        TextCrossArmType4.setVisibility(view.VISIBLE);
                        SpinnerCrossArmType2.setVisibility(view.VISIBLE);
                        txcrossarm2.setVisibility(view.VISIBLE);
                        SpinnerCAType2.setVisibility(view.VISIBLE);
                        txcrossarmtype2.setVisibility(view.VISIBLE);

                        TextCrossArmType6.setVisibility(view.GONE);
                        Spinner2CrossArmType1.setVisibility(view.GONE);
                        tx2crossarm1.setVisibility(view.GONE);
                        Spinner2CAType1.setVisibility(view.GONE);
                        tx2crossarmtype1.setVisibility(view.GONE);

                    } else if (noofcrossarm2.getText().toString().trim().equals("2")) {
                        TextCrossArmType4.setVisibility(view.VISIBLE);
                        SpinnerCrossArmType2.setVisibility(view.VISIBLE);
                        txcrossarm2.setVisibility(view.VISIBLE);
                        SpinnerCAType2.setVisibility(view.VISIBLE);
                        txcrossarmtype2.setVisibility(view.VISIBLE);

                        TextCrossArmType6.setVisibility(view.VISIBLE);
                        Spinner2CrossArmType1.setVisibility(view.VISIBLE);
                        tx2crossarm1.setVisibility(view.VISIBLE);
                        Spinner2CAType1.setVisibility(view.VISIBLE);
                        tx2crossarmtype1.setVisibility(view.VISIBLE);
                    } else {
                        TextCrossArmType4.setVisibility(view.GONE);
                        SpinnerCrossArmType2.setVisibility(view.GONE);
                        txcrossarm2.setVisibility(view.GONE);
                        SpinnerCAType2.setVisibility(view.GONE);
                        txcrossarmtype2.setVisibility(view.GONE);

                        TextCrossArmType6.setVisibility(view.GONE);
                        Spinner2CrossArmType1.setVisibility(view.GONE);
                        tx2crossarm1.setVisibility(view.GONE);
                        Spinner2CAType1.setVisibility(view.GONE);
                        tx2crossarmtype1.setVisibility(view.GONE);
                    }

                } else {
                    TextCrossArmType4.setVisibility(view.GONE);
                    SpinnerCrossArmType2.setVisibility(view.GONE);
                    txcrossarm2.setVisibility(view.GONE);
                    SpinnerCAType2.setVisibility(view.GONE);
                    txcrossarmtype2.setVisibility(view.GONE);

                    TextCrossArmType6.setVisibility(view.GONE);
                    Spinner2CrossArmType1.setVisibility(view.GONE);
                    tx2crossarm1.setVisibility(view.GONE);
                    Spinner2CAType1.setVisibility(view.GONE);
                    tx2crossarmtype1.setVisibility(view.GONE);
                }
            }

        });

        final Button addcrossarmbutton3 = findViewById(R.id.btnAddCrossArm3);
        addcrossarmbutton3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickcount8=clickcount8+1;
                if (clickcount8 % 2 == 1) {
                    if (noofcrossarm3.getText().toString().trim().equals("1")) {
                        TextCrossArmType5.setVisibility(view.VISIBLE);
                        SpinnerCrossArmType3.setVisibility(view.VISIBLE);
                        txcrossarm3.setVisibility(view.VISIBLE);
                        SpinnerCAType3.setVisibility(view.VISIBLE);
                        txcrossarmtype3.setVisibility(view.VISIBLE);

                        TextCrossArmType7.setVisibility(view.GONE);
                        Spinner3CrossArmType1.setVisibility(view.GONE);
                        tx3crossarm1.setVisibility(view.GONE);
                        Spinner3CAType1.setVisibility(view.GONE);
                        tx3crossarmtype1.setVisibility(view.GONE);

                    } else if (noofcrossarm3.getText().toString().trim().equals("2")) {
                        TextCrossArmType5.setVisibility(view.VISIBLE);
                        SpinnerCrossArmType3.setVisibility(view.VISIBLE);
                        txcrossarm3.setVisibility(view.VISIBLE);
                        SpinnerCAType3.setVisibility(view.VISIBLE);
                        txcrossarmtype3.setVisibility(view.VISIBLE);

                        TextCrossArmType7.setVisibility(view.VISIBLE);
                        Spinner3CrossArmType1.setVisibility(view.VISIBLE);
                        tx3crossarm1.setVisibility(view.VISIBLE);
                        Spinner3CAType1.setVisibility(view.VISIBLE);
                        tx3crossarmtype1.setVisibility(view.VISIBLE);
                    } else {
                        TextCrossArmType5.setVisibility(view.GONE);
                        SpinnerCrossArmType3.setVisibility(view.GONE);
                        txcrossarm3.setVisibility(view.GONE);
                        SpinnerCAType3.setVisibility(view.GONE);
                        txcrossarmtype3.setVisibility(view.GONE);

                        TextCrossArmType7.setVisibility(view.GONE);
                        Spinner3CrossArmType1.setVisibility(view.GONE);
                        tx3crossarm1.setVisibility(view.GONE);
                        Spinner3CAType1.setVisibility(view.GONE);
                        tx3crossarmtype1.setVisibility(view.GONE);
                    }

                } else {
                    TextCrossArmType5.setVisibility(view.GONE);
                    SpinnerCrossArmType3.setVisibility(view.GONE);
                    txcrossarm3.setVisibility(view.GONE);
                    SpinnerCAType3.setVisibility(view.GONE);
                    txcrossarmtype3.setVisibility(view.GONE);

                    TextCrossArmType7.setVisibility(view.GONE);
                    Spinner3CrossArmType1.setVisibility(view.GONE);
                    tx3crossarm1.setVisibility(view.GONE);
                    Spinner3CAType1.setVisibility(view.GONE);
                    tx3crossarmtype1.setVisibility(view.GONE);
                }
            }
        });

        final Button addcircuitsbutton = findViewById(R.id.btnAddCircuits);
        addcircuitsbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                clickcount9=clickcount9+1;
                if(clickcount9%2==1) {
                    if (noofcircuits.getText().toString().trim().equals("1")) {
                        txCircuit1.setVisibility(view.VISIBLE);
                        SpinnerSource.setVisibility(view.VISIBLE);
                        txsource.setVisibility(view.VISIBLE);
                        SpinnerVerticlePosition.setVisibility(view.VISIBLE);
                        txverticleposition.setVisibility(view.VISIBLE);
                        SpinnerCircuitFormation.setVisibility(view.VISIBLE);
                        txcircuitformation.setVisibility(view.VISIBLE);
                        SpinnerConductorType.setVisibility(view.VISIBLE);
                        txconductortype.setVisibility(view.VISIBLE);
                        TextCrossArm.setVisibility(view.VISIBLE);
                        noofcrossarm.setVisibility(view.VISIBLE);
                        addcrossarmbutton.setVisibility(view.VISIBLE);
                        addinsulatorbutton.setVisibility(view.VISIBLE);
                        TextNoofInsulator.setVisibility(view.VISIBLE);
                        noofinsulator.setVisibility(view.VISIBLE);


                        txCircuit2.setVisibility(view.GONE);
                        SpinnerSource1.setVisibility(view.GONE);
                        txsource1.setVisibility(view.GONE);
                        SpinnerVerticlePosition1.setVisibility(view.GONE);
                        txverticleposition1.setVisibility(view.GONE);
                        SpinnerCircuitFormation1.setVisibility(view.GONE);
                        txcircuitformation1.setVisibility(view.GONE);
                        SpinnerConductorType1.setVisibility(view.GONE);
                        txconductortype1.setVisibility(view.GONE);
                        TextCrossArm1.setVisibility(view.GONE);
                        noofcrossarm1.setVisibility(view.GONE);
                        addcrossarmbutton1.setVisibility(view.GONE);
                        addinsulatorbutton1.setVisibility(view.GONE);
                        TextNoofInsulator1.setVisibility(view.GONE);
                        noofinsulator1.setVisibility(view.GONE);

                        txCircuit3.setVisibility(view.GONE);
                        SpinnerSource2.setVisibility(view.GONE);
                        txsource2.setVisibility(view.GONE);
                        SpinnerVerticlePosition2.setVisibility(view.GONE);
                        txverticleposition2.setVisibility(view.GONE);
                        SpinnerCircuitFormation2.setVisibility(view.GONE);
                        txcircuitformation2.setVisibility(view.GONE);
                        SpinnerConductorType2.setVisibility(view.GONE);
                        txconductortype2.setVisibility(view.GONE);
                        TextCrossArm2.setVisibility(view.GONE);
                        noofcrossarm2.setVisibility(view.GONE);
                        addcrossarmbutton2.setVisibility(view.GONE);
                        addinsulatorbutton2.setVisibility(view.GONE);
                        TextNoofInsulator2.setVisibility(view.GONE);
                        noofinsulator2.setVisibility(view.GONE);

                        txCircuit4.setVisibility(view.GONE);
                        SpinnerSource3.setVisibility(view.GONE);
                        txsource3.setVisibility(view.GONE);
                        SpinnerVerticlePosition3.setVisibility(view.GONE);
                        txverticleposition3.setVisibility(view.GONE);
                        SpinnerCircuitFormation3.setVisibility(view.GONE);
                        txcircuitformation3.setVisibility(view.GONE);
                        SpinnerConductorType3.setVisibility(view.GONE);
                        txconductortype3.setVisibility(view.GONE);
                        TextCrossArm3.setVisibility(view.GONE);
                        noofcrossarm3.setVisibility(view.GONE);
                        addcrossarmbutton3.setVisibility(view.GONE);
                        addinsulatorbutton3.setVisibility(view.GONE);
                        TextNoofInsulator3.setVisibility(view.GONE);
                        noofinsulator3.setVisibility(view.GONE);

                   /* savebutton1.setVisibility(view.GONE);
                    savebutton2.setVisibility(view.VISIBLE);
                    savebutton3.setVisibility(view.GONE);
                    savebutton4.setVisibility(view.GONE);
                    savebutton5.setVisibility(view.GONE);*/


                    } else if (noofcircuits.getText().toString().trim().equals("0")) {
                        SpinnerVerticlePosition.setVisibility(view.GONE);
                        txverticleposition.setVisibility(view.GONE);
                        SpinnerCircuitFormation.setVisibility(view.GONE);
                        txcircuitformation.setVisibility(view.GONE);
                        SpinnerConductorType.setVisibility(view.GONE);
                        txconductortype.setVisibility(view.GONE);
                        addcrossarmbutton.setVisibility(view.GONE);
                        addinsulatorbutton.setVisibility(view.GONE);
                        SpinnerCrossArmType.setVisibility(view.GONE);
                        txcrossarm.setVisibility(view.GONE);
                        SpinnerCAType.setVisibility(view.GONE);
                        SpinnerCrossArmType.setVisibility(view.GONE);
                        txcrossarm.setVisibility(view.GONE);
                        SpinnerCAType.setVisibility(view.GONE);
                        txcrossarmtype.setVisibility(view.GONE);
                        SpinnerVoltage.setVisibility(view.GONE);
                        txvoltage.setVisibility(view.GONE);
                        SpinnerPhase.setVisibility(view.GONE);
                        txphase.setVisibility(view.GONE);
                        SpinnerInsulatorType.setVisibility(view.GONE);
                        txinsulatortype.setVisibility(view.GONE);
                        SpinnerType.setVisibility(view.GONE);
                        txtype.setVisibility(view.GONE);
                        SpinnerSource.setVisibility(view.GONE);
                        txsource.setVisibility(view.GONE);
                        txCircuit1.setVisibility(view.GONE);
                        SpinnerCopperSize.setVisibility(view.GONE);
                        txcoppersize.setVisibility(view.GONE);
                        SpinnerAAACSize.setVisibility(view.GONE);
                        txaaacsize.setVisibility(view.GONE);
                        SpinnerACSRSize.setVisibility(view.GONE);
                        txacsrsize.setVisibility(view.GONE);
                        SpinnerMVABCType.setVisibility(view.GONE);
                        txmvabctype.setVisibility(view.GONE);
                        SpinnerMVABCSize.setVisibility(view.GONE);
                        txmvabcsize.setVisibility(view.GONE);
                        SpinnerGantry.setVisibility(view.GONE);
                        txgantry.setVisibility(view.GONE);
                        SpinnerFeeder.setVisibility(view.GONE);
                        txfeeder.setVisibility(view.GONE);
                        TextCrossArm.setVisibility(view.GONE);
                        noofcrossarm.setVisibility(view.GONE);
                        TextNoofInsulator.setVisibility(view.GONE);
                        noofinsulator.setVisibility(view.GONE);


                        SpinnerVerticlePosition1.setVisibility(view.GONE);
                        txverticleposition1.setVisibility(view.GONE);
                        SpinnerCircuitFormation1.setVisibility(view.GONE);
                        txcircuitformation1.setVisibility(view.GONE);
                        SpinnerConductorType1.setVisibility(view.GONE);
                        txconductortype1.setVisibility(view.GONE);
                        addcrossarmbutton1.setVisibility(view.GONE);
                        addinsulatorbutton1.setVisibility(view.GONE);
                        SpinnerCrossArmType1.setVisibility(view.GONE);
                        txcrossarm1.setVisibility(view.GONE);
                        SpinnerCAType1.setVisibility(view.GONE);
                        SpinnerCrossArmType1.setVisibility(view.GONE);
                        txcrossarm1.setVisibility(view.GONE);
                        SpinnerCAType1.setVisibility(view.GONE);
                        txcrossarmtype1.setVisibility(view.GONE);
                        SpinnerVoltage1.setVisibility(view.GONE);
                        txvoltage1.setVisibility(view.GONE);
                        SpinnerPhase1.setVisibility(view.GONE);
                        txphase1.setVisibility(view.GONE);
                        SpinnerInsulatorType1.setVisibility(view.GONE);
                        txinsulatortype1.setVisibility(view.GONE);
                        SpinnerType1.setVisibility(view.GONE);
                        txtype1.setVisibility(view.GONE);
                        SpinnerSource1.setVisibility(view.GONE);
                        txsource1.setVisibility(view.GONE);
                        txCircuit2.setVisibility(view.GONE);
                        SpinnerCopperSize1.setVisibility(view.GONE);
                        txcoppersize1.setVisibility(view.GONE);
                        SpinnerAAACSize1.setVisibility(view.GONE);
                        txaaacsize1.setVisibility(view.GONE);
                        SpinnerACSRSize1.setVisibility(view.GONE);
                        txacsrsize1.setVisibility(view.GONE);
                        SpinnerMVABCType1.setVisibility(view.GONE);
                        txmvabctype1.setVisibility(view.GONE);
                        SpinnerMVABCSize1.setVisibility(view.GONE);
                        txmvabcsize1.setVisibility(view.GONE);
                        SpinnerGantry1.setVisibility(view.GONE);
                        txgantry1.setVisibility(view.GONE);
                        SpinnerFeeder1.setVisibility(view.GONE);
                        txfeeder1.setVisibility(view.GONE);
                        TextCrossArm1.setVisibility(view.GONE);
                        noofcrossarm1.setVisibility(view.GONE);
                        TextNoofInsulator1.setVisibility(view.GONE);
                        noofinsulator1.setVisibility(view.GONE);

                        SpinnerVerticlePosition2.setVisibility(view.GONE);
                        txverticleposition2.setVisibility(view.GONE);
                        SpinnerCircuitFormation2.setVisibility(view.GONE);
                        txcircuitformation2.setVisibility(view.GONE);
                        SpinnerConductorType2.setVisibility(view.GONE);
                        txconductortype2.setVisibility(view.GONE);
                        addcrossarmbutton2.setVisibility(view.GONE);
                        addinsulatorbutton2.setVisibility(view.GONE);
                        SpinnerCrossArmType2.setVisibility(view.GONE);
                        txcrossarm2.setVisibility(view.GONE);
                        SpinnerCAType2.setVisibility(view.GONE);
                        SpinnerCrossArmType2.setVisibility(view.GONE);
                        txcrossarm2.setVisibility(view.GONE);
                        SpinnerCAType2.setVisibility(view.GONE);
                        txcrossarmtype2.setVisibility(view.GONE);
                        SpinnerVoltage2.setVisibility(view.GONE);
                        txvoltage2.setVisibility(view.GONE);
                        SpinnerPhase2.setVisibility(view.GONE);
                        txphase2.setVisibility(view.GONE);
                        SpinnerInsulatorType2.setVisibility(view.GONE);
                        txinsulatortype2.setVisibility(view.GONE);
                        SpinnerType2.setVisibility(view.GONE);
                        txtype2.setVisibility(view.GONE);
                        SpinnerSource2.setVisibility(view.GONE);
                        txsource2.setVisibility(view.GONE);
                        txCircuit3.setVisibility(view.GONE);
                        SpinnerCopperSize2.setVisibility(view.GONE);
                        txcoppersize2.setVisibility(view.GONE);
                        SpinnerAAACSize2.setVisibility(view.GONE);
                        txaaacsize2.setVisibility(view.GONE);
                        SpinnerACSRSize2.setVisibility(view.GONE);
                        txacsrsize2.setVisibility(view.GONE);
                        SpinnerMVABCType2.setVisibility(view.GONE);
                        txmvabctype2.setVisibility(view.GONE);
                        SpinnerMVABCSize2.setVisibility(view.GONE);
                        txmvabcsize2.setVisibility(view.GONE);
                        SpinnerGantry2.setVisibility(view.GONE);
                        txgantry2.setVisibility(view.GONE);
                        SpinnerFeeder2.setVisibility(view.GONE);
                        txfeeder2.setVisibility(view.GONE);
                        TextCrossArm2.setVisibility(view.GONE);
                        noofcrossarm2.setVisibility(view.GONE);
                        TextNoofInsulator2.setVisibility(view.GONE);
                        noofinsulator2.setVisibility(view.GONE);

                        SpinnerVerticlePosition3.setVisibility(view.GONE);
                        txverticleposition3.setVisibility(view.GONE);
                        SpinnerCircuitFormation3.setVisibility(view.GONE);
                        txcircuitformation3.setVisibility(view.GONE);
                        SpinnerConductorType3.setVisibility(view.GONE);
                        txconductortype3.setVisibility(view.GONE);
                        addcrossarmbutton3.setVisibility(view.GONE);
                        addinsulatorbutton3.setVisibility(view.GONE);
                        SpinnerCrossArmType3.setVisibility(view.GONE);
                        txcrossarm3.setVisibility(view.GONE);
                        SpinnerCAType3.setVisibility(view.GONE);
                        SpinnerCrossArmType3.setVisibility(view.GONE);
                        txcrossarm3.setVisibility(view.GONE);
                        SpinnerCAType3.setVisibility(view.GONE);
                        txcrossarmtype3.setVisibility(view.GONE);
                        SpinnerVoltage3.setVisibility(view.GONE);
                        txvoltage3.setVisibility(view.GONE);
                        SpinnerPhase3.setVisibility(view.GONE);
                        txphase3.setVisibility(view.GONE);
                        SpinnerInsulatorType3.setVisibility(view.GONE);
                        txinsulatortype3.setVisibility(view.GONE);
                        SpinnerType3.setVisibility(view.GONE);
                        txtype3.setVisibility(view.GONE);
                        SpinnerSource3.setVisibility(view.GONE);
                        txsource3.setVisibility(view.GONE);
                        txCircuit4.setVisibility(view.GONE);
                        SpinnerCopperSize3.setVisibility(view.GONE);
                        txcoppersize3.setVisibility(view.GONE);
                        SpinnerAAACSize3.setVisibility(view.GONE);
                        txaaacsize3.setVisibility(view.GONE);
                        SpinnerACSRSize3.setVisibility(view.GONE);
                        txacsrsize3.setVisibility(view.GONE);
                        SpinnerMVABCType3.setVisibility(view.GONE);
                        txmvabctype3.setVisibility(view.GONE);
                        SpinnerMVABCSize3.setVisibility(view.GONE);
                        txmvabcsize3.setVisibility(view.GONE);
                        SpinnerGantry3.setVisibility(view.GONE);
                        txgantry3.setVisibility(view.GONE);
                        SpinnerFeeder3.setVisibility(view.GONE);
                        txfeeder3.setVisibility(view.GONE);
                        TextCrossArm3.setVisibility(view.GONE);
                        noofcrossarm3.setVisibility(view.GONE);
                        TextNoofInsulator3.setVisibility(view.GONE);
                        noofinsulator3.setVisibility(view.GONE);

                   /* savebutton2.setVisibility(view.GONE);
                    savebutton1.setVisibility(view.VISIBLE);
                    savebutton3.setVisibility(view.GONE);
                    savebutton4.setVisibility(view.GONE);
                    savebutton5.setVisibility(view.GONE);*/

                    } else if (noofcircuits.getText().toString().trim().equals("2")) {
                        SpinnerVerticlePosition.setVisibility(view.VISIBLE);
                        txverticleposition.setVisibility(view.VISIBLE);
                        SpinnerCircuitFormation.setVisibility(view.VISIBLE);
                        txcircuitformation.setVisibility(view.VISIBLE);
                        SpinnerConductorType.setVisibility(view.VISIBLE);
                        txconductortype.setVisibility(view.VISIBLE);
                        addcrossarmbutton.setVisibility(view.VISIBLE);
                        addinsulatorbutton.setVisibility(view.VISIBLE);
                        SpinnerSource.setVisibility(view.VISIBLE);
                        txsource.setVisibility(view.VISIBLE);
                        txCircuit1.setVisibility(view.VISIBLE);
                        TextCrossArm.setVisibility(view.VISIBLE);
                        noofcrossarm.setVisibility(view.VISIBLE);
                        TextNoofInsulator.setVisibility(view.VISIBLE);
                        noofinsulator.setVisibility(view.VISIBLE);

                        SpinnerVerticlePosition1.setVisibility(view.VISIBLE);
                        txverticleposition1.setVisibility(view.VISIBLE);
                        SpinnerCircuitFormation1.setVisibility(view.VISIBLE);
                        txcircuitformation1.setVisibility(view.VISIBLE);
                        SpinnerConductorType1.setVisibility(view.VISIBLE);
                        txconductortype1.setVisibility(view.VISIBLE);
                        addcrossarmbutton1.setVisibility(view.VISIBLE);
                        addinsulatorbutton1.setVisibility(view.VISIBLE);
                        SpinnerSource1.setVisibility(view.VISIBLE);
                        txsource1.setVisibility(view.VISIBLE);
                        txCircuit2.setVisibility(view.VISIBLE);
                        TextCrossArm1.setVisibility(view.VISIBLE);
                        noofcrossarm1.setVisibility(view.VISIBLE);
                        TextNoofInsulator1.setVisibility(view.VISIBLE);
                        noofinsulator1.setVisibility(view.VISIBLE);

                        SpinnerVerticlePosition2.setVisibility(view.GONE);
                        txverticleposition2.setVisibility(view.GONE);
                        SpinnerCircuitFormation2.setVisibility(view.GONE);
                        txcircuitformation2.setVisibility(view.GONE);
                        SpinnerConductorType2.setVisibility(view.GONE);
                        txconductortype2.setVisibility(view.GONE);
                        addcrossarmbutton2.setVisibility(view.GONE);
                        addinsulatorbutton2.setVisibility(view.GONE);
                        SpinnerSource2.setVisibility(view.GONE);
                        txsource2.setVisibility(view.GONE);
                        txCircuit3.setVisibility(view.GONE);
                        TextCrossArm2.setVisibility(view.GONE);
                        noofcrossarm2.setVisibility(view.GONE);
                        TextNoofInsulator2.setVisibility(view.GONE);
                        noofinsulator2.setVisibility(view.GONE);

                        SpinnerVerticlePosition3.setVisibility(view.GONE);
                        txverticleposition3.setVisibility(view.GONE);
                        SpinnerCircuitFormation3.setVisibility(view.GONE);
                        txcircuitformation3.setVisibility(view.GONE);
                        SpinnerConductorType3.setVisibility(view.GONE);
                        txconductortype3.setVisibility(view.GONE);
                        addcrossarmbutton3.setVisibility(view.GONE);
                        addinsulatorbutton3.setVisibility(view.GONE);
                        SpinnerSource3.setVisibility(view.GONE);
                        txsource3.setVisibility(view.GONE);
                        txCircuit4.setVisibility(view.GONE);
                        TextCrossArm3.setVisibility(view.GONE);
                        noofcrossarm3.setVisibility(view.GONE);
                        TextNoofInsulator3.setVisibility(view.GONE);
                        noofinsulator3.setVisibility(view.GONE);

                   /* savebutton1.setVisibility(view.GONE);
                    savebutton2.setVisibility(view.GONE);
                    savebutton3.setVisibility(view.VISIBLE);
                    savebutton4.setVisibility(view.GONE);
                    savebutton5.setVisibility(view.GONE);*/


                    } else if (noofcircuits.getText().toString().trim().equals("3")) {
                        SpinnerVerticlePosition.setVisibility(view.VISIBLE);
                        txverticleposition.setVisibility(view.VISIBLE);
                        SpinnerCircuitFormation.setVisibility(view.VISIBLE);
                        txcircuitformation.setVisibility(view.VISIBLE);
                        SpinnerConductorType.setVisibility(view.VISIBLE);
                        txconductortype.setVisibility(view.VISIBLE);
                        addcrossarmbutton.setVisibility(view.VISIBLE);
                        addinsulatorbutton.setVisibility(view.VISIBLE);
                        SpinnerSource.setVisibility(view.VISIBLE);
                        txsource.setVisibility(view.VISIBLE);
                        txCircuit1.setVisibility(view.VISIBLE);
                        TextCrossArm.setVisibility(view.VISIBLE);
                        noofcrossarm.setVisibility(view.VISIBLE);
                        TextNoofInsulator.setVisibility(view.VISIBLE);
                        noofinsulator.setVisibility(view.VISIBLE);

                        SpinnerVerticlePosition1.setVisibility(view.VISIBLE);
                        txverticleposition1.setVisibility(view.VISIBLE);
                        SpinnerCircuitFormation1.setVisibility(view.VISIBLE);
                        txcircuitformation1.setVisibility(view.VISIBLE);
                        SpinnerConductorType1.setVisibility(view.VISIBLE);
                        txconductortype1.setVisibility(view.VISIBLE);
                        addcrossarmbutton1.setVisibility(view.VISIBLE);
                        addinsulatorbutton1.setVisibility(view.VISIBLE);
                        SpinnerSource1.setVisibility(view.VISIBLE);
                        txsource1.setVisibility(view.VISIBLE);
                        txCircuit2.setVisibility(view.VISIBLE);
                        TextCrossArm1.setVisibility(view.VISIBLE);
                        noofcrossarm1.setVisibility(view.VISIBLE);
                        TextNoofInsulator1.setVisibility(view.VISIBLE);
                        noofinsulator1.setVisibility(view.VISIBLE);



                        SpinnerVerticlePosition2.setVisibility(view.VISIBLE);
                        txverticleposition2.setVisibility(view.VISIBLE);
                        SpinnerCircuitFormation2.setVisibility(view.VISIBLE);
                        txcircuitformation2.setVisibility(view.VISIBLE);
                        SpinnerConductorType2.setVisibility(view.VISIBLE);
                        txconductortype2.setVisibility(view.VISIBLE);
                        addcrossarmbutton2.setVisibility(view.VISIBLE);
                        addinsulatorbutton2.setVisibility(view.VISIBLE);
                        SpinnerSource2.setVisibility(view.VISIBLE);
                        txsource2.setVisibility(view.VISIBLE);
                        txCircuit3.setVisibility(view.VISIBLE);
                        TextCrossArm2.setVisibility(view.VISIBLE);
                        noofcrossarm2.setVisibility(view.VISIBLE);
                        TextNoofInsulator2.setVisibility(view.VISIBLE);
                        noofinsulator2.setVisibility(view.VISIBLE);


                        SpinnerVerticlePosition3.setVisibility(view.GONE);
                        txverticleposition3.setVisibility(view.GONE);
                        SpinnerCircuitFormation3.setVisibility(view.GONE);
                        txcircuitformation3.setVisibility(view.GONE);
                        SpinnerConductorType3.setVisibility(view.GONE);
                        txconductortype3.setVisibility(view.GONE);
                        addcrossarmbutton3.setVisibility(view.GONE);
                        addinsulatorbutton3.setVisibility(view.GONE);
                        SpinnerSource3.setVisibility(view.GONE);
                        txsource3.setVisibility(view.GONE);
                        txCircuit4.setVisibility(view.GONE);
                        TextCrossArm3.setVisibility(view.GONE);
                        noofcrossarm3.setVisibility(view.GONE);
                        TextNoofInsulator3.setVisibility(view.GONE);
                        noofinsulator3.setVisibility(view.GONE);


                   /* savebutton1.setVisibility(view.GONE);
                    savebutton2.setVisibility(view.GONE);
                    savebutton3.setVisibility(view.GONE);
                    savebutton4.setVisibility(view.VISIBLE);
                    savebutton5.setVisibility(view.GONE);*/

                    } else if (noofcircuits.getText().toString().trim().equals("4")) {
                        SpinnerVerticlePosition.setVisibility(view.VISIBLE);
                        txverticleposition.setVisibility(view.VISIBLE);
                        SpinnerCircuitFormation.setVisibility(view.VISIBLE);
                        txcircuitformation.setVisibility(view.VISIBLE);
                        SpinnerConductorType.setVisibility(view.VISIBLE);
                        txconductortype.setVisibility(view.VISIBLE);
                        addcrossarmbutton.setVisibility(view.VISIBLE);
                        addinsulatorbutton.setVisibility(view.VISIBLE);
                        SpinnerSource.setVisibility(view.VISIBLE);
                        txsource.setVisibility(view.VISIBLE);
                        txCircuit1.setVisibility(view.VISIBLE);
                        TextCrossArm.setVisibility(view.VISIBLE);
                        noofcrossarm.setVisibility(view.VISIBLE);
                        TextNoofInsulator.setVisibility(view.VISIBLE);
                        noofinsulator.setVisibility(view.VISIBLE);



                        SpinnerVerticlePosition1.setVisibility(view.VISIBLE);
                        txverticleposition1.setVisibility(view.VISIBLE);
                        SpinnerCircuitFormation1.setVisibility(view.VISIBLE);
                        txcircuitformation1.setVisibility(view.VISIBLE);
                        SpinnerConductorType1.setVisibility(view.VISIBLE);
                        txconductortype1.setVisibility(view.VISIBLE);
                        addcrossarmbutton1.setVisibility(view.VISIBLE);
                        addinsulatorbutton1.setVisibility(view.VISIBLE);
                        SpinnerSource1.setVisibility(view.VISIBLE);
                        txsource1.setVisibility(view.VISIBLE);
                        txCircuit2.setVisibility(view.VISIBLE);
                        TextCrossArm1.setVisibility(view.VISIBLE);
                        noofcrossarm1.setVisibility(view.VISIBLE);
                        TextNoofInsulator1.setVisibility(view.VISIBLE);
                        noofinsulator1.setVisibility(view.VISIBLE);


                        SpinnerVerticlePosition2.setVisibility(view.VISIBLE);
                        txverticleposition2.setVisibility(view.VISIBLE);
                        SpinnerCircuitFormation2.setVisibility(view.VISIBLE);
                        txcircuitformation2.setVisibility(view.VISIBLE);
                        SpinnerConductorType2.setVisibility(view.VISIBLE);
                        txconductortype2.setVisibility(view.VISIBLE);
                        addcrossarmbutton2.setVisibility(view.VISIBLE);
                        addinsulatorbutton2.setVisibility(view.VISIBLE);
                        SpinnerSource2.setVisibility(view.VISIBLE);
                        txsource2.setVisibility(view.VISIBLE);
                        txCircuit3.setVisibility(view.VISIBLE);
                        TextCrossArm2.setVisibility(view.VISIBLE);
                        noofcrossarm2.setVisibility(view.VISIBLE);
                        TextNoofInsulator2.setVisibility(view.VISIBLE);
                        noofinsulator2.setVisibility(view.VISIBLE);


                        SpinnerVerticlePosition3.setVisibility(view.VISIBLE);
                        txverticleposition3.setVisibility(view.VISIBLE);
                        SpinnerCircuitFormation3.setVisibility(view.VISIBLE);
                        txcircuitformation3.setVisibility(view.VISIBLE);
                        SpinnerConductorType3.setVisibility(view.VISIBLE);
                        txconductortype3.setVisibility(view.VISIBLE);
                        addcrossarmbutton3.setVisibility(view.VISIBLE);
                        addinsulatorbutton3.setVisibility(view.VISIBLE);
                        SpinnerSource3.setVisibility(view.VISIBLE);
                        txsource3.setVisibility(view.VISIBLE);
                        txCircuit4.setVisibility(view.VISIBLE);
                        TextCrossArm3.setVisibility(view.VISIBLE);
                        noofcrossarm3.setVisibility(view.VISIBLE);
                        TextNoofInsulator3.setVisibility(view.VISIBLE);
                        noofinsulator3.setVisibility(view.VISIBLE);


                   /* savebutton1.setVisibility(view.GONE);
                    savebutton2.setVisibility(view.GONE);
                    savebutton3.setVisibility(view.GONE);
                    savebutton4.setVisibility(view.GONE);
                    savebutton5.setVisibility(view.VISIBLE);*/

                    } else {


                    }
                }else{
                    SpinnerVerticlePosition.setVisibility(view.GONE);
                    txverticleposition.setVisibility(view.GONE);
                    SpinnerCircuitFormation.setVisibility(view.GONE);
                    txcircuitformation.setVisibility(view.GONE);
                    SpinnerConductorType.setVisibility(view.GONE);
                    txconductortype.setVisibility(view.GONE);
                    addcrossarmbutton.setVisibility(view.GONE);
                    addinsulatorbutton.setVisibility(view.GONE);
                    SpinnerCrossArmType.setVisibility(view.GONE);
                    txcrossarm.setVisibility(view.GONE);
                    SpinnerCAType.setVisibility(view.GONE);
                    SpinnerCrossArmType.setVisibility(view.GONE);
                    txcrossarm.setVisibility(view.GONE);
                    SpinnerCAType.setVisibility(view.GONE);
                    txcrossarmtype.setVisibility(view.GONE);
                    SpinnerVoltage.setVisibility(view.GONE);
                    txvoltage.setVisibility(view.GONE);
                    SpinnerPhase.setVisibility(view.GONE);
                    txphase.setVisibility(view.GONE);
                    SpinnerInsulatorType.setVisibility(view.GONE);
                    txinsulatortype.setVisibility(view.GONE);
                    SpinnerType.setVisibility(view.GONE);
                    txtype.setVisibility(view.GONE);
                    SpinnerSource.setVisibility(view.GONE);
                    txsource.setVisibility(view.GONE);
                    txCircuit1.setVisibility(view.GONE);
                    SpinnerCopperSize.setVisibility(view.GONE);
                    txcoppersize.setVisibility(view.GONE);
                    SpinnerAAACSize.setVisibility(view.GONE);
                    txaaacsize.setVisibility(view.GONE);
                    SpinnerACSRSize.setVisibility(view.GONE);
                    txacsrsize.setVisibility(view.GONE);
                    SpinnerMVABCType.setVisibility(view.GONE);
                    txmvabctype.setVisibility(view.GONE);
                    SpinnerMVABCSize.setVisibility(view.GONE);
                    txmvabcsize.setVisibility(view.GONE);
                    SpinnerGantry.setVisibility(view.GONE);
                    txgantry.setVisibility(view.GONE);
                    SpinnerFeeder.setVisibility(view.GONE);
                    txfeeder.setVisibility(view.GONE);
                    TextCrossArm.setVisibility(view.GONE);
                    noofcrossarm.setVisibility(view.GONE);
                    TextCrossArmType.setVisibility(view.GONE);
                    TextCrossArmType1.setVisibility(view.GONE);
                    tx1crossarm.setVisibility(view.GONE);
                    Spinner1CrossArmType.setVisibility(view.GONE);
                    tx1crossarmtype.setVisibility(view.GONE);
                    Spinner1CAType.setVisibility(view.GONE);
                    TextNoofInsulator.setVisibility(view.GONE);
                    noofinsulator.setVisibility(view.GONE);
                    textViewInsulatorTypeType.setVisibility(view.GONE);
                    textViewInsulatorTypeType1.setVisibility(view.GONE);
                    SpinnerVoltage4.setVisibility(view.GONE);
                    txvoltage4.setVisibility(view.GONE);
                    SpinnerPhase4.setVisibility(view.GONE);
                    txphase4.setVisibility(view.GONE);
                    SpinnerInsulatorType4.setVisibility(view.GONE);
                    txinsulatortype4.setVisibility(view.GONE);
                    SpinnerType4.setVisibility(view.GONE);
                    txtype4.setVisibility(view.GONE);
                    textViewInsulatorTypeType2.setVisibility(view.GONE);
                    SpinnerVoltage5.setVisibility(view.GONE);
                    txvoltage5.setVisibility(view.GONE);
                    SpinnerPhase5.setVisibility(view.GONE);
                    txphase5.setVisibility(view.GONE);
                    SpinnerInsulatorType5.setVisibility(view.GONE);
                    txinsulatortype5.setVisibility(view.GONE);
                    SpinnerType5.setVisibility(view.GONE);
                    txtype5.setVisibility(view.GONE);
                    textViewInsulatorTypeType3.setVisibility(view.GONE);
                    SpinnerVoltage6.setVisibility(view.GONE);
                    txvoltage6.setVisibility(view.GONE);
                    SpinnerPhase6.setVisibility(view.GONE);
                    txphase6.setVisibility(view.GONE);
                    SpinnerInsulatorType6.setVisibility(view.GONE);
                    txinsulatortype6.setVisibility(view.GONE);
                    SpinnerType6.setVisibility(view.GONE);
                    txtype6.setVisibility(view.GONE);

                    SpinnerVerticlePosition1.setVisibility(view.GONE);
                    txverticleposition1.setVisibility(view.GONE);
                    SpinnerCircuitFormation1.setVisibility(view.GONE);
                    txcircuitformation1.setVisibility(view.GONE);
                    SpinnerConductorType1.setVisibility(view.GONE);
                    txconductortype1.setVisibility(view.GONE);
                    addcrossarmbutton1.setVisibility(view.GONE);
                    addinsulatorbutton1.setVisibility(view.GONE);
                    SpinnerCrossArmType1.setVisibility(view.GONE);
                    txcrossarm1.setVisibility(view.GONE);
                    SpinnerCAType1.setVisibility(view.GONE);
                    SpinnerCrossArmType1.setVisibility(view.GONE);
                    txcrossarm1.setVisibility(view.GONE);
                    SpinnerCAType1.setVisibility(view.GONE);
                    txcrossarmtype1.setVisibility(view.GONE);
                    SpinnerVoltage1.setVisibility(view.GONE);
                    txvoltage1.setVisibility(view.GONE);
                    SpinnerPhase1.setVisibility(view.GONE);
                    txphase1.setVisibility(view.GONE);
                    SpinnerInsulatorType1.setVisibility(view.GONE);
                    txinsulatortype1.setVisibility(view.GONE);
                    SpinnerType1.setVisibility(view.GONE);
                    txtype1.setVisibility(view.GONE);
                    SpinnerSource1.setVisibility(view.GONE);
                    txsource1.setVisibility(view.GONE);
                    txCircuit2.setVisibility(view.GONE);
                    SpinnerCopperSize1.setVisibility(view.GONE);
                    txcoppersize1.setVisibility(view.GONE);
                    SpinnerAAACSize1.setVisibility(view.GONE);
                    txaaacsize1.setVisibility(view.GONE);
                    SpinnerACSRSize1.setVisibility(view.GONE);
                    txacsrsize1.setVisibility(view.GONE);
                    SpinnerMVABCType1.setVisibility(view.GONE);
                    txmvabctype1.setVisibility(view.GONE);
                    SpinnerMVABCSize1.setVisibility(view.GONE);
                    txmvabcsize1.setVisibility(view.GONE);
                    SpinnerGantry1.setVisibility(view.GONE);
                    txgantry1.setVisibility(view.GONE);
                    SpinnerFeeder1.setVisibility(view.GONE);
                    txfeeder1.setVisibility(view.GONE);
                    TextCrossArm1.setVisibility(view.GONE);
                    noofcrossarm1.setVisibility(view.GONE);
                    TextCrossArmType2.setVisibility(view.GONE);
                    TextCrossArmType3.setVisibility(view.GONE);
                    tx1crossarm1.setVisibility(view.GONE);
                    Spinner1CrossArmType1.setVisibility(view.GONE);
                    tx1crossarmtype1.setVisibility(view.GONE);
                    Spinner1CAType1.setVisibility(view.GONE);
                    TextNoofInsulator1.setVisibility(view.GONE);
                    noofinsulator1.setVisibility(view.GONE);
                    textViewInsulatorTypeType4.setVisibility(view.GONE);
                    textViewInsulatorTypeType5.setVisibility(view.GONE);
                    SpinnerVoltage7.setVisibility(view.GONE);
                    txvoltage7.setVisibility(view.GONE);
                    SpinnerPhase7.setVisibility(view.GONE);
                    txphase7.setVisibility(view.GONE);
                    SpinnerInsulatorType7.setVisibility(view.GONE);
                    txinsulatortype7.setVisibility(view.GONE);
                    SpinnerType7.setVisibility(view.GONE);
                    txtype7.setVisibility(view.GONE);
                    textViewInsulatorTypeType6.setVisibility(view.GONE);
                    SpinnerVoltage8.setVisibility(view.GONE);
                    txvoltage8.setVisibility(view.GONE);
                    SpinnerPhase8.setVisibility(view.GONE);
                    txphase8.setVisibility(view.GONE);
                    SpinnerInsulatorType8.setVisibility(view.GONE);
                    txinsulatortype8.setVisibility(view.GONE);
                    SpinnerType8.setVisibility(view.GONE);
                    txtype8.setVisibility(view.GONE);
                    textViewInsulatorTypeType7.setVisibility(view.GONE);
                    SpinnerVoltage9.setVisibility(view.GONE);
                    txvoltage9.setVisibility(view.GONE);
                    SpinnerPhase9.setVisibility(view.GONE);
                    txphase9.setVisibility(view.GONE);
                    SpinnerInsulatorType9.setVisibility(view.GONE);
                    txinsulatortype9.setVisibility(view.GONE);
                    SpinnerType9.setVisibility(view.GONE);
                    txtype9.setVisibility(view.GONE);

                    SpinnerVerticlePosition2.setVisibility(view.GONE);
                    txverticleposition2.setVisibility(view.GONE);
                    SpinnerCircuitFormation2.setVisibility(view.GONE);
                    txcircuitformation2.setVisibility(view.GONE);
                    SpinnerConductorType2.setVisibility(view.GONE);
                    txconductortype2.setVisibility(view.GONE);
                    addcrossarmbutton2.setVisibility(view.GONE);
                    addinsulatorbutton2.setVisibility(view.GONE);
                    SpinnerCrossArmType2.setVisibility(view.GONE);
                    txcrossarm2.setVisibility(view.GONE);
                    SpinnerCAType2.setVisibility(view.GONE);
                    SpinnerCrossArmType2.setVisibility(view.GONE);
                    txcrossarm2.setVisibility(view.GONE);
                    SpinnerCAType2.setVisibility(view.GONE);
                    txcrossarmtype2.setVisibility(view.GONE);
                    SpinnerVoltage2.setVisibility(view.GONE);
                    txvoltage2.setVisibility(view.GONE);
                    SpinnerPhase2.setVisibility(view.GONE);
                    txphase2.setVisibility(view.GONE);
                    SpinnerInsulatorType2.setVisibility(view.GONE);
                    txinsulatortype2.setVisibility(view.GONE);
                    SpinnerType2.setVisibility(view.GONE);
                    txtype2.setVisibility(view.GONE);
                    SpinnerSource2.setVisibility(view.GONE);
                    txsource2.setVisibility(view.GONE);
                    txCircuit3.setVisibility(view.GONE);
                    SpinnerCopperSize2.setVisibility(view.GONE);
                    txcoppersize2.setVisibility(view.GONE);
                    SpinnerAAACSize2.setVisibility(view.GONE);
                    txaaacsize2.setVisibility(view.GONE);
                    SpinnerACSRSize2.setVisibility(view.GONE);
                    txacsrsize2.setVisibility(view.GONE);
                    SpinnerMVABCType2.setVisibility(view.GONE);
                    txmvabctype2.setVisibility(view.GONE);
                    SpinnerMVABCSize2.setVisibility(view.GONE);
                    txmvabcsize2.setVisibility(view.GONE);
                    SpinnerGantry2.setVisibility(view.GONE);
                    txgantry2.setVisibility(view.GONE);
                    SpinnerFeeder2.setVisibility(view.GONE);
                    txfeeder2.setVisibility(view.GONE);
                    TextCrossArm2.setVisibility(view.GONE);
                    noofcrossarm2.setVisibility(view.GONE);
                    TextCrossArmType4.setVisibility(view.GONE);
                    TextCrossArmType6.setVisibility(view.GONE);
                    tx2crossarm1.setVisibility(view.GONE);
                    Spinner2CrossArmType1.setVisibility(view.GONE);
                    tx2crossarmtype1.setVisibility(view.GONE);
                    Spinner2CAType1.setVisibility(view.GONE);
                    TextNoofInsulator2.setVisibility(view.GONE);
                    noofinsulator2.setVisibility(view.GONE);
                    textViewInsulatorTypeType8.setVisibility(view.GONE);
                    SpinnerVoltage2.setVisibility(view.GONE);
                    txvoltage2.setVisibility(view.GONE);
                    SpinnerPhase2.setVisibility(view.GONE);
                    txphase2.setVisibility(view.GONE);
                    SpinnerInsulatorType2.setVisibility(view.GONE);
                    txinsulatortype2.setVisibility(view.GONE);
                    SpinnerType2.setVisibility(view.GONE);
                    txtype2.setVisibility(view.GONE);
                    textViewInsulatorTypeType8.setVisibility(view.GONE);
                    textViewInsulatorTypeType9.setVisibility(view.GONE);
                    SpinnerVoltage10.setVisibility(view.GONE);
                    txvoltage10.setVisibility(view.GONE);
                    SpinnerPhase10.setVisibility(view.GONE);
                    txphase10.setVisibility(view.GONE);
                    SpinnerInsulatorType10.setVisibility(view.GONE);
                    txinsulatortype10.setVisibility(view.GONE);
                    SpinnerType10.setVisibility(view.GONE);
                    txtype10.setVisibility(view.GONE);
                    textViewInsulatorTypeType10.setVisibility(view.GONE);
                    SpinnerVoltage11.setVisibility(view.GONE);
                    txvoltage11.setVisibility(view.GONE);
                    SpinnerPhase11.setVisibility(view.GONE);
                    txphase11.setVisibility(view.GONE);
                    SpinnerInsulatorType11.setVisibility(view.GONE);
                    txinsulatortype11.setVisibility(view.GONE);
                    SpinnerType11.setVisibility(view.GONE);
                    txtype11.setVisibility(view.GONE);
                    textViewInsulatorTypeType11.setVisibility(view.GONE);
                    SpinnerVoltage12.setVisibility(view.GONE);
                    txvoltage12.setVisibility(view.GONE);
                    SpinnerPhase12.setVisibility(view.GONE);
                    txphase12.setVisibility(view.GONE);
                    SpinnerInsulatorType12.setVisibility(view.GONE);
                    txinsulatortype12.setVisibility(view.GONE);
                    SpinnerType12.setVisibility(view.GONE);
                    txtype12.setVisibility(view.GONE);

                    SpinnerVerticlePosition3.setVisibility(view.GONE);
                    txverticleposition3.setVisibility(view.GONE);
                    SpinnerCircuitFormation3.setVisibility(view.GONE);
                    txcircuitformation3.setVisibility(view.GONE);
                    SpinnerConductorType3.setVisibility(view.GONE);
                    txconductortype3.setVisibility(view.GONE);
                    addcrossarmbutton3.setVisibility(view.GONE);
                    addinsulatorbutton3.setVisibility(view.GONE);
                    SpinnerCrossArmType3.setVisibility(view.GONE);
                    txcrossarm3.setVisibility(view.GONE);
                    SpinnerCAType3.setVisibility(view.GONE);
                    SpinnerCrossArmType3.setVisibility(view.GONE);
                    txcrossarm3.setVisibility(view.GONE);
                    SpinnerCAType3.setVisibility(view.GONE);
                    txcrossarmtype3.setVisibility(view.GONE);
                    SpinnerVoltage3.setVisibility(view.GONE);
                    txvoltage3.setVisibility(view.GONE);
                    SpinnerPhase3.setVisibility(view.GONE);
                    txphase3.setVisibility(view.GONE);
                    SpinnerInsulatorType3.setVisibility(view.GONE);
                    txinsulatortype3.setVisibility(view.GONE);
                    SpinnerType3.setVisibility(view.GONE);
                    txtype3.setVisibility(view.GONE);
                    SpinnerSource3.setVisibility(view.GONE);
                    txsource3.setVisibility(view.GONE);
                    txCircuit4.setVisibility(view.GONE);
                    SpinnerCopperSize3.setVisibility(view.GONE);
                    txcoppersize3.setVisibility(view.GONE);
                    SpinnerAAACSize3.setVisibility(view.GONE);
                    txaaacsize3.setVisibility(view.GONE);
                    SpinnerACSRSize3.setVisibility(view.GONE);
                    txacsrsize3.setVisibility(view.GONE);
                    SpinnerMVABCType3.setVisibility(view.GONE);
                    txmvabctype3.setVisibility(view.GONE);
                    SpinnerMVABCSize3.setVisibility(view.GONE);
                    txmvabcsize3.setVisibility(view.GONE);
                    SpinnerGantry3.setVisibility(view.GONE);
                    txgantry3.setVisibility(view.GONE);
                    SpinnerFeeder3.setVisibility(view.GONE);
                    txfeeder3.setVisibility(view.GONE);
                    TextCrossArm3.setVisibility(view.GONE);
                    noofcrossarm3.setVisibility(view.GONE);
                    TextCrossArmType5.setVisibility(view.GONE);
                    TextCrossArmType7.setVisibility(view.GONE);
                    tx3crossarm1.setVisibility(view.GONE);
                    Spinner3CrossArmType1.setVisibility(view.GONE);
                    tx3crossarmtype1.setVisibility(view.GONE);
                    Spinner3CAType1.setVisibility(view.GONE);
                    TextNoofInsulator3.setVisibility(view.GONE);
                    noofinsulator3.setVisibility(view.GONE);
                    textViewInsulatorTypeType12.setVisibility(view.GONE);
                    textViewInsulatorTypeType13.setVisibility(view.GONE);
                    SpinnerVoltage13.setVisibility(view.GONE);
                    txvoltage13.setVisibility(view.GONE);
                    SpinnerPhase13.setVisibility(view.GONE);
                    txphase13.setVisibility(view.GONE);
                    SpinnerInsulatorType13.setVisibility(view.GONE);
                    txinsulatortype13.setVisibility(view.GONE);
                    SpinnerType13.setVisibility(view.GONE);
                    txtype13.setVisibility(view.GONE);
                    textViewInsulatorTypeType14.setVisibility(view.GONE);
                    SpinnerVoltage14.setVisibility(view.GONE);
                    txvoltage14.setVisibility(view.GONE);
                    SpinnerPhase14.setVisibility(view.GONE);
                    txphase14.setVisibility(view.GONE);
                    SpinnerInsulatorType14.setVisibility(view.GONE);
                    txinsulatortype14.setVisibility(view.GONE);
                    SpinnerType14.setVisibility(view.GONE);
                    txtype14.setVisibility(view.GONE);
                    textViewInsulatorTypeType15.setVisibility(view.GONE);
                    SpinnerVoltage15.setVisibility(view.GONE);
                    txvoltage15.setVisibility(view.GONE);
                    SpinnerPhase15.setVisibility(view.GONE);
                    txphase15.setVisibility(view.GONE);
                    SpinnerInsulatorType15.setVisibility(view.GONE);
                    txinsulatortype15.setVisibility(view.GONE);
                    SpinnerType15.setVisibility(view.GONE);
                    txtype15.setVisibility(view.GONE);
                }
            }
        });


        ///////////////////////////// S A V E  T O  D A T A B A S E  //////////////////////////////////////
        ///////////////////////////////// k e n t /////////////////////////////////////////////////////////

        Button savebutton = findViewById(R.id.btnSaveDB);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    System.out.println("addmvpolegeneral");

                    AlertDialog.Builder builder = new AlertDialog.Builder(EditMVPoles.this);
                    builder.setCancelable(true);
                    builder.setIcon(R.drawable.logo);
                    builder.setMessage("Do you want to save MV Pole Data?");
                    builder.setTitle("Save MV Pole");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(!isConnected(EditMVPoles.this)){
                                        // createExcel();
                                        Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                                    }else {
                                        new AddmvpoleGeneral().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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




            }



        });
        ////////////////////////K E N T ///////////////////L O A D  D A T A //////////////////////////////////

        Button loadbutton =findViewById(R.id.btnLoadData);
        loadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                latitude.setText(objMvPole.getGpsLatitude().toString());
                longitude.setText(objMvPole.getGpsLongitude().toString());
                noofcircuits.setText(objMvPole.getNoOfCct().toString());
                if(objMvPole.getEarthConductor().toString().equals("1")) {
                    System.out.println("1111111111111111111");
                    LineEarth.setChecked(true);
                    DownEarth.setChecked(true);
                }
                else if(objMvPole.getEarthConductor().toString().equals("2")){
                    System.out.println("222222222222222222222");
                    DownEarth.setChecked(true);
                    LineEarth.setChecked(false);
                }else{
                    System.out.println("333333333333333333333");
                    DownEarth.setChecked(false);
                    LineEarth.setChecked(true);
                }
                /*if(objMvPole.getPoleHeight().toString().equals("1") && objMvPole.getPoleType().toString().equals("0") && objMvPole.getWorkingLoad().toString().equals("0")){
                    System.out.println("CLICKED");
                    addpolebutton.performClick();
                    SpinnerPoletype.setAdapter(adapterpoletype);
                    SpinnerPoletype.setSelection(1);
                    addstrutbutton.performClick();
                    SpinnerStrutTypeNew.setAdapter(adapterstruttypenew);
                    SpinnerStrutTypeNew.setSelection(1);
                    addcircuitsbutton.performClick();
                    SpinnerSource.setAdapter(adaptersource2);
                    SpinnerSource.setSelection(1);
                    SpinnerPoletype.setAdapter(adapterpoletype);
                    SpinnerPoletype.setSelection(1);


                }*/
                addpolebutton.performClick();
                addstrutbutton.performClick();
                addstaybutton.performClick();
                addcircuitsbutton.performClick();
                SpinnerPoleheight.setAdapter(adapter1);
                SpinnerPoleheight.setSelection(Integer.parseInt(objMvPole.getPoleHeight().toString()));
                SpinnerPoletype.setAdapter(adapter3);
                SpinnerPoletype.setSelection(Integer.parseInt(objMvPole.getPoleType().toString()));
                SpinnerWorkingload.setAdapter(adapterstrutworkingload);
                SpinnerWorkingload.setSelection(Integer.parseInt(objMvPole.getWorkingLoad().toString()));
                SpinnerStrutheight.setAdapter(adapterstrutheight);
                SpinnerStrutheight.setSelection(Integer.parseInt(objMvPole.getStrutHeight().toString()));
                SpinnerStruttype.setAdapter(adapterstruttype);
                SpinnerStruttype.setSelection(Integer.parseInt(objMvPole.getStrutType().toString()));
                SpinnerStrutWorkingload.setAdapter(adapterstrutworkingload);
                SpinnerStrutWorkingload.setSelection(Integer.parseInt(objMvPole.getStrutWorkingLoad().toString()));
                SpinnerStayVoltage.setSelection(Integer.parseInt(objMvPole.getStayType().toString()));



                // SpinnerPoleheight.setOnItemSelectedListener(objMvPole.getPoleHeight().intValue());
                //System.out.println(objMvPole.getPoleHeight().intValue());
               /* noOf33kvCircuits.setText(objMvPole.getNoOf33Kvcircuits().toString());
                noOf11kvCircuits.setText(objMvPole.getNoOf11Kvcircuits().toString());
                noOflvcct.setText(objMvPole.getNoOfLvCct().toString());
                streetLight.setText(objMvPole.getStreetLight().toString());
                noOfStay.setText(objMvPole.getStay().toString());
                noOfStrut.setText(objMvPole.getStrut().toString());*/
                System.out.println("=========================================================");

            }
        });

        //////////////////////// K E N T //////////////// G P S Location //////////////////////////////////////
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

    }

    /////////////////e d i t  b y  K e n t //////////////////////////

    ////////// L O A D  A R E A  F E E D E R  B Y  M V   P O L E /////////////////////////////////////////
    private class loadAreaFeederByMvPole extends AsyncTask<String, Void, MmsAddmvpole[]> {

        @Override
        protected MmsAddmvpole[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            System.out.println("////////////////////////////////////////////////"+area);
            String url5 = Util.SRT_URL + "findMvPoleByArea/" + area+"/";

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
            valuesPoleNo = new String[results.length];

            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddmvpole obj = results[c];

                    valuesPoleNo[c] = obj.getPoleNo();
                    SpinnerMapPoleNo.put(c, String.valueOf(obj.getId()));
                    //  System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+obj.getPoleNo());

                }
            }
            ArrayAdapter<String> adapterMvPole = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesPoleNo);
            adapterMvPole.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerPoleNo = findViewById(R.id.spnPoleno);
            SpinnerPoleNo.setAdapter(adapterMvPole);
        }
    }

    ////////// L O A D   M V  P O L E /////////////////////////////////////////////////////////
    private class loadMvPoleObj extends AsyncTask<String, Void, MmsAddmvpole> {

        @Override
        protected MmsAddmvpole doInBackground(String... urls) {

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "findMvPoleById/" + poleno;
            System.out.println("//////////////////////////////////////"+poleno);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            objMvPole =  rest.getForObject(url6, MmsAddmvpole.class);

            return rest.getForObject(url6, MmsAddmvpole.class);
        }
    }

    ////////// L O A D   M V  P O L E /////////////////////////////////////////////////////////
    private class loadMvPoleCirciutsObj extends AsyncTask<String, Void, MmsAddmvpolecct[]> {

        @Override
        protected MmsAddmvpolecct[] doInBackground(String... urls) {

            RestTemplate rest = new RestTemplate();
            String url6 = Util.SRT_URL + "findCctByPoleId/" + poleno+ "/";
            System.out.println("//////////////////////////////////////"+poleno);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
            objMvPoleCct =  rest.getForObject(url6, MmsAddmvpolecct[].class);
            System.out.println(objMvPoleCct.length+"1111111111111111111111111111111111111111111111111");
            return rest.getForObject(url6, MmsAddmvpolecct[].class);

        }
    }


    /////////////////e d i t  b y  K e n t //////////////////////////

    ////////// L O A D  P R O V I N C E /////////////////////////////////////////////////////
    private class loadProvince extends AsyncTask<String, Void, Glcompm[]> {
        @Override
        protected Glcompm[] doInBackground(String... strings) {
            //get deptId from session manager
            SessionManager objS = new SessionManager(getBaseContext());
            String compId = objS.getPhmBranch();
            compId = compId.trim();

            phmBranch = compId;

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
                    System.out.println(spinnerMapProvince);
                    System.out.println(valuesPro);
                }
            }
            ArrayAdapter<String> adapterPr = new ArrayAdapter< String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesPro);
            adapterPr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerProvince = findViewById(R.id.province);
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
            SpinnerArea = findViewById(R.id.area);
            SpinnerArea.setAdapter(adapterArea);
        }
    }

    ////////// L O A D  C S C  B Y  A R E A /////////////////////////////////////////

    private class loadCscbyAea extends AsyncTask<String, Void, Gldeptm[]>{
        @Override
        protected Gldeptm[] doInBackground(String... strings) {
            //get deptId from session manager
            SessionManager objS = new SessionManager(getBaseContext());
            String deptId = objS.getPhmBranch();
            deptId = deptId.trim();

            RestTemplate rest = new RestTemplate();
            //String url6 = Util.SRT_URL+"findAllAreaNew";
            String url6 = Util.SRT_URL+"findAllCSCByArea/"+area+"/";

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url6,Gldeptm[].class);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Gldeptm[]  results) {
            //super.onPostExecute(results);
            // ListView Item Click Listener
            String[] province;
            valuesCsc = new String[results.length];
//
            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    Gldeptm obj = results[c];
                    valuesCsc[c] = obj.getDeptNm();
                    SpinnerMapCsc.put(c,obj.getDeptId());
                }
            }
            ArrayAdapter<String> adapterPr = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesCsc);
            adapterPr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerCSC = findViewById(R.id.spnCSC);
            SpinnerCSC.setAdapter(adapterPr);
        }
    }

    /////////////////////////////////// L O A D  G A N T R Y   B Y  A R E A  1////////////////////////////////////////////
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
            SpinnerGantry = findViewById(R.id.gantry);
            SpinnerGantry.setAdapter(adapterGantry);

        }
    }

    /////////////////////////////////// L O A D  G A N T R Y   B Y  A R E A  2////////////////////////////////////////////
    private class loadGantrybyArea1 extends AsyncTask<String, Void, MmsAddgantry[]>{

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

            valuesGantry1 = new String[results.length];

            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddgantry obj = results[c];
                    valuesGantry1[c] = obj.getName();
                    SpinnerMapGantry1.put(c, obj.getId());
                }
            }

            ArrayAdapter<String> adapterGantry = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesGantry);
            adapterGantry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerGantry1 = findViewById(R.id.gantry1);
            SpinnerGantry1.setAdapter(adapterGantry);
        }
    }

    /////////////////////////////////// L O A D  G A N T R Y   B Y  A R E A  3////////////////////////////////////////////
    private class loadGantrybyArea2 extends AsyncTask<String, Void, MmsAddgantry[]>{

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

            valuesGantry2 = new String[results.length];

            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddgantry obj = results[c];
                    valuesGantry2[c] = obj.getName();
                    SpinnerMapGantry2.put(c, obj.getId());
                }
            }

            ArrayAdapter<String> adapterGantry = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesGantry);
            adapterGantry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerGantry2 = findViewById(R.id.gantry2);
            SpinnerGantry2.setAdapter(adapterGantry);
        }
    }

    /////////////////////////////////// L O A D  G A N T R Y   B Y  A R E A  4////////////////////////////////////////////
    private class loadGantrybyArea3 extends AsyncTask<String, Void, MmsAddgantry[]>{

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

            valuesGantry3 = new String[results.length];

            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddgantry obj = results[c];
                    valuesGantry3[c] = obj.getName();
                    SpinnerMapGantry3.put(c, obj.getId());
                }
            }

            ArrayAdapter<String> adapterGantry = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesGantry);
            adapterGantry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerGantry3 = findViewById(R.id.gantry3);
            SpinnerGantry3.setAdapter(adapterGantry);
        }
    }

    ////////// L O A D   F E E D E R   B Y    G A N T R Y  1 /////////////////////////////////////////
    private class loadFeederByGantry extends AsyncTask<String, Void, MmsAddfeeder[]> {
        @Override
        protected MmsAddfeeder[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findFeederyById/" +gantry + "/";

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
                    System.out.println("name = " + obj.getName() + ", gantry = " + obj.getCode());
//                    valuesFeeder[c] = obj.getName();
                    if (obj.getName() != null && obj.getCode() != null) {
                        valueFeederList.add(obj.getName());
                        SpinnerMapFeeder.put(c, obj.getId().getFeederId());
                    }
                }
            }
            ArrayAdapter<String> adapterFeeder = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valueFeederList.toArray(new String[]{}));
            adapterFeeder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerFeeder = findViewById(R.id.feeder);
            SpinnerFeeder.setAdapter(adapterFeeder);

        }
    }

    ////////// L O A D   F E E D E R   B Y    G A N T R Y  2 /////////////////////////////////////////
    private class loadFeederByGantry1 extends AsyncTask<String, Void, MmsAddfeeder[]> {
        @Override
        protected MmsAddfeeder[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findFeederyById/" +gantry1 + "/";

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
            valuesFeeder1 = new String[results.length];

            List<String> valueFeederList = new ArrayList<>();
            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddfeeder obj = results[c];
                    System.out.println("name = " + obj.getName() + ", gantry = " + obj.getCode());
//                    valuesFeeder[c] = obj.getName();
                    if (obj.getName() != null && obj.getCode() != null) {
                        valueFeederList.add(obj.getName());
                        SpinnerMapFeeder1.put(c, obj.getId().getFeederId());
                    }
                }
            }
            ArrayAdapter<String> adapterFeeder = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valueFeederList.toArray(new String[]{}));
            adapterFeeder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerFeeder1 = findViewById(R.id.feeder1);
            SpinnerFeeder1.setAdapter(adapterFeeder);

        }
    }

    ////////// L O A D   F E E D E R   B Y    G A N T R Y  2 /////////////////////////////////////////
    private class loadFeederByGantry2 extends AsyncTask<String, Void, MmsAddfeeder[]> {
        @Override
        protected MmsAddfeeder[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findFeederyById/" +gantry2 + "/";

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
            valuesFeeder2 = new String[results.length];

            List<String> valueFeederList = new ArrayList<>();
            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddfeeder obj = results[c];
                    System.out.println("name = " + obj.getName() + ", gantry = " + obj.getCode());
//                    valuesFeeder[c] = obj.getName();
                    if (obj.getName() != null && obj.getCode() != null) {
                        valueFeederList.add(obj.getName());
                        SpinnerMapFeeder2.put(c, obj.getId().getFeederId());
                    }
                }
            }
            ArrayAdapter<String> adapterFeeder = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valueFeederList.toArray(new String[]{}));
            adapterFeeder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerFeeder2 = findViewById(R.id.feeder2);
            SpinnerFeeder2.setAdapter(adapterFeeder);

        }
    }

    ////////// L O A D   F E E D E R   B Y    G A N T R Y  2 /////////////////////////////////////////
    private class loadFeederByGantry3 extends AsyncTask<String, Void, MmsAddfeeder[]> {
        @Override
        protected MmsAddfeeder[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findFeederyById/" +gantry3 + "/";

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
            valuesFeeder3 = new String[results.length];

            List<String> valueFeederList = new ArrayList<>();
            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddfeeder obj = results[c];
                    System.out.println("name = " + obj.getName() + ", gantry = " + obj.getCode());
//                    valuesFeeder[c] = obj.getName();
                    if (obj.getName() != null && obj.getCode() != null) {
                        valueFeederList.add(obj.getName());
                        SpinnerMapFeeder3.put(c, obj.getId().getFeederId());
                    }
                }
            }
            ArrayAdapter<String> adapterFeeder = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valueFeederList.toArray(new String[]{}));
            adapterFeeder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerFeeder3 = findViewById(R.id.feeder3);
            SpinnerFeeder3.setAdapter(adapterFeeder);

        }
    }



    /////////////////////////////////////////////////////// e d i t  b y  k e n t //////////////////////////////////

    private class AddmvpoleGeneral extends AsyncTask<Void, Void, String>{

        String Area,Csc,PoleNo,Latitude,Longitude,Noofcircuits,FeederNo,SegmentId,NoOf33kvcircuits,NoOf11kvcircuits,Nooflvcct,StreetLight,NoofStay,NoofStrut;
        int PoleHeight,PoleType,WorkingLoad,strutHeight,strutType,strutWorkingLoad,staytype,conductor,conductor1,conductor2,conductor3,source,verticleposition,circuitformation,conductortype,conductortype01,conductortype02,conductortype03,conductortype04,crossarmtype,catype,voltage,phase,insulatortype,insulatortypetype,source1,verticleposition1,circuitformation1,conductortype1,conductortype11,conductortype12,conductortype13,conductortype14,crossarmtype1,catype1,voltage1,phase1,insulatortype1,insulatortypetype1,source2,verticleposition2,circuitformation2,conducortype2,conductortype21,conductortype22,conductortype23,conductortype24,crossarmtype2,catype2,voltage2,phase2,insulatortype2,insulatortypetype2,source3,verticleposition3,circuitformation3,conducortype3,conductortype31,conductortype32,conductortype33,conductortype34,crossarmtype3,catype3,voltage3,phase3,insulatortype3,insulatortypetype3,checkEarth,
                crossarmtype11,catype11,crossarmtype22,catype22,crossarmtype33,catype33,crossarmtype44,catype44,voltage4,phase4,insulatortype4,insulatortypetype4,voltage5,phase5,insulatortype5,insulatortypetype5,voltage6,phase6,insulatortype6,insulatortypetype6,voltage7,phase7,insulatortype7,insulatortypetype7,voltage8,phase8,insulatortype8,insulatortypetype8,voltage9,phase9,insulatortype9,insulatortypetype9,voltage10,phase10,insulatortype10,insulatortypetype10,voltage11,phase11,insulatortype11,insulatortypetype11,voltage12,phase12,insulatortype12,insulatortypetype12,voltage13,phase13,insulatortype13,insulatortypetype13,voltage14,phase14,insulatortype14,insulatortypetype14,voltage15,phase15,insulatortype15,insulatortypetype15;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Area = area;
            Csc = csc;
            // PoleNo = poleNo.getText().toString();
            Noofcircuits = noofcircuits.getText().toString();
            if(SpinnerTypeNew.getSelectedItem().toString().equals("Select Type")){
                PoleType = 0;
            }else if(SpinnerTypeNew.getSelectedItem().toString().equals("Wood"))
            {   PoleType = 1;

            }else if(SpinnerTypeNew.getSelectedItem().toString().equals("Steel"))
            {   PoleType = 2;

            }else{
                PoleType = 3;
            }

            if(SpinnerPoleheight.getSelectedItem().toString().equals("Select Type"))
            {
                PoleHeight = 0;
            }
            else if(SpinnerPoleheight.getSelectedItem().toString().equals("9m"))
            {
                PoleHeight = 1;
            }
            else if(SpinnerPoleheight.getSelectedItem().toString().equals("10m")){
                PoleHeight = 2;
            }
            else if(SpinnerPoleheight.getSelectedItem().toString().equals("11m")){
                PoleHeight = 3;
            }
            else {
                PoleHeight = 4;
            }

            if(SpinnerStrutheight.getSelectedItem().toString().equals("Select Type"))
            {
                strutHeight = 0;
            }
            else if(SpinnerStrutheight.getSelectedItem().toString().equals("9m"))
            {
                strutHeight = 1;
            }
            else if(SpinnerStrutheight.getSelectedItem().toString().equals("10m")){
                strutHeight = 2;
            }
            else if(SpinnerStrutheight.getSelectedItem().toString().equals("11m")){
                strutHeight = 3;
            }
            else {
                strutHeight = 4;
            }

            if(SpinnerPoletype.getSelectedItem().toString().equals("Select Type"))
            {
                PoleType=0;
            }
            else if(SpinnerPoletype.getSelectedItem().toString().equals("Tubuler"))
            {
                PoleType=1;
            }
            else if(SpinnerPoletype.getSelectedItem().toString().equals("P.G. Pole"))
            {
                PoleType=2;
            }
            else if(SpinnerPoletype.getSelectedItem().toString().equals("RC Pole"))
            {
                PoleType=3;
            }
            else {
                PoleType =4;
            }

            if(SpinnerStruttype.getSelectedItem().toString().equals("Select Type"))
            {
                strutType=0;
            }
            else if(SpinnerStruttype.getSelectedItem().toString().equals("Tubuler"))
            {
                strutType=1;
            }
            else if(SpinnerStruttype.getSelectedItem().toString().equals("P.G. Pole"))
            {
                strutType=2;
            }
            else if(SpinnerStruttype.getSelectedItem().toString().equals("RC Pole"))
            {
                strutType=3;
            }
            else {
                strutType =4;
            }

            if(SpinnerWorkingload.getSelectedItem().toString().equals("Select Type")){
                WorkingLoad = 0;
            }
            else if(SpinnerWorkingload.getSelectedItem().toString().equals("225Kg")){
                WorkingLoad = 1;
            }
            else if(SpinnerWorkingload.getSelectedItem().toString().equals("350Kg")){
                WorkingLoad = 2;

            }else if(SpinnerWorkingload.getSelectedItem().toString().equals("500Kg")){
                WorkingLoad = 3;

            }else if(SpinnerWorkingload.getSelectedItem().toString().equals("850Kg")){
                WorkingLoad = 4;

            }else{
                WorkingLoad = 5;
            }

            if(SpinnerStrutWorkingload.getSelectedItem().toString().equals("Select Type")){
                strutWorkingLoad = 0;
            }
            else if(SpinnerStrutWorkingload.getSelectedItem().toString().equals("225Kg")){
                strutWorkingLoad = 1;
            }
            else if(SpinnerStrutWorkingload.getSelectedItem().toString().equals("350Kg")){
                strutWorkingLoad = 2;

            }else if(SpinnerStrutWorkingload.getSelectedItem().toString().equals("500Kg")){
                strutWorkingLoad = 3;

            }else if(SpinnerStrutWorkingload.getSelectedItem().toString().equals("850Kg")){
                strutWorkingLoad = 4;

            }else{
                strutWorkingLoad = 5;
            }

            if(SpinnerStayVoltage.getSelectedItem().toString().equals("Select Type")){
                staytype = 0;
            }else if(SpinnerStayVoltage.getSelectedItem().toString().equals("33kV")){
                staytype = 1;
            }else{
                staytype = 2;
            }

            if(LineEarth.isChecked() && DownEarth.isChecked()){
                checkEarth=1;
            }else if(DownEarth.isChecked()){
                checkEarth=2;
            }else if(LineEarth.isChecked()){
                checkEarth=3;
            }else{

            }



            if(SpinnerSource.getSelectedItem().toString().equals("Select Type")){
                source = 0;
            }else if(SpinnerSource.getSelectedItem().toString().equals("Gantry")){
                source = 1;
            }else if(SpinnerSource.getSelectedItem().toString().equals("Grid")){
                source = 2;
            }else{
                source = 3;
            }

            if(SpinnerSource1.getSelectedItem().toString().equals("Select Type")){
                source1 = 0;
            }else if(SpinnerSource1.getSelectedItem().toString().equals("Gantry")){
                source1 = 1;
            }else if(SpinnerSource1.getSelectedItem().toString().equals("Grid")){
                source1 = 2;
            }else{
                source1 = 3;
            }

            if(SpinnerSource2.getSelectedItem().toString().equals("Select Type")){
                source2 = 0;
            }else if(SpinnerSource2.getSelectedItem().toString().equals("Gantry")){
                source2 = 1;
            }else if(SpinnerSource2.getSelectedItem().toString().equals("Grid")){
                source2 = 2;
            }else{
                source2 = 3;
            }

            if(SpinnerSource3.getSelectedItem().toString().equals("Select Type")){
                source3 = 0;
            }else if(SpinnerSource3.getSelectedItem().toString().equals("Gantry")){
                source3 = 1;
            }else if(SpinnerSource3.getSelectedItem().toString().equals("Grid")){
                source3 = 2;
            }else{
                source3 = 3;
            }

            if(SpinnerVerticlePosition.getSelectedItem().toString().equals("Select Type")){
                verticleposition = 0;
            }else if(SpinnerVerticlePosition.getSelectedItem().toString().equals("Gantry")){
                verticleposition = 1;
            }else if(SpinnerVerticlePosition.getSelectedItem().toString().equals("Grid")){
                verticleposition = 2;
            }else{
                verticleposition = 3;
            }

            if(SpinnerVerticlePosition1.getSelectedItem().toString().equals("Select Type")){
                verticleposition1 = 0;
            }else if(SpinnerVerticlePosition1.getSelectedItem().toString().equals("Gantry")){
                verticleposition1 = 1;
            }else if(SpinnerVerticlePosition1.getSelectedItem().toString().equals("Grid")){
                verticleposition1 = 2;
            }else{
                verticleposition1 = 3;
            }

            if(SpinnerVerticlePosition2.getSelectedItem().toString().equals("Select Type")){
                verticleposition2 = 0;
            }else if(SpinnerVerticlePosition2.getSelectedItem().toString().equals("Gantry")){
                verticleposition2 = 1;
            }else if(SpinnerVerticlePosition2.getSelectedItem().toString().equals("Grid")){
                verticleposition2 = 2;
            }else{
                verticleposition2 = 3;
            }

            if(SpinnerVerticlePosition3.getSelectedItem().toString().equals("Select Type")){
                verticleposition3 = 0;
            }else if(SpinnerVerticlePosition3.getSelectedItem().toString().equals("Gantry")){
                verticleposition3 = 1;
            }else if(SpinnerVerticlePosition3.getSelectedItem().toString().equals("Grid")){
                verticleposition3 = 2;
            }else{
                verticleposition3 = 3;
            }

            if(SpinnerCircuitFormation.getSelectedItem().toString().equals("Select Type")){
                circuitformation = 0;

            }else if(SpinnerCircuitFormation.getSelectedItem().toString().equals("horizontal")){
                circuitformation = 1;
            }else if(SpinnerCircuitFormation.getSelectedItem().toString().equals("vertical")){
                circuitformation = 2;
            }else{
                circuitformation = 3;
            }

            if(SpinnerCircuitFormation1.getSelectedItem().toString().equals("Select Type")){
                circuitformation1 = 0;

            }else if(SpinnerCircuitFormation1.getSelectedItem().toString().equals("horizontal")){
                circuitformation1 = 1;
            }else if(SpinnerCircuitFormation1.getSelectedItem().toString().equals("vertical")){
                circuitformation1 = 2;
            }else{
                circuitformation1 = 3;
            }

            if(SpinnerCircuitFormation2.getSelectedItem().toString().equals("Select Type")){
                circuitformation2 = 0;

            }else if(SpinnerCircuitFormation2.getSelectedItem().toString().equals("horizontal")){
                circuitformation2 = 1;
            }else if(SpinnerCircuitFormation2.getSelectedItem().toString().equals("vertical")){
                circuitformation2 = 2;
            }else{
                circuitformation2 = 3;
            }

            if(SpinnerCircuitFormation3.getSelectedItem().toString().equals("Select Type")){
                circuitformation3 = 0;

            }else if(SpinnerCircuitFormation3.getSelectedItem().toString().equals("horizontal")){
                circuitformation3 = 1;
            }else if(SpinnerCircuitFormation3.getSelectedItem().toString().equals("vertical")){
                circuitformation3 = 2;
            }else{
                circuitformation3 = 3;
            }


            if(SpinnerConductorType.getSelectedItem().toString().equals("Select Type")){
                conductor = 0;
            }else if(SpinnerConductorType.getSelectedItem().toString().equals("Copper")){
                conductor =1;

            }else if(SpinnerConductorType.getSelectedItem().toString().equals("AAAC")){
                conductor =2;

            }else if(SpinnerConductorType.getSelectedItem().toString().equals("ACSR")){
                conductor =3;

            }else{
                conductor =4;
            }

            if(SpinnerConductorType1.getSelectedItem().toString().equals("Select Type")){
                conductor1 = 0;
            }else if(SpinnerConductorType1.getSelectedItem().toString().equals("Copper")){
                conductor1 =1;

            }else if(SpinnerConductorType1.getSelectedItem().toString().equals("AAAC")){
                conductor1 =2;

            }else if(SpinnerConductorType1.getSelectedItem().toString().equals("ACSR")){
                conductor1 =3;

            }else{
                conductor1 =4;
            }

            if(SpinnerConductorType2.getSelectedItem().toString().equals("Select Type")){
                conductor2 = 0;
            }else if(SpinnerConductorType2.getSelectedItem().toString().equals("Copper")){
                conductor2 =1;

            }else if(SpinnerConductorType2.getSelectedItem().toString().equals("AAAC")){
                conductor2 =2;

            }else if(SpinnerConductorType2.getSelectedItem().toString().equals("ACSR")){
                conductor2 =3;

            }else{
                conductor2 =4;
            }

            if(SpinnerConductorType3.getSelectedItem().toString().equals("Select Type")){
                conductor3 = 0;
            }else if(SpinnerConductorType3.getSelectedItem().toString().equals("Copper")){
                conductor3 =1;

            }else if(SpinnerConductorType3.getSelectedItem().toString().equals("AAAC")){
                conductor3 =2;

            }else if(SpinnerConductorType3.getSelectedItem().toString().equals("ACSR")){
                conductor3 =3;

            }else{
                conductor3 =4;
            }

            if(SpinnerCopperSize.getSelectedItem().toString().equals("Select Type")){
                conductortype = 0;
            }else if(SpinnerCopperSize.getSelectedItem().toString().equals("1(7.6mm)")){
                conductortype = 1;
            }else if(SpinnerCopperSize.getSelectedItem().toString().equals("2(7mm)")){
                conductortype = 2;
            }else if(SpinnerCopperSize.getSelectedItem().toString().equals("4(6mm)")){
                conductortype = 3;
            }else if(SpinnerCopperSize.getSelectedItem().toString().equals("6(5mm)")){
                conductortype = 4;
            }else{
                conductortype = 5;
            }

            if(SpinnerCopperSize1.getSelectedItem().toString().equals("Select Type")){
                conductortype1 = 0;
            }else if(SpinnerCopperSize1.getSelectedItem().toString().equals("1(7.6mm)")){
                conductortype1 = 1;
            }else if(SpinnerCopperSize1.getSelectedItem().toString().equals("2(7mm)")){
                conductortype1 = 2;
            }else if(SpinnerCopperSize1.getSelectedItem().toString().equals("4(6mm)")){
                conductortype1 = 3;
            }else if(SpinnerCopperSize1.getSelectedItem().toString().equals("6(5mm)")){
                conductortype1 = 4;
            }else{
                conductortype1 = 5;
            }

            if(SpinnerCopperSize2.getSelectedItem().toString().equals("Select Type")){
                conducortype2 = 0;
            }else if(SpinnerCopperSize2.getSelectedItem().toString().equals("1(7.6mm)")){
                conducortype2 = 1;
            }else if(SpinnerCopperSize2.getSelectedItem().toString().equals("2(7mm)")){
                conducortype2 = 2;
            }else if(SpinnerCopperSize2.getSelectedItem().toString().equals("4(6mm)")){
                conducortype2 = 3;
            }else if(SpinnerCopperSize2.getSelectedItem().toString().equals("6(5mm)")){
                conducortype2 = 4;
            }else{
                conducortype2 = 5;
            }

            if(SpinnerCopperSize3.getSelectedItem().toString().equals("Select Type")){
                conducortype3 = 0;
            }else if(SpinnerCopperSize3.getSelectedItem().toString().equals("1(7.6mm)")){
                conducortype3 = 1;
            }else if(SpinnerCopperSize3.getSelectedItem().toString().equals("2(7mm)")){
                conducortype3 = 2;
            }else if(SpinnerCopperSize3.getSelectedItem().toString().equals("4(6mm)")){
                conducortype3 = 3;
            }else if(SpinnerCopperSize3.getSelectedItem().toString().equals("6(5mm)")){
                conducortype3 = 4;
            }else{
                conducortype3 = 5;
            }

            if(SpinnerAAACSize.getSelectedItem().toString().equals("Select Type")){

                conductortype01 = 0;
            }else if(SpinnerAAACSize.getSelectedItem().toString().equals("ELM -19/3.76")){
                conductortype01 = 1;
            }else{
                conductortype01 = 2;
            }

            if(SpinnerAAACSize1.getSelectedItem().toString().equals("Select Type")){

                conductortype11 = 0;
            }else if(SpinnerAAACSize1.getSelectedItem().toString().equals("ELM -19/3.76")){
                conductortype11 = 1;
            }else{
                conductortype11 = 2;
            }

            if(SpinnerAAACSize2.getSelectedItem().toString().equals("Select Type")){

                conductortype21 = 0;
            }else if(SpinnerAAACSize2.getSelectedItem().toString().equals("ELM -19/3.76")){
                conductortype21 = 1;
            }else{
                conductortype21 = 2;
            }

            if(SpinnerAAACSize3.getSelectedItem().toString().equals("Select Type")){

                conductortype31 = 0;
            }else if(SpinnerAAACSize3.getSelectedItem().toString().equals("ELM -19/3.76")){
                conductortype31 = 1;
            }else{
                conductortype31 = 2;
            }

            if(SpinnerACSRSize.getSelectedItem().toString().equals("Select Type")){
                conductortype02 = 0;
            }else if(SpinnerACSRSize.getSelectedItem().toString().equals("7/2.59 Weasel")){
                conductortype02 = 1;
            }else if(SpinnerACSRSize.getSelectedItem().toString().equals("Racoon")){
                conductortype02 = 2;
            }else if(SpinnerACSRSize.getSelectedItem().toString().equals("Lynx")){
                conductortype02 = 3;
            }else{
                conductortype02 = 4;
            }

            if(SpinnerACSRSize1.getSelectedItem().toString().equals("Select Type")){
                conductortype12 = 0;
            }else if(SpinnerACSRSize1.getSelectedItem().toString().equals("7/2.59 Weasel")){
                conductortype12 = 1;
            }else if(SpinnerACSRSize1.getSelectedItem().toString().equals("Racoon")){
                conductortype12 = 2;
            }else if(SpinnerACSRSize1.getSelectedItem().toString().equals("Lynx")){
                conductortype12 = 3;
            }else{
                conductortype12 = 4;
            }

            if(SpinnerACSRSize2.getSelectedItem().toString().equals("Select Type")){
                conductortype22 = 0;
            }else if(SpinnerACSRSize2.getSelectedItem().toString().equals("7/2.59 Weasel")){
                conductortype22 = 1;
            }else if(SpinnerACSRSize2.getSelectedItem().toString().equals("Racoon")){
                conductortype22 = 2;
            }else if(SpinnerACSRSize2.getSelectedItem().toString().equals("Lynx")){
                conductortype22 = 3;
            }else{
                conductortype22 = 4;
            }

            if(SpinnerACSRSize3.getSelectedItem().toString().equals("Select Type")){
                conductortype32 = 0;
            }else if(SpinnerACSRSize3.getSelectedItem().toString().equals("7/2.59 Weasel")){
                conductortype32 = 1;
            }else if(SpinnerACSRSize3.getSelectedItem().toString().equals("Racoon")){
                conductortype32 = 2;
            }else if(SpinnerACSRSize3.getSelectedItem().toString().equals("Lynx")){
                conductortype32 = 3;
            }else{
                conductortype32 = 4;
            }

            if(SpinnerMVABCType.getSelectedItem().toString().equals("Select Type")){
                conductortype03 = 0;
            }else if(SpinnerMVABCType.getSelectedItem().toString().equals("11kV")) {
                conductortype03 = 1;
            }else{
                conductortype03 = 2;
            }

            if(SpinnerMVABCType1.getSelectedItem().toString().equals("Select Type")){
                conductortype13 = 0;
            }else if(SpinnerMVABCType1.getSelectedItem().toString().equals("11kV")) {
                conductortype13 = 1;
            }else{
                conductortype13 = 2;
            }

            if(SpinnerMVABCType2.getSelectedItem().toString().equals("Select Type")){
                conductortype23 = 0;
            }else if(SpinnerMVABCType2.getSelectedItem().toString().equals("11kV")) {
                conductortype23 = 1;
            }else{
                conductortype23 = 2;
            }

            if(SpinnerMVABCType3.getSelectedItem().toString().equals("Select Type")){
                conductortype33 = 0;
            }else if(SpinnerMVABCType3.getSelectedItem().toString().equals("11kV")) {
                conductortype33 = 1;
            }else{
                conductortype33 = 2;
            }

            if(SpinnerMVABCSize.getSelectedItem().toString().equals("Select Type")){
                conductortype04 = 0;
            }else if(SpinnerMVABCSize.getSelectedItem().toString().equals("95sqmm")) {
                conductortype04 = 1;
            }else{
                conductortype04 = 2;
            }

            if(SpinnerMVABCSize1.getSelectedItem().toString().equals("Select Type")){
                conductortype14 = 0;
            }else if(SpinnerMVABCSize1.getSelectedItem().toString().equals("95sqmm")) {
                conductortype14 = 1;
            }else{
                conductortype14 = 2;
            }

            if(SpinnerMVABCSize2.getSelectedItem().toString().equals("Select Type")){
                conductortype24 = 0;
            }else if(SpinnerMVABCSize2.getSelectedItem().toString().equals("95sqmm")) {
                conductortype24 = 1;
            }else{
                conductortype24 = 2;
            }

            if(SpinnerMVABCSize3.getSelectedItem().toString().equals("Select Type")){
                conductortype34 = 0;
            }else if(SpinnerMVABCSize3.getSelectedItem().toString().equals("95sqmm")) {
                conductortype34 = 1;
            }else{
                conductortype34 = 2;
            }

            if(SpinnerCrossArmType.getSelectedItem().toString().equals("Select Type")){
                crossarmtype=0;

            }else if(SpinnerCrossArmType.getSelectedItem().toString().equals("Pin CComposite")){
                crossarmtype=1;
            }else if(SpinnerCrossArmType.getSelectedItem().toString().equals("Pin")){
                crossarmtype=2;
            }else{
                crossarmtype=3;
            }

            if(SpinnerCrossArmType1.getSelectedItem().toString().equals("Select Type")){
                crossarmtype1=0;

            }else if(SpinnerCrossArmType1.getSelectedItem().toString().equals("Pin CComposite")){
                crossarmtype1=1;
            }else if(SpinnerCrossArmType1.getSelectedItem().toString().equals("Pin")){
                crossarmtype1=2;
            }else{
                crossarmtype1=3;
            }

            if(SpinnerCrossArmType2.getSelectedItem().toString().equals("Select Type")){
                crossarmtype2=0;

            }else if(SpinnerCrossArmType2.getSelectedItem().toString().equals("Pin CComposite")){
                crossarmtype2=1;
            }else if(SpinnerCrossArmType1.getSelectedItem().toString().equals("Pin")){
                crossarmtype2=2;
            }else{
                crossarmtype2=3;
            }

            if(SpinnerCrossArmType3.getSelectedItem().toString().equals("Select Type")){
                crossarmtype3=0;

            }else if(SpinnerCrossArmType3.getSelectedItem().toString().equals("Pin CComposite")){
                crossarmtype3=1;
            }else if(SpinnerCrossArmType3.getSelectedItem().toString().equals("Pin")){
                crossarmtype3=2;
            }else{
                crossarmtype3=3;
            }

            if(Spinner1CrossArmType.getSelectedItem().toString().equals("Select Type")){
                crossarmtype11=0;

            }else if(Spinner1CrossArmType.getSelectedItem().toString().equals("Pin CComposite")){
                crossarmtype11=1;
            }else if(Spinner1CrossArmType.getSelectedItem().toString().equals("Pin")){
                crossarmtype11=2;
            }else{
                crossarmtype11=3;
            }

            if(Spinner1CrossArmType1.getSelectedItem().toString().equals("Select Type")){
                crossarmtype22=0;

            }else if(Spinner1CrossArmType1.getSelectedItem().toString().equals("Pin CComposite")){
                crossarmtype22=1;
            }else if(Spinner1CrossArmType1.getSelectedItem().toString().equals("Pin")){
                crossarmtype22=2;
            }else{
                crossarmtype22=3;
            }

            if(Spinner2CrossArmType1.getSelectedItem().toString().equals("Select Type")){
                crossarmtype33=0;

            }else if(Spinner2CrossArmType1.getSelectedItem().toString().equals("Pin CComposite")){
                crossarmtype33=1;
            }else if(Spinner2CrossArmType1.getSelectedItem().toString().equals("Pin")){
                crossarmtype33=2;
            }else{
                crossarmtype33=3;
            }

            if(Spinner3CrossArmType1.getSelectedItem().toString().equals("Select Type")){
                crossarmtype44=0;

            }else if(Spinner3CrossArmType1.getSelectedItem().toString().equals("Pin CComposite")){
                crossarmtype44=1;
            }else if(Spinner3CrossArmType1.getSelectedItem().toString().equals("Pin")){
                crossarmtype44=2;
            }else{
                crossarmtype44=3;
            }



            if(SpinnerCAType.getSelectedItem().toString().equals("Select Type")){
                catype=0;

            }else if(SpinnerCAType.getSelectedItem().toString().equals("33kV")){
                catype=1;
            }else{
                catype=2;
            }

            if(SpinnerCAType1.getSelectedItem().toString().equals("Select Type")){
                catype1=0;

            }else if(SpinnerCAType1.getSelectedItem().toString().equals("33kV")){
                catype1=1;
            }else{
                catype1=2;
            }
            if(SpinnerCAType2.getSelectedItem().toString().equals("Select Type")){
                catype2=0;

            }else if(SpinnerCAType2.getSelectedItem().toString().equals("33kV")){
                catype2=1;
            }else{
                catype2=2;
            }
            if(SpinnerCAType3.getSelectedItem().toString().equals("Select Type")){
                catype3=0;

            }else if(SpinnerCAType3.getSelectedItem().toString().equals("33kV")){
                catype3=1;
            }else{
                catype3=2;
            }

            if(Spinner1CAType.getSelectedItem().toString().equals("Select Type")){
                catype11=0;

            }else if(Spinner1CAType.getSelectedItem().toString().equals("33kV")){
                catype11=1;
            }else{
                catype11=2;
            }

            if(Spinner1CAType1.getSelectedItem().toString().equals("Select Type")){
                catype22=0;

            }else if(Spinner1CAType1.getSelectedItem().toString().equals("33kV")){
                catype22=1;
            }else{
                catype22=2;
            }
            if(Spinner2CAType1.getSelectedItem().toString().equals("Select Type")){
                catype33=0;

            }else if(Spinner2CAType1.getSelectedItem().toString().equals("33kV")){
                catype33=1;
            }else{
                catype33=2;
            }
            if(Spinner3CAType1.getSelectedItem().toString().equals("Select Type")){
                catype44=0;

            }else if(Spinner3CAType1.getSelectedItem().toString().equals("33kV")){
                catype44=1;
            }else{
                catype44=2;
            }

            if(SpinnerVoltage.getSelectedItem().toString().equals("Select Type")){
                voltage =0;
            }else if(SpinnerVoltage.getSelectedItem().toString().equals("11kV")){
                voltage =1;
            }else{
                voltage =2;
            }

            if(SpinnerVoltage1.getSelectedItem().toString().equals("Select Type")){
                voltage1 =0;
            }else if(SpinnerVoltage1.getSelectedItem().toString().equals("11kV")){
                voltage1 =1;
            }else{
                voltage1 =2;
            }

            if(SpinnerVoltage2.getSelectedItem().toString().equals("Select Type")){
                voltage2 =0;
            }else if(SpinnerVoltage2.getSelectedItem().toString().equals("11kV")){
                voltage2 =1;
            }else{
                voltage2 =2;
            }

            if(SpinnerVoltage3.getSelectedItem().toString().equals("Select Type")){
                voltage3 =0;
            }else if(SpinnerVoltage3.getSelectedItem().toString().equals("11kV")){
                voltage3 =1;
            }else{
                voltage3 =2;
            }

            if(SpinnerVoltage4.getSelectedItem().toString().equals("Select Type")){
                voltage4 =0;
            }else if(SpinnerVoltage4.getSelectedItem().toString().equals("11kV")){
                voltage4 =1;
            }else{
                voltage4 =2;
            }

            if(SpinnerVoltage5.getSelectedItem().toString().equals("Select Type")){
                voltage5 =0;
            }else if(SpinnerVoltage5.getSelectedItem().toString().equals("11kV")){
                voltage5 =1;
            }else{
                voltage5 =2;
            }

            if(SpinnerVoltage6.getSelectedItem().toString().equals("Select Type")){
                voltage6 =0;
            }else if(SpinnerVoltage6.getSelectedItem().toString().equals("11kV")){
                voltage6 =1;
            }else{
                voltage6 =2;
            }
            if(SpinnerVoltage7.getSelectedItem().toString().equals("Select Type")){
                voltage7 =0;
            }else if(SpinnerVoltage7.getSelectedItem().toString().equals("11kV")){
                voltage7 =1;
            }else{
                voltage7 =2;
            }
            if(SpinnerVoltage8.getSelectedItem().toString().equals("Select Type")){
                voltage8 =0;
            }else if(SpinnerVoltage8.getSelectedItem().toString().equals("11kV")){
                voltage8 =1;
            }else{
                voltage8 =2;
            }
            if(SpinnerVoltage9.getSelectedItem().toString().equals("Select Type")){
                voltage9 =0;
            }else if(SpinnerVoltage9.getSelectedItem().toString().equals("11kV")){
                voltage9 =1;
            }else{
                voltage9 =2;
            }

            if(SpinnerVoltage10.getSelectedItem().toString().equals("Select Type")){
                voltage10 =0;
            }else if(SpinnerVoltage10.getSelectedItem().toString().equals("11kV")){
                voltage10 =1;
            }else{
                voltage10 =2;
            }
            if(SpinnerVoltage11.getSelectedItem().toString().equals("Select Type")){
                voltage11 =0;
            }else if(SpinnerVoltage11.getSelectedItem().toString().equals("11kV")){
                voltage11 =1;
            }else{
                voltage11 =2;
            }

            if(SpinnerVoltage12.getSelectedItem().toString().equals("Select Type")){
                voltage12 =0;
            }else if(SpinnerVoltage12.getSelectedItem().toString().equals("11kV")){
                voltage12 =1;
            }else{
                voltage12 =2;
            }

            if(SpinnerVoltage13.getSelectedItem().toString().equals("Select Type")){
                voltage13 =0;
            }else if(SpinnerVoltage13.getSelectedItem().toString().equals("11kV")){
                voltage13 =1;
            }else{
                voltage13 =2;
            }

            if(SpinnerVoltage14.getSelectedItem().toString().equals("Select Type")){
                voltage14 =0;
            }else if(SpinnerVoltage14.getSelectedItem().toString().equals("11kV")){
                voltage14 =1;
            }else{
                voltage14 =2;
            }

            if(SpinnerVoltage15.getSelectedItem().toString().equals("Select Type")){
                voltage15 =0;
            }else if(SpinnerVoltage15.getSelectedItem().toString().equals("11kV")){
                voltage15 =1;
            }else{
                voltage15 =2;
            }

            if(SpinnerPhase.getSelectedItem().toString().equals("Select Type")){
                phase =0;
            }else if(SpinnerPhase.getSelectedItem().toString().equals("A")){
                phase =1;
            }else if(SpinnerPhase.getSelectedItem().toString().equals("B")){
                phase =2;
            }else{
                phase =3;
            }

            if(SpinnerPhase1.getSelectedItem().toString().equals("Select Type")){
                phase1 =0;
            }else if(SpinnerPhase1.getSelectedItem().toString().equals("A")){
                phase1 =1;
            }else if(SpinnerPhase1.getSelectedItem().toString().equals("B")){
                phase1 =2;
            }else{
                phase1 =3;
            }

            if(SpinnerPhase2.getSelectedItem().toString().equals("Select Type")){
                phase2 =0;
            }else if(SpinnerPhase2.getSelectedItem().toString().equals("A")){
                phase2 =1;
            }else if(SpinnerPhase2.getSelectedItem().toString().equals("B")){
                phase2 =2;
            }else{
                phase2 =3;
            }

            if(SpinnerPhase3.getSelectedItem().toString().equals("Select Type")){
                phase3 =0;
            }else if(SpinnerPhase3.getSelectedItem().toString().equals("A")){
                phase3 =1;
            }else if(SpinnerPhase3.getSelectedItem().toString().equals("B")){
                phase3 =2;
            }else{
                phase3 =3;
            }
            if(SpinnerPhase4.getSelectedItem().toString().equals("Select Type")){
                phase4 =0;
            }else if(SpinnerPhase4.getSelectedItem().toString().equals("A")){
                phase4 =1;
            }else if(SpinnerPhase4.getSelectedItem().toString().equals("B")){
                phase4 =2;
            }else{
                phase4 =3;
            }
            if(SpinnerPhase5.getSelectedItem().toString().equals("Select Type")){
                phase5 =0;
            }else if(SpinnerPhase5.getSelectedItem().toString().equals("A")){
                phase5 =1;
            }else if(SpinnerPhase5.getSelectedItem().toString().equals("B")){
                phase5 =2;
            }else{
                phase5 =3;
            }

            if(SpinnerPhase6.getSelectedItem().toString().equals("Select Type")){
                phase6 =0;
            }else if(SpinnerPhase6.getSelectedItem().toString().equals("A")){
                phase6 =1;
            }else if(SpinnerPhase6.getSelectedItem().toString().equals("B")){
                phase6 =2;
            }else{
                phase6 =3;
            }

            if(SpinnerPhase7.getSelectedItem().toString().equals("Select Type")){
                phase7 =0;
            }else if(SpinnerPhase7.getSelectedItem().toString().equals("A")){
                phase7 =1;
            }else if(SpinnerPhase7.getSelectedItem().toString().equals("B")){
                phase7 =2;
            }else{
                phase7 =3;
            }

            if(SpinnerPhase8.getSelectedItem().toString().equals("Select Type")){
                phase8 =0;
            }else if(SpinnerPhase8.getSelectedItem().toString().equals("A")){
                phase8 =1;
            }else if(SpinnerPhase8.getSelectedItem().toString().equals("B")){
                phase8 =2;
            }else{
                phase8 =3;
            }

            if(SpinnerPhase9.getSelectedItem().toString().equals("Select Type")){
                phase9 =0;
            }else if(SpinnerPhase9.getSelectedItem().toString().equals("A")){
                phase9 =1;
            }else if(SpinnerPhase9.getSelectedItem().toString().equals("B")){
                phase9 =2;
            }else{
                phase9 =3;
            }

            if(SpinnerPhase10.getSelectedItem().toString().equals("Select Type")){
                phase10 =0;
            }else if(SpinnerPhase10.getSelectedItem().toString().equals("A")){
                phase10 =1;
            }else if(SpinnerPhase10.getSelectedItem().toString().equals("B")){
                phase10 =2;
            }else{
                phase10 =3;
            }

            if(SpinnerPhase11.getSelectedItem().toString().equals("Select Type")){
                phase10 =0;
            }else if(SpinnerPhase11.getSelectedItem().toString().equals("A")){
                phase11 =1;
            }else if(SpinnerPhase11.getSelectedItem().toString().equals("B")){
                phase11 =2;
            }else{
                phase11 =3;
            }

            if(SpinnerPhase12.getSelectedItem().toString().equals("Select Type")){
                phase12 =0;
            }else if(SpinnerPhase12.getSelectedItem().toString().equals("A")){
                phase12 =1;
            }else if(SpinnerPhase12.getSelectedItem().toString().equals("B")){
                phase12 =2;
            }else{
                phase12 =3;
            }

            if(SpinnerPhase13.getSelectedItem().toString().equals("Select Type")){
                phase13 =0;
            }else if(SpinnerPhase13.getSelectedItem().toString().equals("A")){
                phase13 =1;
            }else if(SpinnerPhase13.getSelectedItem().toString().equals("B")){
                phase13 =2;
            }else{
                phase13 =3;
            }

            if(SpinnerPhase14.getSelectedItem().toString().equals("Select Type")){
                phase14 =0;
            }else if(SpinnerPhase14.getSelectedItem().toString().equals("A")){
                phase14 =1;
            }else if(SpinnerPhase14.getSelectedItem().toString().equals("B")){
                phase14 =2;
            }else{
                phase14 =3;
            }

            if(SpinnerPhase15.getSelectedItem().toString().equals("Select Type")){
                phase15 =0;
            }else if(SpinnerPhase15.getSelectedItem().toString().equals("A")){
                phase15 =1;
            }else if(SpinnerPhase15.getSelectedItem().toString().equals("B")){
                phase15 =2;
            }else{
                phase15 =3;
            }

            if(SpinnerInsulatorType.getSelectedItem().toString().equals("Select Type")){
                insulatortype=0;
            }else if(SpinnerInsulatorType.getSelectedItem().toString().equals("Pin")){
                insulatortype=1;
            }else if(SpinnerInsulatorType.getSelectedItem().toString().equals("Shackle")){
                insulatortype=2;
            }else{
                insulatortype=3;
            }

            if(SpinnerInsulatorType1.getSelectedItem().toString().equals("Select Type")){
                insulatortype1=0;
            }else if(SpinnerInsulatorType1.getSelectedItem().toString().equals("Pin")){
                insulatortype1=1;
            }else if(SpinnerInsulatorType1.getSelectedItem().toString().equals("Shackle")){
                insulatortype1=2;
            }else{
                insulatortype1=3;
            }

            if(SpinnerInsulatorType2.getSelectedItem().toString().equals("Select Type")){
                insulatortype2=0;
            }else if(SpinnerInsulatorType2.getSelectedItem().toString().equals("Pin")){
                insulatortype2=1;
            }else if(SpinnerInsulatorType2.getSelectedItem().toString().equals("Shackle")){
                insulatortype2=2;
            }else{
                insulatortype2=3;
            }

            if(SpinnerInsulatorType3.getSelectedItem().toString().equals("Select Type")){
                insulatortype3=0;
            }else if(SpinnerInsulatorType3.getSelectedItem().toString().equals("Pin")){
                insulatortype3=1;
            }else if(SpinnerInsulatorType3.getSelectedItem().toString().equals("Shackle")){
                insulatortype3=2;
            }else{
                insulatortype3=3;
            }

            if(SpinnerInsulatorType4.getSelectedItem().toString().equals("Select Type")){
                insulatortype4=0;
            }else if(SpinnerInsulatorType4.getSelectedItem().toString().equals("Pin")){
                insulatortype4=1;
            }else if(SpinnerInsulatorType4.getSelectedItem().toString().equals("Shackle")){
                insulatortype4=2;
            }else{
                insulatortype4=3;
            }

            if(SpinnerInsulatorType5.getSelectedItem().toString().equals("Select Type")){
                insulatortype5=0;
            }else if(SpinnerInsulatorType5.getSelectedItem().toString().equals("Pin")){
                insulatortype5=1;
            }else if(SpinnerInsulatorType5.getSelectedItem().toString().equals("Shackle")){
                insulatortype5=2;
            }else{
                insulatortype5=3;
            }

            if(SpinnerInsulatorType6.getSelectedItem().toString().equals("Select Type")){
                insulatortype6=0;
            }else if(SpinnerInsulatorType6.getSelectedItem().toString().equals("Pin")){
                insulatortype6=1;
            }else if(SpinnerInsulatorType6.getSelectedItem().toString().equals("Shackle")){
                insulatortype6=2;
            }else{
                insulatortype6=3;
            }

            if(SpinnerInsulatorType7.getSelectedItem().toString().equals("Select Type")){
                insulatortype7=0;
            }else if(SpinnerInsulatorType7.getSelectedItem().toString().equals("Pin")){
                insulatortype7=1;
            }else if(SpinnerInsulatorType7.getSelectedItem().toString().equals("Shackle")){
                insulatortype7=2;
            }else{
                insulatortype7=3;
            }

            if(SpinnerInsulatorType8.getSelectedItem().toString().equals("Select Type")){
                insulatortype8=0;
            }else if(SpinnerInsulatorType8.getSelectedItem().toString().equals("Pin")){
                insulatortype8=1;
            }else if(SpinnerInsulatorType8.getSelectedItem().toString().equals("Shackle")){
                insulatortype8=2;
            }else{
                insulatortype8=3;
            }

            if(SpinnerInsulatorType9.getSelectedItem().toString().equals("Select Type")){
                insulatortype9=0;
            }else if(SpinnerInsulatorType9.getSelectedItem().toString().equals("Pin")){
                insulatortype9=1;
            }else if(SpinnerInsulatorType9.getSelectedItem().toString().equals("Shackle")){
                insulatortype9=2;
            }else{
                insulatortype9=3;
            }

            if(SpinnerInsulatorType10.getSelectedItem().toString().equals("Select Type")){
                insulatortype10=0;
            }else if(SpinnerInsulatorType10.getSelectedItem().toString().equals("Pin")){
                insulatortype10=1;
            }else if(SpinnerInsulatorType10.getSelectedItem().toString().equals("Shackle")){
                insulatortype10=2;
            }else{
                insulatortype10=3;
            }

            if(SpinnerInsulatorType11.getSelectedItem().toString().equals("Select Type")){
                insulatortype11=0;
            }else if(SpinnerInsulatorType11.getSelectedItem().toString().equals("Pin")){
                insulatortype11=1;
            }else if(SpinnerInsulatorType11.getSelectedItem().toString().equals("Shackle")){
                insulatortype11=2;
            }else{
                insulatortype11=3;
            }

            if(SpinnerInsulatorType12.getSelectedItem().toString().equals("Select Type")){
                insulatortype12=0;
            }else if(SpinnerInsulatorType12.getSelectedItem().toString().equals("Pin")){
                insulatortype12=1;
            }else if(SpinnerInsulatorType12.getSelectedItem().toString().equals("Shackle")){
                insulatortype12=2;
            }else{
                insulatortype12=3;
            }

            if(SpinnerInsulatorType13.getSelectedItem().toString().equals("Select Type")){
                insulatortype13=0;
            }else if(SpinnerInsulatorType13.getSelectedItem().toString().equals("Pin")){
                insulatortype13=1;
            }else if(SpinnerInsulatorType13.getSelectedItem().toString().equals("Shackle")){
                insulatortype13=2;
            }else{
                insulatortype13=3;
            }
            if(SpinnerInsulatorType14.getSelectedItem().toString().equals("Select Type")){
                insulatortype14=0;
            }else if(SpinnerInsulatorType14.getSelectedItem().toString().equals("Pin")){
                insulatortype14=1;
            }else if(SpinnerInsulatorType14.getSelectedItem().toString().equals("Shackle")){
                insulatortype14=2;
            }else{
                insulatortype14=3;
            }

            if(SpinnerInsulatorType15.getSelectedItem().toString().equals("Select Type")){
                insulatortype15=0;
            }else if(SpinnerInsulatorType15.getSelectedItem().toString().equals("Pin")){
                insulatortype15=1;
            }else if(SpinnerInsulatorType15.getSelectedItem().toString().equals("Shackle")){
                insulatortype15=2;
            }else{
                insulatortype15=3;
            }

            if(SpinnerType.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype=0;
            }else if(SpinnerType.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype=1;
            }else if(SpinnerType.getSelectedItem().toString().equals("Composite")){
                insulatortypetype=2;
            }else{
                insulatortypetype=3;
            }

            if(SpinnerType1.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype1=0;
            }else if(SpinnerType1.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype1=1;
            }else if(SpinnerType1.getSelectedItem().toString().equals("Composite")){
                insulatortypetype1=2;
            }else{
                insulatortypetype1=3;
            }

            if(SpinnerType2.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype2=0;
            }else if(SpinnerType2.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype2=1;
            }else if(SpinnerType2.getSelectedItem().toString().equals("Composite")){
                insulatortypetype2=2;
            }else{
                insulatortypetype2=3;
            }

            if(SpinnerType3.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype3=0;
            }else if(SpinnerType3.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype3=1;
            }else if(SpinnerType3.getSelectedItem().toString().equals("Composite")){
                insulatortypetype3=2;
            }else{
                insulatortypetype3=3;
            }

            if(SpinnerType4.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype4=0;
            }else if(SpinnerType4.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype4=1;
            }else if(SpinnerType4.getSelectedItem().toString().equals("Composite")){
                insulatortypetype4=2;
            }else{
                insulatortypetype4=3;
            }

            if(SpinnerType5.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype5=0;
            }else if(SpinnerType5.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype5=1;
            }else if(SpinnerType5.getSelectedItem().toString().equals("Composite")){
                insulatortypetype5=2;
            }else{
                insulatortypetype5=3;
            }
            if(SpinnerType6.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype6=0;
            }else if(SpinnerType6.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype6=1;
            }else if(SpinnerType6.getSelectedItem().toString().equals("Composite")){
                insulatortypetype6=2;
            }else{
                insulatortypetype6=3;
            }

            if(SpinnerType7.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype7=0;
            }else if(SpinnerType7.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype7=1;
            }else if(SpinnerType7.getSelectedItem().toString().equals("Composite")){
                insulatortypetype7=2;
            }else{
                insulatortypetype7=3;
            }

            if(SpinnerType8.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype8=0;
            }else if(SpinnerType8.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype8=1;
            }else if(SpinnerType8.getSelectedItem().toString().equals("Composite")){
                insulatortypetype8=2;
            }else{
                insulatortypetype8=3;
            }

            if(SpinnerType9.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype9=0;
            }else if(SpinnerType9.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype9=1;
            }else if(SpinnerType9.getSelectedItem().toString().equals("Composite")){
                insulatortypetype9=2;
            }else{
                insulatortypetype9=3;
            }
            if(SpinnerType10.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype10=0;
            }else if(SpinnerType10.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype10=1;
            }else if(SpinnerType10.getSelectedItem().toString().equals("Composite")){
                insulatortypetype10=2;
            }else{
                insulatortypetype10=3;
            }
            if(SpinnerType11.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype11=0;
            }else if(SpinnerType11.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype11=1;
            }else if(SpinnerType11.getSelectedItem().toString().equals("Composite")){
                insulatortypetype11=2;
            }else{
                insulatortypetype11=3;
            }
            if(SpinnerType12.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype12=0;
            }else if(SpinnerType12.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype12=1;
            }else if(SpinnerType12.getSelectedItem().toString().equals("Composite")){
                insulatortypetype12=2;
            }else{
                insulatortypetype12=3;

            }if(SpinnerType13.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype13=0;
            }else if(SpinnerType13.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype13=1;
            }else if(SpinnerType13.getSelectedItem().toString().equals("Composite")){
                insulatortypetype13=2;
            }else{
                insulatortypetype13=3;
            }

            if(SpinnerType14.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype14=0;
            }else if(SpinnerType14.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype14=1;
            }else if(SpinnerType14.getSelectedItem().toString().equals("Composite")){
                insulatortypetype14=2;
            }else{
                insulatortypetype14=3;
            }
            if(SpinnerType15.getSelectedItem().toString().equals("Select Type")){
                insulatortypetype15=0;
            }else if(SpinnerType15.getSelectedItem().toString().equals("Porcelain")){
                insulatortypetype15=1;
            }else if(SpinnerType15.getSelectedItem().toString().equals("Composite")){
                insulatortypetype15=2;
            }else{
                insulatortypetype15=3;
            }

            Latitude = latitude.getText().toString();
            Longitude = longitude.getText().toString();

        }
        @Override
        protected String doInBackground(Void... urls) {
            System.out.println("AddmvpoleGeneral");

            //set values to MmsAddgantry object
            PoleModel objpolemodel =new PoleModel();
            MmsAddmvpolecct objmvpolecct = new MmsAddmvpolecct();
            MmsAddmvpolecct objmvpolecct1 = new MmsAddmvpolecct();
            MmsAddmvpolecct objmvpolecct2 = new MmsAddmvpolecct();
            MmsAddmvpolecct objmvpolecct3 = new MmsAddmvpolecct();
            MmsAddmvpolecctPK objmvpolecctpk = new MmsAddmvpolecctPK();
            //MmsAddmvpole objAddgmvpole = new MmsAddmvpole();

            ArrayList<MmsAddmvpolecct> list=new ArrayList<MmsAddmvpolecct>();
            // set value for mv pole object
            objMvPole.setArea(Area);
            objMvPole.setCsc(Csc);
            objMvPole.setPoleNo(poleName);
            objMvPole.setPoleHeight(new BigDecimal(PoleHeight));
            objMvPole.setPoleType(new BigDecimal(PoleType));
            objMvPole.setWorkingLoad(new BigDecimal(WorkingLoad));
            objMvPole.setGpsLongitude(new BigDecimal(Longitude));
            objMvPole.setGpsLatitude(new BigDecimal(Latitude));
            objMvPole.setStrutHeight(new BigDecimal(strutHeight));
            objMvPole.setStrutType(new BigDecimal(strutType));
            objMvPole.setStrutWorkingLoad(new BigDecimal(strutWorkingLoad));
            objMvPole.setStayType(new BigDecimal(staytype));
            objMvPole.setEarthConductor(new BigDecimal(checkEarth));
            objMvPole.setNoOfCct(new BigDecimal(Noofcircuits));
            System.out.println("EEEEEEEEEEEEE"+checkEarth);

            System.out.println(" N O  O F  C I R C U I T S"+Noofcircuits);
            if(Noofcircuits.equals("1")){

                // set values for circuit object 1
                objMvPoleCct[0].setCctSource(String.valueOf(source));
                objMvPoleCct[0].setCctVerticlePosition(new BigDecimal(verticleposition));
                objMvPoleCct[0].setCctFormation(new BigDecimal(circuitformation));
                objMvPoleCct[0].setCctConductorI(new BigDecimal(conductor));
                if(String.valueOf(conductor).equals("1")){
                    System.out.println("/////////////"+"Copper");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor).equals("2")){
                    System.out.println("/////////////"+"AAAC");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype01));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor).equals("3")){
                    System.out.println("/////////////"+"ACSR");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype02));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor).equals("4")){
                    System.out.println("/////////////"+"MV ABC");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype03));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(conductortype04));
                }else{
                    System.out.println("/////////////"+"Other");

                }
                objMvPoleCct[0].setCctCrossArmType(new BigDecimal(crossarmtype));
                objMvPoleCct[0].setCctCrossArmVoltage(new BigDecimal(catype));
                objMvPoleCct[0].setCctInsulatorVoltage(new BigDecimal(voltage));
                objMvPoleCct[0].setInsPhase(new BigDecimal(phase));
                objMvPoleCct[0].setCctInsulatorTypeI(new BigDecimal(insulatortype));
                objMvPoleCct[0].setCctInsulatorTypeIi(new BigDecimal(insulatortypetype));

                objMvPoleCct[0].setCctCrossArmType2(new BigDecimal(crossarmtype11));
                objMvPoleCct[0].setCctCrossArmVoltage2(new BigDecimal(catype11));
                objMvPoleCct[0].setCctInsulatorVoltage2(new BigDecimal(voltage4));
                objMvPoleCct[0].setInsPhase2(new BigDecimal(phase4));
                objMvPoleCct[0].setCctInsulatorTypeI2(new BigDecimal(insulatortype4));
                objMvPoleCct[0].setCctInsulatorTypeIi2(new BigDecimal(insulatortypetype4));
                objMvPoleCct[0].setCctInsulatorVoltage3(new BigDecimal(voltage5));
                objMvPoleCct[0].setInsPhase3(new BigDecimal(phase5));
                objMvPoleCct[0].setCctInsulatorTypeI3(new BigDecimal(insulatortype5));
                objMvPoleCct[0].setCctInsulatorTypeIi3(new BigDecimal(insulatortypetype5));
                objMvPoleCct[0].setCctInsulatorVoltage4(new BigDecimal(voltage6));
                objMvPoleCct[0].setInsPhase4(new BigDecimal(phase6));
                objMvPoleCct[0].setCctInsulatorTypeI4(new BigDecimal(insulatortype6));
                objMvPoleCct[0].setCctInsulatorTypeIi4(new BigDecimal(insulatortypetype6));


                objMvPoleCct[0].setGantryId(new BigDecimal(gantry));
                objMvPoleCct[0].setFeederId(feeder);
                list.add(objMvPoleCct[0]);
            }else if(Noofcircuits.equals("2")){

                // set values for circuit object 1
                objMvPoleCct[0].setCctSource(String.valueOf(source));
                objMvPoleCct[0].setCctVerticlePosition(new BigDecimal(verticleposition));
                objMvPoleCct[0].setCctFormation(new BigDecimal(circuitformation));
                objMvPoleCct[0].setCctConductorI(new BigDecimal(conductor));
                if(String.valueOf(conductor).equals("1")){
                    System.out.println("/////////////"+"Copper");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor).equals("2")){
                    System.out.println("/////////////"+"AAAC");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype01));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor).equals("3")){
                    System.out.println("/////////////"+"ACSR");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype02));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor).equals("4")){
                    System.out.println("/////////////"+"MV ABC");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype03));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(conductortype04));
                }else{
                    System.out.println("/////////////"+"Other");

                }
                objMvPoleCct[0].setCctCrossArmType(new BigDecimal(crossarmtype));
                objMvPoleCct[0].setCctCrossArmVoltage(new BigDecimal(catype));
                objMvPoleCct[0].setCctInsulatorVoltage(new BigDecimal(voltage));
                objMvPoleCct[0].setInsPhase(new BigDecimal(phase));
                objMvPoleCct[0].setCctInsulatorTypeI(new BigDecimal(insulatortype));
                objMvPoleCct[0].setCctInsulatorTypeIi(new BigDecimal(insulatortypetype));
                objMvPoleCct[0].setCctCrossArmType2(new BigDecimal(crossarmtype11));
                objMvPoleCct[0].setCctCrossArmVoltage2(new BigDecimal(catype11));
                objMvPoleCct[0].setCctInsulatorVoltage(new BigDecimal(voltage));
                objMvPoleCct[0].setInsPhase(new BigDecimal(phase));
                objMvPoleCct[0].setCctInsulatorTypeI(new BigDecimal(insulatortype));
                objMvPoleCct[0].setCctInsulatorTypeIi(new BigDecimal(insulatortypetype));
                objMvPoleCct[0].setCctInsulatorVoltage2(new BigDecimal(voltage4));
                objMvPoleCct[0].setInsPhase2(new BigDecimal(phase4));
                objMvPoleCct[0].setCctInsulatorTypeI2(new BigDecimal(insulatortype4));
                objMvPoleCct[0].setCctInsulatorTypeIi2(new BigDecimal(insulatortypetype4));
                objMvPoleCct[0].setCctInsulatorVoltage3(new BigDecimal(voltage5));
                objMvPoleCct[0].setInsPhase3(new BigDecimal(phase5));
                objMvPoleCct[0].setCctInsulatorTypeI3(new BigDecimal(insulatortype5));
                objMvPoleCct[0].setCctInsulatorTypeIi3(new BigDecimal(insulatortypetype5));
                objMvPoleCct[0].setCctInsulatorVoltage4(new BigDecimal(voltage6));
                objMvPoleCct[0].setInsPhase4(new BigDecimal(phase6));
                objMvPoleCct[0].setCctInsulatorTypeI4(new BigDecimal(insulatortype6));
                objMvPoleCct[0].setCctInsulatorTypeIi4(new BigDecimal(insulatortypetype6));

                // set values for circuit object 2
                objMvPoleCct[1].setCctSource(String.valueOf(source1));
                objMvPoleCct[1].setCctVerticlePosition(new BigDecimal(verticleposition1));
                objMvPoleCct[1].setCctFormation(new BigDecimal(circuitformation1));
                objMvPoleCct[1].setCctConductorI(new BigDecimal(conductor));
                if(String.valueOf(conductor1).equals("1")){
                    System.out.println("/////////////"+"Copper");
                    objMvPoleCct[1].setCctConductorIi(new BigDecimal(conductortype1));
                    objMvPoleCct[1].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor1).equals("2")){
                    System.out.println("/////////////"+"AAAC");
                    objMvPoleCct[1].setCctConductorIi(new BigDecimal(conductortype11));
                    objMvPoleCct[1].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor1).equals("3")){
                    System.out.println("/////////////"+"ACSR");
                    objMvPoleCct[1].setCctConductorIi(new BigDecimal(conductortype12));
                    objMvPoleCct[1].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor1).equals("4")){
                    System.out.println("/////////////"+"MV ABC");
                    objMvPoleCct[1].setCctConductorIi(new BigDecimal(conductortype13));
                    objMvPoleCct[1].setCctConductorIii(new BigDecimal(conductortype14));
                }else{
                    System.out.println("/////////////"+"Other");

                }
                objMvPoleCct[1].setCctCrossArmType(new BigDecimal(crossarmtype1));
                objMvPoleCct[1].setCctCrossArmVoltage(new BigDecimal(catype1));
                objMvPoleCct[1].setCctInsulatorVoltage(new BigDecimal(voltage1));
                objMvPoleCct[1].setInsPhase(new BigDecimal(phase1));
                objMvPoleCct[1].setCctInsulatorTypeI(new BigDecimal(insulatortype1));
                objMvPoleCct[1].setCctInsulatorTypeIi(new BigDecimal(insulatortypetype1));
                objMvPoleCct[1].setCctCrossArmType2(new BigDecimal(crossarmtype22));
                objMvPoleCct[1].setCctCrossArmVoltage2(new BigDecimal(catype22));
                objMvPoleCct[1].setCctInsulatorVoltage2(new BigDecimal(voltage7));
                objMvPoleCct[1].setInsPhase2(new BigDecimal(phase7));
                objMvPoleCct[1].setCctInsulatorTypeI2(new BigDecimal(insulatortype7));
                objMvPoleCct[1].setCctInsulatorTypeIi2(new BigDecimal(insulatortypetype7));
                objMvPoleCct[1].setCctInsulatorVoltage3(new BigDecimal(voltage8));
                objMvPoleCct[1].setInsPhase3(new BigDecimal(phase8));
                objMvPoleCct[1].setCctInsulatorTypeI3(new BigDecimal(insulatortype8));
                objMvPoleCct[1].setCctInsulatorTypeIi3(new BigDecimal(insulatortypetype8));
                objMvPoleCct[1].setCctInsulatorVoltage4(new BigDecimal(voltage9));
                objMvPoleCct[1].setInsPhase4(new BigDecimal(phase9));
                objMvPoleCct[1].setCctInsulatorTypeI4(new BigDecimal(insulatortype9));
                objMvPoleCct[1].setCctInsulatorTypeIi4(new BigDecimal(insulatortypetype9));


                objMvPoleCct[0].setGantryId(new BigDecimal(gantry));
                objMvPoleCct[0].setFeederId(feeder);
                objMvPoleCct[1].setGantryId(new BigDecimal(gantry1));
                objMvPoleCct[1].setFeederId(feeder1);
                list.add(objMvPoleCct[0]);
                list.add(objMvPoleCct[1]);
            }else if(Noofcircuits.equals("3")){
                // set values for circuit object 1
                objMvPoleCct[0].setCctSource(String.valueOf(source));
                objMvPoleCct[0].setCctVerticlePosition(new BigDecimal(verticleposition));
                objMvPoleCct[0].setCctFormation(new BigDecimal(circuitformation));
                objMvPoleCct[0].setCctConductorI(new BigDecimal(conductor));
                if(String.valueOf(conductor).equals("1")){
                    System.out.println("/////////////"+"Copper");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor).equals("2")){
                    System.out.println("/////////////"+"AAAC");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype01));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor).equals("3")){
                    System.out.println("/////////////"+"ACSR");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype02));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor).equals("4")){
                    System.out.println("/////////////"+"MV ABC");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype03));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(conductortype04));
                }else{
                    System.out.println("/////////////"+"Other");

                }
                objMvPoleCct[0].setCctCrossArmType(new BigDecimal(crossarmtype));
                objMvPoleCct[0].setCctCrossArmVoltage(new BigDecimal(catype));
                objMvPoleCct[0].setCctInsulatorVoltage(new BigDecimal(voltage));
                objMvPoleCct[0].setInsPhase(new BigDecimal(phase));
                objMvPoleCct[0].setCctInsulatorTypeI(new BigDecimal(insulatortype));
                objMvPoleCct[0].setCctInsulatorTypeIi(new BigDecimal(insulatortypetype));
                objMvPoleCct[0].setCctCrossArmType2(new BigDecimal(crossarmtype11));
                objMvPoleCct[0].setCctCrossArmVoltage2(new BigDecimal(catype11));
                objMvPoleCct[0].setCctInsulatorVoltage2(new BigDecimal(voltage4));
                objMvPoleCct[0].setInsPhase2(new BigDecimal(phase4));
                objMvPoleCct[0].setCctInsulatorTypeI2(new BigDecimal(insulatortype4));
                objMvPoleCct[0].setCctInsulatorTypeIi2(new BigDecimal(insulatortypetype4));
                objMvPoleCct[0].setCctInsulatorVoltage3(new BigDecimal(voltage5));
                objMvPoleCct[0].setInsPhase3(new BigDecimal(phase5));
                objMvPoleCct[0].setCctInsulatorTypeI3(new BigDecimal(insulatortype5));
                objMvPoleCct[0].setCctInsulatorTypeIi3(new BigDecimal(insulatortypetype5));
                objMvPoleCct[0].setCctInsulatorVoltage4(new BigDecimal(voltage6));
                objMvPoleCct[0].setInsPhase4(new BigDecimal(phase6));
                objMvPoleCct[0].setCctInsulatorTypeI4(new BigDecimal(insulatortype6));
                objMvPoleCct[0].setCctInsulatorTypeIi4(new BigDecimal(insulatortypetype6));

                // set values for circuit object 2
                objMvPoleCct[1].setCctSource(String.valueOf(source1));
                objMvPoleCct[1].setCctVerticlePosition(new BigDecimal(verticleposition1));
                objMvPoleCct[1].setCctFormation(new BigDecimal(circuitformation1));
                objMvPoleCct[1].setCctConductorI(new BigDecimal(conductor));
                if(String.valueOf(conductor1).equals("1")){
                    System.out.println("/////////////"+"Copper");
                    objMvPoleCct[1].setCctConductorIi(new BigDecimal(conductortype1));
                    objMvPoleCct[1].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor1).equals("2")){
                    System.out.println("/////////////"+"AAAC");
                    objMvPoleCct[1].setCctConductorIi(new BigDecimal(conductortype11));
                    objMvPoleCct[1].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor1).equals("3")){
                    System.out.println("/////////////"+"ACSR");
                    objMvPoleCct[1].setCctConductorIi(new BigDecimal(conductortype12));
                    objMvPoleCct[1].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor1).equals("4")){
                    System.out.println("/////////////"+"MV ABC");
                    objMvPoleCct[1].setCctConductorIi(new BigDecimal(conductortype13));
                    objMvPoleCct[1].setCctConductorIii(new BigDecimal(conductortype14));
                }else{
                    System.out.println("/////////////"+"Other");

                }
                objMvPoleCct[1].setCctCrossArmType(new BigDecimal(crossarmtype1));
                objMvPoleCct[1].setCctCrossArmVoltage(new BigDecimal(catype1));
                objMvPoleCct[1].setCctInsulatorVoltage(new BigDecimal(voltage1));
                objMvPoleCct[1].setInsPhase(new BigDecimal(phase1));
                objMvPoleCct[1].setCctInsulatorTypeI(new BigDecimal(insulatortype1));
                objMvPoleCct[1].setCctInsulatorTypeIi(new BigDecimal(insulatortypetype1));
                objMvPoleCct[1].setCctCrossArmType2(new BigDecimal(crossarmtype22));
                objMvPoleCct[1].setCctCrossArmVoltage2(new BigDecimal(catype22));
                objMvPoleCct[1].setCctInsulatorVoltage2(new BigDecimal(voltage7));
                objMvPoleCct[1].setInsPhase2(new BigDecimal(phase7));
                objMvPoleCct[1].setCctInsulatorTypeI2(new BigDecimal(insulatortype7));
                objMvPoleCct[1].setCctInsulatorTypeIi2(new BigDecimal(insulatortypetype7));
                objMvPoleCct[1].setCctInsulatorVoltage3(new BigDecimal(voltage8));
                objMvPoleCct[1].setInsPhase3(new BigDecimal(phase8));
                objMvPoleCct[1].setCctInsulatorTypeI3(new BigDecimal(insulatortype8));
                objMvPoleCct[1].setCctInsulatorTypeIi3(new BigDecimal(insulatortypetype8));
                objMvPoleCct[1].setCctInsulatorVoltage4(new BigDecimal(voltage9));
                objMvPoleCct[1].setInsPhase4(new BigDecimal(phase9));
                objMvPoleCct[1].setCctInsulatorTypeI4(new BigDecimal(insulatortype9));
                objMvPoleCct[1].setCctInsulatorTypeIi4(new BigDecimal(insulatortypetype9));

                // set values for circuit object 3
                objMvPoleCct[2].setCctSource(String.valueOf(source2));
                objMvPoleCct[2].setCctVerticlePosition(new BigDecimal(verticleposition2));
                objMvPoleCct[2].setCctFormation(new BigDecimal(circuitformation2));
                objMvPoleCct[2].setCctConductorI(new BigDecimal(conductor2));
                if(String.valueOf(conductor2).equals("1")){
                    System.out.println("/////////////"+"Copper");
                    objMvPoleCct[2].setCctConductorIi(new BigDecimal(conductortype2));
                    objMvPoleCct[2].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor2).equals("2")){
                    System.out.println("/////////////"+"AAAC");
                    objMvPoleCct[2].setCctConductorIi(new BigDecimal(conductortype21));
                    objMvPoleCct[2].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor2).equals("3")){
                    System.out.println("/////////////"+"ACSR");
                    objMvPoleCct[2].setCctConductorIi(new BigDecimal(conductortype22));
                    objMvPoleCct[2].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor2).equals("4")){
                    System.out.println("/////////////"+"MV ABC");
                    objMvPoleCct[2].setCctConductorIi(new BigDecimal(conductortype23));
                    objMvPoleCct[2].setCctConductorIii(new BigDecimal(conductortype24));
                }else{
                    System.out.println("/////////////"+"Other");

                }
                objMvPoleCct[2].setCctCrossArmType(new BigDecimal(crossarmtype2));
                objMvPoleCct[2].setCctCrossArmVoltage(new BigDecimal(catype2));
                objMvPoleCct[2].setCctInsulatorVoltage(new BigDecimal(voltage2));
                objMvPoleCct[2].setInsPhase(new BigDecimal(phase2));
                objMvPoleCct[2].setCctInsulatorTypeI(new BigDecimal(insulatortype2));
                objMvPoleCct[2].setCctInsulatorTypeIi(new BigDecimal(insulatortypetype2));
                objMvPoleCct[2].setCctCrossArmType2(new BigDecimal(crossarmtype33));
                objMvPoleCct[2].setCctCrossArmVoltage2(new BigDecimal(catype33));
                objMvPoleCct[2].setCctInsulatorVoltage2(new BigDecimal(voltage10));
                objMvPoleCct[2].setInsPhase2(new BigDecimal(phase10));
                objMvPoleCct[2].setCctInsulatorTypeI2(new BigDecimal(insulatortype10));
                objMvPoleCct[2].setCctInsulatorTypeIi2(new BigDecimal(insulatortypetype10));
                objMvPoleCct[2].setCctInsulatorVoltage3(new BigDecimal(voltage11));
                objMvPoleCct[2].setInsPhase3(new BigDecimal(phase11));
                objMvPoleCct[2].setCctInsulatorTypeI3(new BigDecimal(insulatortype11));
                objMvPoleCct[2].setCctInsulatorTypeIi3(new BigDecimal(insulatortypetype11));
                objMvPoleCct[2].setCctInsulatorVoltage4(new BigDecimal(voltage12));
                objMvPoleCct[2].setInsPhase4(new BigDecimal(phase12));
                objMvPoleCct[2].setCctInsulatorTypeI4(new BigDecimal(insulatortype12));
                objMvPoleCct[2].setCctInsulatorTypeIi4(new BigDecimal(insulatortypetype12));

                objMvPoleCct[0].setGantryId(new BigDecimal(gantry));
                objMvPoleCct[0].setFeederId(feeder);
                objMvPoleCct[1].setGantryId(new BigDecimal(gantry1));
                objMvPoleCct[1].setFeederId(feeder1);
                objMvPoleCct[2].setGantryId(new BigDecimal(gantry2));
                objMvPoleCct[2].setFeederId(feeder2);
                list.add(objMvPoleCct[0]);
                list.add(objMvPoleCct[1]);
                list.add(objMvPoleCct[2]);
            }else if(Noofcircuits.equals("4")){

                // set values for circuit object 1
                objMvPoleCct[0].setCctSource(String.valueOf(source));
                objMvPoleCct[0].setCctVerticlePosition(new BigDecimal(verticleposition));
                objMvPoleCct[0].setCctFormation(new BigDecimal(circuitformation));
                objMvPoleCct[0].setCctConductorI(new BigDecimal(conductor));
                if(String.valueOf(conductor).equals("1")){
                    System.out.println("/////////////"+"Copper");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor).equals("2")){
                    System.out.println("/////////////"+"AAAC");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype01));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor).equals("3")){
                    System.out.println("/////////////"+"ACSR");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype02));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor).equals("4")){
                    System.out.println("/////////////"+"MV ABC");
                    objMvPoleCct[0].setCctConductorIi(new BigDecimal(conductortype03));
                    objMvPoleCct[0].setCctConductorIii(new BigDecimal(conductortype04));
                }else{
                    System.out.println("/////////////"+"Other");

                }
                objMvPoleCct[0].setCctCrossArmType(new BigDecimal(crossarmtype));
                objMvPoleCct[0].setCctCrossArmVoltage(new BigDecimal(catype));
                objMvPoleCct[0].setCctInsulatorVoltage(new BigDecimal(voltage));
                objMvPoleCct[0].setInsPhase(new BigDecimal(phase));
                objMvPoleCct[0].setCctInsulatorTypeI(new BigDecimal(insulatortype));
                objMvPoleCct[0].setCctInsulatorTypeIi(new BigDecimal(insulatortypetype));
                objMvPoleCct[0].setCctCrossArmType2(new BigDecimal(crossarmtype11));
                objMvPoleCct[0].setCctCrossArmVoltage2(new BigDecimal(catype11));
                objMvPoleCct[0].setCctInsulatorVoltage2(new BigDecimal(voltage4));
                objMvPoleCct[0].setInsPhase2(new BigDecimal(phase4));
                objMvPoleCct[0].setCctInsulatorTypeI2(new BigDecimal(insulatortype4));
                objMvPoleCct[0].setCctInsulatorTypeIi2(new BigDecimal(insulatortypetype4));
                objMvPoleCct[0].setCctInsulatorVoltage3(new BigDecimal(voltage5));
                objMvPoleCct[0].setInsPhase3(new BigDecimal(phase5));
                objMvPoleCct[0].setCctInsulatorTypeI3(new BigDecimal(insulatortype5));
                objMvPoleCct[0].setCctInsulatorTypeIi3(new BigDecimal(insulatortypetype5));
                objMvPoleCct[0].setCctInsulatorVoltage4(new BigDecimal(voltage6));
                objMvPoleCct[0].setInsPhase4(new BigDecimal(phase6));
                objMvPoleCct[0].setCctInsulatorTypeI4(new BigDecimal(insulatortype6));
                objMvPoleCct[0].setCctInsulatorTypeIi4(new BigDecimal(insulatortypetype6));

                // set values for circuit object 2
                objMvPoleCct[1].setCctSource(String.valueOf(source1));
                objMvPoleCct[1].setCctVerticlePosition(new BigDecimal(verticleposition1));
                objMvPoleCct[1].setCctFormation(new BigDecimal(circuitformation1));
                objMvPoleCct[1].setCctConductorI(new BigDecimal(conductor));
                if(String.valueOf(conductor1).equals("1")){
                    System.out.println("/////////////"+"Copper");
                    objMvPoleCct[1].setCctConductorIi(new BigDecimal(conductortype1));
                    objMvPoleCct[1].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor1).equals("2")){
                    System.out.println("/////////////"+"AAAC");
                    objMvPoleCct[1].setCctConductorIi(new BigDecimal(conductortype11));
                    objMvPoleCct[1].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor1).equals("3")){
                    System.out.println("/////////////"+"ACSR");
                    objMvPoleCct[1].setCctConductorIi(new BigDecimal(conductortype12));
                    objMvPoleCct[1].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor1).equals("4")){
                    System.out.println("/////////////"+"MV ABC");
                    objMvPoleCct[1].setCctConductorIi(new BigDecimal(conductortype13));
                    objMvPoleCct[1].setCctConductorIii(new BigDecimal(conductortype14));
                }else{
                    System.out.println("/////////////"+"Other");

                }
                objMvPoleCct[1].setCctCrossArmType(new BigDecimal(crossarmtype1));
                objMvPoleCct[1].setCctCrossArmVoltage(new BigDecimal(catype1));
                objMvPoleCct[1].setCctInsulatorVoltage(new BigDecimal(voltage1));
                objMvPoleCct[1].setInsPhase(new BigDecimal(phase1));
                objMvPoleCct[1].setCctInsulatorTypeI(new BigDecimal(insulatortype1));
                objMvPoleCct[1].setCctInsulatorTypeIi(new BigDecimal(insulatortypetype1));
                objMvPoleCct[1].setCctCrossArmType2(new BigDecimal(crossarmtype22));
                objMvPoleCct[1].setCctCrossArmVoltage2(new BigDecimal(catype22));
                objMvPoleCct[1].setCctInsulatorVoltage2(new BigDecimal(voltage7));
                objMvPoleCct[1].setInsPhase2(new BigDecimal(phase7));
                objMvPoleCct[1].setCctInsulatorTypeI2(new BigDecimal(insulatortype7));
                objMvPoleCct[1].setCctInsulatorTypeIi2(new BigDecimal(insulatortypetype7));
                objMvPoleCct[1].setCctInsulatorVoltage3(new BigDecimal(voltage8));
                objMvPoleCct[1].setInsPhase3(new BigDecimal(phase8));
                objMvPoleCct[1].setCctInsulatorTypeI3(new BigDecimal(insulatortype8));
                objMvPoleCct[1].setCctInsulatorTypeIi3(new BigDecimal(insulatortypetype8));
                objMvPoleCct[1].setCctInsulatorVoltage4(new BigDecimal(voltage9));
                objMvPoleCct[1].setInsPhase4(new BigDecimal(phase9));
                objMvPoleCct[1].setCctInsulatorTypeI4(new BigDecimal(insulatortype9));
                objMvPoleCct[1].setCctInsulatorTypeIi4(new BigDecimal(insulatortypetype9));

                // set values for circuit object 3
                objMvPoleCct[2].setCctSource(String.valueOf(source2));
                objMvPoleCct[2].setCctVerticlePosition(new BigDecimal(verticleposition2));
                objMvPoleCct[2].setCctFormation(new BigDecimal(circuitformation2));
                objMvPoleCct[2].setCctConductorI(new BigDecimal(conductor2));
                if(String.valueOf(conductor2).equals("1")){
                    System.out.println("/////////////"+"Copper");
                    objMvPoleCct[2].setCctConductorIi(new BigDecimal(conductortype2));
                    objMvPoleCct[2].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor2).equals("2")){
                    System.out.println("/////////////"+"AAAC");
                    objMvPoleCct[2].setCctConductorIi(new BigDecimal(conductortype21));
                    objMvPoleCct[2].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor2).equals("3")){
                    System.out.println("/////////////"+"ACSR");
                    objMvPoleCct[2].setCctConductorIi(new BigDecimal(conductortype22));
                    objMvPoleCct[2].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor2).equals("4")){
                    System.out.println("/////////////"+"MV ABC");
                    objMvPoleCct[2].setCctConductorIi(new BigDecimal(conductortype23));
                    objMvPoleCct[2].setCctConductorIii(new BigDecimal(conductortype24));
                }else{
                    System.out.println("/////////////"+"Other");

                }
                objMvPoleCct[2].setCctCrossArmType(new BigDecimal(crossarmtype2));
                objMvPoleCct[2].setCctCrossArmVoltage(new BigDecimal(catype2));
                objMvPoleCct[2].setCctInsulatorVoltage(new BigDecimal(voltage2));
                objMvPoleCct[2].setInsPhase(new BigDecimal(phase2));
                objMvPoleCct[2].setCctInsulatorTypeI(new BigDecimal(insulatortype2));
                objMvPoleCct[2].setCctInsulatorTypeIi(new BigDecimal(insulatortypetype2));
                objMvPoleCct[2].setCctCrossArmType2(new BigDecimal(crossarmtype33));
                objMvPoleCct[2].setCctCrossArmVoltage2(new BigDecimal(catype33));
                objMvPoleCct[2].setCctInsulatorVoltage2(new BigDecimal(voltage10));
                objMvPoleCct[2].setInsPhase2(new BigDecimal(phase10));
                objMvPoleCct[2].setCctInsulatorTypeI2(new BigDecimal(insulatortype10));
                objMvPoleCct[2].setCctInsulatorTypeIi2(new BigDecimal(insulatortypetype10));
                objMvPoleCct[2].setCctInsulatorVoltage3(new BigDecimal(voltage11));
                objMvPoleCct[2].setInsPhase3(new BigDecimal(phase11));
                objMvPoleCct[2].setCctInsulatorTypeI3(new BigDecimal(insulatortype11));
                objMvPoleCct[2].setCctInsulatorTypeIi3(new BigDecimal(insulatortypetype11));
                objMvPoleCct[2].setCctInsulatorVoltage4(new BigDecimal(voltage12));
                objMvPoleCct[2].setInsPhase4(new BigDecimal(phase12));
                objMvPoleCct[2].setCctInsulatorTypeI4(new BigDecimal(insulatortype12));
                objMvPoleCct[2].setCctInsulatorTypeIi4(new BigDecimal(insulatortypetype12));


                // set values for circuit object 4
                objMvPoleCct[3].setCctSource(String.valueOf(source3));
                objMvPoleCct[3].setCctVerticlePosition(new BigDecimal(verticleposition3));
                objMvPoleCct[3].setCctFormation(new BigDecimal(circuitformation3));
                objMvPoleCct[3].setCctConductorI(new BigDecimal(conductor3));
                if(String.valueOf(conductor3).equals("1")){
                    System.out.println("/////////////"+"Copper");
                    objMvPoleCct[3].setCctConductorIi(new BigDecimal(conductortype3));
                    objMvPoleCct[3].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor3).equals("2")){
                    System.out.println("/////////////"+"AAAC");
                    objMvPoleCct[3].setCctConductorIi(new BigDecimal(conductortype31));
                    objMvPoleCct[3].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor3).equals("3")){
                    System.out.println("/////////////"+"ACSR");
                    objMvPoleCct[3].setCctConductorIi(new BigDecimal(conductortype32));
                    objMvPoleCct[3].setCctConductorIii(new BigDecimal(0));
                }else if(String.valueOf(conductor3).equals("4")){
                    System.out.println("/////////////"+"MV ABC");
                    objMvPoleCct[3].setCctConductorIi(new BigDecimal(conductortype33));
                    objMvPoleCct[3].setCctConductorIii(new BigDecimal(conductortype34));
                }else{
                    System.out.println("/////////////"+"Other");

                }
                objMvPoleCct[3].setCctCrossArmType(new BigDecimal(crossarmtype3));
                objMvPoleCct[3].setCctCrossArmVoltage(new BigDecimal(catype3));
                objMvPoleCct[3].setCctInsulatorVoltage(new BigDecimal(voltage3));
                objMvPoleCct[3].setInsPhase(new BigDecimal(phase3));
                objMvPoleCct[3].setCctInsulatorTypeI(new BigDecimal(insulatortype3));
                objMvPoleCct[3].setCctInsulatorTypeIi(new BigDecimal(insulatortypetype3));
                objMvPoleCct[3].setCctCrossArmType2(new BigDecimal(crossarmtype44));
                objMvPoleCct[3].setCctCrossArmVoltage2(new BigDecimal(catype44));
                objMvPoleCct[3].setCctInsulatorVoltage2(new BigDecimal(voltage13));
                objMvPoleCct[3].setInsPhase2(new BigDecimal(phase13));
                objMvPoleCct[3].setCctInsulatorTypeI2(new BigDecimal(insulatortype13));
                objMvPoleCct[3].setCctInsulatorTypeIi2(new BigDecimal(insulatortypetype13));
                objMvPoleCct[3].setCctInsulatorVoltage3(new BigDecimal(voltage14));
                objMvPoleCct[3].setInsPhase3(new BigDecimal(phase14));
                objMvPoleCct[3].setCctInsulatorTypeI3(new BigDecimal(insulatortype14));
                objMvPoleCct[3].setCctInsulatorTypeIi3(new BigDecimal(insulatortypetype14));
                objMvPoleCct[3].setCctInsulatorVoltage4(new BigDecimal(voltage15));
                objMvPoleCct[3].setInsPhase4(new BigDecimal(phase15));
                objMvPoleCct[3].setCctInsulatorTypeI4(new BigDecimal(insulatortype15));
                objMvPoleCct[3].setCctInsulatorTypeIi4(new BigDecimal(insulatortypetype15));

                objMvPoleCct[0].setGantryId(new BigDecimal(gantry));
                objMvPoleCct[0].setFeederId(feeder);
                objMvPoleCct[1].setGantryId(new BigDecimal(gantry1));
                objMvPoleCct[1].setFeederId(feeder1);
                objMvPoleCct[2].setGantryId(new BigDecimal(gantry2));
                objMvPoleCct[2].setFeederId(feeder2);
                objMvPoleCct[3].setGantryId(new BigDecimal(gantry3));
                objMvPoleCct[3].setFeederId(feeder3);
                list.add(objMvPoleCct[0]);
                list.add(objMvPoleCct[1]);
                list.add(objMvPoleCct[2]);
                list.add(objMvPoleCct[3]);
            }else{

            }

            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+list.get(0).getFeederId().toString()+"list size"+list.size());
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"+String.valueOf(source));
            objpolemodel.setMvPole(objMvPole);
            objpolemodel.setMvPoleCctList(list);

            System.out.println("//////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
            System.out.println("Area"+objMvPole.getArea());
            System.out.println("CSC"+objMvPole.getCsc());
            System.out.println("PoleNo"+objMvPole.getPoleNo());
            System.out.println("Heigth"+objMvPole.getPoleHeight());
            System.out.println("Type"+objMvPole.getPoleType());
            System.out.println("Working"+objMvPole.getWorkingLoad());

            final RestTemplate restTemplate = new RestTemplate();


            String url1 = Util.SRT_URL + "MMSModifyPoleMVMobileNew";
            System.out.println(" url1 " + url1);

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            String objReturn = restTemplate.postForObject(url1, objpolemodel, String.class);
            System.out.println(" objReturn: " + objReturn );
            return objReturn;
        }


    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    // Spinner Province
    public List<String> readExcelProvince() {

        List<String> resultSet = new ArrayList<String>();
        File inputWorkbook = new File(Environment.getExternalStorageDirectory() + File.separator + "Schedule" + File.separator + "Province.xls");

        if (inputWorkbook.exists()) {

            Workbook w;
            try {

                w = Workbook.getWorkbook(inputWorkbook);

                Sheet sheet = w.getSheet(0);

                // Loop over column and lines
                int coloumn = sheet.getRows();
                values1 = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);

                    spinnerMap1.put(j, cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    values1[j] = cell1.getContents();

                    continue;
                }

                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values1);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerProvince = findViewById(R.id.province);
                SpinnerProvince.setAdapter(adapterNs);

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
        return resultSet;
    }

    // Spinner Area
    public List<String> readExcelArea() {

        List<String> resultSet = new ArrayList<String>();
        File inputWorkbook = new File(Environment.getExternalStorageDirectory() + File.separator + "Schedule" + File.separator + "Area.xls");

        if (inputWorkbook.exists()) {

            Workbook w;
            try {

                w = Workbook.getWorkbook(inputWorkbook);

                Sheet sheet = w.getSheet(0);

                // Loop over column and lines
                int coloumn = sheet.getRows();
                values2 = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);

                    spinnerMap2.put(j, cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    values2[j] = cell1.getContents();

                    continue;
                }

                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values2);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerArea = findViewById(R.id.area);
                SpinnerArea.setAdapter(adapterNs);

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
        return resultSet;
    }

    // Spinner Csc
    public List<String> readExcelCsc() {

        List<String> resultSet = new ArrayList<String>();
        File inputWorkbook = new File(Environment.getExternalStorageDirectory() + File.separator + "Schedule" + File.separator + "Csc.xls");

        if (inputWorkbook.exists()) {

            Workbook w;
            try {

                w = Workbook.getWorkbook(inputWorkbook);

                Sheet sheet = w.getSheet(0);

                // Loop over column and lines
                int coloumn = sheet.getRows();
                values3 = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);

                    spinnerMap3.put(j, cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    values3[j] = cell1.getContents();

                    continue;
                }

                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values3);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerCSC = findViewById(R.id.spnCSC);
                SpinnerCSC.setAdapter(adapterNs);

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
        return resultSet;
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
//        getMenuInflater().inflate(R.menu.edit_mv_pole, menu);
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
        NavigationView nv= (NavigationView) findViewById(R.id.nav_view);
        Menu m=nv.getMenu();
        int id = item.getItemId();

        if (id == R.id.apphome) {
            Intent intent = new Intent(EditMVPoles.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(EditMVPoles.this, GetNearByTower.class);
            startActivity(intent);

            //////////////////////////////// PHM - LCM ////////////////////////////////////////////

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(EditMVPoles.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(EditMVPoles.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(EditMVPoles.this, TM2.class);
            startActivity(intent);

            //////////////////////////////// PHM - SUb ////////////////////////////////////////////

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(EditMVPoles.this, AddGantry.class);
            startActivity(intent);
        } else if (id == R.id.nav_editGantry) {
            Intent intent = new Intent(EditMVPoles.this, EditGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addBusBar) {
            Intent intent = new Intent(EditMVPoles.this, AddBusBar.class);
            startActivity(intent);
        } else if (id == R.id.nav_editBusBar) {
            Intent intent = new Intent(EditMVPoles.this, EditBusBar.class);
            startActivity(intent);

        } else if (id == R.id.nav_addStructual) {
            Intent intent = new Intent(EditMVPoles.this, AddStructural.class);
            startActivity(intent);
        } else if (id == R.id.nav_editStructual) {
            Intent intent = new Intent(EditMVPoles.this, EditStructural.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantryMore) {
            Intent intent = new Intent(EditMVPoles.this, AddGantryMore.class);
            startActivity(intent);
        } else if (id == R.id.nav_editGantryMore) {
            Intent intent = new Intent(EditMVPoles.this, EditGantryMore.class);
            startActivity(intent);

        } else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(EditMVPoles.this, AddFeeder.class);
            startActivity(intent);
        } else if (id == R.id.nav_editFeeder) {
            Intent intent = new Intent(EditMVPoles.this, EditFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSwitches) {
            Intent intent = new Intent(EditMVPoles.this, AddSwitches.class);
            startActivity(intent);
        } else if (id == R.id.nav_editSwitches) {
            Intent intent = new Intent(EditMVPoles.this, EditSwitches.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSurge) {
            Intent intent = new Intent(EditMVPoles.this, AddSurgeArrestors.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTransformersG) {
            Intent intent = new Intent(EditMVPoles.this, AddTransformersG.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(EditMVPoles.this, AddEquipment.class);
            startActivity(intent);

///////////////////// POLE DETAILS //////////////////////////////////////////////

        }else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(EditMVPoles.this, AddMVPoles.class);
            startActivity(intent);

        }else if (id == R.id.nav_editMVPoles) {
            Intent intent = new Intent(EditMVPoles.this, EditMVPoles.class);
            startActivity(intent);

//        }else if (id == R.id.nav_selectMvPoleMantenance) {
//            Intent intent = new Intent(EditMVPoles.this, SelectMvPoleMaintenance.class);
//            startActivity(intent);

        } else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(EditMVPoles.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(EditMVPoles.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(EditMVPoles.this, AddTransformers.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(EditMVPoles.this, Login.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}