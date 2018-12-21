package com.byt_eye.tcadmin.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

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

    public static void updateWebsite(final Context context, String language, String category, String id, String websiteName, String websiteLink, String websiteFilter) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language).child("Websites").child(category);
        //creating the upload object to store uploaded image details
        mDatabaseRef.child(id).setValue(new Websites(websiteName, websiteLink, String.valueOf(new Date().getTime()), websiteFilter), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(context, "Website details updated failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Website details updated successfully", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public static void addWebsite(final Context context, String language, String category, String websiteName, String websiteLink, String websiteFilter) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language).child("Websites").child(category);
        //creating the upload object to store uploaded image details
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(new Websites(websiteName, websiteLink, String.valueOf(new Date().getTime()), websiteFilter), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(context, "Website details updated failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Website details updated successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public static void removeWebsite(final Context context, String language, String category, String id) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language).child("Websites").child(category);
        //creating the upload object to store uploaded image details
        mDatabaseRef.child(id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(context, "Website details updated failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Website details updated successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public static void removeCategory(final Context context, String language, String category) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language).child("Websites");
        //creating the upload object to store uploaded image details
        mDatabaseRef.child(category).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(context, "Website details updated failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Website details updated successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public static void addOrUpdateCategory(final Context context, String language, String category, String key) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language).child("Categories");;
        //creating the upload object to store uploaded image details
        mDatabaseRef.child(key).setValue(category, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(context, "Category details updated failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Category details updated successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void EditCategory(final Context context, String language, String category) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language);
        //creating the upload object to store uploaded image details
        mDatabaseRef.child("Websites").setValue(category, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(context, "Website details updated failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Website details updated successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public static DatabaseReference getCategoriesRef(String language) {
        return database.getReference().child("home").child("News").child("Languages").child(language).child("Categories");
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
