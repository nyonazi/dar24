package com.dar24.app.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.dar24.app.utility.LocaleManager;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 1.0.0
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(LocaleManager.setLocale(newBase)));

    }

}
