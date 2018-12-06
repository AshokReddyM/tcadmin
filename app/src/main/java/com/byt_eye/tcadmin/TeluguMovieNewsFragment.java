package com.byt_eye.tcadmin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class TeluguMovieNewsFragment extends Fragment {

    DBHandler dbHelper;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeLayout;
    private Button sendNews;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_telugu_new, container, false);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        swipeLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_container);
        sendNews = (Button) getActivity().findViewById(R.id.send_favorite_news);
        sendNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), AddCustomNews.class);
                startActivity(intent);
            }
        });

        swipeLayout.setRefreshing(true);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                finalDataSetInAdapter();
                Toast.makeText(getContext(), "Latest Tollywood News Updated", Toast.LENGTH_SHORT).show();
                swipeLayout.setRefreshing(false);


            }
        });


        dbHelper = new DBHandler(getActivity());
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        mRecyclerView.setAdapter(new RssFeedListAdapter(mList, getActivity()));

        while (rowsCount > 1000) {
            dbHelper.deleteFirstRow();
            rowsCount--;
        }
    }
}





