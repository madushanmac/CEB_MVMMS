package com.example.akla.login;

/**
 * Created by Admin on 1/12/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // User name (make variable public to access from outside)
    public static final String KEY_ACCNO = "accNo";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public static final String KEY_AREA = "area";
    public static final String KEY_LINE = "line";
    public static final String KEY_CYCLE = "cycle";
    public static final String KEY_PhmBranch="PhmBranch";
    public static final String KEY_UserName="UserName";

    public static final String KEY_MAP_PROVINCE = "mapprovince";
    public static final String KEY_MAP_AREA = "maparea";
    public static final String KEY_MAP_LINE = "mapline";

    public static final String KEY_FEEDER = "feeder";

    /// For location getter /// ROWDY
    public static final String KEY_LOCBUTTON = "locbutton";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    public void createAccList(String accNo){

        editor.putString(KEY_ACCNO, accNo);
       // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public String getAccList(){
        return pref.getString(KEY_ACCNO,null);

    }


    public void createPhmBranch(String PhmBranch){

        editor.putString(KEY_PhmBranch, PhmBranch);
        // commit changes
        editor.commit();
    }

    public void createUserName(String UserName){

        editor.putString(KEY_UserName, UserName);
        // commit changes
        editor.commit();
    }

    public String getUserName(){
        return pref.getString(KEY_UserName,null);

    }


    /**
     * Get stored session data
     * */
    public String getPhmBranch(){
        return pref.getString(KEY_PhmBranch,null);

    }



    public void createKeyCycle(String cycle){

        editor.putString(KEY_CYCLE, cycle);
        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public String getKeyCycle(){
        return pref.getString(KEY_CYCLE,null);

    }


    public void createKeyArea(String area){

        editor.putString(KEY_AREA, area);
        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public String getKeyArea(){
        return pref.getString(KEY_AREA,null);

    }


    public void createKeyLine(String area){

        editor.putString(KEY_LINE, area);
        // commit changes
        editor.commit();
    }
    public String getKeyLine(){
        return pref.getString(KEY_LINE,null);

    }

    public void createKeyMapProvince(String mapprovince){

        editor.putString(KEY_MAP_PROVINCE, mapprovince);
        editor.commit();
    }
    public  String getKeyMapProvince(){
        return pref.getString(KEY_MAP_PROVINCE,null);
    }

    public void createKeyMapArea(String maparea){

        editor.putString(KEY_MAP_AREA, maparea);
        editor.commit();
    }
    public  String getKeyMapArea(){
        return pref.getString(KEY_MAP_AREA,null);
    }

    public void createKeyMapLine(String mapline){

        editor.putString(KEY_MAP_LINE, mapline);
        editor.commit();
    }
    public  String getKeyMapLine(){
        return pref.getString(KEY_MAP_LINE,null);
    }
    /**
     * Get stored session data
     * */



    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context,MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void createKeyFeeder(String feeder){

        editor.putString(KEY_FEEDER, feeder);
        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public String getKeyFeeder(){
        return pref.getString(KEY_FEEDER,null);

    }


    /// FOR Location getter/////////////
    public void createKeyLocButton(String locbutton){

        editor.putString(KEY_LOCBUTTON, locbutton);
        editor.commit();
    }
    public  String getKeyLocbutton(){
        return pref.getString(KEY_LOCBUTTON,null);
    }


    public void createKeyLatitude(String latitude){

        editor.putString(KEY_LATITUDE, latitude);
        editor.commit();
    }
    public  String getKeyLatitude(){
        return pref.getString(KEY_LATITUDE,null);
    }


    public void createKeyLongitude(String longitute){

        editor.putString(KEY_LONGITUDE, longitute);
        editor.commit();
    }
    public  String getKeyLongitude(){
        return pref.getString(KEY_LONGITUDE,null);
    }


}
