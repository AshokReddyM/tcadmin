package com.byt_eye.tcadmin.websites.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.modals.Website;

import java.util.List;

public class WebsitesListAdapter extends RecyclerView.Adapter<WebsitesListAdapter.ViewHolder> {
    private List<Website> websites;
    Context context;

    // RecyclerView recyclerView;
    public WebsitesListAdapter(List<Website> websites) {
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Website website = websites.get(position);
        holder.websiteName.setText((position + 1) + " : " + website.getWebsiteName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebsiteOptionDialog(context);
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


    public void showWebsiteOptionDialog(final Context context) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle("Select Option");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
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
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(context);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }
}