package com.example.akla.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/*public class AddConditionOfTheEquipment extends AppCompatActivity {*/
    public class AddConditionOfTheEquipment extends AppCompatActivity
            implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {


    Spinner SpinnerOilLeaksPresent;
    Spinner SpinnerCorrosion;
    Spinner SpinnerBurnMarks;
    Spinner Spinnerattention;
    Spinner SpinnerEarthingConnection;
    Spinner SpinnerBreather;
    Spinner SpinnerOverload;
    Spinner SpinnerLighting;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_condition_of_the_equipment);


            Toolbar toolbar = findViewById(R.id.toolbar);
          // setSupportActionBar(toolbar);
            System.out.println("condition1");

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            System.out.println("condition2");
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            System.out.println("condition3");
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            Button ButtonSave = findViewById(R.id.btnConditionEq);
            ButtonSave.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    {

                        // Do something in response to button click
//                Toast.makeText(AddTowerMaintainance.this, "alertMessage",
//                        Toast.LENGTH_LONG).show();
                        SpinnerOilLeaksPresent = findViewById(R.id.SpinnerOilLeaksPresent);
                        SpinnerCorrosion = findViewById(R.id.SpinnerCorrosion);
                        SpinnerBurnMarks = findViewById(R.id.SpinnerBurnMarks);
                        Spinnerattention = findViewById(R.id.Spinnerattention);
                        SpinnerEarthingConnection = findViewById(R.id.SpinnerEarthingConnection);
                        SpinnerBreather = findViewById(R.id.SpinnerBreather);
                        SpinnerOverload = findViewById(R.id.SpinnerOverload);
                        SpinnerLighting = findViewById(R.id.SpinnerLighting);

{

                            AlertDialog.Builder builder = new AlertDialog.Builder(AddConditionOfTheEquipment.this);
                            builder.setCancelable(true);
                            builder.setMessage("Do you want to save the Condition Equipment?");
                            builder.setTitle("Save  Condition Equipment");
                            builder.setPositiveButton("Confirm",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Toast.makeText(getApplicationContext(), "Successfully!", Toast.LENGTH_SHORT).show();

                                            new AddConditionOfTheEquipment.SaveCondition().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

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
            Intent intent = new Intent(AddConditionOfTheEquipment.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, AddSupport.class);
            startActivity(intent);


        } else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, TM2.class);
            startActivity(intent);


        } else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, AddEquipment.class);
            startActivity(intent);


        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, Login.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, GetNearByTower.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, AddTransformers.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, AddSupport.class);
            startActivity(intent);


        } else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, TM2.class);
            startActivity(intent);


        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, AddEquipment.class);
            startActivity(intent);


        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, AddConditionOfTheEquipment.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddConditionOfTheEquipment.this, Login.class);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/


    @Override
    public void onClick(View v) {

    }

    private class SaveCondition extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            System.out.println(" doInBackground ");

            SpinnerOilLeaksPresent = findViewById(R.id.SpinnerOilLeaksPresent);
            SpinnerCorrosion = findViewById(R.id.SpinnerCorrosion);
            SpinnerBurnMarks = findViewById(R.id.SpinnerBurnMarks);
            Spinnerattention = findViewById(R.id.Spinnerattention);
            SpinnerEarthingConnection = findViewById(R.id.SpinnerEarthingConnection);
            SpinnerBreather = findViewById(R.id.SpinnerBreather);
            SpinnerOverload = findViewById(R.id.SpinnerOverload);
            SpinnerLighting = findViewById(R.id.SpinnerLighting);



            String oilLeaksPresent = SpinnerOilLeaksPresent.getSelectedItem().toString();
            String corrosion = SpinnerCorrosion.getSelectedItem().toString();
            String burnMarks  = SpinnerBurnMarks.getSelectedItem().toString();
            String attention = Spinnerattention.getSelectedItem().toString();
            String earthingConnection = SpinnerEarthingConnection.getSelectedItem().toString();
            String breather = SpinnerBreather.getSelectedItem().toString();
            String overload = SpinnerOverload.getSelectedItem().toString();
            String lighting = SpinnerLighting.getSelectedItem().toString();





            final RestTemplate restTemplate = new RestTemplate();

            final String url1 = Util.SRT_URL + "addLineDB/" + oilLeaksPresent + "/" + corrosion + "/" + burnMarks + "/" + attention + "/" + earthingConnection + "/" + breather + "/" + overload + "/" + lighting + "/";
            System.out.println(" url1 " + url1);
            System.out.println(" ...........................tttttttttttttt ");
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println(" url1ttttttttttttttgggggg ");
            //return restTemplate.getForObject(url1, String.class);
            return null;

        }

    }

}
