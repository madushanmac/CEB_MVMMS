package com.example.akla.login;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import static com.example.akla.login.Util.isConnected;

public class AddLine extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener {

//    private EditText fromDateEtxt;
//    private DatePickerDialog fromDatePickerDialog;
//
//
//    private SimpleDateFormat dateFormatter;


    EditText LineID;
    EditText Code;
    EditText Name;
    Spinner SpinnerType;
    EditText Length;
    Spinner SpinnerArea;
    EditText NoOfPoles;
    EditText NoOfTowers;
    EditText ComDate;
    Button btn_date;
    EditText in_date;

    DBHelper mydb2;

    String[] values = new String[]{};
    String[] valuesType = new String[]{};

    HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();
    HashMap<Integer, String> spinnerMap1 = new HashMap<Integer, String>();
    String area;
    String Type;

    //private String values[] = new String[]{};
    String massage = "";
    private ProgressDialog ProgDialog;
    private int mYear, mMonth, mDay;

    //Conductor type
    String[] valuesConType = new String[]{};
    Spinner SpinnerConductorType;
    EditText etConType;
    String ConType;
    HashMap<Integer,String> spinnerMap2 = new HashMap<Integer, String>();

    //Circuit type
    RadioGroup radioGroupCircuitType;
    private RadioButton radioButtonCircuitType;

    RadioGroup radioGrouLineGroup;
    private RadioButton Lv;

    //Feeder Identification
    Spinner SpinnerFeederIdentification;
    EditText EnterFeeder;
    String feeder;

    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private EditText etFeeder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_line);

        btn_date = findViewById(R.id.btn_date);
        in_date = findViewById(R.id.in_date);
        btn_date.setOnClickListener(AddLine.this);


        if (!isConnected(AddLine.this)) {
            readExcel();
            readExcelLoadtype();
            readExcelConType();
        } else {
            new loadarea().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new loadtype().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new loadConType().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        }
        System.out.println("hhhhh2");


        //}

//        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//
//        findViewsById();
//
//        setDateTimeField();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SpinnerType = findViewById(R.id.SpinnerType);
        SpinnerType.setPrompt("Select Type");
        SpinnerType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idtype = spinnerMap1.get(parent.getSelectedItemPosition());
                Type = idtype;
                //  Toast.makeText(AddLine.this,"Name" +name,Toast.LENGTH_LONG).show();
                // Toast.makeText(AddLine.this,"Type"+idtype,Toast.LENGTH_LONG).show();


            }


            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        SpinnerArea = findViewById(R.id.spinnerArea);
        SpinnerArea.setPrompt("Select Area");


        SpinnerArea.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idarea = spinnerMap.get(parent.getSelectedItemPosition());
                area = idarea;

            }


            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



