package com.byt_eye.tcadmin.categories;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.byt_eye.tcadmin.R;
import com.byt_eye.tcadmin.data.FirebaseDataManager;
import com.byt_eye.tcadmin.modals.CategoryResponse;

import java.util.List;

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.ViewHolder> {
    private List<CategoryResponse> categories;
    Context context;

    // RecyclerView recyclerView;
    public CategoriesListAdapter(List<CategoryResponse> categories) {
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.categorylist_item, parent, false);
        CategoriesListAdapter.ViewHolder viewHolder = new CategoriesListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoriesListAdapter.ViewHolder holder, final int position) {
        holder.categoryKey.setText((position + 1) + " : " + categories.get(position).getKey());
        holder.categoryValue.setText(categories.get(position).getName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebsiteOptionDialog(context, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryKey;
        public TextView categoryValue;
        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.categoryKey = (TextView) itemView.findViewById(R.id.tv_category_key);
            this.categoryValue = (TextView) itemView.findViewById(R.id.tv_category_value);
        }
    }


    public void showWebsiteOptionDialog(final Context context, final int position) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle("Select Option");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        arrayAdapter.add("Edit");
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
                        Intent intent = new Intent(context, CategoriesEditActivity.class);
                        intent.putExtra("category_name", categories.get(position).getName());
                        intent.putExtra("category_key", categories.get(position).getKey());
                        intent.putExtra("language", ((CategoriesActivity) context).language);
                        context.startActivity(intent);
                        break;
                    case 1:
                        FirebaseDataManager.removeCategory(context, ((CategoriesActivity) context),((CategoriesActivity) context).language, categories.get(position).getKey());
                        break;
                    case 2:
                        break;
                }


            }
        });
        builderSingle.show();
    }
}
