package com.byt_eye.tcadmin.categories;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.data.FirebaseDataManager;

public class CategoriesEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_edit);

        final EditText categoryKey = findViewById(R.id.et_category_key);
        final EditText categoryName = findViewById(R.id.et_category_name);
        Button update = findViewById(R.id.btn_add);

        final String key = getIntent().getStringExtra("category_key");
        final String name = getIntent().getStringExtra("category_name");
        final String language = getIntent().getStringExtra("language");

        if (name != null) {
            categoryKey.setText(key);
            categoryName.setText(name);
        }


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDataManager.addOrUpdateCategory(CategoriesEditActivity.this, language, categoryName.getText().toString(), categoryKey.getText().toString());
            }
        });


    }


}
