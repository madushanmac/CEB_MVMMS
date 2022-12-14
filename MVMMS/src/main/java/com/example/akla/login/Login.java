package com.example.akla.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Login extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 3000;

    Button buttonLogin;
    EditText userName;
    EditText password;
    private String values[] = new String[]{};
    private ProgressDialog ProgDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        if(!isConnected(Login.this)){
            buildDialog(Login.this).show();
        }


        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        userName=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.getText().toString().trim().equals("")) {
                    userName.setError("Enter User Name");
                } else if (password.getText().toString().trim().equals("")) {
                    password.setError("Enter password");
                } else {
                    if(!isConnected(Login.this)){

                        buildDialog(Login.this).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);

                    }else {

                        //startActivity(new Intent(Login.this, MainActivity.class));
                        new LoginUser().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                  //  Intent intent = new Intent(Login.this, MainActivity.class);
     // intent.putExtra("string-array", values);
       //         startActivity(intent);

                }
            }
        });
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
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


    private class LoginUser extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ProgDialog= new ProgressDialog(Login.this);
//message should be changed according to the requirement
            ProgDialog.setMessage("Logging In. Please wait...\n(This may take some time, depending on your network connection)");
            ProgDialog.setIndeterminate(false);
            ProgDialog.setTitle(Util.alert_header);
            ProgDialog.setIcon(R.drawable.logo);
            ProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            ProgDialog.setCancelable(true);
            ProgDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String uName = userName.getText().toString();
            String pw = password.getText().toString();

            System.out.println("................................" + uName);
            System.out.println("................................" + pw);
           ServiceHelper.trustEveryone();
          //  final RestTemplate restTemplate = new RestTemplate();
            //final String url1 = Util.URL + "userLogin?access_token=" + Util.Access_Token + "&userName=" + uName + "&passWd=" + pw;
            //System.out.println("hiii5555 yyy1" + url1);
            //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //System.out.println("hiii5555 str" + restTemplate.getForObject(url1, String.class));
            //return restTemplate.getForObject(url1, String.class);

            final RestTemplate restTemplate = new RestTemplate();
            final String url1 = Util.SRT_URL + "loginnew?userName=" + uName + "&password=" + pw;
            System.out.println("hiii5555 yyy1" + url1);

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            System.out.println("hiii5555 str" + restTemplate.getForObject(url1, String.class));
            return restTemplate.getForObject(url1, String.class);
        }


        @Override
        protected void onPostExecute(String result) {
            System.out.println("resultLoginUser " + result);
            String uName = userName.getText().toString();


            if (!result.equalsIgnoreCase("N")){
               Intent intent = new Intent(Login.this, MainActivity.class);

               SessionManager obj = new SessionManager(getBaseContext());
               obj.createPhmBranch(result);
               obj.createUserName(uName);
               //intent.putExtra("key", values);
               startActivity(intent);

           }else{
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Login.this);
               builder.setMessage("Invalid Username or Password!\nPlease enter a valid username and password.")
                       .setCancelable(false)

                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               //do things String uName = userName.getText().toString();
//                                    userName.setText("");
                               // userName.setFocusable(true);
//                                    password.setText("");
                               Intent intent = new Intent(Login.this, Login.class);
                               startActivity(intent);
                           }
                       });
               builder.setIcon(R.drawable.logo);
               builder.setTitle("Login Error");
               android.app.AlertDialog alert = builder.create();
               alert.show();
           }

           // ProgDialog.dismiss();


        }
    }
    public boolean isConnected(Context context) {

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

    public android.app.AlertDialog.Builder buildDialog(Context c) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please check your internet connection and try again or Do you want to continue as OFFLINE");
        builder.setIcon(R.drawable.logo);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Login.this, MainActivity.class);
                //intent.putExtra("key", values);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
    public void progressSaving(){
        //Show the dialog
        final ProgressDialog dialog= ProgressDialog.show(this,"Loading Data", "Please wait....",true);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    dialog.dismiss();
                }
                catch(InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        }).start();
    }

/*
    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            String status = Util.getConnectivityStatusString(context);

            Toast.makeText(context, status, Toast.LENGTH_LONG).show();
        }
    }*/


    }




