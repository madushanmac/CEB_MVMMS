package com.example.akla.login;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback {

//        public static TextView data;
//        private Button readButton;
//        private static String singleParsed = "";
//        private static String dataParsed = "";
//        Button click;
//        private Button readButton;

    private GoogleMap mMap;
    LocationManager locationManager;
    public static final int MY_PERMISSION_REQUEST_LOCATION = 99;
    //private static final int REQUEST_STORAGE_PERMISSION = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
//        private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //If location off, direct to the location settings window
        int off = 0;
        try {
            off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (off == 0) {
            Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(onGPS);
        }
//        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));

        //checkLocationPermission();
        checkAndRequestPermissions();
        getCurrentLocation();


    }

/*
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
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSION_REQUEST_LOCATION);
                                ActivityCompat.requestPermissions(MainActivity.this,
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
        } if (ContextCompat.checkSelfPermission(MainActivity.this.getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // If you do not have permission, request it
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } if (ContextCompat.checkSelfPermission(MainActivity.this.getApplicationContext(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // If you do not have permission, request it
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_IMAGE_CAPTURE);
        } else {
            return true;
        }return false;
    }*/

//check and request for permissions
    private boolean checkAndRequestPermissions () {
        int permissionLocation = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionLocation1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int camerapermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionLocation1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);



                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        //Log.d(TAG, "sms & location services permission granted");
                        // process the normal flow

                        //else any one or both the permissions are not granted
                    } else {
                        //Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                            showDialogOK("Service Permissions are required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    finish();
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?");
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }

            }

            /*case MY_PERMISSION_REQUEST_LOCATION: {
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
                    //checkLocationPermission();
                }
                return;
            }*/

            /*case REQUEST_IMAGE_CAPTURE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, Do
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {

                    } else if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                    }
                } else {

                }*/




    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
    private void explain(String msg){
        final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        //  permissionsclass.requestPermission(type,code);
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.exampledemo.parsaniahardik.marshmallowpermission")));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }

    /**
    public Location getCurrentLocation() {

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //Get Current Location********************************************************************

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        //check whether the network provider is enabled
        if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
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
        } else if (locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
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
    */


    @SuppressLint("MissingPermission")
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


    @SuppressLint("MissingPermission")
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
        //checkLocationPermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

    }

    /**********************************************************************************************/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Do you want to Exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application

//            Intent intent = new Intent(AddTowerMaintainance.this, TM2.class);
//            startActivity(intent);

                    finish();

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        NavigationView nv= (NavigationView) findViewById(R.id.nav_view);
        Menu m=nv.getMenu();
        int id = item.getItemId();

        if (id == R.id.apphome) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(MainActivity.this, GetNearByTower.class);
            startActivity(intent);

        } else if (id == R.id.mapselection) {
            Intent intent = new Intent(MainActivity.this, MapSelection.class);
            startActivity(intent);

            //////////////////////////////// PHM - LCM ////////////////////////////////////////////

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(MainActivity.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(MainActivity.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(MainActivity.this, TM2.class);
            startActivity(intent);

            //////////////////////////////// PHM - SUb ////////////////////////////////////////////
            //sub path
        } else if (id == R.id.nav_Subline) {
            Intent intent = new Intent(MainActivity.this, subline.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(MainActivity.this, AddGantry.class);
            startActivity(intent);
        } else if (id == R.id.nav_editGantry) {
            Intent intent = new Intent(MainActivity.this, EditGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addBusBar) {
            Intent intent = new Intent(MainActivity.this, AddBusBar.class);
            startActivity(intent);
        } else if (id == R.id.nav_editBusBar) {
            Intent intent = new Intent(MainActivity.this, EditBusBar.class);
            startActivity(intent);

        } else if (id == R.id.nav_addStructual) {
            Intent intent = new Intent(MainActivity.this, AddStructural.class);
            startActivity(intent);
        } else if (id == R.id.nav_editStructual) {
            Intent intent = new Intent(MainActivity.this, EditStructural.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantryMore) {
            Intent intent = new Intent(MainActivity.this, AddGantryMore.class);
            startActivity(intent);
        } else if (id == R.id.nav_editGantryMore) {
            Intent intent = new Intent(MainActivity.this, EditGantryMore.class);
            startActivity(intent);

        } else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(MainActivity.this, AddFeeder.class);
            startActivity(intent);
        } else if (id == R.id.nav_editFeeder) {
            Intent intent = new Intent(MainActivity.this, EditFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSwitches) {
            Intent intent = new Intent(MainActivity.this, AddSwitches.class);
            startActivity(intent);
        } else if (id == R.id.nav_editSwitches) {
            Intent intent = new Intent(MainActivity.this, EditSwitches.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSurge) {
            Intent intent = new Intent(MainActivity.this, AddSurgeArrestors.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTransformersG) {
            Intent intent = new Intent(MainActivity.this, AddTransformersG.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(MainActivity.this, AddEquipment.class);
            startActivity(intent);

///////////////////// POLE DETAILS //////////////////////////////////////////////

        } else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(MainActivity.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addMVPolesMaintenance) {
            Intent intent = new Intent(MainActivity.this, AddMVPolesMaintenanceSelect.class);
            startActivity(intent);

        } else if (id == R.id.nav_addLVFeeders) {
            Intent intent = new Intent(MainActivity.this, AddLVFeeders.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(MainActivity.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(MainActivity.this, AddTransformers.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);

        } else if (id == R.id.nav_Subline) {
            Intent intent = new Intent(MainActivity.this, AddGSS.class);
            startActivity(intent);
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*     public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addLine) {
            Intent intent = new Intent(MainActivity.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(MainActivity.this, AddSupport.class);
            startActivity(intent);


        } else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(MainActivity.this, TM2.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);

               }
               //else if(id == R.id.apphome){
                 //Intent intent = new Intent(MainActivity.this, MainActivity.class);
               //startActivity(intent);
            //}else if(id == R.id.nearby){
              //Intent intent = new Intent(MainActivity.this, NearbyTower.class);
            //startActivity(intent);
            //}

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }*/


/*    public static void showTowers(JSONArray jsonArray) throws JSONException {
        System.out.println(jsonArray);

        for (int i=0; i<jsonArray.length(); i++){
            JSONObject JO = (JSONObject) jsonArray.get(i);

            double lat,lng;
            String id = "", towerNo = "-", towerType = "-", snippet = "";

            lat = (double) JO.get("gpsLatitude");
            lng = (double) JO.get("gpsLongitude");
            towerNo = (String) JO.get("towerNo");
            id = Integer.toString((Integer) JO.get("id"));
            towerType = Integer.toString((Integer) JO.get("towerType"));
            snippet = id + "\n" + towerType;

            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(towerNo).snippet(snippet).icon(BitmapDescriptorFactory.fromResource(R.drawable.towermarker)));

            /**
     * singleParsed = "Lat:" + JO.get("gpsLatitude") + " Lng:" + JO.get("gpsLongitude") + "\n";
     * dataParsed = dataParsed + singleParsed;
     * MainActivity.data.setText(this.dataParsed);
     *//*
        }

    }*/
}


