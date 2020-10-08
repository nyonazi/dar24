package com.dar24.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.dar24.app.R;
import com.dar24.app.model.Setting;
import com.dar24.app.utility.SharedPref;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class SettingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;
    private List<Setting> items = new ArrayList<>();
    private SettingListener listener;
    private SharedPref pref;

    public SettingAdapter(Context context) {
        this.context = context;
        this.listener = (SettingListener) context;
        this.pref = new SharedPref(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_setting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, final int position) {
        Setting setting = items.get(position);
        if (vh instanceof ViewHolder) {
            ((ViewHolder) vh).tvTitle.setText(setting.getTitle());
            if (setting.getDescription() != null) {
                ((ViewHolder) vh).tvDescription.setVisibility(View.VISIBLE);
                ((ViewHolder) vh).tvDescription.setText(setting.getDescription());
            } else {
                ((ViewHolder) vh).tvDescription.setVisibility(View.GONE);
            }
            if (setting.getActive() != null) {
                ((ViewHolder) vh).swAction.setVisibility(View.VISIBLE);
                ((ViewHolder) vh).swAction.setChecked(setting.getActive());
                ((ViewHolder) vh).swAction.setOnCheckedChangeListener(
                        new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         boolean isChecked) {
                                listener.onSelected(position);
                            }
                        });
            } else {
                ((ViewHolder) vh).swAction.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(List<Setting> menuItems) {
        if (menuItems != null) {
            items.clear();
            items.addAll(menuItems);
            notifyDataSetChanged();
        }
    }

    public void updateItem(int position, Setting item) {
        if (item != null) {
            items.set(position, item);
            notifyItemChanged(position);
        }
    }

    public interface SettingListener {
        void onSelected(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvDescription;
        Switch swAction;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            swAction = itemView.findViewById(R.id.switchAction);
            applyFontSize();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onSelected(getAdapterPosition());
        }

        private void applyFontSize(){
            if(pref.getFontSize() == SharedPref.FONT_SIZE_SMALL){
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            } else if(pref.getFontSize() == SharedPref.FONT_SIZE_MEDIUM){
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            } else if(pref.getFontSize() == SharedPref.FONT_SIZE_LARGE){
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            }
        }
    }
}

