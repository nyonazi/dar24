package com.dar24.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dar24.app.R;
import com.dar24.app.activity.PhotoViewActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Mawole
 * @email simonmawole2011@gmail.com
 */
public class AboutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Integer> thumbnailList = new ArrayList<>();

    public AboutAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_about, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int position) {
        if (vh instanceof ViewHolder) {
            Integer thumbnail = thumbnailList.get(position);
            Picasso.get().load(thumbnail)
                    .placeholder(R.color.grey_200)
                    .into(((ViewHolder) vh).ivThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return thumbnailList.size();
    }


    public void addItems(List<Integer> thumbnails) {
        if (thumbnails != null) {
            thumbnailList.clear();
            thumbnailList.addAll(thumbnails);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Integer thumbnail = thumbnailList.get(getAdapterPosition());
            Intent intent = new Intent(context, PhotoViewActivity.class);
            intent.putExtra("photo", thumbnail);
            context.startActivity(intent);
        }
    }
}

