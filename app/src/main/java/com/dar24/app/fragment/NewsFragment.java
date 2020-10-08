package com.dar24.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dar24.app.R;
import com.dar24.app.adapter.NewsAdapter;
import com.dar24.app.endpoint.EndPointService;
import com.dar24.app.endpoint.EndPointUrl;
import com.dar24.app.model.News;
import com.dar24.app.model.NewsRelated;
import com.dar24.app.model.author.AuthorResponse;
import com.dar24.app.model.news.JetpackRelatedPostsItem;
import com.dar24.app.model.news.NewsResponse;
import com.dar24.app.utility.EndlessRecyclerViewScrollListener;
import com.dar24.app.utility.Helpers;
import com.dar24.app.utility.PostCategory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
public class NewsFragment extends Fragment {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private final OkHttpClient mClient = new OkHttpClient.Builder()
            .addInterceptor(interceptor )
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
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private int CURRENT_PAGE = 1;
    private NewsAdapter newsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Call<List<NewsResponse>> callNews;
    private Call<List<AuthorResponse>> callAuthors;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);

        newsAdapter = new NewsAdapter(getActivity());
        recyclerView.setAdapter(newsAdapter);


        final Bundle bundle = getArguments();
        if (bundle.getInt("category") == 0) {
            fetchNews(null);
        } else {
            fetchNews(bundle.getInt("category"));
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CURRENT_PAGE = 1;
                if (bundle.getInt("category") == 0) {
                    fetchNews(null);
                } else {
                    fetchNews(bundle.getInt("category"));
                }
            }
        });
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if (newsAdapter.getItemCount() > 20) {
                    CURRENT_PAGE += 1;
                    if (bundle.getInt("category") == 0) {
                        fetchNews(null);
                    } else {
                        fetchNews(bundle.getInt("category"));
                    }
                }
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);

        return rootView;
    }

    private void fetchNews(Integer category) {
        swipeRefreshLayout.setRefreshing(true);
        if (Helpers.isConnected(getActivity())) {
            int perPage = 25;
            if (category != null) {
                perPage = category == 285 ? 7 : 25;
            }
            EndPointService service = mRetrofit.create(EndPointService.class);
            callNews = service.getPosts(category, "desc",
                    "date", perPage, CURRENT_PAGE);


            callNews.enqueue(new Callback<List<NewsResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<NewsResponse>> call,
                                       @NonNull Response<List<NewsResponse>> response) {

                    if (!call.isCanceled()) {

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
                            if (newsAdapter.getItemCount() == 0 && !callAuthors.isExecuted()) {
                                llMessage.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                //Show the error message
                                Toast.makeText(getActivity(), R.string.something_went_wrong,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<NewsResponse>> call,
                                      @NonNull Throwable t) {

                    if (!call.isCanceled()) {
                        t.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                        llMessage.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), R.string.something_went_wrong + "::2",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            llMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchAuthors(final List<NewsResponse> list, final String authorIds) {
        if (Helpers.isConnected(getActivity())) {
            EndPointService service = mRetrofit.create(EndPointService.class);
            callAuthors = service.getAuthors(authorIds, 100, 1);
            callAuthors.enqueue(new Callback<List<AuthorResponse>>() {
                @Override
                public void onResponse(Call<List<AuthorResponse>> call, Response<List<AuthorResponse>> response) {
                    if (!call.isCanceled()) {
                        if (response.isSuccessful()) {
                            swipeRefreshLayout.setRefreshing(false);
                            List<AuthorResponse> authorResponseList = response.body();
                            List<News> news = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                NewsResponse newsResponse = list.get(i);
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
                            if (CURRENT_PAGE > 1) {
                                newsAdapter.addMoreItems(news);
                            } else {
                                newsAdapter.addItems(news);
                            }

                            if (newsAdapter.getItemCount() == 0) {
                                llMessage.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            } else {
                                llMessage.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            llMessage.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), R.string.something_went_wrong + "::3",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<AuthorResponse>> call, Throwable t) {
                    if (!call.isCanceled()) {
                        t.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                        llMessage.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), R.string.something_went_wrong + "::4",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            llMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (callNews != null) callNews.cancel();
        if (callAuthors != null) callAuthors.cancel();
    }
}
