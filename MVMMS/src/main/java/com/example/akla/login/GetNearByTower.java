package com.example.akla.login;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static com.example.akla.login.MainActivity.MY_PERMISSION_REQUEST_LOCATION;
import static com.example.akla.login.Notification.CHANNEL_1_ID;

public class GetNearByTower extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    private ProgressDialog ProgDialog;

    double lat;
    double lon;

    Location mLastLocation;
    Marker mCurrLocationMarker;


    String url6;
    List<Address> addressList;
    int count;
    private NotificationManagerCompat notificationManager; //notification


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("inside onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_nearby_tower);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**Live location*/
        getCurrentLocation();

        /**Execute the url6*/
        new loadTowers().execute(url6);

        //Notification
        notificationManager = NotificationManagerCompat.from(this);
        System.out.println("count-------------------------------------: " + count);

        //Notification
        Intent notificationIntent = new Intent(this, GetNearByTower.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        notificationIntent.putExtra("pushBundle", extras);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        android.app.Notification notification = new NotificationCompat.Builder(GetNearByTower.this, CHANNEL_1_ID)
                .setChannelId(CHANNEL_1_ID)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(getApplicationContext().getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis())
                .setTicker("God Reacts")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setColor(135-206-250)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentTitle("MVMMS - CEB (Information)")
                .setContentText("There are "  + count + " towers nearby")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                //.addAction(R.drawable.ceb_logo2, "OK", pendingIntent)
                .build();

        notificationManager.notify(1, notification);

//        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
//        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
//        //intent.putExtra(Settings.EXTRA_CHANNEL_ID, notification.getChannelId());
//        startActivity(intent);


    }


    /**
    //Get Current Location
    public void getCurrentLocation() {

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        //check whether the network provider is enabled
        if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
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

                        addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str =    "Current Latitude: " + addressList.get(0).getLatitude() + " \n " +
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
        } else if (locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude
                    final double latitude = location.getLatitude();
//                    System.out.println("Current Latitude in GetNearByTower: " + latitude);

                    //get the longitude
                    final double longitude = location.getLongitude();
//                    System.out.println("Current Longitude in GetNearByTower: " + longitude);

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

                        addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str =    "Current Latitude: " + addressList.get(0).getLatitude() + " \n " +
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
        }

    }
    */



    //Get Current Location
    public void getCurrentLocation() {

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        //check whether the network provider is enabled
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
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

                        addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str =    "Current Latitude: " + addressList.get(0).getLatitude() + " \n " +
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
//                    System.out.println("Current Latitude in GetNearByTower: " + latitude);

                    //get the longitude
                    final double longitude = location.getLongitude();
//                    System.out.println("Current Longitude in GetNearByTower: " + longitude);

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

                        addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str =    "Current Latitude: " + addressList.get(0).getLatitude() + " \n " +
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
        }

    }


    //Check whether location permissions allow or not by default, if not request for location permissions
    public boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(GetNearByTower.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSION_REQUEST_LOCATION);
                                ActivityCompat.requestPermissions(GetNearByTower.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSION_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_REQUEST_LOCATION);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSION_REQUEST_LOCATION);

            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        getCurrentLocation();
                    } else if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        getCurrentLocation();
                    }
                } else {
                    checkLocationPermission();
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        checkLocationPermission();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

    }
    /**********************************************************************************************/


    private void updateUI(Location loc) {
        System.out.println("updateUI");
    }



    private class loadTowers extends AsyncTask<String, Void, MmsAddsupport[]> implements LocationListener {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ProgDialog = new ProgressDialog(GetNearByTower.this);
            /**message should be changed according to the requirement*/
            ProgDialog.setMessage("Please wait...\n(This may take some time, depending on your network connection)");
            ProgDialog.setIndeterminate(false);
            ProgDialog.setTitle(Util.alert_header);
            ProgDialog.setIcon(R.drawable.logo);
            ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            ProgDialog.setCancelable(true);
            ProgDialog.show();
        }

        @Override
        protected MmsAddsupport[] doInBackground(String... params) {

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Looper.prepare();
            if (ActivityCompat.checkSelfPermission(GetNearByTower.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GetNearByTower.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);

            RestTemplate rest = new RestTemplate();
            statusCheck();

            url6 = Util.SRT_URL + "getNearByTower?lat=" + lat  + "&lon=" + lon ;
            System.out.println("url6: ********************" + url6);


            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//            Looper.loop();
            Looper.myLooper();
            System.out.println(rest.getForObject(url6, MmsAddsupport[].class)+"YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
            MmsAddsupport[] nearTowerList = rest.getForObject(url6, MmsAddsupport[].class);
            return nearTowerList;

            }


        @Override
        protected void onPostExecute(MmsAddsupport[] results) {

//            System.out.println("111111111111111111111111111111111111111111111111111");
//            System.out.println("111111111111111111111111111111111111111111111111111results: " + results);

            if(results != null){

                count =  results.length-1;
//                System.out.println("count" + count);
                for(int c = 0; c <= count; c++){

                    MmsAddsupport obj = results[c];

                    String towerNo = obj.getTowerNo();
                    double latitud = obj.getGpsLatitude().doubleValue();
                    double longitud = obj.getGpsLongitude().doubleValue();
                    String area = obj.getArea();

//                    System.out.println("Latitude and Longitude " + c + ": " + latitud + " , " + longitud);

                    /**Instantiate LatLng class*/
                    LatLng latLng = new LatLng(latitud, longitud);

                    /**Instantiate Geocoder class*/
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {

                        /**Set the snippet*/
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
                        for (int i = 0; i <= count; i++){
                            List<Address> addressList1 = geocoder.getFromLocation(latitud, longitud, count+1);
                            String string = "Tower No: " + towerNo + " \n " +
                                            "Latitude: " + addressList1.get(0).getLatitude() + " \n " +
                                            "Longitude: " + addressList1.get(0).getLongitude() + " \n " +
                                            "Area: " + area;
                            mMap.addMarker(new MarkerOptions().position(latLng).snippet(string).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                Toast.makeText(GetNearByTower.this, " No towers to be found around 200m area " , Toast.LENGTH_LONG).show();
//                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
            ProgDialog.dismiss();
            //Toast.makeText(GetNearByTower.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();

        }




        public void statusCheck() {
            System.out.println("inside statusCheck************************************* ");
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //buildAlertMessageNoGps();

            } else if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                //buildAlertMessageNoGps();

            }
            else {

                GPStracker g = new GPStracker(getApplicationContext());
                Location l = g.getLocation();


                if (l != null) {
                    lat = l.getLatitude();
                    lon = l.getLongitude();

                    System.out.println("................................" + lat);
                    System.out.println("................................" + lon);

                }
            }
        }

        @Override
        public void onLocationChanged(Location location) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10.2f);
            mMap.animateCamera(cameraUpdate);
            locationManager.removeUpdates(this);

            mLastLocation = location;
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }

            updateUI(location);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {
            if (locationManager != null) {
                locationManager.removeUpdates(this);
            }

        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.apphome) {
            Intent intent = new Intent(GetNearByTower.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(GetNearByTower.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(GetNearByTower.this, AddSupport.class);
            startActivity(intent);


        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(GetNearByTower.this, TM2.class);
            startActivity(intent);


        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(GetNearByTower.this, AddEquipment.class);
            startActivity(intent);


        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(GetNearByTower.this, Login.class);
            startActivity(intent);
        } else if (id == R.id.nearby) {
            Intent intent = new Intent(GetNearByTower.this, GetNearByTower.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(GetNearByTower.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(GetNearByTower.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(GetNearByTower.this, AddTransformers.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
