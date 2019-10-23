package com.oxycab.provider.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.oxycab.provider.BuildConfig;
import com.oxycab.provider.R;
import com.oxycab.provider.data.network.model.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.MyViewHolder> {

    private ArrayList<Document> list;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rvItem;
        private ImageView ivStatus;
        private TextView tvDocName;

        MyViewHolder(View view) {
            super(view);
            rvItem = view.findViewById(R.id.rl_docitem);
            ivStatus = view.findViewById(R.id.iv_status);
            tvDocName = view.findViewById(R.id.tv_docName);
        }
    }


    public DocumentListAdapter(Context context, ArrayList<Document> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DocumentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_document, parent, false);

        return new DocumentListAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull DocumentListAdapter.MyViewHolder holder, int position) {
        Document obj = list.get(position);
        holder.tvDocName.setText(obj.getName());

        if (obj.getUrl() != null) {
            Glide.with(context).load(R.drawable.correct).into(holder.ivStatus);
        } else {
            Glide.with(context).load(R.drawable.upload).into(holder.ivStatus);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<Document> getList() {
        return list;
    }

    public void setItem(Document item, Integer position) {
        list.set(position, item);
        notifyItemChanged(position);
    }
}

