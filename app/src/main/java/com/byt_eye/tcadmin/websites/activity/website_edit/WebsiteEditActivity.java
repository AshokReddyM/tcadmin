package com.byt_eye.tcadmin.websites.activity.website_edit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.data.FirebaseDataManager;

public class WebsiteEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_edit);

        final EditText websiteName = findViewById(R.id.et_website_name);
        final EditText websiteLink = findViewById(R.id.et_website_url);
        final EditText websiteFilter = findViewById(R.id.et_website_filter);
        Button save = findViewById(R.id.btn_save);

        String name = getIntent().getStringExtra("website_name");
        String link = getIntent().getStringExtra("website_url");
        String filter = getIntent().getStringExtra("website_filter");
        final String id = getIntent().getStringExtra("website_id");
        final String language = getIntent().getStringExtra("language");
        final String category = getIntent().getStringExtra("category");

        websiteName.setText(name);
        websiteLink.setText(link);
        websiteFilter.setText(filter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDataManager.updateWebsite(language, category, id, websiteName.getText().toString(), websiteLink.getText().toString(), websiteFilter.getText().toString());
            }
        });

    }
}
