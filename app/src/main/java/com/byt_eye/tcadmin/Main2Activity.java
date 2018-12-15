package com.byt_eye.tcadmin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.byt_eye.tcadmin.utils.DailyService;
import com.evernote.android.job.JobManager;

import java.util.Calendar;


public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Invitation";
    ViewPager pager;
    SharedPreferences.Editor editor;
    SharedPreferences uPreferences;
    TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        DailyService.initDailyService(this, true);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);


        try {

            pager = (ViewPager) findViewById(R.id.address_viewPager);
            FragmentManager fm = getSupportFragmentManager();
            PagerAdapter adapter = new PagerAdapter(fm);
            pager.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you want to exit ?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }



}
