package com.dar24.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dar24.app.R;
import com.dar24.app.endpoint.EndPointService;
import com.dar24.app.endpoint.EndPointUrl;
import com.dar24.app.model.News;
import com.dar24.app.model.NewsRelated;
import com.dar24.app.model.author.AuthorResponse;
import com.dar24.app.model.news.JetpackRelatedPostsItem;
import com.dar24.app.model.news.NewsResponse;
import com.dar24.app.utility.Helpers;
import com.dar24.app.utility.PostCategory;
import com.dar24.app.utility.SharedPref;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class NewsRelatedDetailActivity extends BaseAppCompatActivity {

    private final OkHttpClient mClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create();
    final Retrofit mRetrofit = new Retrofit.Builder()
            .client(mClient)
            .baseUrl(EndPointUrl.BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            //.addConverterFactory(ScalarsConverterFactory.create())
            .build();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottomsheet)
    BottomSheetLayout bottomSheetLayout;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvPublishedAt)
    TextView tvPublishedAt;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.ivThumbnail)
    ImageView ivThumbnail;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private NewsRelated newsRelated;
    private News news;
    private SharedPref pref;
    private int descriptionSize;
    private Call<NewsResponse> callNews;
    private Call<List<AuthorResponse>> callAuthors;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_related_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref = new SharedPref(this);

        Bundle bundle = getIntent().getExtras();
        newsRelated = Parcels.unwrap(bundle.getParcelable("news"));
        if(newsRelated == null){
            finish();
            Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();
        }

        fetchNews();
    }

    private void setWebView(){
        if (pref.getFontSize() == SharedPref.FONT_SIZE_SMALL) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tvPublishedAt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            descriptionSize = 15;
        } else if (pref.getFontSize() == SharedPref.FONT_SIZE_MEDIUM) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            tvPublishedAt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            descriptionSize = 17;
        } else if (pref.getFontSize() == SharedPref.FONT_SIZE_LARGE) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tvPublishedAt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            descriptionSize = 20;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvTitle.setText(Html.fromHtml(news.getTitle(),
                    Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvTitle.setText(Html.fromHtml(newsRelated.getTitle()));
        }
        tvPublishedAt.setText(Helpers.getTimeAgo(this,
                Helpers.stringDateToLong(news.getPublishedAt())));
        Picasso.get().load(newsRelated.getThumbnail())
                .placeholder(R.color.grey_200)
                .into(ivThumbnail);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost().length() == 0) {
                    return false;
                }
                /*This line is commented to stop link click to function in webview*/
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                view.getContext().startActivity(intent);
                Intent aboutIntent = new Intent(NewsRelatedDetailActivity.this, WebActivity.class);
                aboutIntent.putExtra("title", url);
                aboutIntent.putExtra("url", url);
                startActivity(aboutIntent);
                return true;
            }
        });

        webView.setVisibility(View.VISIBLE);
        webView.loadDataWithBaseURL("file:///android_asset/",
                buildHtml(news.getDescription(), descriptionSize), "text/html",
                "UTF-8", null);
    }

    private String buildHtml(String data, int textSize) {
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <style>\n" +
                "        @font-face {\n" +
                "            font-family: 'ubuntu';\n" +
                "            src: url('fonts/Ubuntu-Regular.ttf');\n" +
                "        }\n" +
                "        body {\n" +
                "            font-family: \"ubuntu\";\n" +
                "            background-color: #FAFAFA;\n" +
                "            width: 100%;\n" +
                "            font-size: " + textSize + "px;" +
                "            text-align: justify;\n" +
                "            margin: auto;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        iframe {\n" +
                "            width: 100%;\n" +
                "            height: auto;\n" +
                "        }\n" +
                "\n" +
                "        img {\n" +
                "            max-width:100% !important;\n" +
                "            width: 100% !important;\n" +
                "            height:auto !important;\n" +
                "        }\n" +
                "\n" +
                "        div {\n" +
                "            max-width:100% !important;\n" +
                "            width: 100% !important;\n" +
                "            height:auto !important;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>";
        html += data;
        html += "<br/><br/>" +
                "</body>\n" +
                "\n" +
                "</html>";
        return html;
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

    private void fetchNews() {
        swipeRefreshLayout.setRefreshing(true);
        if (Helpers.isConnected(this)) {
            EndPointService service = mRetrofit.create(EndPointService.class);
            callNews = service.getPost(newsRelated.getId());
            callNews.enqueue(new Callback<NewsResponse>() {
                @Override
                public void onResponse(@NonNull Call<NewsResponse> call,
                                       @NonNull Response<NewsResponse> response) {
                    if (!call.isCanceled()) {
                        if (response.isSuccessful()) {
                            List<String> ids = new ArrayList<>();
                            NewsResponse newsResponseList = response.body();

                            ids.add(String.valueOf(newsResponseList.getAuthor()));

                            //Remove [ and ] from a string
                            fetchAuthors(response.body(), ids.toString().replace("[", "")
                                    .replace("]", "").trim());
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                            //Show the error message
                            Toast.makeText(NewsRelatedDetailActivity.this, R.string.something_went_wrong,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<NewsResponse> call,
                                      @NonNull Throwable t) {
                    if (!call.isCanceled()) {
                        t.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(NewsRelatedDetailActivity.this, R.string.something_went_wrong + "::2",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(NewsRelatedDetailActivity.this, R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchAuthors(final NewsResponse newsResponse, final String authorIds) {
        if (Helpers.isConnected(this)) {
            EndPointService service = mRetrofit.create(EndPointService.class);
            callAuthors = service.getAuthors(authorIds, 100, 1);
            callAuthors.enqueue(new Callback<List<AuthorResponse>>() {
                @Override
                public void onResponse(Call<List<AuthorResponse>> call, Response<List<AuthorResponse>> response) {
                    if (!call.isCanceled()) {
                        if (response.isSuccessful()) {
                            swipeRefreshLayout.setRefreshing(false);
                            List<AuthorResponse> authorResponseList = response.body();
                            String thumbnail = "";
                            if (newsResponse.getBetterFeaturedImage() != null) {
                                if (newsResponse.getBetterFeaturedImage().getMediaDetails() != null) {
                                    if (newsResponse.getBetterFeaturedImage().getMediaDetails().getSizes() != null) {
                                        if (newsResponse.getBetterFeaturedImage().getMediaDetails()
                                                .getSizes().getBuzzmagMedium() != null) {
                                            thumbnail = newsResponse.getBetterFeaturedImage().getMediaDetails()
                                                    .getSizes().getBuzzmagMedium().getSourceUrl();
                                        } else if (newsResponse.getBetterFeaturedImage().getMediaDetails()
                                                .getSizes().getMedium() != null) {
                                            thumbnail = newsResponse.getBetterFeaturedImage().getMediaDetails()
                                                    .getSizes().getMedium().getSourceUrl();
                                        } else if (newsResponse.getBetterFeaturedImage().getMediaDetails()
                                                .getSizes().getBuzzmagFeatured() != null) {
                                            thumbnail = newsResponse.getBetterFeaturedImage().getMediaDetails()
                                                    .getSizes().getBuzzmagFeatured().getSourceUrl();
                                        } else if (newsResponse.getBetterFeaturedImage().getMediaDetails()
                                                .getSizes().getThumbnail() != null) {
                                            thumbnail = newsResponse.getBetterFeaturedImage().getMediaDetails()
                                                    .getSizes().getThumbnail().getSourceUrl();
                                        } else if (newsResponse.getBetterFeaturedImage().getMediaDetails()
                                                .getSizes().getMediumLarge() != null) {
                                            thumbnail = newsResponse.getBetterFeaturedImage().getMediaDetails()
                                                    .getSizes().getMediumLarge().getSourceUrl();
                                        } else if (newsResponse.getBetterFeaturedImage().getMediaDetails()
                                                .getSizes().getBuzzmagSmall() != null) {
                                            thumbnail = newsResponse.getBetterFeaturedImage().getMediaDetails()
                                                    .getSizes().getBuzzmagSmall().getSourceUrl();
                                        }
                                    }
                                }
                            }
                            String author = "Dar24";
                            for (int j = 0; j < authorResponseList.size(); j++) {
                                if (newsResponse.getAuthor() == authorResponseList.get(j).getId()) {
                                    author = authorResponseList.get(j).getName();
                                    break;
                                }
                            }
                            int categoryId = 10;
                            if (newsResponse.getCategories() != null
                                    && newsResponse.getCategories().size() > 0) {
                                categoryId = newsResponse.getCategories().get(0);
                            }

                            List<NewsRelated> newsRelatedList = new ArrayList<>();
                            if (newsResponse.getJetpackRelatedPosts() != null) {
                                List<JetpackRelatedPostsItem> relatedPostsItems =
                                        newsResponse.getJetpackRelatedPosts();

                                for (int r = 0; r < relatedPostsItems.size(); r++) {
                                    newsRelatedList.add(new NewsRelated(
                                            relatedPostsItems.get(r).getId(),
                                            relatedPostsItems.get(r).getTitle(),
                                            relatedPostsItems.get(r).getExcerpt(),
                                            relatedPostsItems.get(r).getImg() != null ?
                                                    relatedPostsItems.get(r).getImg().getSrc() : "",
                                            relatedPostsItems.get(r).getDate(),
                                            relatedPostsItems.get(r).getUrl()
                                    ));
                                }
                            }

                            news = new News(newsResponse.getTitle().getRendered(),
                                    PostCategory.getCategoryById(categoryId),
                                    newsResponse.getExcerpt().getRendered(),
                                    newsResponse.getContent().getRendered(),
                                    thumbnail,
                                    newsResponse.getDate(),
                                    author,
                                    newsResponse.getLink(),
                                    newsRelatedList);

                            setWebView();
                        } else {
                            Toast.makeText(NewsRelatedDetailActivity.this,
                                    R.string.something_went_wrong + "::3",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<AuthorResponse>> call, Throwable t) {
                    if (!call.isCanceled()) {
                        t.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(NewsRelatedDetailActivity.this,
                                R.string.something_went_wrong + "::4",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(NewsRelatedDetailActivity.this, R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (callNews != null) callNews.cancel();
        if (callAuthors != null) callAuthors.cancel();
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
                intent.putExtra("url", newsRelated.getLink());
                startActivity(intent);
                break;
            case R.id.action_share:
                final Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, newsRelated.getLink());
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

}
