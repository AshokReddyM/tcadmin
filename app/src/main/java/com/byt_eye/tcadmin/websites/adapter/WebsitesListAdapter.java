package com.byt_eye.tcadmin.websites.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.data.FirebaseDataManager;
import com.byt_eye.tcadmin.modals.WebsitesResponse;
import com.byt_eye.tcadmin.websites.activity.WebsitesActivity;
import com.byt_eye.tcadmin.websites.activity.website_edit.WebsiteEditActivity;

import java.util.List;

public class WebsitesListAdapter extends RecyclerView.Adapter<WebsitesListAdapter.ViewHolder> {
    private List<WebsitesResponse> websites;
    Context context;

    // RecyclerView recyclerView;
    public WebsitesListAdapter(List<WebsitesResponse> websites) {
        this.websites = websites;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.webiste_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final WebsitesResponse website = websites.get(position);
        holder.websiteName.setText((position + 1) + " : " + website.getWebsite_name());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebsiteOptionDialog(context, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return websites.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView websiteName;
        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.websiteName = (TextView) itemView.findViewById(R.id.tv_website_name);
        }
    }


    public void showWebsiteOptionDialog(final Context context, final int position) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle("Select Option");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        arrayAdapter.add("Edit");
        arrayAdapter.add("Open Url");
        arrayAdapter.add("Delete");
        arrayAdapter.add("Status");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        Intent intent = new Intent(context, WebsiteEditActivity.class);
                        intent.putExtra("website_name", websites.get(position).getWebsite_name());
                        intent.putExtra("website_url", websites.get(position).getWeb_page_link());
                        intent.putExtra("website_filter", websites.get(position).getFilters());
                        intent.putExtra("website_id", websites.get(position).getWebId());
                        intent.putExtra("category", ((WebsitesActivity) context).categoryList.get(which).getKey());
                        intent.putExtra("language", ((WebsitesActivity) context).language);
                        context.startActivity(intent);
                        break;
                    case 1:
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                        CustomTabsIntent customTabsIntent = builder.build();
                        customTabsIntent.launchUrl(context, Uri.parse(websites.get(position).getWeb_page_link()));

                        break;
                    case 2:
                        FirebaseDataManager.removeWebsite(context,((WebsitesActivity) context).language, ((WebsitesActivity) context).category.getKey(), websites.get(position).getWebId());
                        break;
                    case 3:
                        break;

                }


            }
        });
        builderSingle.show();
    }
}