//    private void findViewsById() {
//        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
//        fromDateEtxt.setInputType(InputType.TYPE_N
// ULL);
//        fromDateEtxt.requestFocus();
//
//    }
//
//    private void setDateTimeField() {
//        fromDateEtxt.setOnClickListener((View.OnClickListener) this);
//
//
//        Calendar newCalendar = Calendar.getInstance();
//        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
//            }
//
//        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//
//       }

        //Spinner Feeder Identification
        SpinnerFeederIdentification = findViewById(R.id.spinnerFeeder);
        EnterFeeder = findViewById(R.id.etFeederHide);
        feeder = SpinnerFeederIdentification.getSelectedItem().toString();
        SpinnerFeederIdentification.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(SpinnerFeederIdentification.getSelectedItem().toString().equals("Other")) {
                    EnterFeeder.setVisibility(View.VISIBLE);

                }
                else {
                    EnterFeeder.setVisibility(View.INVISIBLE);
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button ButtonSendDB = findViewById(R.id.btnSaveDB);
        ButtonSendDB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                {

                    // Check for the external storage permission
                    if (ContextCompat.checkSelfPermission(AddLine.this.getApplicationContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // If you do not have permission, request it
                        ActivityCompat.requestPermissions(AddLine.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_STORAGE_PERMISSION);
                    }

                    // Do something in response to button click
//                Toast.makeText(AddTowerMaintainance.this, "alertMessage",
//                        Toast.LENGTH_LONG).show();

                    LineID = findViewById(R.id.etLineID);
                    Code = findViewById(R.id.etCode);
                    Name = findViewById(R.id.etName);
                    SpinnerType = findViewById(R.id.SpinnerType);
                    Length = findViewById(R.id.etLength);
                    SpinnerArea = findViewById(R.id.spinnerArea);
                    NoOfPoles = findViewById(R.id.etNoOfPoles);
                    NoOfTowers = findViewById(R.id.etNoOfTowers);
                    ComDate = findViewById(R.id.in_date);
                    SpinnerConductorType = findViewById(R.id.spinnerConType);
                    EnterFeeder = findViewById(R.id.etFeederHide);
                    SpinnerFeederIdentification = findViewById(R.id.spinnerFeeder);
                    if(SpinnerFeederIdentification.getSelectedItem().toString().equals("Other")){
                        feeder = EnterFeeder.getText().toString();
                    }else{
                        feeder = SpinnerFeederIdentification.getSelectedItem().toString();
                    }

                    if (Code.getText().toString().trim().equals("")) {
                        Code.setError("Should add a Tower Number!");
                    } else if (Name.getText().toString().trim().equals("")) {
                        Name.setError("Should add a Tower Number!");
                    } else if (Length.getText().toString().trim().equals("")) {
                        Length.setError("Should add a Tappings!");
                    } else if (NoOfPoles.getText().toString().trim().equals("")) {
                        NoOfPoles.setError("Should add a missing parts!");
                    } else if (NoOfTowers.getText().toString().trim().equals("")) {
                        NoOfTowers.setError("Should add a Special Observations!");
                    } else if (ComDate.getText().toString().trim().equals("")) {
                        ComDate.setError("Should add a Special Observations!");
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(AddLine.this);
                        builder.setCancelable(true);
                        builder.setMessage("Do you want to save the line?");
                        builder.setTitle("Save Line");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (!isConnected(AddLine.this)) {
                                            createExcel();
                                            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();


                                        }else{
                                            new SaveLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                                            createExcel();
                                            Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();

                                        }
                                        /*new SaveLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                                        createExcel();*/


                                       // new SaveLine().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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


        Button buttonSync = findViewById(R.id.btnSync);


        if (!isConnected(AddLine.this)) {


            findViewById(R.id.btnSync).setVisibility(View.INVISIBLE);
        }
        else {
            findViewById(R.id.btnSync).setVisibility(View.VISIBLE);
        }

        buttonSync.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                {


                    if (!isConnected(AddLine.this)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddLine.this);
                        builder.setCancelable(true);
                        builder.setMessage("Please Check Your Internet Connection");
                        builder.setTitle("No Internet Connection");
                        builder.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //new SaveLineFromExcel().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        //createExcel();
                                       // readLine();
                                        //Toast.makeText(getApplicationContext(), "Successfully saved 1!", Toast.LENGTH_SHORT).show();
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


                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(AddLine.this);
                        builder.setCancelable(true);
                        builder.setMessage("Do you want to save the line?");
                        builder.setTitle("Save Line");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                      //  readLine();
                                       // Toast.makeText(getApplicationContext(), "startSaveLineFromExcel", Toast.LENGTH_SHORT).show();
                                        //List<MmsAddline> obj = readLine();

                                        new SaveLineFromExcel().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                                        // createExcel();
                                    }

                                });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "UnSuccessfully!", Toast.LENGTH_SHORT).show();

                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        //progressSaving();
                      //  Toast.makeText(getApplicationContext(), "Successfully saved 2!", Toast.LENGTH_SHORT).show();

                    }

                }


            }


        });


        SpinnerConductorType = findViewById(R.id.spinnerConType);
        SpinnerConductorType.setPrompt("Select Conductor Type");

        SpinnerConductorType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                String name = parent.getSelectedItem().toString();
                String idConType = spinnerMap2.get(parent.getSelectedItemPosition());
                ConType = idConType;
                // Toast.makeText(AddSupport.this,"Name" +name,Toast.LENGTH_LONG).show();
                // Toast.makeText(AddSupport.this,"ConductorType"+idConType,Toast.LENGTH_LONG).show();

            }


            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }




    public List<String>  readExcelConType(){
        System.out.println("readExcel");
        // Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
        List<String> resultSet = new ArrayList<String>();
        // Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

        System.out.println("readExcel1");
        File inputWorkbook  = new File(Environment.getExternalStorageDirectory() +File.separator + "Schedule" +File.separator+ "ConductorType.xls");
        System.out.println("readExcel2");
        //  Toast.makeText(getApplication(),"readExcel2: " , Toast.LENGTH_SHORT).show();

        if(inputWorkbook.exists()) {
            System.out.println("readExcel3");
            //    Toast.makeText(getApplication(),"readExcel3: " , Toast.LENGTH_SHORT).show();

            Workbook w;
            try {
                //          System.out.println("readExcel4");

                w = Workbook.getWorkbook(inputWorkbook);
                System.out.println("readExce5");
                //        Toast.makeText(getApplication(),"readExcel4: " , Toast.LENGTH_SHORT).show();

                // Get the first sheet
                Sheet sheet = w.getSheet(0);
                System.out.println("readExce6");

                // Loop over column and lines
                int coloumn = sheet.getRows();
                valuesConType = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);
                    System.out.println(cell.getContents());
                    //          Toast.makeText(getApplication(),"readExcel5: " + cell.getContents(), Toast.LENGTH_SHORT).show();
                    spinnerMap2.put(j,cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    valuesConType[j] = cell1.getContents();

                    continue;
                }



                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesConType);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerConductorType = findViewById(R.id.spinnerConType);
                SpinnerConductorType.setAdapter(adapterNs);

            } catch (BiffException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            resultSet.add("File not found..!");
        }
        if(resultSet.size()==0){
            resultSet.add("Data not found..!");
        }
        return resultSet;
    }


    public void alertMessage() {
        Toast.makeText(AddLine.this, "Error",
                Toast.LENGTH_LONG).show();

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        // Yes button clicked
                        // Yes button clicked
                        //       Toast.makeText(AddLine.this, "Yes Clicked",
                        //             Toast.LENGTH_LONG).show();
                        // progressSaving();
                        // new AddTowerMaintainanceData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        createExcel();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked
                        // do nothing
                        //   Toast.makeText(AddLine.this, "No Clicked",
                        //         Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want create Excel sheet?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }

    public void progressSaving() {
        //Show the dialog
        final ProgressDialog dialog = ProgressDialog.show(this, "Saving Data", "Please wait....", true);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    dialog.dismiss();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

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
        getMenuInflater().inflate(R.menu.add_line, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.apphome) {
            Intent intent = new Intent(AddLine.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nearby) {
            Intent intent = new Intent(AddLine.this, GetNearByTower.class);
            startActivity(intent);

            //////////////////////////////// PHM - LCM ////////////////////////////////////////////

        } else if (id == R.id.nav_addLine) {
            Intent intent = new Intent(AddLine.this, AddLine.class);
            startActivity(intent);
        } else if (id == R.id.nav_editLine) {
            Intent intent = new Intent(AddLine.this, EditLine.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSupport) {
            Intent intent = new Intent(AddLine.this, AddSupport.class);
            startActivity(intent);

        }else if (id == R.id.nav_addTowerMaintainance) {
            Intent intent = new Intent(AddLine.this, TM2.class);
            startActivity(intent);

            //////////////////////////////// PHM - SUb ////////////////////////////////////////////

        } else if (id == R.id.nav_addGantry) {
            Intent intent = new Intent(AddLine.this, AddGantry.class);
            startActivity(intent);

        } else if (id == R.id.nav_addBusBar) {
            Intent intent = new Intent(AddLine.this, AddBusBar.class);
            startActivity(intent);

        } else if (id == R.id.nav_addStructual) {
            Intent intent = new Intent(AddLine.this, AddStructural.class);
            startActivity(intent);

        } else if (id == R.id.nav_addGantryMore) {
            Intent intent = new Intent(AddLine.this, AddGantryMore.class);
            startActivity(intent);

        } else if (id == R.id.nav_addFeeder) {
            Intent intent = new Intent(AddLine.this, AddFeeder.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSwitches) {
            Intent intent = new Intent(AddLine.this, AddSwitches.class);
            startActivity(intent);

        } else if (id == R.id.nav_addSurge) {
            Intent intent = new Intent(AddLine.this, AddSurgeArrestors.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTransformersG) {
            Intent intent = new Intent(AddLine.this, AddTransformersG.class);
            startActivity(intent);

        }else if (id == R.id.nav_addEquipment) {
            Intent intent = new Intent(AddLine.this, AddEquipment.class);
            startActivity(intent);

///////////////////// POLE DETAILS //////////////////////////////////////////////

        } else if (id == R.id.nav_addMVPoles) {
            Intent intent = new Intent(AddLine.this, AddMVPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addPoles) {
            Intent intent = new Intent(AddLine.this, AddPoles.class);
            startActivity(intent);

        } else if (id == R.id.nav_addTowers) {
            Intent intent = new Intent(AddLine.this, AddTransformers.class);
            startActivity(intent);

        } else if (id == R.id.nav_Login) {
            Intent intent = new Intent(AddLine.this, Login.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//    public void onClick(View view) {
//        if(view == fromDateEtxt) {
//            fromDatePickerDialog.show();
//        }
//    }

    public List<String> readExcel() {
        System.out.println("readExcel");
        // Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
        List<String> resultSet = new ArrayList<String>();
        // Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

        System.out.println("readExcel1");
        File inputWorkbook = new File(Environment.getExternalStorageDirectory() + File.separator + "Schedule" + File.separator + "Area.xls");
        System.out.println("readExcel2");
        //  Toast.makeText(getApplication(),"readExcel2: " , Toast.LENGTH_SHORT).show();

        if (inputWorkbook.exists()) {
            System.out.println("readExcel3");
            //    Toast.makeText(getApplication(),"readExcel3: " , Toast.LENGTH_SHORT).show();

            Workbook w;
            try {
                //          System.out.println("readExcel4");

                w = Workbook.getWorkbook(inputWorkbook);
                System.out.println("readExce5");
                //        Toast.makeText(getApplication(),"readExcel4: " , Toast.LENGTH_SHORT).show();

                // Get the first sheet
                Sheet sheet = w.getSheet(0);
                System.out.println("readExce6");

                // Loop over column and lines
                int coloumn = sheet.getRows();
                values = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);
                    System.out.println(cell.getContents());
                    //          Toast.makeText(getApplication(),"readExcel5: " + cell.getContents(), Toast.LENGTH_SHORT).show();
                    spinnerMap.put(j, cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    values[j] = cell1.getContents();

                    //if(cell.getContents().equalsIgnoreCase(key)){
//                            for (int i = 0; i < sheet.getColumns(); i++) {
//                                Cell cel = sheet.getCell(i, j);
//                                Toast.makeText(getApplication(),"readExcel6: " + cel.getContents(), Toast.LENGTH_SHORT).show();
//
//                                resultSet.add(cel.getContents());
//                            }
                    //}
                    continue;
                }

                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerArea = findViewById(R.id.spinnerArea);
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


    public List<String> readExcelLoadtype() {
        System.out.println("readExcel");
        // Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
        List<String> resultSet = new ArrayList<String>();
        //  Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();

        System.out.println("readExcel1");
        File inputWorkbook = new File(Environment.getExternalStorageDirectory() + File.separator + "Schedule" + File.separator + "LineType.xls");
        System.out.println("readExcel2");
        // Toast.makeText(getApplication(),"readExcel2: " , Toast.LENGTH_SHORT).show();

        if (inputWorkbook.exists()) {
            System.out.println("readExcel3");
            //Toast.makeText(getApplication(),"readExcel3: " , Toast.LENGTH_SHORT).show();

            Workbook w;
            try {
                System.out.println("readExcel4");

                w = Workbook.getWorkbook(inputWorkbook);
                System.out.println("readExce5");
                //  Toast.makeText(getApplication(),"readExcel4: " , Toast.LENGTH_SHORT).show();

                // Get the first sheet
                Sheet sheet = w.getSheet(0);
                System.out.println("readExce6");

                // Loop over column and lines
                int coloumn = sheet.getRows();
                valuesType = new String[coloumn];

                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);
                    System.out.println(cell.getContents());
                    //    Toast.makeText(getApplication(),"readExcel5: " + cell.getContents(), Toast.LENGTH_SHORT).show();
                    spinnerMap1.put(j, cell.getContents());
                    Cell cell1 = sheet.getCell(1, j);
                    valuesType[j] = cell1.getContents();

                    //if(cell.getContents().equalsIgnoreCase(key)){
//                            for (int i = 0; i < sheet.getColumns(); i++) {
//                                Cell cel = sheet.getCell(i, j);
//                                Toast.makeText(getApplication(),"readExcel6: " + cel.getContents(), Toast.LENGTH_SHORT).show();
//
//                                resultSet.add(cel.getContents());
//                            }
                    //}
                    continue;
                }
                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesType);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerType = findViewById(R.id.SpinnerType);
                SpinnerType.setAdapter(adapterNs);


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




    public void createExcel() {
        LineID = findViewById(R.id.etLineID);
        Code = findViewById(R.id.etCode);
        Name = findViewById(R.id.etName);
        SpinnerType = findViewById(R.id.SpinnerType);
        Length = findViewById(R.id.etLength);
        SpinnerArea = findViewById(R.id.spinnerArea);
        NoOfPoles = findViewById(R.id.etNoOfPoles);
        NoOfTowers = findViewById(R.id.etNoOfTowers);
        ComDate = findViewById(R.id.in_date);

        //Conductor Type
        SpinnerConductorType = findViewById(R.id.spinnerConType);
        String conductorType = SpinnerConductorType.getSelectedItem().toString();

        //Feeder Identification
        SpinnerFeederIdentification = findViewById(R.id.spinnerFeeder);
        String feederIdentification = SpinnerFeederIdentification.getSelectedItem().toString();

        etFeeder = findViewById(R.id.etFeeder);
        String FeederEt = etFeeder.getText().toString();

        etConType = findViewById(R.id.etConType);
        String contypeEt = etConType.getText().toString();

        System.out.println("createExcel5");


        //Circuit type
        radioGroupCircuitType = findViewById(R.id.rgCircuitType);
        final int circuitType = radioGroupCircuitType.getCheckedRadioButtonId();
        System.out.println("radioGroupCircuitType" + radioGroupCircuitType);
        radioButtonCircuitType = findViewById(circuitType);
        String selectedCircuitType = radioButtonCircuitType.getText().toString();
        System.out.println("NoOfCircuitsButton" + radioButtonCircuitType);
        System.out.println("createExcel6");


        String lineid = LineID.getText().toString();
        String code = Code.getText().toString();
        String name = Name.getText().toString();
        String type = SpinnerType.getSelectedItem().toString();
        String length = Length.getText().toString();
        String Area = SpinnerArea.getSelectedItem().toString();
        String noOfPoles = NoOfPoles.getText().toString();
        String noOfTowers = NoOfTowers.getText().toString();
        String comdate1 = ComDate.getText().toString();



        try {

//            Toast.makeText(getApplication(),
//                    "DBHelper: " , Toast.LENGTH_SHORT).show();

            mydb2 = new DBHelper(this);

            if(conductorType.equals("Other")){

                conductorType = contypeEt;


            }

            if(feederIdentification.equals("Other")){

                feederIdentification = FeederEt;


            }

            String CircuitType = "";
            if (selectedCircuitType.equalsIgnoreCase("Single")) {
                CircuitType = "1";
            }
            else if (selectedCircuitType.equalsIgnoreCase("Double")) {
                CircuitType = "2";
            }
            else if (selectedCircuitType.equalsIgnoreCase("Four")) {
                CircuitType = "4";
            }

            String CircuitTypeStatus = "";
            if (selectedCircuitType.equalsIgnoreCase("")) {
                CircuitTypeStatus = "2";
            }


//            Toast.makeText(getApplication(),
//                   "DBHelper2: " , Toast.LENGTH_SHORT).show();
            //mydb2.onUpgrade(db,1,2);


            mydb2.insertAddLine(lineid, code, name, Type, length, area,
                    noOfPoles, noOfTowers, comdate1, ConType, CircuitType, feederIdentification);


//            Toast.makeText(getApplication(),
//                    "DBHelper3: " , Toast.LENGTH_SHORT).show();
            final Cursor res2 = mydb2.getAddLineData();

//            Toast.makeText(getApplication(),
//                  "DBHelper4: " , Toast.LENGTH_SHORT).show();

/*
            File storageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            + "/MVMMS");
*/
            File sd = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "Line");
            boolean success = true;
            if (!sd.exists()) {
                success = sd.mkdirs();
            }
            if (success) {
                // Do something on success
            } else {
                // Do something else on failure
            }

            // File sd = Environment.getExternalStorageDirectory();

            String csvFile = "Line.xls";

            File directory = new File(sd.getAbsolutePath());


            //create directory if not exist
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }

            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;

            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("AddLineList", 0);
            // column and row
            String[] columnName = res2.getColumnNames();
//
//            Toast.makeText(getApplication(),
//                  "columnName 1: " + columnName, Toast.LENGTH_SHORT).show();


            //if(columnName ==null) {
            //sheet.addCell(new Label(0, 0, "LineID"));
            sheet.addCell(new Label(0, 0, "Code"));
            sheet.addCell(new Label(1, 0, "Name"));
            sheet.addCell(new Label(2, 0, "Type"));
            sheet.addCell(new Label(3, 0, "Length"));
            sheet.addCell(new Label(4, 0, "Area"));
            sheet.addCell(new Label(5, 0, "No Of Poles"));
            sheet.addCell(new Label(6, 0, "No Of Towers"));
            sheet.addCell(new Label(7, 0, "Com Date"));
            sheet.addCell(new Label(8, 0, "Conductor Type"));
            sheet.addCell(new Label(9, 0, "Circuit Type"));
            sheet.addCell(new Label(10, 0, "Feeder Identification"));



//            Toast.makeText(getApplication(),
//                        "column created 1", Toast.LENGTH_SHORT).show();

            // sheet.addCell(new Label(1, 0, "phone"));
            //}
//            Toast.makeText(getApplication(),
//                    "res2" +res2, Toast.LENGTH_SHORT).show();

            if (res2.moveToFirst()) {

                do {
//                   Toast.makeText(getApplication(),
//                            "rrrr", Toast.LENGTH_SHORT).show();

                   // String lineidE = res2.getString(res2.getColumnIndex(DBHelper.ADDLINE_ID));
                    String codeE = res2.getString(res2.getColumnIndex(DBHelper.ADDLINE_CODE));
                    String nameE = res2.getString(res2.getColumnIndex(DBHelper.ADDLINE_NAME));
                    String typeE = res2.getString(res2.getColumnIndex(DBHelper.ADDLINE_TYPE));
                    String lengthE = res2.getString(res2.getColumnIndex(DBHelper.ADDLINE_LENGTH));
                    //String maindate = res2.getString(res2.getColumnIndex(DBHelper.TM_MAIN_DATE));
                    //String maindate = "";
                    String areaE = res2.getString(res2.getColumnIndex(DBHelper.ADDLINE_AREA));
                    String noofpolesE = res2.getString(res2.getColumnIndex(DBHelper.ADDLINE_NOOFPOLES));
                    String nooftowersE = res2.getString(res2.getColumnIndex(DBHelper.ADDLINE_NOOFTOWERS));
                    String comDateE = res2.getString(res2.getColumnIndex(DBHelper.ADDLINE_COMDATE));
                    String supportConductorTypeE = res2.getString(res2.getColumnIndex(DBHelper.ADDLINE_CONDUCTOR_TYPE));
                    String circuitTypeE = res2.getString(res2.getColumnIndex(DBHelper.ADDLINE_CIRCUIT_TYPE));
                    String feederIdentificationE = res2.getString(res2.getColumnIndex(DBHelper.ADDLINE_FEEDER_IDENTIFICATION));

//                   Toast.makeText(getApplication(),
//                            "nooftappings" +nooftappings, Toast.LENGTH_SHORT).show();

                    // String phoneNumber = res.getString(res.getColumnIndex("phone"));
//                    Toast.makeText(getApplication(),
//                            "rrrr 1", Toast.LENGTH_SHORT).show();
                    int i = res2.getPosition() + 1;
//                    Toast.makeText(getApplication(),
//                            "rrrr 2", Toast.LENGTH_SHORT).show();

                    //sheet.addCell(new Label(0, i, lineidE));
                    sheet.addCell(new Label(0, i, codeE));
                    sheet.addCell(new Label(1, i, nameE));
                    sheet.addCell(new Label(2, i, typeE));
                    sheet.addCell(new Label(3, i, lengthE));
                    //sheet.addCell(new Label(5, i, maindate));
                    sheet.addCell(new Label(4, i, areaE));
                    sheet.addCell(new Label(5, i, noofpolesE));
                    sheet.addCell(new Label(6, i, nooftowersE));
                    sheet.addCell(new Label(7, i, comDateE));
                    sheet.addCell(new Label(8, i, supportConductorTypeE));
                    sheet.addCell(new Label(9, i, circuitTypeE));
                    sheet.addCell(new Label(10, i, feederIdentificationE));

//                    Toast.makeText(getApplication(),
//                           "rrrr 3", Toast.LENGTH_SHORT).show();
                    //sheet.addCell(new Label(1, i, phoneNumber));
                } while (res2.moveToNext());
            }

            //closing cursor
            res2.close();
            workbook.write();
            workbook.close();
//            Toast.makeText(getApplication(),
//                    "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
        } catch (WriteException e) {
            e.printStackTrace();
            //Toast.makeText(getApplication(),
            //      " Error while generating excel sheet : ", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(getApplication(),
            //      "error while generating excel sheet: ", Toast.LENGTH_SHORT).show();
        }


//        Toast.makeText(AddLine.this, "Visit ID"  +VisitID.getText() +"weyleavrs status : " + wayleavingstatus ,
//                Toast.LENGTH_LONG).show();


        LineID.setText("");
        Code.setText("");
        Name.setText("");
        //SpinnerType.setText("");
        Length.setText("");
        //SpinnerArea.setText("");
        NoOfPoles.setText("");
        NoOfTowers.setText("");
        ComDate.setText("");

        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(AddLine.this);
        builder1.setTitle("MV-MMS");
        builder1.setIcon(R.drawable.ceb_logo2);
        builder1.setMessage("Data Saved in Excel Sheet")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        android.app.AlertDialog alert = builder1.create();
//        alert.show();
        progressSaving();


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    //}
    private class SaveLine extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            System.out.println(" doInBackground " );
            LineID = findViewById(R.id.etLineID);
            Code = findViewById(R.id.etCode);
            Name = findViewById(R.id.etName);
            SpinnerType = findViewById(R.id.SpinnerType);
            Length = findViewById(R.id.etLength);
            SpinnerArea = findViewById(R.id.spinnerArea);
            NoOfPoles = findViewById(R.id.etNoOfPoles);
            NoOfTowers = findViewById(R.id.etNoOfTowers);
            ComDate = findViewById(R.id.in_date);

            //Conductor type
            SpinnerConductorType = findViewById(R.id.spinnerConType);
            String conductorType = SpinnerConductorType.getSelectedItem().toString();
            etConType = findViewById(R.id.etConType);
            String contypeEt = etConType.getText().toString();

            //Circuit type
            radioGroupCircuitType = findViewById(R.id.rgCircuitType);
            final int radioButtonId = radioGroupCircuitType.getCheckedRadioButtonId();
            System.out.println("radioGroupCircuitType" + radioGroupCircuitType);
            radioButtonCircuitType = findViewById(radioButtonId);
            String selectedCircuitType = radioButtonCircuitType.getText().toString();

            radioGrouLineGroup = findViewById(R.id.rdb_type);
            final int radioGroupLine = radioGrouLineGroup.getCheckedRadioButtonId();
            Lv = findViewById(radioGroupLine);
            String selectedLineType = Lv.getText().toString();

            System.out.println("OooooooooooooooooooooooooooOOOoooOOOOOOOOOOOOOOOOooo"+selectedLineType);

            int typeLVMV;
            if(selectedCircuitType == "MV"){
                typeLVMV=0;
            }else{
                typeLVMV=1;
            }
            System.out.println("YyyyyyyyyyyyyyyyyYYYYYYYYYYYYYYYYYYYyyyyyyyyyyyyyyyY"+typeLVMV);


            System.out.println("radioButtonCircuitType" + radioButtonCircuitType);
            System.out.println("createExcel9");


            String lineid = LineID.getText().toString();

            String code = Code.getText().toString();
            code = code.replace("/", "-");

            String name = Name.getText().toString();
            name = name.replace("/", "-");

            String type = SpinnerType.getSelectedItem().toString();
            //type=type.substring(0,1);

            String length = Length.getText().toString();

          //  String area = SpinnerArea.getSelectedItem().toString();
           // area=area.substring(0,6);


            String noOfPoles = NoOfPoles.getText().toString();
            //Toast.makeText(getApplication(),
              //      "Sub string"+noOfPoles, Toast.LENGTH_SHORT).show();
            String noOfTowers = NoOfTowers.getText().toString();
            //Toast.makeText(getApplication(),
              //      "Sub string"+noOfTowers, Toast.LENGTH_SHORT).show();
            String comdate1  = ComDate.getText().toString();
            //Toast.makeText(getApplication(),
              //      "Sub string"+ComDate, Toast.LENGTH_SHORT).show();

            if (conductorType.equals("Other")) {

                conductorType = contypeEt;

            }

            String CircuitType = "";
            if (selectedCircuitType.equalsIgnoreCase("Single")) {
                CircuitType = "1";
            }
            else if(selectedCircuitType.equalsIgnoreCase("Double")){
                CircuitType = "2";
            }else if(selectedCircuitType.equalsIgnoreCase("Four")){
                CircuitType = "4";
            }else{
                CircuitType = "3";
            }
            System.out.println("selectedNoOfCircuitsRadio" + CircuitType);


            //get PhmBranch
            SessionManager obj = new SessionManager(getBaseContext());
            String PhmBranch = obj.getPhmBranch();
            String userName = obj.getUserName();
            System.out.println("PhmBranch" + PhmBranch.trim());
            PhmBranch = PhmBranch.trim();

            //set values to MmsAddline object
            MmsAddline objAddLine = new MmsAddline();
            //objAddLine.setId(lineid);
            objAddLine.setCode(code);
            objAddLine.setArea(area);
            objAddLine.setLineName(name);
            objAddLine.setLineType(Type);
            objAddLine.setType(new BigDecimal(typeLVMV));
            objAddLine.setLength(new BigDecimal(length));
            objAddLine.setNoofpoles(new BigDecimal(noOfPoles));
            objAddLine.setNooftowers(new BigDecimal(noOfTowers));
            objAddLine.setPhmBranch(PhmBranch);
            //Date date = null;
            //objAddLine.setComdate(date);
            objAddLine.setConductorType(new BigDecimal(ConType));
            objAddLine.setCircuitType(new BigDecimal(CircuitType));
            objAddLine.setFeederIdentification(feeder);
            Date dateNow = null;
            try {
                dateNow = new SimpleDateFormat("yyyy-MM-dd").parse(comdate1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objAddLine.setComdate(dateNow);
            objAddLine.setStatus(new BigDecimal(2));
            objAddLine.setEntBy(userName);

       //   try {
         //       DateFormat format = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
           //     date = format.parse(String.valueOf(ComDate));
           //}catch(Exception e){

            //}


            //set objAddLine to MmsModel
            //MmsAddline lineObj = new MmsAddline();
           // mmsModel.setMmsAddline(objAddLine);
          //  String str = mmsModel.toString();


            //feeder = EnterFeeder.getText().toString();
            //System.out.println("lineid:****************************** " + objAddLine.getId());
            System.out.println("code:****************************** " + objAddLine.getCode());
            System.out.println("area:****************************** " + objAddLine.getArea());
            System.out.println("name:****************************** " + objAddLine.getName());
            System.out.println("Type:****************************** " + objAddLine.getLineType());
            System.out.println("length:****************************** " + objAddLine.getLength());
            System.out.println("noOfPoles:****************************** " + objAddLine.getNoofpoles());
            System.out.println("noOfTowers:****************************** " + objAddLine.getNooftowers());
            System.out.println("PhmBranch:****************************** " + objAddLine.getPhmBranch());
            System.out.println("Date:****************************** " + objAddLine.getComdate());
            System.out.println("ConType:****************************** " + objAddLine.getConductorType());
            System.out.println("CircuitType:****************************** " + objAddLine.getCircuitType());
            System.out.println("Feeder:****************************** " + objAddLine.getFeederIdentification());
            System.out.println("Status:****************************** " + objAddLine.getStatus());
            System.out.println("EntBy:****************************** " + objAddLine.getEntBy());
            System.out.println("model:****************************** " + objAddLine);

            final RestTemplate restTemplate = new RestTemplate();
//            System.out.println(" areaCode " + areaCode);
            //final String url1 = Util.SRT_URL + "addLineDBNewNew/" + code + "/" + area + "/" + name + "/" + Type + "/" + length + "/" + noOfPoles + "/" + noOfTowers + "/" + PhmBranch + "/"+comdate1+ "/" + ConType + "/" + CircuitType + "/" + feeder + "/";
            //final String url1 = Util.SRT_URL + "addLineDBNewNew/" + objAddLine + "/";
            final String url1 = Util.SRT_URL + "MMSAddLineMobile/";
            System.out.println(" url1 " + url1);
            // trustEveryone();
            System.out.println(" ...........................url1tttttttttttttt " );
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println(" url1ttttttttttttttgggggg " );
            //return restTemplate.getForObject(url1, String.class);
            String objReturn = restTemplate.postForObject(url1, objAddLine, String.class);
            System.out.println(" objReturn: " + objReturn );
            return objReturn;

        }


        @Override
        protected void onPostExecute(String result) {
            try {

               System.out.println("hiii5555 yyyyyyyyy" + result);
               // DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
               // InputStream is = new ByteArrayInputStream(result.getBytes("UTF-8"));
              //  Document dom = builder.parse(is);
              //  final String token = dom.getElementsByTagName("string").item(0).getChildNodes().item(0).getTextContent();

                //android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(AddComplain.this);
               // builder1.setIcon(R.drawable.logo);
               // builder1.setMessage("Complaint successfully saved.Your reference number is: "+ token)
                     //   .setCancelable(false)
                     //   .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           // public void onClick(DialogInterface dialog, int id) {
                                //do things
                      //      }
                       // });
                //android.app.AlertDialog alert = builder1.create();
               // alert.show();



//
            } catch (Exception e) {
                System.out.println(" error " + e.getMessage());
            }


        }
    }

    @Override
    public void onClick(View v) {

        if (v == btn_date) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(AddLine.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            in_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

    }

    private class loadarea extends AsyncTask<String, Void, MmsAddArea[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        protected MmsAddArea[] doInBackground(String... urls) {


            //get deptId from session manager
            SessionManager objS = new SessionManager(getBaseContext());
            String deptId = objS.getPhmBranch();
            System.out.println("****************************************PhmBranch" + deptId.trim());
            deptId = deptId.trim();


            RestTemplate rest = new RestTemplate();
            //String url6 = Util.SRT_URL+"findAllAreaNew";
            String url6 = Util.SRT_URL+"findAllAreaNewMobile/" + deptId + "/";

            System.out.println("ssss" +url6);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url6,MmsAddArea[].class);
            //return List<MmsAddArea>null;
        }


        protected void onPostExecute(MmsAddArea[] results) {
            // ListView Item Click Listener
            System.out.println("results" +results);
            System.out.println("results" +results.length);
            String[] area;
            values = new String[results.length];
            //  String[] spinnerArray = new String[Province_ID.size()];
            //  HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
            //  for (int i = 0; i < Province_ID.size(); i++)
            //  {
            //      spinnerMap.put(i,Province_ID.get(i));
            //      spinnerArray[i] = Province_NAME.get(i);
            //  }


//
            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    MmsAddArea  obj = results[c];
                    values[c] = obj.getDeptNm();
                    spinnerMap.put(c,obj.getDeptId());
                    //String deptId = spinnerMap.put(c,obj.getDeptId());


                }
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerArea = findViewById(R.id.spinnerArea);
            SpinnerArea.setAdapter(adapterNs);
//





            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }

    private class loadtype extends AsyncTask<String, Void, MmsAddlinetype[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        protected MmsAddlinetype[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url8 = Util.SRT_URL+"findActiveLineTypes";

            System.out.println("ssss" +url8);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url8,MmsAddlinetype[].class);
            //return List<MmsAddArea>null;
        }


        protected void onPostExecute(MmsAddlinetype[] results) {
            // ListView Item Click Listener
            System.out.println("results" +results);
            System.out.println("results" +results.length);
            String[] area;
            valuesType = new String[results.length];
            //  String[] spinnerArray = new String[Province_ID.size()];
            //  HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
            //  for (int i = 0; i < Province_ID.size(); i++)
            //  {
            //      spinnerMap.put(i,Province_ID.get(i));
            //      spinnerArray[i] = Province_NAME.get(i);
            //  }


//
            if(results != null){
                int count =  results.length -1;
                for(int c =0; c <=count; c++){
                    MmsAddlinetype  obj = results[c];
                    valuesType[c] = obj.getLineType();
                    spinnerMap1.put(c,obj.getId());


                }
            }

            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesType);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerType = findViewById(R.id.SpinnerType);
            SpinnerType.setAdapter(adapterNs);
//





            //ProgDialog.dismiss();
            //Toast.makeText(AddSupport.this, " Successfully Saved. " , Toast.LENGTH_LONG).show();


        }

    }


    private class loadConType extends AsyncTask<String, Void, MmsAddconductortype[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        protected MmsAddconductortype[] doInBackground(String... urls) {
            RestTemplate rest = new RestTemplate();
            String url8 = Util.SRT_URL+"findActiveConductorTypes";

            System.out.println("ssss" +url8);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return rest.getForObject(url8,MmsAddconductortype[].class);
            //    return List<MmsAddArea>null;
        }


        protected void onPostExecute(MmsAddconductortype[] results) {
            // ListView Item Click Listener
            System.out.println("results" +results);
            System.out.println("results" +results.length);
            String[] ConType;
            valuesConType = new String[results.length];


            if(results != null) {
                int count = results.length - 1;
                for (int c = 0; c <= count; c++) {
                    MmsAddconductortype obj = results[c];
                    System.out.println("resultsyyyyyyywer :" + c);
                    if (obj != null) {
                        valuesConType[c] = String.valueOf(obj.getType());
                        spinnerMap2.put(c, obj.getId());

                        System.out.println("resultsyyyyyyywergh " );
                    }
                }
                System.out.println("resultsyyyyyyywerh " );
            }


            ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, valuesConType);
            adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SpinnerConductorType = findViewById(R.id.spinnerConType);
            SpinnerConductorType.setAdapter(adapterNs);


        }

    }




    private class SaveLineFromExcel extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
        String result = "";
            System.out.println(" SaveLineFromExcel1 " );
            MmsAddline objNew = new MmsAddline();
            objNew.setLineType("1");
            objNew.setArea("439.00");
            objNew.setLength(new BigDecimal("88"));
            objNew.setCode("test");
            objNew.setNooftowers(new BigDecimal("33"));
            objNew.setNoofpoles(new BigDecimal("22"));
            objNew.setName("test");
            objNew.setComdate(new Date());
            objNew.setConductorType(new BigDecimal("3"));
            objNew.setCircuitType(new BigDecimal("2"));

            SessionManager objS = new SessionManager(getBaseContext());
            String PhmBranch = objS.getPhmBranch();
            System.out.println("PhmBranch" + PhmBranch.trim());
            PhmBranch = PhmBranch.trim();


           List<MmsAddline> obj = readLine();
           // List<MmsAddline> obj = new ArrayList<MmsAddline>(1);
            //obj.add(objNew);
            if(obj != null){
                int size = obj.size();
                System.out.println("size*****************************: " + size);

                for(int i = 0;i<size;i++){

                    MmsAddline line = obj.get(i);

                    final RestTemplate restTemplate = new RestTemplate();
                    //final String url1 = Util.SRT_URL + "addLineDB/" + line.getCode() +"/"+ line.getArea() +"/" + line.getName() +"/" + line.getLineType() +"/"+ line.getLength() +"/"+ line.getNoofpoles() +"/"+ line.getNooftowers()  + "/" + PhmBranch+ "/12-12-2018/" + line.getConductorType() + "/" + PhmBranch + line.getCircuitType()+"/" + PhmBranch + "/";
                    //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    //result= restTemplate.getForObject(url1, String.class);

                    final String url1 = Util.SRT_URL + "MMSAddLineMobile";
                    System.out.println("url1*****************************: " + url1);
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    String objReturn = restTemplate.postForObject(url1, line, String.class);
                    System.out.println("objReturn*****************************: " + objReturn);


                    return objReturn;


                }
            } else{
                System.out.println(" .list is empty " );
                result = " .list is empty ";

            }
            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            try {

                System.out.println("hiii5555 yyyyyyyyy" + result);

            } catch (Exception e) {
                System.out.println(" error " + e.getMessage());
            }


        }

    }



    public List<MmsAddline> readLine() {
        System.out.println("readExcel");
        List<MmsAddline> obj=null;
        // Toast.makeText(getApplication(),"readExcel: " , Toast.LENGTH_SHORT).show();
        List<String> resultSet = new ArrayList<String>();
       //  Toast.makeText(getApplication(),"readExcel1: " , Toast.LENGTH_SHORT).show();
        System.out.println("readExcel1");
        File inputWorkbook = new File(Environment.getExternalStorageDirectory() + File.separator + "Line" + File.separator + "Line.xls");
        System.out.println("readExcel2");
        //  Toast.makeText(getApplication(),"readExcel2: " , Toast.LENGTH_SHORT).show();

        if (inputWorkbook.exists()) {
            System.out.println("readExcel3");
            //    Toast.makeText(getApplication(),"readExcel3: " , Toast.LENGTH_SHORT).show();

            Workbook w;
            try {
                //          System.out.println("readExcel4");

                w = Workbook.getWorkbook(inputWorkbook);
                System.out.println("readExcel4");
                //        Toast.makeText(getApplication(),"readExcel4: " , Toast.LENGTH_SHORT).show();

                // Get the first sheet
                Sheet sheet = w.getSheet(0);
                System.out.println("readExcel5");

                // Loop over column and lines
                int coloumn = sheet.getRows();
                //values = new String[coloumn];
                obj = new ArrayList<MmsAddline>(coloumn);


                for (int j = 1; j < sheet.getRows(); j++) {

                    MmsAddline objLine = new MmsAddline();

                  //  Cell cell = sheet.getCell(0, j);
                  //  System.out.println(cell.getContents());
                    //Toast.makeText(getApplication(),"readExcelLine: " + cell.getContents(), Toast.LENGTH_SHORT).show();
                    // spinnerMap.put(j, cell.getContents());

                    Cell cell1 = sheet.getCell(0, j);
                    System.out.println("code: " + cell1.getContents());
                    String code = cell1.getContents();
                    objLine.setCode(code);
                    //Toast.makeText(getApplication(),"Code: " + cell1.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell2 = sheet.getCell(1, j);
                    System.out.println("name: " + cell2.getContents());
                    String name =cell2.getContents();
                    objLine.setLineName(name);
                    //Toast.makeText(getApplication(),"Name: " + cell2.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell3 = sheet.getCell(2, j);
                    System.out.println("type: " + cell3.getContents());
                    String Type =cell3.getContents();
                    objLine.setLineType(Type);
                    //Toast.makeText(getApplication(),"Type: " + cell3.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell4 = sheet.getCell(3, j);
                    System.out.println("length: " + cell4.getContents());
                    String Length =cell4.getContents();
                    objLine.setLength(new BigDecimal(Length));
                    //Toast.makeText(getApplication(),"Length: " + cell4.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell5 = sheet.getCell(4, j);
                    System.out.println("area: " + cell5.getContents());
                    String Area =cell5.getContents();
                    objLine.setArea(Area);
                    //Toast.makeText(getApplication(),"Area: " + cell5.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell6 = sheet.getCell(5, j);
                    System.out.println("no of poles: " + cell6.getContents());
                    String NoofPoles =cell6.getContents();
                    objLine.setNoofpoles(new BigDecimal(NoofPoles));
                    //Toast.makeText(getApplication(),"No of Poles: " + cell6.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell7 = sheet.getCell(6, j);
                    System.out.println("no of towers: " + cell7.getContents());
                    String NoofTower =cell7.getContents();
                    objLine.setNooftowers(new BigDecimal(NoofTower));
                    //Toast.makeText(getApplication(),"NoofTower: " + cell7.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell8 = sheet.getCell(7, j);
                    System.out.println("com date: " + cell8.getContents());
                    String ComDate = cell8.getContents();

                    //DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateNowNew = new SimpleDateFormat("yyyy-MM-dd").parse(ComDate);
                    objLine.setComdate(dateNowNew);
                    //Toast.makeText(getApplication(),"ComDate: " + cell8.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell9 = sheet.getCell(8, j);
                    System.out.println("conduc typr: " + cell9.getContents());
                    String ConType = cell9.getContents();
                    objLine.setConductorType(new BigDecimal(ConType));
                    //Toast.makeText(getApplication(),"ConType: " + cell9.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell10 = sheet.getCell(9, j);
                    System.out.println("circuit type: " + cell10.getContents());
                    String CircuitType = cell10.getContents();
                    objLine.setCircuitType(new BigDecimal(CircuitType));
                    //Toast.makeText(getApplication(),"CircuitType: " + cell10.getContents(), Toast.LENGTH_SHORT).show();

                    Cell cell11 = sheet.getCell(10, j);
                    System.out.println("feeder: " + cell11.getContents());
                    String feeder = cell11.getContents();
                    objLine.setFeederIdentification(feeder);
                    objLine.setStatus(new BigDecimal(2));

                    //get PhmBranch
                    SessionManager objSess = new SessionManager(getBaseContext());
                    String PhmBranch = objSess.getPhmBranch();
                    String userName = objSess.getUserName();
                    System.out.println("PhmBranch" + PhmBranch.trim());
                    PhmBranch = PhmBranch.trim();

                    objLine.setPhmBranch(PhmBranch);
                    objLine.setEntBy(userName);

                    obj.add(objLine);

                    continue;
                }

/*
                ArrayAdapter<String> adapterNs = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values);
                adapterNs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerArea = (Spinner) findViewById(R.id.spinnerArea);
                SpinnerArea.setAdapter(adapterNs);
*/

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
        return obj;
    }





}









