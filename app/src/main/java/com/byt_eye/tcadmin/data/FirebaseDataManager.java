package com.byt_eye.tcadmin.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.byt_eye.tcadmin.categories.CategoriesActivity;
import com.byt_eye.tcadmin.modals.MainApp;
import com.byt_eye.tcadmin.modals.Post;
import com.byt_eye.tcadmin.modals.Websites;
import com.byt_eye.tcadmin.utils.DateTimeUtil;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class FirebaseDataManager {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference mDatabaseRef;

    /*    public static void pushWebsites() {
            mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child("Telugu");
            //creating the upload object to store uploaded image details
            String uploadId = mDatabaseRef.push().getKey();

            mDatabaseRef.child("Websites").setValue(new MainApp("టాప్", "ముఖ్యాంశాలు", "ఆంధ్రప్రదేశ్", "తెలంగాణ", "జాతీయం", "రాజకీయం", "వినోదం", "క్రీడలు", "వ్యాపారం", "టెక్నాలజీ", "విద్య", "రాశి ఫలాలు"));
        }*/


    public static void updateWebsite(final Context context, String language, String categoryId, String website_id, String websiteName, String websiteLink, String websiteFilter) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language).child("Websites").child(categoryId);
        website_id = websiteLink.replaceAll("[-+.^:,/@]", "");
        mDatabaseRef.child(website_id).setValue(new Websites(websiteName, websiteLink, String.valueOf(new Date().getTime()), websiteFilter, categoryId, language), new DatabaseReference.CompletionListener() {
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


    public static void removeWebsite(final Context context, String language, String categoryId, String websiteId) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language).child("Websites").child(categoryId);
        mDatabaseRef.child(websiteId).removeValue(new DatabaseReference.CompletionListener() {
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


    public static void removeCategory(final Context context, final CategoriesActivity categoriesActivity, String language, String categoryKey) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language).child("Categories");
        mDatabaseRef.child(categoryKey).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(context, "Website details updated failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Website details updated successfully", Toast.LENGTH_SHORT).show();
                    categoriesActivity.adapter.notifyDataSetChanged();
                }
            }
        });

    }


    public static void addOrUpdateCategory(final Context context, String language, String category, String key) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language).child("Categories");
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


    public static void postNewsIntoFireBase(String language, String categoryId, String title, String link, String imageUrl, String description) {
        mDatabaseRef = database.getReference().child("home").child("News").child("Languages").child(language).child("News");
        //creating the post object to store uploaded image details
        String uniqueKey = link.replaceAll("[-+.^:,/@]", "");
        Post post = new Post(uniqueKey, title, imageUrl, link, "0", DateTimeUtil.getCurrentTime(), description, categoryId);
        mDatabaseRef.child(uniqueKey).setValue(post);

    }


    public static DatabaseReference getCategoriesRef(String language) {
        return database.getReference().child("home").child("News").child("Languages").child(language).child("Categories");
    }



    public static DatabaseReference getWebsitesRef(String language, String category) {
        return database.getReference().child("home").child("News").child("Languages").child(language).child("Websites").child(category);
    }


    public static void videosStructureInFireBase() {
        mDatabaseRef = database.getReference().child("home").child("Videos");
        mDatabaseRef.child("Languages").setValue("languages", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                } else {
                    mDatabaseRef = database.getReference().child("home").child("Videos").child("Languages");
                    mDatabaseRef.child("Telugu").setValue("telugu", new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {
                            } else {
                                mDatabaseRef = database.getReference().child("home").child("Videos").child("Languages").child("Telugu");
                                mDatabaseRef.child("Websites").setValue("websites", new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        if (databaseError != null) {
                                        } else {

                                            mDatabaseRef = database.getReference().child("home").child("Videos").child("Languages").child("Telugu").child("Websites");
                                            mDatabaseRef.child("cat1").setValue(new Websites("Tollywood circle", "UCdtwPYK_0LG3ll1Op0iAMkQ", String.valueOf(new Date().getTime()), "youtube", "",""), new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                                    if (databaseError != null) {
                                                    } else {
                                                    }
                                                }
                                            });


                                            mDatabaseRef = database.getReference().child("home").child("Videos").child("Languages").child("Telugu");
                                            mDatabaseRef.child("Categories").setValue(new MainApp("టాప్", "ముఖ్యాంశాలు", "ఆంధ్రప్రదేశ్", "తెలంగాణ", "జాతీయం", "రాజకీయం", "వినోదం", "క్రీడలు", "వ్యాపారం", "టెక్నాలజీ", "విద్య", "రాశి ఫలాలు"));
                                        }

                                    }
                                });
                            }
                        }
                    });

                }
            }
        });

    }

}
