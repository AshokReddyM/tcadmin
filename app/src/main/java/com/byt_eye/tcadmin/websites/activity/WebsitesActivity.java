package com.byt_eye.tcadmin.websites.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.data.FirebaseDataManager;
import com.byt_eye.tcadmin.modals.WebsitesResponse;
import com.byt_eye.tcadmin.websites.adapter.WebsitesListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class WebsitesActivity extends AppCompatActivity implements WebsitesActivityMvp {

    private DatabaseReference mDatabase;
    String category;
    String language;
    String webId;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    WebsitesActivityMvp mvpView;
    private ArrayList<WebsitesResponse> websitesList;
    private WebsitesListAdapter adapter;
    private ProgressBar loader;
    private WebsitesActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websites);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_websites_list);
        loader = (ProgressBar) findViewById(R.id.loader);
        websitesList = new ArrayList<>();
        adapter = new WebsitesListAdapter(websitesList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        presenter = new WebsitesActivityPresenter();
        presenter.crawlWebsite();

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
        websitesList.addAll(websites);
        adapter.notifyDataSetChanged();
        loader.setVisibility(View.GONE);
    }


    @Override
    public void onGettingLangCategoriesList(final String language, final List<String> categoriesList) {
        final String[] categories = categoriesList.toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Category")
                .setSingleChoiceItems(categories, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setCancelable(true);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                loader.setVisibility(View.VISIBLE);

                category = categories[selectedPosition];
                new WebsitesActivityPresenter().getWebsites(WebsitesActivity.this, FirebaseDataManager.getWebsitesRef(language, categories[selectedPosition]));


            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    protected void showSelectReceiversDialog() {

        final String[] languages = new String[]{"English", "Hindi", "Telugu", "Tamil", "Kannada", "Marathi"};
        DialogInterface.OnMultiChoiceClickListener receiversDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select language")
                .setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setCancelable(true);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                loader.setVisibility(View.VISIBLE);
                language = languages[selectedPosition];

                new WebsitesActivityPresenter().getCategoriesOfLanguage(WebsitesActivity.this, languages[selectedPosition], FirebaseDataManager.getWebsitesCategoriesRef(languages[selectedPosition]));


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


}
