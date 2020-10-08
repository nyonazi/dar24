package com.dar24.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dar24.app.R;
import com.dar24.app.adapter.NewsAdapter;
import com.dar24.app.adapter.NewsSearchAdapter;
import com.dar24.app.endpoint.EndPointService;
import com.dar24.app.endpoint.EndPointUrl;
import com.dar24.app.model.News;
import com.dar24.app.model.NewsRelated;
import com.dar24.app.model.author.AuthorResponse;
import com.dar24.app.model.news.JetpackRelatedPostsItem;
import com.dar24.app.model.news.NewsResponse;
import com.dar24.app.utility.Helpers;
import com.dar24.app.utility.PostCategory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
public class SearchActivity extends BaseAppCompatActivity {
    
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
    @BindView(R.id.llMessage)
    LinearLayout llMessage;
    @BindView(R.id.ivIcon)
    ImageView ivMessage;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private NewsSearchAdapter newsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Call<List<NewsResponse>> callNews;
    private Call<List<AuthorResponse>> callAuthors;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String searchWords;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        newsAdapter = new NewsSearchAdapter(this);
        recyclerView.setAdapter(newsAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchWords = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                fetchNews(searchWords);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNews(searchWords);
            }
        });
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

    private void fetchNews(String search) {
        swipeRefreshLayout.setRefreshing(true);
        if (Helpers.isConnected(this)) {
            EndPointService service = mRetrofit.create(EndPointService.class);
            callNews = service.getSearchPosts(search, "desc",
                    "date", 25, 1);
            callNews.enqueue(new Callback<List<NewsResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<NewsResponse>> call,
                                       @NonNull Response<List<NewsResponse>> response) {
                    if(!call.isCanceled()) {
                        if (response.isSuccessful()) {
                            List<String> ids = new ArrayList<>();
                            List<NewsResponse> newsResponseList = response.body();
                            for (int i = 0; i < newsResponseList.size(); i++) {
                                ids.add(String.valueOf(newsResponseList.get(i).getAuthor()));
                            }
                            //Remove [ and ] from a string
                            fetchAuthors(response.body(), ids.toString().replace("[", "")
                                    .replace("]", "").trim());
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                            llMessage.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setVisibility(View.GONE);
                            //Show the error message
                            Toast.makeText(SearchActivity.this, R.string.something_went_wrong,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<NewsResponse>> call,
                                      @NonNull Throwable t) {
                    if(!call.isCanceled()) {
                        t.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                        llMessage.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                        //Show the error message
                    }
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            llMessage.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
            Toast.makeText(this, R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchAuthors(final List<NewsResponse> list, final String authorIds) {
        if (Helpers.isConnected(this)) {
            EndPointService service = mRetrofit.create(EndPointService.class);
            callAuthors = service.getAuthors(authorIds, 100, 1);
//            callAuthors = service.getAuthors(authorIds, 100, 1);
            callAuthors.enqueue(new Callback<List<AuthorResponse>>() {
                @Override
                public void onResponse(Call<List<AuthorResponse>> call, Response<List<AuthorResponse>> response) {
                    if(!call.isCanceled()) {
                        if (response.isSuccessful()) {
                            swipeRefreshLayout.setRefreshing(false);
                            List<AuthorResponse> authorResponseList = response.body();
                            List<News> news = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                NewsResponse newsResponse = list.get(i);
                                String thumbnail = "";
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
                                String author = "Dar24";
                                for (int j = 0; j < authorResponseList.size(); j++) {
                                    if (newsResponse.getAuthor() == authorResponseList.get(j).getId()) {
                                        author = authorResponseList.get(j).getName();
                                        break;
                                    }
                                }
                                int categoryId = 10;
                                if(newsResponse.getCategories() != null
                                        && newsResponse.getCategories().size() > 0){
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

                                news.add(new News(newsResponse.getTitle().getRendered(),
                                        PostCategory.getCategoryById(categoryId),
                                        newsResponse.getExcerpt().getRendered(),
                                        newsResponse.getContent().getRendered(),
                                        thumbnail,
                                        newsResponse.getDate(),
                                        author,
                                        newsResponse.getLink(),
                                        newsRelatedList));

                            }
                            newsAdapter.addItems(news);
                            if (news.size() == 0) {
                                llMessage.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setVisibility(View.GONE);
                            } else {
                                llMessage.setVisibility(View.GONE);
                                swipeRefreshLayout.setVisibility(View.VISIBLE);
                            }
                        } else {
                            llMessage.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setVisibility(View.GONE);
                            Toast.makeText(SearchActivity.this, R.string.something_went_wrong,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<AuthorResponse>> call, Throwable t) {
                    if(!call.isCanceled()) {
                        t.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                        llMessage.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                        Toast.makeText(SearchActivity.this, R.string.something_went_wrong,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            llMessage.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
            Toast.makeText(this, R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (callNews != null) callNews.cancel();
        if (callAuthors != null) callAuthors.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (callNews != null) callNews.cancel();
        if (callAuthors != null) callAuthors.cancel();
    }
}
