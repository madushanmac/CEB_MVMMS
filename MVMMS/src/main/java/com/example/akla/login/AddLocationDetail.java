package com.example.akla.login;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;

public class AddLocationDetail extends AppCompatActivity
            implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    TextView GPSLongitude;
    TextView GPSLatitude;
    EditText LocationDescription;
    Spinner SpinnerTypeofLocated;
    Spinner SpinnerMounting;
    EditText EquipmentId;

    TextView a;
    TextView b;
    double lat;
    double lon;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
        statusCheck();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        GPSLatitude = findViewById(R.id.etLatitude);
        GPSLongitude = findViewById(R.id.etLongitude);


        Button ButtonSearch = findViewById(R.id.btnSearch);
        ButtonSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                {

                 //  new AddLocationDetail.GetLocation().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }


            }


        });

        Button ButtonSave = findViewById(R.id.btnSave);
        ButtonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                {

                    // Do something in response to button click
//                Toast.makeText(AddTowerMaintainance.this, "alertMessage",
//                        Toast.LENGTH_LONG).show();

                    GPSLongitude = findViewById(R.id.etLongitude);
                    GPSLatitude = findViewById(R.id.etLatitude);
                    LocationDescription = findViewById(R.id.etLocationDescription);
                    SpinnerTypeofLocated = findViewById(R.id.SpinnerTypeofLocated);
                    SpinnerMounting = findViewById(R.id.SpinnerMounting);

                    if /*(GPSLongitude.getText().toString().trim().equals("")) {
                        GPSLongitude.setError("Should add a Longitude!");
                    } else if (GPSLatitude.getText().toString().trim().equals("")) {
                        GPSLatitude.setError("Should add a Latitude!");
                    } else if*/ (LocationDescription.getText().toString().trim().equals("")) {
                        LocationDescription.setError("Should add a Location Description!");
                    }else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(AddLocationDetail.this);
                        builder.setCancelable(true);
                        builder.setMessage("Do you want to save the Location Details?");
                        builder.setTitle("Save Location Details");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        new AddLocationDetail.SaveLocation().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        Toast.makeText(getApplicationContext(), "Successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "UnSuccessfully!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }

            }

        });

    }

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.apphome) {
            Intent intent = new Intent(AddLocationDetail.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddLocationDetail.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddLocationDetail.this, AddSupport.class);
            startActivity(intent);


        } else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddLocationDetail.this, TM2.class);
            startActivity(intent);


        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddLocationDetail.this, AddEquipment.class);
            startActivity(intent);


        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddLocationDetail.this, Login.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(AddLocationDetail.this, GetNearByTower.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddLocationDetail.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(AddLocationDetail.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(AddLocationDetail.this, AddTransformers.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void statusCheck() {
        System.out.println("??????????????????????????????????????????????????????????????????????");
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else {

            GPStracker g = new GPStracker(getApplicationContext());
            Location l = g.getLocation();

            if (l != null) {
                lat = l.getLatitude();
                lon = l.getLongitude();

                a = findViewById(R.id.etLongitude);
                b = findViewById(R.id.etLatitude);

//                String a = String.valueOf(lat);
//                String b = String.valueOf(lon);
                DecimalFormat df2 = new DecimalFormat(".##########");

                a.setText(df2.format(lon));
                b.setText(df2.format(lat));

                System.out.println("................................" + lat);
                System.out.println("................................" + lon);

            }
        }
    }

    private void buildAlertMessageNoGps() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent = new Intent(AddLocationDetail.this, MainActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {

    }

    private class SaveLocation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            System.out.println(" doInBackground ");

           // GPSLongitude = (EditText) findViewById(R.id.etLongitude);
           // GPSLatitude = (EditText) findViewById(R.id.etLatitude);
            LocationDescription = findViewById(R.id.etLocationDescription);
            SpinnerTypeofLocated = findViewById(R.id.SpinnerTypeofLocated);
            SpinnerMounting = findViewById(R.id.SpinnerMounting);

           // String longitude = GPSLongitude.getText().toString();
            //String latitude = GPSLatitude.getText().toString();
            String locationDescription = LocationDescription.getText().toString();
            String typeofLocated = SpinnerTypeofLocated.getSelectedItem().toString();
            String mounting = SpinnerMounting.getSelectedItem().toString();


            GPSLongitude = findViewById(R.id.etLongitude);
            final String gpslongitude = GPSLongitude.getText().toString();


            GPSLatitude = findViewById(R.id.etLatitude);
            final String gpsLatitude = GPSLatitude.getText().toString();

            PcbLocation locObj = new PcbLocation();
            locObj.setGpsLatitude(gpsLatitude);
            locObj.setGpsLongitude(gpslongitude);
            locObj.setLocationDescription(locationDescription);
            locObj.setTypeOfLocated(typeofLocated);
            locObj.setMounting(mounting);


            final RestTemplate restTemplate = new RestTemplate();
            //final String url1 = Util.SRT_URL + "MMSaddEquipmentMobile/" + reference + "/" + type +"/"+ division + "/" + brnach + "/" + condition + "/" + unit + "/" + idNo + "/" + serialNo + "/" + voltage + "/" + weightofTransformer + "/"+oilWeight+"/"+volumeofOil+"/"+photoTaken+"/"+photoRef+"/"+capacity+"/"+manufactureDate+"/"+manufactureBrand+"/"+manufactureLTL+"/";
            final String url1 = Util.SRT_URL + "MMSaddLocationMobile";
            System.out.println(" url1 " + url1);
            System.out.println(" ...........................url1tttttttttttttt ");
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //String massage = restTemplate.getForObject(url1, String.class);
            System.out.println(" url1ttttttttttttttgggggg ");
            String obj = restTemplate.postForObject(url1, locObj, String.class);
            return obj;


            /*final RestTemplate restTemplate = new RestTemplate();

            final String url1 = Util.SRT_URL + "addLineDB/" + gpslongitude + "/" + gpsLatitude + "/" + locationDescription + "/" + typeofLocated + "/" + mounting + "/" ;
            System.out.println(" url1 " + url1);
            System.out.println(" ...........................url1tttttttttttttt ");
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println(" url1ttttttttttttttgggggg ");
            // return restTemplate.getForObject(url1, String.class);
            return null;*/

        }

    }


    private class GetLocation extends AsyncTask<PcbLocation, Void, PcbLocation> {
        @TargetApi(Build.VERSION_CODES.N)
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected PcbLocation doInBackground(PcbLocation... urls) {
            System.out.println(" doInBackground ");

            EquipmentId = findViewById(R.id.etEquipmentId);

            String equipmentId = EquipmentId.getText().toString();



            final RestTemplate restTemplate = new RestTemplate();
            //final String url1 = Util.SRT_URL + "MMSaddEquipmentMobile/" + reference + "/" + type +"/"+ division + "/" + brnach + "/" + condition + "/" + unit + "/" + idNo + "/" + serialNo + "/" + voltage + "/" + weightofTransformer + "/"+oilWeight+"/"+volumeofOil+"/"+photoTaken+"/"+photoRef+"/"+capacity+"/"+manufactureDate+"/"+manufactureBrand+"/"+manufactureLTL+"/";
            final String url1 = Util.SRT_URL + "GetLocationMobile/" +equipmentId;
            //System.out.println(" url1 " + url1);
            System.out.println(" ...........................url1tttttttttttttt ");
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //String massage = restTemplate.getForObject(url1, String.class);
            System.out.println(" url1ttttttttttttttgggggg ");
            PcbLocation obj = restTemplate.getForObject(url1, PcbLocation.class);

            System.out.println(" url1ttttttttttttttgggggg " +obj);

            return obj;

        }

        // @Override
        protected void onPostExecute(PcbLocation result) {
            try {

                System.out.println("hiii5555 yyyyyyyyy" + result);

                EquipmentId.setText(result.getEquipmentId());

                GPSLongitude = findViewById(R.id.etLongitude);
                GPSLatitude = findViewById(R.id.etLatitude);
                LocationDescription = findViewById(R.id.etLocationDescription);
                SpinnerTypeofLocated = findViewById(R.id.SpinnerTypeofLocated);
                SpinnerMounting = findViewById(R.id.SpinnerMounting);

                GPSLongitude.setText(result.getGpsLongitude());
                GPSLatitude.setText(result.getGpsLatitude());
                LocationDescription.setText(result.getLocationDescription());


//
            } catch (Exception e) {
                System.out.println(" error " + e.getMessage());
            }


        }



    }

}
