package com.byt_eye.tcadmin.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.byt_eye.tcadmin.modals.Website;
import com.byt_eye.tcadmin.modals.Websites;
import com.byt_eye.tcadmin.websites.adapter.WebsitesListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

public class FirebaseDataManager {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference mDatabaseRef;

    /*    public static void pushWebsites() {
            mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child("Telugu");
            //creating the upload object to store uploaded image details
            String uploadId = mDatabaseRef.push().getKey();

            mDatabaseRef.child("Websites").setValue(new MainApp("టాప్", "ముఖ్యాంశాలు", "ఆంధ్రప్రదేశ్", "తెలంగాణ", "జాతీయం", "రాజకీయం", "వినోదం", "క్రీడలు", "వ్యాపారం", "టెక్నాలజీ", "విద్య", "రాశి ఫలాలు"));


        }*/

    public static void pushNewWebsites(String language, String category) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language).child("Websites").child(category);
        //creating the upload object to store uploaded image details
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(new Websites("GreatAndhra", "https://telugu.greatandhra.com/movies.php", String.valueOf(new Date().getTime()), ""));

    }

    public static void updateWebsite(String language, String category, String id, String websiteName, String websiteLink, String websiteFilter) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language).child("Websites").child(category);
        //creating the upload object to store uploaded image details
        mDatabaseRef.child(id).setValue(new Websites(websiteName, websiteLink, String.valueOf(new Date().getTime()), websiteFilter));

    }


    public static void removeNewWebsites(String language, String category, String id) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language).child("Websites").child(category);
        //creating the upload object to store uploaded image details
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).removeValue();
    }


    public static DatabaseReference getWebsitesCategoriesRef(String language) {
        return database.getReference().child("home").child("News").child("Languages").child(language).child("Websites");
    }


    public static DatabaseReference getWebsitesRef(String language, String category) {
        return database.getReference().child("home").child("News").child("Languages").child(language).child("Websites").child(category);
    }


    public static void pushCatWebsites() {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child("Telugu");
        //creating the upload object to store uploaded image details
        String uploadId = mDatabaseRef.push().getKey();
    }


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
