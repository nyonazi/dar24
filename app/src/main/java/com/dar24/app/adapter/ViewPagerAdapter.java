package com.dar24.app.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    List<ViewPagerFragment> mFragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(ViewPagerFragment fragment) {
        if (fragment != null) {
            mFragments.add(fragment);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }

    public static class ViewPagerFragment {

        private Fragment fragment;
        private String title;

        public ViewPagerFragment(Fragment fragment, String title) {
            this.fragment = fragment;
            this.title = title;
        }

        public Fragment getFragment() {
            return this.fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
