package com.byt_eye.tcadmin.landing_page;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.categories.CategoriesActivity;
import com.byt_eye.tcadmin.data.FirebaseDataManager;
import com.byt_eye.tcadmin.modals.CategoryResponse;
import com.byt_eye.tcadmin.modals.WebsitesResponse;
import com.byt_eye.tcadmin.new_list.NewsListActivity;
import com.byt_eye.tcadmin.websites.activity.WebsitesActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainActivityMvp {

    private String MyPREFERENCES = "notification";
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter();
        TextView websites = findViewById(R.id.tv_websites);
        TextView news = findViewById(R.id.tv_news);
        TextView categories = findViewById(R.id.tv_categories);

        presenter.getCategoriesOfLanguage(MainActivity.this, "Telugu", FirebaseDataManager.getCategoriesRef("Telugu"));



/*
        FirebaseDataManager.videosStructureInFireBase();
*/


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


        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });


/*        String[] mTestArray = getResources().getStringArray(R.array.encoded_websites);
        for (int i = 0; i < mTestArray.length; i++) {
            FirebaseDataManager.pushWebsites(new Website(mTestArray[i], null, null, null, null, 1));
        }*/


        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        editor.putString("key", "1");
        editor.commit();

    }

    @Override
    public void onGettingLangCategoriesList(String language, List<CategoryResponse> categoriesList) {
        for (int i = 0; i < categoriesList.size(); i++) {
            presenter.getWebsites(this, FirebaseDataManager.getWebsitesRef(language, categoriesList.get(i).getKey()));
        }
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onGettingWebsiteDetails(List<WebsitesResponse> websites) {
        for (int i = 0; i < websites.size(); i++) {
            presenter.crawlWebsite(websites.get(i).getWeb_page_link());
        }
    }
}

