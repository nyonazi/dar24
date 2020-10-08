package com.dar24.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dar24.app.BuildConfig;
import com.dar24.app.R;
import com.dar24.app.adapter.MenuAdapter;
import com.dar24.app.adapter.ViewPagerAdapter;
import com.dar24.app.fragment.MenuFragment;
import com.dar24.app.fragment.NewsFragment;
import com.dar24.app.fragment.VideoFragment;
import com.dar24.app.utility.ForceUpdateChecker;
import com.dar24.app.utility.PostCategory;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseAppCompatActivity implements MenuAdapter.MenuAdapterListener {

    private static boolean activityStarted;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private MenuFragment menuFragment;
    private String playstoreUrl ="https://play.google.com/store/apps/details?id=com.dar24.app";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (activityStarted && getIntent() != null && (getIntent().getFlags()
                & Intent.FLAG_ACTIVITY_REORDER_TO_FRONT) != 0) {
            finish();
            return;
        }
        activityStarted = true;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            menuFragment = new MenuFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayoutMenu, menuFragment).commit();
        }

        setViewPager();

        tabLayout.setupWithViewPager(viewPager);

        //Init adview
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView adView = findViewById(R.id.adView);
        adView.loadAd(adRequest);

       /**/
        new LongOperation().execute();

    }

    public void setViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Bundles
        Bundle bLatest = new Bundle();
        bLatest.putInt("category", 0);
        Bundle bAfya = new Bundle();
        bAfya.putInt("category", PostCategory.ID_AFYA);
        Bundle bBiashara = new Bundle();
        bBiashara.putInt("category", PostCategory.ID_BIASHARA);
        Bundle bBurudani = new Bundle();
        bBurudani.putInt("category", PostCategory.ID_BURUDANI);
        Bundle bHapoKale = new Bundle();
        bHapoKale.putInt("category", PostCategory.ID_HAPO_KALE);
        Bundle bMaisha = new Bundle();
        bMaisha.putInt("category", PostCategory.ID_MAISHA);
        Bundle bMichezo = new Bundle();
        bMichezo.putInt("category", PostCategory.ID_MICHEZO);
        Bundle bSiasaZetu = new Bundle();
        bSiasaZetu.putInt("category", PostCategory.ID_SIASA_ZETU);
        Bundle bTeknolojia = new Bundle();
        bTeknolojia.putInt("category", PostCategory.ID_TEKNOLOJIA);
        Bundle bAjira = new Bundle();
        bAjira.putInt("category", PostCategory.ID_AJIRA);

        //Fragments
        NewsFragment latest = new NewsFragment();
        latest.setArguments(bLatest);
        NewsFragment teknolojia = new NewsFragment();
        teknolojia.setArguments(bTeknolojia);
        NewsFragment siasa = new NewsFragment();
        siasa.setArguments(bSiasaZetu);
        NewsFragment michezo = new NewsFragment();
        michezo.setArguments(bMichezo);
        NewsFragment maisha = new NewsFragment();
        maisha.setArguments(bMaisha);
        NewsFragment hapoKale = new NewsFragment();
        hapoKale.setArguments(bHapoKale);
        NewsFragment burudani = new NewsFragment();
        burudani.setArguments(bBurudani);
        NewsFragment biashara = new NewsFragment();
        biashara.setArguments(bBiashara);
        NewsFragment afya = new NewsFragment();
        afya.setArguments(bAfya);
        NewsFragment ajira = new NewsFragment();
        ajira.setArguments(bAjira);

        //Add fragments to viewpager
        viewPagerAdapter.addFragment(new ViewPagerAdapter.ViewPagerFragment(
                latest, getString(R.string.latest)));
        viewPagerAdapter.addFragment(new ViewPagerAdapter.ViewPagerFragment(
                new VideoFragment(), getString(R.string.dar24_tv)));
        viewPagerAdapter.addFragment(new ViewPagerAdapter.ViewPagerFragment(
                michezo, PostCategory.NAME_MICHEZO));
        viewPagerAdapter.addFragment(new ViewPagerAdapter.ViewPagerFragment(
                ajira, PostCategory.NAME_AJIRA));
        viewPagerAdapter.addFragment(new ViewPagerAdapter.ViewPagerFragment(
                burudani, PostCategory.NAME_BURUDANI));
        viewPagerAdapter.addFragment(new ViewPagerAdapter.ViewPagerFragment(
                siasa, PostCategory.NAME_SIASA_ZETU));
        viewPagerAdapter.addFragment(new ViewPagerAdapter.ViewPagerFragment(
                biashara, PostCategory.NAME_BIASHARA));
        viewPagerAdapter.addFragment(new ViewPagerAdapter.ViewPagerFragment(
                teknolojia, PostCategory.NAME_TEKNOLOJIA));
        viewPagerAdapter.addFragment(new ViewPagerAdapter.ViewPagerFragment(
                maisha, PostCategory.NAME_MAISHA));
        viewPagerAdapter.addFragment(new ViewPagerAdapter.ViewPagerFragment(
                afya, PostCategory.NAME_AFYA));
        viewPagerAdapter.addFragment(new ViewPagerAdapter.ViewPagerFragment(
                hapoKale, PostCategory.NAME_HAPO_KALE));

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onTopMenuItemSelected(int position) throws Exception {
        onBackPressed();
        switch (position) {
            case 0:
                viewPager.setCurrentItem(0);
                menuFragment.updateTopMenuItem(0);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                menuFragment.updateTopMenuItem(1);
                break;
            case 2:
                viewPager.setCurrentItem(2);
                menuFragment.updateTopMenuItem(2);
                break;
            case 3:
                viewPager.setCurrentItem(3);
                menuFragment.updateTopMenuItem(3);
                break;
            case 4:
                viewPager.setCurrentItem(4);
                menuFragment.updateTopMenuItem(4);
                break;
            case 5:
                viewPager.setCurrentItem(5);
                menuFragment.updateTopMenuItem(5);
                break;
            case 6:
                viewPager.setCurrentItem(6);
                menuFragment.updateTopMenuItem(6);
                break;
            case 7:
                viewPager.setCurrentItem(7);
                menuFragment.updateTopMenuItem(7);
                break;
            case 8:
                viewPager.setCurrentItem(8);
                menuFragment.updateTopMenuItem(8);
                break;
            case 9:
                viewPager.setCurrentItem(9);
                menuFragment.updateTopMenuItem(9);
                break;
            case 10:
                viewPager.setCurrentItem(10);
                menuFragment.updateTopMenuItem(10);
                break;
        }
    }

    @Override
    public void onBottomMenuItemSelected(int position) {
        onBackPressed();
        switch (position) {
            case 0:
                startActivity(new Intent(this, SubscribeActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case 3:
                Intent termsIntent = new Intent(this, WebActivity.class);
                termsIntent.putExtra("title", "Privacy Policy");
                termsIntent.putExtra("url", "http://dar24.com/privacy-policy/");
                startActivity(termsIntent);
                break;
            case 4:
                Toast.makeText(this, "Opening email app...", Toast.LENGTH_LONG).show();
                Intent intents = new Intent(Intent.ACTION_SENDTO);
                intents.setData(Uri.parse("mailto:")); // only email apps should handle this
                String[] to = {"developers.dar24@gmail.com"};
                intents.putExtra(Intent.EXTRA_EMAIL, to);
                intents.putExtra(Intent.EXTRA_SUBJECT, "Feedback: from Dar24 android app");
                intents.putExtra(Intent.EXTRA_TEXT, "Write your feedback here,\n\n");
                if (intents.resolveActivity(this.getPackageManager()) != null) {
                    startActivity(intents);
                } else {
                    Toast.makeText(this, "No email app found.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
       * handling remote config
     * */
    private void updateDialog(final String url) {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.new_version_available)
                .setMessage(R.string.please_update_app)
                .setPositiveButton(R.string.Sasisha,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(url);
                            }
                        }).setNegativeButton(R.string.hapana_ahsante, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            remoteConfig();
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public void remoteConfig(){
        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();

        Map<String, Object> remoteConfigDefaults = new HashMap();
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED, true);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION, 10);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_URL, playstoreUrl);

        firebaseRemoteConfig.setDefaults(remoteConfigDefaults);
        firebaseRemoteConfig.setConfigSettings(configSettings);
        firebaseRemoteConfig.fetch(60)// fetch every minutesvers
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            int updatedVersion = (int) firebaseRemoteConfig.getLong("force_update_current_version");
//                            Log.i("MAin Activity", String.valueOf(updatedVersion));

                            int versionCode = BuildConfig.VERSION_CODE;
                            if (versionCode < updatedVersion) {
                                updateDialog(playstoreUrl);
                            } else {
//                                Log.i("MAin Activity", "Version is okay");

                            }
                        }
                    }
                });
    }

}
