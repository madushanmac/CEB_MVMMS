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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

//public class AddInformationReatedToSampling extends AppCompatActivity {
    public class AddInformationReatedToSampling extends AppCompatActivity
            implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {


    Spinner SpinnerType;
  //  Spinner SpinnerPrimary;
    EditText SamplingPort;
    EditText Extracted_from_top;
    EditText SamplingLogicSatisfied;
    EditText SampleNumber;
    EditText SampleDate;
    EditText PCBTestResults;
    EditText PCBTestdate;
    EditText EPF_Numbers_of_the_Test_Group;
    EditText Remarks;
    EditText EPF_Numbers_of_the_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information_reated_to_sampling);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button ButtonSave = findViewById(R.id.btnSample);
        ButtonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                {

                    // Do something in response to button click
//                Toast.makeText(AddTowerMaintainance.this, "alertMessage",
//                        Toast.LENGTH_LONG).show();

                    SamplingPort = findViewById(R.id.etSamplingPort);
                    Extracted_from_top = findViewById(R.id.etExtracted_from_top);
                    SamplingLogicSatisfied = findViewById(R.id.etSamplingLogicSatisfied);
                    SampleNumber = findViewById(R.id.etSampleNumber);
                    SampleDate = findViewById(R.id.etSampleDate);
                    PCBTestResults = findViewById(R.id.etPCBTestResults);
                    PCBTestdate = findViewById(R.id.etPCBTestdate);
                    EPF_Numbers_of_the_Test_Group = findViewById(R.id.etEPF_Numbers_of_the_Test_Group);
                    Remarks = findViewById(R.id.etRemarks);
                    EPF_Numbers_of_the_group = findViewById(R.id.etEPF_Numbers_of_the_group);
                    SpinnerType = findViewById(R.id.SpinnerType);
                    //SpinnerPrimary = (Spinner) findViewById(R.id.SpinnerPrimary);

                    if (SamplingPort.getText().toString().trim().equals("")) {
                        SamplingPort.setError("Should add a Sampling Port!");
                    } else if (Extracted_from_top.getText().toString().trim().equals("")) {
                        Extracted_from_top.setError("Should add a Extracted from top!");
                    } else if (SamplingLogicSatisfied.getText().toString().trim().equals("")) {
                        SamplingLogicSatisfied.setError("Should add a Sampling Logic Satisfied!");
                    } else if (SampleNumber.getText().toString().trim().equals("")) {
                        SampleNumber.setError("Should add a Sample Number!");
                    } else if (SampleDate.getText().toString().trim().equals("")) {
                        SampleDate.setError("Should add a Sample Date!");
                    } else if (PCBTestResults.getText().toString().trim().equals("")) {
                        PCBTestResults.setError("Should add a Weight of PCB Test Results!");
                    } else if (PCBTestdate.getText().toString().trim().equals("")) {
                        PCBTestdate.setError("Should add aPCB Test date!");
                    } else if ( EPF_Numbers_of_the_Test_Group.getText().toString().trim().equals("")) {
                        EPF_Numbers_of_the_Test_Group.setError("Should add a  EPF Numbers of the Test Group!");
                    } else if (Remarks.getText().toString().trim().equals("")) {
                        Remarks.setError("Should add a Remarks!");
                    } else if ( EPF_Numbers_of_the_group .getText().toString().trim().equals("")) {
                        EPF_Numbers_of_the_group .setError("Should add a  EPF Numbers of the group !");
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(AddInformationReatedToSampling.this);
                        builder.setCancelable(true);
                        builder.setMessage("Do you want to save the Information reated to sample?");
                        builder.setTitle("Save  Information reated to sample");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        new AddInformationReatedToSampling.SaveSample().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
            Intent intent = new Intent(AddInformationReatedToSampling.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddInformationReatedToSampling.this, AddLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddInformationReatedToSampling.this, AddSupport.class);
            startActivity(intent);


        } else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddInformationReatedToSampling.this, TM2.class);
            startActivity(intent);


        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddInformationReatedToSampling.this, AddEquipment.class);
            startActivity(intent);


        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddInformationReatedToSampling.this, Login.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(AddInformationReatedToSampling.this, GetNearByTower.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddInformationReatedToSampling.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(AddInformationReatedToSampling.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(AddInformationReatedToSampling.this, AddTransformers.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {

    }

   private class SaveSample extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            System.out.println(" doInBackground ");

            SamplingPort = findViewById(R.id.etSamplingPort);
            Extracted_from_top = findViewById(R.id.etExtracted_from_top);
            SamplingLogicSatisfied = findViewById(R.id.etSamplingLogicSatisfied);
            SampleNumber = findViewById(R.id.etSampleNumber);
            SampleDate = findViewById(R.id.etSampleDate);
            PCBTestResults = findViewById(R.id.etPCBTestResults);
            PCBTestdate = findViewById(R.id.etPCBTestdate);
            EPF_Numbers_of_the_Test_Group = findViewById(R.id.etEPF_Numbers_of_the_Test_Group);
            Remarks = findViewById(R.id.etRemarks);
            EPF_Numbers_of_the_group = findViewById(R.id.etEPF_Numbers_of_the_group);
            SpinnerType = findViewById(R.id.SpinnerType);
            //SpinnerPrimary = (Spinner) findViewById(R.id.SpinnerPrimary);

            String samplingPort = SamplingPort.getText().toString();
            String Type = SpinnerType.getSelectedItem().toString();
          //  String Primary = SpinnerPrimary.getSelectedItem().toString();
            String extracted_from_top = Extracted_from_top.getText().toString();
            String samplingLogicSatisfied = SamplingLogicSatisfied.getText().toString();
            String sampleNumber = SampleNumber.getText().toString();
            String sampleDate = SampleDate.getText().toString();
            String pCBTestResults = PCBTestResults.getText().toString();
            String pCBTestdate = PCBTestdate.getText().toString();
            String ePF_Numbers_of_the_Test_Group = EPF_Numbers_of_the_Test_Group.getText().toString();
            String remarks = Remarks.getText().toString();
            String ePF_Numbers_of_the_group = EPF_Numbers_of_the_group.getText().toString();

            final RestTemplate restTemplate = new RestTemplate();

            final String url1 = Util.SRT_URL + "addLineDB/" + Type + "/"+ "/" + samplingPort + "/" + extracted_from_top + "/" + samplingLogicSatisfied + "/" + sampleDate + "/" + sampleNumber + "/" + pCBTestdate + "/" + pCBTestResults + "/"+ePF_Numbers_of_the_group+"/"+remarks+"/"+ePF_Numbers_of_the_Test_Group+"/";
            System.out.println(" url1 " + url1);
            System.out.println(" ...........................url1tttttttttttttt ");
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println(" url1ttttttttttttttgggggg ");
            // return restTemplate.getForObject(url1, String.class);
            return null;

        }

    }

}
