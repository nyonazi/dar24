package com.dar24.app.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class SharedPref {

    public static final int LANGUAGE_ENGLISH = 0;
    public static final int LANGUAGE_KISWAHILI = 1;
    public static final int VIDEO_AUTOPLAY_NEVER = 0;
    public static final int VIDEO_AUTOPLAY_WIFI = 1;
    public static final int VIDEO_AUTOPLAY_ALWAYS = 2;
    public static final int FONT_SIZE_SMALL = 0;
    public static final int FONT_SIZE_MEDIUM = 1;
    public static final int FONT_SIZE_LARGE = 2;
    public final String BASE = "dar24";
    private final String KEY_LANGUAGE = "language";
    private final String KEY_NOTIFICATION = "notification";
    private final String KEY_FONT_SIZE = "font_size";
    private final String KEY_VIDEO_AUTOPLAY = "video_autoplay";
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;

    public SharedPref(Context c) {
        this.mSharedPref = c.getSharedPreferences(BASE,
                Context.MODE_PRIVATE);
        this.mEditor = mSharedPref.edit();
    }

    public void setLanguage(int language) {
        mEditor.putInt(KEY_LANGUAGE, language);
        mEditor.commit();
    }

    public int getLanguage(){
        return mSharedPref.getInt(KEY_LANGUAGE, 1);
    }

    public void setNotification(boolean status){
        mEditor.putBoolean(KEY_NOTIFICATION, status);
        mEditor.commit();
    }

    public boolean getNotification(){
        return mSharedPref.getBoolean(KEY_NOTIFICATION, true);
    }

    public void setFontSize(int size){
        mEditor.putInt(KEY_FONT_SIZE, size);
        mEditor.commit();
    }

    public int getFontSize(){
        return mSharedPref.getInt(KEY_FONT_SIZE, 1);
    }

    public void setVideoAutoplay(int value){
        mEditor.putInt(KEY_VIDEO_AUTOPLAY, value);
        mEditor.commit();
    }

    public int getVideoAutoplay(){
        return mSharedPref.getInt(KEY_VIDEO_AUTOPLAY, 0);
    }
}
