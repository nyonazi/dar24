package com.dar24.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dar24.app.R;
import com.dar24.app.model.VideoLatest;
import com.dar24.app.utility.Developer;
import com.dar24.app.utility.Helpers;
import com.dar24.app.utility.SharedPref;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class VideoLatestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<VideoLatest> videoList = new ArrayList<>();
    private SharedPref pref;
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_EMPTY_FOOTER = 1;

    public VideoLatestAdapter(Context context) {
        this.context = context;
        this.pref = new SharedPref(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        if(viewType == VIEW_TYPE_EMPTY_FOOTER){
            v = inflater.inflate(R.layout.item_empty_footer, parent, false);
            return new EmptyFooter(v);
        } else {
            v = inflater.inflate(R.layout.item_video_latest, parent, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int position) {
        if (vh instanceof ViewHolder) {
            VideoLatest video = videoList.get(position);
            ((ViewHolder) vh).tvTitle.setText(video.getTitle());
            ((ViewHolder) vh).tvViews.setText(video.getViews() + " "
                    + context.getString(R.string.views));
            ((ViewHolder) vh).tvPublishedAt.setText(Helpers.getTimeAgo(context,
                    Helpers.stringDateToLong(video.getPublishedAt())));
            Picasso.get().load(video.getThumbnail())
                    .placeholder(R.color.grey_200)
                    .into(((ViewHolder) vh).ivThumbnail);
        }

    }

    @Override
    public int getItemCount() {
        return videoList.size() != 0 ? videoList.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == videoList.size()) {
            return VIEW_TYPE_EMPTY_FOOTER;
        }
        return VIEW_TYPE_ITEM;
    }

    public void addItems(List<VideoLatest> videos) {
        if (videos != null) {
            videoList.clear();
            videoList.addAll(videos);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvPublishedAt, tvViews;
        ImageView ivThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPublishedAt = itemView.findViewById(R.id.tvPublishedAt);
            tvViews = itemView.findViewById(R.id.tvViews);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            applyFontSize();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            boolean autoPlay = true;
            VideoLatest videoLatest = videoList.get(getAdapterPosition());
            if (pref.getVideoAutoplay() == SharedPref.VIDEO_AUTOPLAY_NEVER) {
                autoPlay = false;
            }
            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context,
                    Developer.YOUTUBE_API_KEY,
                    videoLatest.getVideoPath(),//video id
                    0,     //after this time, video will start automatically
                    autoPlay,               //autoplay or not
                    false);//lightbox mode or not; show the video in a small box
            if(intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Please! install youtube app", Toast.LENGTH_SHORT).show();
            }
        }

        private void applyFontSize() {
            if (pref.getFontSize() == SharedPref.FONT_SIZE_SMALL) {
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tvPublishedAt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tvViews.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            } else if (pref.getFontSize() == SharedPref.FONT_SIZE_MEDIUM) {
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                tvPublishedAt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tvViews.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            } else if (pref.getFontSize() == SharedPref.FONT_SIZE_LARGE) {
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tvPublishedAt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tvViews.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            }
        }
    }

    public class EmptyFooter extends RecyclerView.ViewHolder {

        public EmptyFooter(View itemView) {
            super(itemView);
        }
    }
}
