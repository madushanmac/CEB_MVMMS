package com.example.akla.login;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Admin on 8/23/2017.
 */
public class Util {
    public static String URL ="http://localhost/MMS/SaveTowermaint/";
    //public static final String SRT_URL = "http://119.235.6.180:9090/MMS/";

    //public static final String SRT_URL = "http://testservice.ceb.lk:9080/MMS/";
    //public static final String SRT_URL = "http://119.235.8.151:9080/MMS/";
    //public static final String SRT_URL = "http://mvmms.ceb.lk:9080/MMS/";
    public static final String SRT_URL = "https://mms.ceb.lk/MMS/";
    //public static final String SRT_URL ="http://10.0.2.2:9090/MMS/";
    //public static final String SRT_URL ="http://localhost:9090/MMS/";

    //public static final String SRT_URL ="http://10.130.3.110:9090/MMS/";

    //public static final String SRT_URL ="http://10.0.2.2:9090/MMS/";
    //public static final String SRT_URL ="http://127.0.0.1:9990";

    public static String Access_Token ="0MSM0bile";
    public static String alert_header = "CEB Maintenance Management System";
    public static String addLineDB_URL;
  /*edited one*/
   /* public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = Util.getConnectivityStatus(context);
        String status = null;
        if (conn == Util.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == Util.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == Util.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }*/
    /*edited one*/
    public static boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

}




