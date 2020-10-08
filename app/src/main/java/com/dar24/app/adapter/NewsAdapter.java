package com.dar24.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dar24.app.R;
import com.dar24.app.activity.NewsDetailActivity;
import com.dar24.app.model.News;
import com.dar24.app.utility.Helpers;
import com.dar24.app.utility.SharedPref;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int VIEW_TYPE_TOP = 0;
    public final int VIEW_TYPE_BOTTOM = 1;
    public final int VIEW_TYPE_EMPTY_FOOTER = 2;
    public Context mContext;
    public List<News> newsList = new ArrayList<>();
    private SharedPref pref;

    public NewsAdapter(Context context) {
        this.mContext = context;
        this.pref = new SharedPref(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        if (VIEW_TYPE_TOP == viewType) {
            v = inflater.inflate(R.layout.item_news_top, parent, false);
        } else if (VIEW_TYPE_EMPTY_FOOTER == viewType) {
            v = inflater.inflate(R.layout.item_empty_footer, parent, false);
            return new EmptyFooter(v);
        } else {
            v = inflater.inflate(R.layout.item_news, parent, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int position) {
        if (vh instanceof ViewHolder) {
            News news = newsList.get(position);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ((ViewHolder) vh).tvTitle.setText(Html.fromHtml(news.getTitle(),
                        Html.FROM_HTML_MODE_COMPACT));
                ((ViewHolder) vh).tvSummary.setText(Html.fromHtml(news.getSummary(),
                        Html.FROM_HTML_MODE_COMPACT));
            } else {
                ((ViewHolder) vh).tvTitle.setText(Html.fromHtml(news.getTitle()));
                ((ViewHolder) vh).tvSummary.setText(Html.fromHtml(news.getSummary()));
            }
            ((ViewHolder) vh).tvCategory.setText(news.getCategory());

            ((ViewHolder) vh).tvPublishedAt.setText(Helpers.getTimeAgo(mContext,
                    Helpers.stringDateToLong(news.getPublishedAt())) + ", "
                    + mContext.getString(R.string.by) + " " + news.getAuthor());
            if (news.getThumbnail().length() != 0) {
                Picasso.get().load(news.getThumbnail())
                        .placeholder(R.color.grey_200)
                        .into(((ViewHolder) vh).ivThumbnail);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_TOP;
        } else if (position == newsList.size()) {
            return VIEW_TYPE_EMPTY_FOOTER;
        } else {
            if ((position % 4) == 0) {
                return VIEW_TYPE_TOP;
            } else {
                return VIEW_TYPE_BOTTOM;
            }
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size() != 0 ? newsList.size() + 1 : 0;
    }

    public void addItems(List<News> news) {
        if (news != null) {
            newsList.clear();
            newsList.addAll(news);
            notifyDataSetChanged();
        }
    }

    public void addMoreItems(List<News> news) {
        if (news != null) {
            newsList.addAll(news);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvCategory, tvSummary, tvPublishedAt;
        ImageView ivThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvSummary = itemView.findViewById(R.id.tvSummary);
            tvPublishedAt = itemView.findViewById(R.id.tvPublishedAt);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            applyFontSize();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            News news = newsList.get(getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putInt("current", getAdapterPosition());
            bundle.putParcelable("news", Parcels.wrap(newsList));
            mContext.startActivity(new Intent(mContext, NewsDetailActivity.class)
                    .putExtras(bundle));
        }

        private void applyFontSize() {
            if (pref.getFontSize() == SharedPref.FONT_SIZE_SMALL) {
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tvCategory.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tvSummary.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tvPublishedAt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            } else if (pref.getFontSize() == SharedPref.FONT_SIZE_MEDIUM) {
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                tvCategory.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tvSummary.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tvPublishedAt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            } else if (pref.getFontSize() == SharedPref.FONT_SIZE_LARGE) {
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tvCategory.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tvSummary.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tvPublishedAt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            }
        }
    }

    public class EmptyFooter extends RecyclerView.ViewHolder {

        public EmptyFooter(View itemView) {
            super(itemView);
        }
    }
}
