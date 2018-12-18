package com.byt_eye.tcadmin.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import com.byt_eye.tcadmin.landing_page.MainActivity;
import com.byt_eye.tcadmin.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


/**
 * Created by ca6 on 8/3/18.
 */


public class NotificationUtil {

    public static String POSITIVE_CLICK = "POSITIVE_CLICK";
    public static String NEGATIVE_CLICK = "NEGATIVE_CLICK";
    public static String REPLY_TEXT_KEY = "REPLY_TEXT_KEY";
    public static String GROUP_KEY = "GROUP_KEY";
    private static String notification_title;
    private static String des;
    private static Context ctx;


    public static void showCustomBigPictureNotification(Context context, String title, String description, final String image_link) {
        ctx = context;
        notification_title = title;
        des = description;

        TestAsync testAsync = new TestAsync();
        testAsync.execute(image_link);
    }


    static class TestAsync extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);


            int notificationId = new Random().nextInt();
            String channelId = "channel-01";
            String channelName = "GPA Offers";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                mNotificationManager.createNotificationChannel(mChannel);
            }


            NotificationCompat.BigPictureStyle bpStyle = new NotificationCompat.BigPictureStyle();
            bpStyle.bigPicture(bitmap).setBigContentTitle(notification_title).setSummaryText(des).build();

            Intent notificationIntent = new Intent(ctx, MainActivity.class).putExtra("notificationUri", "http://cosmo.adz/offer?offer_id=qKjdaYGr3ZB4QR8X");

            PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx, channelId)
                    .setContentTitle(notification_title)
                    .setContentText(des)
                    .setSmallIcon(R.drawable.logo)
                    .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_launcher))
                    .setStyle(bpStyle);

            mBuilder.setAutoCancel(true);

            mBuilder.setContentIntent(contentIntent);

            mNotificationManager.notify(notificationId, mBuilder.build());
        }


    }
}





