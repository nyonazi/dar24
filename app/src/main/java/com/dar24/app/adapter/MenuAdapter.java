package com.dar24.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dar24.app.R;
import com.dar24.app.model.Menu;
import com.dar24.app.utility.SharedPref;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;
    private List<Menu> items = new ArrayList<>();
    private boolean TOP_MENU = false;
    private boolean BOTTOM_MENU = false;
    private MenuAdapterListener listener;
    private SharedPref pref;

    public MenuAdapter(Context context, boolean topMenu) {
        this.context = context;
        this.listener = (MenuAdapterListener) context;
        if (topMenu) {
            TOP_MENU = true;
            BOTTOM_MENU = false;
        } else {
            TOP_MENU = false;
            BOTTOM_MENU = true;
        }
        this.pref = new SharedPref(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (TOP_MENU) {
            View view = inflater.inflate(R.layout.item_menu_top, parent, false);
            return new TopMenuViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_menu_bottom, parent, false);
            return new BottomMenuViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int position) {
        Menu menuItem = items.get(position);
        if (vh instanceof TopMenuViewHolder) {
            ((TopMenuViewHolder) vh).tvTitle.setText(menuItem.getTitle());
            if (menuItem.getIconResource() != null) {
                ((TopMenuViewHolder) vh).ivIcon.setImageResource(menuItem.getIconResource());
            }
        } else {
            ((BottomMenuViewHolder) vh).tvTitle.setText(menuItem.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItem(int position, Menu item) {
        if (item != null) {
            items.set(position, item);
            notifyItemChanged(position);
            for (int i = 0; i < items.size(); i++) {
                if (i != position) {
                    Menu updateItem = items.get(i);
                    updateItem.setIconResource(null);
                    items.set(i, updateItem);
                    notifyItemChanged(i);
                }
            }
        }
    }

    public void addItems(List<Menu> menuItems) {
        if (menuItems != null) {
            items.clear();
            items.addAll(menuItems);
            notifyDataSetChanged();
        }
    }

    public interface MenuAdapterListener {
        void onTopMenuItemSelected(int position) throws Exception;

        void onBottomMenuItemSelected(int position);
    }

    public class TopMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        ImageView ivIcon;

        public TopMenuViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            applyFontSize(tvTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                listener.onTopMenuItemSelected(getAdapterPosition());
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public class BottomMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;

        public BottomMenuViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            applyFontSize(tvTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onBottomMenuItemSelected(getAdapterPosition());
        }

    }

    private void applyFontSize(TextView tvTitle){
        if(pref.getFontSize() == SharedPref.FONT_SIZE_SMALL){
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        } else if(pref.getFontSize() == SharedPref.FONT_SIZE_MEDIUM){
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        } else if(pref.getFontSize() == SharedPref.FONT_SIZE_LARGE){
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
    }
}
