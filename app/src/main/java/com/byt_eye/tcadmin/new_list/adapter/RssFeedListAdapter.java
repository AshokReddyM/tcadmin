package com.byt_eye.tcadmin.new_list.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.data.DbOpenHelper;
import com.byt_eye.tcadmin.modals.RssFeedModel;
import com.byt_eye.tcadmin.webview.WebViewActivity;

import java.util.ArrayList;


public class RssFeedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DEFAULT_VIEW_TYPE = 1;
    TextView title;
    TextView postDate;
    DbOpenHelper dbHelper;
    private ArrayList<RssFeedModel> mRssFeedModels;
    private Activity mActivity;

    public RssFeedListAdapter(ArrayList<RssFeedModel> rssFeedModels, Activity activity) {
        mRssFeedModels = rssFeedModels;
        this.mActivity = activity;
        dbHelper = new DbOpenHelper(activity);
    }


    @Override
    public int getItemViewType(int position) {
        return DEFAULT_VIEW_TYPE;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {

        View v1 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_list, parent, false);
        return new FeedModelViewHolder(v1);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FeedModelViewHolder feedModelViewHolder;
        final RssFeedModel feedModel;

        feedModelViewHolder = (FeedModelViewHolder) holder;
        feedModel = (RssFeedModel) mRssFeedModels.get(position);
        title = ((TextView) feedModelViewHolder.rssFeedView.findViewById(R.id.titleText));
        title.setText(feedModel.getTitle());


        String aux[] = feedModel.getLink().split("/");
        ((TextView) feedModelViewHolder.rssFeedView.findViewById(R.id.post_from_web_link)).setText(aux[1] + " " + aux[2]);


        feedModelViewHolder.rssFeedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mActivity, WebViewActivity.class);
                intent.putExtra("post_link", feedModel.getLink());
                intent.putExtra("post_title", feedModel.getTitle());
                intent.putExtra("post_desc", feedModel.getDescription());


                dbHelper.updateRow(feedModel.ID, "0");
                feedModel.setReadStatus("1");
                mActivity.startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return mRssFeedModels.size();
    }

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {
        private View rssFeedView;

        public FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;
        }
    }


}

