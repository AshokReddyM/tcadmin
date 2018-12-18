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
import com.byt_eye.tcadmin.modals.Website;
import com.byt_eye.tcadmin.websites.adapter.WebsitesListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class WebsitesActivity extends AppCompatActivity implements WebsitesActivityMvp {

    private DatabaseReference mDatabase;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    WebsitesActivityMvp mvpView;
    private ArrayList<Website> websitesList;
    private WebsitesListAdapter adapter;
    private ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websites);

//
//        String[] mTestArray = getResources().getStringArray(R.array.encoded_websites);
//
//        for (int i = 0; i < mTestArray.length; i++) {
//            FirebaseDataManager.pushWebsites(new Website(mTestArray[i], null, null, null, null, 1));
//        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_websites_list);
        loader = (ProgressBar) findViewById(R.id.loader);
        websitesList = new ArrayList<>();
        adapter = new WebsitesListAdapter(websitesList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        /*showSelectReceiversDialog();*/


        new WebsitesActivityPresenter().getMainModules(WebsitesActivity.this, database.getReference().child("home"));


    }


    @Override
    public void onGettingDetails(List<Website> websites) {
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

    protected void showSelectReceiversDialog() {


        String[] languages = new String[]{"English", "Hindi", "Telugu", "Tamil", "Kannada", "Marathi"};


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

                switch (selectedPosition) {
                    case 0:
                        new WebsitesActivityPresenter().getWebsites(WebsitesActivity.this, database.getReference().child("english_websites"));

                        break;
                    case 1:
                        new WebsitesActivityPresenter().getWebsites(WebsitesActivity.this, database.getReference().child("hindi_websites"));

                        break;
                    case 2:
                        new WebsitesActivityPresenter().getWebsites(WebsitesActivity.this, database.getReference().child("websites"));

                        break;
                    case 3:
                        new WebsitesActivityPresenter().getWebsites(WebsitesActivity.this, database.getReference().child("tamil_websites"));

                        break;
                    case 4:
                        new WebsitesActivityPresenter().getWebsites(WebsitesActivity.this, database.getReference().child("kannada_websites"));

                        break;
                    case 5:
                        new WebsitesActivityPresenter().getWebsites(WebsitesActivity.this, database.getReference().child("marathi_websites"));

                        break;
                }

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
