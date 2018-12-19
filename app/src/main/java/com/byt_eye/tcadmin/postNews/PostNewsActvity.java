package com.byt_eye.tcadmin.postNews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.modals.Post;
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
        mDatabase = database.getReference().child("tollywoodnews");

        title = (EditText) findViewById(R.id.custom_title);
        url = (EditText) findViewById(R.id.custom_url);
        img = (EditText) findViewById(R.id.custom_img_url);


        Button sendData = (Button) findViewById(R.id.send_data);

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDataIntoFirebase(title.getText().toString(), url.getText().toString(), img.getText().toString(),"");
                Toast.makeText(PostNewsActvity.this, "News Posted", Toast.LENGTH_SHORT).show();

            }
        });


    }



    private void setDataIntoFirebase(String title, String link, String imageUrl,String description) {
        //creating the post object to store uploaded image details
        String uploadId = mDatabase.push().getKey();
        Post post = new Post(title, imageUrl, link, "0", settingCurrentTime(), uploadId,description);
        mDatabase.child(uploadId).setValue(post);

    }




    public String settingCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return sdf.format(calendar.getTime());
    }
}
