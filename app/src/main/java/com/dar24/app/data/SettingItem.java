package com.dar24.app.data;

import android.content.Context;

import com.dar24.app.R;
import com.dar24.app.model.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class SettingItem {

    public static List<Setting> getSettings(Context context) {
        List<Setting> item = new ArrayList<>();

        item.add(new Setting(context.getString(R.string.language), context.getString(R.string.english_international), null));
        item.add(new Setting(context.getString(R.string.font_size), context.getString(R.string.medium), null));
        item.add(new Setting(context.getString(R.string.notification), null, true));
        item.add(new Setting(context.getString(R.string.video_autoplay), context.getString(R.string.never), null));
        item.add(new Setting(context.getString(R.string.clear_cache), null, null));
        item.add(new Setting(context.getString(R.string.user_agreement), null, null));
        item.add(new Setting(context.getString(R.string.version), context.getString(R.string.version_name), null));

        return item;
    }

}
