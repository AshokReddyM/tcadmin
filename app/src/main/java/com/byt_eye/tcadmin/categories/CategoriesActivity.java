package com.byt_eye.tcadmin.categories;

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

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.data.FirebaseDataManager;
import com.byt_eye.tcadmin.modals.CategoryResponse;
import com.byt_eye.tcadmin.websites.activity.WebsitesActivityMvp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements CategoriesActivityMvp {


    private DatabaseReference mDatabase;
    public String category;
    public String language;
    String webId;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    WebsitesActivityMvp mvpView;
    private ArrayList<CategoryResponse> categoriesList;
    private CategoriesListAdapter adapter;
    private ProgressBar loader;
    private CategoriesActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_categories_list);
        loader = (ProgressBar) findViewById(R.id.loader);
        categoriesList = new ArrayList<>();
        adapter = new CategoriesListAdapter(categoriesList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        presenter = new CategoriesActivityPresenter();

        showSelectReceiversDialog();
    }


    @Override
    public void onGettingLangCategoriesList(final String language, final List<CategoryResponse> categories) {
        categoriesList.addAll(categories);
        adapter.notifyDataSetChanged();
        loader.setVisibility(View.GONE);
    }


    protected void showSelectReceiversDialog() {

        final String[] languages = new String[]{"English", "Hindi", "Telugu", "Tamil", "Kannada", "Marathi"};

        AlertDialog.Builder builder = new AlertDialog.Builder(CategoriesActivity.this);
        builder.setTitle("Select Language");

        builder.setItems(languages, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loader.setVisibility(View.VISIBLE);
                language = languages[which];
                new CategoriesActivityPresenter().getCategoriesOfLanguage(CategoriesActivity.this, language, FirebaseDataManager.getCategoriesRef(languages[which]));

            }
        });


        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

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
                Intent intent = new Intent(this, CategoriesEditActivity.class);
                intent.putExtra("category_key", "cat");
                intent.putExtra("category_name", "");
                intent.putExtra("language", language);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}



