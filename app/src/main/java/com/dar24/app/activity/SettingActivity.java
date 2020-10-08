package com.dar24.app.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dar24.app.R;
import com.dar24.app.adapter.SettingAdapter;
import com.dar24.app.data.SettingItem;
import com.dar24.app.model.Setting;
import com.dar24.app.utility.LocaleManager;
import com.dar24.app.utility.SharedPref;
import com.onesignal.OneSignal;

import java.io.File;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class SettingActivity extends BaseAppCompatActivity implements
        SettingAdapter.SettingListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private SettingAdapter settingAdapter;
    private SharedPref pref;
    private List<Setting> settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setTitle(getString(R.string.settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = new SharedPref(this);

        settingAdapter = new SettingAdapter(this);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.swapAdapter(settingAdapter, true);

        settings = SettingItem.getSettings(this);

        for (int i = 0; i < settings.size(); i++) {
            switch (i) {
                case 0:
                    if (pref.getLanguage() == 0) {
                        settings.get(i).setDescription(getString(R.string.english_international));
                    } else {
                        settings.get(i).setDescription(getString(R.string.kiswahili));
                    }
                    break;
                case 1:
                    if(pref.getFontSize() == SharedPref.FONT_SIZE_SMALL){
                        settings.get(i).setDescription(getString(R.string.small));
                    } else if(pref.getFontSize() == SharedPref.FONT_SIZE_LARGE){
                        settings.get(i).setDescription(getString(R.string.large));
                    } else {
                        settings.get(i).setDescription(getString(R.string.medium));
                    }
                    break;
                case 2:
                    if(pref.getNotification()){
                        settings.get(i).setActive(true);
                        OneSignal.setSubscription(true);
                    } else {
                        settings.get(i).setActive(false);
                        OneSignal.setSubscription(false);
                    }
                    break;
                case 3:
                    break;
            }
        }

        settingAdapter.addItems(settings);

    }

    private void languageDialog() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.language))
                .items(getString(R.string.english_international), getString(R.string.kiswahili))
                .itemsCallbackSingleChoice(pref.getLanguage(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which,
                                               CharSequence text) {
                        changeLanguage(which);
                        return true;
                    }
                })
                .positiveText(R.string.change)
                .negativeText(R.string.cancel)
                .show();
    }

    private void fontDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.font_size)
                .items(getString(R.string.small), getString(R.string.medium), getString(R.string.large))
                .itemsCallbackSingleChoice(pref.getFontSize(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        changeFontSize(which);
                        return true;
                    }
                })
                .positiveText(R.string.change)
                .negativeText(R.string.cancel)
                .show();
    }

    private void videoAutoPlay(){
        new MaterialDialog.Builder(this)
                .title(getString(R.string.video_autoplay))
                .items(getString(R.string.always), getString(R.string.only_on_wifi), getString(R.string.never))
                .itemsCallbackSingleChoice(pref.getVideoAutoplay(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        changeVideoAutoplay(which);
                        return true;
                    }
                })
                .positiveText(R.string.change)
                .negativeText(R.string.cancel)
                .show();
    }

    private void changeVideoAutoplay(int value){
        Setting setting = SettingItem.getSettings(this).get(3);
        if(value == SharedPref.VIDEO_AUTOPLAY_NEVER){
            setting.setDescription(getString(R.string.never));
        } else if(value == SharedPref.VIDEO_AUTOPLAY_WIFI){
            setting.setDescription(getString(R.string.only_on_wifi));
        } else {
            setting.setDescription(getString(R.string.always));
        }
        settingAdapter.updateItem(3, setting);
        pref.setVideoAutoplay(value);
    }


    /**
     * Change app language
     *
     * @param value
     */
    private void changeLanguage(int value) {
        Setting setting = SettingItem.getSettings(this).get(0);

        if (value == SharedPref.LANGUAGE_ENGLISH) {
            LocaleManager.setNewLocale(this,"en");
            setting.setDescription(getString(R.string.english_international));
        } else if (value == SharedPref.LANGUAGE_KISWAHILI) {
            LocaleManager.setNewLocale(this,"sw");
            setting.setDescription(getString(R.string.kiswahili));
        }
        Intent i = new Intent(this, SettingActivity.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

        pref.setLanguage(value);

        settingAdapter.updateItem(0, setting);
    }

    private void changeFontSize(int value){
        Setting setting = SettingItem.getSettings(this).get(1);
        if(value == SharedPref.FONT_SIZE_SMALL){
            setting.setDescription(getString(R.string.small));
        } else if(value == SharedPref.FONT_SIZE_LARGE){
            setting.setDescription(getString(R.string.large));
        } else {
            setting.setDescription(getString(R.string.medium));
        }
        settingAdapter.updateItem(1, setting);
        pref.setFontSize(value);
    }

    @Override
    public void onSelected(int position) {
        switch (position) {
            case 0:
                languageDialog();
                break;
            case 1:
                fontDialog();
                break;
            case 2:
                pref.setNotification(!pref.getNotification());
                break;
            case 3:
                videoAutoPlay();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
