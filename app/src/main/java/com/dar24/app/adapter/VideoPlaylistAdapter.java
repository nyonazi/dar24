package com.dar24.app.adapter;

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

import com.dar24.app.R;
import com.dar24.app.activity.PlaylistVideoActivity;
import com.dar24.app.model.VideoPlaylist;
import com.dar24.app.utility.SharedPref;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class VideoPlaylistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<VideoPlaylist> playList = new ArrayList<>();
    private SharedPref pref;
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_EMPTY_FOOTER = 1;

    public VideoPlaylistAdapter(Context context) {
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
            v = inflater.inflate(R.layout.item_video_playlist, parent, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int position) {
        if (vh instanceof ViewHolder) {
            VideoPlaylist playlist = playList.get(position);
            ((ViewHolder) vh).tvTitle.setText(playlist.getTitle());
            ((ViewHolder) vh).tvVideos.setText(playlist.getVideoCount() + " " +
                    context.getString(R.string.videos));
            Picasso.get().load(playlist.getThumbnail())
                    .placeholder(R.color.grey_200)
                    .into(((ViewHolder) vh).ivThumbnail);
        }

    }

    @Override
    public int getItemCount() {
        return playList.size() != 0 ? playList.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == playList.size()) {
            return VIEW_TYPE_EMPTY_FOOTER;
        }
        return VIEW_TYPE_ITEM;
    }

    public void addItems(List<VideoPlaylist> playlists) {
        if (playlists != null) {
            playList.clear();
            playList.addAll(playlists);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvVideos;
        ImageView ivThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvVideos = itemView.findViewById(R.id.tvVideos);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            applyFontSize();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            VideoPlaylist playlist = playList.get(getAdapterPosition());
            Intent intent = new Intent(context, PlaylistVideoActivity.class);
            intent.putExtra("title", playlist.getTitle());
            intent.putExtra("playlist", playlist.getPlaylistPath());
            context.startActivity(intent);
        }

        private void applyFontSize() {
            if (pref.getFontSize() == SharedPref.FONT_SIZE_SMALL) {
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tvVideos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            } else if (pref.getFontSize() == SharedPref.FONT_SIZE_MEDIUM) {
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                tvVideos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            } else if (pref.getFontSize() == SharedPref.FONT_SIZE_LARGE) {
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tvVideos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            }
        }
    }

    public class EmptyFooter extends RecyclerView.ViewHolder {

        public EmptyFooter(View itemView) {
            super(itemView);
        }
    }

}
