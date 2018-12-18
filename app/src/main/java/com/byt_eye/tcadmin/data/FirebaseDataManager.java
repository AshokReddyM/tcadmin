package com.byt_eye.tcadmin.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.modals.MainApp;
import com.byt_eye.tcadmin.modals.Website;
import com.byt_eye.tcadmin.websites.adapter.WebsitesListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FirebaseDataManager {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference mDatabaseRef;

/*    public static void pushCatWebsites() {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child("Telugu").child("Websites");
        //creating the upload object to store uploaded image details
        String uploadId = mDatabaseRef.push().getKey();

        mDatabaseRef.child("NewsByCategory").setValue(new MainApp("టాప్", "ముఖ్యాంశాలు", "ఆంధ్రప్రదేశ్", "తెలంగాణ", "జాతీయం", "రాజకీయం", "వినోదం", "క్రీడలు", "వ్యాపారం", "టెక్నాలజీ", "విద్య", "రాశి ఫలాలు"));


    }
    */
    public static void pushCatWebsites() {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child("Telugu");
        //creating the upload object to store uploaded image details
        String uploadId = mDatabaseRef.push().getKey();
/*

        String[] mTestArray = getResources().getStringArray(R.array.encoded_websites);
        for (int i = 0; i < mTestArray.length; i++) {
            FirebaseDataManager.pushWebsites(new Website(mTestArray[i], null, null, null, null, 1));
        }
*/

/*
        mDatabaseRef.child("Websites").setValue(new MainApp2();
*/


    }

 /*   public static void pushWebsites() {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages");
        //creating the upload object to store uploaded image details
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child("Telugu").setValue(new MainApp2("hindi"));
        mDatabaseRef.child("Tamil").setValue(new MainApp2("hindi"));
        mDatabaseRef.child("Kannada").setValue(new MainApp2("hindi"));
        mDatabaseRef.child("Marathi").setValue(new MainApp2("hindi"));
    }
*/

    public static void getWebsites(final WebsitesListAdapter adapter, final List<Website> websitesList) {
        mDatabaseRef = database.getReference().child("websites");
        mDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Website website = dataSnapshot.getValue(Website.class);
                websitesList.add(website);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter.notifyDataSetChanged();
    }
}
