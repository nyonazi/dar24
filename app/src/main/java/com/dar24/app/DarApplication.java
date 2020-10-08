package com.dar24.app;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.dar24.app.data.SettingItem;
import com.dar24.app.notification.MyNotificationOpenedHandler;
import com.dar24.app.notification.MyNotificationReceivedHandler;
import com.dar24.app.utility.Developer;
import com.dar24.app.utility.LocaleManager;
import com.dar24.app.utility.SharedPref;
import com.google.android.gms.ads.MobileAds;
import com.onesignal.OneSignal;

import java.util.Locale;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class DarApplication extends Application {

    private static Context context;
    private SharedPref pref;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        pref = new SharedPref(this);

        //Init language
        Resources resource = getResources();
        Configuration config = resource.getConfiguration();

        if (pref.getLanguage() == SharedPref.LANGUAGE_ENGLISH) {
            config.locale = Locale.ENGLISH;
        } else if (pref.getLanguage() == SharedPref.LANGUAGE_KISWAHILI) {
            config.locale = new Locale("sw");
        } else {
            config.locale = new Locale("sw");
        }

        getBaseContext().getResources().updateConfiguration(config, null);

        //Admob init
        MobileAds.initialize(this, Developer.ADMOB_ID);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
                .setNotificationReceivedHandler(new MyNotificationReceivedHandler())

                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        //Calligraphy
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Ubuntu-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

    public static Context getAppContext(){
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
        //Log.d("DarApplication", "attachBaseContext");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
        //Log.d("DarApplication", "onConfigurationChanged: " + newConfig.locale.getLanguage());
    }
}
