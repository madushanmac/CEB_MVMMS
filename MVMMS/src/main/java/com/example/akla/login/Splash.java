package com.example.akla.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Splash extends Activity {
    private Handler handler;
    private Handler handler1;
    private TextView textView;
    private TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView = (TextView) findViewById(R.id.tv);
        textView1 = (TextView) findViewById(R.id.tv2);
        handler = new Handler();
        handler1 = new Handler();

        fade(textView);
        fade(textView1);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                
                Intent mainIntent = new Intent(Splash.this,Login.class);
                startActivity(mainIntent);
                finish();
            }
        },3000L);
    }
    private Runnable updateTextRunnable = new Runnable() {
        public void run() {
            String message = "MY CEB";
            textView.setText(message);
            handler.postDelayed(this, 1000);
        }
    };

    private Runnable updateTextRunnable1 = new Runnable() {
        public void run() {
            String message = "Enriching The Power";
            textView1.setText(message);
            handler1.postDelayed(this, 1000);
        }
    };

    public void onResume() {
        super.onResume();
        handler.postDelayed(updateTextRunnable, 1000);
        handler1.postDelayed(updateTextRunnable1, 1000);
    }

    public void fade(View view){
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade);
        textView.startAnimation(animation1);
        textView1.startAnimation(animation1);
    }
}


