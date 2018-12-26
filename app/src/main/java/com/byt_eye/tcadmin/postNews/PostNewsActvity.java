package com.byt_eye.tcadmin.postNews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.modals.Post;
import com.byt_eye.tcadmin.utils.DateTimeUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PostNewsActvity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText title;
    private EditText img;
    private EditText url;
    private Button sendNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_news);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference().child("News").child("Languages");

        title = (EditText) findViewById(R.id.custom_title);
        url = (EditText) findViewById(R.id.custom_url);
        img = (EditText) findViewById(R.id.custom_img_url);


        Button sendData = (Button) findViewById(R.id.send_data);

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uploadId = mDatabase.push().getKey();
                String uniqueKey = url.getText().toString().replaceAll("[-+.^:,/@]", "");

                Post post = new Post(uniqueKey, title.getText().toString(), img.getText().toString(), url.getText().toString(), "0", DateTimeUtil.getCurrentTime(), "", "");
                Toast.makeText(PostNewsActvity.this, "News Posted", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void setDataIntoFireBase(String language, String categoryId, String title, String link, String imageUrl, String description) {
        //creating the post object to store uploaded image details
        mDatabase.child(language).child("News");
        String uploadId = mDatabase.push().getKey();
        String uniqueKey = link.replaceAll("[-+.^:,/@]", "");

        Post post = new Post(uniqueKey, title, imageUrl, link, "0", DateTimeUtil.getCurrentTime(), description, categoryId);
        mDatabase.child(uploadId).setValue(post);

    }



}
