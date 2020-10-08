package com.dar24.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dar24.app.R;
import com.dar24.app.adapter.ViewPagerAdapter;
import com.dar24.app.fragment.NewsDetailFragment;
import com.dar24.app.model.News;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class NewsDetailActivity extends BaseAppCompatActivity implements
        NewsDetailFragment.NewsDetailFragmentListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottomsheet)
    BottomSheetLayout bottomSheetLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<News> newsList;
    private int currentNews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.detail));

        newsList = Parcels.unwrap(getIntent().getParcelableExtra("news"));
        currentNews = getIntent().getIntExtra("current", 0);
        if (newsList == null || newsList.size() == 0) {
            Toast.makeText(this, R.string.unknown_news, Toast.LENGTH_SHORT).show();
            finish();
        }

        setViewPager();

        //Init adview
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView adView = findViewById(R.id.adView);
        adView.loadAd(adRequest);
    }

    public void setViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle;
        NewsDetailFragment newsDetailFragment;

        for (int i = 0; i < newsList.size(); i++) {
            //Bundles
            bundle = new Bundle();
            bundle.putParcelable("news", Parcels.wrap(newsList.get(i)));
            if(newsList.size() != 1){
                if (i == 0) {
                    bundle.putString("next_title", newsList.get(i + 1).getTitle());
                } else if ((i + 1) < newsList.size()) {
                    bundle.putString("next_title", newsList.get(i + 1).getTitle());
                    bundle.putString("previous_title", newsList.get(i - 1).getTitle());
                } else if ((i + 1) == newsList.size()) {
                    bundle.putString("previous_title", newsList.get(i - 1).getTitle());
                }
            }

            //Fragments
            newsDetailFragment = new NewsDetailFragment();
            newsDetailFragment.setArguments(bundle);

            //Add fragments to viewpager
            viewPagerAdapter.addFragment(new ViewPagerAdapter.ViewPagerFragment(
                    newsDetailFragment, String.valueOf(i)));
        }
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(currentNews);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_comment:
                // launching Facebook comments activity
                Intent intent = new Intent(this, CommentActivity.class);

                // passing the article url
                intent.putExtra("url", newsList.get(viewPager.getCurrentItem()).getLink());
                startActivity(intent);
                break;
            case R.id.action_share:
                final Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, newsList.get(viewPager.getCurrentItem()).getLink());
                shareIntent.setType("text/plain");
                bottomSheetLayout.showWithSheetView(new IntentPickerSheetView(this,
                        shareIntent, getString(R.string.share_with),
                        new IntentPickerSheetView.OnIntentPickedListener() {
                            @Override
                            public void onIntentPicked(
                                    IntentPickerSheetView.ActivityInfo activityInfo) {
                                bottomSheetLayout.dismissSheet();
                                startActivity(activityInfo.getConcreteIntent(shareIntent));
                            }
                        }));
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onNext() {
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
    }

    @Override
    public void onPrevious() {
        viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
    }
}
