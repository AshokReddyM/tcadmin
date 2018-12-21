package com.byt_eye.tcadmin.websites.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.data.FirebaseDataManager;
import com.byt_eye.tcadmin.modals.CategoryResponse;
import com.byt_eye.tcadmin.modals.WebsitesResponse;
import com.byt_eye.tcadmin.websites.activity.website_edit.WebsiteEditActivity;
import com.byt_eye.tcadmin.websites.adapter.WebsitesListAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class WebsitesActivity extends AppCompatActivity implements WebsitesActivityMvp {

    private DatabaseReference mDatabase;
    public CategoryResponse category;
    public String language;
    String webId;
    private ArrayList<WebsitesResponse> websitesList;
    public ArrayList<CategoryResponse> categoryList;
    private WebsitesListAdapter adapter;
    private ProgressBar loader;
    private WebsitesActivityPresenter presenter;
    private ArrayList<String> stringCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websites);

        stringCategories = new ArrayList<>();
        categoryList = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_websites_list);
        loader = (ProgressBar) findViewById(R.id.loader);
        websitesList = new ArrayList<>();
        adapter = new WebsitesListAdapter(websitesList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        presenter = new WebsitesActivityPresenter();

        showSelectReceiversDialog();


        /*new WebsitesActivityPresenter().getMainModules(WebsitesActivity.this, database.getReference().child("home"));*/


    }


    @Override
    public void onGettingDetails(List<WebsitesResponse> websites) {
        websitesList.addAll(websites);
        adapter.notifyDataSetChanged();
        loader.setVisibility(View.GONE);
    }

    @Override
    public void onError(String message) {
        loader.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onGettingModulesList(List<String> modulesList) {

    }

    @Override
    public void onGettingWebsiteDetails(List<WebsitesResponse> websites) {
        if (websites.size() != 0) {
            websitesList.addAll(websites);
            adapter.notifyDataSetChanged();
            loader.setVisibility(View.GONE);
        } else {
            loader.setVisibility(View.GONE);
            Toast.makeText(this, "No websites found. Please add websites in this category", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onGettingLangCategoriesList(final String language, final List<CategoryResponse> categoriesList) {

        if (categoriesList.size() != 0) {

            categoryList.clear();
            categoryList.addAll(categoriesList);

            for (int i = 0; i < categoriesList.size(); i++) {
                stringCategories.add(categoriesList.get(i).getName());
            }

            CharSequence[] categoryValues = stringCategories.toArray(new CharSequence[categoriesList.size()]);


            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Select Category");
            builder.setCancelable(true);
            builder.setItems(categoryValues, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    loader.setVisibility(View.VISIBLE);
                    new WebsitesActivityPresenter().getWebsites(WebsitesActivity.this, FirebaseDataManager.getWebsitesRef(language, categoriesList.get(which).getKey()));
                }
            });

            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    builder.create().cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            loader.setVisibility(View.GONE);
            Toast.makeText(this, "No Categories Found. Please add categories in category section", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    protected void showSelectReceiversDialog() {

        final String[] languages = new String[]{"English", "Hindi", "Telugu", "Tamil", "Kannada", "Marathi"};
        DialogInterface.OnMultiChoiceClickListener receiversDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select language");
        builder.setCancelable(true);

        builder.setItems(languages, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loader.setVisibility(View.VISIBLE);
                language = languages[which];
                new WebsitesActivityPresenter().getCategoriesOfLanguage(WebsitesActivity.this, languages[which], FirebaseDataManager.getCategoriesRef(languages[which]));

            }
        });

        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //
//        String[] mTestArray = getResources().getStringArray(R.array.encoded_websites);
//
//        for (int i = 0; i < mTestArray.length; i++) {
//            FirebaseDataManager.pushWebsites(new Website(mTestArray[i], null, null, null, null, 1));
//        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_add_website:
                showCategories();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void showCategories() {
        if (categoryList.size() != 0) {

            stringCategories.clear();
            for (int i = 0; i < categoryList.size(); i++) {
                stringCategories.add(categoryList.get(i).getName());
            }

            CharSequence[] categoryValues = stringCategories.toArray(new CharSequence[categoryList.size()]);


            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Select Category");
            builder.setCancelable(true);
            builder.setItems(categoryValues, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(WebsitesActivity.this, WebsiteEditActivity.class);
                    intent.putExtra("category", categoryList.get(which).getKey());
                    intent.putExtra("language", language);
                    startActivity(intent);
                }
            });

            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    builder.create().cancel();
                    finish();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            loader.setVisibility(View.GONE);
            Toast.makeText(this, "No Categories Found. Please add categories in category section", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
