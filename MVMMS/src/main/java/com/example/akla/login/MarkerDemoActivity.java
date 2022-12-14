package com.example.akla.login;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MarkerDemoActivity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private Marker mPerth;
    private Marker mSydney;
    private Marker mBrisbane;
    private Marker mLoc;

    private GoogleMap mMap;
    LocationManager locationManager;


    private FusedLocationProviderClient client;

    //ArrayList[] Locations;
    MmsAddsupport[] Obj;

    public String MapProvince;
    public String MapArea;
    public String MapLine;

    //load Line
    Spinner SpinnerLine;
    String valuesLine[] = new String[]{};
    HashMap<Integer,String> SpinnerMapLine = new HashMap<Integer, String>();


    double liveLat;
    double liveLong;

    BigDecimal[] Latitude;
    BigDecimal[] Longitude;
    String[] TowerNumber,LineLength,LineName,LineType,NoOfTowers,NoOfPoles,Area;
    Double[] lat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new MarkerDemoActivity.getLocationDetails().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //new MarkerDemoActivity.getLineDetails().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        SessionManager obj = new SessionManager(getBaseContext());
        MapProvince = obj.getKeyMapProvince();
        MapArea = obj.getKeyMapArea();
        MapLine = obj.getKeyMapLine();
        System.out.println("YYYYYYYYYYYYYYYYYYYYSSSSSSSSSSSSSSSSSSSSYYYYYYYYYYYYYYYYYYYSSSSSSSS");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: "+MapProvince);
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB: "+MapArea);
        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC: "+MapLine);


        ///////////////////////////////////////Get live location details
        requestPermission();
       // client = LocationServices.getFusedLocationProviderClient(this);

        if(ActivityCompat.checkSelfPermission(MarkerDemoActivity.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        /*client.getLastLocation().addOnSuccessListener(MarkerDemoActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    System.out.println("LIVEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                    System.out.println(location.getLatitude());
                    System.out.println(location.getLongitude());

                    liveLat = location.getLatitude();
                    liveLong = location.getLongitude();
                }
            }
        });*/

        getCurrentLocation();

    }


    /**
     * Called when the map is ready.
     */
    //@Override
    public void onMapReady(GoogleMap map) {

        mMap = map;
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXYYYYYYYYYYYYYYYYYYYYYYYYYXXXXXXXXXX");

       BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.bluenewtower_foreground);
        if(Latitude != null){
            for (int i = 0; i <= Latitude.length-1; i++) {
                mLoc = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Latitude[i].doubleValue() ,Longitude[i].doubleValue()))
                        .icon(icon)
                        .title(
                                "Tower Number: "+ TowerNumber[i]+
                                "\nLatitude: "+Latitude[i]+
                                "\nLongitude: "+Longitude[i]+
                                "\nArea: "+Area[i]

                            ));
                mLoc.setTag(0);


            }
        }else{
            System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPP: ");
        }

        //View Live Location
      // mLoc = mMap.addMarker(new MarkerOptions()
        //       .position(new LatLng(liveLat ,liveLong))
          //     .title("Live Location\n Live Location"));
        //mLoc.setTag(0);

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
            builder.setIcon(R.mipmap.bigbluenewtower_foreground);
            builder.setTitle("Tower Details");
            builder.setCancelable(false);
            builder.setMessage(marker.getTitle());

            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }

        return false;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    //////////////////// GET LATITUDE & LONGITUDE //////////////////////////////
    private class getLocationDetails extends AsyncTask<String, Void, MmsAddsupport[]>{

        @Override
        protected MmsAddsupport[] doInBackground(String... strings) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "MapNewMobile/"+MapArea+"/"+MapLine+"/"+MapProvince;

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("KKKKKKKKKKKKKKKKKKKKKKK"+rest.getForObject(url5, MmsAddsupport[].class));
            return rest.getForObject(url5, MmsAddsupport[].class);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(MmsAddsupport[] results) {

            if (results != null) {
                
                Obj = results;

                Latitude = new BigDecimal[results.length];
                Longitude = new BigDecimal[results.length];
                LineName = new String[results.length];
                TowerNumber = new String[results.length];
                LineLength = new String[results.length];
                LineType = new String[results.length];
                NoOfTowers = new String[results.length];
                NoOfPoles = new String[results.length];
                Area = new String[results.length];

                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddsupport obj = results[c];

                    Latitude[c] = obj.getGpsLatitude();
                    Longitude[c] = obj.getGpsLongitude();
                    TowerNumber[c] = obj.getTowerNo();
                    //LineLength[c] = obj.get
                    LineName[c] = obj.getLineName();
                    //LineType[c] = obj.getty
                    //NoOfTowers = obj.getto
                    Area[c] = obj.getArea();
                    System.out.println("Latttttttttttt: "+c+" : "+obj.getGpsLatitude());
                    System.out.println("Longiiiiiiiiii: "+c+" : "+obj.getGpsLongitude());
                }
            }
            System.out.println("RESULTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            onMapReady(mMap);
        }
    }


    ////////// L O A D   L I N E   B Y   P R O V I N C E /////////////////////////////////////////
    private class getLineDetails extends AsyncTask<String, Void, MmsAddline[]> {
        @Override

        protected MmsAddline[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url5 = Util.SRT_URL + "findLineByArea/" + MapArea + "/";

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url5, MmsAddline[].class);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(MmsAddline[] results) {
            // ListView Item Click Listener
            System.out.println("results" + results);
            System.out.println("results" + results.length);
            String[] line;
            valuesLine = new String[results.length];

            if (results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddline obj = results[c];
                    valuesLine[c] = obj.getLineName();
                    //SpinnerMapLine.put(c, obj.getId());
                }
            }
//            ArrayAdapter<String> adapterLine = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesLine);
//            adapterLine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            SpinnerLine = findViewById(R.id.spnLine);
//            SpinnerLine.setAdapter(adapterLine);
        }
    }

    public Location getCurrentLocation() {

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //Get Current Location********************************************************************

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        //check whether the network provider is enabled
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude
                    final double latitude = location.getLatitude();
                    System.out.println("Current Latitude in Main: " + latitude);

                    //get the longitude
                    final double longitude = location.getLongitude();
                    System.out.println("Current Longitude in Main: " + longitude);

                    locationManager.removeUpdates(this);
                    locationManager = null;

                    //Instantiate LatLng class
                    LatLng latLng = new LatLng(latitude, longitude);

                    //Instantiate Geocoder class
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {

                        //Set the snippet
                        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                            @Override
                            public View getInfoWindow(Marker arg0) {
                                return null;
                            }

                            @Override
                            public View getInfoContents(Marker marker) {

                                Context context = getApplicationContext(); //or getActivity(), YourActivity.this, etc.

                                LinearLayout info = new LinearLayout(context);
                                info.setOrientation(LinearLayout.VERTICAL);

                                TextView title = new TextView(context);
                                title.setTextColor(Color.BLACK);
                                title.setGravity(Gravity.CENTER);
                                title.setTypeface(null, Typeface.BOLD);
                                title.setText(marker.getTitle());

                                TextView snippet = new TextView(context);
                                snippet.setTextColor(Color.GRAY);
                                snippet.setText(marker.getSnippet());

                                info.addView(title);
                                info.addView(snippet);

                                return info;
                            }
                        });

                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = "Current Latitude: " + addressList.get(0).getLatitude() + " \n " +
                                "Current Longitude: " + addressList.get(0).getLongitude();
                        mMap.addMarker(new MarkerOptions().position(latLng).snippet(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude
                    final double latitude = location.getLatitude();

                    //get the longitude
                    final double longitude = location.getLongitude();

                    locationManager.removeUpdates(this);
                    locationManager = null;

                    //Instantiate LatLng class
                    LatLng latLng = new LatLng(latitude, longitude);

                    //Instantiate Geocoder class
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = "Current Latitude: " + addressList.get(0).getLatitude() + " \n " +
                                "Current Longitude: " + addressList.get(0).getLongitude();
                        mMap.addMarker(new MarkerOptions().position(latLng).snippet(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        } else {
            //Add a marker in abc and move the camera
//            LatLng abc = new LatLng(8.311700, 80.401593);
//            mMap.addMarker(new MarkerOptions().position(abc).title("abc"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(abc, 10.2f));

        }
        return null;
    }


}