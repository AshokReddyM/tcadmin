package com.byt_eye.tcadmin.new_list;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.new_list.adapter.RssFeedListAdapter;
import com.byt_eye.tcadmin.data.DbOpenHelper;
import com.byt_eye.tcadmin.listeners.RssPullService;
import com.byt_eye.tcadmin.modals.RssFeedModel;
import com.byt_eye.tcadmin.postNews.PostNewsActvity;
import com.byt_eye.tcadmin.services.DailyService;

import java.util.ArrayList;

public class NewsListActivity extends Activity {

    DbOpenHelper dbHelper;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeLayout;
    private Button sendNews;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_telugu_new);

        Intent i = new Intent(this, RssPullService.class);
        RssPullService.enqueueWork(this, i);
        DailyService.initDailyService(this, true);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        sendNews = (Button) findViewById(R.id.send_favorite_news);
        sendNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NewsListActivity.this, PostNewsActvity.class);
                startActivity(intent);
            }
        });

        swipeLayout.setRefreshing(true);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                finalDataSetInAdapter();
                Toast.makeText(NewsListActivity.this, "Latest Tollywood News Updated", Toast.LENGTH_SHORT).show();
                swipeLayout.setRefreshing(false);


            }
        });


        dbHelper = new DbOpenHelper(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        finalDataSetInAdapter();
        swipeLayout.setRefreshing(false);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void finalDataSetInAdapter() {
        int rowsCount = 0;
        ArrayList<RssFeedModel> mList = dbHelper.getDataList();
        rowsCount = mList.size();
        mRecyclerView.setAdapter(new RssFeedListAdapter(mList, this));

        while (rowsCount > 1000) {
            dbHelper.deleteFirstRow();
            rowsCount--;
        }
    }
}





