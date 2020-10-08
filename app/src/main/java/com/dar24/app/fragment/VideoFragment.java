package com.dar24.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dar24.app.R;
import com.dar24.app.activity.SearchActivity;
import com.dar24.app.adapter.ViewPagerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class VideoFragment extends Fragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, rootView);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.new_video));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.playlist));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, new VideoLatestFragment()).commit();
                } else {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, new VideoPlaylistFragment()).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.getTabAt(0).select();
        getFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new VideoLatestFragment()).commitAllowingStateLoss();

        return rootView;
    }

}
