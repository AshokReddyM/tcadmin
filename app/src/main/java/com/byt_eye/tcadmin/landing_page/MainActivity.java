package com.byt_eye.tcadmin.landing_page;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.new_list.NewsListActivity;
import com.byt_eye.tcadmin.websites.activity.WebsitesActivity;


public class MainActivity extends AppCompatActivity {

    private String MyPREFERENCES = "notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView websites = findViewById(R.id.tv_websites);
        TextView news = findViewById(R.id.tv_news);
        TextView navigate = findViewById(R.id.navigate);

        websites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebsitesActivity.class);
                startActivity(intent);
            }
        });


        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
                startActivity(intent);
            }
        });


        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
                startActivity(intent);
            }
        });


/*        String[] mTestArray = getResources().getStringArray(R.array.encoded_websites);
        for (int i = 0; i < mTestArray.length; i++) {
            FirebaseDataManager.pushWebsites(new Website(mTestArray[i], null, null, null, null, 1));
        }*/

/*
        FirebaseDataManager.pushCatWebsites();
*/

        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        editor.putString("key", "1");
        editor.commit();

    }
}

