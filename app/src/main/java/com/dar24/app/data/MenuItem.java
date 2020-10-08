package com.dar24.app.data;

import android.content.Context;

import com.dar24.app.R;
import com.dar24.app.model.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class MenuItem {

    public static List<Menu> getBottomMenu(Context context) {
        List<Menu> items = new ArrayList<>();
        items.add(new Menu(context.getString(R.string.subscribe_to_dar24), null, null));
        items.add(new Menu(context.getString(R.string.settings), null, null));
        items.add(new Menu(context.getString(R.string.about_dar24), null, null));
        items.add(new Menu(context.getString(R.string.privacy_policy), null, null));
        items.add(new Menu(context.getString(R.string.feedback), null, null));
        items.add(new Menu(context.getString(R.string.c_2018), null, null));
        return items;
    }

    public static List<Menu> getTopMenu(Context context) {
        List<Menu> items = new ArrayList<>();
        items.add(new Menu(context.getString(R.string.latest), null, null));
        items.add(new Menu(context.getString(R.string.dar24_tv), null, null));
        items.add(new Menu(context.getString(R.string.michezo), null, null));
        items.add(new Menu(context.getString(R.string.ajira), null, null));
        items.add(new Menu(context.getString(R.string.burudani), null, null));
        items.add(new Menu(context.getString(R.string.siasa_zetu), null, null));
        items.add(new Menu(context.getString(R.string.biashara), null, null));
        items.add(new Menu(context.getString(R.string.teknolojia), null, null));
        items.add(new Menu(context.getString(R.string.maisha), null, null));
        items.add(new Menu(context.getString(R.string.afya), null, null));
        items.add(new Menu(context.getString(R.string.hapo_kale), null, null));
        return items;
    }
}
