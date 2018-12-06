package com.byt_eye.tcadmin;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private String MyPREFERENCES = "notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.hide();

        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        editor.putString("key", "1");
        editor.commit();


        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long l) {

            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFinish() {

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                finish();

            }
        }.start();

    }
}

