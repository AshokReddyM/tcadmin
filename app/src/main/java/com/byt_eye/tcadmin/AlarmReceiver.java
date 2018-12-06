package com.byt_eye.tcadmin;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    Context context;
    DBHandler dbHelper;
    String[] websitesFeedsArray;
    NotificationManager notificationManager;
    Intent repeatingIntent;
    Uri alarmSound;
    String mNotificationTitle;
    String mNotificationDesc;
    String mImageUrl;
    PendingIntent pendingIntent;
    String alarm_status;
    SharedPreferences sharedpreferences;
    private String MyPREFERENCES = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        dbHelper = new DBHandler(context);

        Log.i("AlarmReceiver", "onReceive");
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        alarm_status = sharedpreferences.getString("key", null);

        try {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            repeatingIntent = new Intent(context, Main2Activity.class);
            repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            pendingIntent = PendingIntent.getBroadcast(context, 100, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            websitesFeedsArray = context.getResources().getStringArray(R.array.telugu_websites_feed_links);
            context.startService(new Intent(context, RssPullService.class));
            showNotification();

        } catch (Exception e) {
            e.printStackTrace();
        }


        ComponentName comp = new ComponentName(context.getPackageName(), RssPullService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }


    public void showNotification() {

        if (alarm_status.equals("2")) {


            mNotificationTitle = dbHelper.getTopRow().getTitle();
            mNotificationDesc = dbHelper.getTopRow().getDescription();
            mImageUrl = dbHelper.getTopRow().getWebsiteName();

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager notif = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify = new Notification.Builder(context)
                    .setContentTitle("టాలీవుడ్ న్యూస్")
                    .setAutoCancel(true)
                    .setLights(Color.BLUE, 500, 500)
                    .setLargeIcon(getBitmapFromURL(mImageUrl))
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentText(mNotificationTitle)
                    .setSmallIcon(R.drawable.ic_website)
                    .setTicker(mNotificationTitle)
                    .setColor(Color.RED)
                    .setContentIntent(contentIntent)
                    .setStyle(new Notification.BigTextStyle().bigText(mNotificationTitle))
                    .build();

            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            notif.notify(0, notify);
        } else {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            editor.putString("key", "2");
            editor.commit();

        }
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Bitmap dstBmp = null;

            if (myBitmap.getWidth() >= myBitmap.getHeight()) {

                dstBmp = Bitmap.createBitmap(
                        myBitmap,
                        myBitmap.getWidth() / 2 - myBitmap.getHeight() / 2,
                        0,
                        myBitmap.getHeight(),
                        myBitmap.getHeight()
                );

            } else {

                dstBmp = Bitmap.createBitmap(
                        myBitmap,
                        0,
                        myBitmap.getHeight() / 2 - myBitmap.getWidth() / 2,
                        myBitmap.getWidth(),
                        myBitmap.getWidth()
                );
            }


            return dstBmp;


        } catch (IOException e) {
            // Log exception
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
            return largeIcon;
        }
    }


}

