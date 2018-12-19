package com.byt_eye.tcadmin.listeners;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;

import com.byt_eye.tcadmin.crowl.HandleXML;
import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.data.DbOpenHelper;
import com.byt_eye.tcadmin.modals.Post;
import com.byt_eye.tcadmin.modals.RssFeedModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RssPullService extends JobIntentService {

    Context context;
    DbOpenHelper dbHelper;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    public String mFeedTitle;
    public String mFeedLink;
    public String mFeedDescription;
    public String mFeedImageUrl;
    public String mFeedUrl;
    public String mReadStatus;
    public String mPublishedTime;

    static final int SERVICE_JOB_ID = 1050;

    URL url;
    InputStream inputStream;
    HandleXML handleXML;
    private List<String> totalWebsitesList;
    private List<String> encodedWebsitesList;
    private DatabaseReference mDatabase;
    final String TAG = "MyJobIntenetService";


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d("Rss Service", " : Started");
        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("Rss Service", " : Started");

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                mDatabase = database.getReference().child("home").child("News").child("Languages").child("Telugu").child("Cat1");

                context = getApplicationContext();
                dbHelper = new DbOpenHelper(context);

                String[] websitesFeedsArray = getResources().getStringArray(R.array.telugu_websites_feed_links);
                String[] encodedWebsites = getResources().getStringArray(R.array.encoded_websites);

                totalWebsitesList = Arrays.asList(websitesFeedsArray);
                encodedWebsitesList = Arrays.asList(encodedWebsites);

                sharedpreferences = context.getSharedPreferences("lastExecutePref", Context.MODE_PRIVATE);
                editor = sharedpreferences.edit();

                int count = sharedpreferences.getInt("lastExecutedUrlNo", 0);
                if (count == totalWebsitesList.size() - 1) {
                    editor.putInt("lastExecutedUrlNo", 0);
                    editor.commit();
                    count = 0;
                }

                while (count < totalWebsitesList.size()) {  //30

                    String urlLink = totalWebsitesList.get(count);

                    if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://")) {
                        urlLink = "http://" + urlLink;
                    }

                    editor.putInt("lastExecutedUrlNo", count);
                    editor.commit();

                    try {
                        url = new URL(urlLink);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    try {
                        inputStream = url.openConnection().getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    if (encodedWebsitesList.contains(urlLink)) {
                        handleXML = new HandleXML(mFeedTitle, mFeedLink, mFeedDescription, mFeedUrl, mPublishedTime, mReadStatus, mFeedImageUrl, 1);
                    } else {
                        handleXML = new HandleXML(mFeedTitle, mFeedLink, mFeedDescription, mFeedUrl, mPublishedTime, mReadStatus, mFeedImageUrl, 0);
                    }

                    ArrayList<RssFeedModel> mFeedModelList = null;
                    try {
                        mFeedModelList = handleXML.parseFeed(inputStream);
                    } catch (XmlPullParserException | IOException e) {
                        e.printStackTrace();
                    }


                    if (mFeedModelList.size() != 0) {
                        int j = 0;
                        while (j < mFeedModelList.size()) {
                            try {
                                RssFeedModel data = dbHelper.findLink(mFeedModelList.get(j).link);
                                if (data == null) {
                                    dbHelper.addNews(mFeedModelList.get(j).title,
                                            mFeedModelList.get(j).link, mFeedModelList.get(j).imageUrl,
                                            mFeedModelList.get(j).publishedTime, mFeedModelList.get(j).description, mFeedModelList.get(j).readStatus);
                                    setDataIntoFirebase(mFeedModelList.get(j).title, mFeedModelList.get(j).link, mFeedModelList.get(j).imageUrl, mFeedModelList.get(j).description);
                                    count++;
                                } else {
                                    count++;
                                    break;
                                }
                                j++;
                            } catch (Exception e) {
                                count = count + 1;
                            }
                        }
                    } else {
                        count = count + 1;
                    }
                }
            }
        }).start();

    }


    private void setDataIntoFirebase(String title, String link, String imageUrl, String description) {
        //creating the post object to store uploaded image details
        String uploadId = mDatabase.push().getKey();
        Post post = new Post(title, imageUrl, link, "0", settingCurrentTime(), uploadId, description);
        mDatabase.child(uploadId).setValue(post);

    }


    public String settingCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return sdf.format(calendar.getTime());
    }


    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, RssPullService.class, SERVICE_JOB_ID, intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.wtf(TAG, "All work complete");
        toast("All work complete");
    }

    final Handler mHandler = new Handler();

    // Helper for showing tests
    void toast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RssPullService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}


