package com.example.akla.login;

/**
 * Created by Akla on 1/11/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class DBHelper extends SQLiteOpenHelper {

    // public static final String DATABASE_NAME = "MyDBName.db";
    //
    public static final String DATABASE_NAME = "TM40database.db";
    public static final String TM_TABLE_NAME = "tower_maitainance";
    public static final String ADDLINE_TABLE_NAME = "add_line";
    public static final String ADDSUPPORT_TABLE_NAME = "add_support";
    public static final String AREA_TABLE_NAME = "area";
    public static final String TOWER = "tower";

    //variables for add support
    public static final String SUPPORT_TOWERNO = "S_towerno";
    public static final String SUPPORT_AREA = "support_area";
    public static final String SUPPORT_CSC = "csc";
    public static final String SUPPORT_CONDUCTOR_TYPE = "conductor_type";
    public static final String SUPPORT_EARTH_CONDUCTOR_TYPE = "earth_conductor_type";
    public static final String SUPPORT_TYPE = "support_type";
    public static final String SUPPPORT_TOWER_TYPE = "tower_type";
    public static final String SUPPORT_NOOFCIRCUITS = "no_of_circuits";
    public static final String SUPPORT_TOWER_CONFIGURATION = "tower_configuration";
    public static final String SUPPORT_BODY_EXTENSION = "body_extension";
    public static final String SUPPORT_GPS_LONGITITUDE = "gps_longititude";
    public static final String SUPPORT_GPS_LATITUDE = "gps_latitude";
    public static final String SUPPORT_LINE_NAME = "line_name";
    public static final String SUPPORT_PHM_BRANCH = "phm_branch";
    public static final String SUPPORT_STATUS = "status";
    public static final String SUPPORT_ENT_BY = "ent_by";

    //variable for area
    public static final String AREA_CODE = "areaCode";
    public static final String AREA_NAME = "areaName";

    //variables for add line
    public static final String ADDLINE_ID = "lineid";
    public static final String ADDLINE_CODE = "code";
    public static final String ADDLINE_NAME = "name";
    public static final String ADDLINE_TYPE = "type";
    public static final String ADDLINE_LENGTH = "length";
    public static final String ADDLINE_AREA = "area";
    public static final String ADDLINE_NOOFPOLES = "no_of_poles";
    public static final String ADDLINE_NOOFTOWERS = "no_of_towers";
    public static final String ADDLINE_COMDATE = "com_date";
    public static final String ADDLINE_CONDUCTOR_TYPE = "conductor_type";
    public static final String ADDLINE_CIRCUIT_TYPE = "circuit_type";
    public static final String ADDLINE_FEEDER_IDENTIFICATION = "feeder_identification";


    // variables for tower maintainance
    public static final String TM_AREA = "area";
    public static final String TM_LINE = "line";
    public static final String TM_VISITID = "visitid";
    public static final String TM_TOWERNO = "towerno";
    public static final String TM_NOOFTAPPINGS = "nooftappings";
    public static final String TM_NUMBEROFMISSINGPARTS = "no_of_missing_parts";
    public static final String TM_NUMBEROFFLASHOVERSETS = "no_of_falshover_sets";
    public static final String TM_MAIN_DATE = "main_date";
    public static final String TM_WAYLEAVINGSTATUS = "wayleavingstatus";
    public static final String TM_BASECONCRETESTATUS = "baseconcretestatus";
    public static final String TM_ANTICLIMBINGSTATUS = "anti_climbing_status";
    public static final String TM_CONDUCTORCONDITION = "conductor_condition";
    public static final String TM_JUMPERCONDITION = "jumper_condition";
    public static final String TM_EARTHCONDUCTORCONDITION = "earth_conductor_condition";
    public static final String TM_MAINTAINANCEATTENTION = "maintainance_attention";
    public static final String TM_FUNGUS_SET = "fungus_set";
    public static final String TM_W_PIN_SET = "w_pin_set";
    public static final String TM_END_FITTINGSET = "end_fitting_set";
    public static final String TM_SPECIALOBSERVATIONS = "special_observations";
    public static final String TM_HOTLINE_MAIN = "hot_line_main";
    public static final String TM_LEG_PAINTING = "leg_painting";
    public static final String TM_NUMBEROFPINPOLE1 ="noOfPinPole1";
    public static final String TM_TYPEOFSWICHINGDEVICE1="typeStcDev1";
    public static final String TM_NUMBEROFPINPOLE2 ="noOfPinPole2";
    public static final String TM_TYPEOFSWICHINGDEVICE2="typeStcDev2";
    public static final String TM_NUMBEROFPINPOLE3 ="noOfPinPole3";
    public static final String TM_TYPEOFSWICHINGDEVICE3="typeStcDev3";




//    public static final String TM_FLASHOVERSETNUMBER = "flash_over_set_number";





    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_VISIT_ID="visitID";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";


    private static final int DATABASE_VERSION = 1;
    private HashMap hp;
    public String table_name = "users";
    private String numerofPinpole1;
    private String numerofPinpole2;
    private String numerofPinpole3;




    //public String tm_table_name = "tower_maitainance";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(
                "create table "+TM_TABLE_NAME +
                        "(id integer primary key,visitid text,towerno text,nooftappings text,no_of_missing_parts text,no_of_falshover_sets text," +
                        "wayleavingstatus text,baseconcretestatus text,anti_climbing_status text,conductor_condition text," +
                        "jumper_condition text,earth_conductor_condition text,maintainance_attention text,fungus_set text," +
                        "w_pin_set text,end_fitting_set text,special_observations text,leg_painting text,hot_line_main text,area text,line text,noOfPinPole1 text,typeStcDev1 text,noOfPinPole2 text,typeStcDev2 text,noOfPinPole3 text,typeStcDev3 text)"
        );
        db.execSQL(
                "create table "+ADDLINE_TABLE_NAME +
                        "(id integer primary key,lineid text,code text,name text,type text,length text," +
                        "area text,no_of_poles text,no_of_towers text,com_date text,conductor_type text,circuit_type text,feeder_identification text)"
        );

        db.execSQL(
                "create table "+AREA_TABLE_NAME +
                        "(id integer primary key,areaCode text,areaName text)"
        );

        db.execSQL(
                "create table "+ADDSUPPORT_TABLE_NAME +
                        "(id integer primary key,support_type text,supportid text,line_name text,S_towerno text,support_area text,csc text,conductor_type text," +
                        "tower_type text,tower_configuration text,gps_latitude text,gps_longititude text,no_of_circuits text,body_extension text)"
        );

        db.execSQL(
                "create table "+TOWER +
                        "(id integer primary key,S_towerno text,support_area text,csc text,conductor_type text,earth_conductor_type text,support_type text,tower_type text," +
                        "no_of_circuits text,tower_configuration text,body_extension text,gps_longititude text,gps_latitude text,line_name text,phm_branch text,status text,ent_by text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+TM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ADDLINE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ADDSUPPORT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+AREA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TOWER);

        onCreate(db);
    }

    public boolean insertTowerMnt(String towerNo, String noOfTappings, String noofmissingparts, String noofflashoversets,
                                  String wayLeavingStatus, String baseConcreteStatus, String antiClimbingStatus, String conductorCondition, String jumperConductorCondition,
                                  String earthConductorCondition, String maintainanceAttention, String fungusSet, String wpinSet, String endFittingSet, String specialObservations, String leg_painting, String hot_line_main, String line, String area,String numerofPinpole1,String typeOfSwichingDevice1,String numerofPinpole2,String typeOfSwichingDevice2,String numerofPinpole3,String typeOfSwichingDevice3) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("visitid", visitID);
        contentValues.put("towerno",towerNo );
        contentValues.put("nooftappings", noOfTappings);
        System.out.println("nooftappingshhhhhhhhhhh:" +noOfTappings);
        contentValues.put("no_of_missing_parts", noofmissingparts);
        contentValues.put("no_of_falshover_sets", noofflashoversets);
        //contentValues.put("main_date", maindate);
        contentValues.put("wayleavingstatus", wayLeavingStatus);
        contentValues.put("baseconcretestatus", baseConcreteStatus);
        contentValues.put("anti_climbing_status", antiClimbingStatus);
        contentValues.put("conductor_condition", conductorCondition);
        contentValues.put("jumper_condition", jumperConductorCondition);
        contentValues.put("earth_conductor_condition", earthConductorCondition);
        contentValues.put("maintainance_attention", maintainanceAttention);
        contentValues.put("fungus_set", fungusSet);
        contentValues.put("w_pin_set", wpinSet);
        contentValues.put("end_fitting_set", endFittingSet);
        contentValues.put("special_observations", specialObservations);
        contentValues.put("leg_painting", leg_painting);
        contentValues.put("hot_line_main", hot_line_main);
        contentValues.put("area", area);
        contentValues.put("line", line);
        contentValues.put("noOfPinPole1",numerofPinpole1);
        contentValues.put("typeStcDev1",typeOfSwichingDevice1);
        contentValues.put("noOfPinPole2",numerofPinpole2);
        contentValues.put("typeStcDev2",typeOfSwichingDevice2);
        contentValues.put("noOfPinPole3",numerofPinpole3);
        contentValues.put("typeStcDev3",typeOfSwichingDevice3);



        db.insert(TM_TABLE_NAME, null, contentValues);
        return true;
    }


    public boolean insertArea (String code,String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("areaCode", code);
        contentValues.put("areaName", name);
        db.insert(AREA_TABLE_NAME, null, contentValues);
        return true;
    }





    public boolean insertAddLine(String lineid, String code, String name, String type, String length, String area, String noOfPoles,
                                 String noOfTowers, String comDate, String conductorType, String noOfCircuits, String feederIdentification) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lineid", lineid);
        contentValues.put("code", code);
        contentValues.put("name", name);
        contentValues.put("type", type);
        contentValues.put("length", length);
        contentValues.put("area", area);
        contentValues.put("no_of_poles",noOfPoles);
        contentValues.put("no_of_towers",noOfTowers);
        contentValues.put("com_date",comDate);
        contentValues.put("conductor_type",conductorType);
        contentValues.put("circuit_type",noOfCircuits);
        contentValues.put("feeder_identification",feederIdentification);

        System.out.println("Line ID 1111111111111111111:"+lineid);

        db.insert(ADDLINE_TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean insertAddSupport (String supportType,String supportID,String lineName,String towerNo, String supportArea, String csc, String conductorType,String towerType, String towerConfiguration,
                                     String gpsLatitude, String gpsLongititude,String noOfCircuits,String bodyExtensionSet) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("support_type", supportType);
        contentValues.put("supportid", supportID);
        contentValues.put("line_name", lineName);
        contentValues.put("S_towerno", towerNo);
        contentValues.put("support_area", supportArea);
        contentValues.put("csc", csc);
        contentValues.put("conductor_type", conductorType);
        contentValues.put("tower_type", towerType);
        contentValues.put("tower_configuration",towerConfiguration);
        contentValues.put("gps_latitude",gpsLatitude);
        contentValues.put("gps_longititude",gpsLongititude);
        contentValues.put("no_of_circuits",noOfCircuits);
        contentValues.put("body_extension",bodyExtensionSet);



        System.out.println("Support ID 1111111111111111111:"+supportID);
        System.out.println("Support tower config 1111111111111111111:"+towerConfiguration);


        db.insert(ADDSUPPORT_TABLE_NAME, null, contentValues);
        return true;
    }





    public boolean insertAddSupportA(String S_towerno,String support_area,String csc,String conductor_type, String earth_conductor_type, String support_type, String tower_type,String no_of_circuits, String tower_configuration,
                                     String body_extension, String gps_longititude,String gps_latitude,String line_name, String phm_branch, String status, String ent_by) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("S_towerno", S_towerno);
        contentValues.put("support_area", support_area);
        contentValues.put("csc", csc);
        contentValues.put("conductor_type", conductor_type);
        contentValues.put("earth_conductor_type", earth_conductor_type);
        contentValues.put("support_type", support_type);
        contentValues.put("tower_type", tower_type);
        contentValues.put("no_of_circuits", no_of_circuits);
        contentValues.put("tower_configuration",tower_configuration);
        contentValues.put("body_extension",body_extension);
        contentValues.put("gps_longititude",gps_longititude);
        contentValues.put("gps_latitude",gps_latitude);
        contentValues.put("line_name",line_name);
        contentValues.put("phm_branch",phm_branch);
        contentValues.put("status",status);
        contentValues.put("ent_by",ent_by);



        db.insert(TOWER, null, contentValues);
        return true;
    }

    public boolean insertContact (String name,String visitID, String phone, String email, String street,String place) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("visitID", visitID);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.insert("contacts", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TM_TABLE_NAME+" where id="+id+"", null );
        return res;
    }

    public Cursor getDataTM(String line,String area) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TM_TABLE_NAME+" where line='"+line+"' and area ='"+area+"'", null );
        return res;
    }

    public Cursor getDataTowerNo(String line,String area) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select towerno from "+TM_TABLE_NAME+" where line='"+line+"' and area ='"+area+"'", null );
        return res;
    }


    public Cursor getAddLineData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res2 =  db.rawQuery( "select * from "+ADDLINE_TABLE_NAME+"" , null  );
        return res2;
    }

    public Cursor getAreaData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res2 =  db.rawQuery( "select * from "+AREA_TABLE_NAME+"" , null  );
        return res2;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tower_maitainance", null );
        return res;
    }
    public Cursor getAddSupportData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res3 =  db.rawQuery( "select * from "+ADDSUPPORT_TABLE_NAME+"", null );
        return res3;
    }

    public Cursor getAddSupportDataA() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res3 = db.rawQuery("select * from " + TOWER + "", null);
        return res3;
    }

    public Cursor getSupportByLineId(String lineID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res3 = db.rawQuery("select * from " + TOWER+" where line_name='"+lineID+"'", null);
        return res3;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateTowerMnt (Integer id,String visitID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("visitID", visitID);
        db.update("tower_maitainance", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public boolean updateContact (Integer id,String visitID, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("visitID", visitID);

        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("TM_TABLE_NAME",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    public void deleteTM() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TM_TABLE_NAME);

    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public boolean insertGantry(String provine,
                                String area,
                                String csc,
                                String line,
                                String name,
                                String code,
                                String short_circuit_current_capacity,
                                String earth_fault_current_capacity,
                                String date_of_commissing,
                                String gantry_type,
                                String gantry_elec_type,
                                String no_of_bus_bars,
                                String no_of_sections,
                                String no_of_in_bays,
                                String no_of_out_bays,
                                String total_land_area,
                                String total_no_of_ar,
                                String total_no_of_lbs,
                                String total_no_of_abs,
                                String total_no_of_sa,
                                String total_no_of_ddlo_links,
                                String total_no_of_ddlo_fuses,
                                String total_no_of_in_feeders,
                                String total_no_of_out_feeders,
                                String gps_longititude,
                                String gps_latitude) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Provine", provine);
        contentValues.put("Area", area);
        contentValues.put("Csc", csc);
        contentValues.put("Line", line);
        contentValues.put("Name", name);
        contentValues.put("Code", code);
        contentValues.put("Short_Circuit_Current_Capcity",short_circuit_current_capacity);
        contentValues.put("Earth_Fault_Current_Capacity",earth_fault_current_capacity);
        contentValues.put("Date_Of_Commissing",date_of_commissing);
        contentValues.put("Gantry_Type",gantry_type);
        contentValues.put("Gantry_Electrical_Type",gantry_elec_type);
        contentValues.put("No_Of_Bus_Bars",no_of_bus_bars);
        contentValues.put("No_Of_Bus_Sections",no_of_sections);
        contentValues.put("No_Of_In_Bays",no_of_in_bays);
        contentValues.put("No_Of_Out_Bays",no_of_out_bays);
        contentValues.put("Total_Land_Area",total_land_area);
        contentValues.put("Total_No_Of_Ar",total_no_of_ar);
        contentValues.put("Total_No_Of_Lbs",total_no_of_lbs);
        contentValues.put("Total_No_Of_Abs",total_no_of_abs);
        contentValues.put("Total_No_Of_Sa",total_no_of_sa);
        contentValues.put("Total_No_Of_Ddlo_Links",total_no_of_ddlo_links);
        contentValues.put("Total_No_Of_Ddlo_Fuses",total_no_of_ddlo_fuses);
        contentValues.put("Total_No_Of_In_Feeders",total_no_of_in_feeders);
        contentValues.put("Total_No_Of_Out_Feeders",total_no_of_out_feeders);
        contentValues.put("gps_longititude",gps_longititude);
        contentValues.put("gps_latitude",gps_latitude);

        db.insert(TOWER, null, contentValues);
        return true;
    }
}